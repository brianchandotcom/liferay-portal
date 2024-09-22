/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useNavigate} from 'react-router-dom';

import cash from '../../assets/images/cash.svg';
import cloudUpload from '../../assets/images/cloud_upload.svg';
import {GateCard} from '../../components/Card/GateCard';
import {Header} from '../../components/Header/Header';
import i18n from '../../i18n';
import {Liferay} from '../../liferay/liferay';
import {getSiteURL} from '../../utils/site';

import './PublisherGatePage.scss';

export function PublisherGatePage() {
	const navigate = useNavigate();

	return (
		<div className="publisher-gate-page-body">
			<Header
				description={i18n.translate(
					'we-are-happy-to-have-you-interested-in-the-liferay-marketplace-at-the-moment-we-are-working-on-enhancing-the-experience-for-our-publishers-in-the-marketplace-if-you-are-an-existing-liferay-developer-or-partner-you-can-choose-to-join-our-pilot-group-of-publishers-to-submit-apps-to-the-marketplace-continue-to-request-a-publisher-account'
				)}
				title={i18n.translate(
					'becoming-a-liferay-marketplace-publisher'
				)}
			/>

			<GateCard
				description={i18n.translate(
					'the-liferay-marketplace-is-the-premier-place-for-liferay-customers-to-find-pre-built-pre-approved-app-extensions-to-quickly-extend-the-liferay-platform-to-new-and-legacy-technologies'
				)}
				image={{
					description: i18n.translate('cloud-upload'),
					svg: cloudUpload,
				}}
				label={i18n.translate('free')}
				title={i18n.translate(
					'publish-apps-to-the-liferay-marketplace'
				)}
			/>

			<GateCard
				description={i18n.translate(
					'the-liferay-marketplace-gives-you-the-opportunity-to-monetize-your-app-or-solutions-from-a-single-use-case-to-many-while-engaging-with-new-customer-opportunities-and-generating-ongoing-revenue'
				)}
				image={{
					description: i18n.translate('cash'),
					svg: cash,
				}}
				title={i18n.translate('monetize-your-apps-and-solutions')}
			/>

			<hr className="publisher-gate-page-divider" />

			<div className="publisher-gate-page-button-container">
				<ClayButton
					className="mr-4 publisher-gate-page-button"
					displayType="secondary"
					onClick={() => {
						Liferay.Util.navigate(
							`${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/home`
						);
					}}
				>
					{i18n.translate('go-back-to-marketplace')}
				</ClayButton>

				<ClayButton
					className="publisher-gate-page-button"
					onClick={() => navigate('/request-account')}
				>
					{i18n.translate('request-account')}
				</ClayButton>
			</div>
		</div>
	);
}
