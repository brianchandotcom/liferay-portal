interface ILiferay {
	MarketplaceCustomerFlow: {appId: number};
	Service: Function;
	ThemeDisplay: {
		getCompanyId: () => string;
		getPathThemeImages: () => string;
	};
	detach: Function;
	on: Function;
}
declare global {
	interface Window {
		Liferay: ILiferay;
	}
}

export const Liferay = window.Liferay || {
	MarketplaceCustomerFlow: 0,
	Service: {},
	ThemeDisplay: {
		getCompanyId: () => '',
		getPathThemeImages: () => '',
	},
	detach: (
		type: keyof WindowEventMap,
		callback: EventListenerOrEventListenerObject
	) => window.removeEventListener(type, callback),
	on: (
		type: keyof WindowEventMap,
		callback: EventListenerOrEventListenerObject
	) => window.addEventListener(type, callback),
};
