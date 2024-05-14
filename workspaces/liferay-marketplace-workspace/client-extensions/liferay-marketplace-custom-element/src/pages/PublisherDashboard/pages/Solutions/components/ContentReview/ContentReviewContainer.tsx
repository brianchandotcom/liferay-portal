/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

interface ContentReviewContainerProps {
	children: ReactNode;
}

export function ContentReviewContainer({
	children,
}: ContentReviewContainerProps) {
	return <div className="border p-5 rounded-lg">{children}</div>;
}
