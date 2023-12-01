/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Jethr0ContainerFluid.css';

import ClayLayout from '@clayui/layout';

function Jethr0ContainerFluid({children}) {
	return (
		<ClayLayout.ContainerFluid className="jethr0-container-fluid">
			{children}
		</ClayLayout.ContainerFluid>
	);
}

export default Jethr0ContainerFluid;
