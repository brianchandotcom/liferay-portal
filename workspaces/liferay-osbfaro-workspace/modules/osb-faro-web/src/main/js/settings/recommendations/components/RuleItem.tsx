/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {RULE_NAME_LABEL_MAP, getFilterValueBreakdown} from '../utils/utils';
import MetadataTag from './MetadataTag';

interface IRuleItemProps {
	name: string;
	value: string;
}

const RuleItem: React.FC<IRuleItemProps> = ({name, value}) => {
	const {exactMatchSign, metadataTag, rule} = getFilterValueBreakdown(value);

	const exactMatch = exactMatchSign === '=';

	return (
		<div className="align-items-baseline d-flex rule-item-root">
			<b>
				{`${
					RULE_NAME_LABEL_MAP[
						name as keyof typeof RULE_NAME_LABEL_MAP
					]
				}:`}
			</b>

			{metadataTag && <MetadataTag value={metadataTag} />}

			<span className="rule-value">
				{exactMatch ? `"${rule}"` : rule}
			</span>
		</div>
	);
};

export default RuleItem;
