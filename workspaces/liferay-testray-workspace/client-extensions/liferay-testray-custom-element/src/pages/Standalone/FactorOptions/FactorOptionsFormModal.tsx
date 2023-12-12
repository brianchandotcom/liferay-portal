/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {useFetch} from '../../../hooks/useFetch';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	APIResponse,
	TestrayFactorCategory,
	testrayFactorCategoryRest,
	testrayFactorOptionsImpl,
} from '../../../services/rest';

type FactorOptionsForm = {
	factorCategoryId: string;
	id?: number;
	name: string;
};

type FactorOptionsProps = {
	modal: FormModalOptions;
};

const FactorOptionsFormModal: React.FC<FactorOptionsProps> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
}) => {
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		watch,
	} = useForm<FactorOptionsForm>({
		defaultValues: modalState
			? {
					factorCategoryId: modalState?.factorCategory?.id,
					id: modalState.id,
					name: modalState.name,
			  }
			: {},
		resolver: yupResolver(yupSchema.factorOption),
	});

	const {data} = useFetch<APIResponse<TestrayFactorCategory>>(
		'/factorcategories',
		{
			transformData: (response) =>
				testrayFactorCategoryRest.transformDataFromList(response),
		}
	);

	const factorCategories = data?.items || [];

	const _onSubmit = (form: FactorOptionsForm) =>
		onSubmit(form, {
			create: (data) => testrayFactorOptionsImpl.create(data),
			update: (id, data) => testrayFactorOptionsImpl.update(id, data),
		})
			.then(onSave)
			.catch(onError);

	const factorCategoryId = watch('factorCategoryId');

	const name = watch('name');

	const inputProps = {
		errors,
		register,
		required: true,
	};

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
				modalState?.id ? 'edit-option' : 'new-option'
			)}
			visible
		>
			<Form.Input
				label={i18n.translate('name')}
				name="name"
				{...inputProps}
				value={name}
			/>

			<Form.Select
				{...inputProps}
				forceSelectOption
				label={i18n.translate('type')}
				name="factorCategoryId"
				options={factorCategories.map(({id: value, name: label}) => ({
					label,
					value,
				}))}
				value={factorCategoryId}
			/>
		</Modal>
	);
};

export default withVisibleContent(FactorOptionsFormModal);
