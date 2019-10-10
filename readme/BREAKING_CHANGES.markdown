# What are the Breaking Changes for Liferay 7.3?

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

*This document has been reviewed through commit ``.*

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

### Liferay FontAwesome Is No Longer Included by Default
- **Date:** 2019-Aug-21
- **JIRA Ticket:** [LPS-100021](https://issues.liferay.com/browse/LPS-100021)

#### What changed?

Previously, Liferay FontAwesome that included icon fonts for Font Awesome,
Glyphicon and custom Liferay icons was included by default. These icon fonts are
no longer included out of the box.

#### Who is affected?

This affects any content or code that relies on such icon fonts in pages or
sites where a theme that does not include the fonts is applied.

#### How should I update my code?

Depending on how you're using icon fonts, you might need to take different
approaches:

##### For liferay-ui:icon usage

Replace `<liferay-ui:icon iconCssClass="icon-user">` with
`<liferay-ui:icon icon="user" markupView="lexicon" />`

##### For JS generated icons

Those manually generating fontawesome icon html, can use the
`Liferay.Util.getLexiconIconTpl('user')` API. For example the previous call
would return the html code for a user svg icon

##### For direct html within jsps

Developers directly using icons in jsps, can either use the `liferay-ui:icon`
tag as explained above or the `clay:icon` one to generate svg-based icons
instead.

##### For non-controlled code

People who don't have access to the content that uses icon fonts or if that
don't want to update those occurrences can reintroduce the icon fonts in their
themes.

During the 7.2 upgrade process, the theme upgrade assistant prompted developers
to keep fontawesome as part of the theme. Themes that already include the icon
fonts won't be affected and will continue to work in 7.3.

#### Why was this change made?

This change was made to avoid serving unnecessary files saving bandwidth and
increasing performance of your sites by default.

---------------------------------------

### Removed liferay.frontend.Slider
- **Date:** 2019-Oct-10
- **JIRA Ticket:** [LPS-100124](https://issues.liferay.com/browse/LPS-100124)

#### What changed?

Liferay Frontend compat Slider has been removed.

#### Who is affected?

This affects any code that relies on such component.

#### How should I update my code?

There's no direct replacement for this. Clay Slider component can be used
instead.

#### Why was this change made?

Liferay Frontend compat Slider component is not used and was already
deprecated in 7.2.

---------------------------------------