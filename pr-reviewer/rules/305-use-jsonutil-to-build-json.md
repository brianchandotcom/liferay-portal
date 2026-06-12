# 305: Use JSONUtil to Build JSON

Assemble JSON with the `JSONUtil` helpers (`JSONUtil.put`, `JSONUtil.toJSONArray`, `JSONUtil.toList`, and so on) rather than injecting a `JSONFactory` and building `JSONObject` and `JSONArray` instances by hand.

**Rationale:** `JSONUtil` builds the same structures in a single fluent expression and removes the need to inject and reference a `JSONFactory`, so the construction reads as data rather than as a sequence of `put` calls on a mutable object.

A violation is JSON assembled by injecting `JSONFactory`, or by creating and populating `JSONObject` or `JSONArray` by hand, where the `JSONUtil` helpers apply.

**Example:** commit `4705fc4` replaced `_jsonFactory.createJSONObject().put(...)` with `JSONUtil.put(...)` and dropped the `@Reference JSONFactory`; `c142075` replaced manual population with `JSONUtil.toList`; `20ad3779` replaced a hand built `JSONArray` with `JSONUtil.toJSONArray`.