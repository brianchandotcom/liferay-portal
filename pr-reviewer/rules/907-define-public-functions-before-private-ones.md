# 907: Define Public Functions Before Private Ones

Order functions by visibility, public before private, sorted alphabetically within each group. In a bash script `main` and any function with no leading underscore are public and come first; underscore prefixed functions such as `_fetch_pr` and `_review_in_sandbox` are private and come after, sorted among themselves. This refines rule 202: functions group by visibility, then sort alphabetically within each group.

**Rationale:** A reader meets the entry point and the public surface first, then descends into the helpers, the same top down order a Java class gives with its public methods above its private ones. A pure alphabetical sort would bury `main` among the helpers, since an underscore sorts before a letter.

A violation is a private function defined before a public one, or either group left out of alphabetical order within itself.
