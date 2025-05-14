/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton, useModal} from '@clayui/core';
import {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useState} from 'react';
import SparkMD5 from 'spark-md5';
import {Liferay} from '~/services/liferay';
import i18n from '~/utils/I18n';

import DropzoneUpload from './components/DropzoneUpload';
import FileList from './components/FileList';

import './AttachmentUploader.css';

import {getTicketAttachmentById} from '~/services/liferay/api';
import routerPath from '~/utils/routerPath';

import UploaderModal from './components/UploaderModal';

export interface IAttachment {
	comment?: string;
	file: File;
	hasPersonalData?: boolean;
}

const AttachmentUploader = () => {
	const [attachment, setAttachment] = useState<IAttachment>();
	const [gcsSessionURL, setGcsSessionURL] = useState<string>();
	const [ticketAttachmentId, setTicketAttachmentId] = useState<string>();
	const [abortController, setAbortController] =
		useState<AbortController | null>(null);
	const [uploadedFile, setUploadedFile] = useState<{progress: number}>({
		progress: 0,
	});
	const [showProgress, setShowProgress] = useState(false);
	const [isModalOpen, setIsModalOpen] = useState(false);
	const [uploadAccountKey, setUploadAccountKey] = useState('');

	const urlParams = new URLSearchParams(window.location.search);
	const ticketId = urlParams.get('ticketId');

	const handleNavigateToTicket = () => {
		window.location.href = `https://help.liferay.com/hc/requests/${ticketId}`;
	};

	const handleNavigateToAttachments = () => {
		const pageRoutes = routerPath();

		Liferay.Util.navigate(
			`${pageRoutes.project(uploadAccountKey)}/attachments`
		);
	};

	const {observer} = useModal({
		onClose: () => {
			setIsModalOpen(false);
			handleNavigateToAttachments();
		},
	});

	async function generateFileMd5(file: File): Promise<string> {
		const chunkSize = 2 * 1024 * 1024;
		const chunks = Math.ceil(file.size / chunkSize);
		let currentChunk = 0;

		const spark = new SparkMD5.ArrayBuffer();
		const fileReader = new FileReader();

		return new Promise((resolve, reject) => {
			const loadNext = () => {
				const chunkStart = currentChunk * chunkSize;
				const chunkEnd = Math.min(chunkStart + chunkSize, file.size);
				const blob = file.slice(chunkStart, chunkEnd);
				fileReader.readAsArrayBuffer(blob);
			};

			fileReader.onload = (error) => {
				if (error.target?.result) {
					spark.append(error.target.result as ArrayBuffer);
					currentChunk++;

					if (currentChunk < chunks) {
						loadNext();
					}
					else {
						resolve(spark.end());
					}
				}
				else {
					reject(new Error('Failed to read file chunk'));
				}
			};

			fileReader.onerror = () => {
				reject(fileReader.error);
			};

			loadNext();
		});
	}

	const completeUpload = useCallback(async () => {
		try {
			const response: Response =
				(await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				).fetch(
					`/ticket-attachments/${ticketAttachmentId}/complete-upload`,
					{
						body: JSON.stringify({
							zendeskTicketCommentBody: attachment?.comment,
						}),
						method: 'POST',
					}
				)) as unknown as Response;

			if (!response.ok) {
				throw new Error(
					`Failed to ticket comment upload:  ${response.statusText}`
				);
			}
		}
		catch (error) {
			console.error(error);
		}
	}, [attachment?.comment, ticketAttachmentId]);

	const fetchTicketAttachment = async (id: string) => {
		try {
			const response = await getTicketAttachmentById(id, 'accountKey');

			setUploadAccountKey(response.accountKey);
		}
		catch (error) {
			console.error(error);
		}
	};

	const initiateUpload = async (attachment: IAttachment) => {
		const fileMd5 = await generateFileMd5(attachment.file);

		try {
			const response: Response =
				(await Liferay.OAuth2Client.FromUserAgentApplication(
					'liferay-customer-etc-spring-boot-oaua'
				).fetch('/ticket-attachments/initiate-upload', {
					body: JSON.stringify({
						fileName: attachment.file.name,
						fileSize: String(attachment.file.size),
						md5Checksum: fileMd5,
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
			const newTicketAttachment = responseJson.ticketAttachmentId || '';

			setGcsSessionURL(responseJson.gcsSessionURL || '');
			setTicketAttachmentId(responseJson.ticketAttachmentId || '');

			await fetchTicketAttachment(newTicketAttachment);
		}
		catch (error) {
			console.error(error);
		}
	};

	async function getUploadOffset(
		sessionUrl: string,
		totalSize: number
	): Promise<number> {
		const response = await fetch(sessionUrl, {
			headers: {
				'Content-Length': '0',
				'Content-Range': `bytes */${totalSize}`,
			},
			method: 'PUT',
		});

		if (response.status === 200) {
			return totalSize;
		}

		if (response.status === 308) {
			const range = response.headers.get('Range');

			if (range) {
				const match = range.match(/bytes=0-(\d+)/);

				if (match && match[1]) {
					return parseInt(match[1], 10) + 1;
				}
			}
		}

		return 0;
	}

	const uploadFileToGcs = useCallback(async () => {
		if (!attachment || !gcsSessionURL) {
			return;
		}

		const file = attachment.file;
		const chunkSize = 25 * 1024 * 1024;
		const totalSize = file.size;

		setShowProgress(true);

		const startOffset = await getUploadOffset(gcsSessionURL, totalSize);

		let chunkStart = startOffset;
		let chunkEnd = Math.min(chunkStart + chunkSize, totalSize);

		const controller = new AbortController();
		setAbortController(controller);

		while (chunkStart < totalSize) {
			const chunk = file.slice(chunkStart, chunkEnd);
			const contentRange = `bytes ${chunkStart}-${chunkEnd - 1}/${totalSize}`;

			try {
				const response = await fetch(gcsSessionURL, {
					body: chunk,
					headers: {
						'Content-Length': chunk.size.toString(),
						'Content-Range': contentRange,
					},
					method: 'PUT',
					signal: controller.signal,
				});

				if (!response.ok && response.status !== 308) {
					throw new Error(`Chunk error: ${response.statusText}`);
				}

				chunkStart = chunkEnd;
				chunkEnd = Math.min(chunkStart + chunkSize, totalSize);

				const uploadPercentage = Math.round(
					(chunkStart / totalSize) * 100
				);
				setUploadedFile({progress: uploadPercentage});
			}
			catch (error) {
				console.error('Upload failed:', error);

				break;
			}
		}

		setShowProgress(false);
		setAbortController(null);
		setIsModalOpen(true);
	}, [attachment, gcsSessionURL]);

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

	const _handleCancelUpload = () => {
		if (abortController) {
			abortController.abort();
			setAbortController(null);
		}

		setAttachment(undefined);
		setShowProgress(false);
	};

	useEffect(() => {
		if (!attachment) {
			return;
		}

		if (ticketAttachmentId) {
			completeUpload();
		}

		if (gcsSessionURL) {
			uploadFileToGcs();
		}
	}, [
		attachment,
		completeUpload,
		gcsSessionURL,
		ticketAttachmentId,
		uploadFileToGcs,
	]);

	return (
		<div className="attachment-container mt-4">
			<div className="attachment-uploader">
				<div className="d-flex text-neutral-10">
					<div className="h2">
						{`${i18n.translate('attach-file-to-ticket')} #${ticketId}`}
					</div>
				</div>

				<div className="mt-4">
					<div>
						<div className="attachment-title h5 text-neutral-9">
							{i18n.translate('attachment')}

							<span className="inline-item-after reference-mark text-warning">
								<ClayIcon symbol="asterisk" />
							</span>
						</div>

						<span className="text-neutral-8">
							{i18n.translate(
								'select-a-local-file-to-upload-only-one-file-can-be-attached-at-a-time'
							)}
						</span>
					</div>

					{!attachment && (
						<div className="dropzone-upload">
							<DropzoneUpload
								buttonText={i18n.translate('select-a-file')}
								onDropAccepted={_handleDropzoneOnDropAccepted}
								title={i18n.translate(
									'drag-and-drop-to-upload-or'
								)}
							/>
						</div>
					)}

					{!!attachment && (
						<div className="file-list-item">
							<FileList
								attachment={attachment}
								isUploading={showProgress}
								onDelete={
									showProgress
										? _handleCancelUpload
										: () => {
												setAttachment(undefined);
											}
								}
								uploadedFile={uploadedFile}
							/>
						</div>
					)}

					<div className="h5 text-neutral-9">
						{i18n.translate('leave-a-comment')}
					</div>

					<div className="attach-input mb-4">
						<ClayInput
							component="textarea"
							disabled={showProgress}
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
							disabled={showProgress}
							displayType="secondary"
							onClick={_handleCloseOnClick}
						>
							{i18n.translate('close')}
						</ClayButton>

						<ClayButton
							aria-label="Upload"
							className="ml-3 mt-2"
							disabled={
								!attachment ||
								!attachment.hasPersonalData ||
								showProgress
							}
							displayType="primary"
							onClick={_handleUploadOnClick}
						>
							{showProgress
								? `${i18n.translate('uploading')}...`
								: i18n.translate('upload')}
						</ClayButton>
					</div>

					{isModalOpen && (
						<UploaderModal
							attachmentName={attachment!.file.name}
							handleNavigateToAttachments={
								handleNavigateToAttachments
							}
							handleNavigateToTicket={handleNavigateToTicket}
							observer={observer}
						/>
					)}
				</div>
			</div>
		</div>
	);
};

export default AttachmentUploader;
