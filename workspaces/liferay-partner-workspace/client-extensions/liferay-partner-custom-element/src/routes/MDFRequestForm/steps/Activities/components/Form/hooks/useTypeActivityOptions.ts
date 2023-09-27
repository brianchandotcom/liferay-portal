/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OptionHTMLAttributes} from 'react';

import LiferayPicklist from '../../../../../../../common/interfaces/liferayPicklist';

export default function useTypeActivityOptions(
	typeActivities: OptionHTMLAttributes<HTMLOptionElement>[] | undefined,
	handleSelected: (option: LiferayPicklist) => void,
	handleClearForm: () => void
) {
	const onTypeActivitySelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const optionSelected = typeActivities?.find((typeActivity) => {
			return typeActivity.value === event.target.value;
		});

		if (optionSelected) {
			handleSelected({
				key: optionSelected.value as string,
				name: optionSelected.label,
			});

			handleClearForm();
		}
	};

	return {
		onTypeActivitySelected,
		typeActivitiesOptions: typeActivities,
	};
}
