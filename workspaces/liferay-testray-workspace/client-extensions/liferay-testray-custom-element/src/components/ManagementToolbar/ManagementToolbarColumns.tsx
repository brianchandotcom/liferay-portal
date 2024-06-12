/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useContext, useRef, useState} from 'react';

import {ListViewContext, ListViewTypes} from '../../context/ListViewContext';
import i18n from '../../i18n';
import Form from '../Form';
import {Column} from '../Table';

type ManagementToolbarColumnsProps = {
	columns?: Column[];
};

type ColumnsState = {
	[key: string]: boolean;
};

const ManagementToolbarColumns: React.FC<ManagementToolbarColumnsProps> = ({
	columns,
}) => {
	const buttonRef = useRef<HTMLButtonElement | null>(null);

	const [isVisible, setIsVisible] = useState(false);

	const handleExpand = (
		event: React.MouseEvent<HTMLButtonElement, MouseEvent>
	) => {
		buttonRef.current = event.target as HTMLButtonElement;

		setIsVisible((isVisible) => !isVisible);
	};
	const [{columns: contextColumns, columnsFixed}, dispatch] =
		useContext(ListViewContext);

	const columnsNotFixed = columns?.filter(
		({key, value}) => !columnsFixed.includes(key) && value !== ''
	);

	const [selectedColumns, setSelectedColumns] = useState<ColumnsState>(() => {
		const newColumns: ColumnsState = {};

		columnsNotFixed?.forEach(({key}) => {
			newColumns[key] = contextColumns[key] ?? true;
		});

		return newColumns;
	});

	const disabled = Object.values(selectedColumns).every(
		(visible) => !visible
	);

	return (
		<>
			<ClayButton
				className="management-toolbar-buttons nav-link"
				displayType="unstyled"
				onClick={handleExpand}
			>
				<span>
					<ClayIcon
						className="inline-item inline-item-after inline-item-before"
						symbol="columns"
					/>
				</span>
			</ClayButton>

			<ClayDropDown.Menu
				active={isVisible}
				alignElementRef={buttonRef}
				alignmentPosition={3}
				className="dropdown-management-toolbar"
				closeOnClickOutside
				onActiveChange={() => setIsVisible((isVisible) => !isVisible)}
			>
				<div className="align-content-between d-flex flex-column">
					<ClayDropDown.Section className="dropdown-header">
						<p className="font-weight-bold my-2">
							{i18n.translate('columns')}
						</p>
					</ClayDropDown.Section>

					<div className="dropdown-columns-content">
						{columnsNotFixed?.map(
							(column, index) =>
								column.value !== '' && (
									<Form.Checkbox
										checked={selectedColumns[column.key]}
										key={index}
										label={column.value}
										onChange={(event) =>
											setSelectedColumns({
												...selectedColumns,
												[column.key]:
													event.target.checked,
											})
										}
										value={
											selectedColumns[
												column.key
											] as unknown as string
										}
									/>
								)
						)}
					</div>

					<ClayDropDown.Section className="dropdown-footer">
						<ClayButton
							className="mt-2"
							disabled={disabled}
							onClick={() => {
								dispatch({
									payload: {
										columns: {
											...selectedColumns,
										},
									},
									type: ListViewTypes.SET_COLUMNS,
								});

								setIsVisible(false);
							}}
						>
							{i18n.translate('apply')}
						</ClayButton>
					</ClayDropDown.Section>
				</div>
			</ClayDropDown.Menu>
		</>
	);
};

export default ManagementToolbarColumns;
