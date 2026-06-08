/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import Card from '~/shared/components/Card';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {Routes, toRoute} from '~/shared/util/router';

interface IAddDataSourceProps {
	groupId: string;
}

const AddDataSource: React.FC<IAddDataSourceProps> = ({groupId}) => (
	<>
		<Card.Header>
			<Card.Title>{Liferay.Language.get('distribution')}</Card.Title>
		</Card.Header>

		<Card.Body>
			<NoResultsDisplay
				description={Liferay.Language.get(
					'convert-and-enrich-anonymous-individuals-to-known-individuals-with-attributes-from-another-data-source'
				)}
				primary
				title=""
			>
				<ClayLink
					button
					className="button-root"
					displayType="primary"
					href={toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {groupId})}
				>
					{Liferay.Language.get('add-data-source')}
				</ClayLink>
			</NoResultsDisplay>
		</Card.Body>
	</>
);

export default AddDataSource;
