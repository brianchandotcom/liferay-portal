/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 *
 * @example base64ToText("data:image/png;base64,iV...") returns iV...
 * @description Use this function to remove the filetype from the base64
 * @param base64
 */
const base64ToText = (base64: string) => base64.split(',').at(-1);

const downloadFile = async (filename: string, response: Response) => {
	const blob = await response.blob();

	const contentDisposition = response.headers.get('content-disposition');

	if (contentDisposition) {
		filename = (
			contentDisposition
				.split(';')
				.find((n) => n.includes('filename=')) ?? ''
		)
			.replace('filename=', '')
			.replaceAll('"', '')
			.trim();
	}

	const anchor = document.createElement('a');

	anchor.download = filename;
	anchor.href = URL.createObjectURL(blob);

	document.body.appendChild(anchor);

	anchor.click();
	anchor.remove();
};

const fileToBase64 = (file: File): Promise<ArrayBuffer | null | string> =>
	new Promise((resolve, reject) => {
		const reader = new FileReader();

		reader.readAsDataURL(file);
		reader.onload = () => resolve(reader.result);
		reader.onerror = reject;
	});

export {base64ToText, downloadFile, fileToBase64};
