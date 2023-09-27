/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Tag.scss';

interface TagProps {
	label: string;
}

export function Tag({label}: TagProps) {
	return <span className="tag">{label}</span>;
}
