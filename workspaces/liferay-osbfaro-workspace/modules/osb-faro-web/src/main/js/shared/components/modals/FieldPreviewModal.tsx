/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {get, noop} from 'lodash';
import React, {useEffect, useState} from 'react';
import ListGroup from '~/shared/components/list-group';
import Modal from '~/shared/components/modal';
import {sub} from '~/shared/util/lang';

interface IFieldPreviewModalProps extends React.HTMLAttributes<HTMLDivElement> {
	dataSourceFn: () => Promise<any>;
	fieldName?: string;
	onClose: () => void;
	sourceName: string;
}

const FieldPreviewModal: React.FC<IFieldPreviewModalProps> = ({
	className,
	dataSourceFn,
	fieldName,
	onClose = noop,
	sourceName,
	...otherProps
}) => {
	const [fieldData, setFieldData] = useState([]);

	const getFieldData = () =>
		dataSourceFn().then((fieldData) => {
			setFieldData(get(fieldData, [0, 'values'], []));
		});

	useEffect(() => {
		getFieldData();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<Modal
			{...otherProps}
			className={getCN('field-preview-modal-root', className)}
			size="lg"
		>
			<Modal.Header
				onClose={onClose}
				title={sub(Liferay.Language.get('field-preview-x'), [
					sourceName,
				])}
			/>

			<ListGroup>
				<ListGroup.Item flex header>
					<ListGroup.ItemField>{fieldName}</ListGroup.ItemField>
				</ListGroup.Item>

				{fieldData.map((item, index) => (
					<ListGroup.Item key={index}>
						<ListGroup.ItemField>{item}</ListGroup.ItemField>
					</ListGroup.Item>
				))}
			</ListGroup>
		</Modal>
	);
};

export default FieldPreviewModal;
