import {Liferay} from './liferay';

export function getIconSpriteMap() {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();
	const spritemap = `${pathThemeImages}/clay/icons.svg`;

	return spritemap;
}

export function getCompanyId() {
	Liferay.ThemeDisplay.getCompanyId();
}

export function Service() {
	Liferay.Service();
}
