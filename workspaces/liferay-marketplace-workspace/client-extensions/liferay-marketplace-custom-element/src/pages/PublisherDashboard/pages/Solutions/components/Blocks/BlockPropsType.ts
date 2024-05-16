/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ContentBlock} from '../../../../../../context/SolutionContext';

export type BlockTypeProps<T = ContentBlock> = {
	block: T;
	onChange: (content: any) => void;
	onDeleteImage: (payload: string) => void;
};
