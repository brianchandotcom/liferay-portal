/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';

function Jethr0NavigationBar({active}) {
	return (
		<ClayNavigationBar triggerLabel={active}>
			<ClayNavigationBar.Item active={active === 'Home'}>
				<ClayLink href="/#/">Home</ClayLink>
			</ClayNavigationBar.Item>
			<ClayNavigationBar.Item active={active === 'Jobs'}>
				<ClayLink href="/#/jobs">Jobs</ClayLink>
			</ClayNavigationBar.Item>
		</ClayNavigationBar>
	);
}

export default Jethr0NavigationBar;
