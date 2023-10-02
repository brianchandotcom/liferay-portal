/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useMemo} from 'react';

import PRMForm from '../../common/components/PRMForm/PRMForm';
import PRMFormik from '../../common/components/PRMFormik';
import {ObjectActionName} from '../../common/enums/objectActionName';
import {PermissionActionType} from '../../common/enums/permissionActionType';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import MDFClaimDTO from '../../common/interfaces/dto/mdfClaimDTO';
import {Liferay} from '../../common/services/liferay';
import useGetDocumentFolder from '../../common/services/liferay/headless-delivery/useGetDocumentFolders';
import useGetMDFClaimById from '../../common/services/liferay/object/mdf-claim/useGetMDFClaimById';
import useGetMDFRequestById from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestById';
import {Status} from '../../common/utils/constants/status';
import {getMDFClaimFromDTO} from '../../common/utils/dto/mdf-claim/getMDFClaimFromDTO';
import MDFClaimPage from './components/MDFClaimPage';
import claimSchema from './components/MDFClaimPage/schema/yup';
import useGetMDFRequestIdByHash from './hooks/useGetMDFRequestIdByHash';
import getInitialFormValues from './utils/getInitialFormValues';
import submitForm from './utils/submitForm';

const SECOND_POSITION_AFTER_HASH = 1;
const FOURTH_POSITION_AFTER_HASH = 3;

const MDFClaimForm = () => {
	const {
		data: claimParentFolder,
		isValidating: isValidatingClaimFolder,
	} = useGetDocumentFolder(Liferay.ThemeDisplay.getScopeGroupId(), 'claim');

	const claimParentFolderId = claimParentFolder?.items[0].id;

	const mdfRequestId = useGetMDFRequestIdByHash(SECOND_POSITION_AFTER_HASH);
	const mdfClaimId = useGetMDFRequestIdByHash(FOURTH_POSITION_AFTER_HASH);

	const {
		data: mdfRequest,
		isValidating: isValidatingMDFRequestById,
	} = useGetMDFRequestById(Number(mdfRequestId));

	const {
		data: mdfClaimDTO,
		isValidating: isValidatingMDFClaimById,
	} = useGetMDFClaimById(Number(mdfClaimId));

	const actions = usePermissionActions(ObjectActionName.MDF_CLAIM);

	const hasPermissionToAccess = useMemo(
		() =>
			actions?.some(
				(action) =>
					action === PermissionActionType.CREATE ||
					action === PermissionActionType.UPDATE
			),
		[actions]
	);

	const hasPermissionToByPass = useMemo(
		() =>
			actions?.some(
				(action) =>
					action === PermissionActionType.UPDATE_WO_CHANGE_STATUS
			),
		[actions]
	);

	const currentMDFClaimHasValidStatus =
		mdfClaimDTO?.mdfClaimStatus.key === Status.DRAFT.key ||
		mdfClaimDTO?.mdfClaimStatus.key === Status.REQUEST_MORE_INFO.key;

	const hasPermissionShowForm = mdfClaimId
		? (hasPermissionToAccess && currentMDFClaimHasValidStatus) ||
		  hasPermissionToByPass
		: hasPermissionToAccess;

	const siteURL = useLiferayNavigate();

	const onCancel = () =>
		mdfRequestId &&
		siteURL &&
		Liferay.Util.navigate(`${siteURL}/l/${mdfRequestId}`);

	const mdfClaim =
		mdfClaimDTO && getMDFClaimFromDTO(mdfClaimDTO as MDFClaimDTO);

	if (
		!mdfRequest ||
		(mdfClaimId && !mdfClaimDTO) ||
		isValidatingMDFRequestById ||
		(mdfClaimId && isValidatingMDFClaimById) ||
		isValidatingClaimFolder ||
		!claimParentFolderId ||
		!actions
	) {
		return <ClayLoadingIndicator />;
	}

	if (!hasPermissionShowForm) {
		return (
			<PRMForm name="" title="MDF Claim">
				<div className="d-flex justify-content-center mt-4">
					<ClayAlert
						className="m-0 w-100"
						displayType="info"
						title="Info:"
					>
						This MDF Claim can not be edited.
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

	return (
		<PRMFormik
			initialValues={getInitialFormValues(
				Number(mdfRequestId),
				mdfRequest.currency,
				mdfRequest.mdfReqToActs,
				mdfRequest.totalMDFRequestAmount,
				mdfClaim
			)}
			onSubmit={(values, formikHelpers) =>
				submitForm(
					values,
					formikHelpers,
					claimParentFolderId,
					mdfRequest,
					siteURL,
					Status.PENDING,
					mdfClaimId
						? actions.every(
								(action) =>
									action !==
									PermissionActionType.UPDATE_WO_CHANGE_STATUS
						  )
						: true
				)
			}
		>
			<MDFClaimPage
				mdfRequest={mdfRequest}
				onCancel={onCancel}
				onSaveAsDraft={(values, formikHelpers) =>
					submitForm(
						values,
						formikHelpers,
						claimParentFolderId,
						mdfRequest,
						siteURL,
						Status.DRAFT,
						mdfClaimId
							? actions.every(
									(action) =>
										action !==
										PermissionActionType.UPDATE_WO_CHANGE_STATUS
							  )
							: true
					)
				}
				validationSchema={claimSchema}
			/>
		</PRMFormik>
	);
};

export default MDFClaimForm;
