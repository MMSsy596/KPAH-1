# KPAH local rebuild workspace

This repository collects the server, database, map, client and tooling snapshots needed to reproduce a local KPAH stack.

- Start with [BUILD_PROCESS.md](./BUILD_PROCESS.md) for architecture, known blockers, build phases and the live progress checklist.
- See [SOURCES.md](./SOURCES.md) for upstream repositories, branches, archive hashes and the source inventory.
- Treat `vendor/` as preserved upstream material. Integration work belongs under the future `work/` directory.

Current state: the local stack builds and runs through MariaDB, login, game
server and a socket-enabled FreeJ2ME client. Local registration, login,
character creation, map movement, NPC/shop interaction, two-way combat, monster
kill, death and respawn have been exercised. The solo EXP failure was traced to
an unguarded `party` access and fixed in source; the server rebuild passes, but
the post-fix kill/EXP/persistence regression is still pending. See
`GAME_TEST_REPORT.md` for the exact verified and pending scope.
