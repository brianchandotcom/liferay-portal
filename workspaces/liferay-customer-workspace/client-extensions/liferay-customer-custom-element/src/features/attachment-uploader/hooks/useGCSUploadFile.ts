/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useState} from 'react';
import {useNavigate} from 'react-router-dom';

import useGCSGetUploadOffset from './useGCSGetUploadOffset';
import useTicketAttachmentsCompleteUpload from './useTicketAttachmentsCompleteUpload';

interface IParams {
	accountKey: string;
	comment: string;
	file: File;
	sessionURL: string;
	ticketAttachmentId: string;
	ticketId: string;
}

interface IProps {
	abortUpload: () => void;
	loading: boolean;
	progress: number;
	uploadFile: (params: IParams) => Promise<boolean>;
}

const useGCSUploadFile = (): IProps => {
	const [abortController, setAbortController] =
		useState<AbortController | null>(null);
	const [loading, setLoading] = useState(false);
	const navigate = useNavigate();
	const [progress, setProgress] = useState(0);

	const {
		error: gcsGetUploadOffsetError,
		getUploadOffset,
		loading: gcsGetUploadOffsetLoading,
		offset: currentOffset,
	} = useGCSGetUploadOffset();

	const {
		completeUpload,
		error: completeUploadError,
		loading: completeUploadLoading,
	} = useTicketAttachmentsCompleteUpload();

	const uploadFile = useCallback(
		async (params: IParams) => {
			const {
				accountKey,
				comment,
				file,
				sessionURL,
				ticketAttachmentId,
				ticketId,
			} = params;

			setLoading(true);
			setProgress(0);

			let uploadFailed = false;
			const maxRetries = 5;
			const retryDelay = (attempt: number) => 500 * Math.pow(2, attempt);
			const controller = new AbortController();
			setAbortController(controller);

			try {
				if (!file) {
					throw new Error('Attachment file is missing.');
				}

				const chunkSize = 25 * 1024 * 1024;
				const totalSize = file.size;

				await getUploadOffset({
					sessionURL,
					totalSize,
				});

				if (gcsGetUploadOffsetError) {
					throw gcsGetUploadOffsetError;
				}

				let chunkStart = currentOffset || 0;

				if (chunkStart >= totalSize && totalSize > 0) {
					setProgress(100);
				}
				else if (totalSize === 0) {
					setProgress(100);
				}

				while (chunkStart < totalSize) {
					if (controller.signal.aborted) {
						uploadFailed = true;

						break;
					}

					const chunkEnd = Math.min(
						chunkStart + chunkSize,
						totalSize
					);
					const chunk = file.slice(chunkStart, chunkEnd);
					const contentRange = `bytes ${chunkStart}-${chunkEnd - 1}/${totalSize}`;

					let successInChunkUpload = false;
					let attempt = 0;

					while (!successInChunkUpload && attempt < maxRetries) {
						if (controller.signal.aborted) {
							uploadFailed = true;
							break;
						}
						try {
							const response = await fetch(sessionURL, {
								body: chunk,
								headers: {
									'Content-Length': chunk.size.toString(),
									'Range': contentRange,
								},
								method: 'PUT',
								signal: controller.signal,
							});

							if (response.ok || response.status === 308) {
								successInChunkUpload = true;
								chunkStart = chunkEnd;

								const uploadPercentage = Math.round(
									(chunkStart / totalSize) * 100
								);
								setProgress(uploadPercentage);
							}
							else {
								throw new Error(
									`Chunk upload failed: ${response.status} ${response.statusText}`
								);
							}
						}
						catch (chunkError) {
							if (controller.signal.aborted) {
								uploadFailed = true;

								break;
							}

							console.error(
								`Upload attempt ${attempt + 1} for chunk ${chunkStart}-${chunkEnd - 1} failed:`,
								chunkError
							);
							attempt++;

							if (attempt >= maxRetries) {
								uploadFailed = true;
								if (chunkError instanceof Error) {
									throw chunkError;
								}
								throw new Error(
									'Max retries reached for chunk upload.'
								);
							}
							else {
								await new Promise((resolve) =>
									setTimeout(resolve, retryDelay(attempt))
								);
							}
						}
					}

					if (!successInChunkUpload || uploadFailed) {
						uploadFailed = true;
						break;
					}
				}

				setAbortController(null);

				if (uploadFailed || controller.signal.aborted) {
					if (!controller.signal.aborted) {
						navigate(`/${ticketId}/unexpected-error`, {
							state: {
								attachmentName: file.name,
								ticketId,
								uploadAccountKey: accountKey,
							},
						});
					}

					return false;
				}

				await completeUpload({
					comment,
					ticketAttachmentId: String(ticketAttachmentId),
				});

				if (completeUploadError) {
					throw completeUploadError;
				}

				if (!gcsGetUploadOffsetLoading && !completeUploadLoading) {
					navigate(`/${ticketId}/upload-confirmation`, {
						state: {
							attachmentName: file.name,
							ticketId,
							uploadAccountKey: accountKey,
						},
					});

					return true;
				}
				else {
					navigate(`/${ticketId}/unexpected-error`, {
						state: {
							attachmentName: file.name,
							ticketId,
							uploadAccountKey: accountKey,
						},
					});

					return false;
				}
			}
			catch (uploadError) {
				console.error('Error during GCS upload process:', uploadError);

				navigate(`/${ticketId}/unexpected-error`, {
					state: {
						attachmentName: file.name,
						ticketId,
						uploadAccountKey: accountKey,
					},
				});

				setProgress(0);

				return false;
			}
			finally {
				setLoading(false);
				setAbortController(null);
			}
		},
		[
			completeUpload,
			completeUploadError,
			completeUploadLoading,
			currentOffset,
			gcsGetUploadOffsetError,
			getUploadOffset,
			gcsGetUploadOffsetLoading,
			navigate,
		]
	);

	const abortUpload = useCallback(() => {
		if (abortController) {
			abortController.abort();

			setLoading(false);
			setProgress(0);
		}
	}, [abortController]);

	return {abortUpload, loading, progress, uploadFile};
};

export default useGCSUploadFile;
