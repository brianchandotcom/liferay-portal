/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import {graphql} from '@apollo/client/react/hoc';
import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {addAlert} from '~/shared/actions/alerts';
import Card from '~/shared/components/Card';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {
	compose,
	withBaseResults,
	withQueryPagination,
	withQueryRangeSelectors,
} from '~/shared/hoc';
import getMetricsMapper from '~/shared/hoc/mappers/metrics';
import {Alert} from '~/shared/types';
import {
	GDPRRequestStatuses,
	GDPRRequestTypes,
	Sizes,
} from '~/shared/util/constants';
import {CUSTOM_DATE_FORMAT, formatDateToTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {CREATE_DATE, createOrderIOMap} from '~/shared/util/pagination';
import {User} from '~/shared/util/records';
import URLConstants from '~/shared/util/url-constants';

import DataControlRequest from '../queries/DataControlRequestMutation';
import SuppressedUsersListQuery from '../queries/SuppressedUsersListQuery';

const withData = () =>
	graphql(
		SuppressedUsersListQuery,
		getMetricsMapper(
			({
				suppressions: {suppressions, total},
			}: {
				suppressions: {suppressions: any[]; total: number};
			}) => ({
				items: suppressions,
				total,
			}),
			{
				fetchPolicy: 'no-cache',
			},
			SuppressedUsersListQuery as unknown as null
		)
	);

const withQueryOptions =
	(Component: React.ComponentType<any>) =>
	({
		addAlert,
		currentUser,
		refetch,
		...otherProps
	}: Pick<ISuppressedUserListProps, 'addAlert' | 'currentUser'> & {
		refetch: () => Promise<any>;
	}) => {
		const [unsuppressUser] = useMutation(DataControlRequest);

		const authorized = currentUser.isAdmin();

		return (
			<Component
				{...otherProps}
				renderInlineRowActions={({
					data: {dataControlTaskStatus, emailAddress},
				}: {
					data: {
						dataControlTaskStatus: GDPRRequestStatuses;
						emailAddress: string;
					};
				}) =>
					authorized &&
					dataControlTaskStatus !== GDPRRequestStatuses.Pending && (
						<ClayButton
							className="button-root unsuppress"
							displayType="secondary"
							onClick={() => {
								unsuppressUser({
									variables: {
										emailAddresses: [emailAddress],
										ownerId: String(currentUser.id),
										types: [GDPRRequestTypes.Unsuppress],
										userId: String(currentUser.userId),
										userName: currentUser.name,
									},
								})
									.then(() => {
										addAlert({
											alertType: Alert.Types.Success,
											message: sub(
												Liferay.Language.get(
													'x-has-been-successfully-unsuppressed'
												),
												[emailAddress]
											) as string,
										});

										refetch();
									})
									.catch(() => {
										addAlert({
											alertType: Alert.Types.Error,
											message: sub(
												Liferay.Language.get(
													'there-was-an-error-unsuppressing-x.-please-try-again'
												),
												[emailAddress]
											) as string,
											timeout: false,
										});
									});
							}}
							small
						>
							{Liferay.Language.get('unsuppress')}
						</ClayButton>
					)
				}
			/>
		);
	};

const SuppressedListWithData = withBaseResults(withData, {
	getColumns: ({timeZoneId}: {timeZoneId: string}) => [
		{
			accessor: 'emailAddress',
			className: 'table-cell-expand',
			label: Liferay.Language.get('email'),
			title: true,
		},
		{
			accessor: 'dataControlTaskBatchId',
			label: Liferay.Language.get('request-id'),
		},
		{
			accessor: 'dataControlTaskCreateDate',
			dataFormatter: (val: string) =>
				formatDateToTimeZone(val, CUSTOM_DATE_FORMAT, timeZoneId),
			label: Liferay.Language.get('requested-date'),
		},
		{
			accessor: 'createDate',
			dataFormatter: (val: string) =>
				formatDateToTimeZone(val, CUSTOM_DATE_FORMAT, timeZoneId),
			label: Liferay.Language.get('suppression-date'),
		},
	],
	primary: true,
	showDropdownRangeKey: false,
	withQueryOptions,
});

const connector = connect(null, {addAlert});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface ISuppressedUserListProps extends PropsFromRedux {
	currentUser: User;
	timeZoneId: string;
}

const SuppressedUserList: React.FC<ISuppressedUserListProps> = (props) => (
	<Card className="suppressed-user-list-root" pageDisplay>
		<SuppressedListWithData
			{...props}
			checkDisabled={({
				dataControlTaskStatus,
			}: {
				dataControlTaskStatus: GDPRRequestStatuses;
			}) => dataControlTaskStatus === GDPRRequestStatuses.Pending}
			entityLabel={Liferay.Language.get('suppressed-users')}
			noResultsRenderer={
				<NoResultsDisplay
					description={
						<>
							{Liferay.Language.get(
								'to-suppress-a-user,-go-to-data-control-&-privacy-under-settings-and-create-a-new-request-on-the-request-log'
							)}

							<ClayLink
								className="d-block mb-3"
								href={URLConstants.SuppressedUsersDocumentation}
								key="DOCUMENTATION"
								target="_blank"
							>
								{Liferay.Language.get(
									'access-our-documentation-to-learn-more'
								)}
							</ClayLink>
						</>
					}
					icon={{
						border: false,
						size: Sizes.XXXLarge,
						symbol: 'ac_satellite',
					}}
					title={Liferay.Language.get('no-suppressed-users-found')}
				/>
			}
		/>
	</Card>
);

export default compose<any>(
	connector,
	withQueryPagination({initialOrderIOMap: createOrderIOMap(CREATE_DATE)}),
	withQueryRangeSelectors()
)(SuppressedUserList);
