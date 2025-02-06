/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SVGProps} from 'react';
import {JSX} from 'react/jsx-runtime';

const SVWaves = (props: JSX.IntrinsicAttributes & SVGProps<SVGSVGElement>) => (
	<svg
		fill="none"
		height="369"
		viewBox="0 0 370 369"
		width="370"
		xmlns="http://www.w3.org/2000/svg"
		{...props}
	>
		<g clipPath="url(#clip0_4_9723)">
			<path
				d="M447.352 114.243C293.312 -206.152 -195.251 364.453 144.735 429.787C459.012 490.217 521.178 267.775 447.357 114.243H447.352Z"
				fill="url(#paint0_linear_4_9723)"
			/>
			<path
				d="M132.819 106.831C-117.002 160.678 123.509 523.161 278.681 506.599C433.874 490.092 569.985 238.477 455.144 112.415C340.226 -13.6747 288.671 74.2775 132.819 106.831Z"
				fill="url(#paint1_linear_4_9723)"
			/>
		</g>
		<defs>
			<linearGradient
				gradientUnits="userSpaceOnUse"
				id="paint0_linear_4_9723"
				x1="440.871"
				x2="88.314"
				y1="8.1343"
				y2="502.499"
			>
				<stop stopColor="white" stopOpacity="0.25" />
				<stop offset="0.64" stopColor="white" stopOpacity="0.08" />
				<stop offset="1" stopColor="white" stopOpacity="0" />
			</linearGradient>
			<linearGradient
				gradientUnits="userSpaceOnUse"
				id="paint1_linear_4_9723"
				x1="508.804"
				x2="44.4629"
				y1="73.3333"
				y2="442.129"
			>
				<stop stopColor="white" />
				<stop offset="0.01" stopColor="white" stopOpacity="0.97" />
				<stop offset="0.13" stopColor="white" stopOpacity="0.75" />
				<stop offset="0.25" stopColor="white" stopOpacity="0.55" />
				<stop offset="0.37" stopColor="white" stopOpacity="0.38" />
				<stop offset="0.49" stopColor="white" stopOpacity="0.24" />
				<stop offset="0.62" stopColor="white" stopOpacity="0.14" />
				<stop offset="0.74" stopColor="white" stopOpacity="0.06" />
				<stop offset="0.87" stopColor="white" stopOpacity="0.01" />
				<stop offset="1" stopColor="white" stopOpacity="0" />
			</linearGradient>
			<clipPath id="clip0_4_9723">
				<rect fill="white" height="369" width="370" />
			</clipPath>
		</defs>
	</svg>
);

export {SVWaves};
