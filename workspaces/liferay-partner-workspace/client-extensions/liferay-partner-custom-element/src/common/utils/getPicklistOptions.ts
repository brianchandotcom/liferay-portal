/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import LiferayPicklist from '../interfaces/liferayPicklist';

export default function getPicklistOptions<T>(
	options: React.OptionHTMLAttributes<HTMLOptionElement>[],
	handleSelected: (option: LiferayPicklist, optionalValue?: T) => void
) {
	const onSelected = (
		event: React.ChangeEvent<HTMLInputElement>,
		optionalValue?: T
	) => {
		const optionSelected = options.find(
			(option) => option.value === event.target.value
		);

		handleSelected(
			{
				key: optionSelected?.value as string,
				name: optionSelected?.label as string,
			},
			optionalValue
		);
	};

	return {
		onSelected,
		options,
	};
}
