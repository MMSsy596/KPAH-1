# KPAH local build and integration process

This document is the working source of truth for rebuilding the KPAH stack locally. Update the progress table and the relevant phase notes after every completed checkpoint.

## Current status

Last updated: 2026-07-29

| Phase | Status | Result |
| --- | --- | --- |
| 0. Collect upstream material | Complete | Five source snapshots and the user-provided client are stored locally and hashed. |
| 1. Create reproducible workspace | Not started | Next phase. |
| 2. Provision Java and MariaDB | Not started | JDK and database tooling are not installed or verified yet. |
| 3. Build baseline game server | Not started | No repository binary has been executed. |
| 4. Initialize databases | Not started | SQL snapshots have only been inspected. |
| 5. Start login and game services | Not started | Ports 8023 and 19129 are not configured or tested. |
| 6. Patch a client for localhost | Not started | Bundled clients still contain non-local server bindings. |
| 7. Validate core gameplay loop | Not started | Login, character select, map entry and combat remain to be tested. |
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

- [ ] Create `work/server` from `vendor/server-full-huyhoang`.
- [ ] Keep all `vendor` snapshots unchanged.
- [ ] Create `work/database/migrations`.
- [ ] Create `work/client-pc` and `work/client-j2me`.
- [ ] Create local configuration templates with no embedded upstream credentials.
- [ ] Record hashes of every closed dependency used in the build.

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

### Phase 2: Provision tools

Preferred compatibility environment:

- Windows 10/11 or a Windows VM.
- JDK 8 for the baseline compiler.
- MariaDB 10.4.x, matching the primary database dump producer.
- PowerShell 5.1+.
- FreeJ2ME for J2ME client testing.
- At least 4 GB free RAM for the initial launcher defaults.

Tasks:

- [ ] Install or provide a portable JDK 8.
- [ ] Verify `java`, `javac` and `jar` versions.
- [ ] Install MariaDB or create a local container/VM database.
- [ ] Verify that ports 8023, 19129 and 18023 are free.
- [ ] Do not install services or scheduled tasks during the first build.

### Phase 3: Prepare databases

- [ ] Create an empty `account` database.
- [ ] Create an empty `kpah2` database.
- [ ] Create a least-privilege local database user.
- [ ] Import `loginServer/account.sql` into `account`.
- [ ] Import the baseline game dump into `kpah2`.
- [ ] Decide whether the first test uses a clean account/character set or a preserved snapshot copy.
- [ ] Compare missing runtime tables with `vendor/server-kdev/DATABASE/kpah1.sql`.
- [ ] Add supplemental tables through numbered migration files only.

Suggested migration naming:

```text
001_baseline_account.sql
002_baseline_kpah2.sql
010_add_static_data_tables.sql
020_add_optional_feature_tables.sql
```

### Phase 4: Localize and build the game server

- [ ] Copy the baseline into `work/server`.
- [ ] Replace all configuration secrets with generated local values.
- [ ] Normalize absolute paths.
- [ ] Disable external HTTP/socket code paths.
- [ ] Confirm the required JAR classpath.
- [ ] Run `build_server.bat` from the working copy.
- [ ] Verify that `KPAH.jar` has main class `server.TeamServer`.
- [ ] Hash the build output.
- [ ] Start the server with outbound network blocked.
- [ ] Capture the first startup log and SQL errors.

### Phase 5: Start login and game services

- [ ] Configure `loginServer/server.ini` for local MariaDB.
- [ ] Start `CheckLoginSocket.jar` on port 8023.
- [ ] Confirm the socket is listening.
- [ ] Start the game server on port 19129.
- [ ] Confirm its connection to the login server.
- [ ] Confirm map and template initialization.
- [ ] Do not expose ports through the router or public firewall.

### Phase 6: Patch a localhost client

PC client path:

- [ ] Change the constants in `tools/pc_client_auth/PatchPcServerBinding.cs` to `127.0.0.1:19129`.
- [ ] Remove dependence on the external server-list URL or provide a local `NQSH2.txt`.
- [ ] Build and apply the Mono.Cecil patcher to `Assembly-CSharp.dll`.
- [ ] Keep client integrity authentication disabled for the first test.

J2ME client path:

- [ ] Create a local server list containing `Local:127.0.0.1:19129:0`.
- [ ] Run the baseline `PatchClientJar.java` against a supported bundled client.
- [ ] Test through FreeJ2ME.
- [ ] Patch `client-original/KPAH.jar` only after the supported client succeeds.

### Phase 7: Validate the core gameplay loop

- [ ] Register or insert a test account.
- [ ] Log in through the client.
- [ ] Create/select a character.
- [ ] Load the first map.
- [ ] Validate movement and map transitions.
- [ ] Spawn and attack monsters.
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

Create `work/server` as a working copy of the full baseline, inventory the required Java/MariaDB toolchain on the current PC, and generate localhost-only configuration templates. No server or bundled binary should be executed before the external endpoints have been disabled.
