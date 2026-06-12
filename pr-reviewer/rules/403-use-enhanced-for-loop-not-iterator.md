# 403: Use an Enhanced For Loop Instead of an Explicit Iterator

When you only walk a collection from front to back, use an enhanced `for` loop rather than obtaining an `Iterator` and looping with `while (iterator.hasNext())` and `iterator.next()`.

**Rationale:** The explicit `Iterator` is three moving parts — the `iterator` variable, the `hasNext` test, and the `next` call — for what an enhanced `for` loop expresses in one line. The `for` loop leaves no stray iterator variable to misuse and cannot accidentally call `next` twice in one pass.

A violation is an `Iterator` obtained only to drive a `while (iterator.hasNext())` loop, where an enhanced `for` loop over the collection works.

**Example:** commit `2271c91` replaced a `jsonObject.keys()` `Iterator` and its `while` loop with `for (String key : jsonObject.keySet())`; `3a681cbd` made the same change in another file.