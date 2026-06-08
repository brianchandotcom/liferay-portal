/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import React, {
	Suspense,
	lazy,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';
import {Switch, useParams} from 'react-router-dom';
import BundleRouter from '~/route-middleware/BundleRouter';
import * as API from '~/shared/api';
import {AlertTypes} from '~/shared/components/Alert';
import EmbeddedAlertList from '~/shared/components/EmbeddedAlertList';
import Label from '~/shared/components/Label';
import Loading from '~/shared/components/Loading';
import RouteNotFound from '~/shared/components/RouteNotFound';
import BasePage from '~/shared/components/base-page';
import DownloadPDFReport from '~/shared/components/download-report/DownloadPDFReport';
import {DownloadReportDropdown} from '~/shared/components/download-report/DownloadReportDropdown';
import {DownloadStaticCSVReport} from '~/shared/components/download-report/DownloadStaticCSVReport';
import {CSVType} from '~/shared/components/download-report/utils';
import {ChannelContext} from '~/shared/context/channel';
import {useRequest} from '~/shared/hooks/useRequest';
import ErrorPage from '~/shared/pages/ErrorPage';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {SegmentStates, SegmentTypes} from '~/shared/util/constants';
import {formatUTCDate} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {Segment} from '~/shared/util/records';
import {Routes, SEGMENTS, getMatchedRoute, toRoute} from '~/shared/util/router';

const Overview = lazy(
	() => import(/* webpackChunkName: "SegmentOverview" */ './Overview')
);
const OverviewRealTime = lazy(
	() => import(/* webpackChunkName: "SegmentOverview" */ './OverviewRealTime')
);
const Membership = lazy(
	() => import(/* webpackChunkName: "SegmentMembership" */ './Membership')
);
const Interests = lazy(
	() => import(/* webpackChunkName: "SegmentInterests" */ './Interests')
);
const InterestDetails = lazy(
	() =>
		import(

			/* webpackChunkName: "SegmentInterestDetails" */ './InterestDetails'
		)
);
const Distribution = lazy(
	() => import(/* webpackChunkName: "SegmentDistribution" */ './Distribution')
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.CONTACTS_SEGMENT,
	},
	{
		exact: true,
		label: Liferay.Language.get('membership'),
		route: Routes.CONTACTS_SEGMENT_MEMBERSHIP,
	},
	{
		exact: false,
		label: Liferay.Language.get('interests'),
		route: Routes.CONTACTS_SEGMENT_INTERESTS,
	},
	{
		exact: true,
		label: Liferay.Language.get('distribution'),
		route: Routes.CONTACTS_SEGMENT_DISTRIBUTION,
	},
];

const SEGMENTS_LANGUAGE_MAP = {
	[SegmentTypes.Batch]: Liferay.Language.get('batch-segment'),
	[SegmentTypes.RealTime]: Liferay.Language.get('real-time-segment'),
};

export const SegmentProfileRoutes = function SegmentProfileRoutes() {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId, groupId, id} = useParams();

	const {data, error, loading, refetch} = useRequest({
		dataSourceFn: API.individualSegment.fetch,
		variables: {
			groupId,
			includeReferencedObjects: true,
			segmentId: id,
		},
	});

	const segment = useMemo(() => new Segment(data), [data]);

	const [segmentDetails, setSegmentDetails] = useState({
		dateModified: segment.dateModified,
		name: segment.name,
		segmentType: segment.segmentType,
	});

	useEffect(() => {
		if (data && !loading) {
			setSegmentDetails({
				dateModified: segment.dateModified,
				name: segment.name,
				segmentType: segment.segmentType,
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data, loading]);

	if (loading && !segmentDetails.name) {
		return <Loading />;
	}

	const title = segmentDetails.name || Liferay.Language.get('unknown');

	if (error) {
		return (
			<ErrorPage
				href={toRoute(Routes.CONTACTS_LIST_ENTITY, {
					channelId,
					groupId,
					type: SEGMENTS,
				})}
				linkLabel={Liferay.Language.get('go-to-segments')}
				message={Liferay.Language.get(
					'the-segment-you-are-looking-for-does-not-exist'
				)}
				subtitle={Liferay.Language.get('segment-not-found')}
			/>
		);
	}

	const checkDisabled = () => segment.state === SegmentStates.Disabled;

	const getAlerts = () => {
		if (segment.state === SegmentStates.InProgress) {
			return [
				{
					alertType: AlertTypes.Info,
					message: Liferay.Language.get(
						'segment-data-is-processing-please-check-back-later'
					),
					stripe: true,
				},
			];
		}
		else if (checkDisabled()) {
			return [
				{
					alertType: AlertTypes.Danger,
					message: Liferay.Language.get(
						'this-segment-is-disabled-because-some-criteria-has-been-affected-by-removal-of-a-data-source.-to-continue-using-this-segment-please-update-the-criteria'
					),
					stripe: true,
				},
			];
		}
	};

	const isBatch = segmentDetails.segmentType === SegmentTypes.Batch;
	const lastUpdateMessage = sub(Liferay.Language.get('last-update-x'), [
		formatUTCDate(segmentDetails.dateModified, 'MMM DD, YYYY hh:mm a')
			.replace('am', 'a.m.')
			.replace('pm', 'p.m.'),
	]);

	return (
		<BasePage
			className={getCN(
				'segment-profile-root',
				'segment-overview-root',
				'overview-root'
			)}
			documentTitle={`${segmentDetails.name} - ${Liferay.Language.get(
				'segment'
			)}`}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name,
					}),
					breadcrumbs.getSegments({channelId, groupId}),
					breadcrumbs.getEntityName({label: segmentDetails.name}),
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection
						className="mb-3"
						subtitle={`${Liferay.Language.get('erc')}: ${
							segment.externalReferenceCode
						}`}
						title={title}
					>
						<Label display="secondary" size="lg" uppercase>
							{SEGMENTS_LANGUAGE_MAP[segmentDetails.segmentType]}
						</Label>
					</BasePage.Header.TitleSection>

					{isBatch && (
						<BasePage.Header.Section>
							<BasePage.Header.PageActions
								actions={[
									{
										button: true,
										displayType: 'secondary',
										href: toRoute(
											Routes.CONTACTS_SEGMENT_EDIT,
											{
												channelId,
												groupId,
												id,
												type: SEGMENTS,
											}
										),
										label: Liferay.Language.get(
											'edit-segment'
										),
									},
								]}
							/>
						</BasePage.Header.Section>
					)}
				</BasePage.Row>

				{isBatch && (
					<BasePage.Header.NavBar
						items={NAV_ITEMS}
						routeParams={{channelId, groupId, id}}
					/>
				)}
			</BasePage.Header>

			{isBatch &&
				getMatchedRoute(NAV_ITEMS) === Routes.CONTACTS_SEGMENT && (
					<BasePage.SubHeader>
						<div className="d-flex justify-content-end w-100">
							<DownloadPDFReport
								disabled={false}
								showDateRange={false}
								subtitle={selectedChannel?.name}
								title={title}
							/>
						</div>
					</BasePage.SubHeader>
				)}

			{isBatch &&
				getMatchedRoute(NAV_ITEMS) ===
					Routes.CONTACTS_SEGMENT_MEMBERSHIP && (
					<BasePage.SubHeader>
						<div className="d-flex justify-content-end w-100">
							<DownloadStaticCSVReport
								disabled={checkDisabled()}
								segmentId={segment.id}
								type={CSVType.Membership}
								typeLang={Liferay.Language.get(
									'segment-membership'
								)}
							/>
						</div>
					</BasePage.SubHeader>
				)}

			{!isBatch && (
				<BasePage.SubHeader>
					<div className="align-items-center d-flex justify-content-end w-100">
						<Text color="secondary" size={3}>
							{lastUpdateMessage}
						</Text>

						<span className="ml-3 mr-2">|</span>

						<DownloadReportDropdown
							className="button-root"
							label={Liferay.Language.get('real-time-segment')}
							segmentId={segment.id}
							subtitle={lastUpdateMessage}
							title={segmentDetails.name}
						/>

						<ClayButton
							borderless
							button
							className="button-root"
							disabled={loading}
							displayType="secondary"
							key={Liferay.Language.get('refresh-data')}
							onClick={refetch}
							size="sm"
						>
							{loading ? (
								<Loading align="false" className="mr-2 mt-n1" />
							) : (
								<ClayIcon className="mr-2" symbol="reload" />
							)}
							{Liferay.Language.get('refresh-data')}
						</ClayButton>

						<ClayLink
							borderless
							button
							className="button-root"
							displayType="secondary"
							href={toRoute(Routes.CONTACTS_SEGMENT_EDIT, {
								channelId,
								groupId,
								id,
							})}
							small
						>
							<ClayIcon className="mr-2" symbol="pencil" />
							{Liferay.Language.get('edit-segment')}
						</ClayLink>
					</div>
				</BasePage.SubHeader>
			)}

			<EmbeddedAlertList alerts={getAlerts()} />

			<BasePage.Body disabled={checkDisabled()}>
				{segment.id ? (
					<Suspense fallback={<Loading />}>
						<Switch>
							<BundleRouter
								componentProps={{segment}}
								data={Membership}
								exact
								path={Routes.CONTACTS_SEGMENT_MEMBERSHIP}
							/>

							<BundleRouter
								componentProps={{segment}}
								data={InterestDetails}
								exact
								path={Routes.CONTACTS_SEGMENT_INTEREST_DETAILS}
							/>

							<BundleRouter
								componentProps={{segment}}
								data={Interests}
								destructured={false}
								exact
								path={Routes.CONTACTS_SEGMENT_INTERESTS}
							/>

							<BundleRouter
								componentProps={{segment}}
								data={Distribution}
								exact
								path={Routes.CONTACTS_SEGMENT_DISTRIBUTION}
							/>

							<BundleRouter
								componentProps={{segment}}
								data={isBatch ? Overview : OverviewRealTime}
								exact
								path={Routes.CONTACTS_SEGMENT}
							/>

							<RouteNotFound />
						</Switch>
					</Suspense>
				) : (
					<Loading />
				)}
			</BasePage.Body>
		</BasePage>
	);
};

export default SegmentProfileRoutes;
