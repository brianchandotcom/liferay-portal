# Liferay Forums

A forums application built from Liferay Objects, fragments and client extensions. Members browse categories, open topics, reply, vote, mention each other and subscribe to discussions, and moderators work a flag queue and manage bans.

There are no OSGi modules. Everything ships as client extensions, so the application installs on Liferay SaaS, on Liferay PaaS and on a self hosted bundle alike.

## Table of Contents

- [What Is Included](#what-is-included)
- [Setup](#setup)
- [Required Feature Flags](#required-feature-flags)
- [Optional Portal Properties](#optional-portal-properties)
- [Fragments](#fragments)
- [Category Hierarchy](#category-hierarchy)
- [Thread Priorities](#thread-priorities)
- [User Mentions](#user-mentions)
- [Pages](#pages)
- [Language Keys](#language-keys)
- [Guest Access](#guest-access)
- [Subscription and Mention Notifications](#subscription-and-mention-notifications)
- [Behaviour Worth Knowing](#behaviour-worth-knowing)

---

## What Is Included

| Path | Contents |
| :--- | :--- |
| `client-extensions/liferay-forums-site-initializer` | The site initializer: 10 object definitions, 9 relationships, 8 fragments, 5 pages, 2 display page templates, 4 notification templates, object actions, role grants and the service access policy. |
| `client-extensions/liferay-forums-etc-spring-boot` | A Spring Boot client extension that works out who to notify when content is posted. Listens on port 58082. |
| `language/` | The application's language files. `buildLang` generates the per locale files, and the workspace turns the directory into a batch client extension that imports them. |

---

## Setup

Build the workspace with its wrapper:

```bash
./gradlew build
```

To deploy into a local bundle, point the build at it:

```bash
./gradlew deploy -Pliferay.workspace.home.dir=/path/to/bundles
```

That writes three artifacts into `<bundles>/osgi/client-extensions`: the site initializer, the Spring Boot service, and the generated `language` batch. The site is created automatically, because the initializer declares the site it provisions:

```yaml
    siteExternalReferenceCode: LIFERAY_FORUMS
    siteName: Forums
```

Change those two values to provision under a different name or code. Object permissions and the service access policy are applied by the initializer, so there is no manual setup step.

### After the First Install

The initializer creates the object definitions a moment after the OAuth application is registered, so the per object scopes the Spring Boot service needs are not on it yet. Those are `c_forummessage.everything`, `c_forumnotification.everything`, `c_forumsubscription.everything`, `c_forumthread.everything` and `c_forumuser.everything`, and without them every call the service makes to `/o/c/...` answers `403`. Redeploying the service registers them against the objects that now exist:

```bash
./gradlew :client-extensions:liferay-forums-etc-spring-boot:clean \
  :client-extensions:liferay-forums-etc-spring-boot:deploy \
  -Pliferay.workspace.home.dir=<bundles>
```

Then restart the service process. A service that is already running does not pick up the redeployed registration, and Liferay's calls to it are rejected before they reach the application. Neither side reports anything, so replies and mentions simply record no notification.

Both steps are needed once, on a new installation.

### Upgrading an Install Created Earlier

The initializer's permission step only adds and updates grants, so a grant removed from the source tree stays in place on a site that already applied it. A fresh install is unaffected.

Earlier versions let every member add `ForumUser` and `ForumNotification` entries, because the service wrote those rows as the member who posted. The service now writes them as a service account, so members no longer need either grant, and leaving them in place would let a member address a notification to somebody else. On an install created before that change, revoke them: in Control Panel, under Objects, clear the permission that allows the User role to add entries on `ForumUser` and on `ForumNotification`. Members keep the rest of what they had, including reading `ForumUser` for the mention picker and adding and reading their own `ForumSubscription` rows.

### Validation Rules on an Install Created Earlier

The ban and the thread priority are refused by object validation rules, which the initializer creates with the object definition. Definitions are company scoped and survive the site, so on an install that already has them the rules are not created and both checks stay browser only.

Add them once, per rule, naming the object they guard:

```bash
curl --request POST --user test@liferay.com:test \
  --header "Content-Type: application/json" \
  --url "http://localhost:8080/o/object-admin/v1.0/object-definitions/by-external-reference-code/FORUM-MESSAGE/object-validation-rules" \
  --data '{
    "active": true,
    "engine": "function#liferay-forums-etc-spring-boot-object-validation-rule-ban",
    "errorLabel": {"en_US": "Your account has been banned from participating in the forums."},
    "externalReferenceCode": "FORUM-MESSAGE-BAN-VALIDATION",
    "name": {"en_US": "Ban Enforcement"},
    "outputType": "fullValidation",
    "script": ""
  }'
```

`FORUM-THREAD` needs the same ban rule plus the priority one, whose engine is `function#liferay-forums-etc-spring-boot-object-validation-rule-priority`. The exact payloads are in `object-definitions/forum-message.object-definition.json` and `object-definitions/forum-thread.object-definition.json`, under `objectValidationRules`.

The rules call the Spring Boot client extension, so it has to be deployed and running before they are created; a rule whose engine is not registered is refused.

### Applying a Change to an Existing Site

The initializer creates pages when the site is created. Editing a page or a fragment and redeploying does not retrofit the change onto a site that already exists, so recreate the site to apply it:

```bash
curl --request DELETE --user test@liferay.com:test \
  --url "http://localhost:8080/o/headless-admin-site/v1.0/sites/LIFERAY_FORUMS"

rm -f <bundles>/osgi/client-extensions/liferay-forums-site-initializer.zip

./gradlew :client-extensions:liferay-forums-site-initializer:clean \
  :client-extensions:liferay-forums-site-initializer:deploy \
  -Pliferay.workspace.home.dir=<bundles>
```

Removing the artifact first matters. Gradle decides whether to rebuild from file contents, so with no source edit the deploy rewrites nothing and Liferay never sees a change to pick up.

Object definitions and their entries are company scoped, so forum content survives recreating the site.

---

## Required Feature Flags

Enable these before provisioning. They gate object capabilities the initializer relies on, so turning them on afterwards leaves a partly configured site.

| Flag | Gates |
| :--- | :--- |
| `LPD-17564` | CMS, and `ObjectEntry` display and expiration date persistence |
| `LPD-34594` | Root object definitions, required by `LPD-17564` |
| `LPD-11235` | Enhanced rich text editor, used by the message composer |

Set them in Instance Settings under Feature Flags, or in the bundle's `portal-ext.properties`:

```properties
feature.flag.LPD-11235=true
feature.flag.LPD-17564=true
feature.flag.LPD-34594=true
```

---

## Optional Portal Properties

The application runs without any portal property changes. This one is worth considering:

```properties
layout.show.portlet.access.denied=true
```

With it enabled, a member who reaches the moderation or category administration page without the rights for it is told so. With it disabled, which is the portal default, the widget renders empty and the page looks broken rather than restricted. Add it to the bundle's `portal-ext.properties` if that message is wanted.

---

## Fragments

| Fragment | Purpose |
| :--- | :--- |
| `forums-hero` | Top banner with the solutions, discussions and messages counts, each of which can be switched off in fragment configuration, plus the new discussion button. |
| `forums-category-grid` | Top level categories in a grid, badging those that contain subcategories. |
| `forums-message-list` | Topic listings for a category view or a recent activity feed. |
| `forums-message-detail` | A single topic with its replies, votes, attachments and moderation actions. |
| `forums-message-composer` | Modal composer for topics and replies, including the mention picker and the priority select. |
| `forums-categories-admin` | Category administration, including assigning a parent to create a subcategory. |
| `forums-moderation` | Flag queue and ban management. |
| `forums-related-topics` | Topics related to the one being viewed. |

### Appearance

The fragments ship a style neutral look. Colours reference Classic and Lexicon style book tokens with a literal fallback, for example `var(--primary, #0b5fff)`, so the token wins whenever the active theme or style book defines it. The visual identity therefore belongs to the theme, and the way to re-skin the forum is to edit style book tokens or layer a theme CSS client extension rather than to edit the fragments.

### Category Query Configuration

The fragments that list categories take the page size and the sort of that lookup from fragment configuration. The sort is blank by default, which omits it from the query. Set it where the database supports sorting on a text field, for example `categoryName:asc`.

---

## Category Hierarchy

Categories support one optional level of subcategories. Structure is opt in, so a community that never assigns a parent gets a flat forum.

- **Storage.** A self referential `ForumCategory` relationship (`categorySubcategories`, cascading on delete) puts `r_categorySubcategories_c_forumCategoryId` on the child. Absent or `0` means top level.
- **Authoring.** The parent select lists only top level categories, and a category that already has children cannot be given a parent. Those two rules keep the hierarchy one level deep.
- **Browsing.** The grid shows top level categories and badges those with children. Opening one shows its subcategories as cards above its topic list, so the breadcrumb reaches at most `Forums > Category > Subcategory`.
- **Topic scope.** A parent lists the topics assigned directly to it; subcategory topics live under the subcategory. Posting into a parent stays valid, so a parent is never a dead end, and the composer's category select shows subcategories indented under their parent.
- **Deletion.** The relationship cascades, so deleting a parent also deletes its subcategories and their topics. The confirmation dialog names the subcategory count first.

One level is a deliberate limit rather than a setting. Unbounded nesting is the failure it exists to avoid, and categories work best cutting where permissions and audiences cut, with tags handling topics. Because forum objects are site scoped, the navigational depth already available is Site, Category, Subcategory.

---

## Thread Priorities

Topics can be prioritised the way legacy Message Boards prioritises threads.

- **Levels.** Urgent, Sticky, Announcement, or none.
- **Setting one.** The composer offers a priority select when creating or editing a topic, never on a reply, and only to moderators. A moderator is recognised by holding the create action on the ban collection, which ordinary members never hold. When a member edits their own topic the priority is left untouched.
- **Ordering.** Listings sort by priority before the tab's own sort, so prioritised topics pin to the top. Search results keep the plain tab order, because priority is not part of the search index.
- **Display.** A prioritised topic shows a coloured badge with an icon and a localised name next to its title.

---

## User Mentions

Members can mention each other in a topic or a reply.

- **Composing.** Typing `@` opens a picker anchored at the caret, listing the people who have posted in this forum. The candidates come from the forum's own Forum User record, so nothing here reads user accounts and mentioning needs no permission over them. The person composing is left out of their own list.
- **Who appears.** A member gets a Forum User record the first time they post, written server side by an object action on topics and replies. So the picker offers exactly the people a mention can actually reach, and somebody who has never posted here is not offered.
- **Storing.** A mention is stored as the visible `@screenName` token in the post body, which is the form that survives both the classic and the enhanced rich text editor.
- **Notifying.** A mention notifies by email and in the notifications panel, resolved against the same Forum User record. The author, and anyone already being notified as a subscriber, are left out so nobody is told twice. Editing a post notifies only the people the edit newly mentioned.

---

## Pages

The initializer creates five pages. Only the landing page appears in navigation; the others are reached from the interface.

```
/
├── Forums                   /forums                   (in navigation)
├── Forums Messages          /forums-messages
├── Forums Moderation        /forums-moderation
├── New Discussion           /new-discussion
└── Forum Categories Admin   /forum-categories-admin
```

Two display page templates render a single topic and a single reply.

The moderation and category administration pages are reachable by any member who knows the URL, and their interfaces check permissions themselves. To restrict the pages as well, set page permissions from Site Administration after the site is created.

---

## Language Keys

Every string the application displays comes from a language key. The generic ones resolve against Liferay's own language files, and the forum specific ones live in `language/Language.properties`.

`./gradlew buildLang` regenerates the per locale files, and the workspace turns the directory into a batch client extension that imports the keys as language overrides, so no module is involved.

Two things follow from that:

- **The shipped rows are English.** The per locale files are generated as copies until translations are supplied, so a translated forum means providing those translations and rebuilding.
- **The import applies to the default instance.** Serving forums from a second virtual instance means applying the keys there as well.

A key that shares its name with one of Liferay's own keys overrides that key for the whole instance, not only for the forum site, so the application defines its own keys and reuses Liferay's wording where the wording already exists.

---

## Guest Access

Anonymous access passes two gates, and both are configured by the initializer.

`sap-entries.json` declares the service access policy that decides whether the object endpoints can be called without credentials at all. It names the two scoped read operations the fragments use, and Liferay's own default policy already covers the rest.

`resource-permissions.json` is the second gate. It grants view access on the forum objects to the Guest and Site Member roles, and the right to add entries to signed in members.

> A service access policy applies to an operation, not to one object, because every custom object is served by the same REST class. What confines anonymous readers to forum data is the second gate. **Do not grant the Guest role view access on a custom object that is not meant to be public**, because with this policy in place it becomes readable without credentials.

---

## Subscription and Mention Notifications

A member who subscribes to a topic is notified by email and in the notifications panel when a reply arrives, and a mention notifies the same two ways.

Two custom objects carry this. `ForumSubscription` records who is subscribed to which topic, maintained by the fragments as members subscribe and unsubscribe. `ForumNotification` carries one row per recipient, and the notification object actions on that row deliver the message.

Because the subscription store is the forum's own object, Liferay's built in subscribe action is switched off on the topic and message objects, so there is only one place subscription state is written.

Subscriptions are per topic. Creating a topic notifies nobody, so there is no category level subscription to configure.

### How Delivery Works

The Spring Boot client extension works out **who** to notify. Liferay calls it through two object actions, each carrying a signed token that the service validates against the instance it came from.

| Object action | Trigger | Notifies |
| :--- | :--- | :--- |
| New Reply | A reply is created | Everyone subscribed to the topic, except the author, plus anyone the reply mentions |
| Updated Reply | A reply is edited | Only the people the edit newly mentioned |

The service answers immediately and does the work on a background thread, so a member posting a reply is not kept waiting while recipients are resolved. If it is ever overloaded, the work runs on the calling thread rather than a notification being dropped. Every call it makes is bounded by a timeout, so a slow instance produces a logged failure rather than a stalled notification.

The service acts as a service account when it writes back into Liferay, not as the member who posted. Recording an author, writing a notification row and reading a topic's subscribers are all things the forum does on a member's behalf rather than things the member does, so they are not permissions members hold: a member cannot address a notification to somebody else or read who else is subscribed. The account is `default-service-account`, named on the client extension's headless server OAuth entry.

Delivery itself is Liferay's. There is no direct mail server connection: the email object action queues the message through the instance's own notification queue.

Each row is deleted once its actions have run, so notification content is not left sitting in the object. That behaviour can be switched off where object actions are processed asynchronously.

### Notification Templates

Four templates cover the two kinds of event across the two channels, each bound to its own object action:

| Template | Channel | Event |
| :--- | :--- | :--- |
| `forum-notification-email-reply` | Email | A reply to a subscribed topic |
| `forum-notification-email-mention` | Email | A mention |
| `forum-notification-web-reply` | Notifications panel | A reply to a subscribed topic |
| `forum-notification-web-mention` | Notifications panel | A mention |

The wording lives in the templates rather than in the service. A row carries the author's name, the topic title, an excerpt of the post and a link, and the template composes the sentence from those values. So changing what a notification says, or translating it, is a template change: the subject is a locale keyed value and the body is a locale named file, so adding a language means adding `es_ES.html` beside `en_US.html`.

The sender address and display name are the recipient settings on the email templates. Edit them there, or in Notifications under Templates, rather than in the service.

### Environment Variables

| Variable | Default | Purpose |
| :--- | :--- | :--- |
| `LIFERAY_BASE_URL` | `http://localhost:8080` | The instance the service validates tokens against and calls back into. |
| `LIFERAY_DXP_HOST` | `localhost:8080` | Host used when running locally. Provided by the platform on PaaS. |
| `LIFERAY_DXP_PROTOCOL` | `http` | Protocol paired with the host above. |
| `LIFERAY_FORUMS_ETC_SPRING_BOOT_OAHS_CLIENT_SECRET` | none | Client credential for the service account the service writes as. Injected on PaaS and SaaS; needed only for local runs. |
| `LIFERAY_HEADLESS_API_USER` | `test@liferay.com` | Credentials used only when no token is forwarded. |
| `LIFERAY_HEADLESS_API_PASSWORD` | `test` | Password for the above. |
| `FORUMS_SITE_BASE_URL` | `https://www.example.xyz` | Prepended to the link in a notification so it resolves to the deployed site. |
| `FORUMS_NOTIFICATION_PURGE` | `true` | Deletes each notification row once its actions have run. |
| `FORUMS_API_REQUEST_TIMEOUT_SECONDS` | `15` | Bounds a single call into Liferay. |
| `FORUMS_NOTIFICATION_TIMEOUT_SECONDS` | `60` | Bounds resolving all recipients for one post. |
| `FORUMS_NOTIFICATION_FALLBACK_AUTHOR_NAME` | `A community member` | Used when an author's name cannot be resolved. |
| `FORUMS_NOTIFICATION_FALLBACK_TOPIC_TITLE` | `Forum Discussion` | Used when a topic title cannot be resolved. |
| `FORUMS_NOTIFICATION_ASYNC_CORE_SIZE` | `2` | Threads kept for resolving recipients. |
| `FORUMS_NOTIFICATION_ASYNC_MAX_SIZE` | `8` | Maximum threads for the same. |
| `FORUMS_NOTIFICATION_ASYNC_QUEUE_CAPACITY` | `100` | Queued posts before the work runs on the calling thread. |
| `FORUMS_NOTIFICATION_ASYNC_AWAIT_TERMINATION_SECONDS` | `10` | How long shutdown waits for queued work to finish. |

To run the service locally:

```bash
cd client-extensions/liferay-forums-etc-spring-boot
cp .env.example .env   # then edit values
./run-local.sh         # --skip-build restarts from an existing build
```

---

## Behaviour Worth Knowing

**Bans are enforced in the interface.** A banned member sees the composer disabled and a notice explaining why. The endpoints that create content do not consult the ban list, so a ban is a moderation control rather than a security boundary, and the moderation queue is the backstop for anything posted around it. The same applies to the topic priority select and to the one level category limit: all three are enforced where members work rather than at the API.

**A notification in the panel is not a link.** Opening it marks it read and returns to the notifications list. The email carries a link to the discussion, so that is the route to the content.

**View counts exclude anonymous readers.** A topic's view count advances for signed in members only, because the count is written back through an endpoint that anonymous callers cannot use.

**Recent activity is ordered by latest reply.** The tab surfaces the most recently active topics rather than the most replied to, so a new topic with one reply appears above an older one with many.

**Object definitions have headroom for two more.** The application ships 10, and a site initializer becomes unreliable beyond 12. Ship any further objects outside the initializer, as a batch client extension or through the object API after deployment.

**Use a production grade database.** Hypersonic, which the development bundle ships by default, does not sort on text object fields and serialises writes in a way the notification path can stall on. Use MySQL, PostgreSQL or another supported database for anything beyond a first look.