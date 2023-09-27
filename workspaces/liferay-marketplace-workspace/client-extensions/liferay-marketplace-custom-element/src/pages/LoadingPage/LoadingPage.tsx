/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import './LoadingPage.scss';

interface LoadingPageProps {
	appTitle: string;
	appVersion: string;
}

export function LoadingPage({appTitle, appVersion}: LoadingPageProps) {
	return (
		<div className="loading-page-container">
			<ClayLoadingIndicator
				displayType="primary"
				shape="squares"
				size="lg"
			/>

			<div className="loading-page-text-container">
				<span className="loading-page-text">
					Hang tight, the submission of <strong>{appTitle}</strong>
				</span>

				<span className="loading-page-text">
					<strong>{appVersion}</strong>
					is being sent to
					<strong>Liferay</strong>
				</span>
			</div>
		</div>
	);
}
