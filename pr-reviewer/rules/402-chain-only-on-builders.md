# 402: Chain Only on Builders

Do not chain method calls unless the receiver is a builder being assembled, or a fluent type whose methods are designed to be chained. When a call returns an object outside that set and you would immediately call another method on that result, assign the result to a named local variable and call the next method on the variable instead.

The fluent types are an explicit allow list: `*Builder`, `Stream`, `Optional`, `Instant`, `JSONArray`, `JSONObject`, and the rest of the list maintained by `ChainingCheck` in `modules/util/source-formatter`. Chaining on any of these is the intended use — `Instant.now().truncatedTo(ChronoUnit.SECONDS).toString()` is allowed; `collection.iterator().next()` and `foo.getBar().getBaz()` are not.

**Rationale:** A chain such as `foo.getBar().getBaz()` hides the intermediate types and values, gives a debugger nothing to inspect between steps, and leaves a stack trace with no single line to blame. A named local documents what each call returns and makes each step observable. A builder is the exception: its calls each return the same builder, so the chain is one construction rather than a sequence of separate operations.

A violation is a method call invoked directly on the value returned by another method call, where the receiver is not a builder or a fluent type from the allow list above — for example `collection.iterator().next()` or `foo.getBar().getBaz()`.

**Example:** commit `261f7566` split `sections.iterator().next()` into an `Iterator` local followed by a `next()` call; `0d04ca4` ("Don't chain here, it's not a builder") and `e9232df` (assign a `JournalFolderFixture` to a local before calling `addFolder` on it) are the same fix.
