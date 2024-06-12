/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useForm} from 'react-hook-form';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {withPagePermission} from '~/hoc/withPagePermission';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	TestrayProject,
	TestrayRoutine,
	testrayRoutineImpl,
} from '../../../services/rest';

type RoutineForm = typeof yupSchema.routine.__outputType;

type OutletContext = {
	mutateTestrayRoutine: KeyedMutator<TestrayRoutine>;
	testrayProject: TestrayProject;
	testrayRoutine?: TestrayRoutine;
};

const RoutineForm = () => {
	useHeader({headerActions: {actions: []}, tabs: [], timeout: 150});

	const {mutateTestrayRoutine, testrayProject, testrayRoutine} =
		useOutletContext<OutletContext>();

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<RoutineForm>({
		defaultValues: {autoanalyze: false, ...testrayRoutine},
		resolver: yupResolver(yupSchema.routine),
	});

	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	const _onSubmit = (form: RoutineForm) =>
		onSubmit(
			{
				...form,
				projectId: testrayProject.id,
			},
			{
				create: (...params) => testrayRoutineImpl.create(...params),
				update: (...params) => testrayRoutineImpl.update(...params),
			}
		)
			.then(mutateTestrayRoutine)
			.then(onSave)
			.catch(onError);

	const autoanalyze = watch('autoanalyze');

	return (
		<Container className="container">
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>

			<ClayCheckbox
				checked={autoanalyze}
				className="d-flex"
				label={i18n.translate('autoanalyze')}
				onChange={() => setValue('autoanalyze', !autoanalyze)}
			>
				<ClayTooltipProvider>
					<span
						data-tooltip-floating="true"
						title={i18n.translate(
							'allow-testray-to-automatically-populate-assignee-comments-and-issues-when-an-error-matches-the-previous-result'
						)}
					>
						<ClayIcon
							className="align-bottom ml-2"
							data-tooltip-align="left"
							symbol="question-circle-full"
						/>
					</span>
				</ClayTooltipProvider>
			</ClayCheckbox>

			<Form.Footer
				onClose={onClose}
				onSubmit={handleSubmit(_onSubmit)}
				primaryButtonProps={{loading: isSubmitting}}
			/>
		</Container>
	);
};

export default withPagePermission(RoutineForm, {
	createPath: 'project/:projectId/routines/create',
	restImpl: testrayRoutineImpl,
});
