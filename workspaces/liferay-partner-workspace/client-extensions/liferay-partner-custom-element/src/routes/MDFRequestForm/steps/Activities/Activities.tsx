/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {ArrayHelpers, useFormikContext} from 'formik';
import {useCallback, useEffect, useState} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import useSetTouchedOnForms from '../../../../common/hooks/useSetTouchedOnForms';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import isObjectEmpty from '../../../../common/utils/isObjectEmpty';
import {StepType} from '../../enums/stepType';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import Form from './components/Form';
import Listing from './components/Listing';
import useGetSummaryActivities from './hooks/useGetSummaryActivities';

interface IProps {
	arrayHelpers: ArrayHelpers;
	isEdit: boolean;
}

const Activities = ({
	arrayHelpers,
	onCancel,
	onContinue,
	onPrevious,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFRequestStepProps & IProps) => {
	const {
		isSubmitting,
		isValid,
		setFieldValue,
		status: submitted,
		values,
		...formikHelpers
	} = useFormikContext<MDFRequest>();

	const errors = formikHelpers.errors;

	const [currentActivityIndex, setCurrentActivityIndex] = useState<
		number | undefined
	>();
	const [currentActivityIndexEdit, setCurrentActivityIndexEdit] =
		useState<number>();

	const [isDraft, setIsDraft] = useState(false);

	const activityErrors =
		currentActivityIndex !== undefined &&
		errors.activities?.[currentActivityIndex];

	const updateEditableActivity = () => {
		if (
			currentActivityIndexEdit !== undefined &&
			currentActivityIndex !== undefined
		) {
			arrayHelpers.swap(currentActivityIndex, currentActivityIndexEdit);

			arrayHelpers.remove(currentActivityIndex);
		}

		setCurrentActivityIndexEdit(undefined);
		setCurrentActivityIndex(undefined);
	};

	const onAdd = () => setCurrentActivityIndex(values.activities.length);

	const {
		maxDateActivity,
		minDateActivity,
		totalCostOfExpense,
		totalMDFRequestAmount,
	} = useGetSummaryActivities(values.activities);

	useEffect(() => {
		setFieldValue('maxDateActivity', maxDateActivity);
		setFieldValue('minDateActivity', minDateActivity);
		setFieldValue(
			'convertedTotalCostOfExpense',
			totalCostOfExpense / values.currencyExchangeRate
		);
		setFieldValue('totalMDFRequestAmount', totalMDFRequestAmount);
		setFieldValue(
			'convertedTotalMDFRequestAmount',
			totalMDFRequestAmount / values.currencyExchangeRate
		);
	}, [
		maxDateActivity,
		minDateActivity,
		setFieldValue,
		totalCostOfExpense,
		totalMDFRequestAmount,
		values.currencyExchangeRate,
	]);

	const {isButtonClicked, setIsButtonClicked} = useSetTouchedOnForms(
		useCallback(
			(currentIsButtonClicked) =>
				(!isObjectEmpty(activityErrors) && currentIsButtonClicked) ||
				(!isObjectEmpty(activityErrors) &&
					currentActivityIndexEdit !== undefined),
			[activityErrors, currentActivityIndexEdit]
		),
		formikHelpers
	);

	const onEdit = (index: number) => {
		arrayHelpers.push(values.activities[index]);

		setCurrentActivityIndex(values.activities.length);
		setCurrentActivityIndexEdit(index);
	};

	const onPreviousForm = useCallback(() => {
		setIsButtonClicked(false);

		if (currentActivityIndex !== undefined) {
			arrayHelpers.remove(currentActivityIndex);

			setCurrentActivityIndex(undefined);
		}

		setCurrentActivityIndexEdit(undefined);
	}, [arrayHelpers, currentActivityIndex, setIsButtonClicked]);

	const onContinueForm = () => {
		if (currentActivityIndex === undefined) {
			for (let index = 0; index < values.activities.length; index++) {
				setFieldValue(
					`activities[${index}].convertedTotalCostOfExpense`,
					values.activities[index].totalCostOfExpense /
						values.currencyExchangeRate
				);

				setFieldValue(
					`activities[${index}].convertedMDFRequestAmount`,
					values.activities[index].mdfRequestAmount /
						values.currencyExchangeRate
				);
			}

			onContinue?.(formikHelpers, StepType.REVIEW);

			return;
		}

		updateEditableActivity();
	};

	const onRemove = (index: number) => {
		setFieldValue(`activities[${index}].removed`, true);

		arrayHelpers.remove(index);
	};

	const hasActivityErrorsByIndex = (index: number): boolean =>
		Boolean(errors.activities?.[index]);

	const onSaveAsDraftForm = () => {
		updateEditableActivity();
		setIsDraft(true);
	};

	useEffect(() => {
		if (isDraft) {
			onSaveAsDraft?.(values, formikHelpers);
			setIsDraft(false);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [isDraft]);

	const handleOnClick = () => {
		setIsButtonClicked(true);
		window.scrollTo({
			behavior: (isValid ? 'instant' : 'smooth') as ScrollBehavior,
			top: 0,
		});
		if (
			(isObjectEmpty(activityErrors as Object) && isButtonClicked) ||
			isObjectEmpty(activityErrors as Object)
		) {
			setIsButtonClicked(false);
			onContinueForm();
		}
	};

	const isEditFormWithError =
		!isObjectEmpty(activityErrors as Object) &&
		currentActivityIndexEdit !== undefined;

	const isNewFormWithError =
		!isObjectEmpty(activityErrors as Object) && isButtonClicked;

	const isDisabledSubmittedButton =
		currentActivityIndex !== undefined
			? isNewFormWithError || isEditFormWithError
			: !isValid;

	return (
		<PRMForm
			className={classNames({
				'mb-3': !currentActivityIndex,
				'mb-4': currentActivityIndex,
			})}
			description="Choose the activities that best match your Campaign MDF request"
			name="Activities"
			title={values.overallCampaignName}
		>
			{currentActivityIndex !== undefined ? (
				<Form
					claimPercent={values.claimPercent}
					currency={values.currency}
					currencyExchangeRate={values.currencyExchangeRate}
					currentActivity={values.activities[currentActivityIndex]}
					currentActivityIndex={currentActivityIndex}
					isButtonClicked={isButtonClicked}
					setFieldValue={setFieldValue}
				/>
			) : (
				<Listing
					{...arrayHelpers}
					activities={values.activities}
					claimPercent={values.claimPercent}
					currency={values.currency}
					hasActivityErrorsByIndex={hasActivityErrorsByIndex}
					onAdd={onAdd}
					onEdit={onEdit}
					onRemove={onRemove}
					overallCampaignName={values.overallCampaignName}
				/>
			)}

			<PRMForm.Footer>
				<div className="d-flex justify-content-between mr-auto">
					<Button
						disabled={submitted || isSubmitting}
						displayType={null}
						onClick={() =>
							currentActivityIndex !== undefined
								? onPreviousForm()
								: onPrevious?.(StepType.GOALS)
						}
					>
						Previous
					</Button>

					<Button
						className="inline-item inline-item-after"
						disabled={submitted || isSubmitting}
						displayType={null}
						onClick={onSaveAsDraftForm}
					>
						Save as Draft
						{isSubmitting && (
							<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
						)}
					</Button>
				</div>

				<div className="d-flex justify-content-between px-2 px-md-0">
					<Button
						className="mr-4"
						disabled={submitted || isSubmitting}
						displayType="secondary"
						onClick={onCancel}
					>
						Cancel
					</Button>

					<Button
						className="inline-item inline-item-after"
						disabled={isDisabledSubmittedButton}
						onClick={() => handleOnClick()}
					>
						Continue
						{isSubmitting && (
							<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
						)}
					</Button>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default Activities;
