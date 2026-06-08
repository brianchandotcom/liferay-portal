/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export enum Alignments {
	Center = 'center',
	Left = 'left',
	Right = 'right',
}

export enum Weights {
	Light = 'light',
	Normal = 'normal',
	Semibold = 'semibold',
	Bold = 'bold',
}

export type Column = {
	align?: Alignments;
	className?: string;
	color?: string;
	colspan?: number;
	label: string | (() => React.ReactNode);
	truncated?: boolean;
	weight?: Weights;
	width?: number;
};
