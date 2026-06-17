import getFiltersMapper from 'cerebro-shared/hocs/mappers/filter';
import globalFilterAssetQuery from 'shared/queries/globalFilterAssetQuery';
import {graphql} from '@apollo/client/react/hoc';
import {withFilterComponent} from 'shared/hoc/Filter';

/**
 * HOC
 * @description ObjectEntry Filters
 */
const withObjectEntryFilter = () =>
	graphql(
		globalFilterAssetQuery('ObjectEntry', 'viewsMetric'),
		getFiltersMapper((result) => result.objectEntry.viewsMetric)
	);

export default withFilterComponent(withObjectEntryFilter);
