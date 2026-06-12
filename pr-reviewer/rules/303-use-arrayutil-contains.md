# 303: Use ArrayUtil.contains for Array Membership

To test whether an array contains a value, use `ArrayUtil.contains(array, value)` rather than wrapping the array in a list first, as in `Arrays.asList(array).contains(value)`.

**Rationale:** `Arrays.asList(array).contains(value)` allocates a list wrapper only to discard it, and it reads as two operations. `ArrayUtil.contains` says exactly what is meant in one call with no allocation.

A violation is `Arrays.asList(array).contains(value)`, or an equivalent hand written loop, where `ArrayUtil.contains` applies.

**Example:** commit `4ae44ca` replaced `Arrays.asList(arr).contains(x)` with `ArrayUtil.contains(arr, x)`.