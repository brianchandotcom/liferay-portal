/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useMemo} from 'react';
import {TestraySuite, TestraySuiteCase} from '~/services/rest';

import i18n from '../../../../i18n';
import SuitesCasesTable from '../../Suites/SuiteCasesTable';

type SelectSuitesCasesProps = {
	displayTitle?: boolean;
	selectedCaseIds?: number[];
	setState: any;
	testraySuite: TestraySuite;
};

const SelectSuitesCases: React.FC<SelectSuitesCasesProps> = ({
	displayTitle = true,
	selectedCaseIds = [],
	setState,
	testraySuite,
}) => {
	const isSmartSuite = useMemo(
		() => !!testraySuite.caseParameters,
		[testraySuite.caseParameters]
	);

	return (
		<SuitesCasesTable
			isSmartSuite={isSmartSuite}
			listViewProps={{
				initialContext: {selectedRows: selectedCaseIds},
				managementToolbarProps: {
					filterSchema: 'cases',
					title: displayTitle ? i18n.translate('cases') : '',
				},
				normalizers: {
					...(!isSmartSuite && {
						onSelectRow: (data) => {
							if (Array.isArray(data)) {
								return data;
							}

							return data.r_caseToSuitesCases_c_caseId;
						},
					}),
				},
				onContextChange: ({selectedRows}) => setState(selectedRows),
				tableProps: {
					columns: [
						{
							key: 'priority',
							render: (_, suiteCase: TestraySuiteCase) =>
								suiteCase?.case?.priority,
							value: i18n.translate('priority'),
						},
						{
							key: 'component',
							render: (_, suiteCase: TestraySuiteCase) =>
								suiteCase?.case?.component?.name,
							value: i18n.translate('component'),
						},
						{
							key: 'name',
							render: (_, suiteCase: TestraySuiteCase) =>
								suiteCase.case?.name,
							size: 'lg',
							value: i18n.translate('case'),
						},
					],
					rowSelectable: true,
				},
			}}
			testraySuite={testraySuite}
		/>
	);
};

export default SelectSuitesCases;
