export const Liferay = window.Liferay || {
	OAuth2: {
		getAuthorizeURL: () => '',
		getBuiltInRedirectURL: () => '',
		getIntrospectURL: () => '',
		getTokenURL: () => '',
		getUserAgentApplication: (serviceName) => {},
	},
	OAuth2Client: {
		FromParameters: (options) => {
			return {};
		},
		FromUserAgentApplication: (userAgentApplicationId) => {
			return {};
		},
		fetch: (url, options = {}) => {},
	},
	ThemeDisplay: {
		getCompanyGroupId: () => 0,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		isSignedIn: () => {
			return false;
		},
	},
	authToken: '',
}
