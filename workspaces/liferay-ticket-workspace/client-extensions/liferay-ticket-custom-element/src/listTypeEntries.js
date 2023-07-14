import axios from 'axios';

async function fetchListTypeEntries(externalReferenceCode) {
	const {data} = await axios.get(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/by-external-reference-code/${externalReferenceCode}/list-type-entries?p_auth=${Liferay.authToken}`
	);

	return data?.items.map((item) => ({
		key: item.key,
		name: item.name,
	}));
}

export const LIST_TICKET_PRIORITIES = 'LIST_TICKET_PRIORITIES';
export const LIST_TICKET_RESOLUTIONS = 'LIST_TICKET_RESOLUTIONS';
export const LIST_TICKET_REGIONS = 'LIST_TICKET_REGIONS';
export const LIST_TICKET_STATUSES = 'LIST_TICKET_STATUSES';
export const LIST_TICKET_TYPES = 'LIST_TICKET_TYPES';

const listTypeDefinitionERCs = [
	LIST_TICKET_PRIORITIES,
	LIST_TICKET_RESOLUTIONS,
	LIST_TICKET_REGIONS,
	LIST_TICKET_STATUSES,
	LIST_TICKET_TYPES,
];

export async function fetchListTypeDefinitions() {
	const listTypeDefinitions = {};
	for (const listTypeDefinitionERC of listTypeDefinitionERCs) {
		listTypeDefinitions[listTypeDefinitionERC] = await fetchListTypeEntries(
			listTypeDefinitionERC
		);
	}

	return listTypeDefinitions;
}
