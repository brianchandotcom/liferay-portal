/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

interface IProps {
	button?: React.ReactNode;
	greetings?: string;
	headerClass?: string;
	helper?: string;
	title: string;
}

const FormLayoutHeader: React.FC<IProps> = ({
	button,
	greetings,
	headerClass,
	helper,
	title,
}) => {
	return (
		<header className={classNames('p-4', headerClass)}>
			<div className="d-flex justify-content-between">
				{greetings && (
					<div className="h6 mb-1 text-brand-primary text-small-caps">
						{greetings}
					</div>
				)}

				{button}
			</div>

			<h2
				className={classNames('text-neutral-10', {
					'mb-0': !helper,
					'mb-1': helper,
				})}
			>
				{title}
			</h2>

			{helper && (
				<p className="mb-0 text-neutral-7 text-paragraph-sm">
					{helper}
				</p>
			)}
		</header>
	);
};

export default FormLayoutHeader;
