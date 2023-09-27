/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

const AuthorizedPartnerIcon = () => {
	return (
		<svg
			fill="none"
			height="33"
			viewBox="0 0 32 33"
			width="32"
			xmlns="http://www.w3.org/2000/svg"
		>
			<circle cx="16" cy="16.5469" fill="#DEE9FD" r="16" />

			<mask
				height="33"
				id="mask0_6629_387786"
				maskUnits="userSpaceOnUse"
				style={{maskType: 'alpha'}}
				width="32"
				x="0"
				y="0"
			>
				<circle cx="16" cy="16.5469" fill="#B3CDFF" r="16" />
			</mask>

			<g mask="url(#mask0_6629_387786)">
				<path
					d="M14.8874 16.2829L14.4734 17.7229H17.4794L17.0654 16.2829C16.8734 15.6589 16.6874 15.0169 16.5074 14.3569C16.3394 13.6969 16.1714 13.0429 16.0034 12.3949H15.9314C15.7754 13.0549 15.6074 13.7149 15.4274 14.3749C15.2594 15.0229 15.0794 15.6589 14.8874 16.2829ZM10.9454 22.5469L14.7794 10.7749H17.2274L21.0614 22.5469H18.8654L17.9474 19.3609H13.9874L13.0694 22.5469H10.9454Z"
					fill="#2E5AAC"
				/>

				<path
					d="M28.3333 3.74707L-1.06665 33.1471L14.3333 48.5471L40.9061 21.3416C42.4623 19.7484 42.4175 17.191 40.8065 15.6533L28.3333 3.74707Z"
					fill="url(#paint0_linear_6629_387786)"
					opacity="0.6"
				/>
			</g>

			<defs>
				<linearGradient
					gradientUnits="userSpaceOnUse"
					id="paint0_linear_6629_387786"
					x1="26.2334"
					x2="25.5334"
					y1="0.94707"
					y2="32.4471"
				>
					<stop stopColor="white" />

					<stop offset="1" stopColor="white" stopOpacity="0" />
				</linearGradient>
			</defs>
		</svg>
	);
};

export default AuthorizedPartnerIcon;
