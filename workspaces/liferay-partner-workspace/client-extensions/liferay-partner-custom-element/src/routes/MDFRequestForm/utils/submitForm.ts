/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikHelpers} from 'formik';

import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFRequest from '../../../common/interfaces/mdfRequest';
import {Liferay} from '../../../common/services/liferay';
import deleteObjectEntryByERC from '../../../common/services/liferay/object/deleteObjectEntry/deleteObjectEntryByERC';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import {Status} from '../../../common/utils/constants/status';
import updateStatus from '../../../common/utils/updateStatus';
import submitMDFRequest from './submitMDFRequest';
import submitMDFRequestActivity from './submitMDFRequestActivity';
import submitMDFRequestActivityProxyAPI from './submitMDFRequestActivityProxyAPI';
import submitMDFRequestBudget from './submitMDFRequestBudget';
import submitMDFRequestProxyAPI from './submitMDFRequestProxyAPI';

export default async function submitForm(
	values: MDFRequest,
	formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>,
	siteURL: string,
	currentRequestStatus?: LiferayPicklist,
	changeStatus?: boolean
) {
	formikHelpers.setSubmitting(true);
	formikHelpers.setStatus(true);

	const updatedStatus = updateStatus(
		values.mdfRequestStatus,
		currentRequestStatus,
		changeStatus,
		values.id,
		values.totalMDFRequestAmount
	);

	values.mdfRequestStatus = updatedStatus;

	try {
		const dtoMDFRequest =
			values.mdfRequestStatus.key === Status.DRAFT.key
				? await submitMDFRequest(values)
				: await submitMDFRequestProxyAPI(values);

		values.id = dtoMDFRequest?.id;
		values.externalReferenceCode = dtoMDFRequest?.externalReferenceCode;

		if (
			values?.activities?.length &&
			dtoMDFRequest?.id &&
			values.company?.id
		) {
			for (const mdfRequestActivity of values.activities) {
				if (
					mdfRequestActivity.removed &&
					mdfRequestActivity.externalReferenceCode
				) {
					if (mdfRequestActivity.submitted) {
						await deleteObjectEntryByERC(
							ResourceName.ACTIVITY_SALESFORCE,
							mdfRequestActivity.externalReferenceCode
						);
					}

					await deleteObjectEntryByERC(
						ResourceName.ACTIVITY_DXP,
						mdfRequestActivity.externalReferenceCode
					);
				}
				else {
					const dtoMDFRequestActivity =
						values.mdfRequestStatus.key === Status.DRAFT.key
							? await submitMDFRequestActivity(
									mdfRequestActivity,
									dtoMDFRequest,
									values
							  )
							: await submitMDFRequestActivityProxyAPI(
									mdfRequestActivity,
									dtoMDFRequest,
									values
							  );

					mdfRequestActivity.id = dtoMDFRequestActivity?.id;
					mdfRequestActivity.externalReferenceCode =
						dtoMDFRequestActivity?.externalReferenceCode;

					if (
						dtoMDFRequestActivity?.id &&
						mdfRequestActivity.budgets.length
					) {
						for (const mdfRequestBudget of mdfRequestActivity.budgets) {
							if (
								mdfRequestBudget.removed &&
								mdfRequestBudget.externalReferenceCode
							) {
								await deleteObjectEntryByERC(
									ResourceName.BUDGET,
									mdfRequestBudget.externalReferenceCode
								);
							}
							else {
								const dtoMDFRequestBudget = await submitMDFRequestBudget(
									mdfRequestBudget,
									dtoMDFRequestActivity,
									values
								);

								mdfRequestBudget.id = dtoMDFRequestBudget.id;
								mdfRequestBudget.externalReferenceCode =
									dtoMDFRequestBudget.externalReferenceCode;
							}
						}
					}
				}
			}
		}
		formikHelpers.setValues(values);

		if (values.id) {
			Liferay.Util.navigate(
				`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
			);

			Liferay.Util.openToast({
				message: 'MDF Request was successfully edited.',
				type: 'success',
			});

			return;
		}

		if (values.mdfRequestStatus.key === Status.DRAFT.key) {
			Liferay.Util.navigate(
				`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
			);

			Liferay.Util.openToast({
				message: 'MDF Request was successfully saved as draft.',
				type: 'success',
			});

			return;
		}

		Liferay.Util.openToast({
			message: 'MDF Request was successfully submitted.',
			type: 'success',
		});

		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
		);
	}
	catch (error: unknown) {
		formikHelpers.setStatus(false);
		formikHelpers.setSubmitting(false);

		Liferay.Util.openToast({
			message: 'MDF Request could not be submitted. Please, try again.',
			title: 'Error',
			type: 'danger',
		});
	}
}
