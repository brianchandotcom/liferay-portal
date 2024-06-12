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
import {ObjectActionName} from '../../common/enums/objectActionName';
import useGetObjectIdBySlash from '../../common/hooks/useGetObjectIdBySlash';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import MDFRequestDTO from '../../common/interfaces/dto/mdfRequestDTO';
import LiferayPicklist from '../../common/interfaces/liferayPicklist';
import {LiferayAPIs} from '../../common/services/liferay/common/enums/apis';
import {ResourceName} from '../../common/services/liferay/object/enum/resourceName';
import useGet from '../../common/services/liferay/object/useGet';
import {Status} from '../../common/utils/constants/status';
import getDropdownOptions from './util/getDropdownOptions';
import patchRequestStatus from './util/patchRequestStatus';

const MDFRequestManagerStatus = () => {
	const [displayModalStatus, setDisplayModalStatus] =
		useState<LiferayPicklist>();
	const [isSubmitting, setIsSubmitting] = useState(false);

	const actions = usePermissionActions(ObjectActionName.MDF_REQUEST);

	const mdfRequestId = useGetObjectIdBySlash();
	const {data: mdfRequest, isValidating: isValidatingMdfRequest} =
		useGet<MDFRequestDTO>(
			mdfRequestId &&
				`/o/${LiferayAPIs.OBJECT}/${ResourceName.MDF_REQUEST_DXP}/${mdfRequestId}`
		);
	const [patchedStatus, setPatchedStatus] = useState<LiferayPicklist>();

	const mdfRequestStatus = patchedStatus
		? patchedStatus
		: mdfRequest?.mdfRequestStatus;

	const updateRequestStatus = async (selectedStatus: LiferayPicklist) => {
		const listDisplayModal = [
			Status.REQUEST_MORE_INFO.key,
			Status.MARKETING_DIRECTOR_REVIEW.key,
			Status.REJECT.key,
		];

		if (
			selectedStatus.key &&
			listDisplayModal.includes(selectedStatus.key)
		) {
			setDisplayModalStatus(selectedStatus);

			return;
		}

		setIsSubmitting(true);

		const newRequestStatus = await patchRequestStatus(
			selectedStatus,
			mdfRequestId
		);

		if (newRequestStatus) {
			setPatchedStatus(newRequestStatus);
		}

		setIsSubmitting(false);

		if (
			newRequestStatus?.key === Status.CANCELED.key ||
			newRequestStatus?.key === Status.COMPLETED.key
		) {
			location.reload();
		}

		return;
	};

	const dropdownOptions =
		actions &&
		mdfRequestStatus &&
		getDropdownOptions(actions, mdfRequestStatus, updateRequestStatus);

	const {observer, onClose} = useModal({
		onClose: () => {
			setIsSubmitting(false);
			setDisplayModalStatus(undefined);
		},
	});

	const getModal = (
		displayModalStatus: LiferayPicklist,
		mdfRequestId: string
	) => {
		return (
			<Modal center={false} observer={observer}>
				<ManagerStatusModalContent
					displayModalStatus={displayModalStatus}
					id={mdfRequestId}
					onClose={onClose}
					patchStatus={patchRequestStatus}
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
						label={mdfRequestStatus?.name}
						options={!isSubmitting ? dropdownOptions : []}
					></Dropdown>
				) : (
					mdfRequestStatus?.name && (
						<span className="dislay-inline-block mr-1">
							Status: {mdfRequestStatus?.name}
						</span>
					)
				)}
			</div>

			{displayModalStatus && getModal(displayModalStatus, mdfRequestId)}
		</div>
	);
};

export default MDFRequestManagerStatus;
