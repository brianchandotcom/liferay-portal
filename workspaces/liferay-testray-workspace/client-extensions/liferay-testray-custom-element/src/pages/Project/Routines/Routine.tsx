/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useParams} from 'react-router-dom';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import ProgressBar from '~/components/ProgressBar';
import useSearchBuilder from '~/hooks/useSearchBuilder';
import i18n from '~/i18n';
import {TestrayBuild, testrayBuildImpl} from '~/services/rest';
import dayjs from '~/util/date';

import BuildAddButton from './Builds/BuildAddButton';
import BuildHistoryChart from './Builds/BuildHistoryChart';
import useBuildActions from './Builds/useBuildActions';

const Routine = () => {
	const {actions, formModal} = useBuildActions();
	const {routineId} = useParams();

	const searchBuilder = useSearchBuilder({useURIEncode: false});

	const routineFilter = searchBuilder
		.eq('routineId', routineId as string)
		.and()
		.eq('template', false)
		.and()
		.build();

	return (
		<Container>
			<ListView
				forceRefetch={formModal.forceRefetch}
				initialContext={{
					columns: {
						in_progress: false,
						passed: false,
						total: false,
						untested: false,
					},
				}}
				managementToolbarProps={{
					buttons: (actions) =>
						actions?.create && (
							<BuildAddButton routineId={routineId as string} />
						),
					filterSchema: 'builds',
					title: i18n.translate('build-history'),
				}}
				resource={testrayBuildImpl.resource}
				tableProps={{
					actions,
					columns: [
						{
							key: 'status',
							render: (_, {promoted, tasks}: TestrayBuild) => {
								const [task] = tasks;

								return (
									<>
										{promoted && (
											<span
												title={i18n.translate(
													'promoted'
												)}
											>
												<ClayIcon
													className="mr-3"
													color="darkblue"
													symbol="star"
												/>
											</span>
										)}

										{task && (
											<span title={task.dueStatus.name}>
												<ClayIcon
													className={classNames(
														'label-chart symbol',
														{
															[task.dueStatus.key.toLowerCase()]: task
																.dueStatus.key,
														}
													)}
													symbol="circle"
												/>
											</span>
										)}
									</>
								);
							},
							value: i18n.translate('status'),
						},
						{
							clickable: true,
							key: 'dateCreated',
							render: (dateCreated) =>
								dayjs(dateCreated).format('lll'),
							size: 'sm',
							value: i18n.translate('create-date'),
						},
						{
							clickable: true,
							key: 'gitHash',
							value: i18n.translate('git-hash'),
						},
						{
							clickable: true,
							key: 'product_version',
							render: (_, {productVersion}) =>
								productVersion?.name,
							value: i18n.translate('product-version'),
						},
						{
							clickable: true,
							key: 'name',
							value: i18n.translate('build'),
						},
						{
							clickable: true,
							key: 'caseResultFailed',
							value: i18n.translate('failed'),
						},
						{
							clickable: true,
							key: 'caseResultBlocked',
							value: i18n.translate('blocked'),
						},
						{
							clickable: true,
							key: 'caseResultUntested',
							value: i18n.translate('untested'),
						},
						{
							clickable: true,
							key: 'caseResultInProgress',
							value: i18n.translate('in-progress'),
						},
						{
							clickable: true,
							key: 'caseResultPassed',
							value: i18n.translate('passed'),
						},
						{
							clickable: true,
							key: 'caseResultTestFix',
							value: i18n.translate('test-fix'),
						},
						{
							clickable: true,
							key: 'total',
							render: (_, build: TestrayBuild) =>
								[
									build.caseResultBlocked,
									build.caseResultFailed,
									build.caseResultInProgress,
									build.caseResultIncomplete,
									build.caseResultPassed,
									build.caseResultTestFix,
									build.caseResultUntested,
								]
									.map(Number)
									.reduce(
										(prevCount, currentCount) =>
											prevCount + currentCount
									),
							value: i18n.translate('total'),
						},
						{
							clickable: true,
							key: 'metrics',
							render: (_, build: TestrayBuild) => (
								<ProgressBar
									items={{
										blocked: build.caseResultBlocked as number,
										failed: build.caseResultFailed as number,
										incomplete: build.caseResultIncomplete as number,
										passed: build.caseResultPassed as number,
										test_fix: build.caseResultTestFix as number,
									}}
								/>
							),
							size: 'xl',
							value: i18n.translate('metrics'),
						},
					],
					navigateTo: ({id}) => `build/${id}`,
				}}
				transformData={(response) =>
					testrayBuildImpl.transformDataFromList(response)
				}
				variables={{
					filter: routineFilter,
				}}
			>
				{({items, totalCount}) =>
					totalCount > 0 && <BuildHistoryChart builds={items} />
				}
			</ListView>
		</Container>
	);
};

export default Routine;
