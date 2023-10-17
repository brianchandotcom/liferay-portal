/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import RadioCardList from '../../../components/RadioCardList/RadioCardList';

interface SubscriptionSelectionProps {
	onSelectSubscription: (subscription: Subscription) => void;
	selectedSubscriptionValue: string | undefined;
}

const SelectSubscription = ({
	onSelectSubscription,
	selectedSubscriptionValue,
}: SubscriptionSelectionProps) => {
	const avaliableKeys = {
		provisionedCount: 1,
		purchasedCount: 1,
	};
	const supportLifeStartDate = 'Sep 24, 2023';
	const supportLifeEndDate = 'Sep 24, 2024';

	const contentList = [
		{
			description: `Key activations available: ${avaliableKeys.purchasedCount} of ${avaliableKeys.provisionedCount}`,
			label: `${supportLifeStartDate} - ${supportLifeEndDate}`,
			selected: selectedSubscriptionValue === 'Trial',
			title: 'Trial',
			value: 'Trial',
		},
	];

	const [subscription, setSubscription] = useState<any>(contentList);

	const handleSelect = (radioOption: RadioOption<any>) => {
		onSelectSubscription(radioOption.value);

		setSubscription((previousValue: any) =>
			previousValue.map((subscription: any, index: number) => ({
				...subscription,
				selected: index === radioOption.index,
			}))
		);
	};

	return (
		<>
			<div className="mb-4 mt-3">
				Generate licenses with a selected subscription term.
			</div>

			<div className="radio-card-subscription">
				<RadioCardList
					contentList={subscription}
					customization={true}
					leftRadio
					onSelect={handleSelect}
				/>
			</div>
		</>
	);
};

export default SelectSubscription;
