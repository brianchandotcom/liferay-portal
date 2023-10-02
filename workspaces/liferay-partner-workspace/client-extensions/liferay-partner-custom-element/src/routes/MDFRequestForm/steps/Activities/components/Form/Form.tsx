/* eslint-disable react-hooks/exhaustive-deps */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useMemo} from 'react';

import PRMForm from '../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../common/components/PRMFormik';
import {TypeActivityKey} from '../../../../../../common/enums/TypeActivityKey';
import {LiferayPicklistName} from '../../../../../../common/enums/liferayPicklistName';
import {TacticKeys} from '../../../../../../common/enums/mdfRequestTactics';
import LiferayPicklist from '../../../../../../common/interfaces/liferayPicklist';
import MDFRequestActivity from '../../../../../../common/interfaces/mdfRequestActivity';
import getNewActivity from '../../utils/getNewActivity';
import BudgetBreakdownSection from './components/BudgetBreakdownSection';
import ContentMarketingFields from './components/ContentMarketingFields';
import DigitalMarketingFields from './components/DigitalMarketingFields';
import EventFields from './components/EventFields';
import LeadListSection from './components/LeadListSection';
import MiscellaneousMarketingFields from './components/MiscellaneousMarketingFields';
import useDynamicFieldEntries from './hooks/useDynamicFieldEntries';
import useTacticsOptions from './hooks/useTacticsOptions';
import useTypeActivityOptions from './hooks/useTypeActivityOptions';

interface IProps {
	claimPercent: number;
	currency: LiferayPicklist;
	currentActivity: MDFRequestActivity;
	currentActivityIndex: number;
	setFieldValue: (
		field: string,
		value: any,
		shouldValidate?: boolean | undefined
	) => void;
}

type TypeActivityComponent = {
	[key in string]?: JSX.Element;
};

const Form = ({
	claimPercent,
	currency,
	currentActivity,
	currentActivityIndex,
	setFieldValue,
}: IProps) => {
	const {fieldEntries} = useDynamicFieldEntries();

	const handleClearForm = () => {
		setFieldValue(
			`activities[${currentActivityIndex}].activityDescription`,
			getNewActivity(claimPercent, currency).activityDescription
		);
	};

	useEffect(() => {
		const hasLeadGenerated =
			currentActivity?.activityDescription?.leadGenerated;
		const isEventActivity =
			currentActivity?.typeActivity?.key === TypeActivityKey.EVENT;

		const displaySection =
			hasLeadGenerated || (isEventActivity ? 'true' : '');

		setFieldValue(
			`activities[${currentActivityIndex}].activityDescription.leadGenerated`,
			displaySection
		);
	}, [currentActivity.tactic, currentActivity?.typeActivity?.key]);

	const {
		onTypeActivitySelected,
		typeActivitiesOptions,
	} = useTypeActivityOptions(
		fieldEntries[LiferayPicklistName.TYPE_OF_ACTIVITY],
		(selectedTypeActivity) => {
			setFieldValue(
				`activities[${currentActivityIndex}].typeActivity`,
				selectedTypeActivity
			);

			setFieldValue(`activities[${currentActivityIndex}].tactic`, {});
		},
		handleClearForm
	);

	const {onTacticSelected, tacticsOptions} = useTacticsOptions(
		useMemo(
			() =>
				fieldEntries[LiferayPicklistName.TACTIC]?.filter((tactic) =>
					String(tactic.value).includes(
						String(currentActivity.typeActivity?.key)
					)
				),
			[currentActivity.typeActivity?.key, fieldEntries]
		),
		(selectedTactic) =>
			setFieldValue(
				`activities[${currentActivityIndex}].tactic`,
				selectedTactic
			),
		handleClearForm
	);

	const typeActivityComponents: TypeActivityComponent = {
		[TypeActivityKey.DIGITAL_MARKETING]: (
			<DigitalMarketingFields
				currentActivityIndex={currentActivityIndex}
				tactic={currentActivity.tactic?.key as TacticKeys}
			/>
		),
		[TypeActivityKey.CONTENT_MARKETING]: (
			<ContentMarketingFields
				currentActivityIndex={currentActivityIndex}
			/>
		),
		[TypeActivityKey.EVENT]: (
			<EventFields
				currentActivityIndex={currentActivityIndex}
				tactic={currentActivity.tactic?.key as TacticKeys}
			/>
		),
		[TypeActivityKey.MISCELLANEOUS_MARKETING]: (
			<MiscellaneousMarketingFields
				currentActivityIndex={currentActivityIndex}
				tactic={currentActivity.tactic?.key as TacticKeys}
			/>
		),
	};
	useEffect(() => {
		setFieldValue(`activities[${currentActivityIndex}].currency`, currency);
	}, []);

	return (
		<>
			<PRMForm.Section title="Campaign Activity">
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Activity name"
					name={`activities[${currentActivityIndex}].name`}
					required
				/>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Type of Activity"
						name={`activities[${currentActivityIndex}].typeActivity`}
						onChange={onTypeActivitySelected}
						options={typeActivitiesOptions}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						emptyOptionMessage="Select a Type of Activity"
						label="Tactic"
						name={`activities[${currentActivityIndex}].tactic`}
						onChange={onTacticSelected}
						options={tacticsOptions}
						required
					/>
				</PRMForm.Group>

				{
					typeActivityComponents[
						String(currentActivity.typeActivity?.key) || ''
					]
				}

				<LeadListSection
					currentActivityIndex={currentActivityIndex}
					fieldEntries={fieldEntries}
					selectedTypeActivity={String(
						currentActivity.typeActivity?.key
					)}
				/>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.DatePicker}
						label="Start Date"
						name={`activities[${currentActivityIndex}].startDate`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.DatePicker}
						label="End Date"
						name={`activities[${currentActivityIndex}].endDate`}
						required
					/>
				</PRMForm.Group>
			</PRMForm.Section>
			<PRMFormik.Array
				budgets={currentActivity.budgets}
				claimPercent={claimPercent}
				component={BudgetBreakdownSection}
				currency={currency}
				currentActivityIndex={currentActivityIndex}
				expenseEntries={
					fieldEntries[LiferayPicklistName.BUDGET_EXPENSES]
				}
				name={`activities[${currentActivityIndex}].budgets`}
				setFieldValue={setFieldValue}
			/>
			<div className="d-none total-mdf-request-amount">
				{`Total MDF Requested Amount: ${currentActivity.mdfRequestAmount}`}
			</div>
		</>
	);
};

export default Form;
