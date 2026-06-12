# 105: Name a Method After What It Actually Does

Name a method for the operation it actually performs, not a convenient paraphrase that overstates or misstates it. When a name is not technically accurate, rename it to match the real work, taking the wording from the operation the body carries out, often the call it delegates to. A helper that creates an account entry user relation associates a user, so `_associateUser` is accurate where `_addUserToAccount` is not, since the method never adds a user to an account.

**Rationale:** A name that misdescribes the work sends the reader to the wrong mental model and hides what the method really changes, which is worse than a plainer but accurate name. Wording the name from the actual operation keeps it honest and traceable to what the method does.

A violation is a method name that does not match what its body does, a convenient or higher level description where a precise one, drawn from the operation the method performs, would be accurate.

**Example:** commit `5b3a53d` renamed the test helper `_addUserToAccount` to `_associateUser`, because the old name was not technically accurate: the method creates an account entry user relation rather than adding a user to an account.