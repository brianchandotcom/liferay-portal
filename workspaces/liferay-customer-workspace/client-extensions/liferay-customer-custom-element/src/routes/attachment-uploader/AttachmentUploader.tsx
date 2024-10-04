/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import {useEffect, useState} from 'react';
import i18n from '~/common/I18n';
import {Liferay} from '~/common/services/liferay';

import DropzoneUpload from './components/DropzoneUpload';
import FileList from './components/FileList';

import './app.scss';

export interface IAttachment {
	comment?: string;
	file: File;
	hasPersonalData?: boolean;
}

const AttachmentUploader = () => {
	const [attachment, setAttachment] = useState<IAttachment>();
	const [gcsSessionURL, setGcsSessionURL] = useState<string>();
	const [ticketAttachmentId, setTicketAttachmentId] = useState<string>();

	const urlParams = new URLSearchParams(window.location.search);

	const ticketId = urlParams.get('ticketId');

	const completeUpload = async () => {
		try {
			const response: Response =
				(await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				).fetch(`/ticket-attachments/${ticketId}/complete-upload`, {
					body: JSON.stringify({
						zendeskTicketCommentBody: attachment?.comment,
					}),
					method: 'POST',
				})) as unknown as Response;

			if (!response.ok) {
				throw new Error(
					`Failed to ticket comment upload:  ${response.statusText}`
				);
			}
		}
		catch (error) {
			console.error(error);
		}
	};

	const initiateUpload = async (attachment: IAttachment) => {
		try {
			const response: Response =
				(await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				).fetch('/ticket-attachments/initiate-upload', {
					body: JSON.stringify({
						fileName: attachment.file.name,
						fileSize: attachment.file.size,
						zendeskTicketId: ticketId,
					}),
					method: 'POST',
				})) as unknown as Response;

			if (!response.ok) {
				throw new Error(
					`Failed to initiate upload: ${response.statusText}`
				);
			}

			const responseText = await response.text();

			const responseJson = JSON.parse(responseText);

			setGcsSessionURL(responseJson.gcsSessionURL || '');
			setTicketAttachmentId(responseJson.ticketAttachmentId || '');
		}
		catch (error) {
			console.error(error);
		}
	};

	const uploadFileToGcs = async () => {
		if (!attachment || !gcsSessionURL) {
			return;
		}

		try {
			const response = await fetch(gcsSessionURL, {
				body: attachment.file,
				headers: {
					'Content-Length': attachment.file.size.toString(),
				},
				method: 'PUT',
			});

			if (!response.ok) {
				throw new Error(
					`Failed to upload file to GCS: ${response.statusText}`
				);
			}
		}
		catch (error) {
			console.error(error);
		}
	};

	const _handleCloseOnClick = () => {
		if (window.history.length > 1) {
			window.history.back();
		}
		else {
			window.location.href = window.location.origin;
		}
	};

	const _handleDropzoneOnDropAccepted = (file: File) => {
		const newAttachment: IAttachment = {
			file,
		};

		setAttachment(newAttachment);

		return newAttachment;
	};

	const _handleUploadOnClick = async () => {
		if (attachment) {
			await initiateUpload(attachment);
		}
	};

	useEffect(() => {
		if (!attachment) {
			return;
		}

		if (ticketAttachmentId) {
			completeUpload();

			return;
		}

		if (gcsSessionURL) {
			uploadFileToGcs();
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [gcsSessionURL, ticketAttachmentId]);

	return (
		<div className="container-attach">
			<div className="attachment-uploader">
				<div className="d-flex mt-4 text-neutral-10">
					<h2>{i18n.translate('attach-file-to-ticket')}</h2>
				</div>

				<div className="mt-4">
					<div>
						<h5 className="text-neutral-9">
							{i18n.translate('attachment')}
						</h5>

						<span className="text-neutral-8">
							{i18n.translate(
								'select-a-local-file-to-upload-only-one-file-can-be-attached-at-a-time'
							)}
						</span>
					</div>

					{!attachment && (
						<DropzoneUpload
							buttonText={i18n.translate('select-a-file')}
							onDropAccepted={_handleDropzoneOnDropAccepted}
							title={i18n.translate('drag-and-drop-to-upload-or')}
						/>
					)}

					{!!attachment && (
						<FileList
							attachment={attachment}
							onDelete={() => {
								setAttachment(undefined);
							}}
						/>
					)}

					<h5 className="text-neutral-9">
						{i18n.translate('leave-a-comment')}
					</h5>

					<div className="attach-input mb-4">
						<ClayInput
							component="textarea"
							onChange={(event) =>
								attachment &&
								setAttachment({
									...attachment,
									comment: event.target.value,
								})
							}
							placeholder={i18n.translate(
								'add-a-description-of-the-file-related-to-this-ticket'
							)}
							type="text"
							value={attachment?.comment}
						/>
					</div>

					<div className="attachment-uploader-support-text ml-2">
						<ClayCheckbox
							checked={attachment?.hasPersonalData || false}
							label={i18n.translate(
								'please-check-this-box-if-the-file-you-upload-does-not-contain-any-personal-data-and-therefore-can-be-uploaded-to-and-accessed-from-any-liferay-support-location-globally'
							)}
							onChange={(event) =>
								attachment &&
								setAttachment({
									...attachment,
									hasPersonalData: event.target.checked,
								})
							}
						/>
					</div>

					<div className="d-flex my-4">
						<ClayButton
							aria-label="Close"
							className="ml-auto mt-2"
							displayType="secondary"
							onClick={_handleCloseOnClick}
						>
							{i18n.translate('close')}
						</ClayButton>

						<ClayButton
							aria-label="Upload"
							className="ml-3 mt-2"
							disabled={
								!attachment || !attachment.hasPersonalData
							}
							displayType="primary"
							onClick={_handleUploadOnClick}
						>
							{i18n.translate('upload')}
						</ClayButton>
					</div>
				</div>
			</div>
		</div>
	);
};

export default AttachmentUploader;
