/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const VirtualCluster = (props) => (
	<svg
		fill="none"
		height="20"
		viewBox="0 0 24 24"
		width="20"
		xmlns="http://www.w3.org/2000/svg"
		{...props}
	>
		<g id="File / cloud circle">
			<mask
				height="20"
				id="mask0_70_8106"
				maskUnits="userSpaceOnUse"
				style={{maskType: 'alpha'}}
				width="20"
				x="2"
				y="2"
			>
				<g id="Icon Mask">
					<path
						clipRule="evenodd"
						d="M2 12C2 6.48 6.48 2 12 2C17.52 2 22 6.48 22 12C22 17.52 17.52 22 12 22C6.48 22 2 17.52 2 12ZM8 16H16.5C17.88 16 19 14.88 19 13.5C19 12.12 17.88 11 16.5 11H16C16 8.79 14.21 7 12 7C10.13 7 8.58 8.27 8.14 10H8C6.34 10 5 11.34 5 13C5 14.66 6.34 16 8 16Z"
						fill="black"
						fillRule="evenodd"
						id="Round"
					/>
				</g>
			</mask>

			<g mask="url(#mask0_70_8106)">
				<rect fill="#2E5AAC" height="24" id="Color Fill" width="24" />
			</g>
		</g>
	</svg>
);

export {VirtualCluster};
