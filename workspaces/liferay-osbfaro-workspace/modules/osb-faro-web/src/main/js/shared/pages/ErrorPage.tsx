/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React from 'react';
import {Routes, toRoute} from '~/shared/util/router';

interface IErrorPageProps {
	className?: string;
	href?: string;
	linkLabel?: string;
	message?: string;
	subtitle?: string;
	title?: string;
}

const ErrorPage: React.FC<IErrorPageProps> = ({
	className,
	href = toRoute(Routes.BASE),
	linkLabel = Liferay.Language.get('go-to-home'),
	message = Liferay.Language.get(
		'the-page-you-are-looking-for-does-not-exist'
	),
	subtitle = Liferay.Language.get('page-not-found'),
	title = '404',
}) => (
	<div className={getCN('error-page-root', 'page-container', className)}>
		<h1>{title}</h1>

		<h3>{subtitle}</h3>

		<p>{message}</p>

		<ClayLink
			button
			className="button-root"
			displayType="secondary"
			href={href}
		>
			{linkLabel}
		</ClayLink>
	</div>
);

export default ErrorPage;
