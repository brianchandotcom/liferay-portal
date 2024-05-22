/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, useState} from 'react';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import JiraLink from '~/components/JiraLink';
import {getTruncateText} from '~/util/getTruncateText';

import FloatingBox from '../../../components/FloatingBox';
import ListView from '../../../components/ListView';
import StatusBadge from '../../../components/StatusBadge';
import {StatusBadgeType} from '../../../components/StatusBadge/StatusBadge';
import {ListViewTypes} from '../../../context/ListViewContext';
import SearchBuilder from '../../../core/SearchBuilder';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay';
import {
	PickList,
	TestrayCaseResult,
	TestraySubtask,
	TestraySubtaskCaseResult,
	testraySubtaskImpl,
} from '../../../services/rest';
import {testraySubtaskCaseResultImpl} from '../../../services/rest/TestraySubtaskCaseResults';
import {SubtaskStatuses} from '../../../util/statuses';

type SubtasksCaseResultsProps = {
	forceRefetch: number;
};

type OutletContext = {
	data: {
		buildId: string;
		projectId: string;
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

	const {
		data: {buildId, projectId, testraySubtask},
		mutate: {mutateSubtask},
	} = useOutletContext<OutletContext>();

	const getFloatingBoxAlerts = (
		subtasksCaseResults: TestraySubtaskCaseResult[],
		selectRows: number[]
	) => {
		const alerts = [];

		if (subtasksCaseResults.length === selectRows.length) {
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
		mutate: KeyedMutator<TestraySubtaskCaseResult>,
		selectedCaseResults: TestraySubtaskCaseResult[]
	) => {
		setIsLoading(true);
		const {currentSubtask, newSubtask} = await testraySubtaskImpl.split(
			selectedCaseResults,
			Number(subtaskId),
			Number(taskId)
		);

		mutateSubtask(currentSubtask);

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
					navigate(`../../subtasks/${newSubtask.id}`);
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
			resource={testraySubtaskCaseResultImpl.resource}
			tableProps={{
				columns: [
					{
						clickable: true,
						key: 'run',
						render: (_, caseResult: TestrayCaseResult) =>
							caseResult.run?.number?.toString().padStart(2, '0'),
						value: i18n.translate('run'),
					},
					{
						clickable: true,
						key: 'priority',
						render: (_, {case: testrayCase}: TestrayCaseResult) =>
							testrayCase?.priority,
						value: i18n.translate('priority'),
					},
					{
						clickable: true,
						key: 'team',
						render: (_, testrayCaseResult: TestrayCaseResult) =>
							testrayCaseResult.case?.component?.team?.name,
						value: i18n.translate('team'),
					},
					{
						clickable: true,
						key: 'component',
						render: (_, {case: testrayCase}: TestrayCaseResult) =>
							testrayCase?.component?.name,
						value: i18n.translate('component'),
					},
					{
						clickable: true,
						key: 'name',
						render: (_, {case: testrayCase}: TestrayCaseResult) =>
							testrayCase?.name,
						size: 'xl',
						value: i18n.translate('case'),
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
						key: 'dueStatus',
						render: (dueStatus: PickList) => (
							<StatusBadge
								type={dueStatus?.key as StatusBadgeType}
							>
								{dueStatus?.name}
							</StatusBadge>
						),
						value: i18n.translate('status'),
					},
					{
						clickable: true,
						key: 'comment',
						render: (value) => getTruncateText(value),
						size: 'lg',
						value: i18n.translate('comment'),
					},
				],
				navigateTo: (caseResult) =>
					`/project/${caseResult.build?.project?.id}/routines/${caseResult.build?.routine.id}/build/${caseResult.build?.id}/case-result/${caseResult.id}`,
				rowSelectable: true,
				rowWrap: true,
			}}
			transformData={(response) =>
				testraySubtaskCaseResultImpl.transformDataFromList(response)
			}
			variables={{
				filter: SearchBuilder.eq('subtaskId', subtaskId as string),
			}}
		>
			{({items}, {dispatch, listViewContext: {selectedRows}, mutate}) => {
				const alerts = getFloatingBoxAlerts(items, selectedRows);

				const selectedCaseResults: TestraySubtaskCaseResult[] = selectedRows.map(
					(rowId) => items.find(({id}) => rowId === id)
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
								selectedCaseResults
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
