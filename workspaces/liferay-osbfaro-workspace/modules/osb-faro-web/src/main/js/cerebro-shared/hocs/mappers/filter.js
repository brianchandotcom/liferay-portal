import {getFilterItem} from 'shared/util/filter';
import {getVariables, safeResultToProps} from 'shared/util/mappers';

/**
 * MAPPER
 * @description Get Filters Mapper
 * @param {function} getData
 */
const getFiltersMapper = getData => {
	const mapResultToProps = safeResultToProps(result => {
		const {device = [], geolocation = []} = getData(result);

		return {
			items: [
				getFilterItem(device, 'devices'),
				getFilterItem(geolocation, 'location')
			]
		};
	});

	/**
	 * Map Props to Options
	 * @param {object} param0 props
	 * @param {object} param1 context
	 */
	const mapPropsToOptions = ({rangeSelectors, router: {params}}) =>
		getVariables({params, rangeSelectors});

	return {
		options: mapPropsToOptions,
		props: mapResultToProps
	};
};

export default getFiltersMapper;
export {getFiltersMapper};
