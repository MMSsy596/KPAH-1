# KPAH local build and integration process

This document is the working source of truth for rebuilding the KPAH stack locally. Update the progress table and the relevant phase notes after every completed checkpoint.

## Current status

Last updated: 2026-07-29

| Phase | Status | Result |
| --- | --- | --- |
| 0. Collect and synchronize upstream material | Complete | Five source snapshots and the user-provided client are stored, hashed and pushed to the GitHub `main` branch with Git LFS. |
| 1. Create reproducible workspace | Complete | Baseline copied to `work/server`; database, client and configuration work areas created; closed dependencies hashed. |
| 2. Provision Java and MariaDB | Complete | Portable Temurin JDK 8.0.492 and MariaDB 10.4.32 are verified. MariaDB runs without a Windows service and binds to `127.0.0.1:3306`. |
| 3. Prepare databases | Complete | Baseline `account` and `kpah2` dumps imported; local least-privilege user created; three evidence-driven migrations bring `kpah2` to 65 tables. |
| 4. Localize and build game server | Complete | External application endpoints are locally blocked, configuration secrets are generated in ignored files, and the rebuilt server starts without logged errors. |
| 5. Start login and game services | Complete | MariaDB, login server, game server and local admin API are running; login/database connection and map/template initialization are confirmed. |
| 6. Patch a client for localhost | Complete | The supported J2ME client and FreeJ2ME socket transport use localhost and have completed authenticated sessions. |
| 7. Validate core gameplay loop | In progress | Registration, fresh character, movement, NPC/shop, damage, kill, death and respawn pass. Solo EXP source fix builds; post-fix runtime persistence and loot remain. |
| 8. Restore optional systems | Not started | Events, admin, gift codes, payment hooks and other optional systems remain disabled. |
| 9. Produce clean local release | Not started | No release artifact exists yet. |

## Completion definition

The first playable milestone is complete when all of the following work without external services:

1. Start MariaDB.
2. Start the login server on `127.0.0.1:8023`.
3. Start the game server on `127.0.0.1:19129`.
4. Open a patched PC or J2ME client.
5. Register or load a local account.
6. Select or create a character.
7. Enter a map, see monsters, move and perform combat.
8. Save the character and load it again after a full restart.

## Repository synchronization

- Remote: `https://github.com/cimerkong-jpg/KPAH.git`
- Branch: `main`
- Initial source commit: `4853a0f`
- Git LFS patterns: ZIP, JAR, EXE, DLL, 7Z and RAR
- Initial LFS upload: 96 unique objects, approximately 357 MB
- Status: complete

## Source inventory

See [SOURCES.md](./SOURCES.md) for upstream URLs, branches, archive hashes, sizes and verified key files.

### Baseline

`vendor/server-full-huyhoang`

Use this snapshot as the initial runnable baseline. It contains the game server source, login server binary, SQL dumps, map data, clients, admin tools and Windows launch scripts.

### Supplemental implementations

- `vendor/server-kdev`: later server variant and a broader 88-table database schema.
- `vendor/server-lebaohuan`: smaller independent server implementation useful for comparison.
- `vendor/client-fenix-intellij`: editable J2ME client source with an IntelliJ/Ant build.
- `vendor/client-fenix-225`: older J2ME client source and assets.
- `client-original/KPAH.jar`: user-provided PatchLOCK-obfuscated J2ME client.

Do not merge entire source trees or SQL dumps blindly. Compare and import one migration or implementation at a time.

## Runtime architecture

```text
PC or J2ME client
        |
        | TCP 19129
        v
Game server (KPAH.jar, main class server.TeamServer)
        |
        | TCP 8023
        v
Login server (loginServer/CheckLoginSocket.jar)
        |
        +-- MariaDB database: account
        +-- MariaDB database: kpah2

Optional local-only services:
        - Admin HTTP API: 127.0.0.1:18023
        - Web/admin UI
        - Local server-list endpoint for clients
```

## Known technical facts

### Game server

- Primary source entry point: `vendor/server-full-huyhoang/src/server/TeamServer.java`.
- Build script: `vendor/server-full-huyhoang/build_server.bat`.
- Java language target: Java 8.
- Build output: root `KPAH.jar` and `dist/KPAH2.jar`.
- Runtime launcher currently requests a 4 GB JVM heap.
- The code uses relative project resources as well as several obsolete absolute Windows paths that must be normalized.

### Closed server dependencies

The baseline cannot currently be reproduced from visible source alone.

`libs/NQSH_5h.jar` contains:

- 878 compiled classes.
- About 230 game/server class entries.
- A legacy `server.TeamServer` main class.
- Classes that are not present in the visible baseline source.
- No standard JAR signature.

`loginServer/CheckLoginSocket.jar` contains:

- 166 compiled classes.
- Main class `server.LogServer`.
- A bundled MySQL driver.
- No corresponding login-server source in the snapshot.
- No standard JAR signature.

The first milestone will use these dependencies unchanged but isolated. A later milestone may replace them with source-built implementations.

### Database coverage

The baseline game dump contains 50 tables. Static inspection found about 82 table names referenced by the visible Java source. The baseline dump directly covers about 45 of those references. The kdev schema covers about 68.

Important supplemental tables include:

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

Some missing names are dated backup tables, optional features, dead code or tables created dynamically. Do not create every missing name in advance. Start the core server, collect actual SQL errors and add explicit migrations.

### Map and assets

The full snapshot includes server map data (`map`, `cMap` and related resources), J2ME client resources and a Unity PC client. The presence of data does not guarantee every historical event map is active. Validate map IDs during gameplay tests.

### User-provided client

`client-original/KPAH.jar` is a J2ME MIDlet, not a server binary.

- Entry point: `game.GameMidlet`.
- Manifest name: `KPAH Mod 64`.
- MIDP 2.0 / CLDC 1.0.
- 868 compiled classes and 1,116 archive entries.
- PatchLOCK-obfuscated.
- Does not contain the `yv.class` expected by the baseline J2ME patcher.

Use a bundled baseline client for the first connection test. Return to this client after the server protocol is known to work.

## External dependencies that must be removed or localized

The visible server source contains active references to old Teamobi/Mobiplay endpoints and IP addresses. Some code paths send account or activity data over plain HTTP.

Before any credential is entered, locate and replace:

- `Net.getHttp(...)` calls targeting non-local domains or IPs.
- `new Socket(...)` calls targeting `27.0.14.*`.
- Official login checks.
- External user logging.
- Gift-code, SMS, payment and PQ endpoints.
- Remote version and download checks.

Local replacements may return a deterministic offline result, use a local API stub, or leave the feature disabled. During early testing, block outbound network access for Java processes.

## Configuration target

Use a generated local configuration rather than editing the preserved vendor snapshot directly.

```ini
sv.port=19129
limit.ccu=20

db.host=localhost:3306
db.name=kpah2
db.user=kpah_local
db.password=<generated-local-password>

db.nameNap=kpah2
db.userNap=kpah_local
db.pass=<generated-local-password>

db.host1=localhost:3306
db.name1=kpah2
db.user1=kpah_local
db.password1=<generated-local-password>

sv.iplogin=127.0.0.1
sv.portlogin=8023

sv.localAdminEnabled=1
sv.localAdminHost=127.0.0.1
sv.localAdminPort=18023
sv.localAdminToken=<generated-local-token>

sv.clientAuthEnabled=0
sv.clientAuthSecret=<generated-local-secret>

api.sv=2
api.url=http://127.0.0.1
```

Remove or replace obsolete paths such as:

```text
D:\server\nqsh\log\
D:\nqsh30\toolRestart.bat
C:\Users\Administrator\Desktop\SEVER-KPAH-FULL\
```

Use project-relative `logs`, `runtime` and `tools` paths where possible.

## Build phases

### Phase 1: Create a reproducible workspace

- [x] Create `work/server` from `vendor/server-full-huyhoang`.
- [x] Keep all `vendor` snapshots unchanged.
- [x] Create `work/database/migrations`.
- [x] Create `work/client-pc` and `work/client-j2me`.
- [x] Create local configuration templates with no embedded upstream credentials.
- [x] Record hashes of every closed dependency used in the build.

Checkpoint output:

```text
work/
  server/
  database/
    migrations/
  client-pc/
  client-j2me/
  config/
```

Checkpoint completed on 2026-07-29. The copy contained 8,849 files and
314,528,655 bytes, matching the preserved baseline before local-only edits.
Dependency hashes are recorded in `work/config/closed-dependencies.sha256`.

### Phase 2: Provision tools

Preferred compatibility environment:

- Windows 10/11 or a Windows VM.
- JDK 8 for the baseline compiler.
- MariaDB 10.4.x, matching the primary database dump producer.
- PowerShell 5.1+.
- FreeJ2ME for J2ME client testing.
- At least 4 GB free RAM for the initial launcher defaults.

Tasks:

- [x] Install or provide a portable JDK 8.
- [x] Verify `java`, `javac` and `jar` versions.
- [x] Install MariaDB or create a local container/VM database.
- [x] Verify that ports 8023, 19129 and 18023 are free.
- [x] Do not install services or scheduled tasks during the first build.

Tooling verified on 2026-07-29:

- Eclipse Temurin OpenJDK `1.8.0_492-b09`, portable under the ignored
  `.toolchains/` directory.
- `javac 1.8.0_492` and the matching Java 8 `jar` tool.
- Ports 8023, 19129, 18023 and 3306 had no listeners.
- The current WinGet catalog offered MariaDB 12.3.2, so it was not installed
  in place of the preferred 10.4-compatible runtime.
- MariaDB 10.4.32 was downloaded from the official MariaDB archive as a
  portable Windows ZIP. SHA-256:
  `C7239062B1E491C8292ED53F38DA633F0642C9E5B78A08673FDB4919EE7194BC`.
- The instance uses `runtime-local/mariadb/data`, binds to `127.0.0.1:3306`
  and is not installed as a Windows service.

### Phase 3: Prepare databases

- [x] Create an empty `account` database.
- [x] Create an empty `kpah2` database.
- [x] Create a least-privilege local database user.
- [x] Import `loginServer/account.sql` into `account`.
- [x] Import the baseline game dump into `kpah2`.
- [x] Decide whether the first test uses a clean account/character set or a preserved snapshot copy.
- [x] Compare missing runtime tables with `vendor/server-kdev/DATABASE/kpah1.sql`.
- [x] Add supplemental tables through numbered migration files only.

Suggested migration naming:

```text
001_baseline_account.sql
002_baseline_kpah2.sql
010_add_static_data_tables.sql
020_add_optional_feature_tables.sql
```

Database checkpoint completed on 2026-07-29:

- The first test preserves the supplied snapshot: 1 account row and 1,253
  character rows.
- `account` contains 2 tables; `kpah2` contains 65 tables after migrations.
- `010_add_data_item.sql` adds the first missing template table and 904 rows.
- `011_add_startup_core_tables.sql` adds only the nine tables reported by the
  second startup attempt.
- `012_add_runtime_system_tables.sql` adds `5h_systems` and
  `tob_log_use_luong`, both reported by the successful runtime.
- Root and `kpah_local` passwords, admin token and client secret are randomly
  generated under ignored `local-config/`; an empty root password is rejected.

### Phase 4: Localize and build the game server

- [x] Copy the baseline into `work/server`.
- [x] Replace all configuration placeholders with generated local values immediately before database startup.
- [x] Normalize absolute paths.
- [x] Disable external HTTP/socket code paths.
- [x] Confirm the required JAR classpath.
- [x] Run `build_server.bat` from the working copy.
- [x] Verify that `KPAH.jar` has main class `server.TeamServer`.
- [x] Hash the build output.
- [x] Start the server with external application endpoints blocked by local-only networking replacements.
- [x] Capture the first startup log and SQL errors.

First successful build on 2026-07-29:

```text
cmd.exe /d /c build_server.bat
Build OK: KPAH.jar va dist\KPAH2.jar
SHA-256: 25C21B5C8384C5AB58CF2CDB395497C2A0203735B6F5ADCA9BB7F37260100A06
```

Both output JARs were 1,785,349 bytes and had identical hashes. The manifest
declares `server.TeamServer` and the Java 8 dependency classpath. Two source
uses of Java 15 `String.formatted` were replaced with Java 8-compatible
`String.format`. A source-built `data.Net` now shadows the legacy networking
class in `NQSH_5h.jar` and only permits loopback HTTP endpoints. The visible
raw legacy authentication sockets and external activity queues are disabled.
No server or bundled login binary was executed during this checkpoint.

First successful runtime checkpoint on 2026-07-29:

- Login server JDBC 5.0.8 initially rejected MariaDB's `utf8mb4` handshake;
  adding `useUnicode=true&characterEncoding=UTF-8` to the local JDBC URL fixed
  it.
- Startup attempts then reported missing `data_item`, nine core/static tables,
  and two runtime tables. Each failure was preserved in ignored runtime logs
  and fixed through migrations 010, 011 and 012.
- Missing `logs/runtime` and `logs/vantieu` directories are now generated by
  `Initialize-LocalConfiguration.ps1`.
- Dynamic monthly tables now use `CREATE TABLE IF NOT EXISTS`.
- Local builds skip the obsolete online-status HTTP call.
- Clean rebuilt JAR SHA-256:
  `C88F1861C38AE3266A988A76EBD14A1B643BDA406EA99BCADD6D866D5C4953EF`.
- A clean restart reached template/map initialization, local admin startup and
  port 19129 with no exception, SQL error, missing table or missing file in the
  current logs.

### Phase 5: Start login and game services

- [x] Configure `loginServer/server.ini` for local MariaDB.
- [x] Start `CheckLoginSocket.jar` on port 8023.
- [x] Confirm the socket is listening.
- [x] Start the game server on port 19129.
- [x] Confirm its connection to the login server.
- [x] Confirm map and template initialization.
- [x] Do not expose ports through the router or public firewall.

Verified listeners:

```text
127.0.0.1:3306  MariaDB
*:8023          closed login server (no router/firewall exposure added)
*:19129         game server (no router/firewall exposure added)
127.0.0.1:18023 local admin HTTP API
```

The login JAR's six application classes were statically inspected before
execution. They contain only an inbound `ServerSocket` and JDBC access through
the localhost URL in `server.ini`; no external HTTP or outbound application
socket was found. The game server's legacy external URLs are routed through the
source-built loopback-only `data.Net`.

### Phase 6: Patch a localhost client

PC client path:

- [ ] Change the constants in `tools/pc_client_auth/PatchPcServerBinding.cs` to `127.0.0.1:19129`.
- [ ] Remove dependence on the external server-list URL or provide a local `NQSH2.txt`.
- [ ] Build and apply the Mono.Cecil patcher to `Assembly-CSharp.dll`.
- [ ] Keep client integrity authentication disabled for the first test.

J2ME client path:

- [x] Create a local server list containing `Local:127.0.0.1:19129:0`.
- [x] Run the baseline `PatchClientJar.java` against a supported bundled client.
- [x] Test through FreeJ2ME.
- [ ] Patch `client-original/KPAH.jar` only after the supported client succeeds.

J2ME checkpoint:

- Added portable Temurin JDK 17 under the ignored `.toolchains/jdk17/` tree
  because the baseline patcher imports the JDK's internal ASM package.
- Added reproducible `work/client-j2me/Build-LocalClient.ps1` and
  `work/client-j2me/Start-LocalClient.ps1` helpers.
- The supported baseline `grinding2.jar` was patched to use
  `127.0.0.1` and `http://127.0.0.1:18080/NQSH2.txt`.
- Patched output SHA-256:
  `2F734B001E47AE5700C0C21342EC7B55E99ED5F6CA08606C8E91858B478376AB`.
- The bundled FreeJ2ME 1.52 maps `socket://` connections to a stub
  `HttpConnectionImpl`; its `connect()` method does not create a TCP socket.
- Added `work/client-j2me/Build-LocalEmulator.ps1` and a small replacement
  `Connector`/`SocketConnectionImpl`. The resulting
  `dist/freej2me-network.jar` preserves the bundled emulator UI while providing
  real `java.net.Socket` transport.
- Network-enabled emulator SHA-256:
  `41376D3D6C4653BAF5AB4744C7F04430352A29CDB77BD2CD9DBB841D30A7868C`.
- Verified an established loopback TCP session from FreeJ2ME to
  `127.0.0.1:19129`, successful account authentication and transition to the
  character-creation screen.

### Phase 7: Validate the core gameplay loop

- [x] Register or insert a test account.
- [x] Log in through the client.
- [x] Create/select a character.
- [x] Load the first map.
- [x] Validate movement and map transitions.
- [x] Spawn and attack monsters.
- [ ] Receive experience and loot.
- [ ] Save inventory, position and character state.
- [ ] Restart all services and verify persistence.

Record every failed message command, SQL statement and missing resource before modifying code.

### Phase 8: Restore optional systems incrementally

Enable one subsystem per checkpoint:

- [ ] NPC shops and item templates.
- [ ] Skills and buffs.
- [ ] Parties and friends.
- [ ] Clans.
- [ ] Trade and market.
- [ ] Bosses.
- [ ] Quests.
- [ ] Gift codes.
- [ ] Events.
- [ ] Admin panel.
- [ ] Web UI.
- [ ] Client integrity authentication.

Payment, SMS and external account-provider integrations should remain replaced by local stubs.

### Phase 9: Produce a clean local release

- [ ] Build server artifacts from the working source.
- [ ] Include only required runtime dependencies.
- [ ] Include numbered database migrations.
- [ ] Include a localhost-only default configuration template.
- [ ] Include one tested PC or J2ME client.
- [ ] Exclude upstream credentials, player dumps, runtime logs and temporary tools.
- [ ] Record SHA-256 hashes and exact tool versions.
- [ ] Test installation from a clean machine or VM.

## First-build diagnostics

Collect these artifacts for every failed run:

- Java version and full command line.
- Server stdout/stderr.
- Login server stdout/stderr.
- MariaDB error log.
- First SQL exception with its table and column names.
- Client connection target and port.
- Client/server protocol command at failure.
- Missing resource path or map ID.

Do not fix several unrelated errors in one change. Make one reproducible change, rerun the checkpoint, then update this document.

## Progress update rules

After each completed task:

1. Change its checkbox to `[x]`.
2. Update the top-level status table.
3. Add the command or tool version used.
4. Record generated artifact hashes.
5. Record unresolved errors under the relevant phase.
6. Commit the source, documentation and migration together.

## Next action

Reconnect the localhost FreeJ2ME client, kill one level-1 monster and verify
that the solo EXP null guard produces live EXP and persists it after graceful
logout. Then verify loot pickup before continuing the first quest and equipment
regressions.
