/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
