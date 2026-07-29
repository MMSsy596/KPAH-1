# KPAH source inventory

Downloaded on 2026-07-29. The extracted repositories under `vendor/` are preserved as upstream snapshots. Build integration should be done in a separate working directory so these copies remain available for comparison.

## Primary snapshots

| Local directory | Upstream | Branch | Archive SHA-256 |
| --- | --- | --- | --- |
| `vendor/server-full-huyhoang` | https://github.com/huyhoang0101/kpah121 | `main` | `38EBB07FE70AEBF1BBD8C70342CD1BF7EA88D93B85D8C8746B9F8576C4EB205B` |
| `vendor/server-kdev` | https://github.com/kdev99248-ship-it/kpah | `master` | `7E23730377E1E98932D0A466DFD43A0C97B1EF59F901E4764D1E45EDC31079CD` |
| `vendor/server-lebaohuan` | https://github.com/lebaohuan1998/kpah | `main` | `049CA684EB7D033D0EFC026157225FA8C8F324C4627A1D2C8659FE5C1C00A355` |
| `vendor/client-fenix-intellij` | https://github.com/0xFenix/kpah-mod-intellij-build | `main` | `6C8458E13A4D49A4B0356E113F044EB8FB35301E74E96EAC0E4F87AF78892485` |
| `vendor/client-fenix-225` | https://github.com/0xFenix/kpah_225_mod | `main` | `584AB72E79B8FE500B0A0A7103E415E37A9834E530976672C8A7EC899133B59D` |

The downloaded ZIP files are retained under `vendor/_archives/`.

## User-provided client

`client-original/KPAH.jar`

- SHA-256: `BAA6031C76782729F3082220A775565C59637991FEDBA1E188D03567CF42E45D`
- Type: J2ME MIDlet client
- Entry point: `game.GameMidlet`
- Manifest name: `KPAH Mod 64`

## Key build inputs verified

- Full game server source: `vendor/server-full-huyhoang/src/`
- Game server build script: `vendor/server-full-huyhoang/build_server.bat`
- Login server: `vendor/server-full-huyhoang/loginServer/CheckLoginSocket.jar`
- Login database: `vendor/server-full-huyhoang/loginServer/account.sql`
- Game database snapshot: `vendor/server-full-huyhoang/src/kpah2_characters_backup_20260326_060651.sql`
- Required legacy server dependency: `vendor/server-full-huyhoang/libs/NQSH_5h.jar`
- Supplemental database schema: `vendor/server-kdev/DATABASE/kpah1.sql`
- Supplemental server implementations: `vendor/server-kdev/src/` and `vendor/server-lebaohuan/src/`
- Editable J2ME client source: `vendor/client-fenix-intellij/app/src/`
- Older J2ME client source/reference: `vendor/client-fenix-225/src/`

## Snapshot sizes

| Directory | Files | Extracted bytes |
| --- | ---: | ---: |
| `server-full-huyhoang` | 8,849 | 313,851,684 |
| `server-kdev` | 6,288 | 40,762,668 |
| `server-lebaohuan` | 5,142 | 19,099,141 |
| `client-fenix-intellij` | 78 | 15,296,413 |
| `client-fenix-225` | 204 | 2,302,285 |

## Integration rule

Use `server-full-huyhoang` as the initial runnable baseline. Pull individual schema migrations or implementations from the supplemental snapshots only after comparing their protocol, table, and data-model assumptions. Do not merge entire repository trees blindly.
