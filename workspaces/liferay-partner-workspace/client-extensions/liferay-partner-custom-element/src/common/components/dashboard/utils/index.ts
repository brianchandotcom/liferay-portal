/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const naNToZero = (value: number) => (Number.isNaN(value) ? 0 : value);

const percentFormat = (newValue: number, odlValue: number) =>
	(newValue / odlValue) * 100;

export {naNToZero, percentFormat};
