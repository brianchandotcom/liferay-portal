---

description: Guide for generating, deploying, and verifying Liferay Client Extensions for beginners
globs: client-extensions/**
alwaysApply: false

---

# Liferay Client Extension Mentor Protocol (Beginner Friendly)

## 0. Pre-Flight Check
- Verify if the user wants a guided experience with creating a sample Client Extension. If declined, use this guide only as a reference.

## 1. One-Shot Generation: Form That Stores Data in Objects

Guide the user to create a **form that stores data in Liferay Objects**. This involves **multiple client extensions** in one shot:

1. **Object definition (batch type)**
   Create the initial Object definition using a **batch** client extension so the form has a target Object and API to call. The batch type defines the Object (e.g. custom object containing fields the form will collect).

1. **Custom element (form UI)**
   Create a custom-element client extension that renders the form and submits entries to the Object. Use the Object's REST API to create Object Entries.

**ALWAYS** reference the official Liferay samples as the source of truth:
- **Custom element (form UI)**: https://github.com/liferay/liferay-portal/tree/master/workspaces/liferay-sample-workspace/client-extensions/liferay-sample-custom-element-1
- **Batch (Object definition)**: Use Liferay docs/samples for batch client extensions that define Objects
- **Key files**: `client-extension.yaml`, `assets/index.js`, `assets/style.css` for the custom element; batch extension structure for the Object definition

**Form submission and CSRF (critical for permissions)**
- The form must use **`Liferay.Util.fetch`** when available so Liferay attaches session cookies and the **CSRF token (`p_auth`)** to requests. That avoids 403 when Liferay requires the token even for logged-in users.
- If `Liferay.Util.fetch` is not available (e.g. outside Liferay), the form falls back to native `fetch`.
- This is **critical** for permissions: without the CSRF token, users cannot submit Object Entries to the Object created by the batch extension.

**Batch Object Definitions**
When generating `*.batch-engine-data.json` files, prevent these common errors:
- **`indexedLanguageId`**: Only valid for `String` and `Clob` DBTypes. Do NOT include on `Date` or `DateTime` fields or other non-text types.
- **`timeStorage` (required for DateTime/Date fields)**: Liferay requires a `timeStorage` setting on every `DateTime` (or `Date`) object field. Always include this in `objectFieldSettings` for such fields, e.g. `"objectFieldSettings": [ { "name": "timeStorage", "value": "convertToUTC" } ]`. Use `convertToUTC` to store in UTC, or `useInputAsFormatted` to store as entered. Without it, batch import fails with this error: *"The settings 'timeStorage' are required for object field [fieldName]"*.
- **`permissions`**: Not supported in batch imports. Set permissions via UI (Control Panel → Objects → [Object] → Permissions) after deployment.
- **OAuth scopes**: Include both `Liferay.Headless.Batch.Engine.everything` AND `Liferay.Object.Admin.REST.everything` in the `client-extension.yaml`.

**Structure**
- Create `client-extensions/[app-name]/` for the form (custom element) and the Object definition (batch) as separate extensions.
- **No build.gradle needed**: The Liferay workspace plugin automatically detects client extensions in the `client-extensions/` directory.

## 2. Deployment Guidance
- Explain that Blade uses the Gradle Wrapper (`blade gw`) to package code into a `.zip` file and deploy it to the Liferay server
- Deploy command: `blade gw deploy`. Automatically copies the zip file to the Liferay server in `osgi/client-extensions`. Run this for the user as part of the one-shot generation.
- Verify the `bundles/` folder exists. If it doesn't exist, guide users through the initial setup guide
- Verify the Liferay server is currently running at `localhost:8080` so the user can test the extension
- Explain in Liferay, Client Extensions are "detached" from the core. We are building a small application that the portal will "host"

## 3. Log Verification
- Look for the log entry similar to `STARTED [extension-id]`
- Explain this log entry confirms Liferay detected your new extension and registered it in the OSGi registry

## 4. UI Registration & Interaction

Guide the user through the Liferay UI using the following steps:

1. Open a Site Page and enter **Edit Mode** (Click the 'Pencil' icon)

1. Open the **Fragments and Widgets** sidebar (Click the '+' icon)

1. Select the **Widgets** tab and scroll to the **Client Extensions** category

1. Drag `[Extension Name]` onto the page layout

1. Click **Publish** to view the widget and interact with it

## 5. Follow-up Actions
- Ask if the user wants to create another type of Client Extension
- Additional Client Extension samples and types can be found under https://github.com/liferay/liferay-portal/tree/master/workspaces/liferay-sample-workspace/client-extensions/