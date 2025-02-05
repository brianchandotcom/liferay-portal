/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const FilterIcon = ({columnName, handleSortChange}) => (
	<svg
		className="align-self-center"
		fill="none"
		height="16"
		onClick={() => handleSortChange(columnName)}
		viewBox="0 0 16 16"
		width="16"
		xmlns="http://www.w3.org/2000/svg"
	>
		<mask
			height="16"
			id="mask0_639_1685"
			maskUnits="userSpaceOnUse"
			style={{maskType: 'alpha'}}
			width="7"
			x="9"
			y="0"
		>
			<path
				d="M15.6654 12.0187C15.4646 11.8226 15.2 11.723 14.9355 11.723C14.671 11.723 14.4064 11.8226 14.2056 12.0187L13.6733 12.5385V0.996109C13.6733 0.445136 13.2176 0 12.6534 0C12.0893 0 11.6335 0.445136 11.6335 0.996109V12.5385L11.1012 12.0187C10.6965 11.6233 10.0431 11.6233 9.64148 12.0187C9.2367 12.414 9.2367 13.0521 9.64148 13.4444L11.9235 15.6732C12.118 15.863 12.3793 15.9689 12.6534 15.9689C12.9275 15.9689 13.1889 15.863 13.3833 15.6732L15.6654 13.4444C16.0702 13.049 16.0702 12.4109 15.6654 12.0187Z"
				fill="#6B6C7E"
			/>
		</mask>

		<g mask="url(#mask0_639_1685)">
			<rect fill="#999AA3" height="16" width="16" />
		</g>

		<mask
			height="16"
			id="mask1_639_1685"
			maskUnits="userSpaceOnUse"
			style={{maskType: 'alpha'}}
			width="7"
			x="0"
			y="0"
		>
			<path
				d="M6.35874 2.55564L4.07667 0.326848C3.88225 0.136964 3.62089 0.0311279 3.34679 0.0311279C3.07268 0.0311279 2.81133 0.136964 2.61691 0.326848L0.334836 2.55564C-0.0699452 2.95097 -0.0699452 3.5891 0.334836 3.98132C0.535632 4.17743 0.800174 4.27704 1.06472 4.27704C1.32926 4.27704 1.5938 4.17743 1.7946 3.98132L2.32687 3.46148V15.0039C2.32687 15.5549 2.78264 16 3.34679 16C3.91093 16 4.36671 15.5549 4.36671 15.0039V3.46148L4.89898 3.98132C5.30376 4.37665 5.95715 4.37665 6.35874 3.98132C6.76352 3.58599 6.76352 2.94786 6.35874 2.55564Z"
				fill="#6B6C7E"
			/>
		</mask>

		<g mask="url(#mask1_639_1685)">
			<rect fill="#999AA3" height="16" width="16" />
		</g>
	</svg>
);

export {FilterIcon};
