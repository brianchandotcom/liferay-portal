/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import {
	buildQueryString,
	translateQueryToCriteria,
} from '~/segment/segment-editor/dynamic/utils/odata';
import Input from '~/shared/components/Input';

const ODataParsingKit = function ODataParsingKit() {
	const [code, setCode] = useState(
		"accounts.filterByCount(filter='activityKey eq ''Page#pageViewed#348853654381438580'' and between(date,''1-2-20'',''1-4-20'')',operator='lt',value=2)"
	);

	const properties = [
		{
			label: 'Page Viewed',
			name: 'pageViewed',
			type: 'behavior',
		},
	];

	const buildCriteria = () => {
		try {
			return buildQueryString(
				[translateQueryToCriteria(code)],
				null,
				properties
			);
		}
		catch (error) {
			return `Error: ${error.message}`;
		}
	};

	return (
		<div>
			<Input
				onChange={(event) => setCode(event.currentTarget.value)}
				type="textarea"
				value={code}
			/>

			<p>{JSON.stringify(translateQueryToCriteria(code))}</p>
			<p>{buildCriteria()}</p>
		</div>
	);
};

export default ODataParsingKit;
