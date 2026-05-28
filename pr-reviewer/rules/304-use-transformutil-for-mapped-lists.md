# 304: Use TransformUtil to Build a Mapped List

When building a list by looping over a collection and adding a mapped element to a new `ArrayList`, use `TransformUtil.transform(collection, item -> ...)`, or `transformToList`, instead.

**Rationale:** The loop and its throwaway `ArrayList` spread a simple mapping across several lines and an extra mutable variable. `TransformUtil.transform` states the mapping in one expression, and a `null` returned from the mapper is skipped, which removes the manual guard that would otherwise wrap each element.

A violation is a `for` loop that creates a new list and adds one mapped element per iteration, where `TransformUtil.transform` or `transformToList` applies.

**Example:** commits `d45c429`, `89132e5`, and `f05d6ba` each replaced a `new ArrayList` and a populating loop with `TransformUtil.transform`; `875cb0f` returned `null` from the mapper to skip an element instead of using `continue`.
