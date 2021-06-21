# What are the Breaking Changes for Liferay 7.4?

This document presents a chronological list of changes that break existing functionality, APIs, or contracts with third party Liferay developers or users. We try our best to minimize these disruptions, but sometimes they are unavoidable.

Here are some of the types of changes documented in this file:

* Functionality that is removed or replaced
* API incompatibilities: Changes to public Java or JavaScript APIs
* Changes to context variables available to templates
* Changes in CSS classes available to Liferay themes and portlets
* Configuration changes: Changes in configuration files, like `portal.properties`, `system.properties`, etc.
* Execution requirements: Java version, J2EE Version, browser versions, etc.
* Deprecations or end of support: For example, warning that a certain feature or API will be dropped in an upcoming version.

*This document has been reviewed through commit `4334fc6cc349`.*

## Breaking Changes Contribution Guidelines

Each change must have a brief descriptive title and contain the following information:

* **[Title]** Provide a brief descriptive title. Use past tense and follow the capitalization rules from <http://en.wikibooks.org/wiki/Basic_Book_Design/Capitalizing_Words_in_Titles>.
* **Date:** Specify the date you submitted the change. Format the date as *YYYY-MMM-DD* (e.g., 2014-Feb-25).
* **JIRA Ticket:** Reference the related JIRA ticket (e.g., LPS-12345) (Optional).
* **What changed?** Identify the affected component and the type of change that was made.
* **Who is affected?** Are end-users affected? Are developers affected? If the only affected people are those using a certain feature or API, say so.
* **How should I update my code?** Explain any client code changes required.
* **Why was this change made?** Explain the reason for the change. If applicable, justify why the breaking change was made instead of following a deprecation process.

Here is the template to use for each breaking change (note how it ends with a horizontal rule):

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

The remaining content of this document consists of the breaking changes listed in ascending chronological order.

## Breaking Changes List

### Removed the liferay-ui:flash Tag
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121732](https://issues.liferay.com/browse/LPS-121732)

#### What changed?

The tag `liferay-ui:flash` has been deleted and is no longer available.

#### Who is affected?

This affects any development that uses the `liferay-ui:flash` tag.

#### How should I update my code?

If you still need to embed Adobe Flash content in a page, write your own code using a standard mechanism such as `SWFObject`.

#### Why was this change made?

This change aligns with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html) in December 31, 2020 and with upcoming versions browsers removing Flash support.

---------------------------------------

### Removed the /portal/flash Path
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121733](https://issues.liferay.com/browse/LPS-121733)

#### What changed?

Previously you could play an Adobe Flash movie by passing a movie URL as a parameter to the `/portal/flash` public path. The `/portal/flash path` path has been removed.

Additionally, the property and accessors have been removed from `ThemeDisplay` and are no longer available.

#### Who is affected?

This affects you if you are using the `/c/portal/flash` path directly to show pages with Adobe Flash content.

#### How should I update my code?

A direct code update is impossible. However, you can create a custom page that simulates the old behavior. The page could parse movie parameters from the URL and instantiate the movie using the common means for Adobe Flash reproduction.

#### Why was this change made?

This change aligns with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html) in December 31, 2020 and with upcoming versions browsers removing Flash support.

---------------------------------------

### Removed the swfobject AUI Module
- **Date:** 2020-Oct-13
- **JIRA Ticket:** [LPS-121736](https://issues.liferay.com/browse/LPS-121736)

#### What changed?

The AUI module `swfobject` has been removed. It provided a way to load the SWFObject library, commonly used to embed Adobe Flash content.

#### Who is affected?

This affects you if you are making the SWFObject library available globally via the AUI `swfobject` module.

#### How should I update my code?

If you still need to embed Adobe Flash content, you can inject the SWFObject library directly in your application using other available mechanisms.

#### Why was this change made?

This change aligns with [Adobe dropping support for Flash](https://www.adobe.com/products/flashplayer/end-of-life.html) in December 31, 2020 and with upcoming versions browsers removing Flash support.

---------------------------------------

### Refactored AntivirusScanner Support and Clamd Integration
- **Date:** 2020-Oct-21
- **JIRA Ticket:** [LPS-122280](https://issues.liferay.com/browse/LPS-122280)

#### What changed?

The portal's Clamd integration implementation has been replaced by an OSGi service that uses a Clamd remote service. The AntivirusScanner OSGi integration has replaced the AntivirusScanner implementation selection portal properties and AntivirusScanner implementation hook registration portal properties.

#### Who is affected?

This affects you if you are using the portal's Clamd integration implementation or if you are providing your own AntivirusScanner implementation via a hook.

#### How should I update my code?

Enable the new Clamd integration and AntivirusScanner support. See [Enabling Antivirus Scanning for Uploaded Files](https://learn.liferay.com/dxp/latest/en/system-administration/file-storage/enabling-antivirus-scanning-for-uploaded-files.html).

If you are providing your own AntivirusScanner implementation via a hook, convert it to an OSGi service that has a service ranking higher greater than zero. The Clamd remote service AntivirusScanner implementation service ranking is zero.

#### Why was this change made?

This change supports container environments better and unifies the API to do OSGi integration.

---------------------------------------

### Removed the AssetEntries_AssetCategories Table and Corresponding Code
- **Date:** 2020-Oct-16
- **JIRA Ticket:** [LPS-89065](https://issues.liferay.com/browse/LPS-89065)

#### What changed?

The `AssetEntries_AssetCategories` mapping table and its corresponding code have been removed.

#### Who is affected?

This affects you if you use `AssetEntryLocalService` and `AssetCategoryLocalService` to operate on relationships between asset entries and asset categories.

#### How should I update my code?

Use the new methods in `AssetEntryAssetCategoryRelLocalService`. Note, that the method signatures are the same as they were in `AssetEntryAssetCategoryRelLocalService`.

#### Why was this change made?

This change removes an unnecessary table and corresponding code.

It is a followup step to the [Liferay AssetEntries_AssetCategories Is No Longer Used](https://learn.liferay.com/dxp/latest/en/liferay-internals/reference/7-2-breaking-changes.html#liferay-assetentries-assetcategories-is-no-longer-used) 7.2 breaking change where the table was replaced by the `AssetEntryAssetCategoryRel` table and the corresponding interfaces in `AssetEntryLocalService` and `AssetCategoryLocalService` were moved into `AssetEntryAssetCategoryRelLocalService`.

---------------------------------------

### Changed Entity Display Page Registration Tracking Logic
- **Date:** 2020-Oct-27
- **JIRA Ticket:** [LPS-122275](https://issues.liferay.com/browse/LPS-122275)

#### What changed?

The persisting (tracking) of entity associations with display pages has changed. In Liferay DXP/Portal versions 7.1 through 7.3, only entity associations with default display pages were persisted; entities with no display page associations and entity associations with specific, non-default display pages were not persisted. This change switches the behavior.

Here is the new behavior:

- Entities linked to a default display page are not persisted.
- Entities that do not have a display page are persisted.
- Entities that have a specific, non-default display page are persisted.

#### Who is affected?

This affects you if you have custom entities for which display pages can be created.

#### How should I update my code?

If you have custom entities with display pages, swap the display page logic by adding the `BaseUpgradeAssetDisplayPageEntries` upgrade process to your application. The process receives a table, primary key column name, and a class name.

#### Why was this change made?

This change makes the display page logic more consistent with the overall display page concept.

---------------------------------------

### Removed Deprecated and Unused JSP Tags
- **Date:** 2020-Nov-24
- **JIRA Ticket:** [LPS-112476](https://issues.liferay.com/browse/LPS-112476)

#### What changed?

A series of deprecated and unused JSP tags have been removed and are no longer available. These tags are included:

- `clay:table`
- `liferay-ui:alert`
- `liferay-ui:input-scheduler`
- `liferay-ui:organization-search-container-results`
- `liferay-ui:organization-search-form`
- `liferay-ui:ratings`
- `liferay-ui:search-speed`
- `liferay-ui:table-iterator`
- `liferay-ui:toggle-area`
- `liferay-ui:toggle`
- `liferay-ui:user-search-container-results`
- `liferay-ui:user-search-form`

#### Who is affected?

This affects you if you are using any of the removed tags.

#### How should I update my code?

See the 7.3 [`liferay-ui.tld`](https://github.com/liferay/liferay-portal/blob/7.3.x/util-taglib/src/META-INF/liferay-ui.tld) for removed tags that have replacements. However, many of the tags have no direct replacement. If you still need to use a tag that does not have a direct replacement, you can copy the old implementation and serve it directly from your project.

#### Why was this change made?

These tags were removed in an attempt to clarify the default JSP component offering and to focus on providing a smaller but higher quality component set.

---------------------------------------

### Replaced the .container-fluid-1280 CSS Class
- **Date:** 2020-Nov-24
- **JIRA Ticket:** [LPS-123894](https://issues.liferay.com/browse/LPS-123894)

#### What changed?

The `.container-fluid-1280` CSS class has been replaced with `.container-fluid.container-fluid-max-xl`. The compatibility layer that had `.container-fluid-1280`'s style has been removed too.

#### Who is affected?

This affects you if your container elements have the `.container-fluid-1280` CSS class.

#### How should I update my code?

Use the updated CSS classes from Clay `.container-fluid.container-fluid-max-xl` instead of `.container-fluid-1280`. Alternative, use ClayLayout [Components](https://clayui.com/docs/components/layout.html) and [TagLibs](https://clayui.com/docs/get-started/using-clay-in-jsps.html#clay-sidebar).

#### Why was this change made?

The change removes deprecated legacy code and improves code consistency and performance.

---------------------------------------

### Disabled Runtime Minification of CSS and JavaScript Resources by Default
- **Date:** 2020-Nov-27
- **JIRA Ticket:** [LPS-123550](https://issues.liferay.com/browse/LPS-123550)

#### What changed?

The `minifier.enable` portal property now defaults to `false`. Instead of performing minification of CSS and JS resources at run time, we prepare pre-minified resources at build time. There should be no user-visible changes in page styles or logic.

#### Who is affected?

This affects you if your implementations depend on the runtime minifier (usually the Google Closure Compiler).

#### How should I update my code?

If you want to maintain the former runtime minification behavior, set the `minifier.enable` portal property to `true`.

#### Why was this change made?

Moving frontend resource minification from run time to build time reduces server load and facilitates using the latest minification technologies available within the frontend ecosystem.

---------------------------------------

### Removed the SoyPortlet Class
- **Date:** 2020-Dec-9
- **JIRA Ticket:** [LPS-122955](https://issues.liferay.com/browse/LPS-122955)

#### What changed?

The `SoyPortlet` class has been removed. It used to implement a portlet whose views were backed by Closure Templates (Soy).

#### Who is affected?

This affects you if you are using `SoyPortlet` as a base for your portlet developments.

#### How should I update my code?

We heavily recommend re-writing your Soy portlets using either a well established architecture such as `MVCPortlet` using JSPs or a particular frontend framework of your choice.

#### Why was this change made?

This was done as a way to simplify our frontend technical offering and better focus on proven technologies with high demand in the market.

A further exploration and analysis of the different front-end options available can be found in [The State of Frontend Infrastructure](https://liferay.dev/blogs/-/blogs/the-state-of-frontend-infrastructure) including a rationale on why we are moving away from Soy:

> Liferay invested several years into Soy believing it was "the Holy Grail". We believed the ability to compile Closure Templates would provide us with performance comparable to JSP and accommodate reusable components from other JavaScript frameworks. While Soy came close to achieving some of our goals, we never hit the performance we wanted and more importantly, we felt like we were the only people using this technology.

---------------------------------------

### Server-side Closure Templates (Soy) Support has been removed
- **Date:** 2020-Dec-14
- **JIRA Ticket:** [LPS-122956](https://issues.liferay.com/browse/LPS-122956)

#### What changed?

The following modules and the classes they exported to allow Soy rendering
server-side have been removed:
- `portal-template-soy-api`
- `portal-template-soy-impl`
- `portal-template-soy-context-contributor`

To simplify the migration, the following modules remain available in a deprecated
deprecated fashion providing only client-side initialization of previous Soy
components:
- `portal-template-soy-renderer-api`
- `portal-template-soy-renderer-impl`

#### Who is affected?

Anyone directly using removed classes like `SoyContext`, `SoyHTMLData`... or
declaring `TemplateContextContributor` using `LANG_TYPE_SOY` as the value for
the `lang.type` attribute.

Developers using our Soy `ComponentRenderer` to initialize Soy components.

#### How should I update my code?

There is no replacement for the removed Soy support. If you fall under the first
scenario, we recommend switching to a different supported template language and
rewrite your templates and components.

If you're using `ComponentRenderer`, the only difference should be that your
components no longer produce markup server-side. If this is important to you, a
temporary workaround has been added. You can manually generate a version of the
markup you want to render server-side and pass it as a `__placeholder__` property
in your `context` parameter. Keep in mind that `ComponentRenderer` is deprecated
and will go away in the future, so we kindly recommend that you rewrite your
component using a different technology.

#### Why was this change made?

This is done as a way to simplify our frontend technical offering and better
focus on proven technologies with high demand in the market.

A further exploration and analysis of the different frontend options available
can be found in [The State of Frontend Infrastructure](https://liferay.dev/blogs/-/blogs/the-state-of-frontend-infrastructure) including a rationale on why we're moving
away from Soy:

> Liferay has invested several years into Soy believing it was the holy grail.
> We believed the ability to compile Closure templates would provide us the
> performance of JSP with the reusable components of other JavaScript
> frameworks. While it came close to achieving some of those goals, we never
> hit the performance we wanted and more importantly, it always felt like we
> were the only people using this technology.

---------------------------------------

### The spi.id property in log4j xml definition file has been removed
- **Date:** 2021-Jan-19
- **JIRA Ticket:** [LPS-125998](https://issues.liferay.com/browse/LPS-125998)

#### What changed?

The `spi.id` property in log4j xml definition file has been removed.

#### Who is affected?

Anyone is using `@spi.id@` in its custom log4j xml definition file.

#### How should I update my code?

Remove `@spi.id@` from log4j xml definition file.

#### Why was this change made?

The support of SPI has been removed by LPS-110758.

---------------------------------------

### Deprecated attributes have been removed from the frontend-taglib-clay tags
- **Date:** 2021-Jan-26
- **JIRA Ticket:** [LPS-125256](https://issues.liferay.com/browse/LPS-125256)

#### What changed?

The deprecated attributes have been removed from the `frontend-taglib-clay`
taglib.

#### Who is affected?

Anyone using deprecated attributes for `<clay:*>` tags.

#### Why was this change made?

The `frontend-taglib-clay` module is now using components from
[`Clay v3`](https://github.com/liferay/clay), which doesn't support the
previous attributes.

---------------------------------------

### Handling HTML boolean attributes in tags
- **Date:** 2021-Feb-18
- **JIRA Ticket:** [LPS-127832](https://issues.liferay.com/browse/LPS-127832)

#### What changed?

Boolean HTML attributes will only be rendered if passed a value of `true`.
The value for such attributes will be their canonical name.

Previously, a value such as `false` for a `disabled` attribute
would be rendered into the DOM as `disabled="false"`; now, it is simply
omitted. Likewise, a `true` value for a `disabled` attribute was
formerly rendered into the DOM as `disabled="true"`; now it is rendered
as `disabled="disabled"`.

#### Who is affected?

Anyone passing the following boolean attributes to tag libraries:

"allowfullscreen", "allowpaymentrequest", "async", "autofocus", "autoplay",
"checked", "controls", "default", "disabled", "formnovalidate", "hidden",
"ismap", "itemscope", "loop", "multiple", "muted", "nomodule", "novalidate",
"open", "playsinline", "readonly", "required", "reversed", "selected",
and "truespeed".

#### How should I update my code?

Ensure that you pass `true` when you want a boolean attribute to be
present in the DOM. If you have any CSS selectors targeting a `true`
value (e.g., `[disabled="true"]`) update them to instead target presence
of the attribute (e.g., `[disabled]`) or its canonical name (e.g.,
`[disabled="disabled"]`).

#### Why was this change made?

This change is being made for better compliance with [the HTML Standard](https://html.spec.whatwg.org/#boolean-attribute),
which says that "The presence of a boolean attribute on an element represents
the true value, and the absence of the attribute represents the false value. If
the attribute is present, its value must either be the empty string or a value
that is an ASCII case-insensitive match for the attribute's canonical name."

---------------------------------------

### Remove CSS Compatibility Layer
- **Date:** 2021-Jan-2
- **JIRA Ticket:** [LPS-123359](https://issues.liferay.com/browse/LPS-123359)

#### What changed?

The support for Boostrap 3 markup has been deleted and is no longer available.

#### Who is affected?

This affects any development that uses the old Boostrap 3 markup and was not correctly migrated to Boostrap 4 markup.

#### How should I update my code?

If you are using Clay markup you can update it by following the last [Clay components](https://clayui.com/docs/components/index.html) version.
If your markup is based on Boostrap 3, you can update it with new Boostrap 4 markup following [migrating guidelines](https://getbootstrap.com/docs/4.4/migration/).

#### Why was this change made?

We included a "small" configurable CSS compatibility layer to simplify the migration from Liferay 7.0 to 7.1. Now it has been removed in order to fix conflicts with new styles and improve general CSS weight.

---------------------------------------

### item-selector-taglib no longer fires coverImage-related events
- **Date:** 2021-Apr-15
- **JIRA Ticket:** [LPS-130359](https://issues.liferay.com/browse/LPS-130359)

#### What changed?

The `ImageSelector` JS module no longer fires the `coverImageDeleted`,
`coverImageSelected`, and `coverImageUploaded` events using the `Liferay.fire()`
API. These events were used for internal communication between the
`item-selector-taglib` and the `blogs-web` module, but now state is synchronized
between the two modules via the `Liferay.State` mechanism instead, using
`imageSelectorCoverImageAtom`.

#### Who is affected?

Anybody listening for the removed events with `Liferay.on()` or similar
functions.

#### How should I update my code?

In practice, you should not be observing the interaction between these two
modules, but if you must, you could instead use the `Liferay.State.subscribe()`
API to subscribe to `imageSelectorCoverImageAtom`.

#### Why was this change made?

`Liferay.fire()` and `Liferay.on()` publish globally visible events on a shared
channel. The `Liferay.State` API is a better fit for modules that wish to
coordinate at a distance in this way, and it does so in a type-safe manner.

---------------------------------------

### OAuth 2 Token Instrospection feature identifier has changed
- **Date:** 2021-May-04
- **JIRA Ticket:** [LPS-131573](https://issues.liferay.com/browse/LPS-131573)

#### What changed?

The feature identifier for Token Introspection at OAuth2 has changed from
`token_introspection` to `token.introspection` due to standarization of
OAuth 2 constants.

#### Who is affected?

This affects any development that uses the Token Introspection feature
identifier. For example where an OAuth2Application is added programatically
with this specific feature enabled, or when inspecting an OAuth2Application to
see if the feature is enabled.

#### How should I update my code?

If you are using the Token Introspection feature identifier, you should
manually change it from `token_introspection` to `token.introspection`.

#### Why was this change made?

This change was made to align and standarize all OAuth 2 constants in our code.
We recommend that feature identifiers use a dot to separate words.

---------------------------------------