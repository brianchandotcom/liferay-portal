/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useFormikContext} from 'formik';
import {useCallback} from 'react';

import PRMForm from '../../../../common/components/PRMForm';
import InputMultipleFilesListing from '../../../../common/components/PRMForm/components/fields/InputMultipleFilesListing/InputMultipleFilesListing';
import PRMFormik from '../../../../common/components/PRMFormik';
import PRMFormikPageProps from '../../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import ResumeCard from '../../../../common/components/ResumeCard';
import useSetTouchedOnForms from '../../../../common/hooks/useSetTouchedOnForms';
import MDFRequestDTO from '../../../../common/interfaces/dto/mdfRequestDTO';
import LiferayFile from '../../../../common/interfaces/liferayFile';
import MDFClaim from '../../../../common/interfaces/mdfClaim';
import MDFClaimActivity from '../../../../common/interfaces/mdfClaimActivity';
import MDFClaimProps from '../../../../common/interfaces/mdfClaimProps';
import {ResourceName} from '../../../../common/services/liferay/object/enum/resourceName';
import {Status} from '../../../../common/utils/constants/status';
import getIntlNumberFormat from '../../../../common/utils/getIntlNumberFormat';
import useDynamicFieldEntries from '../../../MDFClaimList/hooks/useDynamicFieldEntries';
import ActivityClaimPanel from './components/ActivityClaimPanel';
import useActivitiesAmount from './hooks/useActivitiesAmount';

interface IProps {
	hasPermissionShowForm: boolean;
	mdfRequest: MDFRequestDTO;
}

const MDFClaimPage = ({
	hasPermissionShowForm,
	mdfRequest,
	onCancel,
	onSaveAsDraft,
}: PRMFormikPageProps & MDFClaimProps & IProps) => {
	const {
		isSubmitting,
		isValid,
		setFieldValue,
		status: submitted,
		values,
		...formikHelpers
	} = useFormikContext<MDFClaim>();

	const errors = formikHelpers.errors;

	useActivitiesAmount(
		values.activities,
		useCallback(
			(amountValue) => {
				setFieldValue(
					'totalClaimAmount',
					amountValue * mdfRequest.claimPercent
				);
				setFieldValue(
					'convertedTotalClaimAmount',
					(amountValue * mdfRequest.claimPercent) /
						mdfRequest.currencyExchangeRate
				);
			},
			[
				mdfRequest.claimPercent,
				mdfRequest.currencyExchangeRate,
				setFieldValue,
			]
		)
	);

	const {companiesEntries, fieldEntries} = useDynamicFieldEntries();

	const {isButtonClicked, setIsButtonClicked} = useSetTouchedOnForms(
		useCallback(() => Boolean(values.id), [values.id]),
		formikHelpers
	);

	const claimsFiltered = mdfRequest.mdfReqToMDFClms?.filter(
		(mdfRequestToMdfClaim) => {
			const ignoreStatus = [
				Status.DRAFT.key,
				Status.EXPIRED.key,
				Status.REJECT.key,
			];

			return !ignoreStatus.includes(
				mdfRequestToMdfClaim.mdfClaimStatus.key as string
			);
		}
	).length;

	const isDisplayableMDFActivityClaim = (activity: MDFClaimActivity) => {
		const claimableActivityByStatus =
			activity.activityStatus?.key !== Status.EXPIRED.key &&
			!activity.claimed;

		const editableClaimActivityByStatus =
			Boolean(activity.id) && !activity.selected;

		const isDisplayable = activity.id
			? hasPermissionShowForm
			: claimableActivityByStatus || editableClaimActivityByStatus;

		return isDisplayable;
	};

	const availableMDFActivities = values.activities?.filter((activity) =>
		isDisplayableMDFActivityClaim(activity)
	).length;

	const getCreateClaimDenialMessage = () => {
		if (mdfRequest.mdfRequestStatus?.key !== Status.APPROVED.key) {
			return 'Waiting for Manager approval.';
		}
		else if (claimsFiltered && claimsFiltered >= 2 && !values.id) {
			return 'You already submitted 2 claims.';
		}
		else if (!availableMDFActivities) {
			return "You don't have activities available to claim.";
		}
	};

	const getClaimPage = () => {
		if (!fieldEntries || !companiesEntries) {
			return <ClayLoadingIndicator />;
		}

		const createClaimDenialMessage = getCreateClaimDenialMessage();

		if (createClaimDenialMessage) {
			return (
				<PRMForm name="New" title="Reimbursement Claim">
					<div className="d-flex justify-content-center mt-4">
						<ClayAlert
							className="m-0 w-100"
							displayType="info"
							title="Info:"
						>
							{createClaimDenialMessage}
						</ClayAlert>
					</div>

					<PRMForm.Footer>
						<div className="d-flex mr-auto">
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={() => onCancel()}
							>
								Cancel
							</ClayButton>
						</div>
					</PRMForm.Footer>
				</PRMForm>
			);
		}

		const handleOnClick = () => {
			setIsButtonClicked(true);
			window.scrollTo({
				behavior: (isValid ? 'instant' : 'smooth') as ScrollBehavior,
				top: 0,
			});
		};

		const isButtonDisabled =
			((!isValid || isSubmitting || submitted) && isButtonClicked) ||
			(Boolean(values.id) && !isValid);

		return (
			<PRMForm name="New" title="Reimbursement Claim">
				<PRMForm.Section
					subtitle="Check each expense you would like claim and please provide proof of performance for each of the selected expenses."
					title={`${mdfRequest?.overallCampaignDescription} (${mdfRequest?.id})`}
				>
					<p className="font-weight-bold my-4 text-paragraph">
						Upload Proof of Performance Documents
						<span className="text-danger">*</span>
					</p>

					{values.activities?.map(
						(activity, index) =>
							isDisplayableMDFActivityClaim(activity) && (
								<ActivityClaimPanel
									activity={activity}
									activityIndex={index}
									errors={errors}
									hasPermissionEditClaimActivity={
										hasPermissionShowForm
									}
									isButtonClicked={isButtonClicked}
									isEdit={!!values.id}
									key={`${activity.id}-${index}`}
									overallCampaignDescription={
										mdfRequest.overallCampaignDescription
									}
									setFieldValue={setFieldValue}
								/>
							)
					)}

					{errors?.activities &&
						typeof errors.activities === 'string' &&
						(isButtonClicked || Boolean(values.id)) && (
							<ClayAlert
								displayType="danger"
								hideCloseIcon={true}
							>
								{errors.activities}
							</ClayAlert>
						)}
				</PRMForm.Section>

				<PRMForm.Section
					subtitle="Total Claim is the reimbursement of your expenses, and is up to the Total MDF Requested. In case need to claim more than the MDF Requested you need to apply for a New MDF Request."
					title="Total Claim"
				>
					<InputMultipleFilesListing
						acceptedFilesExtensions="doc, docx, jpg, jpeg, png, tif, tiff, pdf"
						description="Drag and drop your files here to upload an invoice for the Total Claim Amount."
						label="Reimbursement Invoices"
						name="reimbursementInvoices"
						onAccept={(liferayFiles: LiferayFile[]) =>
							setFieldValue(
								`reimbursementInvoices`,
								values.reimbursementInvoices
									? values.reimbursementInvoices.concat(
											liferayFiles as LiferayFile[]
										)
									: liferayFiles
							)
						}
						required
						resourceName={ResourceName.MDF_CLAIM_DOCUMENTS}
						value={values.reimbursementInvoices}
					/>

					<ResumeCard
						className="my-4"
						leftContent="Total Activity Cost"
						rightContent={getIntlNumberFormat(
							values.currency
						).format(values.totalMDFRequestedAmount || 0)}
					/>

					<PRMFormik.Field
						component={PRMForm.InputCurrency}
						description="The amount to be claimed for the Total of  selected expenses"
						label="Total Claim Amount"
						name="totalClaimAmount"
						onAccept={(value: number) => {
							setFieldValue('totalClaimAmount', value);
							setFieldValue(
								'convertedTotalClaimAmount',
								value / mdfRequest.currencyExchangeRate
							);
						}}
						required
					/>
				</PRMForm.Section>

				<PRMForm.Footer>
					<div className="d-flex mr-auto">
						<ClayButton
							className="inline-item inline-item-after pl-0"
							disabled={isSubmitting || submitted}
							displayType={null}
							onClick={() => onSaveAsDraft(values, formikHelpers)}
						>
							Save as Draft
							{isSubmitting && (
								<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
							)}
						</ClayButton>
					</div>

					<div>
						<ClayButton
							className="mr-4"
							disabled={isSubmitting || submitted}
							displayType="secondary"
							onClick={() => onCancel()}
						>
							Cancel
						</ClayButton>

						<ClayButton
							className="inline-item inline-item-after"
							disabled={isButtonDisabled}
							onClick={() => {
								handleOnClick();
							}}
							type="submit"
						>
							Submit
							{isSubmitting && (
								<ClayLoadingIndicator className="inline-item inline-item-after ml-2" />
							)}
						</ClayButton>
					</div>
				</PRMForm.Footer>
			</PRMForm>
		);
	};

	return getClaimPage();
};

export default MDFClaimPage;
