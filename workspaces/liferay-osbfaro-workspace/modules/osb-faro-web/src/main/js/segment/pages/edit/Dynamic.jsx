import React from 'react';
import SegmentEditor from 'segment/segment-editor/dynamic';
import withBaseEdit from 'contacts/hoc/segment/WithBaseEdit';
import withPropertyGroups from 'segment/segment-editor/dynamic/hoc/WithPropertyGroups';
import {compose} from 'shared/hoc';
import {get} from 'lodash';
import {useQueryParams} from 'shared/hooks/useQueryParams';

const DynamicSegmentEdit = props => {
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
