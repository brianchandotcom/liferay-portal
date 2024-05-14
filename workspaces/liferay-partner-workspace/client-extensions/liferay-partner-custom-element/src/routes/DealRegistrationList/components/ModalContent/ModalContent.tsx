/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal from '@clayui/modal';

import ModalFormatedInformation from '../../../../common/components/ModalFormatedInformation';
import {DealRegistrationColumnKey} from '../../../../common/enums/dealRegistrationColumnKey';
import Project from '../../../../common/interfaces/project';
import {LiferayAPIs} from '../../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../../common/services/liferay/common/interfaces/liferayItems';
import {ResourceName} from '../../../../common/services/liferay/object/enum/resourceName';
import useGet from '../../../../common/services/liferay/object/useGet';
import {DealRegistrationItem} from '../../DealRegistrationList';

interface ModalContentProps {
	content: DealRegistrationItem;
	onClose: () => void;
}

export default function ModalContent({content, onClose}: ModalContentProps) {
	const swrResponse = useGet<LiferayItems<Project[]>>(
		content[DealRegistrationColumnKey.EXTERNAL_REFERENCE_CODE] &&
			`/o/${LiferayAPIs.OBJECT}/${
				ResourceName.PROJECT_SALESFORCE
			}?filter=leadExternalReferenceCode eq '${
				content[DealRegistrationColumnKey.EXTERNAL_REFERENCE_CODE]
			}'`
	);

	const project = swrResponse.data?.items[0];

	return (
		<ClayModal.Body>
			<div className="align-items-center d-flex justify-content-between mb-4">
				<h3 className="mb-2">Partner Deal Details</h3>

				<ClayButtonWithIcon
					aria-label="Close"
					displayType={null}
					onClick={onClose}
					symbol="times"
				/>
			</div>

			<div>
				{content[DealRegistrationColumnKey.PARTNER_ACCOUNT_NAME] && (
					<div>
						<div className="mb-0 text-paragraph-md">
							General Information
						</div>

						<hr className="mt-0" />

						<ModalFormatedInformation
							className="d-flex"
							information={
								content[
									DealRegistrationColumnKey
										.PARTNER_ACCOUNT_NAME
								]
							}
							label="Partner Account Name"
						/>
					</div>
				)}

				{content[DealRegistrationColumnKey.PARTNER_NAME] && (
					<ModalFormatedInformation
						className="d-flex mb-4"
						information={
							content[DealRegistrationColumnKey.PARTNER_NAME]
						}
						label="Partner Name"
					/>
				)}

				{content[DealRegistrationColumnKey.ACCOUNT_NAME] && (
					<div>
						<div className="mb-0 text-paragraph-md">
							Prospect Information
						</div>

						<hr className="mt-0" />

						<ModalFormatedInformation
							className="d-flex mb-2"
							information={
								content[DealRegistrationColumnKey.ACCOUNT_NAME]
							}
							label="Account Name"
						/>
					</div>
				)}

				{content[DealRegistrationColumnKey.PROSPECT_INDUSTRY] && (
					<ModalFormatedInformation
						className="d-flex mb-2"
						information={
							content[DealRegistrationColumnKey.PROSPECT_INDUSTRY]
						}
						label="Industry"
					/>
				)}

				{content[DealRegistrationColumnKey.PROSPECT_ADDRESS] && (
					<ModalFormatedInformation
						className="d-flex mb-4"
						information={
							content[DealRegistrationColumnKey.PROSPECT_ADDRESS]
						}
						label="Address"
					/>
				)}

				{content[DealRegistrationColumnKey.PRIMARY_PROSPECT_NAME] && (
					<div>
						<div className="mb-0 text-paragraph-md">
							Primary Prospect Contact
						</div>

						<hr className="mt-0" />

						<ModalFormatedInformation
							className="d-flex mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_NAME
								]
							}
							label="Name"
						/>
					</div>
				)}

				{content[DealRegistrationColumnKey.PRIMARY_PROSPECT_EMAIL] && (
					<ModalFormatedInformation
						className="d-flex mb-2"
						information={
							content[
								DealRegistrationColumnKey.PRIMARY_PROSPECT_EMAIL
							]
						}
						label="Email"
					/>
				)}

				{content[DealRegistrationColumnKey.PRIMARY_PROSPECT_PHONE] && (
					<ModalFormatedInformation
						className="d-flex mb-2"
						information={
							content[
								DealRegistrationColumnKey.PRIMARY_PROSPECT_PHONE
							]
						}
						label="Phone"
					/>
				)}

				{content[
					DealRegistrationColumnKey.PRIMARY_PROSPECT_DEPARTMENT
				] && (
					<ModalFormatedInformation
						className="d-flex mb-2"
						information={
							content[
								DealRegistrationColumnKey
									.PRIMARY_PROSPECT_DEPARTMENT
							]
						}
						label="Department"
					/>
				)}

				{content[
					DealRegistrationColumnKey.PRIMARY_PROSPECT_JOB_ROLE
				] && (
					<ModalFormatedInformation
						className="d-flex mb-4"
						information={
							content[
								DealRegistrationColumnKey
									.PRIMARY_PROSPECT_JOB_ROLE
							]
						}
						label="Role"
					/>
				)}

				{content[DealRegistrationColumnKey.ADDITIONAL_CONTACTS] && (
					<div>
						<div className="mb-0 text-paragraph-md">
							Additional Contact
						</div>

						<hr className="mt-0" />

						<ModalFormatedInformation
							className="d-flex mb-4"
							information={
								content[
									DealRegistrationColumnKey
										.ADDITIONAL_CONTACTS
								]
							}
							label="Contact"
						/>
					</div>
				)}

				<div>
					<div className="mb-0 text-paragraph-md">
						Deal Information
					</div>

					<hr className="mt-0" />

					<ModalFormatedInformation
						className="d-flex mb-4"
						information={
							project?.additionalInformationAboutTheOpportunity
								? project?.additionalInformationAboutTheOpportunity
								: '-'
						}
						label="Additional Information about the Opportunity"
					/>
				</div>

				<div>
					<div className="mb-0 text-paragraph-md">
						Project Information
					</div>

					<hr className="mt-0" />

					<ModalFormatedInformation
						className="d-flex mb-2"
						information={
							project?.projectNeed
								? project?.projectNeed.replaceAll(';', '; ')
								: '-'
						}
						label="Project Need"
					/>
				</div>

				<ModalFormatedInformation
					className="d-flex mb-2"
					information={
						project?.projectCategories
							? project?.projectCategories.replaceAll(';', '; ')
							: '-'
					}
					label="Solution Categories"
				/>

				<ModalFormatedInformation
					className="d-flex mb-2"
					information={
						project?.projectTimeline
							? project?.projectTimeline
							: '-'
					}
					label="Project Timeline"
				/>
			</div>

			<div className="d-flex justify-content-end">
				<Button displayType="secondary" onClick={onClose}>
					Close
				</Button>
			</div>
		</ClayModal.Body>
	);
}
