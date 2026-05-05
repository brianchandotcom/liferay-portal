/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Suspense} from 'react';
import {Outlet} from 'react-router-dom';

import SideNav, {NavItem} from './SideNav';

type AppLayoutProps = {
	navItems: NavItem[];
	title: string;
};

export default function AppLayout({navItems, title}: AppLayoutProps) {
	return (
		<div className="layout">
			<SideNav items={navItems} title={title} />

			<main className="layout__content">
				<Suspense fallback={null}>
					<Outlet />
				</Suspense>
			</main>
		</div>
	);
}
