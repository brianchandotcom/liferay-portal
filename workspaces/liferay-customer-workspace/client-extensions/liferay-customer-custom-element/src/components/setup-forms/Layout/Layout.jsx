/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import Footer from './Footer';
import Header from './Header';

const Layout = ({
	children,
	className,
	footerProps,
	headerProps,
	headerSkeleton,
	layoutType = 'onboarding',
}) => (
	<div
		className={classNames(
			'border d-flex flex-column mx-auto rounded-lg shadow-lg',
			layoutType
		)}
	>
		{headerProps ? <Header {...headerProps} /> : headerSkeleton}

		<main className={classNames('flex-grow-1 overflow-auto', className)}>
			{children}
		</main>

		<Footer {...footerProps} />
	</div>
);
export default Layout;
