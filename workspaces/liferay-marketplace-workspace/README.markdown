# Marketplace Workspace

## Setup
1. Set up the [liferay cli environment](https://github.com/liferay/liferay-cli#liferay---liferay-client-extension-control-cli)
2. Make sure to also do the steps for [generating local certificates](https://github.com/liferay/liferay-cli/blob/main/docs/GETTING_STARTED.markdown#generate-localdev-certificates)
3. Download [Marketplace Tiltfile](https://gist.github.com/ryanschuhler/8f6f6b6bdab173dadd1b14f891f345e2) and put in `/full/path/to/repo/liferay-portal/workspaces/liferay-marketplace-workspace/client-extensions/` 
4. Configure the liferay cli to point to the marketplace workspace
`liferay config set extension.client-extension.dir /full/path/to/repo/liferay-portal/workspaces/liferay-marketplace-workspace/client-extensions/`
5. Start the extension environment with `liferay ext start -v`
6. Liferay will startup and be accessible at [https://dxp.lfr.dev](https://dxp.lfr.dev) as well as the Tilt UI at [http://127.0.0.1:10350/](http://127.0.0.1:10350/)
7. To shutdown environment run `liferay runtime stop`
