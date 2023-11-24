import {useOutletContext} from 'react-router-dom';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import projectsIcon from '../../../assets/icons/projects_icon.svg';
import {ProjectsPage} from '../../ProjectsPage/ProjectsPage';

const Projects = () => {
	const {selectedAccount, setShowDashboardNavigation} =
		useOutletContext<any>();

	return (
		<ProjectsPage
			icon={projectsIcon}
			selectedAccount={selectedAccount}
			setShowDashboardNavigation={setShowDashboardNavigation}
		/>
	);
};

export default Projects;
