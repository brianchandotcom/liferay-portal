const Liferay = window.Liferay || {
	ThemeDisplay: {
		getCompanyGroupId: () => 0,
		getPathContext: () => "",
		getPortalURL: () => "",
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		isSignedIn: () => false,
	},
	authToken: "",
	OAuth: {
		getAuthorizeURL: () => "",
		getBuiltInRedirectURL: () => "",
		getIntrospectURL: () => "",
		getTokenURL: () => "",
		getUserAgentApplication: (serviceName) => {}
	}
};

export { Liferay };