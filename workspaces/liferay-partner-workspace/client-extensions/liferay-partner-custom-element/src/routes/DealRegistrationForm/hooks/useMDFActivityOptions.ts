/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import MDFRequestActivity from '../../../common/interfaces/mdfRequestActivity';

export default function useMDFActivityOptions(
	mdfActivities: MDFRequestActivity[] | undefined,
	handleSelected: (selectedActivity?: MDFRequestActivity) => void
) {
	const onMDFActivitySelected = (
		event: React.ChangeEvent<HTMLInputElement>
	) => {
		const optionSelected = mdfActivities?.find(
			(mdfActivity) => mdfActivity.id === Number(event.target.value)
		);

		handleSelected(optionSelected);
	};

	return {
		mdfActivitiesOptions: useMemo(
			() =>
				mdfActivities?.map((mdfActivity) => ({
					label: mdfActivity.name,
					value: mdfActivity.id,
				})),
			[mdfActivities]
		),
		onMDFActivitySelected,
	};
}
