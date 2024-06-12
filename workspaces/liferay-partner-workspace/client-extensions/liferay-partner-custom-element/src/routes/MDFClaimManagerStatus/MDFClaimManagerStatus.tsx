/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useModal} from '@clayui/modal';
import {useState} from 'react';

import Dropdown from '../../common/components/Dropdown';
import ManagerStatusModalContent from '../../common/components/ManagerStatusModalContent';
import Modal from '../../common/components/Modal';
import PRMFormik from '../../common/components/PRMFormik';
import {ObjectActionName} from '../../common/enums/objectActionName';
import useGetObjectIdBySlash from '../../common/hooks/useGetObjectIdBySlash';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import MDFClaimDTO from '../../common/interfaces/dto/mdfClaimDTO';
import LiferayPicklist from '../../common/interfaces/liferayPicklist';
import MDFClaim from '../../common/interfaces/mdfClaim';
import {LiferayAPIs} from '../../common/services/liferay/common/enums/apis';
import {ResourceName} from '../../common/services/liferay/object/enum/resourceName';
import useGet from '../../common/services/liferay/object/useGet';
import {Status} from '../../common/utils/constants/status';
import ClaimPaidModalContent from './components/ClaimPaidModalContent';
import claimPaidSchema from './schema/yup';
import getDropdownOptions from './util/getDropdownOptions';
import patchClaimStatus from './util/patchClaimStatus';

const MDFClaimManagerStatus = () => {
	const [displayModalStatus, setDisplayModalStatus] =
		useState<LiferayPicklist>();
	const [isSubmitting, setIsSubmitting] = useState(false);

	const actions = usePermissionActions(ObjectActionName.MDF_CLAIM);

	const mdfClaimId = useGetObjectIdBySlash();
	const {data: mdfClaim, isValidating: isValidatingMdfRequest} =
		useGet<MDFClaimDTO>(
			mdfClaimId &&
				`/o/${LiferayAPIs.OBJECT}/${ResourceName.MDF_CLAIM_DXP}/${mdfClaimId}`
		);
	const [patchedStatus, setPatchedStatus] = useState<LiferayPicklist>();

	const mdfClaimStatus = patchedStatus
		? patchedStatus
		: mdfClaim?.mdfClaimStatus;

	const updateClaimStatus = async (selectedStatus: LiferayPicklist) => {
		setDisplayModalStatus(selectedStatus);

		return;
	};

	const dropdownOptions =
		actions &&
		mdfClaimStatus &&
		getDropdownOptions(actions, mdfClaimStatus, updateClaimStatus);

	const {observer, onClose} = useModal({
		onClose: () => {
			setIsSubmitting(false);
			setDisplayModalStatus(undefined);
		},
	});

	const getModal = (
		displayModalStatus: LiferayPicklist,
		mdfClaimId: string
	) => {
		if (displayModalStatus.key === Status.CLAIM_PAID.key) {
			return (
				<Modal center={false} observer={observer}>
					<PRMFormik
						initialValues={
							{
								checkNumber: '',
								claimPaid: 0,
								mdfClaimStatus: Status.CLAIM_PAID,
								paymentDate: '',
							} as Partial<MDFClaim>
						}
						onSubmit={async (values) => {
							setIsSubmitting(true);

							const newClaimStatus = await patchClaimStatus(
								displayModalStatus,
								mdfClaimId,
								values
							);

							if (newClaimStatus) {
								setPatchedStatus(newClaimStatus);
								onClose();
								location.reload();
							}
						}}
					>
						<ClaimPaidModalContent
							currencyExchangeRate={
								mdfClaim?.currencyExchangeRate
							}
							onClose={onClose}
							validationSchema={claimPaidSchema}
						/>
					</PRMFormik>
				</Modal>
			);
		}

		return (
			<Modal center={false} observer={observer}>
				<ManagerStatusModalContent
					displayModalStatus={displayModalStatus}
					id={mdfClaimId}
					onClose={onClose}
					patchStatus={patchClaimStatus}
					setIsSubmitting={setIsSubmitting}
					setPatchedStatus={setPatchedStatus}
				/>
			</Modal>
		);
	};

	if (isValidatingMdfRequest) {
		return (
			<div>
				<ClayLoadingIndicator className="d-flex ml-5" />
			</div>
		);
	}

	return (
		<div>
			<div>
				{dropdownOptions?.length ? (
					<Dropdown
						className="btn btn-secondary"
						closeOnClick={true}
						icon="caret-bottom"
						label={mdfClaimStatus?.name}
						options={!isSubmitting ? dropdownOptions : []}
					></Dropdown>
				) : (
					mdfClaimStatus?.name && (
						<span className="dislay-inline-block mr-1">
							Status: {mdfClaimStatus?.name}
						</span>
					)
				)}
			</div>
			{(mdfClaimStatus?.key === Status.PENDING.key ||
				mdfClaimStatus?.key === Status.IN_DIRECTOR_REVIEW.key) && (
				<p className="mb-0 mt-2 text-neutral-8 text-paragraph-sm">
					Select <strong>In Finance Review</strong> to approve the
					claim
				</p>
			)}
			{displayModalStatus && getModal(displayModalStatus, mdfClaimId)}
		</div>
	);
};

export default MDFClaimManagerStatus;
