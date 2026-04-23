---

allowed-tools: [Bash]
description: Merge the current branch's PR to liferay-one master-temp, then forward it to liferay-one master via a new PR with ci:forward. Use when the user asks to forward a PR, send a PR upstream, or invokes /one-pr-forward.

name: one-pr-forward

---

# Forward Current Branch PR Upstream

From the current branch: merge its open PR into `liferay-one/liferay-portal:master-temp`, rebase onto `liferay-one/master`, open a forwarding PR against `liferay-one/liferay-portal:master`, and trigger CI with `ci:forward`.

## Usage

```
/one-pr-forward
```

Run from the branch whose PR you have just reviewed and are ready to ship.

## Preconditions

- The working tree has no uncommitted changes. When dirty, abort and ask the user to commit or stash first.
- Not on a protected branch (`master`, `master-temp`). When on one of these, abort and report the error.

## Steps

### 1. Resolve Context

Read the GitHub username from the `origin` remote URL (e.g., `ryanschuhler` from `git@github.com:ryanschuhler/liferay-portal.git`).

Record the current branch name.

Find the remote whose URL contains `liferay-one/liferay-portal` using `git remote -v`. Record its name (expected: `liferay-one` or `team`).

### 2. Find the Open PR

Look up the open PR for the current branch targeting `master-temp`:

```bash
gh pr list \
  --repo liferay-one/liferay-portal \
  --head <github-username>:<branch-name> \
  --base master-temp \
  --state open \
  --json number,title,body,url
```

Abort if no open PR is found. Report: "No open PR found for branch `<branch-name>` targeting master-temp on liferay-one/liferay-portal."

Extract:
- `number` — PR number.
- `title` — PR title; also used to derive the Jira ticket.
- `body` — PR description.
- `url` — link back to this PR.

Extract the Jira ticket ID from the title using the pattern `[A-Z]+-[0-9]+` (e.g., `LRSD-12345`).

### 3. Merge the PR into master-temp

```bash
gh pr merge <number> \
  --repo liferay-one/liferay-portal \
  --merge \
  --delete-branch=false
```

Abort on failure and report the error message.

### 4. Rebase onto liferay-one/master

Fetch the latest master:

```bash
git fetch <liferay-one-remote> master
```

Rebase the current branch onto it:

```bash
git rebase <liferay-one-remote>/master
```

If the rebase fails due to conflicts, run `git rebase --abort`, report the conflicting files, and stop. Do not attempt to resolve conflicts automatically.

### 5. Push to origin

```bash
git push origin <branch-name> --force-with-lease
```

Force-with-lease is required here because the rebase rewrote the branch history. Abort on failure and report the error.

### 6. Create the Forwarding PR

Build the PR body:

```
<original PR body>

---

Jira: https://liferay.atlassian.net/browse/<ticket-id>
Forwarded from: <original-pr-url>
```

Create the PR against `liferay-one/liferay-portal:master`:

```bash
gh pr create \
  --repo liferay-one/liferay-portal \
  --base master \
  --head <github-username>:<branch-name> \
  --title "<original title>" \
  --body "$(cat <<'EOF'
<body>
EOF
)"
```

Record the new PR's number and URL from the command output.

### 7. Trigger CI Forwarding

```bash
gh pr comment <new-pr-number> \
  --repo liferay-one/liferay-portal \
  --body "ci:forward"
```

## Report

Return:
- The forwarding PR URL.
- Confirmation that `ci:forward` was posted — CI will run tests and send to bchan automatically.