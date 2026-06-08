/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Router} from '~/shared/types';
import Touchpoints from '~/sites/hocs/Touchpoints';

const TouchpointsPage = ({router}: {router: Router}) => (
	<div className="sites-dashboard-touchpoints-list-root">
		<div className="row">
			<div className="col-xl-12">
				<Touchpoints router={router} />
			</div>
		</div>
	</div>
);

export default TouchpointsPage;
