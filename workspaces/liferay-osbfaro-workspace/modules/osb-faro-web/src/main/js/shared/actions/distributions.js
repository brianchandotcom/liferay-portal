import * as API from 'shared/api';
import {CALL_API} from 'shared/middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {getDistributionSchema} from 'shared/middleware/schema';

export const INDIVIDUALS_DASHBOARD_DISTRUBTIONS_KEY = 'individualsDashboard';

export const actionTypes = {
	...createActionTypes('fetch', 'distribution'),
	...createActionTypes('fetch', 'individuals_distribution')
};

export function fetchDistribution(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.distributions.fetch,
				schema: getDistributionSchema(data.individualSegmentId),
				types: [
					actionTypes.FETCH_DISTRIBUTION_REQUEST,
					actionTypes.FETCH_DISTRIBUTION_SUCCESS,
					actionTypes.FETCH_DISTRIBUTION_FAILURE
				]
			},
			fieldMappingFieldName: data.fieldMappingFieldName,
			individualSegmentId: data.individualSegmentId
		},
		payload: {
			id: data.individualSegmentId
		},
		type: 'NO_OP'
	};
}

export function fetchIndividualsDistribution(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.distributions.fetch,
				schema: getDistributionSchema(
					INDIVIDUALS_DASHBOARD_DISTRUBTIONS_KEY
				),
				types: [
					actionTypes.FETCH_DISTRIBUTION_REQUEST,
					actionTypes.FETCH_DISTRIBUTION_SUCCESS,
					actionTypes.FETCH_DISTRIBUTION_FAILURE
				]
			},
			fieldMappingFieldName: data.fieldMappingFieldName
		},
		payload: {
			id: INDIVIDUALS_DASHBOARD_DISTRUBTIONS_KEY
		},
		type: 'NO_OP'
	};
}
