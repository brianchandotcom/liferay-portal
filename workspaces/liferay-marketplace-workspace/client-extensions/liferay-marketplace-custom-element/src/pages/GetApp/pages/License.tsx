/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useMemo} from 'react';
import {useOutletContext} from 'react-router-dom';

import CardButton from '../../../components/CardButton/CardButton';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';
import {PaidTimeline} from '../components/LicenseSelector/PaidTimeline';
import {TrialTimeline} from '../components/LicenseSelector/TrialTimeline';

export default function LicenseSelector() {
	const [{license, product}, dispatch] = useGetAppContext();
	const {
		cartUtil,
		productBasePriceAndTrial: {trialSku},
	} = useOutletContext<GetAppOutletContext>();

	const licenseType = license.type;

	const licenseTypes = useMemo(
		() => [
			{
				description: 'Try now. Pay later.',
				disabled: !trialSku,
				icon: 'check-circle',
				title: licenseType === 'TRIAL' ? '30-day Trial' : 'Trial',
				type: 'TRIAL',
			},
			{
				description: 'Pay Today',
				icon: 'credit-card',
				title: 'Paid',
				type: 'PAID',
			},
		],
		[licenseType, trialSku]
	);

	return (
		<div className="license-selector-timeline">
			<h1 className="my-4 text-center">License Selection</h1>

			<div className="license-selector mb-6">
				{licenseTypes.map((_licenseType, index) => (
					<CardButton
						{..._licenseType}
						icon={
							<span className="license-icon">
								<ClayIcon symbol={_licenseType.icon} />
							</span>
						}
						key={index}
						onClick={() =>
							dispatch({
								payload: _licenseType.type as any,
								type: 'SET_LICENSE_TYPE',
							})
						}
						selected={licenseType === _licenseType.type}
					/>
				))}
			</div>

			<div className="timeline-container">
				{licenseType === 'TRIAL' && (
					<TrialTimeline
						handleLicenseSelect={() =>
							dispatch({
								payload: trialSku,
								type: 'SET_SELETED_SKU',
							})
						}
					/>
				)}

				{licenseType === 'PAID' && (
					<PaidTimeline cartUtil={cartUtil} product={product} />
				)}
			</div>
		</div>
	);
}
