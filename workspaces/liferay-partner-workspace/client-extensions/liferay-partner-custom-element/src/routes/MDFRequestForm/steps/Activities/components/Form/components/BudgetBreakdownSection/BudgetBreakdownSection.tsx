/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ArrayHelpers} from 'formik';
import React, {useCallback} from 'react';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import ResumeCard from '../../../../../../../../common/components/ResumeCard';
import LiferayPicklist from '../../../../../../../../common/interfaces/liferayPicklist';
import MDFRequestBudget from '../../../../../../../../common/interfaces/mdfRequestBudget';
import getIntlNumberFormat from '../../../../../../../../common/utils/getIntlNumberFormat';
import getPicklistOptions from '../../../../../../../../common/utils/getPicklistOptions';
import useBudgetsAmount from './hooks/useBudgetsAmount';
import getNewBudget from './utils/getNewBudget';

interface IProps {
	arrayHelpers: ArrayHelpers;
	budgets: MDFRequestBudget[];
	claimPercent: number;
	currency: LiferayPicklist;
	currentActivityIndex: number;
	expenseEntries: React.OptionHTMLAttributes<HTMLOptionElement>[];
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean | undefined
	) => void;
}

const BudgetBreakdownSection = ({
	arrayHelpers,
	budgets = [],
	claimPercent,
	currency,
	currentActivityIndex,
	expenseEntries,
	setFieldValue,
}: IProps) => {
	const {
		onSelected: onExpenseSelected,
		options: expensesOptions,
	} = getPicklistOptions<number>(
		expenseEntries,
		(expenseSelected, currentBudgetIndex) =>
			setFieldValue(
				`activities[${currentActivityIndex}].budgets[${currentBudgetIndex}].expense`,
				expenseSelected
			)
	);

	const budgetsAmount = useBudgetsAmount(
		budgets,
		useCallback(
			(amountValue) => {
				setFieldValue(
					`activities[${currentActivityIndex}].totalCostOfExpense`,
					amountValue
				);

				setFieldValue(
					`activities[${currentActivityIndex}].mdfRequestAmount`,
					amountValue * claimPercent
				);
			},
			[claimPercent, currentActivityIndex, setFieldValue]
		)
	);

	const onRemove = (index: number) =>
		setFieldValue(
			`activities[${currentActivityIndex}].budgets[${index}].removed`,
			true
		);

	return (
		<PRMForm.Section
			subtitle="Add all the expenses that best match with your Activity to add your Total  MDF Requested Amount"
			title="Budget Breakdown"
		>
			<div>
				{budgets.map(
					({removed}, index) =>
						!removed && (
							<div
								className="align-items-center d-flex"
								key={index}
							>
								<PRMForm.Group className="mr-4">
									<PRMFormik.Field
										component={PRMForm.Select}
										label="Expense"
										name={`activities[${currentActivityIndex}].budgets[${index}].expense`}
										onChange={(
											event: React.ChangeEvent<
												HTMLInputElement
											>
										) => onExpenseSelected(event, index)}
										options={expensesOptions}
										required
									/>

									<PRMFormik.Field
										component={PRMForm.InputCurrency}
										label="Budget"
										name={`activities[${currentActivityIndex}].budgets[${index}].cost`}
										onAccept={(value: number) =>
											setFieldValue(
												`activities[${currentActivityIndex}].budgets[${index}].cost`,
												value
											)
										}
										required
									/>
								</PRMForm.Group>

								<ClayButtonWithIcon
									className="mt-2"
									displayType="secondary"
									onClick={() => onRemove(index)}
									small
									symbol="hr"
								/>
							</div>
						)
				)}

				<Button
					className="align-items-center d-flex"
					onClick={() => arrayHelpers.push(getNewBudget())}
					outline
					small
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="plus" />
					</span>
					Add Expense
				</Button>
			</div>

			<div className="my-3">
				<ResumeCard
					leftContent="Total cost"
					rightContent={getIntlNumberFormat(currency).format(
						budgetsAmount
					)}
				/>

				<ResumeCard
					className="mt-3"
					leftContent="Claim Percent"
					rightContent={`${claimPercent * 100}%`}
				/>
			</div>

			<PRMFormik.Field
				component={PRMForm.InputCurrency}
				label="Total MDF Requested Amount"
				name={`activities[${currentActivityIndex}].mdfRequestAmount`}
				onAccept={(value: number) =>
					setFieldValue(
						`activities[${currentActivityIndex}].mdfRequestAmount`,
						value
					)
				}
				required
			/>
		</PRMForm.Section>
	);
};

export default BudgetBreakdownSection;
