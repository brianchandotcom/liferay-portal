# 102: Name Boolean Methods With Is or Has

Name a boolean method with an `is` or `has` prefix, and prefer `has` over `contains` or `exists` for a method that tests presence. Write `isShowPreviewDiff()`, `hasPortlet()`, and `hasContent()`, not `showPreviewDiff()`, `existsPortlet()`, or `containsContent()`. This governs methods you name; it does not rename standard library methods such as `Collection.contains`.

A boolean variable or field does not take the prefix. A field holds a value, not a question, so write `boolean quotaExceeded` or `AtomicBoolean quotaExceeded`, never `isQuotaExceeded`; the `is` form belongs to the method that exposes it, `isQuotaExceeded()`.

**Rationale:** An `is` or `has` prefix makes a method call read as a question at the point of use (`if (hasContent())`), separating it from a method that returns a value or performs an action. A variable is already a value in hand, so the same prefix only adds noise and invites confusion with a method.

A violation is a boolean method you define whose name lacks an `is` or `has` prefix, a presence test named `exists...` or `contains...` rather than `has...`, or a boolean variable or field that carries an `is` or `has` prefix it should not.

**Example:** commit `56e0246` renamed `showPreviewDiff` to `isShowPreviewDiff`; `68c6983` renamed `_existsPortlet` to `_hasPortlet`; `5991762` renamed `_containsContent` to `_hasContent`.