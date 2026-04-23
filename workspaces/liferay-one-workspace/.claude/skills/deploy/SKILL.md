---

allowed-tools: [Bash, Glob, Grep, Read]
argument-hint: "[client-extension-name or 'all']"
description: Build and deploy one or all One workspace client extensions to the running devcontainer Liferay.
name: deploy

---

# Deploy One Workspace Client Extensions

Deploy `liferay-one-*` client extensions to the devcontainer Liferay. Pure SaaS — no `ant deploy`, no portal-core changes.

## 1. Pre-flight

```bash
./gradlew formatSource build
```

Stop and report if either step fails. Do not deploy a failing build.

## 2. Resolve Target

Valid targets: `liferay-one-custom-element`, `liferay-one-global-css`, `liferay-one-instance-settings`, `liferay-one-site-initializer`, `all`.

Use `${ARGUMENTS}` first. When missing, check `git diff --name-only` and pick every touched `client-extensions/liferay-one-*` directory. When multiple are touched, confirm with the user before proceeding.

## 3. Deploy

```bash
# Single
./gradlew :client-extensions:<name>:clean :client-extensions:<name>:deploy

# All
./gradlew clean deploy
```

Report: what was deployed, Gradle result, and log evidence of pickup.