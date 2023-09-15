/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';

import './Jethr0Card.css';

function Jethr0Card({children}) {
	return <ClayCard className="jethr0-card">{children}</ClayCard>;
}

export default Jethr0Card;
