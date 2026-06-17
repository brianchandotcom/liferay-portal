import FieldValueFilter from './FieldValueFilter';
import React from 'react';

const IndustryFilter = () => (
	<FieldValueFilter
		className="mr-2"
		entityLabel={Liferay.Language.get('industries')}
		fieldMappingFieldName="industry"
		filterKey="industryFilter"
	/>
);

export default IndustryFilter;
