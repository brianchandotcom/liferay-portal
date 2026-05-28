# 301: Prefer GetterUtil for Type Coercion

When converting a value of unknown or nullable form to a primitive or wrapper, use the matching `GetterUtil` method (`GetterUtil.getLong`, `getBoolean`, `getInteger`, `getString`, and so on) rather than a raw `parse` or `valueOf` call. `GetterUtil` is null safe and falls back to a sensible default instead of throwing.

**Rationale:** `Long.valueOf(x)`, or `Boolean.parseBoolean(String.valueOf(x))`, throws or misbehaves when the input is null or malformed, so each call site has to guard against it. `GetterUtil.getLong(x)` returns a default for bad input, so the coercion is safe and reads as a single call. Using it everywhere keeps coercion uniform and removes the scattered guards.

A violation is a `parseLong`, `parseInt`, `parseBoolean`, `valueOf`, or similar raw conversion where the corresponding `GetterUtil` method should be used.

**Example:** commit `dbf917f` replaced `Long.valueOf(...)` with `GetterUtil.getLong(...)`; `eaefa29` replaced `Boolean.parseBoolean(String.valueOf(...))` with `GetterUtil.getBoolean(...)`; `9c33536` replaced `Long::parseLong` with `GetterUtil::getLong`.
