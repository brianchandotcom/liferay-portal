/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';

interface SubscriptionSelectionProps {
	onSelectSubscription: (subscription: string) => void;
	selectedSubscriptionValue?: string;
}

const licenseKeyData = {
	avaliableKeys: {
		provisionedCount: 1,
		purchasedCount: 1,
	},
	supportLifeEndDate: 'Sep 24, 2024',
	supportLifeStartDate: 'Sep 24, 2023',
};

const SelectSubscription = ({
	onSelectSubscription,
	selectedSubscriptionValue,
}: SubscriptionSelectionProps) => {
	const [subscription, setSubscription] = useState<
		RadioCardContent<String>[]
	>([]);

	const getSubscriptionList = useCallback(async () => {
		const contentList: RadioCardContent<String>[] = [
			{
				description: (
					<small className="text-success">
						Key activations available:{' '}
						{licenseKeyData.avaliableKeys.purchasedCount} of{' '}
						{licenseKeyData.avaliableKeys.provisionedCount}
					</small>
				),
				label: `${licenseKeyData.supportLifeStartDate} - ${licenseKeyData.supportLifeEndDate}`,
				selected: selectedSubscriptionValue === 'Trial',
				title: <h3 className="mt-0">Trial</h3>,
				value: 'Trial',
			},
		];

		setSubscription(contentList);
	}, [selectedSubscriptionValue]);

	useEffect(() => {
		getSubscriptionList();
	}, [getSubscriptionList]);

	const handleSelect = (radioOption: RadioOption<String>) => {
		onSelectSubscription(String(radioOption.value));

		setSubscription((previousValue) =>
			previousValue.map((subscription, index) => ({
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
					leftRadio
					onSelect={handleSelect}
				/>
			</div>
		</>
	);
};

export default SelectSubscription;
