/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {ReactNode} from 'react';
import {Link} from 'react-router-dom';

import i18n from '../../../../../../i18n';

interface ContentReviewHeaderProps {
	as?: 'h1' | 'h2' | 'h3' | 'span';
	children: ReactNode;
	className?: string;
	path?: string;
}

export function ContentReviewHeader({
	as = 'h1',
	children,
	className,
	path,
}: ContentReviewHeaderProps) {
	const Wrapper = as;

	return (
		<div
			className={classNames(
				'align-items-center d-flex flex-row justify-content-between mb-5',
				className
			)}
		>
			<Wrapper>{children}</Wrapper>

			{path && (
				<Link className="font-weight-bold" to={path}>
					{i18n.translate('edit')}
				</Link>
			)}
		</div>
	);
}
