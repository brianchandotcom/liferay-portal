/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Modal from '~/shared/components/modal';
import Table from '~/shared/components/table';

interface IIndividualAttributesModalInterface
	extends React.HTMLAttributes<HTMLElement> {
	dataSources: {
		dataSourceFieldName: string;
		dataSourceName: string;
	}[];
	fieldName: string;
	onClose: () => void;
}

const IndividualAttributesModal: React.FC<
	IIndividualAttributesModalInterface
> = ({dataSources, fieldName, onClose}) => (
	<Modal>
		<Modal.Header onClose={onClose} title={fieldName} />

		<Modal.Body>
			<div className="h5">{Liferay.Language.get('data-sources')}</div>

			<Table
				columns={[
					{
						accessor: 'dataSourceName',
						label: Liferay.Language.get('source'),
						sortable: false,
					},
					{
						accessor: 'dataSourceFieldName',
						label: Liferay.Language.get('attribute[noun]'),
						sortable: false,
					},
				]}
				items={dataSources}
				rowIdentifier="dataSourceName"
			/>
		</Modal.Body>
	</Modal>
);

export default IndividualAttributesModal;
