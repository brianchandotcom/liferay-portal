/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Nav} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import NavigationBar from '@clayui/navigation-bar';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {ButtonDropDown} from '~/components';
import Table, {IRow} from '~/components/Table';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {useCustomerPortal} from '~/features/project/context';
import {Liferay} from '~/services/liferay';
import {
	getBusinessEventById,
	getBusinessEventVersions,
} from '~/services/liferay/api';
import i18n from '~/utils/I18n';
import {getFormattedDate} from '~/utils/getFormattedDate';
import {getFormattedTime} from '~/utils/getFormattedTime';
import {
	IBusinessEvent,
	IBusinessEventVersion,
	IUserAccount,
} from '~/utils/types';

import './BusinessEventsItemActivityHistory.css';

import {getUserAccount} from '~/services/liferay/graphql/queries';

import Avatar from '../../../../TeamMembers/components/TeamMembersTable/components/columns/NameColumn/components/Avatar';
import ManageEventModal from '../../../components/ManageEventModal';
import useHasAllEventsPermissions from '../../../hooks/useHasAllEventsPermissions';

const BusinessEventsItemActivityHistory = () => {
	const {accountKey, id} = useParams<{accountKey: string; id: string}>();

	const [businessEvent, setBusinessEvent] = useState<
		IBusinessEvent | undefined
	>(undefined);
	const [businessEventVersion, setBusinessEventVersion] = useState<
		IBusinessEventVersion[]
	>([]);

	const [{project}] = useCustomerPortal();
	const {client, gravatarAPI} = useAppPropertiesContext();

	const {hasAllEventsPermissions} = useHasAllEventsPermissions();

	const [modalType, setModalType] = useState('');
	const [loading, setLoading] = useState(true);
	const {observer, onOpenChange, open} = useModal();

	const navigate = useNavigate();

	const fetchBusinessEvent = useCallback(async () => {
		try {
			setLoading(true);

			const eventData = await getBusinessEventById(id!);

			setBusinessEvent(eventData);
		}
		catch (error) {
			console.error('Error', error);

			setBusinessEvent(undefined);
		}
		finally {
			setLoading(false);
		}
	}, [id]);

	const handleOnCancel = useCallback(() => {
		fetchBusinessEvent();

		Liferay.Util.openToast({
			message: i18n.translate('business-event-canceled-successfully'),
			type: 'success',
		});
	}, [fetchBusinessEvent]);

	const handleOnCompleted = useCallback(() => {
		fetchBusinessEvent();

		Liferay.Util.openToast({
			message: i18n.translate(
				'business-event-actual-go-live-date-recorded-successfully'
			),
			type: 'success',
		});
	}, [fetchBusinessEvent]);

	useEffect(() => {
		if (id) {
			fetchBusinessEvent();
		}
	}, [fetchBusinessEvent, id]);

	const generateFilterQuery = useCallback(() => {
		const queryParams: string[] = [];

		if (id) {
			queryParams.push(
				`r_businessEventToBusinessEventVersions_c_businessEventId eq '${id}'`
			);
		}

		const filterQuery = queryParams.length
			? `filter=${queryParams.join(' and ')}`
			: '';
		const sortQuery = 'sort=dateModified:desc';

		if (filterQuery) {
			return `${filterQuery}&${sortQuery}`;
		}

		return sortQuery;
	}, [id]);

	const filterQuery = generateFilterQuery();

	const fetchBusinessEventsVersions = useCallback(async () => {
		setLoading(true);

		try {
			const businessEventsVersionsResponse =
				await getBusinessEventVersions(filterQuery);

			setBusinessEventVersion(businessEventsVersionsResponse.items);
		}
		catch (error) {
			console.error('Error fetching business events versions:', error);
		}
		finally {
			setLoading(false);
		}
	}, [filterQuery]);

	useEffect(() => {
		if (!project?.id) {
			setLoading(true);

			return;
		}

		fetchBusinessEventsVersions();
	}, [fetchBusinessEventsVersions, filterQuery, project?.id]);

	const [userAccount, setUserAccount] = useState<IUserAccount | null>(null);

	useEffect(() => {
		const getUser = async () => {
			try {
				const {data} = await client.query({
					query: getUserAccount,
					variables: {
						id: Liferay.ThemeDisplay.getUserId(),
					},
				});

				setUserAccount(data.userAccount);
			}
			catch (error) {
				console.error('Error fetching user account:', error);
			}
		};

		getUser();
	}, [client]);

	const rows = useMemo(() => {
		if (businessEventVersion?.length > 0) {
			return businessEventVersion.map((businessEventVersion) => {
				return {
					change: (
						<div className="font-weight-semi-bold text-neutral-10">
							{businessEventVersion?.change?.name}
						</div>
					),

					comment: (
						<div className="text-neutral-10">
							{businessEventVersion?.comment}
						</div>
					),
					date: (
						<div>
							<div className="text-neutral-10">
								{getFormattedDate(
									businessEventVersion?.dateModified,
									'day2DMonthSYearN',
									'GMT'
								)}
							</div>

							<div className="be-subtitle text-neutral-7">
								{getFormattedTime(
									businessEventVersion?.dateModified,
									'GMT'
								)}
							</div>
						</div>
					),
					user: (
						<div className="align-items-center d-flex">
							<Avatar
								emailAddress={userAccount?.emailAddress}
								gravatarAPI={gravatarAPI}
								userName={businessEventVersion?.creator?.name}
							/>

							<div className="font-weight-semi-bold m-0 ml-2 mr-1 text-neutral-10 text-truncate">
								{businessEventVersion?.creator?.name}
							</div>
						</div>
					),
				};
			});
		}

		return [];
	}, [businessEventVersion, gravatarAPI, userAccount?.emailAddress]);

	if (loading) {
		return (
			<div className="mx-auto">
				<ClayLoadingIndicator size="sm" />
			</div>
		);
	}

	if (!businessEvent) {
		return <div>{i18n.translate('no-data-found')}</div>;
	}

	const userOptions = [
		{
			customOptionStyle: 'pr-5',
			icon: <ClayIcon symbol="pencil" />,
			label: i18n.translate('edit-event-details'),
			onClick: () => {
				navigate(`/${accountKey}/business-events/${id}/edit`);
			},
		},
		{
			customOptionStyle: 'pr-5',
			icon: <ClayIcon symbol="check-circle" />,
			label: i18n.translate('record-actual-go-live'),
			onClick: () => {
				setModalType('goLiveEvent');
				onOpenChange(true);
			},
		},
		{
			customOptionStyle: 'cancel-event-option pr-5',
			icon: <ClayIcon symbol="trash" />,
			label: i18n.translate('cancel-event'),
			onClick: () => {
				setModalType('cancelEvent');
				onOpenChange(true);
			},
		},
	];

	const columns = [
		{
			columnKey: 'change',
			label: i18n.translate('change'),
		},
		{
			columnKey: 'user',
			label: i18n.translate('user'),
		},
		{
			columnKey: 'comment',
			label: i18n.translate('comment'),
		},
		{
			columnKey: 'date',
			label: i18n.translate('date'),
		},
	];

	return (
		<div>
			<div className="be-breadcrumbs font-weight-semi-bold mb-4">
				<span className="mx-2">
					<Link to={`/${accountKey}/business-events/`}>
						<ClayIcon className="mr-1" symbol="order-arrow-left" />

						{i18n.translate('back-to-business-events')}
					</Link>
				</span>
			</div>

			<div>
				<div
					className={`align-items-center font-weight-semi-bold be-status be-status-${businessEvent?.eventStatus?.key} mb-1 d-inline px-2 py-1`}
				>
					{businessEvent?.eventStatus?.name}
				</div>

				<div className="align-items-center d-flex justify-content-between mb-4 mt-2">
					<div className="font-weight-bold text-neutral-10">
						<h3>{businessEvent.name}</h3>
					</div>

					{hasAllEventsPermissions &&
						!['canceled', 'completed'].includes(
							businessEvent.eventStatus?.key!
						) && (
							<div>
								<ButtonDropDown
									items={userOptions}
									label="Actions"
									menuElementAttrs={{
										className: 'p-0',
									}}
								/>
							</div>
						)}
				</div>
			</div>

			<div className="mb-4">
				<NavigationBar
					fluidSize={false}
					triggerLabel={i18n.translate('activity-history')}
				>
					<Nav.Item
						onClick={() =>
							navigate(`/${accountKey}/business-events/${id}`)
						}
					>
						<Nav.Link
							active={false}
							aria-label={`Switch to ${i18n.translate('event-details')}`}
							className="be-nav-link text-neutral-10"
						>
							{i18n.translate('event-details')}
						</Nav.Link>
					</Nav.Item>
					<Nav.Item>
						<Nav.Link
							active={true}
							aria-label={`Switch to ${i18n.translate('activity-history')}`}
							className="be-nav-link text-neutral-10"
						>
							{i18n.translate('activity-history')}
						</Nav.Link>
					</Nav.Item>
				</NavigationBar>
			</div>

			<div className="mt-4"></div>

			{businessEvent && open && (
				<ManageEventModal
					accountExternalReferenceCode={accountKey || ''}
					businessEvent={businessEvent}
					client={client}
					closeFunction={onOpenChange}
					modalType={modalType}
					observer={observer}
					onCancel={handleOnCancel}
					onCompleted={handleOnCompleted}
				/>
			)}

			<div className="">
				{businessEventVersion?.length ? (
					<div className="versions-table">
						<Table
							columns={columns}
							rows={rows as unknown as IRow[]}
						/>
					</div>
				) : (
					<div className="p-3">
						{i18n.translate('no-history-of-activity-was-found')}
					</div>
				)}
			</div>
		</div>
	);
};

export default BusinessEventsItemActivityHistory;
