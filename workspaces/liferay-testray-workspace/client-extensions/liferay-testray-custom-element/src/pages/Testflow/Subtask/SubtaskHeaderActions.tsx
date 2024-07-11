/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useAtom} from 'jotai';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {taskSidebarRefresh} from '~/hooks/useSidebarTask';

import AssignModal from '../../../components/AssignModal';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {Liferay} from '../../../services/liferay';
import {TestraySubtask, UserAccount} from '../../../services/rest';
import {testraySubtaskImpl} from '../../../services/rest/TestraySubtask';
import {SubtaskStatuses} from '../../../util/statuses';
import SubtaskCompleteModal from './SubtaskCompleteModal';

type SubtaskHeaderActionsProps = {
	setForceRefetch: React.Dispatch<React.SetStateAction<number>>;
};

type OutletContext = {
	data: {
		testraySubtask: TestraySubtask;
	};
	mutate: {
		mutateSubtask: KeyedMutator<TestraySubtask>;
	};
	revalidate: {
		revalidateSubtask: () => void;
	};
};

const SubtaskHeaderActions: React.FC<SubtaskHeaderActionsProps> = ({
	setForceRefetch,
}) => {
	const [, setTaskSidebarRefresh] = useAtom(taskSidebarRefresh);
	const {
		data: {testraySubtask},
		mutate: {mutateSubtask},
		revalidate: {revalidateSubtask},
	} = useOutletContext<OutletContext>();
	const {modal: assignUserModal} = useFormModal({
		onSave: (user: UserAccount) =>
			testraySubtaskImpl
				.assignTo(testraySubtask, user.id)
				.then(mutateSubtask)
				.then(() => {
					setTaskSidebarRefresh(new Date().getTime());
					setForceRefetch(new Date().getTime());
				}),
	});

	const {modal: completeModal} = useFormModal();

	return (
		<>
			<AssignModal modal={assignUserModal} />

			<SubtaskCompleteModal
				modal={completeModal}
				revalidateSubtask={revalidateSubtask}
				setForceRefetch={setForceRefetch}
				subtask={testraySubtask}
			/>

			{[SubtaskStatuses.COMPLETE, SubtaskStatuses.OPEN].includes(
				testraySubtask.dueStatus?.key as SubtaskStatuses
			) ? (
				<ClayButton
					className="mb-3 ml-3"
					displayType="secondary"
					onClick={() => assignUserModal.open()}
				>
					{i18n.translate(
						testraySubtask.dueStatus?.key === SubtaskStatuses.OPEN
							? 'assign-and-begin-analysis'
							: 'assign-and-reanalyze'
					)}
				</ClayButton>
			) : (
				<ClayButton.Group className="mb-3 ml-3" spaced>
					<ClayButton
						displayType="secondary"
						onClick={() => assignUserModal.open()}
					>
						{i18n.translate('assign')}
					</ClayButton>

					<ClayButton
						onClick={() => {
							if (
								testraySubtask.user.id ===
								Number(Liferay.ThemeDisplay.getUserId())
							) {
								return completeModal.open();
							}

							Liferay.Util.openToast({
								message: i18n.translate(
									'you-are-not-the-assigned-user'
								),
								type: 'danger',
							});
						}}
					>
						{i18n.translate('complete')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() =>
							testraySubtaskImpl
								.returnToOpen(testraySubtask)
								.then(mutateSubtask)
								.then(() => {
									setTaskSidebarRefresh(new Date().getTime());
									setForceRefetch(new Date().getTime());
								})
						}
					>
						{i18n.translate('return-to-open')}
					</ClayButton>
				</ClayButton.Group>
			)}
		</>
	);
};

export default SubtaskHeaderActions;
