/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import React from 'react';

export const buildHeaderSubtitle = function buildHeaderSubtitle(individual: {
	accountName: string;
	lastSessionCountry: string;
	properties: {email: string};
}) {
	const {email} = individual.properties;
	const {accountName, lastSessionCountry} = individual;

	return (
		<Text color="secondary" size={4}>
			{[email, accountName, lastSessionCountry]
				.filter(Boolean)
				.join(' | ')}
		</Text>
	);
};
