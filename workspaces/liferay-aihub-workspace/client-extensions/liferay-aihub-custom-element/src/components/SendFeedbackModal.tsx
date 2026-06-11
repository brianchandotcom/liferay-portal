/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClaySelect} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useContext} from 'react';

import {type ReportFeedbackReason} from '../api';
import useReportFeedback from '../hooks/useReportFeedback';
import ShadowPortalContext from '../shadow';

const REASON_OPTIONS: {label: string; value: ReportFeedbackReason | ''}[] = [
	{label: 'Select Reason', value: ''},
	{
		label: 'Incorrect or Inaccurate Response',
		value: 'inaccurateResponse',
	},
	{
		label: 'Inappropriate or Harmful Content',
		value: 'harmfulContent',
	},
	{
		label: 'Exposure of Personal or Sensitive Data (PII)',
		value: 'personalDataExposure',
	},
	{label: 'Agent Error or Malfunction', value: 'agentError'},
	{label: 'Other', value: 'other'},
];

interface SendFeedbackModalProps {
	agentDefinitionExternalReferenceCodes: string[];
	chatbotExternalReferenceCode: string;
	onClose: () => void;
	onSubmitted: () => void;
}

export default function SendFeedbackModal({
	agentDefinitionExternalReferenceCodes,
	chatbotExternalReferenceCode,
	onClose,
	onSubmitted,
}: SendFeedbackModalProps) {
	const {observer, onClose: closeModal} = useModal({onClose});

	const portalContainerRef = useContext(ShadowPortalContext);

	const {
		canSubmit,
		error,
		reason,
		setReason,
		setUserMessage,
		submit,
		submitting,
		userMessage,
	} = useReportFeedback({
		agentDefinitionExternalReferenceCodes,
		chatbotExternalReferenceCode,
	});

	async function handleSubmit(event: React.FormEvent) {
		event.preventDefault();

		if (await submit()) {
			onSubmitted();
		}
	}

	return (
		<ClayModal
			containerElementRef={portalContainerRef ?? undefined}
			observer={observer}
		>
			<ClayModal.Header>Send Feedback</ClayModal.Header>

			<ClayModal.Body>
				<form id="aihub-feedback-form" onSubmit={handleSubmit}>
					{error && (
						<div className="alert alert-danger" role="alert">
							{error}
						</div>
					)}

					<ClayForm.Group>
						<label htmlFor="aihub-feedback-reason">
							Reason
							<span className="reference-mark text-warning">
								*
							</span>
						</label>

						<ClaySelect
							disabled={submitting}
							id="aihub-feedback-reason"
							onChange={(event) =>
								setReason(
									event.target.value as ReportFeedbackReason
								)
							}
							required
							value={reason}
						>
							{REASON_OPTIONS.map((option) => (
								<ClaySelect.Option
									key={option.value}
									label={option.label}
									value={option.value}
								/>
							))}
						</ClaySelect>
					</ClayForm.Group>

					<ClayForm.Group>
						<label htmlFor="aihub-feedback-comment">
							Comment (Optional)
						</label>

						<textarea
							className="form-control"
							disabled={submitting}
							id="aihub-feedback-comment"
							onChange={(event) =>
								setUserMessage(event.target.value)
							}
							rows={4}
							value={userMessage}
						/>
					</ClayForm.Group>
				</form>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							disabled={submitting}
							displayType="secondary"
							onClick={closeModal}
						>
							Cancel
						</ClayButton>

						<ClayButton
							disabled={!canSubmit}
							form="aihub-feedback-form"
							type="submit"
						>
							Send
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
