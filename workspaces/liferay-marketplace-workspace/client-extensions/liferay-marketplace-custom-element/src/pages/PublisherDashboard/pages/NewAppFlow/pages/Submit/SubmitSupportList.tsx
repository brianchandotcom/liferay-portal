/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import {NewAppInitialState} from '../../../../../../context/NewAppContext';

type SubmitSuportListProps = {
	appData: NewAppInitialState;
};

const SubmitSupportList = ({appData}: SubmitSuportListProps) => {
	return (
		<>
			<div className="border mt-5 p-4 rounded-lg">
				<div className="card-link-main-info">
					<div className="card-link-icon">
						<ClayIcon
							aria-label="Icon"
							className="card-link-icon-image"
							symbol="link"
						/>
					</div>

					<div className="card-link-info">
						<span className="card-link-info-text">Support URL</span>
						{appData.support.url && (
							<a
								className="card-link-info-description text-truncate"
								href={appData.support.url}
								target="_blank"
								title={appData.support.url}
							>
								{appData.support.url}
							</a>
						)}
					</div>
				</div>
			</div>

			<div className="border mt-5 p-4 rounded-lg">
				<div className="card-link-main-info">
					<div className="card-link-icon">
						<ClayIcon
							aria-label="Icon"
							className="card-link-icon-image"
							symbol="globe"
						/>
					</div>

					<div className="card-link-info">
						<span className="card-link-info-text">
							Publisher website URL
						</span>
						{appData.support.publisherWebsiteURL && (
							<a
								className="card-link-info-description text-truncate"
								href={appData.support.publisherWebsiteURL}
								target="_blank"
								title={appData.support.publisherWebsiteURL}
							>
								{appData.support.publisherWebsiteURL}
							</a>
						)}
					</div>
				</div>
			</div>

			<div className="border mt-5 p-4 rounded-lg">
				<div className="card-link-main-info">
					<div className="card-link-icon">
						<ClayIcon
							aria-label="Icon"
							className="card-link-icon-image"
							symbol="envelope-open"
						/>
					</div>

					<div className="card-link-info">
						<span className="card-link-info-text">
							Support Email
						</span>
						{appData.support.email && (
							<a
								className="card-link-info-description text-truncate"
								href={appData.support.email}
								target="_blank"
								title={appData.support.email}
							>
								{appData.support.email}
							</a>
						)}
					</div>
				</div>
			</div>

			<div className="border mt-5 p-4 rounded-lg">
				<div className="card-link-main-info">
					<div className="card-link-icon">
						<ClayIcon
							aria-label="Icon"
							className="card-link-icon-image"
							symbol="phone"
						/>
					</div>

					<div className="card-link-info">
						<span className="card-link-info-text">
							Support Phone
						</span>
						{appData.support.phone && (
							<a
								className="card-link-info-description text-truncate"
								href={appData.support.phone}
								target="_blank"
								title={appData.support.phone}
							>
								{appData.support.phone}
							</a>
						)}
					</div>
				</div>
			</div>

			<div className="border mt-5 p-4 rounded-lg">
				<div className="card-link-main-info">
					<div className="card-link-icon">
						<ClayIcon
							aria-label="Icon"
							className="card-link-icon-image"
							symbol="document"
						/>
					</div>

					<div className="card-link-info">
						<span className="card-link-info-text">
							App usage terms (EULA) URL
						</span>
						{appData.support.appUsageTermsURL && (
							<a
								className="card-link-info-description text-truncate"
								href={appData.support.appUsageTermsURL}
								target="_blank"
								title={appData.support.appUsageTermsURL}
							>
								{appData.support.appUsageTermsURL}
							</a>
						)}
					</div>
				</div>
			</div>

			<div className="border mt-5 p-4 rounded-lg">
				<div className="card-link-main-info">
					<div className="card-link-icon">
						<ClayIcon
							aria-label="Icon"
							className="card-link-icon-image"
							symbol="document"
						/>
					</div>

					<div className="card-link-info">
						<span className="card-link-info-text">
							App documentation URL
						</span>
						{appData.support.documentationURL && (
							<a
								className="card-link-info-description text-truncate"
								href={appData.support.documentationURL}
								target="_blank"
								title={appData.support.documentationURL}
							>
								{appData.support.documentationURL}
							</a>
						)}
					</div>
				</div>
			</div>

			<div className="border mt-5 p-4 rounded-lg">
				<div className="card-link-main-info">
					<div className="card-link-icon">
						<ClayIcon
							aria-label="Icon"
							className="card-link-icon-image"
							symbol="sites"
						/>
					</div>

					<div className="card-link-info">
						<span className="card-link-info-text">
							App installation guide URL
						</span>
						{appData.support.installationGuideURL && (
							<a
								className="card-link-info-description text-truncate"
								href={appData.support.installationGuideURL}
								target="_blank"
								title={appData.support.installationGuideURL}
							>
								{appData.support.installationGuideURL}
							</a>
						)}
					</div>
				</div>
			</div>
		</>
	);
};

export default SubmitSupportList;
