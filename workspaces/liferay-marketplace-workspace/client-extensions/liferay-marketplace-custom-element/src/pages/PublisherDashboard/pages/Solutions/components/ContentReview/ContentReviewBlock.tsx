/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

interface ContentReviewBlockProps {
	children: ReactNode;
	title: string;
}
export function ContentReviewBlock({children, title}: ContentReviewBlockProps) {
	return (
		<div className="border my-3 overflow-hidden rounded-lg">
			<div className="bg-light px-4 py-3">
				<strong>{title}</strong>
			</div>

			<div className="p-4">{children}</div>
		</div>
	);
}
