# Liferay One Workspace

Instructions here stack on top of the repo-root instructions — when the two conflict, this file wins.

## Architecture

Pure Liferay SaaS client-extension workspace — no OSGi modules, no Ant. Client extensions under `client-extensions/`:

- `liferay-one-custom-element/` — single React element serving Marketplace, Support, and Admin page groups.
- `liferay-one-etc-spring-boot/` — custom REST, Salesforce Pub/Sub subscriber, crons, integration clients.
- `liferay-one-global-css/` — shared styles.
- `liferay-one-instance-settings/` — global Liferay instance configs.
- `liferay-one-site-initializer/` — single site, all Object definitions + roles + fragments.

## Development

Run from `workspaces/liferay-one-workspace/`.

- **Build:** `./gradlew build`
- **Format:** Run the repo-root `/format-source` skill.
- **Deploy:** Run the `/deploy` skill.
- **Pre-commit:** Run format and build first; do not deploy a failing build.

@./rules/object-naming.md
@./skills/deploy.md