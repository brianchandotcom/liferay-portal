export const Liferay = window.Liferay || {
	OAuth2: {
		getAuthorizeURL: () => '',
		getBuiltInRedirectURL: () => '',
		getIntrospectURL: () => '',
		getTokenURL: () => '',
		getUserAgentApplication: (_serviceName) => {},
	},
	OAuth2Client: {
		FromParameters: (_options) => {
			return {};
		},
		FromUserAgentApplication: (_userAgentApplicationId) => {
			return {};
		},
		fetch: (_url, _options = {}) => {},
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
};