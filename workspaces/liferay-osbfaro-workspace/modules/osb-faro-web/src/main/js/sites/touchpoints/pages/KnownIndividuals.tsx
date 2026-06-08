/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import KnownIndividualsListCard from '../hocs/KnownIndividualsListCard';

interface ITouchpointKnownIndividualsPageProps {
	router: object;
}

const TouchpointKnownIndividualsPage: React.FC<
	ITouchpointKnownIndividualsPageProps
> = ({router}) => (
	<div className="row">
		<div className="col-sm-12">
			<KnownIndividualsListCard router={router} />
		</div>
	</div>
);

export default TouchpointKnownIndividualsPage;
