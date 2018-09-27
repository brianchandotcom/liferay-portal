# JSDoc Submission Process

Documenting your code with JSDoc is essential for anyone developing for
Liferay products. Please follow the submission processes outlined here to ensure
an effective review process.

## Liferay Internal Developers

Developers are responsible for creating JSDoc when writing/maintaining its
related code. This ensures that the documentation is correct and useful. There
are two paths you can take for providing JSDoc for your area of expertise:

- [Write JSDoc during the code creation/modification process.](#write-jsdoc-during-code-creation)
- [Write JSDoc for completed code that's already merged and available.](#write-jsdoc-for-mergedavailable-code)

Follow the path corresponding to your current workflow.

### Write JSDoc During Code Creation

Developers can submit JSDoc **with** code if certain precautions are followed
(outlined below). Follow the below process for submitting JSDoc this way:

1.  Write JSDoc for code areas that you have deep knowledge for. Be sure to
    follow the [JSDoc Guidelines](JSDOC_GUIDELINES.markdown) when writing JSDoc
    to ensure comprehensive descriptions and consistent style.

2.  Insert an `@review` tag below each new JSDoc entry. This will be searched
    for by the Knowledge Management (KM) team after it's merged to ensure that
    all JSDoc submissions follow the correct format and make sense. This
    should look like this:

        /**
        * Initial content sent to the editor.
        *
        * @default ''
        * @instance
        * @memberOf AceEditor
        * @review
        * @type {string}
        */
        initialContent: Config.string().value(''),

    Submit your JSDoc following your typical code review process.

3.  Notify your team's tech writer (a KM team member) that JSDoc is waiting
    for review. If you're not sure whom to notify, contact Cody Hoag
    (cody.hoag@liferay.com).

The tech writer will track the JSDoc and review it as soon as possible.

### Write JSDoc for Merged/Available Code

Follow the process outlined below to provide JSDoc for code that is already
published:

1.  Write JSDoc for code areas that you have deep knowledge on. Be sure to
    follow the [JSDoc Guidelines](JSDOC_GUIDELINES.markdown) when
    writing JSDoc to ensure comprehensive descriptions and consistent style.

2.  Send a pull request containing the new JSDoc to your team's tech writer
    (a KM team member). If you're unsure who should receive the pull request,
    send it to Cody Hoag (Github handle:
    [`codyhoag`](https://github.com/codyhoag)).

The tech writer will review the JSDoc as soon as possible.

## KM Members

KM members are restricted to only **review** JSDoc. Developers are responsible
for providing drafted JSDoc for their areas of expertise. Below is a checklist
to complete to ensure JSDoc is properly formatted and ready for submission:

- All JSDoc should follow the
  [JSDoc Guidelines](JSDOC_GUIDELINES.markdown).
- Format JSDoc using the JSDoc Formatter. See
  [here](JSDOC_GUIDELINES.markdown#formatting-and-building-jsdoc)
  for more information.
- Ensure all commit descriptions start with a ticket number (e.g., *LPS-12345
  JSDoc*). If the JSDoc is also associated with an LRDOCS ticket, include
  that in the commit description too (e.g., *LPS-12345 LRDOCS-9876 JSDoc*).

When finished reviewing, send your edits in a pull request to
[`codyhoag`](https://github.com/codyhoag).