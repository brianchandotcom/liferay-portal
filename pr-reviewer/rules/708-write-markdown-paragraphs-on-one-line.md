# 708: Write Markdown Paragraphs on One Line

In a Markdown file, put each paragraph on a single line and rely on the editor's soft wrap; do not hard wrap at eighty columns. Separate paragraphs with a blank line.

**Rationale:** A one line paragraph keeps diffs clean, since an edit changes one line rather than reflowing a whole block, and it lets the rendered output decide wrapping rather than the width of the source. This is intentional, and it contradicts most editor defaults.

A violation is a Markdown paragraph hard wrapped across several lines.
