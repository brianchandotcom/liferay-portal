---

allowed-tools: [Bash, Glob, Grep, Read]
description: Deploy `liferay-one-*` client extensions to the devcontainer Liferay. Use when the user asks to deploy, run /deploy, or wants to push client extension changes.
name: one-deploy

---

# Deploy One Workspace Client Extensions

Deploy `liferay-one-*` client extensions to the Liferay Docker Compose setup. Pure SaaS — no `ant deploy`, no portal-core changes.

## 1. Pre-flight

```bash
./gradlew formatSource build
```

Stop and report if either step fails. Do not deploy a failing build.

## 2. Resolve Target

Valid targets: `liferay-one-custom-element`, `liferay-one-global-css`, `liferay-one-instance-settings`, `liferay-one-site-initializer`, `all`.

Check `git diff --name-only` and pick every touched `client-extensions/liferay-one-*` directory. When multiple are touched, confirm with the user before proceeding.

## 3. Deploy

```bash
# Single
./gradlew :client-extensions:<name>:clean :client-extensions:<name>:deploy \
    -Ddeploy.docker.container.id=$(docker compose -f ~/repos/lfris-www/docker-compose.yml ps -q liferay)

# All
./gradlew clean deploy \
    -Ddeploy.docker.container.id=$(docker compose -f ~/repos/lfris-www/docker-compose.yml ps -q liferay)
```

Report: what was deployed, Gradle result, and log evidence of pickup.
