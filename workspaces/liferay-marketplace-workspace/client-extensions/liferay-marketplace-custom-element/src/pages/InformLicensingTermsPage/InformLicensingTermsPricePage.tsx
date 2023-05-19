/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {Header} from '../../components/Header/Header';
import {LicensePriceCard} from '../../components/LicensePriceCard/LicensePriceCard';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';

import './InformLicensingTermsPage.scss';
import {getSKUById, patchSKUById} from '../../utils/api';

interface InformLicensingTermsPricePageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function InformLicensingTermsPricePage({
	onClickBack,
	onClickContinue,
}: InformLicensingTermsPricePageProps) {
	const [{appLicensePrice, skuVersionId}, _] = useAppContext();

	return (
		<div className="informing-licensing-terms-page-container">
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renew experience."
				title="Inform licensing terms"
			/>

			<Section
				label="Standard License prices"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				<LicensePriceCard />
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appLicensePrice}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitLicensePrice = async () => {
						const skuJSON = await getSKUById(skuVersionId);

						const skuBody = {
							...skuJSON,
							price: parseFloat(appLicensePrice),
						};

						await patchSKUById(skuVersionId, skuBody);
					};

					submitLicensePrice();

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
