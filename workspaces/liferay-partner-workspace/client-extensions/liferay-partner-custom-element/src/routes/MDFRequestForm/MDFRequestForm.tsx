/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {FormikHelpers, setNestedObjectValues} from 'formik';
import {useMemo, useState} from 'react';

import PRMForm from '../../common/components/PRMForm/PRMForm';
import PRMFormik from '../../common/components/PRMFormik';
import {ObjectActionName} from '../../common/enums/objectActionName';
import {PermissionActionType} from '../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import usePermissionActions from '../../common/hooks/usePermissionActions';
import MDFRequestDTO from '../../common/interfaces/dto/mdfRequestDTO';
import MDFRequest from '../../common/interfaces/mdfRequest';
import {Liferay} from '../../common/services/liferay';
import useGetMDFRequestById from '../../common/services/liferay/object/mdf-requests/useGetMDFRequestById';
import useGetMyUserAccount from '../../common/services/liferay/user-account/useGetMyUserAccount';
import {Status} from '../../common/utils/constants/status';
import {getMDFRequestFromDTO} from '../../common/utils/dto/mdf-request/getMDFRequestFromDTO';
import isObjectEmpty from '../../common/utils/isObjectEmpty';
import useGetMDFRequestIdByHash from '../MDFClaimForm/hooks/useGetMDFRequestIdByHash';
import {StepType} from './enums/stepType';
import Activities from './steps/Activities';
import activitiesSchema from './steps/Activities/schema/yup';
import Goals from './steps/Goals';
import goalsSchema from './steps/Goals/schema/yup';
import Review from './steps/Review/Review';
import submitForm from './utils/submitForm';

const initialFormValues: MDFRequest = {
	activities: [],
	additionalOption: {},
	claimPercent: 0,
	company: {},
	currency: {},
	liferayBusinessSalesGoals: [],
	maxDateActivity: '',
	mdfRequestStatus: Status.DRAFT,
	minDateActivity: '',
	overallCampaignDescription: '',
	overallCampaignName: '',
	partnerCountry: {},
	submitted: false,
	targetAudienceRoles: [],
	targetMarkets: [],
	totalCostOfExpense: 0,
	totalMDFRequestAmount: 0,
};

type StepComponent = {
	[key in StepType]?: JSX.Element;
};

const FIRST_POSITION_AFTER_HASH = 0;

const MDFRequestForm = () => {
	const [step, setStep] = useState<StepType>(StepType.GOALS);
	const siteURL = useLiferayNavigate();

	const mdfRequestId = Number(
		useGetMDFRequestIdByHash(FIRST_POSITION_AFTER_HASH)
	);

	const {data, isValidating} = useGetMDFRequestById(mdfRequestId);
	const {data: myUserAccountData} = useGetMyUserAccount();
	const actions = usePermissionActions(ObjectActionName.MDF_REQUEST);

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

	const currentMDFRequestHasValidStatus =
		data?.mdfRequestStatus.key === Status.DRAFT.key ||
		data?.mdfRequestStatus.key === Status.REQUEST_MORE_INFO.key;

	const hasPermissionShowForm = mdfRequestId
		? (hasPermissionToAccess && currentMDFRequestHasValidStatus) ||
		  hasPermissionToByPass
		: hasPermissionToAccess;

	const onCancel = () =>
		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.MDF_REQUESTS_LISTING}`
		);

	const onContinue = async (
		formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>,
		nextStep: StepType
	) => {
		const validationErrors = await formikHelpers.validateForm();

		if (isObjectEmpty(validationErrors)) {
			setStep(nextStep);

			return;
		}

		formikHelpers.setTouched(setNestedObjectValues(validationErrors, true));
	};

	const onPrevious = (previousStep: StepType) => setStep(previousStep);

	const StepFormComponent: StepComponent = {
		[StepType.GOALS]: (
			<Goals
				disableCompany={Boolean(mdfRequestId) && hasPermissionToByPass}
				onCancel={onCancel}
				onContinue={onContinue}
				onSaveAsDraft={(
					values: MDFRequest,
					formikHelpers: Omit<
						FormikHelpers<MDFRequest>,
						'setFieldValue'
					>
				) =>
					actions &&
					submitForm(
						values,
						formikHelpers,
						siteURL,
						Status.DRAFT,
						mdfRequestId
							? actions.every(
									(action) =>
										action !==
										PermissionActionType.UPDATE_WO_CHANGE_STATUS
							  )
							: true
					)
				}
				validationSchema={goalsSchema}
			/>
		),
		[StepType.ACTIVITIES]: (
			<PRMFormik.Array
				component={Activities}
				name="activities"
				onCancel={onCancel}
				onContinue={onContinue}
				onPrevious={onPrevious}
				onSaveAsDraft={(
					values: MDFRequest,
					formikHelpers: Omit<
						FormikHelpers<MDFRequest>,
						'setFieldValue'
					>
				) =>
					actions &&
					submitForm(
						values,
						formikHelpers,
						siteURL,
						Status.DRAFT,
						mdfRequestId
							? actions.every(
									(action) =>
										action !==
										PermissionActionType.UPDATE_WO_CHANGE_STATUS
							  )
							: true
					)
				}
				validationSchema={activitiesSchema}
			/>
		),
		[StepType.REVIEW]: (
			<Review
				onCancel={onCancel}
				onPrevious={onPrevious}
				onSaveAsDraft={(
					values: MDFRequest,
					formikHelpers: Omit<
						FormikHelpers<MDFRequest>,
						'setFieldValue'
					>
				) =>
					actions &&
					submitForm(
						values,
						formikHelpers,
						siteURL,
						Status.DRAFT,
						mdfRequestId
							? actions.every(
									(action) =>
										action !==
										PermissionActionType.UPDATE_WO_CHANGE_STATUS
							  )
							: true
					)
				}
			/>
		),
	};

	if (
		((isValidating || !data) && mdfRequestId) ||
		!myUserAccountData ||
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
						This MDF Request can not be edited.
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
			initialValues={
				mdfRequestId
					? getMDFRequestFromDTO(data as MDFRequestDTO)
					: initialFormValues
			}
			onSubmit={(values, formikHelpers) =>
				submitForm(
					values,
					formikHelpers,
					siteURL,
					Status.PENDING,
					mdfRequestId
						? actions.every(
								(action) =>
									action !==
									PermissionActionType.UPDATE_WO_CHANGE_STATUS
						  )
						: true
				)
			}
		>
			{StepFormComponent[step]}
		</PRMFormik>
	);
};

export default MDFRequestForm;
