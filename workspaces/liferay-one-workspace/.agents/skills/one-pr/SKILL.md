---

allowed-tools: [Bash, Glob, Grep, Read]
description: Create a GitHub pull request for the current branch, transition the corresponding Jira ticket to review, and record the PR link on the ticket. Use when the user asks to create a PR, send a PR, or invokes /pr.
name: one-pr

---

# Create a Pull Request

Create a GitHub pull request against `liferay-one/liferay-portal`, transition the linked Jira ticket to review, and record the pull request URL on that ticket.

## Preconditions

- The current branch is a development branch, not `master` or any other protected branch.

- The working tree has no uncommitted changes. When dirty, abort and ask the user to commit first (suggest `/commit`); do not stash or discard their work.

## Pre-flight Checks

Before creating the PR, verify these Brian-enforced requirements:

**Scope:** Run `git diff liferay-one/master-temp --name-only` and confirm all changed files belong to the workspace for this ticket. If files from another workspace (e.g., `clarity-solution-workspace`) appear, abort and ask the user to remove them.

**Commit messages:** Confirm every commit on the branch has a valid Jira ticket prefix (`LPD-`, `LRSD-`, `LCD-`, etc.). The CI bot auto-closes PRs missing a ticket reference.

**Merge conflicts:** Confirm the branch is rebased on top of `liferay-one/master-temp` with no conflicts. Run `git merge-base --is-ancestor liferay-one/master-temp HEAD` — if the branch is behind, offer to run `/one-rebase` first.

## Input

### Branch

The current Git branch must contain the commits ready to ship.

### Jira Ticket

Resolve a ticket key in priority order:

1. **Branch Name** — extract the ticket from the current branch (e.g., branch `LRSD-12299` yields `LRSD-12299`).

1. **Recent Commits** — when the branch name yields nothing, scan recent commit messages for a ticket prefix.

1. **Fallback** — when nothing surfaces, prompt the user.

The ticket key follows the pattern `LPD-12345` or similar (uppercase letters, hyphen, digits).

### Target Repository

Always `liferay-one/liferay-portal`. The pull request head is `<github-username>:<branch-name>` (read the GitHub username from the user's `origin` remote URL) and the base is `master-temp`.

## Expected Output

### Pushed Branch

Push the current branch to the user's remote when it has not been pushed yet or when new local commits exist.

### Pull Request

The title is concise (under 72 characters) and prefixed with the Jira ticket:

```
LPD-12345 Fix something in liferay-one
```

The body follows this format:

```markdown
https://liferay.atlassian.net/browse/TICKET-ID

## What is being fixed

Explain the problem or bug that motivated the change — what was going
wrong or what was missing.

## How it is being fixed

Explain the approach taken across all commits. Describe the key changes
and the reasoning behind the approach. Write in plain prose rather than
bullet points.
```

Present the proposed title and body to the user before submitting, and proceed once they approve.

### Transitioned Jira Ticket

Fetch the input ticket (issue type, status, subtasks) and resolve the **target ticket** — the one whose status reflects active work and on which the pull request URL is recorded:

| Ticket Type | Target |
| --- | --- |
| Bug (`10004`) | The bug itself |
| Task (`10002`) | Its Technical Task (`10153`) subtask |
| Technical Task (`10153`) | Itself |

When the target is not already in an in-progress status, transition it first:

| Target Type | Destination | Transition ID |
| --- | --- | --- |
| Bug | In Progress | `61` |
| Technical Task | In Progress | `41` |

Then transition it to review:

| Target Type | Destination | Transition ID |
| --- | --- | --- |
| Bug | In Review | `71` |
| Technical Task | In Peer Review | `31` |

When the review transition fails (for example, because the ticket is already in a later status), still proceed to record the pull request URL.

Set the **Git Pull Request** field (`customfield_10201`) on the target ticket to the new pull request URL.

### Summary

Report back to the user with:

- The Jira ticket status and link.
- The pull request URL.
