# Liferay One Workspace

Instructions here stack on top of the repo-root `.claude/CLAUDE.md` — when the two conflict, this file wins.

## Architecture

Pure Liferay SaaS client-extension workspace — no OSGi modules, no Ant. Client extensions under `client-extensions/`:

- `liferay-one-custom-element/` — single React element serving Marketplace, Support, and Admin page groups.
- `liferay-one-etc-spring-boot/` — custom REST, Salesforce Pub/Sub subscriber, crons, integration clients.
- `liferay-one-global-css/` — shared styles.
- `liferay-one-instance-settings/` — global Liferay instance configs.
- `liferay-one-site-initializer/` — single site, all Object definitions + roles + fragments.

## Development

Run from `workspaces/liferay-one-workspace/`.

- **Deploy:** Run `/deploy` skill
- **Format:** Run `/format` skill
- **Build:** `./gradlew build`
- **Pre-commit:** Run format and build
- **Commit:** Run `/commit` skill