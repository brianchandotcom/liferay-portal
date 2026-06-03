import FieldValueFilter from './FieldValueFilter';
import React from 'react';

const CountryFilter = () => (
	<FieldValueFilter
		entityLabel={Liferay.Language.get('countries')}
		fieldMappingFieldName='country'
		filterKey='countryFilter'
	/>
);

export default CountryFilter;
