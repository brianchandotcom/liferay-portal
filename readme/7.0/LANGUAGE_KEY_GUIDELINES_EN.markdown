# English Language Key Guidelines

All English language keys must conform to the following guidelines.

## Text Styles

The three types of text styles that can be applied to Liferay's language keys
are:

### Title

- All words capitalized, except for articles (*a*, *the*), prepositions (*to*,
*at*, *in*, *with*), and coordinating conjunctions (*and*, *but*, *or*)
- No punctuation
- Examples:
    - *Account Created Notification*
    - *Private Pages*

### Phrase

- Only first word and all proper nouns after are capitalized
- No periods, but can use question mark if the phrase is a question
- Examples:
    - *Reset preview and thumbnail files for Documents and Media portlet*
    - *Maximum file size*
    - *How do users authenticate?*

### Sentence

- First word and all proper nouns after are capitalized       
- Uses proper punctuation (including periods) 
- Examples: 
    - *Enabling ImageMagick and GhostScript provides document preview
    functionality.*
    - *Email address and type are required fields.*

## Where to Use Text Styles

### 1. Radio/Checkbox/Drop-Down/Text Fields

These should follow the *Title* text style. However, if any of these uses an
action word, use the *Phrase* text style. Handling this decision will be on a
page by page basis.

**Note:** If a page requires a phrase, then the remaining selectors/fields
should also be phrases to be consistent on that specific page. Examples for
pages using all phrases and all titles, respectively, are below:

**Examples:**

The *Portal Settings* &rarr; *Authentication* &rarr; *General* page is
consistent by using phrases (in this case, question phrases) for all of its
checkboxes:

![ ](./images/common-images/language/authentication-checkboxes.png)

The *Portal Settings* &rarr; *Users* &rarr; *Fields* page is consistent by
using concise titles for all of its checkboxes:

![ ](./images/common-images/language/user-fields-checkboxes.png)

More examples of radio/checkbox/drop-down/text field labels that are titles
include:

- *Membership Type*
- *Trash Entries Max Age*

More examples of radio/checkbox/drop-down/text field labels that are phrases
include:

- *Allow subsites to display content from this site*
- *Use the default language options*

If a radio or checkbox provides more options once selected, use an action word
to make that obvious to the user. For example, the following checkbox labels
hint that more options will be presented when you select their associated
checkbox:

- *Define social interactions for users*
- *Define a custom default language and additional available languages for this
site*

In rare cases, like the Social Activity portlet, a mix of text styles are used
on the same page. In Social Activity, text fields are used mid-sentence *inline*
to give them proper context. These special cases require mixed text styles to
look stylistically pleasing. Mix text styles with caution.

### 2. Action Word Tense

Use present tense when using an action word to do something:

- *Enable* this functionality
- *Require* this functionality

Use past tense when only using the single action word or pairing action word
with by *default*:

- *Required*
- *Enabled* by default

**Avoid** using past tense action words at the end of phrases, like:

*CDN dynamic resources enabled*

and, instead, describe the phrase like:

*Enable CDN dynamic resources*
    
**Avoid** using future tense for any text style. For example:

*Checking this box will enable users to view...*

should be modified to say:

*Checking this box lets users view...*

### 3. Portal Messages 

Any insightful message given to update or warn a user should use the *Sentence*
text style. The following are examples of messages in the portal:

- Help messages, (for example: *-help=*) except for *hide-syntax-help*.

    ![ ](./images/common-images/language/help-message.png)

- Success and error messages

    ![ ](./images/common-images/language/success-error-messages.png)

- Empty results messages (*emptyResultsMessage=*)

    ![ ](./images/common-images/language/empty-results-messages.png)

### 4. Instructions

When you're displaying instructions, use the *Sentence* text style. Instructions
are not associated with one particular button/selector/field, but with a group
of them.

**Examples:**

- *Please enter JDBC information for new database.*
- *Configure the file upload settings.*

    ![ ](./images/common-images/language/file-upload-settings.png)

### 5. UI Component Labels

Labels for any UI component should follow the *Title* text style. Labels are
defined in a JSP as *label=""*.

**Example:**

The UI label for *Maximum Items to Display* is
*label="maximum-items-to-display"*

![ ](./images/common-images/language/max-items-to-display.png)

### 6. Menus and Higher Level Tabs

For menu and higher level tabs, use the *Title* text style.

![ ](./images/common-images/language/menu-example.png)

![ ](./images/common-images/language/control-panel-menu.png)

### 7. Section and Text Area Descriptions

For section descriptions or text area descriptions, use the *Sentence* text
style.

![ ](./images/common-images/language/portal-settings-analytics.png)

### 8. Omit Needless Words

There are tendencies to include words that have no meaning in Liferay's language
keys. Make sure to omit these needless words.

**Example:** In some programming languages, you need if/then. In English, you
often do not:

*If this is checked, then the site administrator...*

should be changed to:
        
*If this is checked, the site administrator...*

A list of common phrases with unnecessary words can be viewed below with their
more direct conversions:

- just = (remove)
- simply = (remove)
- allows you to = lets you
- directs you to = shows you
- have the option of = can

### 9. Consider Other Languages

Be conscientious of other languages. Not all languages have the same word order
for phrases/sentences. Make sure the language key is designed to work with other
languages.

**Example:** Assume you'd like to create a language key that displays in the
Portal as: *You have attempted [number] times.*

**Incorrect Way:**

*JSP:*

        <liferay-ui:message key="you-have-attempted" /> <%=value %> <liferay-ui:message key="times" />

*Language Keys:*

        you-have-attempted=You have attempted

        times=times.

**Correct Way:**

*JSP:*

        <liferay-ui:message key="you-have-attempted-x-times" arguments="<%=value %>" translateArguments="<%= false %>"/>

*Language Key:*

        you-have-attempted-x-times=You have attempted {0} times.