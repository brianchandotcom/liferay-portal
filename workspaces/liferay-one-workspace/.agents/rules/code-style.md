# Code Style

These rules apply to all code in this workspace. Brian Chan enforces them during PR review — violations are rejected or corrected with a follow-up commit.

## Sort Everything

Lists, arrays, and JSON entries must always be in sorted order. This applies to:

- JSON array items in site initializer files (sort by `externalReferenceCode` or `key`)
- `[#assign ... /]` variable blocks in FreeMarker templates (logical dependency order, then alphabetical)
- Java `import` statements (already handled by source formatter)
- Entries in configuration files

When Brian sees items out of order, he comments `"sort"` and bounces the PR back.

## Log Message Conventions

Do not write log statements like AI-generated code. Specifically:

- **Never** use `StringBundler.concat()` inside a `_log.info()` call — this is the "AI slop" pattern Brian rejects
- Log method: use plain string concatenation with `+` for log calls, not `StringBundler`
- Error messages: use `"Unable to <verb>"` not `"Error <verb>ing"` or `"Error: <noun>"`
- Product/object names in log strings: no hyphens — write `"business event"` not `"business-event"`, `"business events"` not `"business-events"`

Example of what Brian corrected:
```java
// Wrong — AI slop
_log.info(StringBundler.concat("POST business-event update: id=", id, ", body=", json));
_log.info("GET business-events for " + externalReferenceCode);
_log.error("Error updating business event " + id);

// Correct
_log.info("POST business event update: id=" + id + ", body=" + json);
_log.info("GET business events for " + externalReferenceCode);
_log.error("Unable to update business event " + id);
```

## User-Facing Text

- Use "IDs" (not "Id", "id", "codes", or other terms) when referring to identifier values shown to users
- Semantic precision matters: "Email" and "Email Address" are different — don't add "Address" if the field is just an email

## FreeMarker Variable Blocks

In FreeMarker templates (`.ftl`, `index.html`), group all `[#assign ... /]` statements together in a single block at the top. Within the block, order variables by dependency (assign prerequisites before their dependents).

```freemarker
[#assign
    currentFriendlyURL = ... /]
[#assign
    currentURL = ... /]
```

→ Both should be in one logical block, with `currentFriendlyURL` before `currentURL` since `currentURL` may depend on it.

## Java Code Ordering

In Java classes, declare resource clients before the objects that use them:

```java
// Wrong
Account account = new Account();
account.setName(...);
AccountResource accountResource = AccountResource.builder()...build();

// Correct
AccountResource accountResource = AccountResource.builder()...build();
Account account = new Account();
account.setName(...);
```
