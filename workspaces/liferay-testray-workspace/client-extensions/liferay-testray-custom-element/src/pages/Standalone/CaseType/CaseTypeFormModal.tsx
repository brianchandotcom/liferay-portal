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
import {testrayCaseTypeImpl} from '../../../services/rest';

type CaseTypeForm = {
	id?: number;
	name: string;
};

type CaseTypeProps = {
	modal: FormModalOptions;
};

const CaseTypeFormModal: React.FC<CaseTypeProps> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
}) => {
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
	} = useForm<CaseTypeForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.caseType),
	});

	const _onSubmit = (form: CaseTypeForm) =>
		onSubmit(
			{id: form.id, name: form.name},
			{
				create: (...params) => testrayCaseTypeImpl.create(...params),
				update: (...params) => testrayCaseTypeImpl.update(...params),
			}
		)
			.then(onSave)
			.catch(onError);

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
				modalState?.id ? 'edit-case-type' : 'new-case-type'
			)}
			visible
		>
			<Form.Input
				label={i18n.translate('name')}
				name="name"
				{...inputProps}
			/>
		</Modal>
	);
};

export default withVisibleContent(CaseTypeFormModal);
