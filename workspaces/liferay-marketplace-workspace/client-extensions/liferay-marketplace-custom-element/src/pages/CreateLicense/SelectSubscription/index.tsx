/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';
import {formatDate} from '../../PublishedAppsDashboard/PublishedDashboardPageUtil';

type SubscriptionSelectionProps = {
	licenseKeyData: any;
	onSelectSubscription: (subscription: any) => void;
	selectedSubscriptionValue?: any;
};

const SelectSubscription = ({
	licenseKeyData,
	onSelectSubscription,
	selectedSubscriptionValue,
}: SubscriptionSelectionProps) => {
	const [subscription, setSubscription] = useState<
		RadioCardContent<String>[]
	>([]);

	const getSubscriptionList = useCallback(async () => {
		setSubscription(
			licenseKeyData.map((licenseKey: any) => {
				const expirationDate =
					licenseKey.endDate === 'DNE'
						? 'DNE'
						: formatDate(licenseKey.endDate);

				return {
					description: (
						<small className="text-success">
							Key activations available:{' '}
							{licenseKey.purchasedCount} of{' '}
							{licenseKey.provisionedCount}
						</small>
					),
					label: `${formatDate(
						licenseKey.startDate
					)} - ${expirationDate}`,
					selected:
						selectedSubscriptionValue?.name === licenseKey.name,
					title: <h3 className="mt-0">{licenseKey.name}</h3>,
					value: licenseKey,
				};
			})
		);
	}, [licenseKeyData, selectedSubscriptionValue]);

	useEffect(() => {
		getSubscriptionList();
	}, [getSubscriptionList]);

	const handleSelect = (radioOption: RadioOption<unknown>) => {
		onSelectSubscription(radioOption.value);

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
