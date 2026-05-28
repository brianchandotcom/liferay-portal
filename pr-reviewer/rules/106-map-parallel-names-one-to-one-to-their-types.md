# 106: Map Parallel Names One to One to Their Types

When a set of names runs parallel to a set of types, mirror each type name exactly so the mapping is one to one. Do not add a word to some names that has no counterpart in the type. A method that throws `NoSuchModelException` is `noSuchModelException`, reached by the path `no-such-model-exception`; one that throws `PrincipalException` is `principalException` with `principal-exception`, not `principalDenied` and `principal-denied`. The stray "denied" sits in two of the three names but in none of the exceptions, so the set no longer lines up.

**Rationale:** Parallel names that map straight onto their types let the reader pair each one with its target at a glance and spot a missing or wrong case at once. A qualifier present on some members but absent from the type it names breaks the symmetry and hides which name goes with which.

A violation is a member of a parallel set whose name does not mirror its type, most often an extra word on some names that the type does not carry, where matching the type name exactly would keep the set one to one.

**Example:** commit `d39aa5a` renamed `noSuchModel`, `principalDenied`, and `securityDenied` to `noSuchModelException`, `principalException`, and `securityException`, along with their paths, so each name maps one to one to the exception it represents.
