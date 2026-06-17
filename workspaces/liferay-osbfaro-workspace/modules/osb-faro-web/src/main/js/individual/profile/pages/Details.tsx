import * as API from 'shared/api';
import BaseDetails from 'contacts/pages/BaseDetails';
import React from 'react';
import {Individual} from 'shared/util/records';

interface IFieldMapping {
	name: string;
	sourceName: string;
}

interface IFetchDetailsResult {
	custom: Record<string, [IFieldMapping, ...IFieldMapping[]]>;
	demographics: Record<string, IFieldMapping[]>;
}

const fetchIndividualDetails = ({groupId, id}: {groupId: string; id: string}) =>
	API.individuals
		.fetchDetails({groupId, individualId: id})
		.then(({custom, demographics}: IFetchDetailsResult) => {
			const retVal: Record<string, IFieldMapping[]> = {...demographics};

			(
				Object.values(custom) as [IFieldMapping, ...IFieldMapping[]][]
			).forEach(([fieldMapping, ...others]) => {
				retVal[`custom-${fieldMapping.name}`] = [
					{
						...fieldMapping,
						sourceName: `[${Liferay.Language.get(
							'custom-field'
						)}] ${fieldMapping.sourceName}`,
					},
					...others,
				];
			});

			return retVal;
		});

interface IDetailsProps {
	groupId: string;
	individual: Individual;
}

const Details: React.FC<IDetailsProps> = ({groupId, individual}) => (
	<BaseDetails
		dataSourceFn={fetchIndividualDetails}
		groupId={groupId}
		id={individual.id}
		title={Liferay.Language.get('individual-attributes')}
	/>
);

export default Details;
