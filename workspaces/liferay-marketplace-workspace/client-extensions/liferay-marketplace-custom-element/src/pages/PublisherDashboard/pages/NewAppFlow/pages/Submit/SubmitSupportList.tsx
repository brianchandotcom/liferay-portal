/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import {NewAppInitialState} from '../../../../../../context/NewAppContext';
import i18n from '../../../../../../i18n';

type SubmitSuportListProps = {
	appData: NewAppInitialState;
};

type SupportContent = {
	symbol: string;
	title: string;
	url?: string;
};

const SupportContent = ({symbol, title, url}: SupportContent) => {
	return (
		<div className="border mt-5 p-4 rounded-lg">
			<div className="submit-support-main-info">
				<div className="submit-support-icon">
					<ClayIcon
						aria-label="Icon"
						className="submit-support-icon-image"
						symbol={symbol}
					/>
				</div>
				<div className="submit-support-info">
					<span className="submit-support-info-text">{title}</span>
					{url && (
						<a
							className="submit-support-info-description text-truncate"
							href={url}
							target="_blank"
							title={url}
						>
							{url}
						</a>
					)}
				</div>
			</div>
		</div>
	);
};

const SubmitSupportList = ({appData}: SubmitSuportListProps) => {
	return (
		<>
			<SupportContent
				symbol="link"
				title={i18n.translate('support-url')}
				url={appData.support.url}
			/>

			<SupportContent
				symbol="globe"
				title={i18n.translate('publisher-website-url')}
				url={appData.support.publisherWebsiteURL}
			/>

			<SupportContent
				symbol="envelope-open"
				title={i18n.translate('support-email-address')}
				url={appData.support.email}
			/>

			<SupportContent
				symbol="phone"
				title={i18n.translate('support-phone-number')}
				url={appData.support.phone}
			/>

			<SupportContent
				symbol="info-book"
				title={i18n.translate('app-usage-terms-url')}
				url={appData.support.appUsageTermsURL}
			/>

			<SupportContent
				symbol="order-form-tag"
				title={i18n.translate('app-documentation-url')}
				url={appData.support.documentationURL}
			/>

			<SupportContent
				symbol="sites"
				title={i18n.translate('app-installation-guide-url')}
				url={appData.support.installationGuideURL}
			/>
		</>
	);
};

export default SubmitSupportList;
