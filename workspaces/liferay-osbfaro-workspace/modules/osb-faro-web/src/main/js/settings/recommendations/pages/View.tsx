/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation, useQuery} from '@apollo/client';
import {getOperationName} from '@apollo/client/utilities';
import {get} from 'lodash';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {useParams} from 'react-router-dom';
import {compose} from 'redux';
import BasePage from '~/settings/components/base-page/BasePage';
import {addAlert} from '~/shared/actions/alerts';
import {close, modalTypes, open} from '~/shared/actions/modals';
import {withHistory} from '~/shared/hoc';
import withRecommendation from '~/shared/hoc/WithRecommendation';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {useTimeZone} from '~/shared/hooks/useTimeZone';
import {Alert} from '~/shared/types';
import {getRecommendations} from '~/shared/util/breadcrumbs';
import Constants, {JobRunStatuses} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import {Routes, toRoute} from '~/shared/util/router';

import OutputVersionsCard from '../components/OutputVersionsCard';
import TrainingItemsCard from '../components/TrainingItemsCard';
import RecommendationJobRunsQuery from '../queries/RecommendationJobRunsQuery';
import {
	RECOMMENDATION_DELETE_MUTATION,
	RECOMMENDATION_RUN_MUTATION,
} from '../queries/RecommendationMutation';
import {Filter, Job, JobParameter} from '../utils/utils';

const {
	pagination: {orderDescending},
} = Constants;
const connector = connect(null, {addAlert, close, open});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IViewProps extends PropsFromRedux {
	history: {
		push: (value: string) => void;
	};
	job: Job;
}

const View: React.FC<IViewProps> = ({addAlert, close, history, job, open}) => {
	const {groupId = '', jobId = ''} = useParams<{
		groupId: string;
		jobId: string;
	}>();
	const {timeZoneId} = useTimeZone();
	const currentUser = useCurrentUser();

	const {data: jobRuns, loading} = useQuery(RecommendationJobRunsQuery, {
		variables: {
			jobId,
			size: 1,
			sort: {
				column: 'id',
				type: orderDescending.toUpperCase(),
			},
			start: 0,
		},
	});

	const [deleteRecommendationJobs] = useMutation(
		RECOMMENDATION_DELETE_MUTATION
	);

	const jobRun =
		!!jobRuns &&
		jobRuns.jobRuns.total &&
		get(jobRuns, ['jobRuns', 'jobRuns', 0], null);

	const jobRunRunning: boolean =
		get(jobRun, 'status', null) === JobRunStatuses.Running;

	const [runRecommendationJob] = useMutation(RECOMMENDATION_RUN_MUTATION);

	const itemFilters: Filter[] = (
		get(job, 'parameters', [] as JobParameter[]) as Filter[]
	).filter(({name}) => name !== 'includePreviousPeriod');

	const name = get(job, 'name');

	return (
		<div className="row">
			<div className="col-xl-8">
				<BasePage
					breadcrumbItems={[
						getRecommendations({groupId}),
						{
							active: true,
							label: name,
						},
					]}
					pageActions={
						currentUser.isAdmin()
							? [
									{
										disabled: loading || jobRunRunning,
										label: Liferay.Language.get('retrain'),
										onClick: () => {
											open(
												modalTypes.MANUALLY_RETRAIN_MODEL_MODAL,
												{
													job,
													onClose: close,
													onSubmit: ({
														runDataPeriod,
													}: {
														runDataPeriod: string;
													}) => {
														runRecommendationJob({
															awaitRefetchQueries:
																true,
															refetchQueries: [
																getOperationName(
																	RecommendationJobRunsQuery
																) as string,
															],
															variables: {
																jobId,
																runDataPeriod,
															},
														})
															.then(() => {
																addAlert({
																	alertType:
																		Alert
																			.Types
																			.Success,
																	message:
																		Liferay.Language.get(
																			'retraining-has-been-started'
																		),
																});

																close();
															})
															.catch(() => {
																addAlert({
																	alertType:
																		Alert
																			.Types
																			.Error,
																	message:
																		Liferay.Language.get(
																			'there-was-an-error-processing-your-request.-please-try-again'
																		),
																	timeout:
																		false,
																});
															});
													},
													trainingPeriod: get(
														job,
														'trainingPeriod'
													),
												}
											);
										},
									},
									{
										href: toRoute(
											Routes.SETTINGS_RECOMMENDATION_EDIT,
											{
												groupId,
												jobId,
											}
										),
										label: Liferay.Language.get('edit'),
									},
									{
										label: Liferay.Language.get('delete'),
										onClick: () => {
											open(
												modalTypes.CONFIRMATION_MODAL,
												{
													message: (
														<div>
															<div className="h4 text-secondary">
																{sub(
																	Liferay.Language.get(
																		'delete-x-and-its-historical-training-output-data'
																	),
																	[name]
																)}
															</div>

															<p>
																{Liferay.Language.get(
																	'components-using-this-model-will-need-to-be-reconfigured'
																)}
															</p>
														</div>
													),
													modalVariant:
														'modal-warning',
													onClose: close,
													onSubmit: () => {
														deleteRecommendationJobs(
															{
																variables: {
																	jobIds: [
																		jobId,
																	],
																},
															}
														)
															.then(() => {
																addAlert({
																	alertType:
																		Alert
																			.Types
																			.Success,
																	message:
																		sub(
																			Liferay.Language.get(
																				'x-has-been-deleted'
																			),
																			[
																				name,
																			]
																		) as string,
																});

																history.push(
																	toRoute(
																		Routes.SETTINGS_RECOMMENDATIONS,
																		{
																			groupId,
																		}
																	)
																);
															})
															.catch(() => {
																addAlert({
																	alertType:
																		Alert
																			.Types
																			.Error,
																	message:
																		Liferay.Language.get(
																			'there-was-an-error-processing-your-request.-please-try-again'
																		),
																	timeout:
																		false,
																});
															});
													},
													submitButtonDisplay:
														'warning',
													submitMessage:
														Liferay.Language.get(
															'delete'
														),
													title: sub(
														Liferay.Language.get(
															'deleting-x'
														),
														[name]
													),
													titleIcon: 'warning-full',
												}
											);
										},
									},
								]
							: []
					}
					pageActionsDisplayLimit={3}
					pageTitle={name}
				>
					<OutputVersionsCard
						jobId={jobId}
						nextRunDate={get(job, 'nextRunDate')}
						runFrequency={get(job, 'runFrequency')}
						timeZoneId={timeZoneId}
					/>
					<TrainingItemsCard itemFilters={itemFilters} />
				</BasePage>
			</div>
		</div>
	);
};

export default compose<React.ComponentType<any>>(
	withRecommendation,
	withHistory,
	connector
)(View);
