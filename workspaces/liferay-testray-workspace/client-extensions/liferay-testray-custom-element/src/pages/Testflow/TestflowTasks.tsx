/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {Dispatch, useContext} from 'react';
import {Link, useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import Avatar from '~/components/Avatar';
import AssignToMe from '~/components/Avatar/AssignToMe';
import Code from '~/components/Code';
import FloatingBox from '~/components/FloatingBox/index';
import Container from '~/components/Layout/Container';
import ListView from '~/components/ListView';
import Loading from '~/components/Loading';
import TaskbarProgress from '~/components/ProgressBar/TaskbarProgress';
import StatusBadge from '~/components/StatusBadge';
import {StatusBadgeType} from '~/components/StatusBadge/StatusBadge';
import QATable from '~/components/Table/QATable';
import {ListViewTypes} from '~/context/ListViewContext';
import {TestrayContext} from '~/context/TestrayContext';
import SearchBuilder from '~/core/SearchBuilder';
import useCaseResultGroupBy from '~/hooks/data/useCaseResultGroupBy';
import useSubtaskScore from '~/hooks/data/useSubtaskScore';
import useHeader from '~/hooks/useHeader';
import useMutate from '~/hooks/useMutate';
import i18n from '~/i18n';
import {Liferay} from '~/services/liferay';
import {
	PickList,
	TestraySubTask,
	TestrayTask,
	TestrayTaskUser,
	UserAccount,
} from '~/services/rest';
import {testraySubTaskImpl} from '~/services/rest/TestraySubtask';
import {StatusesProgressScore, chartClassNames} from '~/util/constants';
import {getTimeFromNow} from '~/util/date';
import {SubTaskStatuses} from '~/util/statuses';

import SubtaskCompleteModal from './Subtask/SubtaskCompleteModal';
import useSubtasksActions from './Subtask/useSubtasksActions';
import TaskHeaderActions from './TaskHeaderActions';

type OutletContext = {
	data: {
		testrayTask: TestrayTask & {
			actions: {
				[key: string]: string;
			};
		};
		testrayTaskUser: TestrayTaskUser[];
	};
	revalidate: {revalidateSubtask: () => void};
};
const ShortcutIcon = () => (
	<ClayIcon className="ml-2" fontSize={12} symbol="shortcut" />
);

const TestFlowTasks = () => {
	const {
		data: {testrayTask, testrayTaskUser},
		revalidate: {revalidateSubtask},
	} = useOutletContext<OutletContext>();
	const {actions, completeModal, forceRefetch} = useSubtasksActions();
	const {taskId} = useParams();
	const {updateItemFromList} = useMutate();

	const [{myUserAccount}] = useContext(TestrayContext);

	useHeader({
		heading: [
			{
				category: i18n.translate('task'),
				title: testrayTask?.name,
			},
		],
		tabs: [],
	});

	const {
		donut: {columns},
	} = useCaseResultGroupBy(testrayTask?.build?.id);

	const subtaskScore = useSubtaskScore({
		testrayTask,
		userId: myUserAccount?.id as number,
	});

	if (!testrayTask) {
		return <Loading />;
	}

	const getFloatingBoxAlerts = (subtasks: TestraySubTask[]) => {
		const alerts = [];

		if (subtasks.length === 1) {
			alerts.push({
				text: i18n.translate(
					'please-select-at-least-two-subtasks-to-merge'
				),
			});
		}

		const subtasksWithDifferentAssignedUsers = subtasks
			.filter(
				({user}) =>
					user &&
					user.id.toString() !== Liferay.ThemeDisplay.getUserId()
			)
			.map(({name}) => ({
				text: i18n.sub(
					'subtask-x-must-be-assigned-to-you-to-be-user-in-a-merge',
					name
				),
			}));

		return [...alerts, ...subtasksWithDifferentAssignedUsers];
	};

	const onMergeSubtasks = async (
		subtasks: TestraySubTask[],
		mutate: KeyedMutator<any>,
		dispatch: Dispatch<any>
	) => {
		await testraySubTaskImpl.mergedToSubtask(subtasks);

		updateItemFromList(
			mutate,
			0,
			{},
			{
				revalidate: true,
			}
		);

		dispatch({
			payload: [],
			type: ListViewTypes.SET_CHECKED_ROW,
		});
	};

	const searchBuilder = new SearchBuilder({useURIEncode: false});

	const subTaskFilter = searchBuilder
		.eq('taskId', taskId as string)
		.and()
		.ne('dueStatus', SubTaskStatuses.MERGED)
		.build();

	return (
		<>
			{testrayTask.actions?.update && <TaskHeaderActions />}

			<Container collapsable title={i18n.sub('task-x', 'details')}>
				<div className="d-flex flex-wrap">
					<div className="col-4 col-lg-4 col-md-12 p-0">
						<QATable
							items={[
								{
									title: i18n.translate('status'),
									value: (
										<StatusBadge
											type={
												testrayTask.dueStatus.key.toLowerCase() as StatusBadgeType
											}
										>
											{testrayTask.dueStatus.name}
										</StatusBadge>
									),
								},
								{
									title: i18n.translate('assigned-users'),
									value: (
										<Avatar.Group
											assignedUsers={testrayTaskUser
												.map(
													({user}) =>
														user as UserAccount
												)
												.map(({image, name}) => ({
													name,
													url: image,
												}))}
											groupSize={5}
										/>
									),
								},
								{
									title: i18n.translate('created'),
									value: getTimeFromNow(
										testrayTask.dateCreated
									),
								},
							]}
						/>
					</div>

					<div className="col-8 col-lg-8 col-md-12 mb-3 p-0">
						<QATable
							items={[
								{
									title: i18n.translate('project-name'),
									value: (
										<Link
											className="text-dark"
											to={`/project/${testrayTask.build?.project?.id}/routines`}
										>
											{testrayTask.build?.project?.name}

											<ShortcutIcon />
										</Link>
									),
								},
								{
									title: i18n.translate('routine-name'),
									value: (
										<Link
											className="text-dark"
											to={`/project/${testrayTask.build?.project?.id}/routines/${testrayTask.build?.routine?.id}`}
										>
											{testrayTask.build?.routine?.name}

											<ShortcutIcon />
										</Link>
									),
								},
								{
									title: i18n.translate('build-name'),
									value: (
										<Link
											className="text-dark"
											to={`/project/${testrayTask.build?.project?.id}/routines/${testrayTask.build?.routine?.id}/build/${testrayTask.build?.id}`}
										>
											{testrayTask.build?.name}

											<ShortcutIcon />
										</Link>
									),
								},
							]}
						/>

						<div className="pb-4">
							<TaskbarProgress
								displayTotalCompleted={false}
								items={columns as any}
								legend
								taskbarClassNames={chartClassNames}
							/>
						</div>
					</div>
				</div>
			</Container>

			<Container
				className="mt-3"
				collapsable
				title={i18n.translate('progress-score')}
			>
				<div className="pb-5">
					<TaskbarProgress
						displayTotalCompleted
						items={[
							[
								StatusesProgressScore.SELF,
								Number(subtaskScore.selfCompleted ?? 0),
							],
							[
								StatusesProgressScore.OTHER,
								Number(subtaskScore.othersCompleted ?? 0),
							],
							[
								StatusesProgressScore.INCOMPLETE,
								Number(subtaskScore.incomplete ?? 0),
							],
						]}
						legend
						taskbarClassNames={chartClassNames}
						totalCompleted={Number(subtaskScore.completed ?? 0)}
					/>
				</div>
			</Container>

			<Container className="mt-3">
				<ListView
					forceRefetch={forceRefetch}
					managementToolbarProps={{
						filterSchema: 'subtasks',
						title: i18n.translate('subtasks'),
					}}
					resource={testraySubTaskImpl.resource}
					tableProps={{
						actions,
						bodyVerticalAlignment: 'top',
						columns: [
							{
								clickable: true,
								key: 'name',
								sorteable: true,
								value: i18n.translate('name'),
							},
							{
								clickable: true,
								key: 'dueStatus',
								render: (dueStatus: PickList) => (
									<StatusBadge
										type={
											dueStatus?.key.toLowerCase() as StatusBadgeType
										}
									>
										{dueStatus?.name}
									</StatusBadge>
								),
								sorteable: true,
								value: i18n.translate('status'),
							},
							{
								clickable: true,
								key: 'score',
								sorteable: true,
								value: i18n.translate('score'),
							},
							{
								clickable: true,
								key: 'tests',
								value: i18n.translate('tests'),
							},
							{
								key: 'errors',
								render: (value) => <Code>{value}</Code>,
								size: 'xl',
								value: i18n.translate('errors'),
							},
							{
								key: 'user',
								render: (
									_: any,
									subtask: TestraySubTask & {
										actions: {
											[key: string]: string;
										};
									},
									mutate
								) => {
									if (subtask.user) {
										return (
											<Avatar
												className="text-capitalize"
												displayName
												name={subtask?.user?.name}
												size="sm"
												url={subtask.user.image}
											/>
										);
									}

									return (
										<AssignToMe
											hidden={!subtask.actions.update}
											onClick={() =>
												testraySubTaskImpl
													.assignToMe(subtask)
													.then(() => {
														updateItemFromList(
															mutate,
															0,
															{},
															{
																revalidate: true,
															}
														);
													})
											}
										/>
									);
								},
								value: i18n.translate('assignee'),
							},
						],
						navigateTo: (subtask) => `subtasks/${subtask.id}`,
						rowSelectable: true,
						rowWrap: true,
					}}
					transformData={(response) =>
						testraySubTaskImpl.transformDataFromList(response)
					}
					variables={{
						filter: subTaskFilter,
					}}
				>
					{(
						{items},
						{dispatch, listViewContext: {selectedRows}, mutate}
					) => {
						const selectedSubtasks: TestraySubTask[] = selectedRows.map(
							(rowId) => items.find(({id}) => rowId === id)
						);

						const alerts = getFloatingBoxAlerts(selectedSubtasks);

						return (
							<FloatingBox
								alerts={alerts}
								clearList={() =>
									dispatch({
										payload: [],
										type: ListViewTypes.SET_CHECKED_ROW,
									})
								}
								isVisible={!!selectedRows.length}
								onSubmit={() =>
									onMergeSubtasks(
										selectedSubtasks,
										mutate,
										dispatch
									)
								}
								primaryButtonProps={{
									disabled: !!alerts.length,
									title: i18n.translate('merge-subtasks'),
								}}
								selectedCount={selectedRows.length}
								tooltipText={i18n.translate(
									'merge-selected-subtasks-into-the-highest-scoring-subtask'
								)}
							/>
						);
					}}
				</ListView>
			</Container>

			<SubtaskCompleteModal
				modal={completeModal}
				revalidateSubtask={revalidateSubtask}
				subtask={completeModal.modalState}
			/>
		</>
	);
};

export default TestFlowTasks;
