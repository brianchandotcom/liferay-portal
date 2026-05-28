# 707: Use Complete Sentences in Prose

In prose — comments, documentation, and Javadoc — write complete sentences rather than standalone phrases or fragments. Short labels in list headers or comment dividers are fine, and two contexts have their own forms: UI labels follow rule 702, and log or exception messages follow rule 703.

**Rationale:** A complete sentence carries a subject and a verb, so the reader does not have to reconstruct the missing pieces. A fragment forces the reader to guess what was meant.

A violation is a comment or documentation statement left as a fragment where a complete sentence is meant, outside the label and message exceptions above.
