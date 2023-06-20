/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import type {FDSFilter} from '@liferay/js-api/data-set';

const mySampleFilter: FDSFilter = ({filter, setFilter}) => {
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
