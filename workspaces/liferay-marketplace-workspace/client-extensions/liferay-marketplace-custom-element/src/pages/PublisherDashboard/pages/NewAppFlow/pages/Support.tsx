/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Input} from '../../../../../components/Input/Input';
import {Section} from '../../../../../components/Section/Section';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../context/NewAppContext';
import i18n from '../../../../../i18n';

const Support = () => {
	const [
		{
			pricing: {priceModel},
			support,
		},
		dispatch,
	] = useNewAppContext();

	const isPaidApp = priceModel === 'Paid';

	return (
		<Section
			label={i18n.translate('app-support-and-help')}
			tooltip={i18n.translate(
				'define-the-support-and-help-references-users-can-access-these-resources-pre-and-post-purchase-to-find-out-more-information-about-your-app-or-solution'
			)}
			tooltipText={i18n.translate('more-info')}
		>
			<Input
				label={i18n.translate('support-url')}
				onChange={(event) =>
					dispatch({
						payload: {
							url: event.target.value,
						},
						type: NewAppTypes.SET_SUPPORT,
					})
				}
				placeholder="https://"
				value={support.url}
			/>

			<Input
				label={i18n.translate('publisher-website-url')}
				onChange={(event) =>
					dispatch({
						payload: {
							publisherWebsiteURL: event.target.value,
						},
						type: NewAppTypes.SET_SUPPORT,
					})
				}
				placeholder="https://"
				required={isPaidApp}
				value={support.publisherWebsiteURL}
			/>

			<Input
				label={i18n.translate('support-email')}
				onChange={(event) =>
					dispatch({
						payload: {
							email: event.target.value,
						},
						type: NewAppTypes.SET_SUPPORT,
					})
				}
				required={isPaidApp}
				value={support.email}
			/>

			<Input
				label={i18n.translate('support-phone')}
				onChange={(event) =>
					dispatch({
						payload: {
							phone: event.target.value,
						},
						type: NewAppTypes.SET_SUPPORT,
					})
				}
				required={isPaidApp}
				value={support.phone}
			/>

			<Input
				label="App usage terms (EULA) URL"
				onChange={(event) =>
					dispatch({
						payload: {
							appUsageTermsURL: event.target.value,
						},
						type: NewAppTypes.SET_SUPPORT,
					})
				}
				value={support.appUsageTermsURL}
			/>

			<Input
				label={i18n.translate('app-documentation-url')}
				onChange={(event) =>
					dispatch({
						payload: {
							documentationURL: event.target.value,
						},
						type: NewAppTypes.SET_SUPPORT,
					})
				}
				value={support.documentationURL}
			/>

			<Input
				label={i18n.translate(
					'app-installation-and-uninstallation-guide-url'
				)}
				onChange={(event) =>
					dispatch({
						payload: {
							installationGuideURL: event.target.value,
						},
						type: NewAppTypes.SET_SUPPORT,
					})
				}
				value={support.installationGuideURL}
			/>
		</Section>
	);
};

export default Support;
