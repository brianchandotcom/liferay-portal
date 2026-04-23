---

allowed-tools: [Bash, Glob, Grep, Read]
argument-hint: "[client-extension-name or 'all']"
description: Run formatSource on one or all One workspace client extensions. Use when the user asks to format, lint, or invokes /format.
name: format

---

# Format One Workspace Client Extensions

Run from `workspaces/liferay-one-workspace/`.

## 1. Resolve Target

Use `${ARGUMENTS}` first. When missing, check `git diff --name-only` and pick every touched `client-extensions/liferay-one-*` directory. When multiple are touched or unclear, default to all.

## 2. Format

```bash
# Single
./gradlew :client-extensions:<name>:formatSource

# All
./gradlew formatSource
```

## 3. Report

List any files that were modified by the formatter. If none were changed, confirm clean. If formatSource exits non-zero, report the error and stop.