/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import {BoxItem} from '../../../components/Form/DualListBox';
import Container from '../../../components/Layout/Container';
import QATable from '../../../components/Table/QATable';
import i18n from '../../../i18n';
import {TestraySuite} from '../../../services/rest';
import dayjs from '../../../util/date';
import SuitesCasesTable from './SuiteCasesTable';
import {getCaseParameters} from './useSuiteCaseFilter';

const Suite = () => {
	const {testraySuite}: {testraySuite: TestraySuite} = useOutletContext();

	const isSmartSuite = !!testraySuite.caseParameters;

	const caseParameters = getCaseParameters(testraySuite);

	const getCaseParameterKey = (caseParameter: BoxItem[]) =>
		caseParameter?.map(({label}) => label).join(', ');

	return (
		<>
			<Container collapsable title={i18n.translate('details')}>
				<QATable
					items={[
						{
							title: i18n.translate('description'),
							value: testraySuite?.description,
						},
						{
							title: i18n.translate('create-date'),
							value: dayjs(testraySuite?.dateCreated).format(
								'lll'
							),
						},
						{
							title: i18n.translate('date-last-modified'),
							value: dayjs(testraySuite?.dateModified).format(
								'lll'
							),
						},
						{
							title: i18n.translate('created-by'),
							value: testraySuite.creator.name,
						},
					]}
				/>
			</Container>

			{testraySuite.caseParameters && (
				<Container
					className="mt-4"
					collapsable
					title={i18n.translate('case-parameters')}
				>
					<QATable
						items={[
							{
								title: i18n.translate('case-types'),
								value: getCaseParameterKey(
									caseParameters.testrayCaseTypes
								),
							},
							{
								title: i18n.translate('components'),
								value: getCaseParameterKey(
									caseParameters.testrayComponents
								),
							},
							{
								title: i18n.translate('subcomponents'),
								value: getCaseParameterKey(
									caseParameters.testraySubComponents
								),
							},
							{
								title: i18n.translate('priority'),
								value: getCaseParameterKey(
									caseParameters.testrayPriorities
								),
							},
							{
								title: i18n.translate('requirements'),
								value: getCaseParameterKey(
									caseParameters.testrayRequirements
								),
							},
						]}
					/>
				</Container>
			)}

			<Container className="mt-4">
				<SuitesCasesTable
					isSmartSuite={isSmartSuite}
					testraySuite={testraySuite}
				/>
			</Container>
		</>
	);
};

export default Suite;
