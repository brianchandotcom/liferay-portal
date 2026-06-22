import {getMetricsData} from 'shared/components/metric-card/util';
import {getVariables, safeResultToProps} from 'shared/util/mappers';

/**
 * MAPPER
 * @description Get Metrics Mapper
 * @param {function} getData
 * @param {array} metrics
 */
const getMetricsMapper = (getData, metrics, chartDataMapFn) => {
	const mapResultToProps = safeResultToProps(
		(result, context, {rangeSelectors}) => ({
			items: getMetricsData(
				getData(result),
				metrics,
				rangeSelectors,
				chartDataMapFn
			)
		})
	);

	/**
	 * Map Props to Options
	 * @param {object} param0 props
	 * @param {object} param1 context
	 */
	const mapPropsToOptions = ({
		assetId,
		filters,
		interval,
		rangeSelectors,
		router: {params}
	}) => {
		const {variables} = getVariables({
			assetId,
			filters,
			interval,
			params,
			rangeSelectors
		});

		return {
			variables
		};
	};

	return {
		options: mapPropsToOptions,
		props: mapResultToProps
	};
};

export default getMetricsMapper;
export {getMetricsMapper};
