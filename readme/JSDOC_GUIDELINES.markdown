# JSDoc Guidelines

We welcome you to contribute JSDoc for Liferay's JavaScript classes. Following
these guidelines helps to ensure that JavaScript classes are documented
sufficiently and consistently. The guidelines also explain how to leverage
Liferay's JSDoc tools. Refer to the table of contents below for easier
navigation.

1. [Class Comments](#class-comments)
2. [Method Comments](#method-comments)
3. [Top Tips](#top-tips)
4. [JSDoc Linking](#jsdoc-linking)
    - [@see Tags](#see-tags)
    - [@link Tags](#link-tags)
5. [Formatting Tags](#formatting-tags)
6. [State Object Comments](#state-object-comments)
7. [State Properties](#state-properties)
8. [Formatting and Building JSDoc](#formatting-and-building-jsdoc)
9. [Submitting JSDoc](#submitting-jsdoc)

## Class Comments

The following information should be present in the following order in the class
comment:

- Initial class description (paragraph/sentence)
    - **First sentence** - Should describe the class clearly and concisely
		(required).
    - **Followup sentences** - Support the first sentence with important points
	  about the class (optional).
- Detailed class description (additional paragraph(s))
    - Provide more information on the class's purpose, abilities, and general
		 role. For some classes (simple utility classes for instance), this
		 additional information is not necessary if the initial paragraph provides
		 an adequate description.
    - Usage examples or `@link` tags to where the class can be seen in use.
- [@see tags](http://usejsdoc.org/tags-see.html) link to other closely
  related classes whose JSDocs give the reader a clearer picture of the
  purpose of this class (if applicable).
- [@deprecated tags](http://usejsdoc.org/tags-deprecated.html) should contain a
  short description that includes the release/version of initial deprecation,
  why the method was deprecated, and a link to what should be used in its
  place (if applicable).

A simple example class comment is provided below:

```JS
/**
 * Represents an example class. If a basic description requires more than one
 * sentence, include it in the first paragraph.
 *
 * <p>
 * Example of a second paragraph. Note the blank line between the first <p> tag
 * and the end of the first paragraph.
 * </p>
 *
 * @see BigExample
 */
class Example {
    ...
}
```

## Method Comments

The following information should be present in the JSDoc comment on each
method:

- A short, one sentence description of the method.
- Additional sentences and/or paragraphs providing more information on the
  method's purpose and function. Any information the user of the method would
	find useful should be included here, including special requirements,
	circumstances where it should/should not be used, etc. For many methods, this
	additional explanation is not necessary.
- Usage examples if necessary and possible. Trivial methods don't need examples,
  and if an example would be extremely large, simply *@link* to a place the
	method is used.
- If the method is only used in one or two places, *@link* to the methods it is
  called from. This helps later developers to understand its role in Liferay.
- If the method is a callback method, identify this in the description.

Some method JSDoc tags are more popular than others. Listed below are the most
popular ones used in Liferay Portal's JavaScript files:

- [@inheritdoc](http://usejsdoc.org/tags-inheritdoc.html) - The method's parent
  documentation is inherited and displayed when generated.
- [@memberOf](http://usejsdoc.org/tags-memberof.html) - Marks the property as a
  member of the parent.
- [@param](http://usejsdoc.org/tags-param.html) - The method parameters,
  alphabetically ordered, with descriptions. The descriptions should begin with
  *The* and end with a period.
- [@return](http://usejsdoc.org/tags-returns.html) - All possible
  return values, including `null`. If the method is void, do not include this.
  The return values should begin with *The* or *A* (unless it's a boolean; in
  that case, use *Whether*) and end with a period.
- [@throws](http://usejsdoc.org/tags-throws.html) - The exceptions the method
  can throw, alphabetically ordered, with explanations of what would trigger
  them.
- [@see](http://usejsdoc.org/tags-see.html) - Links to another JSDoc area
  if more important details can be gleaned from that description. See the
  [JSDoc Linking](#jsdoc-linking) section for more details.
- [@deprecated](http://usejsdoc.org/tags-deprecated.html) - A short description
  that includes the release/version of initial deprecation, why the method was
  deprecated, and a link to what should be used in its place.

An example method description is provided below:

```JS
/**
 * Dispatches a client event.
 *
 * @memberof PortletInit
 * @param {string} type The type of listener.
 * @param {any} payload The payload to be delivered.
 * @return {number} The number of events queued for delivery.
 * @throws {TypeError} If the type was a system event type.
 */
dispatchClientEvent(type, payload) {
  validateArguments(arguments, 2, 2, ['string']);

  if (type.match(new RegExp(portletRegex))) {
    throw new TypeError('The event type is invalid: ' + type);
  }

  return Object.keys(eventListeners).reduce(
    (amount, key) => {
      const listener = eventListeners[key];

      if (type.match(listener.type)) {
        listener.handler(type, payload);
        amount++;
      }
      return amount;
    },
    0
  );
}
```

Here is another method example:

```JS
/**
 * Spawns a webworker to process the image in a different thread.
 *
 * @param  {Object} message The image and brightness value.
 * @return {CancellablePromise} A promise that resolves when the webworker
 * finishes processing the image.
 */
spawnWorker_(message) {
  return new CancellablePromise((resolve, reject) => {
    let workerURI = this.modulePath + '/BrightnessWorker.js';
    let processWorker = new Worker(workerURI);

    processWorker.onmessage = (event) => resolve(event.data);
    processWorker.postMessage(message);
  });
}
```

## Top Tips

1.  If you're new to JSDoc, familiarize yourself with the
    [official JSDOC guide](http://usejsdoc.org/index.html).
2.  Starting with an *action word*, describe what each [class](#class-comments)
    and [method](#method-comments) does.
3.  Avoid just restating the class or method name (e.g., avoid
    *updateLocalization(...)* &rarr; *Updates the localization* **OR** *@param
    {string} key the key*).
4.  Describe the most important details in the first sentence.
5.  Include all relevant tags
    ([@param](http://usejsdoc.org/tags-param.html),
    [@return](http://usejsdoc.org/tags-returns.html), etc.) for
    each method; without them, the method JSDoc is incomplete.
6.  Start a method description with *Returns*, if the method returns a value.
7.  Avoid referring explicitly to parameters by name; instead, refer to them in
    general terms (e.g., *class name ID* instead of
    *&lt;code>classNameId&lt;/code>*).
8. Begin boolean parameter descriptions with *whether*. See example below:

    ```JS
    /**
     * @param {boolean} renderData Whether to render the data.
     */
    ```

9. Punctuate every class and method description (sentence or phrase) with a
    period.
10. Punctuate all tag descriptions (sentence or phrase) (e.g.,
    [@param](http://usejsdoc.org/tags-param.html),
    [@return](http://usejsdoc.org/tags-returns.html), and
    [@throws](http://usejsdoc.org/tags-throws.html)) with a period. Write
    follow-up text in complete sentences.
11. Capitalize the first world for all tag descriptions.
12. Describe exceptions (i.e.,
	  [@throws](http://usejsdoc.org/tags-throws.html)) in past
    tense.
13. Don't wrap the first paragraph of a JSDoc comment with paragraph tags. Do
    wrap subsequent paragraphs with paragraph tags.
14. Wrap JSDoc at 80 columns.

Terrific! You're off to a great start to writing JSDoc.

## JSDoc Linking

There are two popular ways to link to JSDoc: `@link` and `@see`.

**Note:** It's not currently possible to provide direct links to JSDoc residing
in an external Liferay module. You should refer to the outside logic using
common terms. For example, for the JS class `EventScreen` you can refer to it as
`<code>EventScreen</code>`. You can, optionally, include the module name it
belongs to for easier navigation (e.g., the `foo` module's
`<code>EventScreen</code>` class).

### @see Tags

Use [`@see`](http://usejsdoc.org/tags-see.html) tags to link to other closely
related areas whose JSDocs give the reader a clearer picture of the purpose of
the specific JS logic. These should only be used directly below the class/method
description, not within.

**Examples:**

```
@see class
@see class#method
@see class.object
@see class#property
```

Use the inline `{@link}` tag to include a link within a free-form description.

```
@see {@link foo} for further information.
@see {@link http://github.com|GitHub}
@see {@link class#method|method}
```
### @link Tags

Use [`@link`](http://usejsdoc.org/tags-inline-link.html) tags to create an
inline link. This should be used within descriptions, and surrounded by `{}`
(e.g., `{@link BigExample}`.

**Examples:**

```
This is similar to {@link class}.
This is similar to {@link class#method}.
This is similar to {@link class.object}.
This is similar to {@link class#property}.
This is similar to {@link class#method|method}.
```

## Formatting Tags

HTML tags should be used to format JSDoc. Use the following tips when deciding
how to format your text:

- All HTML tags (except for `<b>`, `<i>`, `<code>`, etc.) should be on a line by
  themselves.
- When including multiple paragraphs for a description, wrap the additional
  paragraphs in `<p>` tags. The first paragraph should not be wrapped this way.
- Format all keywords, special constants (`true`, `false`, `null`), and file
  names (`portal-ext.properties`) as code using the HTML `<code>` tag (e.g.,
  `<code>true</code>`).
- Unordered and ordered lists in comments should be represented using `<ul>` or
  `<ol>` respectively. The `<ul>` and `</ul>` or `<ol>` and `</ol>` tags should
  each be on a line of their own. List items should each be placed on their own
  line, and the `<li>` and `</li>` tags should be on their own line immediately
  before and after the item text.
- Unordered and ordered lists should not be nested within paragraph (`<p></p>`)
  tags.

## STATE Object Comments

Each class may contain STATE objects that contain properties related to the
instance. These should resemble [method comments](#method-comments), except
they're typically shorter and don't begin with an action word.

The following information should always be present in the JSDoc tags for the
STATE properties:

- [@type](http://usejsdoc.org/tags-type.html) - Specifies the type that the
  STATE object contains.
- [@static](http://usejsdoc.org/tags-static.html) - Specifies that the STATE
  object is contained within the parent and can be accessed without
  instantiating the parent.

Below are example comments for a STATE object:

```JS
/**
 * State definition.
 *
 * @type {!Object}
 * @static
 */
FragmentPreview.STATE = {
  ...
}
```

### STATE Properties

Each STATE object contains properties that define settings for the instance.
These resemble [method comments](#method-comments), except they're typically
shorter and don't begin with an action word.

The following information should be present in the JSDoc tags for the STATE
property:

- [@default](http://usejsdoc.org/tags-default.html) - The default
  value for the property.
- [@instance](http://usejsdoc.org/tags-instance.html) - Marks the property as an
  instance member of the parent.
- [@memberOf](http://usejsdoc.org/tags-memberof.html) - Marks the property as a
  member of the parent.
- [@type](http://usejsdoc.org/tags-type.html) - Specifies the property type.

The following information can be present in the JSDoc tags for the STATE
property:

- [@private](http://usejsdoc.org/tags-private.html) - Marks the property as
  private, meaning HTML for the property will not be generated.
- [@protected](http://usejsdoc.org/tags-protected.html) - Marks the property as
  protected, meaning it should only be used with the current module.

Example STATE property comments:

```JS
/**
 * Flag that checks if the preview content is loading.
 *
 * @default false
 * @instance
 * @memberOf FragmentPreview
 * @protected
 * @type {boolean}
 */
_loading: Config.bool()
  .internal()
  .value(false),
```

If the type is an Array or Object that contains elements you would also like to
document, you can use the
[Closure Compiler's syntax](http://usejsdoc.org/tags-type.html)
to specify their type. Below is an example configuration. Note that the types
**must** be documented on the same line:

```JS
/**
 * URLs used for communicating with the back-end
 *
 * @instance
 * @memberOf FragmentEditor
 * @type {{edit: !string, redirect: !string}}
 */
urls: Config.shapeOf(
  {
    edit: Config.string().required(),
    redirect: Config.string().required()
  }
).required(),
```

## Formatting and Building JSDoc

Before committing any new or modified JSDocs, you should format your JS files
first! This will automatically wrap your comments to the proper width, format
HTML tags, and line up JSDoc tags. Be sure to install Liferay's
[Frontend Source Formatter](https://github.com/liferay/liferay-frontend-source-formatter)
before proceeding.

1. Run the JSDoc Formatter.

    To format JSDoc in a module under `liferay-portal/modules/apps/app-name`,
    execute this in the module's root folder:

        npm run csf -i

    Alternatively, you can run the formatter from the `liferay-portal/modules`
    folder to format all the modules:

        ../gradlew npmRunFormat

2. Building JSDoc (optional)

    To build a module's JSDoc HTML to the module's
    `build/docs/jsdoc/module-name/version` folder to see what it looks like,
    execute this from the module's root folder:

        ../../../../gradlew jsdoc

    Alternatively, you can generate the JSDoc for an entire app suite by
    executing this from the app suite's root folder:

        ../../../gradlew appJSDoc

    Open the generated `index.html` file to view the generated JSDoc.

    **Note:** JSDoc HTML is only generated for modules that use the `.es.js`
    extension. This task can take a few minutes to generate the JSDoc as it
    downloads Node as part of the process.

## Submitting JSDoc

For more information on submitting JSDoc, see the
[JSDoc Submission Process](JSDOC_SUBMISSION_PROCESS.markdown) article.

To request **re-adding** JSDoc or comments that have been removed from a file,
please open an [LRDOCS](https://issues.liferay.com/browse/LRDOCS) JIRA ticket:

- **Issue Type:** API
- **Summary:** Re-add JSDoc for .... (name or prefix of class/file)
- **Component:** Area the JSDoc pertains to
- **Affected Release:** 7.2.x, 7.1.x, 7.0.x, 6.2.x, 6.1.x
- **Description:** Include the *commit number* of the original JSDoc commit
  and/or the JSDoc removal commit