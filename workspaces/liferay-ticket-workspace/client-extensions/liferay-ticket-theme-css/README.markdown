# Dev Mode Configuration

By deploying the dev version of this client extension, the build is changed as follows:

- A custom `gulpfile.mjs` watches the `.scss` files in `src` and builds them using Dart SASS
- A javascript extension is deployed which enables `Browsersync` to run in Liferay
- When updates are made, `Browsersync` is notified and the css is updated without having to explicitly deploy or refresh the browser.

## Example:

![Example](./liveedit.gif)

## Limitations:

- Compiling clay is relatively slow (4-5 seconds) so be patient. But `main.css` changes are updated relatively quickly.
- This is a custom build that might not 100% reflect the normal gradle build

## Setup:

1. Deploy in dev mode:

    ```
    ../../gradlew clean deployDev packageRunServe
    ```

2. Enable the `Browsersync` javascript extension. It is named **Liferay Ticket Theme CSS (Dev Mode)** and needs to be added to whatever context you are using to test (e.g. you can add it to all pages or just the page you are testing)

3. Update `scss` files - the css changes should show automatically