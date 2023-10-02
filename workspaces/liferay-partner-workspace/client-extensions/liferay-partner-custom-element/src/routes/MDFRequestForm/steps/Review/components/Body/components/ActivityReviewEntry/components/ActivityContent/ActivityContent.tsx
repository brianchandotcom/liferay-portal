/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Table from '../../../../../../../../../../common/components/Table';
import MDFRequestActivity from '../../../../../../../../../../common/interfaces/mdfRequestActivity';
import getBooleanValidation from '../../../../../../../../../../common/utils/getBooleanValidation';
import getBooleanValue from '../../../../../../../../../../common/utils/getBooleanValue';
import getIntlNumberFormat from '../../../../../../../../../../common/utils/getIntlNumberFormat';

interface IProps {
	mdfRequestActivity: MDFRequestActivity;
}

const ActivityContent = ({mdfRequestActivity}: IProps) => {
	const leadList = [
		{
			title: 'Is a lead list an outcome of this activity?',
			value: getBooleanValue(
				mdfRequestActivity?.activityDescription?.leadGenerated as string
			),
		},
	];

	if (
		getBooleanValidation(
			mdfRequestActivity?.activityDescription?.leadGenerated as string
		)
	) {
		leadList.push(
			{
				title: 'Target # of Leads',
				value: mdfRequestActivity?.activityDescription
					?.targetOfLeads as string,
			},
			{
				title: 'Lead Follow Up strategy',
				value: mdfRequestActivity?.activityDescription?.leadFollowUpStrategies?.join(
					', '
				) as string,
			},
			{
				title: 'Details on Lead Follow Up',
				value: mdfRequestActivity?.activityDescription
					?.detailsLeadFollowUp as string,
			}
		);
	}

	return (
		<>
			<Table
				className="bg-brand-primary-lighten-6 border-top table-striped"
				columns={[
					{
						columnKey: 'title',
						label: 'Budget Breakdown',
					},
					{
						columnKey: 'value',
						label: '',
					},
				]}
				rows={mdfRequestActivity.budgets
					.filter((budget) => !budget.removed)
					.map((budget) => ({
						title: budget.expense.name,
						value: getIntlNumberFormat(
							mdfRequestActivity.currency
						).format(budget.cost),
					}))}
			/>

			<Table
				className="bg-brand-primary-lighten-6 border-top table-striped"
				columns={[
					{
						columnKey: 'title',
						label: 'Lead List',
					},
					{
						columnKey: 'value',
						label: '',
					},
				]}
				rows={leadList}
			/>
		</>
	);
};
export default ActivityContent;
