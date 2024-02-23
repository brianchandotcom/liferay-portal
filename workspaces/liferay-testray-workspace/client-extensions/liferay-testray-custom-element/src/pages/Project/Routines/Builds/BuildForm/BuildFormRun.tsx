/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useState} from 'react';
import {UseFormRegister, useFieldArray, useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';
import Loading from '~/components/Loading';
import {CURRENT_BUILD_PAGE} from '~/util/constants';

import Form from '../../../../../components/Form';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema from '../../../../../schema/yup';
import {
	TestrayFactorOption,
	TestrayOptionsByCategory,
} from '../../../../../services/rest';
import FactorOptionsFormModal from '../../../../Standalone/FactorOptions/FactorOptionsFormModal';
import BuildSelectStacksModal, {FactorStack} from './BuildSelectStacksModal';
import StackList from './Stack';
import useGetFactorsData from './hooks/useGetFactorsData';
import useGetRunsData from './hooks/useGetRunsData';

export type BuildFormType = typeof yupSchema.build.__outputType;

type BuildFormRunProps = {
	register: UseFormRegister<BuildFormType>;
};

const BuildFormRun: React.FC<BuildFormRunProps> = ({register}) => {
	const {modal: optionModal} = useFormModal();
	const {buildId, routineId} = useParams();
	const {control} = useForm({});

	const operation = buildId
		? CURRENT_BUILD_PAGE.UPDATE
		: CURRENT_BUILD_PAGE.CREATE;

	const {append, fields, remove, update} = useFieldArray({
		control,
		name: 'factorStacks',
	});

	const [runOptionsList, setRunOptionsList] = useState<
		TestrayOptionsByCategory[]
	>([[] as any]);

	const [factorOptionsList, setFactorOptionsList] = useState<
		TestrayFactorOption[][]
	>([[] as any]);

	const {modal: optionSelectModal} = useFormModal({
		onSave: (factorStacks: FactorStack[]) => {
			for (const factor of factorStacks) {
				append({...factor, disabled: false});
			}
		},
	});

	const {factorItems, loading: loadingFactors} = useGetFactorsData(
		setFactorOptionsList,
		update,
		routineId
	);

	const {loading: loadingRuns, runItems} = useGetRunsData(
		setRunOptionsList,
		update,
		buildId
	);

	if (loadingRuns || loadingFactors) {
		return <Loading />;
	}

	return (
		<>
			<h3>{i18n.translate('runs')}</h3>

			<Form.Divider />

			{!runItems.length && !factorItems.length && (
				<ClayAlert>
					{i18n.translate(
						'create-environment-factors-if-you-want-to-generate-runs'
					)}
				</ClayAlert>
			)}

			{(!!runItems.length || !!factorItems.length) && (
				<>
					<ClayButton.Group className="mb-4">
						<ClayButton
							displayType="secondary"
							onClick={() => optionModal.open()}
						>
							{i18n.translate('add-option')}
						</ClayButton>
						{operation === CURRENT_BUILD_PAGE.CREATE && (
							<ClayButton
								className="ml-1"
								displayType="secondary"
								onClick={() => optionSelectModal.open()}
							>
								{i18n.translate('select-stacks')}
							</ClayButton>
						)}
					</ClayButton.Group>

					<StackList
						append={append as any}
						factorItems={factorItems}
						fields={fields}
						operation={operation}
						optionsList={
							operation === CURRENT_BUILD_PAGE.CREATE
								? factorOptionsList
								: runOptionsList
						}
						register={register}
						remove={remove}
						update={update as any}
					/>
				</>
			)}

			<FactorOptionsFormModal modal={optionModal} />

			<BuildSelectStacksModal
				factorItems={factorItems}
				modal={optionSelectModal}
			/>
		</>
	);
};

export default BuildFormRun;
