/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const AttachmentsIcon = (props) => (
	<svg
		fill="none"
		height="16"
		viewBox="0 0 16 16"
		width="16"
		xmlns="http://www.w3.org/2000/svg"
		{...props}
	>
		<mask
			height="14"
			id="mask0_631_4022"
			maskUnits="userSpaceOnUse"
			style={{maskType: 'alpha'}}
			width="16"
			x="0"
			y="1"
		>
			<path
				d="M14 4H11L7.53438 1.4C7.1875 1.14062 6.76562 1 6.33437 1H2C0.896875 1 0 1.89688 0 3V13C0 14.1031 0.896875 15 2 15H14C15.1031 15 16 14.1031 16 13V6C16 4.89687 15.1031 4 14 4Z"
				fill="#6B6C7E"
			/>
		</mask>

		<g mask="url(#mask0_631_4022)">
			<rect fill="#282934" height="16" width="16" />
		</g>
	</svg>
);

export {AttachmentsIcon};
