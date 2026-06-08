/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import getCN from 'classnames';
import {noop} from 'lodash';
import React, {useEffect, useState} from 'react';
import * as API from '~/shared/api';
import Modal from '~/shared/components/modal';
import {sub} from '~/shared/util/lang';

const ROW_DELTA = 10;

interface ICSVPreviewModalProps extends React.HTMLAttributes<HTMLElement> {
	fieldName?: string;
	fileVersionId?: string | number;
	groupId: string;
	id: string;
	name: string;
	onClose?: () => void;
}

type CSVFieldData = {
	name: string;
	values: string[];
};

const CSVPreviewModal: React.FC<ICSVPreviewModalProps> = ({
	className,
	fieldName,
	fileVersionId,
	groupId,
	id,
	name,
	onClose = noop,
}) => {
	const [data, setData] = useState<CSVFieldData[]>([]);

	useEffect(() => {
		const fetchData = () => {
			API.dataSource
				.fetchFieldValues({
					count: ROW_DELTA,
					fieldName,
					fileVersionId,
					groupId,
					id,
				})
				.then(setData)
				.catch(noop);
		};

		fetchData();
	}, [fieldName, fileVersionId, groupId, id]);

	return (
		<Modal className={getCN('csv-preview-modal-root', className)} size="lg">
			<Modal.Header
				onClose={onClose}
				title={sub(Liferay.Language.get('data-preview-x'), [name])}
			/>

			<Modal.Body>
				<ClayTable>
					<ClayTable.Head>
						<ClayTable.Row>
							{data.map(({name}, i) => (
								<ClayTable.Cell expanded headingCell key={i}>
									{name}
								</ClayTable.Cell>
							))}
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						{!!data.length &&
							data[0].values.map((_val: string, row: number) => (
								<ClayTable.Row key={row}>
									{data.map((_val, column) => (
										<ClayTable.Cell
											key={`${column}-${row}`}
										>
											{data[column].values[row]}
										</ClayTable.Cell>
									))}
								</ClayTable.Row>
							))}
					</ClayTable.Body>
				</ClayTable>
			</Modal.Body>
		</Modal>
	);
};

export default CSVPreviewModal;
