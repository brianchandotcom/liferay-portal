/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayLabel from '@clayui/label';
import React, {useCallback, useRef} from 'react';
import InputWithEditToggle from '~/shared/components/InputWithEditToggle';
import {validateUniqueName} from '~/shared/util/data-sources';
import {sequence} from '~/shared/util/promise';
import {DataSource} from '~/shared/util/records';
import {
	toPromise,
	validateMaxLength,
	validateRequired,
} from '~/shared/util/validators';

interface IDataSourceEditableTitleProps {
	dataSource: DataSource;
	description?: React.ReactNode;
	displayType?: string;
	editable?: boolean;
	groupId: string;
	label: React.ReactNode;
	onUpdateName: (name: string) => Promise<any> | void;
}

const DataSourceEditableTitle = ({
	dataSource,
	description,
	displayType,
	editable,
	groupId,
	label,
	onUpdateName,
}: IDataSourceEditableTitleProps) => {
	const cachedNameValuesRef = useRef(new Map());

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const handleUpdateName = useCallback(onUpdateName, [
		groupId,
		dataSource.id,
	]);

	const handleValidate = useCallback(
		(value: string) => {
			let error = null;

			if (value !== dataSource.name) {
				if (cachedNameValuesRef.current.has(value)) {
					error = cachedNameValuesRef.current.get(value);
				}
				else {
					error = validateUniqueName({groupId, value});

					cachedNameValuesRef.current.set(value, error);
				}
			}

			return toPromise(error);
		},
		[dataSource.name, groupId]
	);

	return (
		<div className="mb-5">
			<ClayLabel
				className="mb-2"
				displayType={
					displayType as
						| 'secondary'
						| 'info'
						| 'warning'
						| 'danger'
						| 'success'
						| 'unstyled'
						| undefined
				}
			>
				{label}
			</ClayLabel>

			<InputWithEditToggle
				editable={!!editable}
				inputWidth={30}
				name="dataSourceName"
				onSubmit={(name) => toPromise(handleUpdateName(name))}
				required
				validate={sequence([
					validateRequired,
					validateMaxLength(75),
					handleValidate,
				])}
				value={dataSource.name || ''}
			/>

			{description && (
				<div className="mt-1">
					<Text color="secondary" size={2}>
						{description}
					</Text>
				</div>
			)}
		</div>
	);
};

export {DataSourceEditableTitle};
