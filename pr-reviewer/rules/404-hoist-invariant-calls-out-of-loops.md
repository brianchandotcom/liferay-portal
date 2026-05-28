# 404: Hoist Invariant Calls Out of Loops

Hoist a call out of a loop only when the call is expensive — a database lookup, a network request, a non trivial computation that does measurable work — or when its inline form is making the loop body genuinely hard to read. For a cheap, in scope call such as a `HashMap` `get`, a `List` index access, or a plain getter, prefer leaving it inline at the use site even when its result does not change across iterations.

**Rationale:** Hoisting a cheap call buys nothing at runtime and costs the reader a new name and a longer scope. `_functions.get(column)` written next to its single use, inside the inner loop where `column` is in scope, is the most direct form of the code; a hoisted local or a `TransformUtil.transform(Arrays.asList(columns), _functions::get)` precomputation just to "avoid the call" adds noise the reader then has to map back to the original intent. Hoisting earns its place when the runtime saving is real (the call is expensive) or when the same expression appears so many times in the loop body that naming it once tightens the body's vocabulary.

A violation is an *expensive* invariant call left inside a loop, where lifting it above the loop would remove real work. A simple in scope lookup, a getter, or any constant time access is not a violation when invariant — keep it where it reads.

**Example:** commits `8c616f9` and `4e96176f` ("Avoid calling N times") hoisted invariant calls that were doing real work, not a `HashMap` `get`. The threshold is real work, not mere invariance.
