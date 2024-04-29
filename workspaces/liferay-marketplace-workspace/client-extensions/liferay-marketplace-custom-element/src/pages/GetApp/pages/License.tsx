/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useMemo} from 'react';
import {useOutletContext} from 'react-router-dom';

import CardButton from '../../../components/CardButton/CardButton';
import i18n from '../../../i18n';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';
import {PaidTimeline} from '../components/LicenseSelector/PaidTimeline';
import Container from '../containers/Container';

export default function LicenseSelector() {
	const [
		{
			formState: {isValid},
			license,
			product,
			stepState,
		},
		dispatch,
	] = useGetAppContext();
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
		<Container
			className="d-flex flex-column license-selector-timeline"
			footerProps={{
				primaryButtonProps: {
					children: i18n.translate('continue'),
					disabled: !isValid,
					onClick: () => {
						if (licenseType === 'TRIAL') {
							dispatch({
								payload: 'trial',
								type: 'SET_PAYMENT_METHOD',
							});
						}

						stepState.onNext();
					},
				},
			}}
			title="License Selection"
		>
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
					<div className="d-flex flex-column trial-timeline">
						<p className="d-flex mb-2 trial-info">
							Need help with license calculations?
							<span className="align-items-center d-flex info-button">
								More Info
								<ClayIcon symbol="question-circle-full" />
							</span>
						</p>

						<CardButton
							description="Trial licenses are intended for you to try the app before you buy. Typical trials are 30 days."
							disabled={false}
							icon={
								<span className="trial-card-icon">
									<ClayIcon symbol="percentage-symbol" />
								</span>
							}
							iconRight
							onClick={() => {
								dispatch({
									payload: trialSku,
									type: 'SET_SELETED_SKU',
								});
							}}
							selected={license.selectedSKU}
							title="Trial License"
						/>
					</div>
				)}

				{licenseType === 'PAID' && (
					<PaidTimeline cartUtil={cartUtil} product={product} />
				)}
			</div>
		</Container>
	);
}
