/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {useFormikContext} from 'formik';
import {useCallback} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import PRMFormik from '../../../../common/components/PRMFormik';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import {LiferayPicklistName} from '../../../../common/enums/liferayPicklistName';
import useCompanyOptions from '../../../../common/hooks/useCompanyOptions';
import DealRegistration from '../../../../common/interfaces/dealRegistration';
import useGetMDFActivity from '../../../../common/services/liferay/object/activity/useGetMDFActivity';
import getPicklistOptions from '../../../../common/utils/getPicklistOptions';
import {StepType} from '../../enums/stepType';
import useDynamicFieldEntries from '../../hooks/useDynamicFieldEntries';
import useMDFActivityOptions from '../../hooks/useMDFActivityOptions';
import DealRegistrationStepProps from '../../interfaces/dealRegistrationStepProps';

const General = ({
	onCancel,
	onContinue,
}: PRMFormikPageProps & DealRegistrationStepProps) => {
	const {
		dirty,
		isValid,
		setFieldValue,
		values,
		...formikHelpers
	} = useFormikContext<DealRegistration>();

	const {companiesEntries, fieldEntries} = useDynamicFieldEntries(
		useCallback(
			(firstName, lastName) => {
				setFieldValue('partnerFirstName', firstName);
				setFieldValue('partnerLastName', lastName);
			},
			[setFieldValue]
		)
	);

	const {data: mdfActivities} = useGetMDFActivity(
		values.partnerAccount.externalReferenceCode
	);

	const {companyOptions, onCompanySelected} = useCompanyOptions(
		useCallback(
			(_, company, currency) => {
				setFieldValue('partnerAccount', company);
				setFieldValue('currency', currency);
			},
			[setFieldValue]
		),
		companiesEntries,
		fieldEntries[LiferayPicklistName.CURRENCIES]
	);

	const {mdfActivitiesOptions, onMDFActivitySelected} = useMDFActivityOptions(
		mdfActivities?.items,
		useCallback(
			(selectedActivity) => {
				setFieldValue('mdfActivityAssociated', selectedActivity);
			},
			[setFieldValue]
		)
	);

	const {
		onSelected: onCountrySelected,
		options: countryOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.COUNTRIES],
		(selected) => setFieldValue('prospect.country', selected)
	);

	const {
		onSelected: onCurrencySelected,
		options: currencyOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.CURRENCIES],
		(selected) => setFieldValue('currency', selected)
	);

	const companyCurrencies =
		currencyOptions &&
		currencyOptions.filter(
			(currency) => currency.value === values.currency.key
		);

	const {
		onSelected: onIndustrySelected,
		options: industryOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.INDUSTRIES],
		(selected) => setFieldValue('prospect.industry', selected)
	);

	const {
		onSelected: onDepartmentSelected,
		options: departmentOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.DEPARTMENTS],
		(selected) => setFieldValue('primaryProspect.department', selected)
	);

	const {
		onSelected: onJobRoleSelected,
		options: jobRoleOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.JOB_ROLES],
		(selected) => setFieldValue('primaryProspect.jobRole', selected)
	);

	const {
		onSelected: onStateSelected,
		options: stateOptions,
	} = getPicklistOptions(
		fieldEntries[LiferayPicklistName.STATES],
		(selected) => setFieldValue('prospect.state', selected)
	);

	return (
		<PRMForm name="general" title="Deal Registration">
			<PRMForm.Section title="General Details">
				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Partner Account Name"
						name="partnerAccount"
						onChange={onCompanySelected}
						options={companyOptions}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="MDF Activity Associated"
						name="mdfActivityAssociated"
						onChange={onMDFActivitySelected}
						options={mdfActivitiesOptions}
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="Currency"
						name="currency"
						onChange={onCurrencySelected}
						options={companyCurrencies}
						required
					/>
				</PRMForm.Group>
			</PRMForm.Section>

			<PRMForm.Section title="Prospect Information">
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Account Name"
					name="prospect.accountName"
					required
				/>

				<PRMFormik.Field
					component={PRMForm.Select}
					label="Industry"
					name="prospect.industry"
					onChange={onIndustrySelected}
					options={industryOptions}
					required
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Address"
					name="prospect.address"
					required
				/>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="City"
						name="prospect.city"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Postal Code"
						name="prospect.postalCode"
						required
					/>
				</PRMForm.Group>

				<PRMForm.Group>
					<PRMFormik.Field
						component={PRMForm.Select}
						label="Country"
						name="prospect.country"
						onChange={onCountrySelected}
						options={countryOptions}
						required
					/>

					{values.prospect?.country.key === 'US' && (
						<PRMFormik.Field
							component={PRMForm.Select}
							label="State"
							name="prospect.state"
							onChange={onStateSelected}
							options={stateOptions}
							required
						/>
					)}
				</PRMForm.Group>

				<PRMForm.Section title="Primary Prospect Contact">
					<PRMForm.Group>
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="First Name"
							name="primaryProspect.firstName"
							required
						/>

						<PRMFormik.Field
							component={PRMForm.InputText}
							label="Last Name"
							name="primaryProspect.lastName"
							required
						/>
					</PRMForm.Group>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Email Address"
						name="primaryProspect.emailAddress"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Phone"
						name="primaryProspect.phone"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Business Unit"
						name="primaryProspect.businessUnit"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="Department"
						name="primaryProspect.department"
						onChange={onDepartmentSelected}
						options={departmentOptions}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.Select}
						label="Job Role"
						name="primaryProspect.jobRole"
						onChange={onJobRoleSelected}
						options={jobRoleOptions}
						required
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Additional Contacts">
					<PRMForm.Group>
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="First Name"
							name="additionalContact.firstName"
						/>

						<PRMFormik.Field
							component={PRMForm.InputText}
							label="Last Name"
							name="additionalContact.lastName"
						/>
					</PRMForm.Group>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Email Address"
						name="additionalContact.emailAddress"
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Deal Information">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Additional Information about the Opportunity"
						name="additionalInformationAboutTheOpportunity"
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Project Information">
					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[
								LiferayPicklistName.PROJECT_INFORMATIONS
							]
						}
						label="Project Need (Select all that apply)"
						name="projectNeed"
						required
					/>

					<PRMFormik.Field
						component={PRMForm.CheckboxGroup}
						items={
							fieldEntries[LiferayPicklistName.PROJECT_CATEGORIES]
						}
						label="Project Solution Categories (Select all that apply)"
						name="projectCategories"
						required
					/>
				</PRMForm.Section>

				<PRMForm.Section title="Business Objectives">
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Project Timeline"
						name="projectTimeline"
						required
					/>
				</PRMForm.Section>
			</PRMForm.Section>

			<PRMForm.Footer>
				<div className="d-flex justify-content-between mr-auto">
					<Button
						className="mr-4"
						displayType="secondary"
						onClick={onCancel}
					>
						Cancel
					</Button>
				</div>

				<div className="d-flex justify-content-between px-2 px-md-0">
					<Button
						disabled={!isValid || !dirty}
						onClick={() =>
							onContinue?.(formikHelpers, StepType.REVIEW)
						}
					>
						Proceed
					</Button>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
};

export default General;
