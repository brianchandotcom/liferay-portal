/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getLocationsData} from '~/shared/util/charts';
import {getFilters} from '~/shared/util/filter';
import {getVariables, safeResultToProps} from '~/shared/util/mappers';
import {getSafeRangeSelectors} from '~/shared/util/util';

/**
 * MAPPER
 * @description Get Locations Mapper
 * @param {function} getMetric
 */
const getLocationsMapper = (getMetric) => {
	const mapResultToProps = safeResultToProps(
		(result, {filters}, {rangeSelectors}) => {
			let {geolocation} = getMetric(result);

			const {location} = getFilters(filters);

			if (location !== 'Any') {
				geolocation = geolocation[0].metrics;
			}

			const locationsData = getLocationsData(geolocation, location);

			return {
				...getSafeRangeSelectors(rangeSelectors),
				data: {
					geolocation: locationsData,
					total: locationsData.length,
				},
			};
		}
	);

	/**
	 * Map Props to Options
	 * @param {object} param0 props
	 * @param {object} param1 context
	 */
	const mapPropsToOptions = ({
		filters,
		interval,
		rangeSelectors,
		router: {params},
	}) => getVariables({filters, interval, params, rangeSelectors});

	return {
		options: mapPropsToOptions,
		props: mapResultToProps,
	};
};

/**
 * MAPPER
 * @description Get Countries Mapper
 * @param {function} getMetric
 */
const getLocationsMapperCountries = (getMetric) => {
	const mapResultToProps = safeResultToProps((result) => {
		const {geolocation} = getMetric(result);
		const countries = getLocationsData(geolocation, location);

		return {
			data: {
				countries,
				total: countries.length,
			},
		};
	});

	/**
	 * Map Props to Options
	 * @param {object} param0 props
	 * @param {object} param1 context
	 */
	const mapPropsToOptions = ({
		experienceId,
		filters,
		interval,
		rangeSelectors,
		router: {params},
	}) => {
		const {variables} = getVariables({
			experienceId,
			filters,
			interval,
			params,
			rangeSelectors,
		});

		return {
			variables: {
				...variables,
				location: 'Any',
			},
		};
	};

	return {
		options: mapPropsToOptions,
		props: mapResultToProps,
	};
};
export {getLocationsMapper, getLocationsMapperCountries};
export default getLocationsMapper;
