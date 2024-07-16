/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getTooltipTitles(title) {
	const activationNames = title?.split(',');

	return (
		!!activationNames.length && (
			<div>
				<p className="font-weight-bold m-0">{activationNames[0]}</p>

				{activationNames.length > 1 && (
					<p className="font-weight-normal m-0 text-paragraph-sm">
						{activationNames[1]}
					</p>
				)}
			</div>
		)
	);
}
