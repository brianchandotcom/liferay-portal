/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';

import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';
import useProvisioningKoroneikiOAuth2 from '../../GetAppPage/hooks/useProvisioningKoroneikiOAuth2';
import {formatDate} from '../../PublishedAppsDashboard/PublishedDashboardPageUtil';

type SubscriptionSelectionProps = {
	onSelectSubscription: (subscription: any) => void;
	selectedSubscriptionValue?: any;
};

const SelectSubscription = ({
	onSelectSubscription,
	selectedSubscriptionValue,
}: SubscriptionSelectionProps) => {
	const [subscription, setSubscription] = useState<
		RadioCardContent<String>[]
	>([]);

	const params = useParams();

	const orderId = Number(params.orderId);

	const provisioningKoroneikiOAuth2 = useProvisioningKoroneikiOAuth2();

	const getSubscriptionList = useCallback(async () => {
		const _subscriptions = await provisioningKoroneikiOAuth2.getSubscriptions(
			orderId
		);

		const subscriptions = _subscriptions.map((licenseKey: any) => {
			const expirationDate = licenseKey?.endDate
				? formatDate(new Date(licenseKey.endDate).toISOString())
				: 'DNE';

			return {
				description: (
					<small className="text-success">
						Key activations available: {licenseKey.provisionedCount}{' '}
						of {licenseKey.purchasedCount}
					</small>
				),
				label: `${formatDate(
					licenseKey.startDate
				)} - ${expirationDate}`,
				selected: selectedSubscriptionValue?.name === licenseKey.name,
				title: <h3 className="mt-0">{licenseKey.name}</h3>,
				value: licenseKey,
			};
		});

		setSubscription(subscriptions);
	}, [orderId, provisioningKoroneikiOAuth2, selectedSubscriptionValue?.name]);

	useEffect(() => {
		getSubscriptionList();
	}, [getSubscriptionList]);

	const handleSelect = (radioOption: RadioOption<any>) => {
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
