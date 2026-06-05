/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {type ReportFeedbackPayload, postAIIssueReport} from './api';

export function submitPositiveFeedback(
	payload: Omit<ReportFeedbackPayload, 'feedback' | 'reason' | 'userMessage'>,
	onSuccess: () => void
): Promise<void> {
	return postAIIssueReport({...payload, feedback: 'positive'})
		.then(() => onSuccess())
		.catch((error) => {
			console.error('Failed to submit feedback:', error);
		});
}
