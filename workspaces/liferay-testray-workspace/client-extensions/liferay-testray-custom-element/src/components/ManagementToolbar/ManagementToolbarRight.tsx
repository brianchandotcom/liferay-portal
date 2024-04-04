/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {ReactNode, useCallback, useContext, useMemo} from 'react';
import {useParams} from 'react-router-dom';
import useSWR from 'swr';
import {ListViewContext, ListViewTypes} from '~/context/ListViewContext';
import fetcher from '~/services/fetcher';
import {Liferay} from '~/services/liferay';
import {safeJSONParse} from '~/util';

import i18n from '../../i18n';
import {FilterSchema} from '../../schema/filter';
import {Column} from '../Table';
import ManagementToolbarColumns from './ManagementToolbarColumns';
import ManagementToolbarFilter from './ManagementToolbarFilter';

export type IItem = {
	active?: boolean;
	checked?: boolean;
	disabled?: boolean;
	href?: string;
	items?: IItem[];
	label?: string;
	name?: string;
	onChange?: Function;
	onClick?: (event: React.MouseEvent<HTMLElement, MouseEvent>) => void;
	symbolLeft?: string;
	symbolRight?: string;
	type?:
		| 'checkbox'
		| 'contextual'
		| 'divider'
		| 'group'
		| 'item'
		| 'radio'
		| 'radiogroup';
	value?: string;
};

type ManagementToolbarRightProps = {
	actions?: any;
	addButton?: () => void;
	applyFilters?: boolean;
	buttons?: ReactNode | ((actions: any) => ReactNode);
	columns?: Column[];
	customFilterFields?: {[key: string]: string};
	disabled: boolean;
	display?: {
		columns?: boolean;
	};
	filterSchema?: FilterSchema;
};

const ManagementToolbarRight: React.FC<ManagementToolbarRightProps> = ({
	actions,
	addButton,
	applyFilters = true,
	buttons,
	columns,
	customFilterFields,
	display = {columns: true},
	filterSchema,
}) => {
	const [{filters, pin}, dispatch] = useContext(ListViewContext);
	const params = useParams();

	const hasAppliedFilters = !!filters.entries.length;

	const onPin = useCallback(() => {
		if (hasAppliedFilters) {
			dispatch({
				payload: filterSchema?.name as string,
				type: ListViewTypes.SET_PIN,
			});

			return Liferay.Util.openToast({
				message: i18n.translate(
					pin
						? 'filters-unpinned-successfully'
						: 'filters-pinned-successfully'
				),
				type: 'success',
			});
		}

		Liferay.Util.openToast({
			message: i18n.translate(
				'you-must-select-one-or-more-filters-before-pinning'
			),
			type: 'danger',
		});
	}, [dispatch, filterSchema?.name, hasAppliedFilters, pin]);

	const paramsMemoized = useMemo(() => {
		const testrayModalParams = document.getElementById(
			'testray-modal-params'
		);

		if (testrayModalParams) {
			return testrayModalParams.textContent!;
		}

		return JSON.stringify({...params, ...customFilterFields});
	}, [params, customFilterFields]);

	const fieldsMemoized = useMemo(() => filterSchema?.fields, [filterSchema]);

	const {data: fieldOptions = {}, isLoading} = useSWR(
		filterSchema?.fields?.length ? `/filter-${filterSchema?.name}` : null,
		async () => {
			const parameters = safeJSONParse(paramsMemoized);

			const fieldsWithResource = fieldsMemoized?.filter(
				({resource}) => resource
			);

			const _fieldOptions: any = {};

			if (fieldsWithResource) {
				await Promise.all(
					fieldsWithResource.map((field) =>
						fetcher(
							(typeof field.resource === 'function'
								? field.resource(parameters)
								: field.resource) as string
						)
					)
				).then((results) =>
					results.forEach((result, index) => {
						const field = fieldsWithResource[index];

						if (field.transformData) {
							const parsedValue = field.transformData(result);

							_fieldOptions[field.name] = parsedValue;
						}
					})
				);
			}

			return _fieldOptions;
		}
	);

	return (
		<ClayManagementToolbar.ItemList>
			{filterSchema?.fields?.length && (
				<>
					<ClayManagementToolbar.Item>
						<ClayButtonWithIcon
							aria-label={i18n.translate('add-pin')}
							className="nav-btn nav-btn-monospaced"
							displayType="unstyled"
							onClick={onPin}
							symbol={i18n.translate(pin ? 'unpin' : 'pin')}
							title={i18n.translate(pin ? 'unpin' : 'pin')}
						/>
					</ClayManagementToolbar.Item>

					<ManagementToolbarFilter
						applyFilters={applyFilters}
						fieldOptions={fieldOptions}
						filterSchema={filterSchema}
						isLoading={isLoading}
					/>
				</>
			)}

			{display.columns && <ManagementToolbarColumns columns={columns} />}

			{typeof buttons === 'function' ? buttons(actions) : buttons}

			{actions?.create && addButton && (
				<ClayManagementToolbar.Item
					className="ml-2"
					onClick={addButton}
				>
					<ClayButtonWithIcon
						aria-labelledby="plus"
						className="nav-btn nav-btn-monospaced"
						symbol="plus"
					/>
				</ClayManagementToolbar.Item>
			)}
		</ClayManagementToolbar.ItemList>
	);
};

export default ManagementToolbarRight;
