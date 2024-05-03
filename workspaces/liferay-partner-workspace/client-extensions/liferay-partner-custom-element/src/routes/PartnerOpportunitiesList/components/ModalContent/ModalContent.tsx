/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';

import ModalFormatedInformation from '../../../../common/components/ModalFormatedInformation';
import {PartnerOpportunitiesColumnKey} from '../../../../common/enums/partnerOpportunitiesColumnKey';
import PartnerOpportunitiesItem from '../../interfaces/partnerOpportunitiesItem';

interface IProps {
	content: PartnerOpportunitiesItem | undefined;
	onClose: () => void;
}

export default function ModalContent({content, onClose}: IProps) {
	return (
		<ClayModal.Body>
			<div className="align-items-center d-flex justify-content-between mb-4">
				<h3 className="col-6 mb-0">Opportunity Details</h3>

				<ClayButtonWithIcon
					aria-label="Close"
					displayType={null}
					onClick={onClose}
					symbol="times"
				/>
			</div>

			{!content && <ClayLoadingIndicator />}
			<div className="d-md-flex">
				<div className="col">
					{content?.[
						PartnerOpportunitiesColumnKey.PARTNER_ACCOUNT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.PARTNER_ACCOUNT_NAME
								] as string
							}
							label="Partner Account Name"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.CURRENCY] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.CURRENCY
								] as string
							}
							label="Currency"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.PARTNER_NAME] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.PARTNER_NAME
								] as string
							}
							label="Partner Name"
						/>
					)}

					{content?.[
						PartnerOpportunitiesColumnKey.OPPORTUNITY_ACCOUNT_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.OPPORTUNITY_ACCOUNT_NAME
								] as string
							}
							label="Account Name"
						/>
					)}

					{content?.[
						PartnerOpportunitiesColumnKey.SUBSCRIPTION_TERM
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.SUBSCRIPTION_TERM
								] as string
							}
							label="Subscription Term"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.CLOSE_DATE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.CLOSE_DATE
								] as string
							}
							label="Close Date"
						/>
					)}

					{content?.[
						PartnerOpportunitiesColumnKey.SUBSCRIPTION_ARR
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.SUBSCRIPTION_ARR
								] as string
							}
							label="Subscription ARR"
						/>
					)}
				</div>

				<div className="col">
					{content?.[
						PartnerOpportunitiesColumnKey.PARTNER_REP_NAME
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.PARTNER_REP_NAME
								] as string
							}
							label="Partner Rep Name"
						/>
					)}

					{content?.[
						PartnerOpportunitiesColumnKey.PARTNER_REP_EMAIL
					] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey
										.PARTNER_REP_EMAIL
								] as string
							}
							label="Partner Rep Email"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.LIFERAY_REP] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.LIFERAY_REP
								] as string
							}
							label="Liferay Rep"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.STAGE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.STAGE
								] as string
							}
							label="Stage"
						/>
					)}

					{content?.[PartnerOpportunitiesColumnKey.TYPE] && (
						<ModalFormatedInformation
							className="col mb-2"
							information={
								content?.[
									PartnerOpportunitiesColumnKey.TYPE
								] as string
							}
							label="Type"
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
