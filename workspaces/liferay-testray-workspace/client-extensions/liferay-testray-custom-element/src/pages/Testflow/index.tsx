/* eslint-disable no-console */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Avatar from '../../components/Avatar';
import Container from '../../components/Layout/Container';
import ListView from '../../components/ListView';
import TaskbarProgress from '../../components/ProgressBar/TaskbarProgress';
import StatusBadge from '../../components/StatusBadge';
import {StatusBadgeType} from '../../components/StatusBadge/StatusBadge';
import SearchBuilder from '../../core/SearchBuilder';
import {useHeader} from '../../hooks';
import i18n from '../../i18n';
import {
	PickList,
	TestrayTask,
	UserAccount,
	testrayTaskImpl,
} from '../../services/rest';
import {StatusesProgressScore, chartClassNames} from '../../util/constants';
import {getTimeFromNow} from '../../util/date';
import {getPercentLabel} from '../../util/graph.util';
import {TaskStatuses} from '../../util/statuses';
import TestflowModal from './TestflowModal';
import useTestflowActions from './useTestflowActions';

const TestFlow = () => {
	const {actions, modal} = useTestflowActions();

	const searchBuilder = new SearchBuilder({useURIEncode: false});

	const taskFilter = searchBuilder.ne('dueStatus', TaskStatuses.OPEN).build();

	useHeader({icon: 'merge'});

	return (
		<Container>
			<ListView
				managementToolbarProps={{
					addButton: () => modal.open(),
					filterSchema: 'testflow',
					title: i18n.translate('tasks'),
				}}
				resource={testrayTaskImpl.resource}
				tableProps={{
					actions,
					columns: [
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
							value: i18n.translate('status'),
						},
						{
							clickable: true,
							key: 'dateCreated',
							render: (_, task) =>
								task?.build?.dateCreated &&
								getTimeFromNow(task?.build?.dateCreated),
							value: i18n.translate('start-date'),
						},
						{
							clickable: true,
							key: 'name',
							size: 'lg',
							value: i18n.translate('task'),
						},
						{
							clickable: true,
							key: 'projectName',
							render: (_, task) => task?.build?.project?.name,
							value: i18n.translate('project-name'),
						},
						{
							clickable: true,
							key: 'routineName',
							render: (_, task) => task?.build?.routine?.name,
							value: i18n.translate('routine-name'),
						},
						{
							clickable: true,
							key: 'buildName',
							render: (_, task) => task?.build?.name,
							size: 'lg',
							value: i18n.translate('build-name'),
						},
						{
							key: 'score',
							render: (
								_,
								{
									subtaskScore,
									subtaskScoreCompleted,
								}: TestrayTask
							) => (
								<span>
									{`${subtaskScoreCompleted ?? 0} / ${
										subtaskScore ?? 0
									}`}

									<span className="text-gray">
										{` (${getPercentLabel(
											(Number(subtaskScoreCompleted) /
												Number(subtaskScore)) *
												100
										)}) `}
									</span>
								</span>
							),
							size: 'sm',
							value: i18n.translate('score'),
						},
						{
							key: 'progress',
							render: (
								_,
								{
									subtaskScoreCompleted,
									subtaskScoreSelfIncomplete,
								}: TestrayTask
							) => (
								<TaskbarProgress
									displayTotalCompleted={false}
									items={[
										[
											StatusesProgressScore.OTHER,
											Number(subtaskScoreCompleted),
										],
										[
											StatusesProgressScore.INCOMPLETE,
											Number(subtaskScoreSelfIncomplete),
										],
									]}
									taskbarClassNames={chartClassNames}
								/>
							),
							size: 'md',
							value: i18n.translate('progress'),
						},
						{
							key: 'users',
							render: (users: UserAccount[]) => (
								<Avatar.Group
									assignedUsers={users.map(
										({image, name}) => ({
											name,
											url: image,
										})
									)}
									groupSize={5}
								/>
							),
							value: i18n.translate('assigned-users'),
						},
					],
					navigateTo: (task) => `/testflow/${task.id}`,
					rowWrap: true,
				}}
				transformData={(response) =>
					testrayTaskImpl.transformDataFromList(response)
				}
				variables={{
					filter: taskFilter,
				}}
			/>

			<TestflowModal modal={modal} />
		</Container>
	);
};

export default TestFlow;
