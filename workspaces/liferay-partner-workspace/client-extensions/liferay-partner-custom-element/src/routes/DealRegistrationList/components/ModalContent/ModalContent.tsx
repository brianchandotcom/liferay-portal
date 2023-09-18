/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayModal from '@clayui/modal';

import ModalFormatedInformation from '../../../../common/components/ModalFormatedInformation';
import {DealRegistrationColumnKey} from '../../../../common/enums/dealRegistrationColumnKey';
import {DealRegistrationItem} from '../../DealRegistrationList';

interface ModalContentProps {
	content: DealRegistrationItem;
	onClose: () => void;
}

export default function ModalContent({content, onClose}: ModalContentProps) {
	return (
		<ClayModal.Body>
			<div className="align-items-center d-flex justify-content-between mb-4">
				<h3 className="col-6 mb-0">Partner Deal Registration</h3>

				<ClayButtonWithIcon
					displayType={null}
					onClick={onClose}
					symbol="times"
				/>
			</div>

			<div className="d-md-flex">
				<div className="col">
					{content[
						DealRegistrationColumnKey.PARTNER_ACCOUNT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PARTNER_ACCOUNT_NAME
								]
							}
							label="Partner Account Name"
						/>
					)}

					{content[DealRegistrationColumnKey.PARTNER_NAME] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[DealRegistrationColumnKey.PARTNER_NAME]
							}
							label="Partner Name"
						/>
					)}

					{content[DealRegistrationColumnKey.CURRENCY] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[DealRegistrationColumnKey.CURRENCY]
							}
							label="Currency"
						/>
					)}

					{content[DealRegistrationColumnKey.DATE_SUBMITTED] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey.DATE_SUBMITTED
								]
							}
							label="Date Submitted"
						/>
					)}

					{content[DealRegistrationColumnKey.STATUS] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[DealRegistrationColumnKey.STATUS]
							}
							label="Status"
						/>
					)}

					{content[DealRegistrationColumnKey.STATUS_DETAIL] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[DealRegistrationColumnKey.STATUS_DETAIL]
							}
							label="Status Detail"
						/>
					)}

					{content[DealRegistrationColumnKey.TYPE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[DealRegistrationColumnKey.TYPE]
							}
							label="Type"
						/>
					)}
				</div>

				<div className="col">
					{content[DealRegistrationColumnKey.ACCOUNT_NAME] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[DealRegistrationColumnKey.ACCOUNT_NAME]
							}
							label="Account Name"
						/>
					)}

					{content[DealRegistrationColumnKey.PROSPECT_INDUSTRY] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_INDUSTRY
								]
							}
							label="Prospect Industry"
						/>
					)}

					{content[DealRegistrationColumnKey.PROSPECT_ADDRESS] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_ADDRESS
								]
							}
							label="Prospect Address"
						/>
					)}

					{content[DealRegistrationColumnKey.PROSPECT_CITY] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[DealRegistrationColumnKey.PROSPECT_CITY]
							}
							label="Prospect City"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PROSPECT_POSTAL_CODE
					] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[
									DealRegistrationColumnKey
										.PROSPECT_POSTAL_CODE
								]
							}
							label="Prospect Postal Code"
						/>
					)}

					{content[DealRegistrationColumnKey.PROSPECT_COUNTRY] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_COUNTRY
								]
							}
							label="Prospect Country"
						/>
					)}

					{content[DealRegistrationColumnKey.PROSPECT_STATE] && (
						<ModalFormatedInformation
							className="col mb-b2"
							information={
								content[
									DealRegistrationColumnKey.PROSPECT_STATE
								]
							}
							label="Prospect State"
						/>
					)}
				</div>

				<div className="col">
					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_NAME
								]
							}
							label="Primary Prospect Name"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_EMAIL
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_EMAIL
								]
							}
							label="Primary Prospect Email"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_PHONE
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_PHONE
								]
							}
							label="Primary Prospect Phone"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_BUSINESS_UNIT
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_BUSINESS_UNIT
								]
							}
							label="Primary Prospect Business Unit"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_DEPARTMENT
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_DEPARTMENT
								]
							}
							label="Primary Prospect Department"
						/>
					)}

					{content[
						DealRegistrationColumnKey.PRIMARY_PROSPECT_JOB_ROLE
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.PRIMARY_PROSPECT_JOB_ROLE
								]
							}
							label="Primary Prospect Job Role"
						/>
					)}

					{content[DealRegistrationColumnKey.ADDITIONAL_CONTACTS] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content[
									DealRegistrationColumnKey
										.ADDITIONAL_CONTACTS
								]
							}
							label="Additional Contacts"
						/>
					)}
				</div>
			</div>

			<div className="d-flex justify-content-end">
				<Button displayType="secondary" onClick={onClose}>
					Close
				</Button>
			</div>
		</ClayModal.Body>
	);
}
