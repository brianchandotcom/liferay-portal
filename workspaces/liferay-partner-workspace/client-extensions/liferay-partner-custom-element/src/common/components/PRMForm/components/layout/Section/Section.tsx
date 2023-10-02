/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IProps {
	children?: React.ReactNode;
	subtitle?: string;
	title: string;
}

const Section = ({children, subtitle, title}: IProps) => (
	<div>
		<div className="border-bottom border-neutral-2 mb-4 py-2">
			<h5 className="font-weight-bold mb-0 text-paragraph">{title}</h5>

			<div className="text-neutral-8 text-paragraph-sm">{subtitle}</div>
		</div>

		{children}
	</div>
);

export default Section;
