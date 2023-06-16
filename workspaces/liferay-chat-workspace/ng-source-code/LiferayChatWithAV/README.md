# Liferay Chat

The "Liferay Chat" repository contains an Angular application designed to be integrated with Liferay, using the headless capabilities, to store data in Liferay objects. The application also utilizes a Node.js WebSocket server for real-time communication. The repository includes a custom element named "liferay-chat-window" that can be used as a remote app within Liferay.

## Features

- Angular application integrated with Liferay using the headless API
- Real-time communication using a Node.js WebSocket server
- Custom element "liferay-chat-window" for remote app integration with Liferay

## Prerequisites

Before you begin, ensure you have the following installed:

- Node.js (https://nodejs.org)
- Angular CLI (https://angular.io/cli)
- Liferay DXP (https://www.liferay.com)


## Integration with Liferay

To integrate the "liferay-chat-window" custom element as a remote app within Liferay, follow these steps:

1. Build the Angular project for production:

   ```bash
   ng build --prod
   ```

2. Copy the generated `dist` directory to your Liferay portal's web server:

   ```bash
   cp -R dist <path-to-your-liferay-portal>/tomcat/webapps/liferay-chat
   ```

3. Log in to your Liferay portal as an administrator.

4. Create a new remote app:

    - Go to "Control Panel" > "Apps" > "Remote Apps".

    - Click on the "Add" button.

    - Fill in the required fields, providing a name and the URL to the custom element:

        - Name: Liferay Chat
        - HTML Element Name: `liferay-chat-window`
        - JavaScript URL: `<URL to compiled JS file>`

    - Save the remote app.

5. Add the remote app to a Liferay page:

    - Go to the page where you want to add the "Liferay Chat" remote app.

    - Edit the page.

    - Click on the "+" button to add a new element.

    - Select the "Liferay Chat" remote app from the list of available apps.

    - Configure the remote app element as desired.

    - Save the changes.

    - The "Liferay Chat" custom element should now be visible and accessible as a remote app within your Liferay portal.

## Contributing

Contributions are welcome! If you have any suggestions, bug reports, or feature requests, please open an issue or submit a pull request.

