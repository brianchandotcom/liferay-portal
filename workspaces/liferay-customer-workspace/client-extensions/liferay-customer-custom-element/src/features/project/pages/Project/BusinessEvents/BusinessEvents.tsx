/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';

import './BusinessEvents.css';

import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import {useCallback, useMemo, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {ButtonDropDown} from '~/components';
import Table, {IRow} from '~/components/Table';
import TableHeader from '~/components/Table/TableHeader';
import {useAppContext} from '~/features/project/context';
import useAccountKey from '~/hooks/useAccountKey';
import {Liferay} from '~/services/liferay';
import {getFormattedDate} from '~/utils/getFormattedDate';
import {getFormattedTime} from '~/utils/getFormattedTime';
import getKebabCase from '~/utils/getKebabCase';
import {IBusinessEvent} from '~/utils/types';

import ManageEventModal from './components/ManageEventModal';
import useFilters from './hooks/useFilters';
import useGetBusinessEvents from './hooks/useGetBusinessEvents';
import useHasAllEventsPermissions from './hooks/useHasAllEventsPermissions';
import parseAssociatedTickets from './utils/parseAssociatedTickets';
import useIsSaasOnly from './utils/useIsSaasOnly';

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
		columnKey: 'plannedEventDate',
		label: i18n.translate('planned-event-date'),
	},
	{
		columnKey: 'actions',
		label: '',
	},
];

const BusinessEvents = () => {
	const [{subscriptionGroups}] = useAppContext();

	const accountKey = useAccountKey();

	const {filters, handleFilterChange, handleSearchChange} = useFilters();

	const {businessEvents, fetchBusinessEvents, loading} =
		useGetBusinessEvents();
	const [modalType, setModalType] = useState('');

	const {hasAllEventsPermissions} = useHasAllEventsPermissions();

	const {isSaasOnly} = useIsSaasOnly(subscriptionGroups);

	const navigate = useNavigate();

	const [selectedBusinessEvent, setSelectedBusinessEvent] = useState<
		IBusinessEvent | undefined
	>(undefined);

	const {observer, onOpenChange, open} = useModal({
		onClose: () => {
			setSelectedBusinessEvent(undefined);
		},
	});

	const handleOnCancel = useCallback(() => {
		fetchBusinessEvents();

		Liferay.Util.openToast({
			message: i18n.translate('business-event-canceled-successfully'),
			type: 'success',
		});
	}, [fetchBusinessEvents]);

	const handleOnCompleted = useCallback(() => {
		fetchBusinessEvents();

		Liferay.Util.openToast({
			message: i18n.translate(
				'business-event-actual-event-date-recorded-successfully'
			),
			type: 'success',
		});
	}, [fetchBusinessEvents]);

	const filteredBusinessEvents = useMemo(() => {
		const normalize = (value?: string) =>
			(value || '').toLowerCase().replace(/[\s_-]/g, '');

		const oneYearAgo = new Date();

		oneYearAgo.setFullYear(oneYearAgo.getFullYear() - 1);

		return businessEvents.filter((event) => {
			const statusKey = event.eventStatus?.key;
			const normalizedStatus = normalize(statusKey);

			if (normalizedStatus === 'canceled') {
				const modified = event.plannedEventDate
					? new Date(event.plannedEventDate)
					: null;

				if (modified && modified < oneYearAgo) {
					return false;
				}
			}

			if (normalizedStatus === 'completed') {
				const goLive = event.actualEventDate
					? new Date(event.actualEventDate)
					: null;

				if (goLive && goLive < oneYearAgo) {
					return false;
				}
			}

			if (filters.selectedFilters?.length) {
				const statusFilter = filters.selectedFilters.find(
					(f) => f.key === 'eventStatus'
				);

				if (statusFilter?.values?.length) {
					const selectedKeys = statusFilter.values.map(
						(v: {key: string}) => normalize(v.key)
					);

					if (
						normalizedStatus &&
						!selectedKeys.includes(normalizedStatus)
					) {
						return false;
					}
				}

				const typeFilter = filters.selectedFilters.find(
					(f) => f.key === 'eventType'
				);

				if (typeFilter?.values?.length) {
					const selectedKeys = typeFilter.values.map(
						(v: {key: string}) => normalize(v.key)
					);

					const normalizedType = normalize(event.eventType?.key);

					if (
						normalizedType &&
						!selectedKeys.includes(normalizedType)
					) {
						return false;
					}
				}
			}

			if (filters.searchTerm?.trim()) {
				const search = filters.searchTerm.toLowerCase();

				if (!event.name?.toLowerCase().includes(search)) {
					return false;
				}
			}

			return true;
		});
	}, [businessEvents, filters]);

	const rows = useMemo(() => {
		if (filteredBusinessEvents?.length > 0) {
			return filteredBusinessEvents.map((businessEvent) => {
				const associatedTicketsCount = parseAssociatedTickets(
					businessEvent.associatedTickets
				).length;

				const associatedTicketsString =
					associatedTicketsCount > 0
						? associatedTicketsCount > 1
							? `${associatedTicketsCount} Tickets`
							: `1 Ticket`
						: '';

				const isGoLiveType =
					businessEvent?.eventType?.key === 'Go-Live';

				const isOtherEventType =
					businessEvent?.eventType?.key === 'Other Event';

				const DetailsColumn = () => {
					if (isGoLiveType) {
						return (
							<div className="text-neutral-10">
								{businessEvent?.currentLiferayVersion?.name}
							</div>
						);
					}

					if (isOtherEventType) {
						return (
							<div className="be-details text-neutral-10">
								{businessEvent?.description}
							</div>
						);
					}

					return (
						<div className="align-items-center d-flex">
							{!isSaasOnly && (
								<>
									<div className="text-neutral-10">
										{
											businessEvent?.currentLiferayVersion
												?.name
										}
									</div>

									<ClayIcon
										className="mx-2 text-neutral-4"
										symbol="order-arrow-right"
									/>
								</>
							)}

							<div className="text-neutral-10">
								{businessEvent?.newLiferayVersion?.name}
							</div>
						</div>
					);
				};

				const userOptions = [
					{
						customOptionStyle: 'pr-5',
						label: i18n.translate('view-details'),
						onClick: () => {
							navigate(
								`/${accountKey}/business-events/${businessEvent.id}`
							);
						},
					},
				];

				if (
					hasAllEventsPermissions &&
					!['Canceled', 'Completed'].includes(
						businessEvent.eventStatus?.key!
					)
				) {
					userOptions.push(
						{
							customOptionStyle: 'pr-5',
							label: i18n.translate('edit-event'),
							onClick: () => {
								navigate(
									`/${accountKey}/business-events/${businessEvent.id}/edit`
								);
							},
						},
						{
							customOptionStyle: 'pr-5',
							label: i18n.translate('record-actual-event-date'),
							onClick: () => {
								setModalType('goLiveEvent');
								onOpenChange(true);
								setSelectedBusinessEvent(businessEvent);
							},
						},
						{
							customOptionStyle: 'be-cancel-event-option pr-5',
							label: i18n.translate('cancel-event'),
							onClick: () => {
								setModalType('cancelEvent');
								onOpenChange(true);
								setSelectedBusinessEvent(businessEvent);
							},
						}
					);
				}

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
								menuElementAttrs={{
									className: 'p-0',
								}}
							/>
						</div>
					),
					associatedTickets: (
						<div className="text-neutral-10">
							{associatedTicketsString}
						</div>
					),
					details: <DetailsColumn />,
					eventName: (
						<div>
							<div className="be-event-name font-weight-semi-bold text-neutral-10">
								<Link
									to={`/${accountKey}/business-events/${businessEvent.id}`}
								>
									{businessEvent?.name}
								</Link>
							</div>

							<div className="be-subtitle text-neutral-7">
								{i18n.translate(
									getKebabCase(
										businessEvent?.eventType?.key as string
									) as string
								)}
							</div>
						</div>
					),
					plannedEventDate: (
						<div>
							<div className="text-neutral-10">
								{getFormattedDate(
									businessEvent?.plannedEventDate,
									'day2DMonthSYearN',
									'UTC'
								)}
							</div>

							<div className="be-subtitle text-neutral-7">
								{getFormattedTime(
									businessEvent?.plannedEventDate,
									'UTC'
								)}
							</div>
						</div>
					),
					status: (
						<div className="align-items-center d-flex">
							<div
								className={`align-items-center font-weight-semi-bold be-status be-status-${businessEvent?.eventStatus?.key.toLowerCase()} px-2 py-1`}
							>
								{i18n.translate(
									getKebabCase(
										businessEvent?.eventStatus
											?.key as string
									) as string
								)}
							</div>
						</div>
					),
				};
			});
		}

		return [];
	}, [
		accountKey,
		filteredBusinessEvents,
		hasAllEventsPermissions,
		isSaasOnly,
		navigate,
		onOpenChange,
	]);

	return loading ? (
		<div className="mx-auto">
			<ClayLoadingIndicator size="sm" />
		</div>
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
					searchResultsCount={filteredBusinessEvents.length}
					searchTerm={filters.searchTerm || ''}
					selectedFilters={filters.selectedFilters || []}
				/>
			</div>

			<div>
				{filteredBusinessEvents.length ? (
					<>
						<Table
							columns={columns}
							rows={rows as unknown as IRow[]}
						/>

						{selectedBusinessEvent && open && (
							<ManageEventModal
								accountExternalReferenceCode={accountKey || ''}
								businessEvent={selectedBusinessEvent}
								closeFunction={onOpenChange}
								modalType={modalType}
								observer={observer}
								onCancel={handleOnCancel}
								onCompleted={handleOnCompleted}
							/>
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
