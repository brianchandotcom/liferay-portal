/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useFormikContext} from 'formik';
import {useCallback, useEffect, useMemo} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormik from '../../../../common/components/PRMFormik';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import {LiferayPicklistName} from '../../../../common/enums/liferayPicklistName';
import useCompanyOptions from '../../../../common/hooks/useCompanyOptions';
import MDFRequest from '../../../../common/interfaces/mdfRequest';
import getPicklistOptions from '../../../../common/utils/getPicklistOptions';
import isObjectEmpty from '../../../../common/utils/isObjectEmpty';
import {StepType} from '../../enums/stepType';
import MDFRequestStepProps from '../../interfaces/mdfRequestStepProps';
import useDynamicFieldEntries from './hooks/useDynamicFieldEntries';

const Goals = ({
	disableCompany,
	onCancel,
	onContinue,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFRequestStepProps) => {
	const {
		errors,
		isSubmitting,
		isValid,
		setFieldValue,
		status: submitted,
		values,
		...formikHelpers
	} = useFormikContext<MDFRequest>();
	const {companiesEntries, fieldEntries} = useDynamicFieldEntries(
		disableCompany
	);

	const {companyOptions, onCompanySelected} = useCompanyOptions(
		useCallback(
			(partnerCountry, company, currency, claimPercent) => {
				setFieldValue('company', company);
				setFieldValue('partnerCountry', partnerCountry);
				setFieldValue('currency', currency);
				setFieldValue('claimPercent', claimPercent);
			},
			[setFieldValue]
		),
		companiesEntries,
		fieldEntries[LiferayPicklistName.CURRENCIES],
		!isObjectEmpty(values.currency) ? values.currency : undefined,
		fieldEntries[LiferayPicklistName.COUNTRIES],
		!isObjectEmpty(values.partnerCountry)
			? values.partnerCountry
			: undefined,
		!isObjectEmpty(values.company) ? values.company : undefined
	);

	const {
		onSelected: onAdditionalOptionSelected,
		options: additionalOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.ADDITIONAL_OPTIONS],
		(selected) => setFieldValue('additionalOption', selected)
	);

	const goalsErrors = useMemo(() => {
		delete errors.activities;

		return errors;
	}, [errors]);

	useEffect(() => {
		if (
			!values.liferayBusinessSalesGoals?.includes(
				'Other - Please describe'
			)
		) {
			setFieldValue(`liferayBusinessSalesGoalsOther`, '');
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.liferayBusinessSalesGoals]);

	const getRequestPage = () => {
		if (!fieldEntries) {
			return <ClayLoadingIndicator />;
		}

		return (
			<PRMForm name="Goals" title="Campaign Information">
				<PRMForm.Section title="Partner">
					<PRMForm.Group>
						<PRMFormik.Field
							component={PRMForm.Select}
							disabled={disableCompany}
							label="Company Name"
							name="company"
							onChange={onCompanySelected}
							options={companyOptions}
							required
						/>
					</PRMForm.Group>
				</PRMForm.Section>

				<PRMForm.Section title="Campaign">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Provide the name of the campaign"
						name="overallCampaignName"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Provide a short description of the overall campaign"
						name="overallCampaignDescription"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[
								LiferayPicklistName.LIFERAY_BUSINESS_SALES_GOALS
							]
						}
						label="Select Liferay business/sales goals this Campaign serves (choose up to three)"
						name="liferayBusinessSalesGoals"
						required
					>
						{values.liferayBusinessSalesGoals?.includes(
							'Other - Please describe'
						) && (
							<PRMFormik.Field
								component={PRMForm.InputText}
								name="liferayBusinessSalesGoalsOther"
								required
							/>
						)}
					</PRMFormik.Field>
				</PRMForm.Section>

				<PRMForm.Section title="Target Market">
					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={fieldEntries[LiferayPicklistName.TARGET_MARKETS]}
						label="Please select the target market(s) for this campaign (choose up to three)"
						name="targetMarkets"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.RadioGroup}
						items={additionalOptions}
						label="Additional options? Choose one if applicable"
						name="additionalOption"
						onChange={onAdditionalOptionSelected}
					/>

					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[
								LiferayPicklistName.TARGET_AUDIENCE_ROLES
							]
						}
						label="Choose your target audience/role (Select all that apply)"
						name="targetAudienceRoles"
						required
					/>
				</PRMForm.Section>

				<PRMForm.Footer>
					<div className="d-flex justify-content-end mr-auto">
						<Button
							className="inline-item inline-item-after pl-0"
							disabled={
								submitted ||
								!values.company?.externalReferenceCode
							}
							displayType={null}
							onClick={() =>
								onSaveAsDraft?.(values, formikHelpers)
							}
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
							disabled={isSubmitting || submitted}
							displayType="secondary"
							onClick={onCancel}
						>
							Cancel
						</Button>

						<Button
							className="inline-item inline-item-after"
							disabled={
								(!isValid && !isObjectEmpty(goalsErrors)) ||
								isSubmitting ||
								submitted
							}
							onClick={() =>
								onContinue?.(formikHelpers, StepType.ACTIVITIES)
							}
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

	return getRequestPage();
};
export default Goals;
