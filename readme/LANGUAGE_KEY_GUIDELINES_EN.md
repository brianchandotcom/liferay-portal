# English Language Key Guidelines

All English language keys must conform to the following guidelines.

To request review of your language key changes, send them in a pull request to
Cody Hoag (GitHub handle: codyhoag).

## Text Styles

Title and sentence are the two types of text styles used in Liferay's language
keys. They're described below.

### Title

- Capitalize all words, except articles (*a*, *the*), prepositions (*to*, *at*,
  *in*, *with*), and coordinating conjunctions (*and*, *but*, *or*).
- Omit any trailing period.
- Examples:
    - *Account Created Notification*
    - *Private Pages*

### Sentence

- Capitalize only its first word and all proper nouns.
- Use normal punctuation (including periods).
- Examples:
    - *Enabling ImageMagick and GhostScript provides document preview
    functionality.*
    - *Email address and type are required fields.*

## Applying Text Styles

This section provides rules for applying the text styles and writing the
language keys.

### 1. Radio/Checkbox/Selectors/Text Fields

Typically, use titles. Use a sentence, however, if the key starts with an action
word followed by long descriptive text.

**Example 1:**

The *Instance Settings* &rarr; *User Authentication* &rarr; *General* page
consistently uses sentences (in this case, question sentences) for all of its
checkboxes:

![ ](./images/language/authentication_checkboxes.png)

**Example 2:**

The *Instance Settings* &rarr; *Users* &rarr; *Fields* page consistently uses
concise titles for all of its checkboxes:

![ ](./images/language/user_fields_checkboxes.png)

More examples of radio/checkbox/selector/text field labels that are titles
include:

- *Membership Type*
- *Trash Entries Max Age*

More examples of radio/checkbox/selector/text field labels that are sentences
include:

- *Allow subsites to display content from this site.*
- *Use the default language options.*

If a radio or checkbox provides more options when selecting it, make that
obvious by starting its label with an action word. For example, a checkbox using
one of the labels below hints that selecting it brings up more options:

- *Define social interactions for users.*
- *Define a custom default language and additional available languages for this
  site.*

There are cases that require mixing text styles. For example, the mid-sentence
*inline* text fields in the Social Activity portlet mix text styles to improve
readability. If you need to mix text styles, do so cautiously.

### 2. Action Word Tense

Use present tense when describing actions that do something:

- *Enable* this functionality.
- *Require* this functionality.

Use past tense, when applicable, to describe state:

- *Required*
- *Enabled* by Default

**Avoid** using past tense action words at the end of sentences.

Incorrect way:

*CDN dynamic resources enabled.*

Correct way:

*Enable CDN dynamic resources.*

**Avoid** using future tense anywhere.

Incorrect way:

*Checking this box will enable users to view...*

Correct way:

*Checking this box lets users view...*

### 3. Help, Error, Success, and Empty Results Messages

Use sentences to inform or warn a user. Here are some example messages:

- Help messages. Portal's keys ending in *-help*, except *hide-syntax-help*,
  use sentences.

    ![ ](./images/language/help_message.png)

- Success and error messages

    ![ ](./images/language/success_error_messages.png)

- Empty results messages (*emptyResultsMessage=*)

    ![ ](./images/language/empty_results_messages.png)

### 4. Instructions

Use sentences to instruct the user about a user interface, but not a particular
button, selector, or field.

**Examples:**

- *Please enter JDBC information for new database.*
- *Select the maps API provider to use when displaying geolocalized assets.*

    ![ ](./images/language/maps_api_instructions.png)

### 5. UI Component Labels

Use titles to label UI components. Labels are defined in a JSP as *label=""*.

**Example:**

The UI label for *Number of Items to Display* is
*label="number-of-items-to-display"*

![ ](./images/language/max_items_to_display.png)

### 6. Menus and Higher Level Tabs

Use titles to label menus and high level tabs.

![ ](./images/language/menu_example.png)

![ ](./images/language/web_content_menu.png)

### 7. Section and Text Area Descriptions

Use sentences to describe sections and text areas.

![ ](./images/language/portal_settings_analytics.png)

### 8. Omit Needless Words

Omit needless words from your language keys.

**Example:** In some programming languages, you need *if*/*then*. In English,
you often do not.

*Incorrect way:*

*If this is checked, then the site administrator...*

*Correct way:*

*If this is checked, the site administrator...*

Here are some commonly used needless phrases and words that you can
remove/replace:

- just = (remove)
- simply = (remove)
- allows you to = lets you
- directs you to = shows you
- have the option of = can

### 9. Consider Other Languages

To accommodate languages that arrange words differently in a sentence, use
complete sentences.

**Example:** Assume you'd like to create a language key that displays this:
*You have attempted [number] times.*

**Incorrect way:**

*JSP:*

        <liferay-ui:message key="you-have-attempted" /> <%=value %> <liferay-ui:message key="times" />

*Language Keys:*

        you-have-attempted=You have attempted

        times=times.

**Correct way:**

*JSP:*

        <liferay-ui:message key="you-have-attempted-x-times" arguments="<%=value %>" translateArguments="<%= false %>"/>

*Language Key:*

        you-have-attempted-x-times=You have attempted {0} times.