/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo, useState} from 'react';

import {Header} from '../../../../../../components/Header/Header';
import {Input} from '../../../../../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../../../../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../../../../../components/Section/Section';
import {submitSpecification} from '../../../../../../utils/util';
import {useAppContext} from '../AppContext/AppManageState';
import {TYPES} from '../AppContext/actionTypes';

import './ProvideAppSupportAndHelpPage.scss';
import {
	PRODUCT_PRICE_MODEL,
	PRODUCT_SUPPORT_SPECIFICATION_KEY,
} from '../../../../../../enums/Product';

interface ProvideAppSupportAndHelpPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function ProvideAppSupportAndHelpPage({
	onClickBack,
	onClickContinue,
}: ProvideAppSupportAndHelpPageProps) {
	const [processing, setProcessing] = useState(false);
	const [
		{
			appDocumentationURL,
			appInstallationGuideURL,
			appProductId,
			appUsageTermsURL,
			priceModel,
			publisherWebsiteURL,
			supportEmail,
			supportPhone,
			supportURL,
		},
		dispatch,
	] = useAppContext();

	const bodySpecification = useMemo(
		() => [
			{
				specificationKey: PRODUCT_SUPPORT_SPECIFICATION_KEY.SUPPORT_URL,
				value: supportURL?.value,
			},
			{
				specificationKey:
					PRODUCT_SUPPORT_SPECIFICATION_KEY.PUBLISHER_WEBSITE_URL,
				value: publisherWebsiteURL?.value,
			},
			{
				specificationKey:
					PRODUCT_SUPPORT_SPECIFICATION_KEY.SUPPORT_EMAIL,
				value: supportEmail?.value,
			},
			{
				specificationKey:
					PRODUCT_SUPPORT_SPECIFICATION_KEY.SUPPORT_PHONE,
				value: supportPhone?.value,
			},
			{
				specificationKey:
					PRODUCT_SUPPORT_SPECIFICATION_KEY.APP_USAGE_TERMS_URL,
				value: appUsageTermsURL?.value,
			},
			{
				specificationKey:
					PRODUCT_SUPPORT_SPECIFICATION_KEY.APP_DOCUMENTATION_URL,
				value: appDocumentationURL?.value,
			},
			{
				specificationKey:
					PRODUCT_SUPPORT_SPECIFICATION_KEY.APP_INSTALLATION_GUIDE_URL,
				value: appInstallationGuideURL?.value,
			},
		],
		[
			appDocumentationURL?.value,
			appInstallationGuideURL?.value,
			appUsageTermsURL?.value,
			publisherWebsiteURL?.value,
			supportEmail?.value,
			supportPhone?.value,
			supportURL?.value,
		]
	);

	const isPaidApp = priceModel.value === PRODUCT_PRICE_MODEL.PAID;

	return (
		<div className="provide-app-support-and-help-page-container">
			<div className="provide-app-support-and-help-page-header">
				<Header
					description="Inform the support and help references. This will impact how users will experience this app’s customer support and learning."
					title="Provide app support and help"
				/>
			</div>

			<Section
				label="App Support and help"
				tooltip="Define the support and help references. Users can access these resources pre and post purchase to find out more information about your app or solution."
				tooltipText="More Info"
			>
				<Input
					label="Support URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: supportURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_SUPPORT_URL,
						})
					}
					placeholder="http:// Enter app name"
					value={supportURL?.value}
				/>

				<Input
					label="Publisher website URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: publisherWebsiteURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_PUBLISHER_WEBSITE_URL,
						})
					}
					placeholder="http:// Enter app name"
					required={isPaidApp}
					value={publisherWebsiteURL?.value}
				/>

				<Input
					label="Support Email"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: supportEmail?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_SUPPORT_EMAIL,
						})
					}
					placeholder="Enter Support Email Address"
					required={isPaidApp}
					value={supportEmail.value}
				/>

				<Input
					label="Support Phone"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: supportPhone?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_SUPPORT_PHONE,
						})
					}
					placeholder="Enter Support Phone"
					required={isPaidApp}
					value={supportPhone.value}
				/>

				<Input
					label="App usage terms (EULA) URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: appUsageTermsURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_USAGE_TERMS_URL,
						})
					}
					placeholder="http:// Enter app name"
					value={appUsageTermsURL?.value}
				/>

				<Input
					label="App documentation URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: appDocumentationURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_DOCUMENTATION_URL,
						})
					}
					placeholder="http:// Enter app name"
					value={appDocumentationURL?.value}
				/>

				<Input
					label="App installation and uninstallation guide URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: appInstallationGuideURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_INSTALLATION_AND_UNINSTALLATION_GUIDE_URL,
						})
					}
					placeholder="http://Enter app name"
					value={appInstallationGuideURL?.value}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={
					!isPaidApp
						? processing
						: processing ||
							!supportEmail.value.length ||
							!supportPhone.value.length ||
							!publisherWebsiteURL.value.length
				}
				isLoading={processing}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					setProcessing(true);

					await submitSpecification(appProductId, bodySpecification);

					setProcessing(false);

					onClickContinue();
				}}
				showBackButton={true}
			/>
		</div>
	);
}
