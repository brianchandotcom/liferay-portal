# 405: Prefer Preincrement for Counters in Bash

In a shell script under `set -o errexit`, increment a counter with `((++var))`, not `((var++))` and not `var=$((var + 1))`. The preincrement form returns the new value of `var`, so for a monotonic counter the arithmetic expression is always at least `1` and the exit status of `((...))` is `0`. The postincrement form returns the old value, so when `var` is `0` the expression evaluates to `0`, `((0))` exits with status `1`, and `errexit` kills the script on the first increment. The `var=$((var + 1))` form is safe but noisier than the C style operator.

**Rationale:** Bash's `((...))` arithmetic command sets its exit status to `1` whenever the final expression evaluates to `0`, which collides with the project's `set -o errexit`. Preincrementing keeps the value positive after the first call and the exit status `0`. The same reasoning applies to predecrement when the variable passes through `1` on the way down.

A violation is `((var++))` or `var=$((var + 1))` in a shell script under `set -o errexit`, where `((++var))` would do.
