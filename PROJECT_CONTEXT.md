# KPAH project context

Last updated: 2026-07-29

This document preserves the project decisions and technical findings from the original Codex working session. It is intended to let a new Codex session or another developer continue on a different PC without access to the original chat history.

## Project objective

Reconstruct a self-contained KPAH game stack that runs locally and can be developed as an independent project.

The target stack includes:

- A locally built game server.
- A local login server.
- Local MariaDB databases.
- Server map and gameplay data.
- A PC or J2ME client patched to localhost.
- Local-only administration tools.
- A reproducible build and migration process.

The first milestone is not feature completeness. It is a stable core loop:

```text
start database
  -> start login server
  -> start game server
  -> connect client
  -> authenticate
  -> select/create character
  -> enter map
  -> move and fight
  -> save and reload state
```

## Working assumptions

- Treat all collected source, binaries, database structures, maps and assets as available project inputs.
- Focus on technical reconstruction rather than legal analysis.
- Preserve downloaded repositories unchanged under `vendor/`.
- Perform integration in a separate `work/` tree.
- Keep the final runtime local-only until the core stack is reproducible.
- Do not execute bundled binaries before their role and network behavior are understood.

## Repository

- GitHub remote: `https://github.com/cimerkong-jpg/KPAH.git`
- Branch: `main`
- Initial source import: `4853a0f`
- Repository synchronization checkpoint: `c421b69`
- Large ZIP/JAR/EXE/DLL files are managed by Git LFS.

On another PC:

```powershell
git clone https://github.com/cimerkong-jpg/KPAH.git
cd KPAH
git lfs install
git lfs pull
```

The project does not depend on files or notes outside this repository. The new PC still needs its own JDK and database runtime.

## Collected upstream material

### Primary runnable baseline

`vendor/server-full-huyhoang`

Upstream: `https://github.com/huyhoang0101/kpah121`

This is the main integration baseline because it contains:

- Game server Java source.
- Build and launcher scripts.
- Login server binary.
- Game and account SQL dumps.
- Map and runtime data.
- J2ME and PC clients.
- Client patching tools.
- Desktop and web administration tools.
- Backup and Windows service tooling.

### Supplemental server and database snapshot

`vendor/server-kdev`

Upstream: `https://github.com/kdev99248-ship-it/kpah`

Use this snapshot for:

- A later/different version of the server core.
- A broader database schema with 88 tables.
- Missing static-data and optional-feature tables.
- Comparing implementations when the primary baseline fails.

Do not replace the baseline database or source tree wholesale. Import individual migrations or implementations after comparison.

### Smaller independent server implementation

`vendor/server-lebaohuan`

Upstream: `https://github.com/lebaohuan1998/kpah`

This implementation is smaller and easier to read but does not include a complete SQL dump. It is useful as a behavioral and architectural reference.

### Editable J2ME clients

`vendor/client-fenix-intellij`

Upstream: `https://github.com/0xFenix/kpah-mod-intellij-build`

`vendor/client-fenix-225`

Upstream: `https://github.com/0xFenix/kpah_225_mod`

These contain Java client source and build material. Their protocol version may not match the full-server baseline, so use a client bundled with the baseline for the first connection test.

### User-provided client

`client-original/KPAH.jar`

Static inspection established:

- It is a J2ME MIDlet client, not a server.
- Entry point: `game.GameMidlet`.
- Manifest name: `KPAH Mod 64`.
- MIDP 2.0 / CLDC 1.0.
- 868 compiled classes and 1,116 archive entries.
- It contains client graphics and gameplay resources.
- It is heavily PatchLOCK-obfuscated.
- It does not contain the `yv.class` expected by the baseline J2ME patcher.
- Its embedded server host was not recoverable through a simple static string scan.

Keep it as a later compatibility target. Use a supported bundled client first.

## Runtime architecture discovered

```text
Client PC or J2ME
    |
    | TCP 19129
    v
Game server: KPAH.jar
Main class: server.TeamServer
    |
    | TCP 8023
    v
Login server: loginServer/CheckLoginSocket.jar
    |
    +-- MariaDB: account
    +-- MariaDB: kpah2

Optional local admin HTTP API:
127.0.0.1:18023
```

The client connects to the game-server port. The game server uses the login socket for authentication/account coordination.

## Baseline build behavior

Relevant files:

- `vendor/server-full-huyhoang/build_server.bat`
- `vendor/server-full-huyhoang/run.bat`
- `vendor/server-full-huyhoang/server.ini`
- `vendor/server-full-huyhoang/src/server/TeamServer.java`

The build script:

- Compiles Java source with Java 8 source/target compatibility.
- Uses JXL, MySQL Connector/J and `NQSH_5h.jar` on the classpath.
- Produces root `KPAH.jar` and `dist/KPAH2.jar`.
- Sets `server.TeamServer` as the main class.

The main launcher:

- Builds the server when source changed.
- Starts the login server.
- Starts the game server.
- Can launch the admin application.
- Uses PowerShell with execution-policy bypass.
- Can force-stop an existing server process during restart.
- Requests a 4 GB JVM heap by default.

For the first build, invoke the build components deliberately rather than double-clicking the full launcher.

## Closed binary dependencies

### NQSH_5h.jar

`vendor/server-full-huyhoang/libs/NQSH_5h.jar`

Findings:

- 878 compiled classes.
- Main class `server.TeamServer`.
- About 230 entries in game/server packages.
- Contains classes absent from the visible source.
- Contains 15 class names that also exist in visible source.
- Has no standard JAR signature.

It is not merely a utility library; it contains a legacy compiled server implementation and supplies missing classes. The initial baseline needs it. A fully source-built release will eventually need replacements for the required closed classes.

### CheckLoginSocket.jar

`vendor/server-full-huyhoang/loginServer/CheckLoginSocket.jar`

Findings:

- 166 compiled classes.
- Main class `server.LogServer`.
- Bundles a MySQL driver.
- Has no standard JAR signature.
- No complete login-server source was found in the snapshot.

The first milestone may run this component in an isolated local environment. A later milestone can replace it with a source implementation after documenting its socket protocol.

## Other binary inventory findings

The full snapshot contains approximately:

- 40 JAR files.
- 23 DLL files.
- 10 EXE files.

Important categories:

- Obfuscated J2ME clients.
- Unity PC client and managed assemblies.
- WinSW-style Windows service wrappers.
- APKTool and decompiler utilities.
- Mono.Cecil client-patching tools.
- Java attach/hotswap agents.
- Live account/currency/item administration tools.

The bundled Unity `KPAH_276.exe` reported an Authenticode hash mismatch during static inspection, consistent with a modified executable. Use a VM or sandbox for later client testing.

Small live tools and attach agents can mutate the running server or database. They are not needed for the first milestone.

## Database findings

### Baseline game dump

`vendor/server-full-huyhoang/src/kpah2_characters_backup_20260326_060651.sql`

- Produced by MariaDB 10.4.32.
- Contains 50 tables.
- Contains existing character and gameplay state rather than only schema.
- Approximately 1,253 character records were detected.
- Includes inventory, equipment, currencies, pets, quests, friends and activity state.

### Account dump

`vendor/server-full-huyhoang/loginServer/account.sql`

- Creates the `team_user` and email-verification tables.
- Includes at least one existing account row.
- The stored account password uses the legacy MySQL `*` plus SHA-1 representation.

### Supplemental schema

`vendor/server-kdev/DATABASE/kpah1.sql`

- Contains 88 tables.
- Includes static data and operational tables missing from the baseline dump.

Static analysis found about 82 table names referenced by visible baseline Java source:

- Baseline dump directly covered about 45 references.
- The kdev schema covered about 68 references.
- Other references appear to include optional features, dated backups, dead code or dynamically created tables.

Do not combine the SQL dumps by importing one over the other. Create numbered migrations for tables proven missing by an actual startup or feature test.

Likely supplemental tables include:

- `data_attribute`
- `data_item`
- `data_monster`
- `data_potion`
- `data_quest`
- `data_shop`
- `giftcode`
- `giftcode_log`
- `tob_event`
- `tob_fruit`
- `tob_x2exp`
- `tob_smsnap`
- `top_boss`

## Network behavior that must be localized

Visible server source still references old external Teamobi/Mobiplay services and IPs.

Detected behaviors include:

- External account checks.
- Plain-HTTP username/password transmission in old authentication code paths.
- User/activity logging to external IP addresses.
- Gift-code endpoints.
- SMS/payment endpoints.
- PQ/currency endpoints.
- Remote client-version and download checks.
- Direct socket connections to `27.0.14.*` addresses.

Before entering any test credentials:

1. Search visible source for `Net.getHttp`.
2. Search for `new Socket` with non-local hosts.
3. Replace external authentication with the local `account` database.
4. Stub or disable payment, SMS, gift and provider integrations.
5. Block outbound network access for Java during initial runtime tests.

No Discord, Telegram or webhook references were found in the 107 visible primary server Java files, but the two closed server JARs were not fully decompiled during the original session.

## Administration architecture

The baseline local-admin HTTP service binds by default to:

```text
127.0.0.1:18023
```

It authenticates requests through an `X-Admin-Token` header and exposes powerful operations including:

- Account ban/unban and password changes.
- Kicking players.
- Granting items/currency.
- Starting/canceling maintenance.
- Changing event settings.
- License operations.
- Runtime content operations.

Keep it bound to localhost. Generate a new local token.

The web admin uses HMAC-SHA256 session signing and scrypt password hashing. Set an explicit `KPAH_ADMIN_SESSION_SECRET`; do not rely on fallback values.

## Client localization findings

The bundled PC client currently contains a hard-coded non-local server IP. The patch source is:

`vendor/server-full-huyhoang/tools/pc_client_auth/PatchPcServerBinding.cs`

Change its forced host and port to:

```text
127.0.0.1:19129
```

It patches `Assembly-CSharp.dll` through Mono.Cecil.

The bundled J2ME patcher is:

`vendor/server-full-huyhoang/tools/client_jar_auth/PatchClientJar.java`

It expects a supported client containing `yv.class`. A local server-list entry should use:

```text
Local:127.0.0.1:19129:0
```

Client-integrity authentication exists in the baseline and uses HMAC-SHA256, but it is disabled in the checked configuration. Keep it disabled until the core protocol works.

## Recommended compatibility environment

- Windows 10 or Windows 11.
- JDK 8 for the first baseline build.
- MariaDB 10.4.x for compatibility with the primary dump.
- PowerShell 5.1 or later.
- FreeJ2ME for J2ME client testing.
- At least 4 GB free memory for the launcher defaults.
- Git and Git LFS for repository synchronization.

## Configuration decisions

Generate a working configuration rather than editing `vendor` files.

Target values:

```ini
sv.port=19129
db.host=localhost:3306
db.name=kpah2
sv.iplogin=127.0.0.1
sv.portlogin=8023
sv.localAdminEnabled=1
sv.localAdminHost=127.0.0.1
sv.localAdminPort=18023
sv.clientAuthEnabled=0
api.sv=2
api.url=http://127.0.0.1
```

Generate new values for:

- Database username and password.
- Admin token.
- Client-auth secret.
- Web admin session secret.

Normalize obsolete absolute paths such as `D:\server\...` and `C:\Users\Administrator\Desktop\SEVER-KPAH-FULL\...` to project-relative paths.

## Work already completed

- [x] Identified candidate public repositories.
- [x] Compared the primary server variants.
- [x] Analyzed baseline configuration and startup scripts.
- [x] Classified server/client/tool binaries without executing them.
- [x] Inspected the user-provided J2ME client.
- [x] Compared baseline and supplemental database coverage.
- [x] Identified external network calls in visible source.
- [x] Downloaded five source snapshots.
- [x] Preserved original archives.
- [x] Calculated archive SHA-256 hashes.
- [x] Added `SOURCES.md`.
- [x] Added the detailed `BUILD_PROCESS.md` checklist.
- [x] Installed Git and Git LFS on the original PC.
- [x] Initialized the Git repository.
- [x] Uploaded the repository and LFS objects to GitHub.

No game binary has been executed and no server build has been attempted yet.

## Next checkpoint

Continue with Phase 1 from `BUILD_PROCESS.md`:

1. Create `work/server` from `vendor/server-full-huyhoang`.
2. Preserve `vendor` unchanged.
3. Create `work/database/migrations`.
4. Create `work/client-pc`, `work/client-j2me` and `work/config`.
5. Inventory JDK and MariaDB availability on the active PC.
6. Generate localhost-only configuration templates.
7. Disable external source endpoints before running any server or entering credentials.

## Prompt for a new Codex session

Use this prompt after cloning on another PC:

> Read README.md, SOURCES.md, PROJECT_CONTEXT.md and BUILD_PROCESS.md completely. Continue from the current Next checkpoint. Preserve vendor snapshots unchanged, perform integration under work/, update BUILD_PROCESS.md after every completed checkpoint, and commit/push each reproducible change to main. Do not run bundled server binaries until external network endpoints have been disabled or outbound networking has been blocked.

## Documentation precedence

When documents differ:

1. The latest checked-in `BUILD_PROCESS.md` progress state is authoritative for execution status.
2. `PROJECT_CONTEXT.md` is authoritative for original-session findings and decisions.
3. `SOURCES.md` is authoritative for source origins and archive hashes.
4. `README.md` is the navigation entry point.
