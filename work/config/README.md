# Local configuration

The checked-in configuration templates are localhost-only and contain no
usable credentials. Before the first runtime test, copy the templates to local
ignored files and replace every `CHANGE_ME_LOCAL_ONLY` value with a freshly
generated secret.

- `server.ini.template` configures the game server for `127.0.0.1:19129`.
- `login-server.ini.template` configures the login server for
  `127.0.0.1:3306/account` and port `8023`.

Do not commit generated database passwords, admin tokens, or session secrets.
