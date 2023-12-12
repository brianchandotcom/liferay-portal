/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Container from '../../../../../components/Layout/Container';
import ListView from '../../../../../components/ListView';
import ProgressBar from '../../../../../components/ProgressBar';
import i18n from '../../../../../i18n';

const CaseTypes = () => (
	<Container className="mt-4">
		<ListView
			initialContext={{
				columns: {
					blocked: false,
					in_progress: false,
					test_fix: false,
					untested: false,
				},
				columnsFixed: ['name'],
			}}
			managementToolbarProps={{
				filterSchema: 'buildCaseTypes',
				title: i18n.translate('case-types'),
			}}
			resource="/casetypes"
			tableProps={{
				columns: [
					{
						key: 'name',
						size: 'md',
						value: i18n.translate('test-type'),
					},
					{
						clickable: true,
						key: 'total',
						render: () => 0,
						value: i18n.translate('total'),
					},
					{
						clickable: true,
						key: 'failed',
						render: () => 0,
						value: i18n.translate('failed'),
					},
					{
						clickable: true,
						key: 'blocked',
						render: () => 0,
						value: i18n.translate('blocked'),
					},
					{
						clickable: true,
						key: 'untested',
						render: () => 0,
						value: i18n.translate('untested'),
					},
					{
						clickable: true,
						key: 'in_progress',
						render: () => 0,
						value: i18n.translate('in-progress'),
					},
					{
						clickable: true,
						key: 'passed',
						render: () => 0,
						value: i18n.translate('passed'),
					},
					{
						clickable: true,
						key: 'test_fix',
						render: () => 0,
						value: i18n.translate('test-fix'),
					},
					{
						clickable: true,
						key: 'metrics',
						render: () => (
							<ProgressBar
								items={{
									blocked: 0,
									failed: 2,
									incomplete: 0,
									passed: 30,
									test_fix: 0,
								}}
							/>
						),
						size: 'sm',
						value: i18n.translate('metrics'),
					},
				],
			}}
		/>
	</Container>
);

export default CaseTypes;
