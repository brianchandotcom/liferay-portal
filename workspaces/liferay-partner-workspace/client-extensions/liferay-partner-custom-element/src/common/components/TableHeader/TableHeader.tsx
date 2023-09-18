/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IProps {
	children?: JSX.Element | JSX.Element[];
}

const TableHeader = ({children}: IProps) => {
	return (
		<div className="bg-neutral-1 d-flex d-md-flex flex-column-reverse flex-md-row justify-content-between p-3 rounded">
			{children}
		</div>
	);
};
export default TableHeader;
