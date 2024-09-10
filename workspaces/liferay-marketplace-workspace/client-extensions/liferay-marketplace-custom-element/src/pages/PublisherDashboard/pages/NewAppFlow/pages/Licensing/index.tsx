/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RadioCard} from '../../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../../components/Section/Section';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../../context/NewAppContext';
import {
	LICENSING_30_DAYS_TRIAL_OPTIONS,
	LICENSING_OPTIONS,
} from '../../constants';

const Licensing = () => {
	const [
		{
			licensing: {licenseType, trial30Day},
		},
		dispatch,
	] = useNewAppContext();

	return (
		<div>
			<Section
				label="App License"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				{LICENSING_OPTIONS.map(({value, ...licensingOption}, index) => (
					<RadioCard
						className="mb-5"
						{...licensingOption}
						key={index}
						onChange={() => {
							dispatch({
								payload: {
									licenseType: value,
								},
								type: NewAppTypes.SET_LICENSING,
							});
						}}
						selected={licenseType === value}
					/>
				))}
			</Section>

			<Section
				label="30-day Trial"
				required
				tooltip="Trials can be offered to users for 30 days.  After this time, they will be notified of their pending trial expiration and given the opportunity to purchase the app at full price."
				tooltipText="More Info"
			>
				<div className="informing-licensing-terms-page-day-trial-container">
					{LICENSING_30_DAYS_TRIAL_OPTIONS.map(
						({value, ...licensingOption}, index) => (
							<RadioCard
								{...licensingOption}
								key={index}
								onChange={() => {
									dispatch({
										payload: {
											trial30Day: value,
										},
										type: NewAppTypes.SET_LICENSING,
									});
								}}
								selected={trial30Day === value}
							/>
						)
					)}
				</div>
			</Section>
		</div>
	);
};

export default Licensing;
