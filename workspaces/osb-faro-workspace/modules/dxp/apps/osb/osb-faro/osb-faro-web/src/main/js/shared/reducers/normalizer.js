import {
	Account,
	DataSource,
	Distribution,
	EntityLayout,
	Individual,
	Project,
	RemoteData,
	SearchResults,
	Segment,
	User
} from 'shared/util/records';
import {fromJS, Map} from 'immutable';
import {keys} from 'lodash';

const ENTITY_RECORD_MAP = {
	accounts: Account,
	dataSources: DataSource,
	individuals: Individual,
	interests: SearchResults,
	layouts: EntityLayout,
	projects: Project,
	segments: Segment,
	users: User
};

function getRequestState(Record, data) {
	return new RemoteData({
		data: new Record(fromJS(data)),
		error: false,
		loading: false
	});
}

export default (state = new Map(), action) => {
	const {meta, payload} = action;

	let retVal = state;

	if (payload && payload.entities) {
		const {entities} = payload;

		retVal = keys(entities).reduce(
			(finalResult, entityType) =>
				keys(entities[entityType]).reduce((result, id) => {
					const entity = entities[entityType][id];
					const entityRecord = ENTITY_RECORD_MAP[entityType];

					switch (entityType) {
						case 'cardTemplates':
							return result.setIn(
								[entityType, id],
								fromJS(entity)
							);
						case 'distributions':
							return result.setIn(
								[entityType, id],
								new Distribution(
									fromJS({
										data: entity.data,
										fieldMappingFieldName:
											meta.fieldMappingFieldName,
										loading: false
									})
								)
							);
						case 'interests':
							return result.setIn(
								[entityType, meta.contactsEntityId],
								getRequestState(entityRecord, entity.data)
							);
						case 'layouts':
							return result.setIn(
								[entityType, entity.data.type, id],
								getRequestState(entityRecord, entity.data)
							);
						case 'projects':
							return result.setIn(
								[entityType, String(id)],
								getRequestState(entityRecord, entity.data)
							);
						default:
							if (entityRecord) {
								result = result.setIn(
									[entityType, id],
									getRequestState(entityRecord, entity.data)
								);
							}

							return result;
					}
				}, finalResult),
			state
		);
	}

	return retVal;
};
