/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {FormikContextType} from 'formik';
import {useState} from 'react';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import LiferayFile from '../../../../../../../../common/interfaces/liferayFile';
import MDFClaim from '../../../../../../../../common/interfaces/mdfClaim';
import MDFClaimBudget from '../../../../../../../../common/interfaces/mdfClaimBudget';
import deleteDocument from '../../../../../../../../common/services/liferay/headless-delivery/deleteDocument';
import PanelBody from '../PanelBody';
import PanelHeader from '../PanelHeader';

interface IProps {
	activityIndex: number;
	budget: MDFClaimBudget;
	budgetIndex: number;
}

const BudgetClaimPanel = ({
	activityIndex,
	budget,
	budgetIndex,
	setFieldValue,
}: IProps & Pick<FormikContextType<MDFClaim>, 'setFieldValue'>) => {
	const [expanded, setExpanded] = useState<boolean>(!budget.selected);
	const budgetFieldName = `activities[${activityIndex}].budgets[${budgetIndex}]`;

	return (
		<ClayPanel
			className="bg-white border-neutral-4"
			displayType="secondary"
			expanded={budget.selected && expanded}
		>
			<PanelHeader
				expanded={budget.selected && expanded}
				onClick={() => {
					if (budget.selected) {
						setExpanded((previousExpanded) => !previousExpanded);
					}
				}}
			>
				<div className="d-flex">
					<PRMFormik.Field
						component={PRMForm.Checkbox}
						name={`${budgetFieldName}.selected`}
					/>

					<h5 className="mb-0 ml-3 text-neutral-10">
						{budget.expenseName}
					</h5>
				</div>
			</PanelHeader>

			<PanelBody expanded={budget.selected && expanded}>
				<ClayPanel.Body>
					<div>
						<PRMFormik.Field
							component={PRMForm.InputCurrency}
							description="Silver Partner can claim up to 50%"
							label="Invoice Amount"
							name={`${budgetFieldName}.invoiceAmount`}
							onAccept={(liferayFile: LiferayFile) => {
								setFieldValue(
									`${budgetFieldName}.invoiceAmount`,
									liferayFile
								);
							}}
							required={budget.selected}
						/>

						<PRMFormik.Field
							component={PRMForm.InputFile}
							displayType="secondary"
							label="Third Party Invoice"
							name={`${budgetFieldName}.invoice`}
							onAccept={(liferayFile: LiferayFile) => {
								if (budget.invoice?.documentId) {
									deleteDocument(budget.invoice?.documentId);
								}

								setFieldValue(
									`${budgetFieldName}.invoice`,
									liferayFile
								);
							}}
							outline
							required={budget.selected}
							small
						/>
					</div>
				</ClayPanel.Body>
			</PanelBody>
		</ClayPanel>
	);
};

export default BudgetClaimPanel;
