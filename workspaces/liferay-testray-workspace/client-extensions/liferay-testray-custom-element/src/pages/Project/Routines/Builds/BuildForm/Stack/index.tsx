/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import RunsList, {RunsListProps} from './RunsList';
import RunsHistory from './RunsHistory';

type StackProps = {} & RunsListProps;

const Stack: React.FC<StackProps> = ({
	append,
	fields,
	remove,
	...stackProps
}) => {
	const [fieldsHistory, setFieldsHistory] = useState<{id: number}[]>([]);

	const onRemove = (index: number) => {
		remove(index);

		setFieldsHistory([...fieldsHistory, fields[index] as any]);
	};

	return (
		<>
			<RunsHistory
				append={append}
				fieldsHistory={fieldsHistory}
				setFieldsHistory={setFieldsHistory}
			/>

			<RunsList
				{...stackProps}
				append={append}
				fields={fields}
				remove={onRemove}
			/>
		</>
	);
};

export default Stack;
