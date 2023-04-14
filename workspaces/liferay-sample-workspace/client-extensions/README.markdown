# Liferay Sample Workspace

## Client Extensions

### liferay-sample-etc-frontend-1

This sample was created to showcase that you can combine multiple client extensions in a single `client-extension.yaml`.

Client Extensions Included:
- liferay-sample-etc-frontend-1-custom-element-header (`customElement`)
- liferay-sample-etc-frontend-1-custom-element-sidebar (`customElement`)
- liferay-sample-etc-frontend-1-custom-element-theme-spritemap (`themeSpritemap`)
- liferay-sample-etc-frontend-1-fds-cell-renderer (`fdsCellRenderer`)

### liferay-sample-etc-frontend-2

This sample was created to showcase that you can share code across multiple client extensions in a single `client-extension.yaml`.

Each client extension shares the same code from `shared-utils.js` without duplicating or re-requesting the resource.

Client Extensions Included:
- liferay-sample-etc-frontend-2-custom-element (`customElement`)
- liferay-sample-etc-frontend-2-fds-cell-renderer (`fdsCellRenderer`)