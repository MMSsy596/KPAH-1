using System;
using System.IO;
using System.Linq;
using Mono.Cecil;
using Mono.Cecil.Cil;

public static class PatchPcServerBinding
{
    private const string LegacyHost = "163.61.183.129";
    private static string forcedServerName = "KPAH";
    private static string forcedHost = "127.0.0.1";
    private static int forcedPort = 19129;
    private static sbyte forcedIndex = 0;
    private static string forcedServerListUrl = "http://127.0.0.1:18080/NQSH2.txt";

    public static int Main(string[] args)
    {
        if (args.Length < 1)
        {
            Console.Error.WriteLine("Usage: PatchPcServerBinding <assembly-csharp.dll> [host] [port] [server-name] [server-list-url]");
            return 1;
        }

        string assemblyPath = Path.GetFullPath(args[0]);
        if (!File.Exists(assemblyPath))
        {
            Console.Error.WriteLine("Khong tim thay Assembly-CSharp.dll: " + assemblyPath);
            return 2;
        }

        if (args.Length >= 2 && !string.IsNullOrWhiteSpace(args[1]))
        {
            forcedHost = args[1].Trim();
        }
        int parsedPort = forcedPort;
        if (args.Length >= 3 &&
            (!int.TryParse(args[2], out parsedPort) || parsedPort < 1 || parsedPort > 65535))
        {
            Console.Error.WriteLine("Port khong hop le: " + args[2]);
            return 3;
        }
        if (args.Length >= 3)
        {
            forcedPort = parsedPort;
        }
        if (args.Length >= 4 && !string.IsNullOrWhiteSpace(args[3]))
        {
            forcedServerName = args[3].Trim();
        }
        if (args.Length >= 5 && !string.IsNullOrWhiteSpace(args[4]))
        {
            forcedServerListUrl = args[4].Trim();
        }

        PatchAssembly(assemblyPath);
        Console.WriteLine("SERVER_NAME=" + forcedServerName);
        Console.WriteLine("SERVER_HOST=" + forcedHost);
        Console.WriteLine("SERVER_PORT=" + forcedPort);
        Console.WriteLine("SERVER_LIST_URL=" + forcedServerListUrl);
        return 0;
    }

    private static void PatchAssembly(string assemblyPath)
    {
        string assemblyDir = Path.GetDirectoryName(assemblyPath);
        ReaderParameters readerParameters = new ReaderParameters
        {
            ReadWrite = false,
            ReadSymbols = false,
            AssemblyResolver = CreateResolver(assemblyDir)
        };
        ModuleDefinition module = ModuleDefinition.ReadModule(assemblyPath, readerParameters);

        PatchLegacyNetworkLiterals(module);
        PatchServerListScr(module);
        PatchSessionConnect(module);

        string tempPath = assemblyPath + ".binding";
        module.Write(tempPath, new WriterParameters { WriteSymbols = false });
        module.Dispose();

        File.Copy(tempPath, assemblyPath, true);
        File.Delete(tempPath);
    }

    private static void PatchLegacyNetworkLiterals(ModuleDefinition module)
    {
        foreach (TypeDefinition type in GetAllTypes(module.Types))
        {
            foreach (MethodDefinition method in type.Methods)
            {
                if (!method.HasBody)
                {
                    continue;
                }
                foreach (Instruction instruction in method.Body.Instructions)
                {
                    string value = instruction.Operand as string;
                    if (value == null || value.IndexOf(LegacyHost, StringComparison.OrdinalIgnoreCase) < 0)
                    {
                        continue;
                    }
                    instruction.Operand = value.Replace(LegacyHost, forcedHost);
                }
            }
        }
    }

    private static System.Collections.Generic.IEnumerable<TypeDefinition> GetAllTypes(
        System.Collections.Generic.IEnumerable<TypeDefinition> roots)
    {
        foreach (TypeDefinition type in roots)
        {
            yield return type;
            foreach (TypeDefinition nested in GetAllTypes(type.NestedTypes))
            {
                yield return nested;
            }
        }
    }

    private static DefaultAssemblyResolver CreateResolver(string baseDirectory)
    {
        DefaultAssemblyResolver resolver = new DefaultAssemblyResolver();
        if (!string.IsNullOrEmpty(baseDirectory) && Directory.Exists(baseDirectory))
        {
            resolver.AddSearchDirectory(baseDirectory);
        }
        return resolver;
    }

    private static void PatchServerListScr(ModuleDefinition module)
    {
        TypeDefinition serverListScr = module.Types.FirstOrDefault(t => t.Name == "ServerListScr");
        TypeDefinition loginScr = module.Types.FirstOrDefault(t => t.Name == "LoginScr");
        if (serverListScr == null || loginScr == null)
        {
            throw new InvalidOperationException("Khong tim thay ServerListScr/LoginScr");
        }

        FieldDefinition nameServerField = FindStaticField(serverListScr, "nameServer");
        FieldDefinition addressField = FindStaticField(serverListScr, "address");
        FieldDefinition portField = FindStaticField(serverListScr, "port");
        FieldDefinition indexServerField = FindStaticField(serverListScr, "index_server");
        FieldDefinition linkGetHostField = FindStaticField(serverListScr, "linkGetHost");
        FieldDefinition nameSvAutoField = FindStaticField(serverListScr, "nameSvAuto");
        FieldDefinition addressAutoField = FindStaticField(serverListScr, "addressAuTo");
        FieldDefinition portAutoField = FindStaticField(serverListScr, "portAuTo");
        FieldDefinition loginNameServerField = FindStaticField(loginScr, "nameServer");

        RewriteGrindingServerMethod(
            serverListScr.Methods.FirstOrDefault(m => m.Name == "loadSv" && m.Parameters.Count == 1 && m.IsStatic),
            nameServerField,
            addressField,
            portField,
            indexServerField,
            linkGetHostField
        );

        RewriteGrindingServerMethod(
            serverListScr.Methods.FirstOrDefault(m => m.Name == "loadIP" && m.Parameters.Count == 0 && m.IsStatic),
            nameServerField,
            addressField,
            portField,
            indexServerField,
            linkGetHostField
        );

        RewriteGrindingAutoServerMethod(
            serverListScr.Methods.FirstOrDefault(m => m.Name == "loadIPAuTo" && m.Parameters.Count == 0 && m.IsStatic),
            nameSvAutoField,
            addressAutoField,
            portAutoField,
            loginNameServerField
        );
    }

    private static void PatchSessionConnect(ModuleDefinition module)
    {
        TypeDefinition sessionType = module.Types.FirstOrDefault(t => t.Name == "Session_ME");
        if (sessionType == null)
        {
            throw new InvalidOperationException("Khong tim thay Session_ME");
        }

        PatchConnectMethod(sessionType.Methods.FirstOrDefault(m => m.Name == "connect" && m.Parameters.Count == 2));
        PatchConnectMethod(sessionType.Methods.FirstOrDefault(m => m.Name == "doConnect" && m.Parameters.Count == 2));
    }

    private static FieldDefinition FindStaticField(TypeDefinition type, string name)
    {
        FieldDefinition field = type.Fields.FirstOrDefault(f => f.Name == name && f.IsStatic);
        if (field == null)
        {
            throw new InvalidOperationException("Khong tim thay field " + type.Name + "." + name);
        }
        return field;
    }

    private static void RewriteGrindingServerMethod(
        MethodDefinition method,
        FieldDefinition nameServerField,
        FieldDefinition addressField,
        FieldDefinition portField,
        FieldDefinition indexServerField,
        FieldDefinition linkGetHostField)
    {
        if (method == null)
        {
            throw new InvalidOperationException("Khong tim thay method grind server binding");
        }

        MethodBody body = method.Body;
        body.Instructions.Clear();
        body.ExceptionHandlers.Clear();
        body.Variables.Clear();
        body.InitLocals = false;

        ILProcessor il = body.GetILProcessor();

        AppendSingleStringArrayAssignment(il, nameServerField, forcedServerName);
        AppendSingleStringArrayAssignment(il, addressField, forcedHost);
        AppendSingleShortArrayAssignment(il, portField, forcedPort);
        AppendSingleSByteArrayAssignment(il, indexServerField, forcedIndex);
        il.Append(il.Create(OpCodes.Ldstr, forcedServerListUrl));
        il.Append(il.Create(OpCodes.Stsfld, linkGetHostField));
        il.Append(il.Create(OpCodes.Ret));
    }

    private static void RewriteGrindingAutoServerMethod(
        MethodDefinition method,
        FieldDefinition nameSvAutoField,
        FieldDefinition addressAutoField,
        FieldDefinition portAutoField,
        FieldDefinition loginNameServerField)
    {
        if (method == null)
        {
            throw new InvalidOperationException("Khong tim thay method loadIPAuTo");
        }

        MethodBody body = method.Body;
        body.Instructions.Clear();
        body.ExceptionHandlers.Clear();
        body.Variables.Clear();
        body.InitLocals = false;

        ILProcessor il = body.GetILProcessor();
        il.Append(il.Create(OpCodes.Ldstr, forcedServerName));
        il.Append(il.Create(OpCodes.Stsfld, nameSvAutoField));
        il.Append(il.Create(OpCodes.Ldstr, forcedHost));
        il.Append(il.Create(OpCodes.Stsfld, addressAutoField));
        il.Append(il.Create(OpCodes.Ldc_I4, forcedPort));
        il.Append(il.Create(OpCodes.Conv_I2));
        il.Append(il.Create(OpCodes.Stsfld, portAutoField));
        il.Append(il.Create(OpCodes.Ldstr, forcedServerName));
        il.Append(il.Create(OpCodes.Stsfld, loginNameServerField));
        il.Append(il.Create(OpCodes.Ret));
    }

    private static void PatchConnectMethod(MethodDefinition method)
    {
        if (method == null || !method.HasBody || method.Parameters.Count != 2)
        {
            throw new InvalidOperationException("Khong tim thay method Session_ME connect can patch");
        }

        ILProcessor il = method.Body.GetILProcessor();
        Instruction first = method.Body.Instructions.First();
        il.InsertBefore(first, il.Create(OpCodes.Ldstr, forcedHost));
        il.InsertBefore(first, il.Create(OpCodes.Starg, method.Parameters[0]));
        il.InsertBefore(first, il.Create(OpCodes.Ldc_I4, forcedPort));
        il.InsertBefore(first, il.Create(OpCodes.Starg, method.Parameters[1]));
    }

    private static void AppendSingleStringArrayAssignment(ILProcessor il, FieldDefinition field, string value)
    {
        il.Append(il.Create(OpCodes.Ldc_I4_1));
        il.Append(il.Create(OpCodes.Newarr, field.Module.TypeSystem.String));
        il.Append(il.Create(OpCodes.Dup));
        il.Append(il.Create(OpCodes.Ldc_I4_0));
        il.Append(il.Create(OpCodes.Ldstr, value));
        il.Append(il.Create(OpCodes.Stelem_Ref));
        il.Append(il.Create(OpCodes.Stsfld, field));
    }

    private static void AppendSingleShortArrayAssignment(ILProcessor il, FieldDefinition field, int value)
    {
        ArrayType arrayType = (ArrayType)field.FieldType;
        il.Append(il.Create(OpCodes.Ldc_I4_1));
        il.Append(il.Create(OpCodes.Newarr, arrayType.ElementType));
        il.Append(il.Create(OpCodes.Dup));
        il.Append(il.Create(OpCodes.Ldc_I4_0));
        il.Append(il.Create(OpCodes.Ldc_I4, (int)value));
        il.Append(il.Create(OpCodes.Conv_I2));
        il.Append(il.Create(OpCodes.Stelem_I2));
        il.Append(il.Create(OpCodes.Stsfld, field));
    }

    private static void AppendSingleSByteArrayAssignment(ILProcessor il, FieldDefinition field, sbyte value)
    {
        ArrayType arrayType = (ArrayType)field.FieldType;
        il.Append(il.Create(OpCodes.Ldc_I4_1));
        il.Append(il.Create(OpCodes.Newarr, arrayType.ElementType));
        il.Append(il.Create(OpCodes.Dup));
        il.Append(il.Create(OpCodes.Ldc_I4_0));
        il.Append(il.Create(OpCodes.Ldc_I4, (int)value));
        il.Append(il.Create(OpCodes.Conv_I1));
        il.Append(il.Create(OpCodes.Stelem_I1));
        il.Append(il.Create(OpCodes.Stsfld, field));
    }
}
