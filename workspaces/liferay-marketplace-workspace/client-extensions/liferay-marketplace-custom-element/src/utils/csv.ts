/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const escapeCSV = (value: string | number): string => {
	const str = String(value);

	return '"' + str.replace(/"/g, '""') + '"';
};

export function exportToCSV(
	filename: string,
	headers: string[],
	rows: (string | number)[][]
): void {
	let csv = '\uFEFF' + headers.map(escapeCSV).join(',') + '\n';

	for (const row of rows) {
		csv += row.map(escapeCSV).join(',') + '\n';
	}

	const blob = new Blob([csv], {type: 'text/csv;charset=utf-8;'});

	const url = URL.createObjectURL(blob);

	const downloadLink = document.createElement('a');
	downloadLink.href = url;
	downloadLink.download = filename;
	downloadLink.click();

	URL.revokeObjectURL(url);
}
