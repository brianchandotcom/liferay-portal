/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {RangeSelectors} from '~/shared/types';
import PagePathCard from '~/sites/touchpoints/components/sankey/PagePathCard';

interface ITouchpointPathPageProps {
	rangeSelectors: RangeSelectors;
	selectedSegment?: {id: string};
}

const TouchpointPathPage = (props: ITouchpointPathPageProps) => (
	<div className="row">
		<div className="col-sm-12">
			<PagePathCard {...props} />
		</div>
	</div>
);

export default TouchpointPathPage;
