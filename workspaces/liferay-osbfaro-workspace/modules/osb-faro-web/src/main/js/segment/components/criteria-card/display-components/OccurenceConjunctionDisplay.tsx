/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {
	OCCURENCE_OPTIONS,
	RelationalOperators,
} from '~/segment/segment-editor/dynamic/utils/constants';

interface IOccurenceConjunctionDisplayProps {
	operatorName: RelationalOperators;
	value: number;
}

const OccurenceConjunctionDisplay: React.FC<
	IOccurenceConjunctionDisplayProps
> = ({operatorName, value}) => {
	const {label = ''} =
		OCCURENCE_OPTIONS.find(({value}) => value === operatorName) || {};

	return (
		<>
			<span>{label}</span>

			<b>{value}</b>

			<span>{Liferay.Language.get('times')}</span>
		</>
	);
};

export default OccurenceConjunctionDisplay;
