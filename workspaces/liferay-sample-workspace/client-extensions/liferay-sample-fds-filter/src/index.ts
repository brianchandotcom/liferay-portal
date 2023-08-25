/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {FDSFilterHTMLElementBuilder} from '@liferay/js-api/data-set';

const mySampleFilter: FDSFilterHTMLElementBuilder = ({filter, setFilter}) => {
	const div = document.createElement('div');
	const button = document.createElement('button');
	const input = document.createElement('input');

	div.className = 'dropdown-item';

	button.className = 'btn btn-block btn-secondary btn-sm mt-2';
	button.innerText = 'Submit';
	button.onclick = () =>
		setFilter({
			odataFilterString: input.value,
			selectedData: input.value,
		});

	if (filter.selectedData) {
		input.value = filter.selectedData;
	}

	input.className = 'form-control';
	input.placeholder = 'Search with Odata';

	div.appendChild(input);
	div.appendChild(button);

	return div;
};

export default mySampleFilter;
