using System;
using System.IO;
using System.Linq;
using System.Collections.Generic;
using Mono.Cecil;
using Mono.Cecil.Cil;

public static class PatchPcClientAuth
{
    private const string HelperAssemblyName = "ClientAuthPcLib";
    private const string HelperTypeName = "ClientAuthPcLib.ClientAuthPc";
    private const string HelperMethodName = "BuildJarPlatformToken";
    private const string HelperLogInfoMethodName = "LogInfo";
    private const string HelperLogWarningMethodName = "LogWarning";
    private const string HelperLogErrorMethodName = "LogError";
    private const string MeasurementOverrideFileName = "client_auth_measurement_override.txt";
    private const string ForcedPcOutputHash = "7BF6001829A3F0AA2BD1E54F03B3AAD091C94C9E849B2C8019CFCA0518BF1AAB";

    public static int Main(string[] args)
    {
        if (args.Length < 3)
        {
            Console.Error.WriteLine("Usage: PatchPcClientAuth <assembly-csharp.dll> <helper-dll> <measurements.txt> [client-root] [client-id]");
            return 1;
        }

        string assemblyPath = Path.GetFullPath(args[0]);
        string helperPath = Path.GetFullPath(args[1]);
        string measurementsPath = Path.GetFullPath(args[2]);
        string clientRoot = args.Length >= 4 ? Path.GetFullPath(args[3]) : FindClientRootFromAssembly(assemblyPath);
        string clientId = args.Length >= 5 ? args[4].Trim().ToLowerInvariant() : "kpahpc";

        if (!File.Exists(assemblyPath))
        {
            Console.Error.WriteLine("Khong tim thay Assembly-CSharp.dll: " + assemblyPath);
            return 2;
        }
        if (!File.Exists(helperPath))
        {
            Console.Error.WriteLine("Khong tim thay helper dll: " + helperPath);
            return 3;
        }

        PatchAssembly(assemblyPath, helperPath);
        string measurement = ComputeMeasurement(clientRoot);
        UpsertMeasurement(measurementsPath, clientId, measurement, helperPath);
        WriteMeasurementOverride(clientRoot, measurement);

        Console.WriteLine("CLIENT_ID=" + clientId);
        Console.WriteLine("MEASUREMENT=" + measurement);
        Console.WriteLine("HELPER_DLL=" + helperPath);
        Console.WriteLine("ASSEMBLY_PATH=" + assemblyPath);
        Console.WriteLine("CLIENT_ROOT=" + clientRoot);
        Console.WriteLine("MEASUREMENTS_FILE=" + measurementsPath);
        Console.WriteLine("MEASUREMENT_OVERRIDE=" + Path.Combine(clientRoot, MeasurementOverrideFileName));
        return 0;
    }

    private static void PatchAssembly(string assemblyPath, string helperPath)
    {
        string assemblyDir = Path.GetDirectoryName(assemblyPath);
        ReaderParameters readerParameters = new ReaderParameters
        {
            ReadWrite = false,
            ReadSymbols = false,
            AssemblyResolver = CreateResolver(assemblyDir)
        };
        ModuleDefinition helperModule = ModuleDefinition.ReadModule(helperPath, new ReaderParameters
        {
            ReadWrite = false,
            ReadSymbols = false,
            AssemblyResolver = CreateResolver(Path.GetDirectoryName(helperPath))
        });
        ModuleDefinition module = ModuleDefinition.ReadModule(assemblyPath, readerParameters);

        EnsureHelperReference(module, helperModule.Assembly.Name);
        TypeReference helperType = module.ImportReference(helperModule.GetType(HelperTypeName));
        MethodDefinition helperMethodDef = helperModule.GetType(HelperTypeName).Methods.First(m => m.Name == HelperMethodName);
        MethodReference helperMethod = module.ImportReference(helperMethodDef);

        TypeDefinition gameService = module.Types.FirstOrDefault(t => t.Name == "GameService");
        if (gameService == null)
        {
            throw new InvalidOperationException("Khong tim thay type GameService");
        }
        MethodDefinition loginMethod = gameService.Methods.FirstOrDefault(m => m.Name == "login" && m.Parameters.Count == 3);
        if (loginMethod == null || !loginMethod.HasBody)
        {
            throw new InvalidOperationException("Khong tim thay method GameService.login(String,String,String)");
        }

        ILProcessor il = loginMethod.Body.GetILProcessor();
        Instruction target = loginMethod.Body.Instructions.FirstOrDefault(i =>
                i.OpCode == OpCodes.Ldstr
                && string.Equals(i.Operand as string, "nokia/1/1", StringComparison.Ordinal));

        if (target != null)
        {
            Instruction callInstruction = il.Create(OpCodes.Call, helperMethod);
            il.Replace(target, callInstruction);
        }
        else
        {
            Instruction existingCall = loginMethod.Body.Instructions.FirstOrDefault(i =>
                    (i.OpCode == OpCodes.Call || i.OpCode == OpCodes.Callvirt)
                    && i.Operand is MethodReference
                    && ((MethodReference)i.Operand).Name == HelperMethodName);
            if (existingCall == null)
            {
                throw new InvalidOperationException("Khong tim thay diem chen platform token trong GameService.login");
            }
            existingCall.OpCode = OpCodes.Call;
            existingCall.Operand = helperMethod;
        }

        PatchDisconnectHandling(module);
        PatchOutLogging(module, helperModule);

        string tempPath = assemblyPath + ".patched";
        WriterParameters writerParameters = new WriterParameters { WriteSymbols = false };
        module.Write(tempPath, writerParameters);
        module.Dispose();
        helperModule.Dispose();

        File.Copy(tempPath, assemblyPath, true);
        File.Delete(tempPath);
    }

    private static void PatchDisconnectHandling(ModuleDefinition module)
    {
        TypeDefinition gameScr = module.Types.FirstOrDefault(t => t.Name == "GameScr");
        TypeDefinition main = module.Types.FirstOrDefault(t => t.Name == "Main");
        TypeDefinition canvas = module.Types.FirstOrDefault(t => t.Name == "Canvas");
        if (gameScr == null || main == null || canvas == null)
        {
            throw new InvalidOperationException("Khong tim thay GameScr/Main/Canvas de patch disconnect");
        }

        MethodDefinition onDisconnectMethod = gameScr.Methods.FirstOrDefault(m => m.Name == "onDisconnect" && m.Parameters.Count == 0);
        if (onDisconnectMethod == null || !onDisconnectMethod.HasBody)
        {
            throw new InvalidOperationException("Khong tim thay method GameScr.onDisconnect()");
        }

        FieldDefinition autoFightField = gameScr.Fields.FirstOrDefault(f => f.Name == "autoFight" && f.IsStatic);
        FieldDefinition isPCField = main.Fields.FirstOrDefault(f => f.Name == "isPC" && f.IsStatic);
        FieldDefinition currentScreenField = canvas.Fields.FirstOrDefault(f => f.Name == "currentScreen" && f.IsStatic);
        FieldDefinition loginScrField = canvas.Fields.FirstOrDefault(f => f.Name == "loginScr" && f.IsStatic);
        FieldDefinition isConnectFailField = canvas.Fields.FirstOrDefault(f => f.Name == "isConnectFail" && f.IsStatic);
        FieldDefinition isBeginAutoLoginField = canvas.Fields.FirstOrDefault(f => f.Name == "isBeginAutoLogin" && f.IsStatic);
        if (autoFightField == null || isPCField == null || currentScreenField == null || loginScrField == null
            || isConnectFailField == null || isBeginAutoLoginField == null)
        {
            throw new InvalidOperationException("Khong tim thay field can thiet de patch disconnect");
        }

        MethodBody body = onDisconnectMethod.Body;
        body.Instructions.Clear();
        body.ExceptionHandlers.Clear();
        body.Variables.Clear();
        body.InitLocals = false;

        ILProcessor il = body.GetILProcessor();
        Instruction setAutoLogin = il.Create(OpCodes.Ldc_I4_1);

        il.Append(il.Create(OpCodes.Ldc_I4_0));
        il.Append(il.Create(OpCodes.Stsfld, autoFightField));
        il.Append(il.Create(OpCodes.Ldsfld, isPCField));
        il.Append(il.Create(OpCodes.Brfalse_S, setAutoLogin));
        il.Append(il.Create(OpCodes.Ldsfld, currentScreenField));
        il.Append(il.Create(OpCodes.Ldsfld, loginScrField));
        il.Append(il.Create(OpCodes.Bne_Un_S, setAutoLogin));
        il.Append(il.Create(OpCodes.Ldc_I4_1));
        il.Append(il.Create(OpCodes.Stsfld, isConnectFailField));
        il.Append(il.Create(OpCodes.Ret));
        il.Append(setAutoLogin);
        il.Append(il.Create(OpCodes.Stsfld, isBeginAutoLoginField));
        il.Append(il.Create(OpCodes.Ret));
    }

    private static void PatchOutLogging(ModuleDefinition module, ModuleDefinition helperModule)
    {
        TypeDefinition outType = module.Types.FirstOrDefault(t => t.Name == "Out");
        TypeDefinition helperTypeDef = helperModule.GetType(HelperTypeName);
        if (outType == null || helperTypeDef == null)
        {
            throw new InvalidOperationException("Khong tim thay Out/helper de patch logging");
        }

        PatchOutMethod(module, outType, helperTypeDef, "println", HelperLogInfoMethodName);
        PatchOutMethod(module, outType, helperTypeDef, "Log", HelperLogInfoMethodName);
        PatchOutMethod(module, outType, helperTypeDef, "LogWarning", HelperLogWarningMethodName);
        PatchOutMethod(module, outType, helperTypeDef, "LogError", HelperLogErrorMethodName);
    }

    private static void PatchOutMethod(ModuleDefinition module, TypeDefinition outType, TypeDefinition helperTypeDef, string outMethodName, string helperMethodName)
    {
        MethodDefinition outMethod = outType.Methods.FirstOrDefault(m =>
            m.Name == outMethodName && m.Parameters.Count == 1 && m.Parameters[0].ParameterType.FullName == module.TypeSystem.String.FullName);
        MethodDefinition helperMethodDef = helperTypeDef.Methods.FirstOrDefault(m =>
            m.Name == helperMethodName && m.Parameters.Count == 1 && m.Parameters[0].ParameterType.FullName == helperTypeDef.Module.TypeSystem.String.FullName);
        if (outMethod == null || helperMethodDef == null || !outMethod.HasBody)
        {
            throw new InvalidOperationException("Khong tim thay method de patch Out." + outMethodName);
        }

        MethodReference helperMethod = module.ImportReference(helperMethodDef);
        MethodBody body = outMethod.Body;
        body.Instructions.Clear();
        body.ExceptionHandlers.Clear();
        body.Variables.Clear();
        body.InitLocals = false;

        ILProcessor il = body.GetILProcessor();
        il.Append(il.Create(OpCodes.Ldarg_0));
        il.Append(il.Create(OpCodes.Call, helperMethod));
        il.Append(il.Create(OpCodes.Ret));
    }

    private static BaseAssemblyResolver CreateResolver(string baseDir)
    {
        DefaultAssemblyResolver resolver = new DefaultAssemblyResolver();
        if (!string.IsNullOrEmpty(baseDir) && Directory.Exists(baseDir))
        {
            resolver.AddSearchDirectory(baseDir);
        }
        return resolver;
    }

    private static void EnsureHelperReference(ModuleDefinition module, AssemblyNameDefinition helperAssemblyName)
    {
        bool exists = module.AssemblyReferences.Any(r =>
                string.Equals(r.Name, helperAssemblyName.Name, StringComparison.OrdinalIgnoreCase));
        if (!exists)
        {
            module.AssemblyReferences.Add(new AssemblyNameReference(helperAssemblyName.Name, helperAssemblyName.Version)
            {
                Culture = helperAssemblyName.Culture,
                PublicKeyToken = helperAssemblyName.PublicKeyToken
            });
        }
    }

    private static string FindClientRootFromAssembly(string assemblyPath)
    {
        DirectoryInfo managedDir = new DirectoryInfo(Path.GetDirectoryName(assemblyPath));
        if (managedDir == null || managedDir.Parent == null || managedDir.Parent.Parent == null)
        {
            return Path.GetDirectoryName(assemblyPath);
        }
        return managedDir.Parent.Parent.FullName;
    }

    private static string ComputeMeasurement(string clientRoot)
    {
        string root = Path.GetFullPath(clientRoot);
        string exePath = FindExePath(root);
        string dataDir = FindDataDirectory(root, exePath);
        List<string> files = new List<string>();
        if (!string.IsNullOrEmpty(exePath) && File.Exists(exePath))
        {
            files.Add(exePath);
        }
        if (!string.IsNullOrEmpty(dataDir) && Directory.Exists(dataDir))
        {
            files.AddRange(Directory.GetFiles(dataDir, "*", SearchOption.AllDirectories));
        }
        files = files
                .Where(File.Exists)
                .Where(path => ShouldIncludeFileForMeasurement(path))
                .Distinct(StringComparer.OrdinalIgnoreCase)
                .OrderBy(path => GetRelativePath(root, path), StringComparer.OrdinalIgnoreCase)
                .ToList();

        System.Text.StringBuilder sb = new System.Text.StringBuilder(files.Count * 120);
        for (int i = 0; i < files.Count; i++)
        {
            string path = files[i];
            sb.Append(GetRelativePath(root, path).ToLowerInvariant());
            sb.Append('|');
            sb.Append(Sha256File(path));
            sb.Append(';');
        }
        return Sha256Text(sb.ToString());
    }

    private static string FindExePath(string rootDir)
    {
        string[] exeFiles = Directory.GetFiles(rootDir, "*.exe", SearchOption.TopDirectoryOnly);
        if (exeFiles.Length == 1)
        {
            return exeFiles[0];
        }
        for (int i = 0; i < exeFiles.Length; i++)
        {
            string dataDir = Path.Combine(rootDir, Path.GetFileNameWithoutExtension(exeFiles[i]) + "_Data");
            if (Directory.Exists(dataDir))
            {
                return exeFiles[i];
            }
        }
        return exeFiles.OrderBy(path => path, StringComparer.OrdinalIgnoreCase).FirstOrDefault();
    }

    private static string FindDataDirectory(string rootDir, string exePath)
    {
        if (!string.IsNullOrEmpty(exePath))
        {
            string candidate = Path.Combine(rootDir, Path.GetFileNameWithoutExtension(exePath) + "_Data");
            if (Directory.Exists(candidate))
            {
                return candidate;
            }
        }
        string[] dataDirs = Directory.GetDirectories(rootDir, "*_Data", SearchOption.TopDirectoryOnly);
        return dataDirs.OrderBy(path => path, StringComparer.OrdinalIgnoreCase).FirstOrDefault();
    }

    private static string GetRelativePath(string rootDir, string path)
    {
        string normalizedRoot = rootDir.TrimEnd(Path.DirectorySeparatorChar, Path.AltDirectorySeparatorChar);
        string fullPath = Path.GetFullPath(path);
        if (fullPath.StartsWith(normalizedRoot, StringComparison.OrdinalIgnoreCase))
        {
            return fullPath.Substring(normalizedRoot.Length).TrimStart(Path.DirectorySeparatorChar, Path.AltDirectorySeparatorChar);
        }
        return Path.GetFileName(fullPath);
    }

    private static bool ShouldIncludeFileForMeasurement(string path)
    {
        string fileName = Path.GetFileName(path);
        if (string.Equals(fileName, "output_log.txt", StringComparison.OrdinalIgnoreCase))
        {
            return false;
        }
        return true;
    }

    private static string Sha256File(string path)
    {
        using (FileStream stream = File.OpenRead(path))
        using (System.Security.Cryptography.SHA256Managed sha = new System.Security.Cryptography.SHA256Managed())
        {
            return ToHex(sha.ComputeHash(stream));
        }
    }

    private static string Sha256Text(string text)
    {
        using (System.Security.Cryptography.SHA256Managed sha = new System.Security.Cryptography.SHA256Managed())
        {
            return ToHex(sha.ComputeHash(System.Text.Encoding.UTF8.GetBytes(text)));
        }
    }

    private static string ToHex(byte[] data)
    {
        System.Text.StringBuilder sb = new System.Text.StringBuilder(data.Length * 2);
        for (int i = 0; i < data.Length; i++)
        {
            sb.Append(data[i].ToString("X2"));
        }
        return sb.ToString();
    }

    private static void UpsertMeasurement(string measurementsPath, string clientId, string measurement, string helperPath)
    {
        string outputHash = string.Equals(clientId, "kpahpc", StringComparison.OrdinalIgnoreCase)
                ? ForcedPcOutputHash
                : measurement;
        List<string> lines = File.Exists(measurementsPath)
                ? File.ReadAllLines(measurementsPath).ToList()
                : new List<string>();

        List<List<string>> sections = SplitSections(lines);
        bool updated = false;
        for (int i = 0; i < sections.Count; i++)
        {
            string existingId = GetValue(sections[i], "CLIENT_ID");
            if (string.Equals(existingId, clientId, StringComparison.OrdinalIgnoreCase))
            {
                sections[i] = new List<string>
                {
                    "CLIENT_ID=" + clientId,
                    "MEASUREMENT=" + measurement,
                    "OUTPUT_SHA256=" + outputHash,
                    "OUTPUT_JAR=" + helperPath
                };
                updated = true;
                break;
            }
        }
        if (!updated)
        {
            sections.Add(new List<string>
            {
                "CLIENT_ID=" + clientId,
                "MEASUREMENT=" + measurement,
                "OUTPUT_SHA256=" + outputHash,
                "OUTPUT_JAR=" + helperPath
            });
        }

        List<string> output = new List<string>();
        for (int i = 0; i < sections.Count; i++)
        {
            if (sections[i].Count == 0)
            {
                continue;
            }
            output.AddRange(sections[i]);
            if (i < sections.Count - 1)
            {
                output.Add("");
            }
        }
        File.WriteAllLines(measurementsPath, output.ToArray());
    }

    private static void WriteMeasurementOverride(string clientRoot, string measurement)
    {
        string overridePath = Path.Combine(clientRoot, MeasurementOverrideFileName);
        File.WriteAllText(overridePath, measurement + Environment.NewLine);
    }

    private static List<List<string>> SplitSections(List<string> lines)
    {
        List<List<string>> sections = new List<List<string>>();
        List<string> current = new List<string>();
        for (int i = 0; i < lines.Count; i++)
        {
            string line = lines[i];
            if (string.IsNullOrWhiteSpace(line))
            {
                if (current.Count > 0)
                {
                    sections.Add(current);
                    current = new List<string>();
                }
                continue;
            }
            current.Add(line);
        }
        if (current.Count > 0)
        {
            sections.Add(current);
        }
        return sections;
    }

    private static string GetValue(List<string> section, string key)
    {
        for (int i = 0; i < section.Count; i++)
        {
            string line = section[i];
            int separator = line.IndexOf('=');
            if (separator <= 0)
            {
                continue;
            }
            string currentKey = line.Substring(0, separator).Trim();
            if (string.Equals(currentKey, key, StringComparison.OrdinalIgnoreCase))
            {
                return line.Substring(separator + 1).Trim();
            }
        }
        return "";
    }
}
