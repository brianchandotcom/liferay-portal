# 706: Capitalize ID in Prose

In prose, write "ID" in uppercase. Reserve lowercase `id` for code contexts — identifier names, JSON keys, URL parameters, and the like. Write "the group ID is `0`", not "the group id is `0`".

**Rationale:** "ID" is an initialism, so prose capitalizes it as it would any other. Lowercase `id` belongs only where it is a literal token in code, such as the `companyId` field or a `?id=` parameter.

A violation is a lowercase "id" used as a word in prose rather than as a code token.
