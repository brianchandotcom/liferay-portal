# 104: Suffix the Type When a Class Owns the Plain Name

When a class already claims the plain name — a `Filter` instance is `filter` — a variable of a different type that holds the same thing takes a type suffix so the two never collide. A `String` that holds a filter expression is `filterString`, not `filter`; likewise a `JSONObject` is `xyzJSONObject` while the `String` of its serialized JSON is `xyzJSON`. The object keeps the plain name, and the other representation carries the suffix that names its type.

**Rationale:** Two values for one concept but of different types often sit side by side, and giving both the plain name forces a rename later or hides which one a line uses. Naming the other representation by its type from the start keeps every reference unambiguous and the pair readable together.

A violation is a variable whose plain name matches a class, or an object of a different type, in the same scope, such as a `String filter` beside a `Filter`, where it should be `filterString`. This refines rule 103: when the plain name is free, use it; when a class owns it, suffix the type.

**Example:** commit `623dd6c` renamed the `String filter` parameter and its `_getFilter` helper to `filterString` and `_getFilterString`, because `filter` is the name for a `Filter` object.
