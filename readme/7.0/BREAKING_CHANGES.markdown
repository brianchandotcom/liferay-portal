# What are the Breaking Changes for Liferay 7.0?

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
 portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain
feature or API will be dropped in an upcoming version.
* Recommendations: For example, recommending using a newly introduced API that
replaces an old API, in spite of the old API being kept in Liferay Portal for
backwards compatibility.

## Breaking Changes

Each change must have a brief descriptive title and contain the following
information:

* **Date:** Specify the date you submitted the change. Format the date as
*YYYY-MMM* (e.g. 2014-Mar) or *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **Jira Ticket:** Reference the related Jira ticket (e.g., LPS-123456)
(Optional).
* **What changed?** Identify the affected component and the type of change that
was made.
* **Who is affected?** Are end users affected? Are developers affected? If the
only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If
applicable, justify why the breaking change was made instead of following a
deprecation process.

Here's the template to use for each breaking change (note how it ends with a horizontal rule):

```
### [Title]
* Date:
* Jira Ticket:

#### What changed?

#### Who is affected?

#### How should I update my code?

#### Why was this change made?

---------------------------------------
```
---------------------------------------

### Removal of Methods get and format which use the PortletConfig in LanguageUtil and UnicodeLanguageUtil
* Date: 7th March 2014
* Jira Ticket: LPS-44342

#### What changed?
All the methods get() and format() which had the PortletConfig as a parameter
have been removed.

#### Who is affected?
Any invocations from Java classes or JSPs to these methods in LanguageUtil and
UnicodeLanguageUtil

#### How should I update my code?
Replace the invocation to those methods with the ones with the same name that
take ResourceBundle as a parameter instead.

**Example**

Replace:
```
LanguageUtil.get(portletConfig, locale, key);
```

With:
```
LanguageUtil.get(portletConfig.getResourceBundle(locale), key);
```

#### Why was this change made?
The removed methods didn't work properly and they would never do since they
didn't have all the information required in order to work. Since we expect them
to be rarely used we considered it was better to remove them without deprecation
than to leave buggy methods in the API.

---------------------------------------
