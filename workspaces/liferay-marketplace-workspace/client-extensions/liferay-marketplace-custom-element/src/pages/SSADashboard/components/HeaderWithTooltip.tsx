/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Tooltip} from '../../../components/Tooltip/Tooltip';

const HeaderWithTooltip = ({
	title,
	tooltip,
}: {
	title: string;
	tooltip: string;
}) => (
	<div className="align-items-center d-flex">
		<span className="mr-2">{title}</span>
		<Tooltip tooltip={tooltip} />
	</div>
);

export default HeaderWithTooltip;
