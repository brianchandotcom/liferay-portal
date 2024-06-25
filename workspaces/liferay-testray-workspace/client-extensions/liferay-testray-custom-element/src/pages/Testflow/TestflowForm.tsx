/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {useAtom} from 'jotai';
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {withPagePermission} from '~/hoc/withPagePermission';
import {taskSidebarRefresh} from '~/hooks/useSidebarTask';

import Form from '../../components/Form';
import Container from '../../components/Layout/Container';
import SearchBuilder from '../../core/SearchBuilder';
import {useHeader} from '../../hooks';
import useFormActions from '../../hooks/useFormActions';
import useFormModal from '../../hooks/useFormModal';
import i18n from '../../i18n';
import yupSchema, {yupResolver} from '../../schema/yup';
import {Liferay} from '../../services/liferay';
import {
	APIResponse,
	TestrayTask,
	TestrayTaskCaseTypes,
	TestrayTaskUser,
	testrayTaskImpl,
	testrayTaskUsersImpl,
} from '../../services/rest';
import {TaskStatuses} from '../../util/statuses';
import {UserListView} from '../Manage/User';
import useTestFlowAssign from './TestflowFormAssignUserActions';
import TestflowAssignUserModal, {TestflowAssigUserType} from './modal';

type TestflowFormType = typeof yupSchema.task.__outputType;

type OutletContext = {
	data: {
		testrayTask: TestrayTask;
		testrayTaskCaseTypes: TestrayTaskCaseTypes[];
		testrayTaskUser: TestrayTaskUser[];
	};
	mutate: {
		mutateTask: KeyedMutator<TestrayTask>;
		mutateTaskUser: KeyedMutator<APIResponse<TestrayTaskUser>>;
	};
	revalidate: {revalidateTaskUser: () => void};
};

const TestflowForm = () => {
	const {
		form: {onClose, onError, onSubmit, onSuccess},
	} = useFormActions();

	const [modalType, setModalType] =
		useState<TestflowAssigUserType>('select-users');
	const [userIds, setUserIds] = useState<number[]>([]);
	const {modal} = useFormModal({
		onSave: setUserIds,
	});
	const {buildId, taskId} = useParams();
	const {actions} = useTestFlowAssign({setUserIds});
	const navigate = useNavigate();
	const [, setTaskSidebarRefresh] = useAtom(taskSidebarRefresh);

	const outletContext = useOutletContext<OutletContext>();

	const {setHeading} = useHeader({timeout: 210});

	const {
		data: {testrayTask = undefined, testrayTaskUser = undefined} = {},
		mutate: {mutateTask = () => null} = {mutateTask: undefined},
		revalidate: {revalidateTaskUser = () => null} = {
			revalidateTaskUser: undefined,
		},
	} = outletContext;

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
	} = useForm<TestflowFormType>({
		defaultValues: {
			buildId: Number(testrayTask?.build?.id ?? buildId),
			dueStatus: TaskStatuses.IN_ANALYSIS,
			id: Number(taskId ?? 0),
			name: testrayTask?.name,
			userIds: [],
		},
		resolver: yupResolver(yupSchema.task),
	});

	const onOpenModal = (option: 'select-users' | 'select-user-groups') => {
		setModalType(option);

		modal.open(userIds);
	};

	const _onSubmit = async (form: TestflowFormType) => {
		let hasError = false;

		if (!form.userIds?.length) {
			hasError = true;

			Liferay.Util.openToast({
				message: i18n.translate(
					'mark-at-last-one-user-or-user-group-for-assignment'
				),
				type: 'danger',
			});
		}

		if (hasError) {
			return;
		}

		try {
			const response = await onSubmit(form, {
				create: (data) => testrayTaskImpl.create(data),
				update: (id, data) => testrayTaskImpl.update(id, data),
			});

			await testrayTaskUsersImpl.assign(response.id, userIds);

			if (form.id) {
				mutateTask(response);

				revalidateTaskUser();
			}

			setTaskSidebarRefresh(new Date().getTime());

			onSuccess();

			navigate(`/testflow/${response.id}`);
		}
		catch (error) {
			onError(error);
		}
	};

	const inputProps = {
		errors,
		register,
	};

	useEffect(() => {
		setHeading(
			[
				{
					category: i18n.translate('task'),
					title: i18n.translate('testflow'),
				},
			],
			true
		);
	}, [setHeading]);

	useEffect(() => {
		if (testrayTaskUser) {
			setUserIds(testrayTaskUser.map(({user}) => user?.id as number));
		}
	}, [setUserIds, testrayTaskUser]);

	useEffect(() => {
		setValue('userIds', userIds);
	}, [setValue, userIds]);

	return (
		<Container>
			<ClayInput.GroupItem shrink>
				<Form.Input
					{...inputProps}
					label={i18n.translate('name')}
					name="name"
					required
					size={100}
				/>
			</ClayInput.GroupItem>

			<label className="mb-2 mt-4 required">
				<h5>{i18n.translate('users')}</h5>
			</label>
			<Form.Divider />

			<Form.Clay.Group>
				<ClayButton
					displayType="secondary"
					onClick={() => onOpenModal('select-users')}
				>
					{i18n.translate('assign-users')}
				</ClayButton>

				<ClayButton
					className="ml-2"
					displayType="secondary"
					onClick={() => onOpenModal('select-user-groups')}
				>
					{i18n.translate('assign-user-groups')}
				</ClayButton>
			</Form.Clay.Group>

			{!userIds.length && (
				<ClayAlert>
					{i18n.translate('there-are-no-linked-users')}
				</ClayAlert>
			)}

			{!!userIds.length && (
				<UserListView
					actions={actions}
					listViewProps={{
						managementToolbarProps: {
							visible: false,
						},
						variables: {filter: SearchBuilder.in('id', userIds)},
					}}
				/>
			)}

			<Form.Divider />

			<Form.Footer
				onClose={() => onClose()}
				onSubmit={handleSubmit(_onSubmit)}
				primaryButtonProps={{loading: isSubmitting}}
			/>

			<TestflowAssignUserModal modal={modal} type={modalType} />
		</Container>
	);
};

export default withPagePermission(TestflowForm, {
	createPath:
		'/project/:projectId/routines/:routinesId/build/:buildId/testflow/create',
	restImpl: testrayTaskImpl,
});
