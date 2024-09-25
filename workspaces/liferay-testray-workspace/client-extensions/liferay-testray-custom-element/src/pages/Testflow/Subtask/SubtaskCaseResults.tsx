/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useAtom} from 'jotai';
import {Dispatch, useState} from 'react';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import JiraLink from '~/components/JiraLink';
import {useFetch} from '~/hooks/useFetch';
import {taskSidebarRefresh} from '~/hooks/useSidebarTask';

import FloatingBox from '../../../components/FloatingBox';
import ListView from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import {ListViewTypes} from '../../../context/ListViewContext';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay';
import {
	APIResponse,
	TestrayCaseResult,
	TestraySubtask,
	testraySubtaskImpl,
} from '../../../services/rest';
import {testraySubtaskCaseResultImpl} from '../../../services/rest/TestraySubtaskCaseResults';
import {SubtaskStatuses} from '../../../util/statuses';

let totalItems: TestrayCaseResult[] = [];

type SubtasksCaseResultsProps = {
	forceRefetch: number;
};

type OutletContext = {
	data: {
		buildId: string;
		projectId: string;
		routineId: string;
		testraySubtask: TestraySubtask;
	};
	mutate: {
		mutateSubtask: KeyedMutator<TestraySubtask>;
	};
};

const SubtasksCaseResults: React.FC<SubtasksCaseResultsProps> = ({
	forceRefetch,
}) => {
	const navigate = useNavigate();
	const {subtaskId, taskId} = useParams();
	const {updateItemFromList} = useMutate();
	const [isLoading, setIsLoading] = useState(false);
	const [, setTaskSidebarRefresh] = useAtom(taskSidebarRefresh);

	const {data: response} = useFetch<APIResponse<TestrayCaseResult>>(
		`/subtasks/${subtaskId}/subtaskToCaseResults?pageSize=1&fields=id`
	);

	const {
		data: {buildId, projectId, routineId, testraySubtask},
		mutate: {mutateSubtask},
	} = useOutletContext<OutletContext>();

	const getFloatingBoxAlerts = (selectRows: number[]) => {
		const alerts = [];

		if (selectRows.length === response?.totalCount) {
			alerts.push({
				text: i18n.translate(
					'you-cannot-split-all-case-results-from-a-subtask'
				),
			});
		}

		const subtaskStatusCheck = () => {
			if (testraySubtask.dueStatus?.key !== SubtaskStatuses.IN_ANALYSIS) {
				return [
					{
						text: i18n.sub(
							'subtask-x-must-be-in-analysis-to-be-used-in-a-split',
							testraySubtask?.name as string
						),
					},
				];
			}
		};

		const subtaskUserCheck = () => {
			const subtasksWithDifferentAssignedUsers =
				testraySubtask?.user?.id?.toString() !==
					Liferay.ThemeDisplay.getUserId() ||
				!testraySubtask?.user?.id;

			if (subtasksWithDifferentAssignedUsers) {
				return [
					{
						text: i18n.sub(
							'subtask-x-must-be-assigned-to-you-to-be-user-in-a-split',
							testraySubtask?.name ?? ''
						),
					},
				];
			}
		};

		const alreadyAssigned = subtaskUserCheck() || [];
		const statusOpen = subtaskStatusCheck() || [];

		return [...alerts, ...alreadyAssigned, ...statusOpen];
	};

	const onSplitSubtasks = async (
		dispatch: Dispatch<any>,
		mutate: KeyedMutator<TestrayCaseResult>,
		selectedCaseResults: TestrayCaseResult[]
	) => {
		setIsLoading(true);
		const {currentSubtask, newSubtask} = await testraySubtaskImpl.split(
			selectedCaseResults,
			testraySubtask,
			Number(subtaskId),
			Number(taskId)
		);

		totalItems = [];

		mutateSubtask(currentSubtask);

		updateItemFromList(
			mutate,
			0,
			{},
			{
				revalidate: true,
			}
		);

		setTaskSidebarRefresh(new Date().getTime());

		dispatch({
			payload: [],
			type: ListViewTypes.SET_CLEAR_CHECKED_ROW,
		});

		setIsLoading(false);

		Liferay.Util.openToast({
			message: i18n.sub('x-tests-were-split-into-x-successfully-view-x', [
				selectedCaseResults.length.toString(),
				newSubtask.name,
				newSubtask.name,
			]),
			onClick: ({event}) => {
				const {target} = event;

				if (target?.id === 'testray-link') {
					navigate(`../subtasks/${newSubtask.id}`);
				}
			},
		});
	};

	return (
		<ListView
			forceRefetch={forceRefetch}
			managementToolbarProps={{
				applyFilters: true,
				customFilterFields: {
					buildId,
					projectId,
				},
				filterSchema: 'subtaskCaseResults',
			}}
			resource={`/testray-case-result/${buildId}?testraySubtaskId=${subtaskId}`}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'flaky',
						render: (_, {flaky, testrayCaseName}) => (
							<>
								{flaky && (
									<ClayTooltipProvider>
										<span
											className="tr-table__row__flaky-icon"
											data-tooltip-align="top"
											title={i18n.translate(
												'this-is-a-possible-flaky-test'
											)}
										>
											<ClayIcon symbol="flag-full" />
										</span>
									</ClayTooltipProvider>
								)}
								{testrayCaseName}
							</>
						),
						size: 'md',
						value: i18n.translate('case'),
						width: '350',
					},
					{
						clickable: true,
						key: 'testrayRunNumber',
						render: (testrayRunNumber) =>
							testrayRunNumber?.toString().padStart(2, '0'),
						value: i18n.translate('run'),
					},
					{
						clickable: true,
						key: 'priority',
						value: i18n.translate('priority'),
					},
					{
						clickable: true,
						key: 'testrayTeamName',
						value: i18n.translate('team'),
					},
					{
						clickable: true,
						key: 'testrayComponentName',
						value: i18n.translate('component'),
					},
					{
						key: 'issues',
						render: (issues: string) => (
							<JiraLink
								displayViewInJira={false}
								issue={issues}
							/>
						),
						value: i18n.translate('issues'),
					},
					{
						clickable: true,
						key: 'status',
						render: (dueStatus) => (
							<StatusBadge type={dueStatus as StatusBadgeType}>
								{dueStatus}
							</StatusBadge>
						),
						value: i18n.translate('status'),
					},
					{
						clickable: true,
						key: 'comment',
						size: 'lg',
						value: i18n.translate('comment'),
					},
				],
				navigateTo: ({testrayCaseResultId}) =>
					`/project/${projectId}/routines/${routineId}/build/${buildId}/case-result/${testrayCaseResultId}`,
				rowSelectable: true,
				rowWrap: true,
			}}
			transformData={(response) =>
				testraySubtaskCaseResultImpl.transformDataFromList(response)
			}
		>
			{({items}, {dispatch, listViewContext: {selectedRows}, mutate}) => {
				const alerts = getFloatingBoxAlerts(selectedRows);

				totalItems = totalItems.concat(
					items.filter(
						(pageItem) =>
							!totalItems.some((item) => item.id === pageItem.id)
					)
				);

				const selectedCaseResults = selectedRows.map((rowId) =>
					totalItems.find(
						({testrayCaseResultId}) => rowId === testrayCaseResultId
					)
				);

				return (
					<FloatingBox
						alerts={alerts}
						clearList={() =>
							dispatch({
								payload: [],
								type: ListViewTypes.SET_CLEAR_CHECKED_ROW,
							})
						}
						isVisible={!!selectedRows.length}
						onSubmit={() =>
							onSplitSubtasks(
								dispatch,
								mutate,
								selectedCaseResults as TestrayCaseResult[]
							)
						}
						primaryButtonProps={{
							disabled: !!alerts.length && isLoading,
							loading: isLoading,
							title: i18n.translate('split-tests'),
						}}
						selectedCount={selectedRows.length}
						tooltipText={i18n.translate(
							'move-selected-tests-to-a-new-subtask'
						)}
					/>
				);
			}}
		</ListView>
	);
};

export default SubtasksCaseResults;
