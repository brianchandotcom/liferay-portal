/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Nav} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import NavigationBar from '@clayui/navigation-bar';
import {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {ButtonDropDown} from '~/components';
import {getBusinessEventById} from '~/services/liferay/api';
import i18n from '~/utils/I18n';
import {IBusinessEvent} from '~/utils/types';

const BusinessEventsItemActivityHistory = () => {
	const {accountKey, id} = useParams<{accountKey: string; id: string}>();
	const [businessEvent, setBusinessEvent] = useState<IBusinessEvent | null>(
		null
	);
	const [loading, setLoading] = useState(true);

	const navigate = useNavigate();

	useEffect(() => {
		if (id) {
			const fetchEvent = async () => {
				try {
					setLoading(true);

					const eventData = await getBusinessEventById(id);

					setBusinessEvent(eventData);
				}
				catch (error) {
					console.error('Error', error);

					setBusinessEvent(null);
				}
				finally {
					setLoading(false);
				}
			};

			fetchEvent();
		}
	}, [id]);

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
			onClick: () => {},
		},
		{
			customOptionStyle: 'pr-5',
			icon: <ClayIcon symbol="trash" />,
			label: i18n.translate('cancel-event'),
			onClick: () => {},
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
					<div>
						<ButtonDropDown items={userOptions} label="Actions" />
					</div>
				</div>
			</div>

			<div className="mb-4">
				<NavigationBar
					triggerLabel={i18n.translate('activity-history')}
				>
					<Nav.Item>
						<Nav.Link
							active={false}
							aria-label={`Switch to ${i18n.translate('event-details')}`}
							className="be-nav-link text-neutral-10"
							onClick={() =>
								navigate(`/${accountKey}/business-events/${id}`)
							}
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
		</div>
	);
};

export default BusinessEventsItemActivityHistory;
