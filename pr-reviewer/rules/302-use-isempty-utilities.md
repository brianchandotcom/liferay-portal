# 302: Use isEmpty Utilities for Empty Checks

When testing whether a collection, map, or array is null or empty, use the matching utility — `ListUtil.isEmpty`, `SetUtil.isEmpty`, `MapUtil.isEmpty`, `ArrayUtil.isEmpty` — rather than writing `x == null || x.isEmpty()` or `x == null || x.length == 0` by hand.

**Rationale:** The manual null and emptiness check is verbose, repeated across the codebase, and easy to get subtly wrong by testing one condition and forgetting the other. The utility states the intent in a single call and handles both conditions the same way every time.

A violation is a hand written `x == null || x.isEmpty()`, `x == null || x.length == 0`, or the negated `x != null && !x.isEmpty()`, where the corresponding `isEmpty` utility applies.

**Example:** commit `8faff8b` replaced `x == null || x.isEmpty()` with `SetUtil.isEmpty(x)`; `505c255` used `ListUtil.isEmpty`; `ad06c51` replaced `items == null || items.length == 0` with `ArrayUtil.isEmpty(items)`.
