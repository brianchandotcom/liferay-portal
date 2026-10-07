import * as API from 'shared/api';
import CustomStringInput from './CustomStringInput';
import React from 'react';
import {ICustomStringInputProps} from './CustomStringInput';
import {IPaginatedDataSourceParams} from 'shared/hooks/usePaginatedRequest';

const OrganizationTextInput: React.FC<ICustomStringInputProps> = (props) => {
	const {
		channelId,
		groupId,
		property: {id},
	} = props;

	const fieldValuesDataSourceFn = ({
		page,
		pageSize,
		query,
	}: IPaginatedDataSourceParams) =>
		API.individuals.fetchFieldValues({
			channelId,
			delta: pageSize,
			fieldMappingFieldName: id,
			groupId,
			page,
			query,
		});
	return (
		<CustomStringInput
			{...props}
			autocomplete={!!id}
			fieldValuesDataSourceFn={fieldValuesDataSourceFn}
		/>
	);
};
export default OrganizationTextInput;
