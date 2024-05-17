/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toLocaleString} from '../../services/DateUtil';

function Jethr0InformationField({
	fieldLabel,
	fieldType,
	fieldURLValue,
	fieldValue,
}) {
	if (fieldType === 'BOOLEAN') {
		return (
			<>
				<strong>{fieldLabel + ': '}</strong>
				{fieldValue ? 'yes' : 'no'}
				<br />
			</>
		);
	}

	if (!fieldValue || fieldValue === '') {
		return <></>;
	}

	if (fieldType === 'DATE') {
		return (
			<>
				<strong>{fieldLabel + ': '}</strong>
				{toLocaleString(fieldValue)}
				<br />
			</>
		);
	}

	if (fieldType === 'URL') {
		if (!fieldURLValue) {
			fieldURLValue = fieldValue;
		}

		return (
			<>
				<strong>{fieldLabel + ': '}</strong>
				<a href={fieldURLValue}>{fieldValue}</a>
				<br />
			</>
		);
	}

	return (
		<>
			<strong>{fieldLabel + ': '}</strong>
			{fieldValue}
			<br />
		</>
	);
}

export default Jethr0InformationField;
