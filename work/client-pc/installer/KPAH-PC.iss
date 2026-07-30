#ifndef ClientDir
  #error ClientDir must be supplied by Build-PcInstaller.ps1
#endif
#ifndef OutputDir
  #error OutputDir must be supplied by Build-PcInstaller.ps1
#endif
#ifndef AppVersion
  #define AppVersion "1.0.0"
#endif
#ifndef OutputName
  #define OutputName "KPAH-PC-Setup"
#endif

[Setup]
AppId={{D0603A62-861A-4C50-A261-31C1486E17B4}
AppName=KPAH PC
AppVersion={#AppVersion}
AppPublisher=KPAH
DefaultDirName={localappdata}\Programs\KPAH PC
DefaultGroupName=KPAH PC
DisableProgramGroupPage=yes
OutputDir={#OutputDir}
OutputBaseFilename={#OutputName}
Compression=lzma2/ultra64
SolidCompression=yes
PrivilegesRequired=lowest
ArchitecturesInstallIn64BitMode=x64compatible
WizardStyle=modern
SetupLogging=yes
UninstallDisplayIcon={app}\KPAH_276.exe
CloseApplications=yes
RestartApplications=no
VersionInfoVersion={#AppVersion}
VersionInfoProductName=KPAH PC
VersionInfoDescription=KPAH PC game installer

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "Tạo biểu tượng ngoài Desktop"; GroupDescription: "Biểu tượng:"; Flags: checkedonce

[Files]
Source: "{#ClientDir}\*"; DestDir: "{app}"; Excludes: "KPAH_276_Data\output_log.txt,KPAH_276_Data\Managed\Assembly-CSharp.backup.dll"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{autoprograms}\KPAH PC"; Filename: "{app}\KPAH_276.exe"; WorkingDir: "{app}"
Name: "{autodesktop}\KPAH PC"; Filename: "{app}\KPAH_276.exe"; WorkingDir: "{app}"; Tasks: desktopicon

[Run]
Filename: "{app}\KPAH_276.exe"; Description: "Mở KPAH PC"; WorkingDir: "{app}"; Flags: nowait postinstall skipifsilent
