/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Control, UseFormRegister, useFieldArray} from 'react-hook-form';
import {useParams} from 'react-router-dom';
import Loading from '~/components/Loading';

import Form from '../../../../../components/Form';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema from '../../../../../schema/yup';
import {
	TestrayOptionsByCategory,
	TestrayRun,
} from '../../../../../services/rest';
import FactorOptionsFormModal from '../../../../Standalone/FactorOptions/FactorOptionsFormModal';
import StackList from './Stack';
import useGetFactorsData from './hooks/useGetFactorsData';

export type BuildFormType = typeof yupSchema.build.__outputType;

type BuildFormRunProps = {
	action: string;
	control: Control<any>;
	loadingRuns: boolean;
	register: UseFormRegister<BuildFormType>;
	runItems: TestrayRun[];
	runOptionsList: TestrayOptionsByCategory[];
};

const BuildFormRun: React.FC<BuildFormRunProps> = ({
	action,
	control,
	loadingRuns,
	register,
	runItems,
	runOptionsList,
}) => {
	const {modal: optionModal} = useFormModal();
	const {routineId} = useParams();

	const {append, fields, remove, update} = useFieldArray({
		control,
		name: 'runOptions',
	});

	const {factorItems} = useGetFactorsData(update, routineId);

	if (loadingRuns) {
		return <Loading />;
	}

	return (
		<>
			<h3>{i18n.translate('runs')}</h3>

			<Form.Divider />

			{!runItems.length && (
				<ClayAlert>
					{i18n.translate(
						'create-environment-factors-if-you-want-to-generate-runs'
					)}
				</ClayAlert>
			)}

			{!!runItems.length && (
				<>
					<ClayButton.Group className="mb-4">
						<ClayButton
							displayType="secondary"
							onClick={() => optionModal.open()}
						>
							{i18n.translate('add-option')}
						</ClayButton>
					</ClayButton.Group>

					<StackList
						action={action}
						append={append as any}
						factorItems={factorItems}
						fields={fields}
						optionsList={runOptionsList}
						register={register}
						remove={remove}
						update={update as any}
					/>
				</>
			)}

			<FactorOptionsFormModal modal={optionModal} />
		</>
	);
};

export default BuildFormRun;
