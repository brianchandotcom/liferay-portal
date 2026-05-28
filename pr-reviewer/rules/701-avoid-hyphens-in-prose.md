# 701: Avoid Hyphens in Prose

Do not hyphenate words in prose — labels, messages, documentation, comments, and any other user facing or developer facing text. Remove the hyphen: close up a prefix (`multiarg`, not `multi-arg`; `nonoverlapping`, not `non-overlapping`) and separate a true compound into its words (`long running`, not `long-running`; `At Risk`, not `At-Risk`).

**Rationale:** Whether a given compound takes a hyphen is a fussy and subjective question that produces endless small inconsistencies across a large codebase. Avoiding hyphens entirely removes that churn, and the text reads cleanly with prefixes closed up and compounds written as separate words. This is the Liferay convention, and it is intentionally stricter than ordinary English prose, which hyphenates many compound modifiers.

A violation is a hyphenated prefix or compound modifier in prose, such as `long-running`, `multi-method`, `self-harm`, or `At-Risk`. It does not apply to code identifiers, command line flags, or numeric ranges, where the hyphen is part of the token.

**Example:** commit `4da1390` changed `At-Risk Accounts` to `At Risk Accounts` in a language key; `89026b3` changed `long-running` to `long running` in a properties comment; `8138671` changed `multi-arg` to `multiarg` in documentation.
