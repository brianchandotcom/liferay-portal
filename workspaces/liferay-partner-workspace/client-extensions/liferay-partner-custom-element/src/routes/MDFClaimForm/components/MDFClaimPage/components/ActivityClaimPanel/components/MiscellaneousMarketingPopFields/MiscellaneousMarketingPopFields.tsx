/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikContextType} from 'formik';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import InputMultipleFilesListing from '../../../../../../../../common/components/PRMForm/components/fields/InputMultipleFilesListing/InputMultipleFilesListing';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import LiferayFile from '../../../../../../../../common/interfaces/liferayFile';
import MDFClaim from '../../../../../../../../common/interfaces/mdfClaim';
import MDFClaimActivity from '../../../../../../../../common/interfaces/mdfClaimActivity';
import deleteDocument from '../../../../../../../../common/services/liferay/headless-delivery/deleteDocument';

interface IProps {
	activity: MDFClaimActivity;
	currentActivityIndex: number;
}

const MiscellaneousMarketingPopFields = ({
	activity,
	currentActivityIndex,
	setFieldValue,
}: IProps & Pick<FormikContextType<MDFClaim>, 'setFieldValue'>) => {
	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Telemarketing Metrics"
				name={`activities[${currentActivityIndex}].telemarketingMetrics`}
				required={activity.selected}
				textArea
			/>

			<PRMFormik.Field
				component={PRMForm.InputFile}
				description="Only files with the following extensions wil be accepted: doc, docx, jpg, jpeg, png, tif, tiff, pdf"
				displayType="secondary"
				label="Telemarketing Script"
				name={`activities[${currentActivityIndex}].telemarketingScript`}
				onAccept={async (liferayFile: LiferayFile) => {
					if (activity.telemarketingScript?.documentId) {
						deleteDocument(
							activity.telemarketingScript?.documentId
						);
					}
					setFieldValue(
						`activities[${currentActivityIndex}].telemarketingScript`,
						liferayFile
					);
				}}
				outline
				small
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="jpg, jpeg, png, tif, tiff, pdf"
				description="Drag and drop your files here to upload."
				label="Images"
				name={`activities[${currentActivityIndex}].proofOfPerformance.images`}
				onAccept={(liferayFiles: LiferayFile[]) =>
					setFieldValue(
						`activities[${currentActivityIndex}].proofOfPerformance.images`,
						activity.proofOfPerformance?.images
							? activity.proofOfPerformance.images.concat(
									liferayFiles as LiferayFile[]
							  )
							: liferayFiles
					)
				}
				value={activity.proofOfPerformance?.images}
			/>

			<InputMultipleFilesListing
				acceptedFilesExtensions="doc, docx, jpg, jpeg, png, tif, tiff, pdf"
				description="Drag and drop your files here to upload."
				label="All Contents"
				name={`activities[${currentActivityIndex}].proofOfPerformance.allContents`}
				onAccept={(liferayFiles: LiferayFile[]) =>
					setFieldValue(
						`activities[${currentActivityIndex}].proofOfPerformance.allContents`,
						activity.proofOfPerformance?.allContents
							? activity.proofOfPerformance.allContents.concat(
									liferayFiles as LiferayFile[]
							  )
							: liferayFiles
					)
				}
				required={activity.selected}
				value={activity.proofOfPerformance?.allContents}
			/>
		</>
	);
};

export default MiscellaneousMarketingPopFields;
