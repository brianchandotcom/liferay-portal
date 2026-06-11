/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React from 'react';

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
			<ClayButtonWithIcon
				aria-label="Good response"
				className="aihub-feedback-btn"
				disabled={feedbackGiven}
				displayType="unstyled"
				onClick={onThumbsUp}
				symbol="thumbs-up"
				title="Good response"
			/>

			<ClayButtonWithIcon
				aria-label="Report Bad Result"
				className="aihub-feedback-btn"
				disabled={feedbackGiven}
				displayType="unstyled"
				onClick={onThumbsDown}
				symbol="thumbs-down"
				title="Report Bad Result"
			/>
		</div>
	);
}
