/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {Button as ClayButton} from '@clayui/core';
import {filesize} from 'filesize';
import {useEffect, useState} from 'react';
import i18n from '~/common/I18n';
import AttachmentComment from './components/AttachmentComment';
import DropzoneUpload from './components/DropzoneUpload';
import FileList from './components/FileList';

const AttachmentUploader = () => {
	const [fileAttached, setFileAttached] = useState(null);
	const [isCheckboxChecked, setIsCheckboxChecked] = useState(false);
	const [attachmentComment, setAttachmentComment] = useState('');
	const [ticketAttachmentId, setTicketAttachmentId] = useState(null);
	const [gcsSessionURL, setGcsSessionURL] = useState('');

	const ticketId = 378777;

	const handleUpload = (file) => {
		const newUploadedFile = {
			changed: true,
			error: false,
			file: file[0],
			fileName: file[0].name,
			progress: 0,
			readableSize: filesize(file[0].size),
			uploaded: true,
		};

		setFileAttached(newUploadedFile);

		return newUploadedFile;
	};

	const initiateUpload = async (fileUploaded) => {
		try {
			const response =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oauth-application-user-agent'
				).fetch('/ticket-attachments/initiate-upload', {
					body: JSON.stringify({
						zendeskTicketId: ticketId,
						fileName: fileUploaded.fileName,
						fileSize: String(fileUploaded.file.size),
					}),
					method: 'POST',
				});

			if (!response.ok) {
				throw new Error(
					`Failed to initiate upload: ${response.statusText}`
				);
			}

			const responseText = await response.text();
			const responseJson = JSON.parse(responseText);

			setTicketAttachmentId(responseJson.ticketAttachmentId || null);
			setGcsSessionURL(responseJson.gcsSessionURL || '');
		}
		catch (error) {
			console.error(error);
		}
	};

	const uploadFileToGcs = async () => {
		if (!gcsSessionURL || !fileAttached) {
			return;
		}

		try {
			const response = await fetch(gcsSessionURL, {
				method: 'PUT',
				body: fileAttached.file,
				headers: {
					'Content-Length': fileAttached.file.size.toString(),
				},
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

	const ticketCommentUpload = async () => {
		if (!ticketAttachmentId) {
			return;
		}

		try {
			const response =
				await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oauth-application-user-agent'
				).fetch(
					`/ticket-attachments/${ticketAttachmentId}/complete-upload`,
					{
						body: JSON.stringify({
							zendeskTicketCommentBody: attachmentComment,
						}),
						method: 'POST',
					}
				);

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

	useEffect(() => {
		if (gcsSessionURL && fileAttached) {
			uploadFileToGcs();
		}

		if (gcsSessionURL && ticketAttachmentId) {
			ticketCommentUpload();
		}
	}, [gcsSessionURL, fileAttached, ticketAttachmentId]);

	const handleClickUpload = async () => {
		if (fileAttached) {
			await initiateUpload(fileAttached);
		}
	};

	return (
		<div className="attachment-uploader">
			<div className="d-flex mt-4 text-neutral-10">
				<h2 className="">{i18n.translate('attach-file-to-ticket')}</h2>
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

				{!fileAttached && (
					<DropzoneUpload
						buttonText={i18n.translate('select-a-file')}
						onHandleUpload={handleUpload}
						title={i18n.translate('drag-and-drop-to-upload-or')}
					/>
				)}

				{!!fileAttached && (
					<FileList
						onDelete={() => {
							setFileAttached(null);
						}}
						type="document"
						uploadedFiles={fileAttached}
					/>
				)}

				<AttachmentComment
					attachmentComment={attachmentComment}
					isCheckboxChecked={isCheckboxChecked}
					setAttachmentComment={setAttachmentComment}
					setIsCheckboxChecked={setIsCheckboxChecked}
				/>

				<div className="d-flex my-4 px-4">
					<ClayButton
						aria-label="Close"
						displayType="secondary ml-auto mt-2"
						onClick={() =>
							window.history.length > 1
								? window.history.back()
								: (window.location.href =
										window.location.origin)
						}
					>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton
						aria-label="Upload"
						disabled={!fileAttached || !isCheckboxChecked}
						displayType="primary ml-3 mt-2"
						onClick={handleClickUpload}
					>
						{i18n.translate('upload')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default AttachmentUploader;
