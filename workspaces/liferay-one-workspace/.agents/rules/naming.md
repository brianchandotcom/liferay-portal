# Naming Conventions

## Brand Names

Always use the exact official casing for brand and product names. Brian specifically corrected these:

| Wrong | Correct |
| --- | --- |
| `argoCD` | `ArgoCD` |
| `argoWorkflows` | `Argo Workflows` |
| `grafana` | `Grafana` |
| `koroneiki` | `Koroneiki` |

This applies everywhere: UI labels, log messages, configuration values, and comments.

## File Naming: SVG and CSS

SVG and CSS files use underscores (`_`), not hyphens (`-`):

```
# Wrong
hero-banner.svg
service-card.css

# Correct
hero_banner.svg
service_card.css
```

## REST Controller Endpoints

REST controller mapping paths and method names must be "very robotic" — path segments map directly and mechanically to the method name, one-to-one. Follow the established pattern in the existing controller:

```java
// Pattern: @GetMapping("/security-vulnerabilities/affected-versions")
//          → getSecurityVulnerabilitiesAffectedVersions()

@GetMapping("/business-events")
public ResponseEntity<String> getBusinessEvents() { ... }

@GetMapping("/business-events/{id}")
public ResponseEntity<String> getBusinessEventsById(@PathVariable String id) { ... }
```

Each URL path word becomes a camelCase word in the method name. Do not abbreviate, rephrase, or add words that are not in the URL.
