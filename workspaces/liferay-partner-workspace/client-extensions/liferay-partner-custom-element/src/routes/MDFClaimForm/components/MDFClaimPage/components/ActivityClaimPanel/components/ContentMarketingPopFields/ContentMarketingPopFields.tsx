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
import {ResourceName} from '../../../../../../../../common/services/liferay/object/enum/resourceName';

interface IProps {
	activity: MDFClaimActivity;
	currentActivityIndex: number;
}

const ContentMarketingPopFields = ({
	activity,
	currentActivityIndex,
	setFieldValue,
}: IProps & Pick<FormikContextType<MDFClaim>, 'setFieldValue'>) => {
	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Video Link"
				name={`activities[${currentActivityIndex}].videoLink`}
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
				resourceName={ResourceName.MDF_CLAIM_ACTIVITY_DOCUMENTS}
				value={activity.proofOfPerformance?.allContents}
			/>
		</>
	);
};

export default ContentMarketingPopFields;
