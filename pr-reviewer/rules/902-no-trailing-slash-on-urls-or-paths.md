# 902: No Trailing Slash on URLs or Paths

Do not end a URL or a path with a trailing slash, in code or in prose. Write `https://example.com/path`, not `https://example.com/path/`; `/usr/local/bin`, not `/usr/local/bin/`; `modules/apps`, not `modules/apps/`.

**Rationale:** The trailing slash is redundant, it is applied inconsistently across a codebase, and it changes how some tools resolve the path. Dropping it everywhere makes URLs and paths compare equal and read uniformly.

A violation is a URL or path written with a trailing slash.