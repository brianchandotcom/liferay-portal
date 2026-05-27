/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {type ReportFeedbackReason} from '../api';
import useReportFeedback from '../hooks/useReportFeedback';
import {CloseIcon} from './Icons';

const REASON_OPTIONS: {label: string; value: ReportFeedbackReason | ''}[] = [
	{label: 'Select Reason', value: ''},
	{
		label: 'Incorrect or Inaccurate Response',
		value: 'INCORRECT_OR_INACCURATE_RESPONSE',
	},
	{
		label: 'Inappropriate or Harmful Content',
		value: 'INAPPROPRIATE_OR_HARMFUL_CONTENT',
	},
	{
		label: 'Exposure of personal / Sensitive Data (PII)',
		value: 'PII_EXPOSURE',
	},
	{label: 'Agent Error / Malfunction', value: 'AGENT_ERROR_OR_MALFUNCTION'},
	{label: 'Other', value: 'OTHER'},
];

interface SendFeedbackModalProps {
	agentId: string;
	onClose: () => void;
	onSubmitted: () => void;
	traceId: string;
}

export default function SendFeedbackModal({
	agentId,
	onClose,
	onSubmitted,
	traceId,
}: SendFeedbackModalProps) {
	const {
		canSubmit,
		comment,
		error,
		reason,
		setComment,
		setReason,
		submit,
		submitting,
	} = useReportFeedback({agentId, traceId});

	async function handleSubmit(event: React.FormEvent) {
		event.preventDefault();

		if (await submit()) {
			onSubmitted();
		}
	}

	return (
		<div className="aihub-modal-overlay" onMouseDown={onClose}>
			<div
				aria-modal="true"
				className="aihub-modal"
				onMouseDown={(event) => event.stopPropagation()}
				role="dialog"
			>
				<form onSubmit={handleSubmit}>
					<div className="aihub-modal-header">
						<h2 className="aihub-modal-title">Send Feedback</h2>

						<button
							aria-label="Close"
							className="aihub-modal-close"
							onClick={onClose}
							type="button"
						>
							<CloseIcon />
						</button>
					</div>

					<div className="aihub-modal-body">
						{error && (
							<div className="aihub-modal-error">{error}</div>
						)}

						<div className="aihub-modal-field">
							<label htmlFor="aihub-feedback-reason">
								Reason
								<span className="aihub-modal-required">*</span>
							</label>

							<select
								disabled={submitting}
								id="aihub-feedback-reason"
								onChange={(event) =>
									setReason(
										event.target
											.value as ReportFeedbackReason
									)
								}
								required
								value={reason}
							>
								{REASON_OPTIONS.map((option) => (
									<option
										key={option.value}
										value={option.value}
									>
										{option.label}
									</option>
								))}
							</select>
						</div>

						<div className="aihub-modal-field">
							<label htmlFor="aihub-feedback-comment">
								Comment (Optional)
							</label>

							<textarea
								disabled={submitting}
								id="aihub-feedback-comment"
								onChange={(event) =>
									setComment(event.target.value)
								}
								rows={4}
								value={comment}
							/>
						</div>
					</div>

					<div className="aihub-modal-footer">
						<button
							className="aihub-modal-btn aihub-modal-btn-secondary"
							disabled={submitting}
							onClick={onClose}
							type="button"
						>
							Cancel
						</button>

						<button
							className="aihub-modal-btn aihub-modal-btn-primary"
							disabled={!canSubmit}
							type="submit"
						>
							Send
						</button>
					</div>
				</form>
			</div>
		</div>
	);
}
