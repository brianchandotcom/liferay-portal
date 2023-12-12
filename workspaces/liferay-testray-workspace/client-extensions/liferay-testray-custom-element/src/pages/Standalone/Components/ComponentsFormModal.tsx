/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import SearchBuilder from '../../../core/SearchBuilder';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {useFetch} from '../../../hooks/useFetch';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	APIResponse,
	TestrayTeam,
	testrayComponentImpl,
} from '../../../services/rest';

type ComponentForm = typeof yupSchema.component.__outputType;

type ComponentProps = {
	modal: FormModalOptions;
	projectId: number;
};

const ComponentFormModal: React.FC<ComponentProps> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
	projectId,
}) => {
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		watch,
	} = useForm<ComponentForm>({
		defaultValues: modalState
			? {
					id: modalState.id,
					name: modalState.name,
					teamId: modalState.team?.id,
			  }
			: {
					teamId: '',
			  },
		resolver: yupResolver(yupSchema.component),
	});

	const {data: teamsResponse} = useFetch<APIResponse<TestrayTeam>>(`/teams`, {
		params: {
			fields: 'id,name',
			filter: SearchBuilder.eq('projectId', projectId),
			pageSize: 100,
			sort: 'name:asc',
		},
	});

	const teamId = watch('teamId');
	const teams = teamsResponse?.items || [];

	const _onSubmit = (componentForm: ComponentForm) =>
		onSubmit(
			{
				...componentForm,
				projectId,
			},
			{
				create: (data) => testrayComponentImpl.create(data),
				update: (id, data) => testrayComponentImpl.update(id, data),
			}
		)
			.then(onSave)
			.catch(onError);

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: isSubmitting}}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.translate(
				modalState?.id ? 'edit-component' : 'new-component'
			)}
			visible
		>
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>

			<Form.Select
				errors={errors}
				label={i18n.translate('team')}
				name="teamId"
				options={teams.map(({id, name}) => ({label: name, value: id}))}
				register={register}
				value={teamId}
			/>
		</Modal>
	);
};

export default withVisibleContent(ComponentFormModal);
