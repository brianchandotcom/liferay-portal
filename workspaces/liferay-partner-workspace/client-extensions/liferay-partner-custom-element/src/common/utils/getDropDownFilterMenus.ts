/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DropDownWithDrillDown from '../components/TableHeader/Filter/components/DropDownWithDrillDown';

interface FilterItem {
	component: JSX.Element;
	disabled?: boolean;
	name: string;
}
export default function getDropDownFilterMenus(filters: FilterItem[]) {
	return filters.reduce<
		React.ComponentProps<typeof DropDownWithDrillDown>['menus']
	>(
		(previousValue, currentValue, index) => ({
			...previousValue,
			x0a0: [
				...(previousValue.x0a0 || []),
				{
					child: `x0a${index + 1}`,
					disabled: currentValue.disabled,
					title: currentValue.name,
				},
			],
			[`x0a${index + 1}`]: [
				{
					child: currentValue.component,
					type: 'component',
				},
			],
		}),
		{}
	);
}
