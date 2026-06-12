# 703: Log and Exception Message Form

The one thing to avoid in a log or exception message is a single complete sentence that ends with a period. The trailing period on a lone sentence is the problem, not the wording.

These are all correct and must not be flagged:

- A phrase or a single clause with no ending period, such as `PageSpeed scan completed`, `Scan is missing a domain`, or `Unable to start the scan`. A missing period here is correct, never a violation.
- A title, such as `Quota Exceeded`.
- Two or more sentences, each ending with a period, such as `Quota exceeded. Try again later.`

Only a lone `This is a sentence.` needs fixing: drop the period to make it a phrase, or add a second sentence.

**Rationale:** A line with no trailing period reads as a label, and two or more sentences read as a paragraph. The single sentence with one trailing period is the awkward middle, so it is the only form to avoid.

Per rule 001, if a class's existing messages already follow a different form, match the class rather than this rule.

A violation is exactly one thing: a single complete sentence that ends with a period. A message without a trailing period is never a violation, even if it reads as a full sentence.