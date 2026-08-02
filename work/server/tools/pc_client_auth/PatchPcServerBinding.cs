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
    private static string gameplayHelperPath;

    public static int Main(string[] args)
    {
        if (args.Length < 1)
        {
            Console.Error.WriteLine("Usage: PatchPcServerBinding <assembly-csharp.dll> [host] [port] [server-name] [server-list-url] [gameplay-helper.dll]");
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
        if (args.Length >= 6 && !string.IsNullOrWhiteSpace(args[5]))
        {
            gameplayHelperPath = Path.GetFullPath(args[5]);
        }
        if (string.IsNullOrEmpty(gameplayHelperPath) || !File.Exists(gameplayHelperPath))
        {
            Console.Error.WriteLine("Khong tim thay gameplay helper: " + gameplayHelperPath);
            return 4;
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
        PatchNumpadKeys(module);
        PatchPersistentLogin(module);
        PatchGameplayEnhancements(module);
        PatchAutoTrainMenuContinuity(module);

        string tempPath = assemblyPath + ".binding";
        module.Write(tempPath, new WriterParameters { WriteSymbols = false });
        module.Dispose();

        File.Copy(tempPath, assemblyPath, true);
        File.Delete(tempPath);
        VerifyNumpadKeys(assemblyPath);
        VerifyPersistentLogin(assemblyPath);
        VerifyGameplayEnhancements(assemblyPath);
        VerifyAutoTrainMenuContinuity(assemblyPath);
    }

    private static void PatchAutoTrainMenuContinuity(ModuleDefinition module)
    {
        TypeDefinition gameScrType = module.Types.FirstOrDefault(t => t.Name == "GameScr");
        MethodDefinition update = gameScrType == null ? null : gameScrType.Methods.FirstOrDefault(m =>
            m.Name == "update" && m.Parameters.Count == 0 && m.HasBody);
        if (update == null)
        {
            throw new InvalidOperationException("Khong tim thay GameScr.update");
        }

        Instruction currentScreenCheck = update.Body.Instructions.FirstOrDefault(i =>
        {
            FieldReference field = i.Operand as FieldReference;
            return i.OpCode == OpCodes.Ldsfld && field != null &&
                field.DeclaringType.Name == "Canvas" && field.Name == "currentScreen";
        });
        Instruction typeCheck = currentScreenCheck == null ? null : currentScreenCheck.Next;
        Instruction branch = typeCheck == null ? null : typeCheck.Next;
        TypeReference checkedType = typeCheck == null ? null : typeCheck.Operand as TypeReference;
        if (currentScreenCheck == null || typeCheck.OpCode != OpCodes.Isinst ||
            checkedType == null || checkedType.Name != "GameScr" || branch.OpCode != OpCodes.Brfalse)
        {
            throw new InvalidOperationException("Khong tim thay dieu kien dung auto khi mo menu");
        }

        // GameScr.update vẫn được các màn hình túi đồ/menu gọi để cập nhật nền.
        // Bỏ riêng điều kiện currentScreen để vòng auto tiếp tục, không thay đổi xử lý phím của menu.
        currentScreenCheck.OpCode = OpCodes.Nop;
        currentScreenCheck.Operand = null;
        typeCheck.OpCode = OpCodes.Nop;
        typeCheck.Operand = null;
        branch.OpCode = OpCodes.Nop;
        branch.Operand = null;
        Console.WriteLine("AUTO_TRAIN_MENU=CONTINUE");
    }

    private static void VerifyAutoTrainMenuContinuity(string assemblyPath)
    {
        using (ModuleDefinition module = ModuleDefinition.ReadModule(assemblyPath))
        {
            TypeDefinition gameScrType = module.Types.FirstOrDefault(t => t.Name == "GameScr");
            MethodDefinition update = gameScrType == null ? null : gameScrType.Methods.FirstOrDefault(m =>
                m.Name == "update" && m.Parameters.Count == 0 && m.HasBody);
            bool stillBlocksByCurrentScreen = update != null && update.Body.Instructions.Any(i =>
            {
                FieldReference field = i.Operand as FieldReference;
                return i.OpCode == OpCodes.Ldsfld && field != null &&
                    field.DeclaringType.Name == "Canvas" && field.Name == "currentScreen" &&
                    i.Next != null && i.Next.OpCode == OpCodes.Isinst &&
                    i.Next.Next != null && i.Next.Next.OpCode == OpCodes.Brfalse;
            });
            if (update == null || stillBlocksByCurrentScreen)
            {
                throw new InvalidOperationException("Khong the xac minh auto train tiep tuc khi mo menu");
            }
        }
        Console.WriteLine("AUTO_TRAIN_MENU_VERIFY=PASS");
    }

    private static void PatchGameplayEnhancements(ModuleDefinition module)
    {
        AssemblyDefinition helperAssembly = AssemblyDefinition.ReadAssembly(gameplayHelperPath);
        try
        {
            TypeDefinition inputType = helperAssembly.MainModule.Types.FirstOrDefault(t =>
                t.FullName == "KpahPcGameplay.InputEnhancements");
            TypeDefinition potionType = helperAssembly.MainModule.Types.FirstOrDefault(t =>
                t.FullName == "KpahPcGameplay.PotionUseAll");
            MethodDefinition handleEnter = inputType == null ? null : inputType.Methods.FirstOrDefault(m =>
                m.Name == "HandleEnter" && m.Parameters.Count == 1);
            MethodDefinition addMenuCommand = potionType == null ? null : potionType.Methods.FirstOrDefault(m =>
                m.Name == "AddMenuCommand" && m.Parameters.Count == 2);
            MethodDefinition updateUseAll = potionType == null ? null : potionType.Methods.FirstOrDefault(m =>
                m.Name == "Update" && m.Parameters.Count == 0);
            if (handleEnter == null || addMenuCommand == null || updateUseAll == null)
            {
                throw new InvalidOperationException("Gameplay helper thieu method can thiet");
            }

            PatchCanvasEnter(module, module.ImportReference(handleEnter));
            PatchCanvasUpdate(module, module.ImportReference(updateUseAll));
            PatchPotionMenu(module, module.ImportReference(addMenuCommand));
        }
        finally
        {
            helperAssembly.Dispose();
        }

        Console.WriteLine("GAMEPLAY_ENHANCEMENTS=ENTER,USE_ALL,PLAYER_SUPPORT");
    }

    private static void PatchCanvasEnter(ModuleDefinition module, MethodReference handleEnter)
    {
        TypeDefinition canvasType = module.Types.FirstOrDefault(t => t.Name == "Canvas");
        MethodDefinition keyPress = canvasType == null ? null : canvasType.Methods.FirstOrDefault(m =>
            m.Name == "keyPress" && m.Parameters.Count == 1 && m.HasBody);
        if (keyPress == null)
        {
            throw new InvalidOperationException("Khong tim thay Canvas.keyPress");
        }

        ILProcessor il = keyPress.Body.GetILProcessor();
        Instruction originalFirst = keyPress.Body.Instructions.First();
        Instruction continueOriginal = il.Create(OpCodes.Nop);
        il.InsertBefore(originalFirst, il.Create(OpCodes.Ldarg_1));
        il.InsertBefore(originalFirst, il.Create(OpCodes.Call, handleEnter));
        il.InsertBefore(originalFirst, il.Create(OpCodes.Brfalse, continueOriginal));
        il.InsertBefore(originalFirst, il.Create(OpCodes.Ret));
        il.InsertBefore(originalFirst, continueOriginal);
    }

    private static void PatchCanvasUpdate(ModuleDefinition module, MethodReference updateUseAll)
    {
        TypeDefinition canvasType = module.Types.FirstOrDefault(t => t.Name == "Canvas");
        MethodDefinition update = canvasType == null ? null : canvasType.Methods.FirstOrDefault(m =>
            m.Name == "update" && m.Parameters.Count == 0 && m.HasBody);
        if (update == null)
        {
            throw new InvalidOperationException("Khong tim thay Canvas.update");
        }

        ILProcessor il = update.Body.GetILProcessor();
        il.InsertBefore(update.Body.Instructions.First(), il.Create(OpCodes.Call, updateUseAll));
    }

    private static void PatchPotionMenu(ModuleDefinition module, MethodReference addMenuCommand)
    {
        TypeDefinition windowType = module.Types.FirstOrDefault(t => t.Name == "WindowInfoScr");
        MethodDefinition showMenu = windowType == null ? null : windowType.Methods.FirstOrDefault(m =>
            m.Name == "showMenuForPotion" && m.Parameters.Count == 1 && m.HasBody);
        if (showMenu == null)
        {
            throw new InvalidOperationException("Khong tim thay WindowInfoScr.showMenuForPotion");
        }

        Instruction useCaption = showMenu.Body.Instructions.FirstOrDefault(i =>
            i.OpCode == OpCodes.Ldstr && string.Equals(i.Operand as string, "Sử dụng", StringComparison.Ordinal));
        Instruction addUseCommand = useCaption == null ? null : showMenu.Body.Instructions
            .SkipWhile(i => i != useCaption)
            .FirstOrDefault(i =>
            {
                MethodReference method = i.Operand as MethodReference;
                return (i.OpCode == OpCodes.Call || i.OpCode == OpCodes.Callvirt) &&
                    method != null && method.Name == "addElement" && method.DeclaringType.Name == "mVector";
            });
        if (addUseCommand == null || addUseCommand.Previous == null || addUseCommand.Previous.Previous == null)
        {
            throw new InvalidOperationException("Khong tim thay vi tri chen menu Dung tat ca");
        }

        VariableDefinition menuVariable = GetLoadedVariable(showMenu, addUseCommand.Previous.Previous);
        if (menuVariable == null)
        {
            throw new InvalidOperationException("Khong xac dinh duoc bien menu vat pham");
        }

        ILProcessor il = showMenu.Body.GetILProcessor();
        Instruction cursor = addUseCommand;
        cursor = InsertAfter(il, cursor, il.Create(OpCodes.Ldloc, menuVariable));
        cursor = InsertAfter(il, cursor, il.Create(OpCodes.Ldarg_1));
        InsertAfter(il, cursor, il.Create(OpCodes.Call, addMenuCommand));
    }

    private static Instruction InsertAfter(ILProcessor il, Instruction target, Instruction instruction)
    {
        il.InsertAfter(target, instruction);
        return instruction;
    }

    private static VariableDefinition GetLoadedVariable(MethodDefinition method, Instruction instruction)
    {
        if (instruction.OpCode == OpCodes.Ldloc_0)
        {
            return method.Body.Variables[0];
        }
        if (instruction.OpCode == OpCodes.Ldloc_1)
        {
            return method.Body.Variables[1];
        }
        if (instruction.OpCode == OpCodes.Ldloc_2)
        {
            return method.Body.Variables[2];
        }
        if (instruction.OpCode == OpCodes.Ldloc_3)
        {
            return method.Body.Variables[3];
        }
        if (instruction.OpCode == OpCodes.Ldloc || instruction.OpCode == OpCodes.Ldloc_S)
        {
            return instruction.Operand as VariableDefinition;
        }
        return null;
    }

    private static void VerifyGameplayEnhancements(string assemblyPath)
    {
        using (ModuleDefinition module = ModuleDefinition.ReadModule(assemblyPath))
        {
            int helperCalls = module.Types.SelectMany(t => t.Methods)
                .Where(m => m.HasBody)
                .SelectMany(m => m.Body.Instructions)
                .Count(i =>
                {
                    MethodReference method = i.Operand as MethodReference;
                    return method != null && method.DeclaringType.Namespace == "KpahPcGameplay";
                });
            bool hasHelperReference = module.AssemblyReferences.Any(r => r.Name == "KpahPcGameplay");
            if (!hasHelperReference || helperCalls < 3)
            {
                throw new InvalidOperationException("Khong the xac minh patch Enter/Dung tat ca");
            }
        }
        Console.WriteLine("GAMEPLAY_ENHANCEMENTS_VERIFY=PASS");
    }

    private static void PatchPersistentLogin(ModuleDefinition module)
    {
        TypeDefinition mainType = module.Types.FirstOrDefault(t => t.Name == "Main");
        TypeDefinition rmsType = module.Types.FirstOrDefault(t => t.Name == "RMS");
        MethodDefinition loadDataGame = mainType == null ? null : mainType.Methods.FirstOrDefault(m =>
            m.Name == "loadDataGame" && m.Parameters.Count == 0 && m.HasBody);
        MethodDefinition saveRms = rmsType == null ? null : rmsType.Methods.FirstOrDefault(m =>
            m.Name == "__saveRMS" && m.Parameters.Count == 2 && m.HasBody);
        if (loadDataGame == null || saveRms == null)
        {
            throw new InvalidOperationException("Khong tim thay Main.loadDataGame/RMS.__saveRMS");
        }

        Instruction deleteAll = loadDataGame.Body.Instructions.FirstOrDefault(i => IsPlayerPrefsCall(i, "DeleteAll"));
        if (deleteAll == null)
        {
            throw new InvalidOperationException("Khong tim thay PlayerPrefs.DeleteAll trong Main.loadDataGame");
        }

        // Không xóa PlayerPrefs khi khởi động để tài khoản đã chọn ghi nhớ còn tồn tại ở lần mở sau.
        deleteAll.OpCode = OpCodes.Nop;
        deleteAll.Operand = null;

        Instruction setString = saveRms.Body.Instructions.FirstOrDefault(i => IsPlayerPrefsCall(i, "SetString"));
        if (setString == null)
        {
            throw new InvalidOperationException("Khong tim thay PlayerPrefs.SetString trong RMS.__saveRMS");
        }

        MethodReference setStringMethod = setString.Operand as MethodReference;
        if (setStringMethod == null)
        {
            throw new InvalidOperationException("PlayerPrefs.SetString khong co method reference");
        }
        MethodReference saveMethod = new MethodReference("Save", module.TypeSystem.Void, setStringMethod.DeclaringType)
        {
            HasThis = false
        };
        saveRms.Body.GetILProcessor().InsertAfter(setString, Instruction.Create(OpCodes.Call, module.ImportReference(saveMethod)));
        Console.WriteLine("REMEMBER_LOGIN=PERSISTENT");
    }

    private static bool IsPlayerPrefsCall(Instruction instruction, string methodName)
    {
        MethodReference method = instruction.Operand as MethodReference;
        return (instruction.OpCode == OpCodes.Call || instruction.OpCode == OpCodes.Callvirt) &&
            method != null && method.Name == methodName && method.DeclaringType.FullName == "UnityEngine.PlayerPrefs";
    }

    private static void VerifyPersistentLogin(string assemblyPath)
    {
        using (ModuleDefinition module = ModuleDefinition.ReadModule(assemblyPath))
        {
            TypeDefinition mainType = module.Types.FirstOrDefault(t => t.Name == "Main");
            TypeDefinition rmsType = module.Types.FirstOrDefault(t => t.Name == "RMS");
            MethodDefinition loadDataGame = mainType == null ? null : mainType.Methods.FirstOrDefault(m =>
                m.Name == "loadDataGame" && m.Parameters.Count == 0 && m.HasBody);
            MethodDefinition saveRms = rmsType == null ? null : rmsType.Methods.FirstOrDefault(m =>
                m.Name == "__saveRMS" && m.Parameters.Count == 2 && m.HasBody);
            bool stillDeletesLogin = loadDataGame != null && loadDataGame.Body.Instructions.Any(i => IsPlayerPrefsCall(i, "DeleteAll"));
            bool flushesPlayerPrefs = saveRms != null && saveRms.Body.Instructions.Any(i => IsPlayerPrefsCall(i, "Save"));
            if (loadDataGame == null || saveRms == null || stillDeletesLogin || !flushesPlayerPrefs)
            {
                throw new InvalidOperationException("Khong the xac minh chuc nang ghi nho dang nhap");
            }
        }
        Console.WriteLine("REMEMBER_LOGIN_VERIFY=PASS");
    }

    private static void PatchNumpadKeys(ModuleDefinition module)
    {
        TypeDefinition keyMapType = module.Types.FirstOrDefault(t => t.Name == "MyKeyMap");
        if (keyMapType == null)
        {
            throw new InvalidOperationException("Khong tim thay MyKeyMap");
        }

        MethodDefinition mapMethod = keyMapType.Methods.FirstOrDefault(m =>
            m.Name == "map" && m.IsStatic && m.Parameters.Count == 1 &&
            m.ReturnType.MetadataType == MetadataType.Int32);
        if (mapMethod == null || !mapMethod.HasBody)
        {
            throw new InvalidOperationException("Khong tim thay MyKeyMap.map");
        }

        // Unity KeyCode.Keypad0..Keypad9 are 256..265. The original client only
        // maps Alpha0..Alpha9 (48..57), so translate NumPad keys to the same
        // ASCII values before the legacy lookup table runs.
        ILProcessor il = mapMethod.Body.GetILProcessor();
        Instruction originalFirst = mapMethod.Body.Instructions.First();
        Instruction[] prefix = new[]
        {
            il.Create(OpCodes.Ldarg_0),
            il.Create(OpCodes.Ldc_I4, 256),
            il.Create(OpCodes.Blt, originalFirst),
            il.Create(OpCodes.Ldarg_0),
            il.Create(OpCodes.Ldc_I4, 265),
            il.Create(OpCodes.Bgt, originalFirst),
            il.Create(OpCodes.Ldarg_0),
            il.Create(OpCodes.Ldc_I4, 208),
            il.Create(OpCodes.Sub),
            il.Create(OpCodes.Ret)
        };
        foreach (Instruction instruction in prefix)
        {
            il.InsertBefore(originalFirst, instruction);
        }

        Console.WriteLine("NUMPAD_KEYS=0-9");
    }

    private static void VerifyNumpadKeys(string assemblyPath)
    {
        using (ModuleDefinition module = ModuleDefinition.ReadModule(assemblyPath))
        {
            TypeDefinition keyMapType = module.Types.FirstOrDefault(t => t.Name == "MyKeyMap");
            MethodDefinition mapMethod = keyMapType == null ? null : keyMapType.Methods.FirstOrDefault(m =>
                m.Name == "map" && m.IsStatic && m.Parameters.Count == 1);
            if (mapMethod == null || !mapMethod.HasBody || mapMethod.Body.Instructions.Count < 10)
            {
                throw new InvalidOperationException("Khong the xac minh ban va NumPad");
            }

            Instruction[] instructions = mapMethod.Body.Instructions.Take(10).ToArray();
            bool valid = instructions[0].OpCode == OpCodes.Ldarg_0 &&
                GetInt32Constant(instructions[1]) == 256 &&
                instructions[2].OpCode == OpCodes.Blt &&
                instructions[3].OpCode == OpCodes.Ldarg_0 &&
                GetInt32Constant(instructions[4]) == 265 &&
                instructions[5].OpCode == OpCodes.Bgt &&
                instructions[6].OpCode == OpCodes.Ldarg_0 &&
                GetInt32Constant(instructions[7]) == 208 &&
                instructions[8].OpCode == OpCodes.Sub &&
                instructions[9].OpCode == OpCodes.Ret;
            if (!valid)
            {
                throw new InvalidOperationException("Ban va NumPad khong dung mau IL mong doi");
            }
        }
        Console.WriteLine("NUMPAD_VERIFY=PASS");
    }

    private static int GetInt32Constant(Instruction instruction)
    {
        if (instruction.OpCode == OpCodes.Ldc_I4)
        {
            return (int)instruction.Operand;
        }
        if (instruction.OpCode == OpCodes.Ldc_I4_S)
        {
            return (sbyte)instruction.Operand;
        }
        throw new InvalidOperationException("Instruction khong phai hang so Int32: " + instruction.OpCode);
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
