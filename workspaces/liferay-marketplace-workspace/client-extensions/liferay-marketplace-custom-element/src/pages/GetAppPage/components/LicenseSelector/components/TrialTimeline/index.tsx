/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import './index.scss';

import {useState} from 'react';

import {CardButton} from '../../../../../../components/CardButton/CardButton';

type TrialTimelineProps = {
	handleLicenseSelect: () => void;
};

export function TrialTimeline({handleLicenseSelect}: TrialTimelineProps) {
	const [selectedLicense, setSelectedLicense] = useState(false);

	return (
		<div className="d-flex flex-column trial-timeline">
			<p className="d-flex mb-2 trial-info">
				Need help with license calculations?
				<span className="d-flex info-button">
					More Info
					<span>
						<ClayIcon symbol="question-circle-full" />
					</span>
				</span>
			</p>

			<CardButton
				description="Trial licenses are intended for you to try the app before you buy. Typical trials are 30 days."
				disabled={false}
				icon={
					<span className="trial-card-icon">
						<ClayIcon symbol="percentage-symbol" />
					</span>
				}
				iconRight
				onClick={() => {
					handleLicenseSelect();
					setSelectedLicense(true);
				}}
				selected={selectedLicense}
				title="Trial License"
			/>
		</div>
	);
}
