/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React, {useMemo} from 'react';
import {useParams} from 'react-router-dom';
import {STORAGE_KEYS} from '~/core/Storage';
import {CONSENT_TYPE} from '~/util/enum';

import EmptyState from '../../../components/EmptyState';
import Container from '../../../components/Layout/Container';
import MarkdownPreview from '../../../components/Markdown';
import QATable from '../../../components/Table/QATable';
import SearchBuilder from '../../../core/SearchBuilder';
import {useFetch} from '../../../hooks/useFetch';
import useStorage from '../../../hooks/useStorage';
import i18n from '../../../i18n';
import {
	APIResponse,
	TestrayCase,
	TestrayRequirement,
	TestrayRequirementCase,
	testrayCaseImpl,
	testrayCaseRequirementsImpl,
} from '../../../services/rest';
import dayjs from '../../../util/date';

type CaseWithRequirement = {
	[key: number]: TestrayRequirement[];
};

type CaseItemsProps = {
	caseWithRequirements: CaseWithRequirement;
	cases: TestrayCase[];
};

type RequirementTableProps = {
	requirements: TestrayRequirement[];
};

const RequirementTable: React.FC<RequirementTableProps> = ({requirements}) => (
	<ClayTable>
		<ClayTable.Head>
			<ClayTable.Row>
				<ClayTable.Cell expanded headingCell>
					{i18n.translate('key')}
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					{i18n.translate('link')}
				</ClayTable.Cell>

				<ClayTable.Cell headingCell>
					{i18n.translate('summary')}
				</ClayTable.Cell>
			</ClayTable.Row>
		</ClayTable.Head>

		<ClayTable.Body>
			{requirements.map((requirement, index) => (
				<ClayTable.Row key={index}>
					<ClayTable.Cell headingTitle>
						{requirement?.key}
					</ClayTable.Cell>

					<ClayTable.Cell>
						<a
							className="cursor-pointer"
							onClick={() =>
								window.open(requirement?.linkURL, '_blank')
							}
						>
							{requirement?.linkURL}
						</a>
					</ClayTable.Cell>

					<ClayTable.Cell>{requirement?.summary}</ClayTable.Cell>
				</ClayTable.Row>
			))}
		</ClayTable.Body>
	</ClayTable>
);

const CaseItems: React.FC<CaseItemsProps> = ({caseWithRequirements, cases}) => (
	<div>
		<h5>{i18n.translate('case')}</h5>

		{cases?.map((Case, index) => {
			const requirements = caseWithRequirements[Case?.id] || [];

			return (
				<div className="mt-3" key={index}>
					<Container>
						<h5>{Case.name}</h5>

						<QATable
							items={[
								{
									title: i18n.translate('project-name'),
									value: Case?.project?.name,
								},

								{
									title: i18n.translate('type'),
									value: Case?.caseType?.name,
								},
								{
									title: i18n.translate('priority'),
									value: Case?.priority,
								},
								{
									title: i18n.translate('team'),
									value: Case?.component?.team?.name,
								},
								{
									title: i18n.translate('main-component'),
									value: Case?.component?.name,
								},
								{
									title: i18n.translate('description'),
									value: (
										<MarkdownPreview
											markdown={Case.description}
										/>
									),
								},
								{
									title: i18n.translate('estimated-duration'),
									value: Case?.estimatedDuration,
								},
								{
									title: i18n.translate('steps'),
									value: Case?.steps,
								},
								{
									title: i18n.translate('date-last-modified'),
									value: dayjs(Case?.dateModified).format(
										'lll'
									),
								},
								{
									title: i18n.translate('all-issues-found'),
									value: Case?.name,
								},
								{
									title: i18n.translate('requirements'),
									value: requirements?.length && (
										<RequirementTable
											requirements={requirements}
										/>
									),
								},
							]}
						/>
					</Container>
				</div>
			);
		})}
	</div>
);

const ExportCaseContainer: React.FC<CaseItemsProps> = ({
	caseWithRequirements,
	cases,
}) => (
	<div>
		<h5 className="mt-5">{i18n.translate('associated-requirements')}</h5>

		{cases.map((Case: TestrayCase) => {
			const requirements = caseWithRequirements[Case?.id] || [];

			return requirements.map((requirement) => (
				<div className="mt-3" key={requirement.key}>
					<Container>
						<h5>{requirement.key}</h5>

						<QATable
							items={[
								{
									title: i18n.translate('link'),
									value: (
										<a
											className="cursor-pointer"
											onClick={() =>
												window.open(
													requirement?.linkURL,
													'_blank'
												)
											}
										>
											{requirement?.linkURL}
										</a>
									),
								},
								{
									title: i18n.translate('team'),
									value: requirement?.component?.team?.name,
								},
								{
									title: i18n.translate('component'),
									value: requirement?.component?.name,
								},
								{
									title: i18n.translate('jira-components'),
									value: requirement?.component?.team?.name,
								},
								{
									title: i18n.translate('summary'),
									value: requirement?.summary,
								},
								{
									title: i18n.translate('description'),
									value: (
										<MarkdownPreview
											markdown={requirement?.description}
										/>
									),
								},
							]}
						/>
					</Container>
				</div>
			));
		})}
	</div>
);

const Export = () => {
	const {id} = useParams();

	const [caseIds] = useStorage(
		`${STORAGE_KEYS.EXPORT_CASE_IDS}-${id}` as STORAGE_KEYS,
		{
			consentType: CONSENT_TYPE.NECESSARY,
			initialValue: [],
			storageType: 'persisted',
		}
	);

	const {data: casesData, loading} = useFetch<APIResponse<TestrayCase>>(
		'/cases',
		{
			params: {
				filter: SearchBuilder.in('id', caseIds),
				nestedFields: 'caseType,component,project,team',
				nestedFieldsDepth: 3,
				pageSize: 1000,
			},
			swrConfig: {shouldFetch: caseIds.length},
			transformData: (response) =>
				testrayCaseImpl.transformDataFromList(response),
		}
	);

	const {data: requirementCasesData} = useFetch<
		APIResponse<TestrayRequirementCase>
	>('/requirementscaseses', {
		params: {
			filter: SearchBuilder.in('caseId', caseIds),
			nestedFields: 'case.component,requirement,team',
			nestedFieldsDepth: 3,
			pageSize: 1000,
		},
		swrConfig: {
			shouldFetch: !loading,
		},
		transformData: (response) =>
			testrayCaseRequirementsImpl.transformDataFromList(response),
	});

	const cases = casesData?.items || [];

	const casesWithRequirements = useMemo(() => {
		const requirementCases = requirementCasesData?.items || [];
		const casesWithRequirement: CaseWithRequirement = {};

		requirementCases.forEach((requirementCase) => {
			const caseId = requirementCase.case?.id;
			const requirement = requirementCase?.requirement;

			if (!caseId || !requirement) {
				return;
			}

			if (requirement && casesWithRequirement[caseId]) {
				casesWithRequirement[caseId].push(requirement);
			}
			else {
				casesWithRequirement[caseId] = [requirement];
			}
		});

		return casesWithRequirement;
	}, [requirementCasesData]);

	if (!caseIds?.length) {
		return <EmptyState />;
	}

	return (
		<div className="tr-export-case">
			<div>
				<CaseItems
					caseWithRequirements={casesWithRequirements}
					cases={cases}
				/>

				{cases?.length && (
					<ExportCaseContainer
						caseWithRequirements={casesWithRequirements}
						cases={cases}
					/>
				)}
			</div>
		</div>
	);
};

export default Export;
