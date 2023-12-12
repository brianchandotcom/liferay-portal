/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet} from 'react-router-dom';

import Sidebar from '../Sidebar';
import Header from './Header';

const Layout = () => (
	<main className="tr-main">
		<div className="tr-main__body">
			<Sidebar />

			<div className="tr-main__body__page">
				<Header />

				<section className="tr-main__body__page__content">
					<Outlet />
				</section>
			</div>
		</div>
	</main>
);

export default Layout;
