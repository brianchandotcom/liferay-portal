# PR Hygiene

These rules come directly from Brian's PR feedback. Violations result in immediate rejection.

## Scope: One Workspace, One PR

A PR must not touch files from a workspace other than the one the ticket describes. Brian explicitly rejected a PR for including `clarity-solution-workspace` changes in what should have been a `liferay-one-workspace`-only PR.

Before sending a PR, verify:

```bash
git diff origin/master-temp --name-only
```

If any files outside the intended workspace appear, remove them before sending.

## No Merge Conflicts

Never send a PR that has merge conflicts. Brian immediately closes them with the comment "conflicts". Rebase or merge first, verify the branch is clean, then resend.

## Reference Upstream Merges

When your work depends on a PR that was already merged upstream by Brian, your PR must include or reference those merged commits. If Brian merged a dependency, rebase onto the latest `liferay-one/master-temp` before sending your PR so the upstream commits are included.

## Commit Messages Must Have a Jira Ticket

Every commit message must reference a valid Jira ticket (LPD-12345, LRSD-12345, etc.). The CI bot automatically closes PRs where any commit is missing this reference.

Valid prefixes: `BLADE`, `CLDSVCS`, `COMMERCE`, `IDE`, `ISOPS`, `ISSD`, `ISSUP`, `LCD`, `LOOP`, `LPD`, `LPS`, `LRAC`, `LRCI`, `LRDOCS`, `LRIS`, `LRQA`, `LRSD`, `OAUTH2`, `POSHI`, `RELEASE`, `SYNC`, `TR`.

## One Ticket Per PR

PRs should address a single Jira ticket. If your fix actually addresses a different problem from the stated ticket, use the correct ticket or create a new one.
