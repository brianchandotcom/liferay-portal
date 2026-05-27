/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {ThumbsDownIcon, ThumbsUpIcon} from './Icons';

interface FeedbackActionsProps {
	feedbackGiven?: boolean;
	onThumbsDown: () => void;
	onThumbsUp: () => void;
}

export default function FeedbackActions({
	feedbackGiven = false,
	onThumbsDown,
	onThumbsUp,
}: FeedbackActionsProps) {
	return (
		<div className="aihub-feedback-actions">
			<button
				aria-label="Good response"
				className="aihub-feedback-btn"
				disabled={feedbackGiven}
				onClick={onThumbsUp}
				title="Good response"
				type="button"
			>
				<ThumbsUpIcon />
			</button>

			<button
				aria-label="Report Bad Result"
				className="aihub-feedback-btn"
				disabled={feedbackGiven}
				onClick={onThumbsDown}
				title="Report Bad Result"
				type="button"
			>
				<ThumbsDownIcon />
			</button>
		</div>
	);
}
