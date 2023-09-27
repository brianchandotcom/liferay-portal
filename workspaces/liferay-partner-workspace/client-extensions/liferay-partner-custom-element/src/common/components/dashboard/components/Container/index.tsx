/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

interface IProps {
	children?: ReactNode;
	className?: string;
	footer?: ReactNode;
	title?: string;
}

const Container = ({children, className, footer, title}: IProps) => (
	<div
		className={`bg-neutral-0 d-flex flex-column justify-content-between py-4 rounded ${className}`}
	>
		<div>
			<div className="font-weight-semi-bold h5 m-0">{title}</div>

			<div>
				<hr className="mb-4 mt-1" />
			</div>
		</div>

		<div className="align-items-stretch d-flex flex-column">{children}</div>

		<div>
			<hr className="mb-3 mt-1" />
		</div>

		{footer && (
			<div className="d-flex justify-content-end">
				<div>{footer}</div>
			</div>
		)}
	</div>
);

export default Container;
