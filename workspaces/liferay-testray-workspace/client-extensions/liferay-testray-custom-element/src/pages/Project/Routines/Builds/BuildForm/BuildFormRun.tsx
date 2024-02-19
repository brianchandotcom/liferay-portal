/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useEffect, useMemo, useState} from 'react';
import {UseFormRegister, useFieldArray, useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';
import Loading from '~/components/Loading';

import Form from '../../../../../components/Form';
import SearchBuilder from '../../../../../core/SearchBuilder';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema from '../../../../../schema/yup';
import {
	APIResponse,
	TestrayFactorCategory,
	TestrayOptionsByCategory,
	TestrayRun,
	testrayFactorCategoryRest,
	testrayRunImpl,
} from '../../../../../services/rest';
import FactorOptionsFormModal from '../../../../Standalone/FactorOptions/FactorOptionsFormModal';
import StackList from './Stack';

export type BuildFormType = typeof yupSchema.build.__outputType;

type BuildFormRunProps = {
	register: UseFormRegister<BuildFormType>;
};

const BuildFormRun: React.FC<BuildFormRunProps> = ({register}) => {
	const {modal: optionModal} = useFormModal();
	const {buildId} = useParams();
	const {control} = useForm({});

	const {append, fields, remove, update} = useFieldArray({
		control,
		name: 'runOptions',
	});

	const [runOptionsList, setRunOptionsList] = useState<
		TestrayOptionsByCategory[]
	>([[] as any]);

	const {data: runsData, loading} = useFetch<APIResponse<TestrayRun>>(
		testrayRunImpl.resource,
		{
			params: {
				filter: SearchBuilder.eq('buildId', buildId as string),
				pageSize: 100,
			},
			transformData: (response) =>
				testrayRunImpl.transformDataFromList(response),
		}
	);

	const runItems = useMemo(() => runsData?.items || [], [runsData?.items]);

	const {data: categories} = useFetch<APIResponse<TestrayFactorCategory>>(
		testrayFactorCategoryRest.resource,
		{
			transformData: (response) =>
				testrayFactorCategoryRest.transformDataFromList(response),
		}
	);

	const categoryItems = useMemo(() => categories?.items || [], [
		categories?.items,
	]);

	useEffect(() => {
		if (categoryItems.length) {
			testrayFactorCategoryRest
				.getOptionsByCategoryItems(categoryItems)
				.then(setRunOptionsList)
				.catch(console.error);
		}
	}, [categoryItems]);

	useEffect(() => {
		runItems.forEach((item, index) => {
			update(index, {
				applicationServer: item?.applicationServer as string,
				browser: item?.browser as string,
				database: item?.database as string,
				javaJDK: item?.javaJDK as string,
				operatingSystem: item?.operatingSystem as string,
			});
		});
	}, [update, runItems]);

	if (loading) {
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

			{!!runItems.length && !loading && (
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
						append={append as any}
						fields={fields}
						register={register}
						remove={remove}
						runOptionsList={runOptionsList}
						update={update as any}
					/>
				</>
			)}

			<FactorOptionsFormModal modal={optionModal} />
		</>
	);
};

export default BuildFormRun;
