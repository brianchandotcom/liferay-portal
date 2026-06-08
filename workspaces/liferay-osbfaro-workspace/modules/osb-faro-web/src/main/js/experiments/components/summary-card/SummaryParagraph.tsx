/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {SummaryTitle} from './SummaryTitle';

export const SummaryParagraph = function SummaryParagraph({
	description,
	title,
}: {
	description?: React.ReactNode;
	title: React.ReactNode;
}) {
	return (
		<>
			<SummaryTitle className="mb-4" label={title} />
			{description && (
				<>
					<p className="font-size-sm mb-0">{description}</p>
				</>
			)}
		</>
	);
};
