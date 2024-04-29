/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable react/no-unescaped-entities */

import ClayIcon from '@clayui/icon';

import timeline from '../../../../../assets/images/timeline.png';

import './TrialMethod.scss';

const months = [
	'January',
	'February',
	'March',
	'April',
	'May',
	'June',
	'July',
	'August',
	'September',
	'October',
	'November',
	'December',
];

export function TrialMethod() {
	const date = new Date();

	date.setTime(date.getTime() + 30 * 24 * 60 * 60 * 1000);

	const endOfTrialDay = date.getDate();
	const endOfTrialMonth = months[date.getMonth()];

	return (
		<div className="d-flex get-app-modal-trial">
			<div className="d-flex flex-column get-app-modal-trial-timeline">
				<ClayIcon
					aria-label="circle fill"
					className="get-app-modal-trial-timeline-icon-selected"
					symbol="radio-button"
				/>

				<img
					alt="timeline"
					className="get-app-modal-trial-timeline-dash"
					src={timeline}
				/>

				<ClayIcon
					aria-label="circleFill"
					className="get-app-modal-trial-timeline-icon text-muted"
					symbol="circle"
				/>
			</div>

			<div className="get-app-modal-trial-messages">
				<div className="get-app-modal-trial-messages-item">
					<div className="active title">
						Today - free trial for 30 days.
					</div>

					<div className="description">
						Start your free trial and test the full app for 30 days.
					</div>
				</div>

				<div className="get-app-modal-trial-messages-item">
					<div className="title">
						{endOfTrialMonth} {endOfTrialDay}
					</div>

					<div className="description">
						Your trial ends and this app will no longer function in
						your project, unless you've subscribed during the trial.
					</div>
				</div>
			</div>
		</div>
	);
}
