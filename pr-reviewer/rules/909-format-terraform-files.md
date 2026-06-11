# 909: Format Terraform Files

Indent Terraform with tabs, one tab per level, and write no space on either side of the `=` in an argument or a nested assignment: `region=var.region`, `type=string`, `tags={`. This matches the Liferay source formatter style the rest of the repository follows and departs from the Terraform ecosystem default of two space indentation with a padded `=`.

Do not run `terraform fmt`. It rewrites every file to that ecosystem default — spaces around `=` and space based indentation — and so fights the committed style across the whole tree. Format by hand to the convention above, and leave the existing formatting in place.

**Rationale:** A single whitespace convention across Terraform, shell, and the rest of the codebase means a reader never reorients between files, and a tight `=` keeps an argument list dense and scannable. Letting `terraform fmt` reformat would churn every line it touches, bury the real change in a diff, and split the tree between two conventions.

A violation is a Terraform file indented with spaces, an argument with a space before or after its `=`, or a diff that shows `terraform fmt` reformatting unrelated lines.