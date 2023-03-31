import {Liferay} from './liferay';

export function getIconSpriteMap() {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();
	const spritemap = `${pathThemeImages}/clay/icons.svg`;

	return spritemap;
}

export function getCompanyId() {
	return Liferay.ThemeDisplay.getCompanyId();
}

export function Service() {
	return Liferay.Service();
}
