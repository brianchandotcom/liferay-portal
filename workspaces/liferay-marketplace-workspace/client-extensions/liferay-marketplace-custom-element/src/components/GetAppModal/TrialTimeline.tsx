/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/* eslint-disable react/no-unescaped-entities */

import circleFill from '../../assets/icons/circle_fill_icon.svg';
import radioSelected from '../../assets/icons/radio_button_checked_2_icon.svg';
import timeline from '../../assets/images/timeline.png';

import './TrialTimeline.scss';

export function TrialTimeline() {
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

	const date = new Date();

	date.setTime(date.getTime() + 30 * 24 * 60 * 60 * 1000);

	const endOfTrialDay = date.getDate();
	const endOfTrialMonth = months[date.getMonth()];

	return (
		<div className="get-app-modal-trial">
			<div className="get-app-modal-trial-timeline">
				<img
					alt="circle fill"
					className="get-app-modal-trial-timeline-icon-selected"
					src={radioSelected}
				/>

				<img
					alt="timeline"
					className="get-app-modal-trial-timeline-dash"
					src={timeline}
				/>

				<img
					alt="circleFill"
					className="get-app-modal-trial-timeline-icon"
					src={circleFill}
				/>
			</div>

			<div className="get-app-modal-trial-messages">
				<div className="get-app-modal-trial-messages-item">
					<div className="active title">
						Today - free trial for 30 days.{' '}
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
