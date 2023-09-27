/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IProps {
	children?: React.ReactNode;
	name?: string;
	title?: string;
}

const Body = ({children, name, title}: IProps) => (
	<div className="bg-neutral-0 mt-4 p-md-2 pt-4 px-lg-4 py-1 rounded">
		{name && (
			<div className="font-weight-bold mb-1 text-brand-primary-lighten-2 text-small-caps">
				{name}
			</div>
		)}

		<h5>{title}</h5>

		{children}
	</div>
);

export default Body;
