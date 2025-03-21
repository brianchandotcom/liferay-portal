/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';

import './BusinessEvents.css';

import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {ButtonDropDown} from '~/components';
import {IFilterOption} from '~/components/Filter/Filter';
import Table, {IRow} from '~/components/Table';
import TableHeader from '~/components/Table/TableHeader';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {useCustomerPortal} from '~/features/project/context';
import {PRODUCT_TYPES} from '~/features/project/utils/constants';
import {Liferay} from '~/services/liferay';
import {getBusinessEvents} from '~/services/liferay/api';
import {getFormattedDate} from '~/utils/getFormattedDate';
import {getFormattedTime} from '~/utils/getFormattedTime';
import {IBusinessEvent} from '~/utils/types';

import CancelEventForm from './components/CancelEventForm';
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
	const [{project, subscriptionGroups}] = useCustomerPortal();

	const [filters, setFilters] = useState<IState>({
		availableFilters: INITIAL_FILTER,
		searchTerm: '',
		selectedFilters: [],
	});

	const [businessEvents, setBusinessEvents] = useState<IBusinessEvent[]>([]);
	const [loading, setLoading] = useState(true);

	const {client} = useAppPropertiesContext();

	const [selectedBusinessEvent, setSelectedBusinessEvent] = useState<
		IBusinessEvent | undefined
	>(undefined);

	const {hasAllEventsPermissions} = useHasAllEventsPermissions();

	const navigate = useNavigate();

	const {observer, onOpenChange, open} = useModal({
		onClose: () => {
			setSelectedBusinessEvent(undefined);
		},
	});

	const generateFilterQuery = useCallback(
		(filters: IState) => {
			const queryParams: string[] = [];

			if (project?.id) {
				queryParams.push(
					`r_accountEntryToBusinessEvents_accountEntryId eq '${project.id}'`
				);
			}

			if (
				filters.selectedFilters &&
				Boolean(filters.selectedFilters.length)
			) {
				filters.selectedFilters.forEach((filter) => {
					if (filter.values && Boolean(filter.values.length)) {
						const filterQuery = `(${filter.values
							.map(
								(value: {key: string; name: string}) =>
									`${filter.key} eq '${value.key}'`
							)
							.join(' or ')})`;
						queryParams.push(filterQuery);
					}
				});
			}

			if (filters.searchTerm?.trim()) {
				queryParams.push(`(contains(name, '${filters.searchTerm}'))`);
			}

			const oneYearAgo = new Date();

			oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);

			queryParams.push(
				`(eventStatus ne 'canceled' or (eventStatus eq 'canceled' and dateModified ge ${oneYearAgo.toISOString()}))`
			);

			return queryParams.length
				? `filter=${queryParams.join(' and ')}`
				: '';
		},
		[project?.id]
	);

	const filterQuery = useMemo(
		() => generateFilterQuery(filters),
		[filters, generateFilterQuery]
	);

	const fetchBusinessEvents = useCallback(async () => {
		setLoading(true);

		try {
			const businessEventsResponse = await getBusinessEvents(filterQuery);

			setBusinessEvents(businessEventsResponse.items);
		}
		catch (error) {
			console.error('Error fetching business events:', error);
		}
		finally {
			setLoading(false);
		}
	}, [filterQuery]);

	const handleEventCanceled = useCallback(() => {
		fetchBusinessEvents();

		Liferay.Util.openToast({
			message: i18n.translate('business-event-canceled-successfully'),
			type: 'success',
		});
	}, [fetchBusinessEvents]);

	const handleFilterChange = useCallback(
		(newFilterOptions: IFilterOption[]) => {
			setFilters((prevFilters) => ({
				...prevFilters,
				selectedFilters: newFilterOptions,
			}));
		},
		[]
	);

	const handleSearchChange = useCallback((searchTerm: string) => {
		setFilters((prevFilters) => ({
			...prevFilters,
			searchTerm,
		}));
	}, []);

	useEffect(() => {
		if (!project?.id) {
			setLoading(true);

			return;
		}

		fetchBusinessEvents();
	}, [fetchBusinessEvents, filterQuery, project?.id]);

	const isSaasOnly = useMemo(
		() =>
			subscriptionGroups?.length === 1 &&
			subscriptionGroups[0].name === PRODUCT_TYPES.liferayExperienceCloud,
		[subscriptionGroups]
	);

	const rows = useMemo(() => {
		if (businessEvents?.length > 0) {
			return businessEvents.map((businessEvent) => {
				const userOptions = [
					{
						customOptionStyle: 'pr-5',
						label: i18n.translate('view-details'),
						onClick: () => {
							navigate(
								`/${project?.accountKey}/business-events/${businessEvent.id}`
							);
						},
					},
				];

				if (
					hasAllEventsPermissions &&
					!['canceled', 'completed'].includes(
						businessEvent.eventStatus?.key!
					)
				) {
					userOptions.push(
						{
							customOptionStyle: 'pr-5',
							label: i18n.translate('edit-event'),
							onClick: () => {
								navigate(
									`/${project?.accountKey}/business-events/${businessEvent.id}/edit`
								);
							},
						},
						{
							customOptionStyle: 'pr-5',
							label: i18n.translate('record-actual-go-live'),
							onClick: () => {},
						},
						{
							customOptionStyle: 'be-cancel-event-option pr-5',
							label: i18n.translate('cancel-event'),
							onClick: () => {
								onOpenChange(true);
								setSelectedBusinessEvent(businessEvent);
							},
						}
					);
				}

				const isOtherEventType =
					businessEvent?.eventType?.key === 'otherEvent';
				const isGoLiveType = businessEvent?.eventType?.key === 'goLive';

				const DetailsColumn = () => {
					if (isOtherEventType) {
						return (
							<div className="text-neutral-10">
								{businessEvent?.description}
							</div>
						);
					}

					if (isGoLiveType && !isSaasOnly) {
						return (
							<div className="text-neutral-10">
								{businessEvent?.currentLiferayVersion?.name}
							</div>
						);
					}

					if (!isGoLiveType && !isOtherEventType) {
						if (isSaasOnly) {
							return (
								<div className="align-items-center d-flex">
									<div className="text-neutral-10">
										{businessEvent?.newLiferayVersion?.name}
									</div>
								</div>
							);
						}

						return (
							<div className="align-items-center d-flex">
								<div className="text-neutral-10">
									{businessEvent?.currentLiferayVersion?.name}
								</div>
								<ClayIcon
									className="mx-2 text-neutral-4"
									symbol="order-arrow-right"
								/>
								<div className="text-neutral-10">
									{businessEvent?.newLiferayVersion?.name}
								</div>
							</div>
						);
					}

					return null;
				};

				return {
					actions: (
						<div className="d-flex justify-content-center">
							<ButtonDropDown
								customDropDownButton={
									<Button
										aria-label={i18n.translate(
											'manage-user-options'
										)}
										borderless
										className="text-neutral-5"
									>
										<span>
											<ClayIcon symbol="ellipsis-v" />
										</span>
									</Button>
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
					details: <DetailsColumn />,
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
									'day2DMonthSYearN',
									'GMT'
								)}
							</div>

							<div className="be-subtitle text-neutral-7">
								{getFormattedTime(
									businessEvent?.targetGoLiveDateTime,
									'GMT'
								)}
							</div>
						</div>
					),
				};
			});
		}

		return [];
	}, [
		businessEvents,
		hasAllEventsPermissions,
		isSaasOnly,
		navigate,
		onOpenChange,
		project?.accountKey,
	]);

	return loading ? (
		<div className="py-4">{i18n.translate('loading')}</div>
	) : (
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
				{businessEvents.length ? (
					<>
						<Table
							columns={columns}
							rows={rows as unknown as IRow[]}
						/>

						{selectedBusinessEvent && open && (
							<ClayModal
								center
								disableAutoClose
								observer={observer}
							>
								<CancelEventForm
									businessEvent={selectedBusinessEvent}
									client={client}
									closeFunction={onOpenChange}
									onCancel={handleEventCanceled}
								/>
							</ClayModal>
						)}
					</>
				) : (
					<div className="p-3">
						{i18n.translate('no-business-events-were-found')}
					</div>
				)}
			</div>
		</div>
	);
};

export default BusinessEvents;
