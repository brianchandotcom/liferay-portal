# 903: Use Long Form CLI Flags, Sorted

In a shell command, use the long form of each flag (`--data`, not `-d`; `--silent`, not `-s`) and list the flags in alphabetical order.

**Rationale:** Long forms are self documenting and avoid the short flag collisions that differ from one tool to the next. Alphabetical order makes a command scannable and easy to compare against another.

**Exception (BSD and macOS portability):** Some tools have no portable long form because the BSD utilities on macOS reject the GNU long options. For these, use the short form on both platforms so one invocation runs everywhere: `mkdir -p` (no `--parents`), `pkill -KILL -f` (no `--full` or `--signal=`), `rm -f` (no `--force`), and `sed -i`, `sed -e`, `sed -E` (no `--in-place`, `--expression`, `--regexp-extended`). When such a tool fixes the order of its short flags, that required order wins over alphabetical: `pkill` takes the signal first, so `-KILL -f`, not `-f -KILL`.

A violation is a short flag where a portable long form exists, or a set of flags left out of alphabetical order. A short flag from the portability list above is not a violation.