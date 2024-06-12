/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams, useSearchParams} from 'react-router-dom';
import {withPagePermission} from '~/hoc/withPagePermission';
import {ACTIONS} from '~/util/constants';
import {BuildStatuses} from '~/util/statuses';

import Form from '../../../../../components/Form';
import Container from '../../../../../components/Layout/Container';
import SearchBuilder from '../../../../../core/SearchBuilder';
import {useHeader} from '../../../../../hooks';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormActions from '../../../../../hooks/useFormActions';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema, {yupResolver} from '../../../../../schema/yup';
import {Liferay} from '../../../../../services/liferay';
import {
	APIResponse,
	TestrayBuild,
	TestrayOptionsByCategory,
	TestrayProductVersion,
	TestrayRoutine,
	TestrayRun,
	testrayBuildImpl,
	testrayRunImpl,
} from '../../../../../services/rest';
import ProductVersionFormModal from '../../../../Standalone/ProductVersions/ProductVersionFormModal';
import BuildFormCases from './BuildFormCases';
import BuildFormRun, {BuildFormType} from './BuildFormRun';
import BuildFormStacks from './BuildFormStacks';
import useGetRunsData from './hooks/useGetRunsData';

import type {KeyedMutator} from 'swr';

type OutletContext = {
	mutateBuild: KeyedMutator<any>;
	testrayBuild?: TestrayBuild;
};

export const MAX_BUILD_CASES = 500;

const BuildForm = () => {
	const [caseIds, setCaseIds] = useState<number[]>([]);
	const [fetchingNewCaseIds, setFetchingNewCaseIds] = useState(false);
	const [totalCases, setTotalCases] = useState(0);
	const {buildId, buildTemplateId, projectId, routineId} = useParams();

	const [runOptionsList, setRunOptionsList] = useState<
		TestrayOptionsByCategory[]
	>([[] as any]);

	const action = buildId ? ACTIONS.UPDATE : ACTIONS.CREATE;

	const [searchParams] = useSearchParams();

	const buildTemplate = searchParams.get(`template`);

	useEffect(() => {
		if (buildId) {
			testrayBuildImpl
				.getCurrentCaseIds(buildId)
				.then(setCaseIds)
				.catch(console.error);
		}

		if (buildTemplateId) {
			testrayBuildImpl
				.getCurrentCaseIds(buildTemplateId)
				.then(setCaseIds)
				.catch(console.error);
		}
	}, [buildId, buildTemplateId]);

	const {data: productVersionsData, mutate} = useFetch<
		APIResponse<TestrayProductVersion>
	>(
		`/productversions?fields=id,name&filter=${SearchBuilder.eq(
			'projectId',
			projectId as string
		)}&sort=name:asc`
	);

	const {data: routinesData} = useFetch<APIResponse<TestrayRoutine>>(
		`/routines?fields=id,name&filter=${SearchBuilder.eq(
			'projectId',
			projectId as string
		)}`
	);

	const {modal: newProductVersionModal} = useFormModal({
		onSave: (produtVersion: TestrayProductVersion) => {
			mutate((productVersionResponse) => {
				if (!productVersionResponse) {
					return;
				}

				return {
					...productVersionResponse,
					items: [...productVersionResponse?.items, produtVersion],
				};
			});
		},
	});

	const {mutateBuild, testrayBuild}: OutletContext = useOutletContext();

	const {
		form: {onClose, onError, onSave, onSubmit, submitting},
	} = useFormActions();

	const {
		control,
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<BuildFormType>({
		defaultValues: testrayBuild
			? {
					description: testrayBuild.description,
					factorStacks: [{}],
					gitHash: testrayBuild.gitHash,
					name: testrayBuild.name,
					productVersionId: String(testrayBuild.productVersion?.id),
					projectId: Number(projectId),
					routineId: String(testrayBuild.routine?.id || routineId),
					runOptions: [],
					template: testrayBuild.template,
					templateTestrayBuildId: buildTemplateId ?? '',
				}
			: {
					dueStatus: BuildStatuses.ACTIVATED,
					factorStacks: [{}],
					projectId: Number(projectId),
					routineId,
					runOptions: [],
					template: false,
					templateTestrayBuildId: buildTemplateId ?? '',
				},
		resolver: yupResolver(
			buildTemplate ? yupSchema.buildTemplate : yupSchema.build
		),
	});

	const {loading: loadingRuns, runItems} = useGetRunsData(
		buildId,
		setValue,
		setRunOptionsList
	);

	useHeader({
		tabs: [],
		timeout: 150,
	});

	const productVersionId = watch('productVersionId');
	const productVersions = productVersionsData?.items || [];
	const routines = routinesData?.items || [];

	const inputProps = {
		errors,
		register,
	};

	if (buildTemplate) {
		setValue('template', true);
	}

	const _onSubmit = async (data: BuildFormType) => {
		const runsIds = data.runOptions.map((run: TestrayRun) => run.runId);

		const runsIdsToRemove = runItems
			?.filter((run: TestrayRun) => !runsIds.includes(run.id))
			.map((run: TestrayRun) => run.id);

		if (runsIdsToRemove?.length) {
			await testrayRunImpl.removeBatch(
				runsIdsToRemove.map((id: number) => id)
			);
		}

		if (action === ACTIONS.CREATE) {
			const hasFactorStacks = data.factorStacks.some((factorStack: any) =>
				Object.keys(factorStack).some(
					(key) => !!Object.keys(factorStack[key]).length
				)
			);

			if (!hasFactorStacks) {
				return Liferay.Util.openToast({
					message: i18n.translate(
						'at-least-one-environment-stack-is-required'
					),
					type: 'danger',
				});
			}
		}
		else {
			if (!data.runOptions) {
				return Liferay.Util.openToast({
					message: i18n.translate('at-least-one-run-is-required'),
					type: 'danger',
				});
			}
		}

		data.caseIds = caseIds;
		if (testrayBuild) {
			data.id = testrayBuild.id.toString();
		}
		const response = await onSubmit(data, {
			create: (data) => testrayBuildImpl.create(data),
			update: (id, data) =>
				testrayBuildImpl.updateBuild(id, data, runItems),
		})
			.then(onSave)
			.catch(onError);
		if (testrayBuild) {
			mutateBuild(response);
		}
	};

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
				<Form.Input
					{...inputProps}
					label={i18n.translate('name')}
					name="name"
					required
				/>

				<Form.Select
					{...inputProps}
					defaultOption={false}
					label="routine"
					name="routineId"
					options={routines.map(({id: value, name: label}) => ({
						label,
						value,
					}))}
				/>

				{!buildTemplate && (
					<div className="row">
						<div className="col-md-6">
							<Form.Select
								{...inputProps}
								label="product-version"
								name="productVersionId"
								options={productVersions.map(
									({id: value, name: label}) => ({
										label,
										value,
									})
								)}
								required
								value={productVersionId}
							/>
						</div>

						<ClayButtonWithIcon
							aria-label={i18n.sub('add-x', 'product-version')}
							className="mt-5"
							displayType="primary"
							onClick={() => newProductVersionModal.open()}
							symbol="plus"
							title={i18n.sub('add-x', 'product-version')}
						/>
					</div>
				)}

				<Form.Input
					{...inputProps}
					label={i18n.translate('git-hash')}
					name="gitHash"
				/>

				<Form.Input
					{...inputProps}
					label={i18n.translate('description')}
					name="description"
					type="textarea"
				/>

				{action === ACTIONS.UPDATE && (
					<BuildFormRun
						action={action}
						control={control}
						loadingRuns={loadingRuns}
						register={register}
						runItems={runItems}
						runOptionsList={runOptionsList}
					/>
				)}

				{action === ACTIONS.CREATE && (
					<BuildFormStacks
						action={action}
						control={control}
						register={register}
					/>
				)}

				<BuildFormCases
					buildId={buildId}
					caseIds={caseIds}
					setCaseIds={setCaseIds}
					setCaseIdsLoading={setFetchingNewCaseIds}
					setTotalCases={setTotalCases}
					title={i18n.translate('cases')}
					totalCases={totalCases}
				/>

				<div className="mt-4">
					<Form.Footer
						onClose={onClose}
						onSubmit={handleSubmit(_onSubmit)}
						primaryButtonProps={{
							disabled: totalCases > MAX_BUILD_CASES,
							loading: submitting || fetchingNewCaseIds,
						}}
					/>
				</div>
			</ClayForm>

			<ProductVersionFormModal
				modal={newProductVersionModal}
				projectId={projectId as unknown as number}
			/>
		</Container>
	);
};

export default withPagePermission(BuildForm, {
	createPath: 'project/:projectId/routines/:routineId/create',
	restImpl: testrayBuildImpl,
});
