# 904: Put Each Command Argument on Its Own Line

When a shell command's arguments span multiple lines, put only the command on the first line and each argument on its own line below it. Do not leave some arguments on the command line and the rest below.

**Rationale:** A uniform layout of one argument per line makes a long command readable, makes each argument easy to add, remove, or reorder, and gives a clean diff when one of them changes.

A violation is a multiline command that mixes arguments on the first line with arguments on later lines.
