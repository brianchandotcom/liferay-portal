# 903: Use Long Form CLI Flags, Sorted

In a shell command, use the long form of each flag (`--data`, not `-d`; `--silent`, not `-s`) and list the flags in alphabetical order.

**Rationale:** Long forms are self documenting and avoid the short flag collisions that differ from one tool to the next. Alphabetical order makes a command scannable and easy to compare against another.

A violation is a short flag where a long form exists, or a set of flags left out of alphabetical order.
