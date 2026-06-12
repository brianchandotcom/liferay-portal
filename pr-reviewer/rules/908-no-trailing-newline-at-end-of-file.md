# 908: No Trailing Newline at End of File

Do not end a file with a trailing newline. The Liferay source formatter (`WhitespaceCheck` in `modules/util/source-formatter`) strips a trailing `\n` from every file it processes, so any added newline is rewritten on the next format pass. A diff that shows `\ No newline at end of file` on the final line is in the correct state.

**Rationale:** Stripping the trailing newline is the formatter's standing behavior, and matching it keeps the diff stable across format passes. The `\ No newline at end of file` marker in a unified diff is the expected state, not a defect.

A violation is a diff that introduces a trailing newline at the end of a file; reporting the `\ No newline at end of file` marker as a defect is itself a false positive.