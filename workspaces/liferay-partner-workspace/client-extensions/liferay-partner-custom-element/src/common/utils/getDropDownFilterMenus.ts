/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DrilldownMenuItems from '../components/TableHeader/Filter/components/DropDownWithDrillDown/components/DrilldownMenuItems';
import {FilterProps} from '../components/TableHeader/Filter/components/FilterSelector/FilterSelector';

export interface FilterItem {
	component: FilterProps;
	disabled?: boolean;
	name: string;
}

type Menu = {
	[id: string]: React.ComponentProps<typeof DrilldownMenuItems>['items'];
};

export default function getDropDownFilterMenus(filters: FilterItem[]) {
	return filters.reduce<Menu>(
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
