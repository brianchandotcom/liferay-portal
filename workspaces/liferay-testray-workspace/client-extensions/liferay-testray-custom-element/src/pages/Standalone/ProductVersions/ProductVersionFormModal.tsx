/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {testrayProductVersionImpl} from '../../../services/rest';

type ProductVersionForm = typeof yupSchema.productVersion.__outputType;

type ProductVersionProps = {
	modal: FormModalOptions;
	projectId: number;
};

const ProductVersionFormModal: React.FC<ProductVersionProps> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
	projectId,
}) => {
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
	} = useForm<ProductVersionForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.productVersion),
	});

	const _onSubmit = (productVersionForm: ProductVersionForm) =>
		onSubmit(
			{
				id: productVersionForm.id,
				name: productVersionForm.name,
				projectId,
			},
			{
				create: (data) => testrayProductVersionImpl.create(data),
				update: (id, data) =>
					testrayProductVersionImpl.update(id, data),
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
			title={i18n.sub(
				modalState?.id ? 'edit-x' : 'new-x',
				'product-version'
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
		</Modal>
	);
};

export default withVisibleContent(ProductVersionFormModal);
