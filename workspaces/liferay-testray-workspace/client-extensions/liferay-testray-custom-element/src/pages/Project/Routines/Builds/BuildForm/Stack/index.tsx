/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {ACTIONS} from '~/util/constants';

import FactorStackList, {FactorStackListProps} from './FactorStackList';
import RunsHistory from './RunsHistory';
import RunsList, {RunsListProps} from './RunsList';

type StackProps = ({} & RunsListProps) | FactorStackListProps;

const Stack: React.FC<StackProps> = ({
	action,
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
			{action === ACTIONS.CREATE ? (
				<FactorStackList
					{...stackProps}
					append={append}
					fields={fields}
					remove={onRemove}
				/>
			) : (
				<RunsList
					{...stackProps}
					append={append}
					fields={fields}
					remove={onRemove}
				/>
			)}
		</>
	);
};

export default Stack;
