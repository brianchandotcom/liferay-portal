/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import './HangTight.scss';

interface LoadingPageProps {
	title: string;
	version: string;
}

const HangTight: React.FC<LoadingPageProps> = ({title, version}) => (
	<div className="hang-tight-container">
		<ClayLoadingIndicator displayType="primary" shape="squares" size="lg" />

		<div className="hang-tight-text-container">
			<span className="hang-tight-text">
				Hang tight, the submission of <strong>{title}</strong>
			</span>

			<span className="hang-tight-text">
				<strong>{version}</strong>
				is being sent to
				<strong className="ml-2">Liferay</strong>
			</span>
		</div>
	</div>
);

export default HangTight;
