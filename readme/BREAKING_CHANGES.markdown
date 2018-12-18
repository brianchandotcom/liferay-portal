# What are the Breaking Changes for Liferay 7.2?

This document presents a chronological list of changes that break existing
functionality, APIs, or contracts with third party Liferay developers or users.
We try our best to minimize these disruptions, but sometimes they are
unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like
  `portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain
  feature or API will be dropped in an upcoming version.
* Recommendations: For example, recommending using a newly introduced API that
  replaces an old API, in spite of the old API being kept in Liferay Portal for
  backwards compatibility.

*This document has been reviewed through commit `67429b52ebbd`.*

## Breaking Changes Contribution Guidelines

Each change must have a brief descriptive title and contain the following
information:

* **[Title]** Provide a brief descriptive title. Use past tense and follow
  the capitalization rules from
  <http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles>.
* **Date:** Specify the date you submitted the change. Format the date as
  *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **JIRA Ticket:** Reference the related JIRA ticket (e.g., LPS-12345)
  (Optional).
* **What changed?** Identify the affected component and the type of change that
  was made.
* **Who is affected?** Are end-users affected? Are developers affected? If the
  only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If
  applicable, justify why the breaking change was made instead of following a
  deprecation process.

Here's the template to use for each breaking change (note how it ends with a
horizontal rule):

```
### Title
- **Date:**
- **JIRA Ticket:**

#### What changed?

#### Who is affected?

#### How should I update my code?

#### Why was this change made?

---------------------------------------
```

**80 Columns Rule:** Text should not exceed 80 columns. Keeping text within 80
columns makes it easier to see the changes made between different versions of
the document. Titles, links, and tables are exempt from this rule. Code samples
must follow the column rules specified in Liferay's
[Development Style](http://www.liferay.com/community/wiki/-/wiki/Main/Liferay+development+style).

The remaining content of this document consists of the breaking changes listed
in ascending chronological order.

## Breaking Changes List

### Removed Support for JSP Templates in Themes
- **Date:** 2018-Nov-14
- **JIRA Ticket:** [LPS-87064](https://issues.liferay.com/browse/LPS-87064)

#### What changed?

Themes can no longer leverage JSP templates. Also, related logic has been
removed from the public APIs `com.liferay.portal.kernel.util.ThemeHelper` and
`com.liferay.taglib.util.ThemeUtil`.

#### Who is affected?

This affects anyone who has themes using JSP templates or is using the removed
methods.

#### How should I update my code?

If you have a theme using JSP templates, consider migrating it to FreeMarker.

#### Why was this change made?

JSP is not a real template engine and is rarely used. FreeMarker is the
recommended template engine moving forward.

The removal of JSP templates allows for an increased focus on existing and new
template engines.

---------------------------------------

### Lodash Is No Longer Included by Default
- **Date:** 2018-Nov-27
- **JIRA Ticket:** [LPS-87677](https://issues.liferay.com/browse/LPS-87677)

#### What changed?

Previously, Lodash was included in every page by default and made available
through the global `window._` and scoped `AUI._` variables. Lodash is no longer
included by default and those variables are now undefined.

#### Who is affected?

This affects any developer who used the `AUI._` or `window._` variables in their
custom scripts.

#### How should I update my code?

You should provide your own Lodash version for your custom developments to use
following any of the possible strategies to add third party libraries.

As a temporary measure, you can bring back the old behavior by setting the
*Enable Lodash* property in *System Settings* &rarr; *Third Party* &rarr;
*Lodash* to `true`.

#### Why was this change made?

This change was made to avoid bundling and serving additional library code on
every page that was mostly unused and redundant.

---------------------------------------

### Remove Link Application URLs to Page Functionality
- **Date:** 2018-Dec-14
- **JIRA Ticket:** [LPS-85948](https://issues.liferay.com/browse/LPS-85948)

#### What changed?

The *Link Portlet URLs to Page* option in the Look and Feel portlet was marked
as deprecated in Liferay Portal 7.1, allowing the user to show and hide the
option through a configuration property. In Liferay Portal 7.2, this has been
removed and can no longer be configured.

#### Who is affected?

This affects administrators who used the option in the UI and developers who
leveraged the option in the portlet.

#### How should I update my code?

You should update any portlets leveraging this feature, since any preconfigured
reference to the property is ignored in the portal.

#### Why was this change made?

A limited number of portlets use this property; there are better ways to achieve
the same results.

---------------------------------------