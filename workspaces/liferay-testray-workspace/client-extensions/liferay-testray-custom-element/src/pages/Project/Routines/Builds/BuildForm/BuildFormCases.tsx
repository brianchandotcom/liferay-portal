/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Dispatch, SetStateAction} from 'react';
import {useParams} from 'react-router-dom';

import {MAX_BUILD_CASES} from '.';
import Form from '../../../../../components/Form';
import SearchBuilder from '../../../../../core/SearchBuilder';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import {APIResponse, TestrayCase} from '../../../../../services/rest';
import {CaseListView} from '../../../Cases';
import SelectCasesModal from '../../../Suites/modal';
import BuildSelectSuitesModal from '../BuildSelectSuitesModal';
import useSuitesCasesHandler from '../useSuitesCasesHandler';

type BuildFormCasesProps = {
	buildId?: string;
	caseIds: number[];
	setCaseIds: Dispatch<SetStateAction<number[]>>;
	setCaseIdsLoading: Dispatch<SetStateAction<boolean>>;
	setTotalCases: Dispatch<SetStateAction<number>>;
	title?: string;
	totalCases: number;
};

const BuildFormCases: React.FC<BuildFormCasesProps> = ({
	buildId,
	caseIds,
	setCaseIds,
	setCaseIdsLoading,
	setTotalCases,
	title,
	totalCases,
}) => {
	const {projectId} = useParams();

	const {data: casesResponse} = useFetch<APIResponse<TestrayCase>>('/cases', {
		params: {
			fields: 'id',
			filter: SearchBuilder.eq('projectId', projectId as string),
			pageSize: 1,
		},
	});

	const {filter, setModalContext, suiteIds, suitesCasesHandler} =
		useSuitesCasesHandler(
			buildId,
			caseIds,
			setCaseIds,
			setCaseIdsLoading,
			setTotalCases
		);

	const {modal} = useFormModal<number[]>({
		onSave: suitesCasesHandler,
	});

	const {modal: buildSelectSuitesModal} = useFormModal({
		onSave: suitesCasesHandler,
	});

	if (casesResponse?.totalCount === 0) {
		return (
			<ClayAlert>
				{i18n.translate(
					'create-cases-if-you-want-to-link-cases-to-this-build'
				)}
			</ClayAlert>
		);
	}

	return (
		<>
			{title && (
				<>
					<h3>{title}</h3>

					<Form.Divider />
				</>
			)}

			<ClayButton.Group className="mb-4">
				<ClayButton
					displayType="secondary"
					onClick={() => {
						setModalContext('cases');
						modal.open(caseIds);
					}}
				>
					{i18n.translate('add-cases')}
				</ClayButton>

				<ClayButton
					className="ml-1"
					displayType="secondary"
					onClick={() => {
						setModalContext('suites');
						buildSelectSuitesModal.open(caseIds);
					}}
				>
					{i18n.translate('add-suites')}
				</ClayButton>
			</ClayButton.Group>

			{filter ? (
				<CaseListView
					listViewProps={{
						managementToolbarProps: {visible: false},
						tableProps: {
							actions: [
								{
									action: ({id}: TestrayCase) =>
										setCaseIds((prevCases) =>
											prevCases.filter(
												(prevCase: number) =>
													prevCase !== id
											)
										),
									icon: 'trash',
									name: i18n.translate('delete'),
								},
							] as any,
							columns: [
								{
									key: 'priority',
									size: 'lg',
									value: i18n.translate('priority'),
								},
								{
									key: 'component',
									render: (component) => component?.name,
									value: i18n.translate('component'),
								},
								{
									key: 'name',
									size: 'md',
									value: i18n.translate('name'),
								},
							],
						},
						variables: {
							filter,
						},
					}}
				/>
			) : (
				<ClayAlert>
					{i18n.translate('there-are-no-linked-cases')}
				</ClayAlert>
			)}

			{totalCases > MAX_BUILD_CASES && (
				<ClayAlert>
					{i18n.sub(
						'only-x-cases-can-be-added',
						MAX_BUILD_CASES.toString()
					)}
				</ClayAlert>
			)}

			<BuildSelectSuitesModal
				modal={buildSelectSuitesModal}
				setModalContext={setModalContext}
				suiteIds={suiteIds}
			/>

			<SelectCasesModal
				modal={modal}
				selectedCaseIds={caseIds}
				type="select-cases"
			/>
		</>
	);
};

export default BuildFormCases;
