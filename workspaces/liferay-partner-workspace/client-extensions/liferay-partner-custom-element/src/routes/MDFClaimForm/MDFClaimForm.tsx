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
import MDFRequestDTO from '../../common/interfaces/dto/mdfRequestDTO';
import LiferayDocumentFolder from '../../common/interfaces/liferayDocumentFolder';
import {Liferay} from '../../common/services/liferay';
import {LiferayAPIs} from '../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../common/services/liferay/object/useGet';
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
	const {data: claimParentFolder, isValidating: isValidatingClaimFolder} =
		useGet<LiferayItems<LiferayDocumentFolder[]>>(
			`/o/${
				LiferayAPIs.HEADERLESS_DELIVERY
			}/sites/${Liferay.ThemeDisplay.getScopeGroupId()}/document-folders/?filter=name eq 'claim'`
		);

	const claimParentFolderId = claimParentFolder?.items[0].id;

	const mdfRequestId = useGetMDFRequestIdByHash(SECOND_POSITION_AFTER_HASH);
	const mdfClaimId = useGetMDFRequestIdByHash(FOURTH_POSITION_AFTER_HASH);
	const {data: mdfRequest, isValidating: isValidatingMDFRequestById} =
		useGet<MDFRequestDTO>(
			mdfRequestId &&
				`/o/${LiferayAPIs.OBJECT}/mdfrequests/${mdfRequestId}?nestedFields=accountEntry,mdfReqToActs,actToBgts,actToMDFClmActs,r_mdfClmToMDFClmActs_c_mdfClaimId,mdfReqToMDFClms&nestedFieldsDepth=5`
		);

	const {data: mdfClaimDTO, isValidating: isValidatingMDFClaimById} =
		useGet<MDFClaimDTO>(
			mdfClaimId &&
				`/o/${LiferayAPIs.OBJECT}/mdfclaims/${mdfClaimId}?nestedFields=mdfClmToMDFClmActs,mdfClmActToMDFClmBgts,mdfClmActToMDFActDocs,mdfClmToMDFClmDocs&nestedFieldsDepth=2`
		);

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

	const onCancel = () => history.back();

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
				mdfRequest.currencyExchangeRate,
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
				hasPermissionShowForm={hasPermissionShowForm}
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
