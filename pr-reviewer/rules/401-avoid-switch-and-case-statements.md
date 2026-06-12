# 401: Avoid Switch and Case Statements

Do not use a `switch` statement, or the shell `case` construct, in any language. Express the branching with an `if` / `else if` / `else` chain instead.

**Rationale:** An `if` / `else if` / `else` chain expresses everything a `switch` can, and also handles conditions a `switch` cannot — ranges, compound tests, and arbitrary expressions — so the `switch` construct is redundant. It also carries language specific hazards: silent fall through when a `break` is forgotten in C family languages, and a separate pattern syntax with `;;` terminators in the shell. Standardizing on `if` removes a second, error prone branching form and keeps every branch in the codebase written the same way.

A violation is any `switch` statement or shell `case` block in the diff, in any language, however few branches it has.

**Example:** https://github.com/brianchandotcom/liferay-portal/pull/175440 — in `wait-for-elasticsearch.sh`, a `case "${SEARCH_URL}" in https://*) protocol="https" ;; esac` block sets `protocol` to `https` when the URL scheme is HTTPS. It should be an `if` statement testing `[[ ${SEARCH_URL} == https://* ]]`.