/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';

import './BusinessEvents.css';

import {ButtonWithIcon} from '@clayui/core';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {ButtonDropDown} from '~/components';
import {IFilterOption} from '~/components/Filter/Filter';
import Table, {IRow} from '~/components/Table';
import TableHeader from '~/components/Table/TableHeader';
import {useCustomerPortal} from '~/features/project/context';
import {getFormattedDate} from '~/features/project/utils/getFormattedDate';
import {getBusinessEvents} from '~/services/liferay/api';
import {getFormattedTime} from '~/utils/getFormattedTime';
import {IBusinessEvent} from '~/utils/types';

import useHasAllEventsPermissions from './hooks/useHasAllEventsPermissions';
import {INITIAL_FILTER} from './utils/constants/initialFilter';

export interface IState {
	availableFilters?: IFilterOption[];
	searchTerm?: string;
	selectedFilters?: IFilterOption[];
}

const columns = [
	{
		columnKey: 'eventName',
		label: i18n.translate('event-name'),
		subLabel: i18n.translate('type'),
	},
	{
		columnKey: 'status',
		label: i18n.translate('status'),
	},
	{
		columnKey: 'details',
		label: i18n.translate('details'),
	},
	{
		columnKey: 'associatedTickets',
		label: i18n.translate('associated-tickets'),
	},
	{
		columnKey: 'targetGoLiveDate',
		label: i18n.translate('target-go-live-date'),
	},
	{
		columnKey: 'actions',
		label: '',
	},
];

const BusinessEvents = () => {
	const [{project}] = useCustomerPortal();

	const [filters, setFilters] = useState<IState>({
		availableFilters: INITIAL_FILTER,
		searchTerm: '',
		selectedFilters: [],
	});

	const [businessEvents, setBusinessEvents] = useState<IBusinessEvent[]>([]);

	const hasAllEventsPermissions = useHasAllEventsPermissions();

	const navigate = useNavigate();

	const handleEditEvent = useCallback(
		(businessEventId: number | undefined) => {
			if (businessEventId) {
				navigate(
					`/${project?.accountKey}/business-events/${businessEventId}`
				);
			}
		},
		[navigate, project?.accountKey]
	);

	const generateFilterQuery = (filters: IState) => {
		const queryParams = Object.entries(filters)
			.map(([key, {value}]) => {
				if (Array.isArray(value) && !!value.length) {
					return `(${value
						.map((item) => `${key} eq '${item}'`)
						.join(' or ')})`;
				}

				return '';
			})
			.filter(Boolean);

		if (filters.searchTerm?.trim()) {
			queryParams.push(`(contains(name, '${filters.searchTerm}'))`);
		}

		return queryParams.length ? `filter=${queryParams.join(' and ')}` : '';
	};

	const filterQuery = generateFilterQuery(filters);

	const handleFilterChange = (newFilterOptions: IFilterOption[]) => {
		setFilters((prevFilters) => {
			let updatedFilters: IFilterOption[] = prevFilters.selectedFilters
				? [...prevFilters.selectedFilters]
				: [];

			if (newFilterOptions && !!newFilterOptions.length) {
				newFilterOptions.forEach((newOption) => {
					updatedFilters = updatedFilters.filter(
						(filter) => filter.name !== newOption.name
					);
					updatedFilters.push(newOption);
				});
			}
			else {
				updatedFilters = updatedFilters.filter(
					(filter) =>
						filter.name !==
						(prevFilters.selectedFilters?.length &&
							prevFilters.selectedFilters[0].name)
				);
			}

			return {
				...prevFilters,
				selectedFilters: updatedFilters,
			};
		});
	};

	const handleSearchChange = (searchTerm: string) => {
		setFilters((prevFilters) => ({
			...prevFilters,
			searchTerm,
		}));
	};

	useEffect(() => {
		const fetchBusinessEvents = async () => {
			try {
				const businessEventsResponse =
					await getBusinessEvents(filterQuery);

				setBusinessEvents(businessEventsResponse.items);
			}
			catch (error) {
				console.error('Error', error);
			}
		};

		fetchBusinessEvents();
	}, [filterQuery]);

	const rows = useMemo(() => {
		if (businessEvents?.length > 0) {
			return businessEvents.map((businessEvent) => {
				const userOptions = [
					{
						customOptionStyle: 'pr-5',
						label: i18n.translate('view-details'),
						onClick: () => {
							handleEditEvent(businessEvent.id);
						},
					},
				];

				if (hasAllEventsPermissions) {
					userOptions.push(
						{
							customOptionStyle: 'pr-5',
							label: i18n.translate('edit-event'),
							onClick: () => {
								handleEditEvent(businessEvent.id);
							},
						},
						{
							customOptionStyle: 'pr-5',
							label: i18n.translate('record-actual-go-live'),
							onClick: () => {},
						},
						{
							customOptionStyle: 'pr-5 be-cancel-event-option',
							label: i18n.translate('cancel-event'),
							onClick: () => {},
						}
					);
				}

				return {
					actions: (
						<div className="d-flex justify-content-center">
							<ButtonDropDown
								customDropDownButton={
									<ButtonWithIcon
										aria-label={i18n.translate(
											'manage-user-options'
										)}
										borderless
										className="text-neutral-5"
										onPointerEnterCapture={undefined}
										onPointerLeaveCapture={undefined}
										placeholder={undefined}
										symbol="ellipsis-v"
									/>
								}
								items={userOptions}
								label="Options"
							/>
						</div>
					),
					associatedTickets: (
						<div className="text-neutral-10">
							{businessEvent?.associatedTickets}
						</div>
					),
					details: (
						<div className="text-neutral-10">
							{businessEvent?.description}
						</div>
					),
					eventName: (
						<div>
							<div className="font-weight-semi-bold text-neutral-10">
								{businessEvent?.name}
							</div>

							<div className="be-subtitle text-neutral-7">
								{businessEvent?.eventType?.name}
							</div>
						</div>
					),
					status: (
						<div className="align-items-center d-flex">
							<div
								className={`align-items-center font-weight-semi-bold be-status be-status-${businessEvent?.eventStatus?.key} px-2 py-1`}
							>
								{businessEvent?.eventStatus?.name}
							</div>
						</div>
					),
					targetGoLiveDate: (
						<div>
							<div className="text-neutral-10">
								{getFormattedDate(
									businessEvent?.targetGoLiveDateTime,
									'day2DMonthSYearN'
								)}
							</div>

							<div className="be-subtitle text-neutral-7">
								{getFormattedTime(
									businessEvent?.targetGoLiveDateTime
								)}
							</div>
						</div>
					),
				};
			});
		}

		return [];
	}, [businessEvents, hasAllEventsPermissions, handleEditEvent]);

	return (
		<div className="py-4">
			<div>
				<h1 className="font-weight-bold text-neutral-10">
					{i18n.translate('business-events')}
				</h1>

				<h6 className="font-weight-normal text-neutral-7">
					{i18n.translate(
						'this-table-allows-you-to-create-manage-and-track-your-business-events-please-note-that-business-events-closed-for-more-than-a-year-will-not-be-displayed-here'
					)}
				</h6>
			</div>

			<div className="mb-1">
				<TableHeader
					availableFilters={filters.availableFilters || []}
					hasCreatePermissions={hasAllEventsPermissions}
					onFilterChange={handleFilterChange}
					onSearchChange={handleSearchChange}
					searchResultsCount={businessEvents.length}
					searchTerm={filters.searchTerm || ''}
					selectedFilters={filters.selectedFilters || []}
				/>
			</div>

			<div>
				<Table columns={columns} rows={rows as unknown as IRow[]} />
			</div>
		</div>
	);
};

export default BusinessEvents;
