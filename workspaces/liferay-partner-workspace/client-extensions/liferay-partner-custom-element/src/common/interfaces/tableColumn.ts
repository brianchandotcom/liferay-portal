/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default interface TableColumn<T> {
	columnKey: string;
	label: string | JSX.Element;
	render?: (data: T[keyof T], item: T, index: number) => JSX.Element;
}
