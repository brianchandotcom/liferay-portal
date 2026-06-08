/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get} from 'lodash';
import React from 'react';
import withBaseEdit from '~/contacts/hoc/segment/WithBaseEdit';
import SegmentEditor from '~/segment/segment-editor/dynamic';
import withPropertyGroups from '~/segment/segment-editor/dynamic/hoc/WithPropertyGroups';
import {compose} from '~/shared/hoc';
import {useQueryParams} from '~/shared/hooks/useQueryParams';

const DynamicSegmentEdit = (props) => {
	const params = useQueryParams();
	const {id, propertyGroupsIList, segment} = props;

	const segmentType = get(segment, 'segmentType') || params.type;

	return (
		<SegmentEditor
			{...props}
			id={id}
			propertyGroupsIList={propertyGroupsIList}
			segment={segment}
			type={segmentType}
		/>
	);
};

export default compose(withPropertyGroups, withBaseEdit)(DynamicSegmentEdit);
