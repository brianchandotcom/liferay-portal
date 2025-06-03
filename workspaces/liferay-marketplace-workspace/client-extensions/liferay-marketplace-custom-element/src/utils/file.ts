/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../core/SearchBuilder';
import orderExportOAuth2 from '../services/oauth/OrderExport';

/**
 *
 * @example base64ToText("data:image/png;base64,iV...") returns iV...
 * @description Use this function to remove the filetype from the base64
 * @param base64
 */
const base64ToText = (base64: string) => base64.split(',').at(-1);

const fileToBase64 = (file: File): Promise<ArrayBuffer | null | string> =>
	new Promise((resolve, reject) => {
		const reader = new FileReader();

		reader.readAsDataURL(file);
		reader.onload = () => resolve(reader.result);
		reader.onerror = reject;
	});

const exportOrderPageAsCSVFile = async (filter: string) => {
	try {
		const response = await orderExportOAuth2.oAuth2Client.fetch(
			filter?.length
				? `/marketplace/orders/export?filters=${SearchBuilder.in(
						'orderTypeExternalReferenceCode',
						[filter]
					)}`
				: '/marketplace/orders/export'
		);

		const blob = await response.blob();
		const url = URL.createObjectURL(blob);
		const a = document.createElement('a');

		a.href = url;
		a.download = 'orders.csv';
		document.body.appendChild(a);
		a.click();
		document.body.removeChild(a);

		URL.revokeObjectURL(url);
	}
	catch (error) {
		console.error('Download failed:', error);
	}
};

export {base64ToText, fileToBase64, exportOrderPageAsCSVFile};
