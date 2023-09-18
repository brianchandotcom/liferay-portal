/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikHelpers} from 'formik';

import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {Liferay} from '../../../common/services/liferay';
import {Status} from '../../../common/utils/constants/status';
import updateStatus from '../../../common/utils/updateStatus';
import submitDocuments from './submitDocuments';
import submitMDFClaim from './submitMDFClaim';
import submitMDFClaimActivity from './submitMDFClaimActivity';
import submitMDFClaimActivityDocuments from './submitMDFClaimActivityDocuments';
import submitMDFClaimBudget from './submitMDFClaimBudget';
import submitMDFClaimProxyAPI from './submitMDFClaimProxyAPI';

export default async function submitForm(
	values: MDFClaim,
	formikHelpers: Omit<FormikHelpers<MDFClaim>, 'setFieldValue'>,
	claimParentFolderId: number,
	mdfRequest: MDFRequestDTO,
	siteURL: string,
	currentClaimStatus?: LiferayPicklist,
	changeStatus?: boolean
) {
	formikHelpers.setSubmitting(true);
	formikHelpers.setStatus(true);

	try {
		const submitValues = await submitDocuments(values, claimParentFolderId);

		formikHelpers.setValues(submitValues);

		const updatedStatus = updateStatus(
			submitValues.mdfClaimStatus,
			currentClaimStatus,
			changeStatus,
			submitValues.id
		);

		submitValues.mdfClaimStatus = updatedStatus;

		submitValues.partial = submitValues.activities?.some((activity) =>
			Boolean(activity.budgets?.some((budget) => !budget.selected))
		);

		const dtoMDFClaim =
			submitValues.mdfClaimStatus.key === Status.DRAFT.key
				? await submitMDFClaim(submitValues, mdfRequest)
				: await submitMDFClaimProxyAPI(submitValues, mdfRequest);

		submitValues.id = dtoMDFClaim?.id;
		submitValues.externalReferenceCode = dtoMDFClaim?.externalReferenceCode;

		if (submitValues.activities?.length) {
			for (const mdfClaimActivity of submitValues.activities) {
				if (
					mdfClaimActivity.selected &&
					mdfRequest.r_accToMDFReqs_accountEntryId &&
					dtoMDFClaim?.id
				) {
					const dtoMDFClaimActivity = await submitMDFClaimActivity(
						mdfClaimActivity,
						mdfRequest.r_accToMDFReqs_accountEntryId,
						dtoMDFClaim.id
					);

					mdfClaimActivity.id = dtoMDFClaimActivity.id;
					mdfClaimActivity.externalReferenceCode =
						dtoMDFClaimActivity.externalReferenceCode;

					if (
						mdfClaimActivity.proofOfPerformance &&
						dtoMDFClaimActivity?.id &&
						mdfRequest.r_accToMDFReqs_accountEntryId
					) {
						submitMDFClaimActivityDocuments(
							mdfClaimActivity.proofOfPerformance,
							mdfRequest.r_accToMDFReqs_accountEntryId,
							dtoMDFClaimActivity.id
						);
					}

					if (mdfClaimActivity.budgets?.length) {
						for (const mdfClaimBudget of mdfClaimActivity.budgets) {
							if (
								mdfClaimBudget.selected &&
								mdfRequest.r_accToMDFReqs_accountEntryId &&
								dtoMDFClaimActivity.id
							) {
								const dtoMDFClaimBudget = await submitMDFClaimBudget(
									mdfClaimBudget,
									mdfRequest.r_accToMDFReqs_accountEntryId,
									dtoMDFClaimActivity.id
								);

								mdfClaimBudget.id = dtoMDFClaimBudget.id;
								mdfClaimBudget.externalReferenceCode =
									dtoMDFClaimBudget.externalReferenceCode;
							}
						}
					}
				}
			}
		}

		formikHelpers.setValues(submitValues);

		if (values.dateCreated) {
			Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);

			Liferay.Util.openToast({
				message: 'MDF Claim was successfully edited.',
				type: 'success',
			});

			return;
		}

		if (submitValues.mdfClaimStatus.key === Status.DRAFT.key) {
			Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);

			Liferay.Util.openToast({
				message: 'MDF Claim was successfully saved as draft.',
				type: 'success',
			});

			return;
		}

		Liferay.Util.navigate(`${siteURL}/l/${mdfRequest.id}`);

		Liferay.Util.openToast({
			message: 'MDF Claim was successfully submitted.',
			type: 'success',
		});

		return;
	}
	catch (error: unknown) {
		formikHelpers.setStatus(false);
		formikHelpers.setSubmitting(false);

		Liferay.Util.openToast({
			message: 'MDF Claim could not be submitted. Please, try again.',
			title: 'Error',
			type: 'danger',
		});
	}
}
