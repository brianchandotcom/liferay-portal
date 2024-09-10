/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Navbar from './Navbar';
import Sidebar from './Sidebar';

type PropsWithChildren = {
	children?: any;
};

const Body: React.FC<PropsWithChildren> = ({children}) => (
	<div className="d-flex justify-content-center mt-8">{children}</div>
);

const Content: React.FC<PropsWithChildren> = ({children}) => (
	<div className="ml-8 new-app-body-container">{children}</div>
);

const AppPublish: React.FC<PropsWithChildren> & {
	Body: typeof Body;
	Content: typeof Content;
	Navbar: typeof Navbar;
	Sidebar: typeof Sidebar;
} = ({children}) => <div className="publish-app">{children}</div>;

AppPublish.Body = Body;
AppPublish.Content = Content;
AppPublish.Navbar = Navbar;
AppPublish.Sidebar = Sidebar;

export default AppPublish;
