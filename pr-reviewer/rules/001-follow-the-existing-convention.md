# 001: Follow the Existing Convention

When the code already in a file or class follows a consistent convention, follow it — even where that convention departs from a rule in this set. The same holds across the codebase: when an identifier is already used widely, follow the existing name rather than introduce a new variant. These rules are the default for new code and for files that have no established pattern; they never override the established style of existing code.

**Rationale:** This codebase is twenty years of evolved code. Forcing a rule onto a file that consistently does otherwise trades a small local inconsistency for a larger one, and it churns code that was already fine. You do not widen the streets of London to match a planned suburb; you respect the layout that is already there. Consistency within a file outranks conformance to the global default.

A violation is a change that breaks from the convention its surrounding code already follows, or that introduces a second, conflicting pattern into a file that had one. Code that follows its file's established convention is not a violation, even when that convention differs from another rule here.

**Example:** commit `a75a9e7` kept the established `jxPortletPreferences` name instead of introducing a new `jakartaPortletPreferences` variant — `git grep` shows many references to the established name and would have left the new one as the lone case.