/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getDoubleParagraph(
	name?: string,
	description?: string
) {
	return (
		<>
			{name && (
				<span className="font-weight-semi-bold my-0 text-neutral-10 text-truncate">
					{name}
				</span>
			)}
			{description && (
				<span className="my-0 text-neutral-7 text-paragraph-sm text-truncate">
					{description}
				</span>
			)}
		</>
	);
}
