/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {ReactNode} from 'react';

import {Liferay} from '../../../services/liferay';

const getIconSpriteMap = () => {
	const pathThemeImages = Liferay.ThemeDisplay.getPathThemeImages();
	const spritemap = `${pathThemeImages}/clay/icons.svg`;

	return spritemap;
};

interface IProps {
	children: ReactNode;
}

const ClayIconProvider = ({children}: IProps) => {
	return (
		<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
			{children}
		</ClayIconSpriteContext.Provider>
	);
};

export default ClayIconProvider;
