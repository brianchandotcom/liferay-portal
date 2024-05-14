/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

interface ContentReviewSeparatorProps {
	className?: string;
}

export function ContentReviewSeparator({
	className,
}: ContentReviewSeparatorProps) {
	return <hr className={classNames('my-6', className)} />;
}
