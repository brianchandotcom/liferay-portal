/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {
	type ReportFeedbackPayload,
	type ReportFeedbackReason,
	postAIIssueReport,
} from '../api';

interface UseReportFeedbackOptions {
	agentDefinitionExternalReferenceCodes: string[];
	chatbotExternalReferenceCode: string;
}

export default function useReportFeedback({
	agentDefinitionExternalReferenceCodes,
	chatbotExternalReferenceCode,
}: UseReportFeedbackOptions) {
	const [reason, setReason] = useState<ReportFeedbackReason | ''>('');
	const [userMessage, setUserMessage] = useState('');
	const [submitting, setSubmitting] = useState(false);
	const [error, setError] = useState('');

	const canSubmit = Boolean(reason) && !submitting;

	async function submit(): Promise<boolean> {
		if (!reason) {
			return false;
		}

		const payload: ReportFeedbackPayload = {
			agentDefinitionExternalReferenceCodes,
			chatbotExternalReferenceCode,
			feedback: 'negative',
			reason,
			surface: 'clickToChat',
			...(userMessage.trim() ? {userMessage: userMessage.trim()} : {}),
		};

		setError('');
		setSubmitting(true);

		try {
			await postAIIssueReport(payload);

			return true;
		}
		catch (caught) {
			setError(
				(caught as Error).message ||
					'Unable to send feedback. Please try again.'
			);

			return false;
		}
		finally {
			setSubmitting(false);
		}
	}

	return {
		canSubmit,
		error,
		reason,
		setReason,
		setUserMessage,
		submit,
		submitting,
		userMessage,
	};
}
