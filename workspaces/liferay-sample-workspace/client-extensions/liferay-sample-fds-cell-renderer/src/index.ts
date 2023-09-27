/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FDSTableCellHTMLElementBuilder} from '@liferay/js-api/data-set';

const fdsCellRenderer: FDSTableCellHTMLElementBuilder = ({value}) => {
	const element = document.createElement('div');

	element.innerHTML = value === 'Green' ? '🍏' : value.toString();

	return element;
};

export default fdsCellRenderer;
