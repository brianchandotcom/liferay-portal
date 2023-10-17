/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// import {useState} from 'react';

import RadioCardList from '../../../components/RadioCardList/RadioCardList';

const SelectSubscription = () => {
	// const [selectedSubscription, setSelectedSubscription] = useState<any>();

	const avaliableKeys = {
		provisionedCount: 1,
		purchasedCount: 1,
	};

	const supportLifeStartDate = 'Sep 24, 2023';
	const supportLifeEndDate = 'Sep 24, 2024';

	const contentList = [
		{
			customization: true,
			description: `Key activations available: ${avaliableKeys.purchasedCount} of ${avaliableKeys.provisionedCount}`,
			label: `${supportLifeStartDate} - ${supportLifeEndDate}`,
			selected: true,
			title: 'Trial',
			value: 1,
		},
	];

	return (
		<>
			<div className="mb-4 mt-3">
				Generate licenses with a selected subscription term.
			</div>

			<div className="radio-card-subscription">
				<RadioCardList
					contentList={contentList}
					leftRadio
					onSelect={() => null}
				/>
			</div>
		</>
	);
};

export default SelectSubscription;
