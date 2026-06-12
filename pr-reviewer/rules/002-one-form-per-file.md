# 002: One Form Per File

When the file you are touching contains two or more forms for the same kind of call — for example one `System.out.println` written as a phrase and another written as a complete sentence with a period — pick the form that complies with the other rules here (typically rule 703 for log and output messages) and apply it across every similar call in the file. Do not add a new call in either form while leaving the mixed forms in place.

**Rationale:** Rule 001 tells you to follow the existing convention when there is one. When the file already has a split convention, there is nothing single to follow, and adding to either side compounds the chaos. Picking one form and applying it across the file restores a single convention and saves the next reader from asking which one is right.

A violation is a diff that adds a similar call — a console print, log statement, exception message, or other repeated pattern — in a form that differs from at least one existing call in the same file, without normalizing the existing ones to the chosen form.

**Example:** https://github.com/brianchandotcom/liferay-portal/pull/175556 — the file already had `System.out.println("No violations were found.")` (a complete sentence with a period) and the PR added `System.out.println("Scanning translations of " + ...)` (a phrase, no trailing period). Both forms ended up in the same file; the PR should have removed the trailing period from the existing call so only the phrase form remained.