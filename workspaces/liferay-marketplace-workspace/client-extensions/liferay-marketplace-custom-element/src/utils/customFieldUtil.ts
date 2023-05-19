export function getCustomFieldValue(
	customFields: CustomField[],
	customFieldName: string
) {
	const customField = customFields?.find(
		(customField) => customField.name === customFieldName
	);

	if (customField) {
		const {
			customValue: {data},
		} = customField;

		return data;
	}

	return '';
}
