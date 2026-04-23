---

allowed-tools: [Bash]
description: Rebase the current branch onto liferay-one/liferay-portal master-temp. Use when the user asks to rebase, sync with upstream, or update their branch.
name: one-rebase

---

# Rebase onto liferay-one master-temp

Rebase the current branch onto the latest `master-temp` from `liferay-one/liferay-portal`.

## Preconditions

- The working tree has no uncommitted changes. When dirty, abort and ask the user to commit first (suggest `/commit`); do not stash or discard their work.

- Not on a protected branch (`master`, `master-temp`). When on one of these, abort and report the error.

## Steps

### 1. Locate the Upstream Remote

Scan `git remote -v` for a remote whose URL contains `liferay-one/liferay-portal`. Record its name.

When none exists, add it:

```bash
git remote add liferay-one https://github.com/liferay-one/liferay-portal.git
```

Use `liferay-one` as the remote name for subsequent steps.

### 2. Fetch

```bash
git fetch <remote> master-temp
```

### 3. Rebase

```bash
git rebase <remote>/master-temp
```

When conflicts arise, stop immediately and report the conflicting files to the user. Do not attempt to resolve them automatically.

## Report

On success, report the branch name and the upstream ref (`<remote>/master-temp`) it was rebased onto.