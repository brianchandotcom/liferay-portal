/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useAtomValue} from 'jotai';

import {APIResponse, TestraySubtask, TestrayTask} from '../../services/rest';
import {SubtaskStatuses} from '../../util/statuses';
import {useFetch} from '../useFetch';
import {taskSidebarRefresh} from '../useSidebarTask';

const useSubtaskScore = ({
	testrayTask,
	userId,
}: {
	testrayTask: TestrayTask;
	userId: number;
}) => {
	const refresh = useAtomValue(taskSidebarRefresh);

	const progressScore = {
		completed: testrayTask?.subtaskScoreCompleted,
		incomplete: testrayTask?.subtaskScoreSelfIncomplete,
		othersCompleted: 0,
		selfCompleted: 0,
	};

	const {data: testraySubtasks} = useFetch<APIResponse<TestraySubtask>>(
		`/testray-testflow/testray-subtask?testrayTaskId=${testrayTask?.id}&t=${refresh}`,
		{
			params: {
				pageSize: -1,
			},
		}
	);

	for (const subtask of testraySubtasks?.items ?? []) {
		if (subtask?.status !== SubtaskStatuses.COMPLETE) {
			continue;
		}

		const property =
			subtask.userId === userId ? 'selfCompleted' : 'othersCompleted';

		progressScore[property] += subtask.score;
	}

	return progressScore;
};

export default useSubtaskScore;
