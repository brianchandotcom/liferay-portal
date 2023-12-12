/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {Link} from 'react-router-dom';

import {useSidebarTask} from '../../../hooks/useSidebarTask';
import i18n from '../../../i18n';
import {TestraySubTask} from '../../../services/rest';
import {StatusesProgressScore, chartClassNames} from '../../../util/constants';
import TaskbarProgress from '../../ProgressBar/TaskbarProgress';
import Tooltip from '../../Tooltip';

type TaskBadgeProps = {
	className?: string;
	count: number;
};

type TaskSidebarProps = {
	expanded: boolean;
};

type SubtaskCardProps = {
	subtask: {
		id: number;
		name: string;
		score: number;
	};
	taskId: number;
};

const TaskBadge: React.FC<TaskBadgeProps> = ({className, count}) => (
	<span className={classNames(className)}>{count}</span>
);

const SubtaskCard: React.FC<SubtaskCardProps> = ({subtask, taskId}) => (
	<Link
		className="tr-task-sidebar__subtask-item__subtask-card"
		to={`testflow/${taskId}/subtasks/${subtask?.id}`}
	>
		<div className="col-4">
			<span>{subtask.name}</span>
		</div>

		<div className="col-8 d-flex justify-content-between overflow-hidden">
			<span className="mr-2">{i18n.translate('score')}</span>

			<span>{subtask.score}</span>
		</div>
	</Link>
);

const TaskSidebar: React.FC<TaskSidebarProps> = ({expanded}) => {
	const {tasks} = useSidebarTask();

	if (!tasks.length) {
		return null;
	}

	return (
		<div
			className={classNames('tr-task-sidebar', {
				'tr-task-sidebar--hidden': !expanded,
			})}
		>
			<div
				className={classNames('tr-task-sidebar__title', {
					'tr-task-sidebar__title--expanded': expanded,
				})}
			>
				<span className={classNames('tr-task-sidebar__title__text')}>
					{i18n.translate('tasks')}
				</span>

				<TaskBadge
					className={classNames('tr-task-sidebar__quantity-badge')}
					count={tasks.length}
				/>
			</div>

			<Tooltip position="right" title={i18n.translate('tasks')}>
				<div
					className={classNames('tr-task-sidebar__notification', {
						'tr-task-sidebar__notification--hidden': !expanded,
					})}
				>
					<ClayIcon fontSize={20} symbol="blogs" />

					{!!tasks.length && (
						<span className="tr-task-sidebar__notification--badge">
							{tasks.length}
						</span>
					)}
				</div>
			</Tooltip>

			<ul>
				{tasks.map((task, index) => (
					<li
						className={classNames('tr-task-sidebar__subtask-item', {
							'tr-task-sidebar__subtask-item--expanded': expanded,
						})}
						key={index}
					>
						<Tooltip position="right" title={task?.name}>
							<Link to={`testflow/${task?.id}`}>
								<p className="tr-task-sidebar__ellipsis-text">
									<TaskBadge
										className="tr-task-sidebar__quantity-badge"
										count={task?.subTasks?.length as number}
									/>

									<span className="ml-2">{task?.name}</span>
								</p>
							</Link>
						</Tooltip>

						<Tooltip position="right" title={task?.build?.name}>
							<Link
								className="mt-3"
								to={`/project/${task?.build?.project?.id}/routines/${task?.build?.routine?.id}/build/${task?.build?.id}`}
							>
								<p className="tr-task-sidebar__ellipsis-text">
									{task?.build?.name}
								</p>
							</Link>
						</Tooltip>

						<div className="tr-task-sidebar__subtask-item--expanded__progress-bar">
							<TaskbarProgress
								displayTotalCompleted
								items={[
									[StatusesProgressScore.SELF, 0],
									[
										StatusesProgressScore.OTHER,
										Number(
											task?.subtaskScoreCompleted ?? 0
										),
									],
									[
										StatusesProgressScore.INCOMPLETE,
										Number(
											task?.subtaskScoreSelfIncomplete ??
												0
										),
									],
								]}
								taskbarClassNames={chartClassNames}
							/>
						</div>

						{task?.subTasks?.map(
							(subtask: TestraySubTask, index) => (
								<SubtaskCard
									key={index}
									subtask={{
										id: subtask?.id,
										name: subtask?.name,
										score: subtask.score,
									}}
									taskId={task.id as number}
								/>
							)
						)}
					</li>
				))}
			</ul>
		</div>
	);
};

export default TaskSidebar;
