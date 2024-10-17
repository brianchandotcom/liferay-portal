/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {Navigate, useLocation, useOutletContext} from 'react-router-dom';
import {useGetMyUserAccount} from '~/common/services/liferay/graphql/user-accounts';
import {useCustomerPortal} from '../../context';
import {getOrRequestToken} from '../../../../common/services/liferay/security/auth/getOrRequestToken';
import {hasAdminOrPartnerManager} from '../ActivationKeysTable/utils/hasAdminOrPartnerManager';
import {hasAdminUserAccount} from '../ActivationKeysTable/utils/hasAdminUserAccount';
import GenerateNewKeySkeleton from './Skeleton';
import ComplimentaryDate from './pages/ComplimentaryDate';
import RequiredInformation from './pages/RequiredInformation';
import SelectSubscription from './pages/SelectSubscription';
import {STEP_TYPES} from './utils/constants/stepType';

const ACTIVATION_ROOT_ROUTER = 'activation';

const GenerateNewKey = ({
	hasComplimentaryKey,
	productGroupName,
	setHasComplimentaryKey,
}) => {
	const {state} = useLocation();
	const {data: myAccount} = useGetMyUserAccount();
	const [oAuthToken, setOAuthToken] = useState();
	const [{project, userAccount}] = useCustomerPortal();
	const [selectedKeyData, setSelectedKeyData] = useState();
	const [step, setStep] = useState(STEP_TYPES.selectDescriptions);
	const {setHasSideMenu} = useOutletContext();
	const [status, setStatus] = useState({
		deactivate: '',
		downloadAggregated: '',
		downloadMultiple: '',
	});

	const [purposeDescription, setPurposeDescription] = useState('');
	const [submitKeyAction, setSubmitKeyAction] = useState({});
	const [licenseEntryTypeName, setLicenseEntryTypeName] = useState('');
	const [expirationRenewDate, setExpirationRenewDate] = useState('');
	const [startRenewDate, setStartRenewDate] = useState('');

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		}

		fetchToken();
	}, []);

	useEffect(() => {
		setHasSideMenu(false);
	}, [setHasSideMenu]);

	const isAdminUserAccount = hasAdminUserAccount(myAccount);

	const isAdminOrPartnerManager = hasAdminOrPartnerManager(
		project,
		userAccount
	);

	if (!isAdminUserAccount && !isAdminOrPartnerManager) {
		return <Navigate replace={true} to={`/${project?.accountKey}`} />;
	}

	const urlPreviousPage = `/${
		project?.accountKey
	}/${ACTIVATION_ROOT_ROUTER}/${productGroupName.toLowerCase()}`;

	const StepLayout = {
		[STEP_TYPES.generateKeys]: (
			<RequiredInformation
				accountKey={project?.accountKey}
				expirationRenewDate={expirationRenewDate}
				hasComplimentaryKey={hasComplimentaryKey}
				licenseEntryTypeName={licenseEntryTypeName}
				oAuthToken={oAuthToken}
				purposeDescription={purposeDescription}
				selectedKeyData={selectedKeyData}
				setStep={setStep}
				startRenewDate={startRenewDate}
				state={state}
				submitKeyAction={submitKeyAction}
				urlPreviousPage={urlPreviousPage}
			/>
		),
		[STEP_TYPES.selectDescriptions]: (
			<SelectSubscription
				accountKey={project?.accountKey}
				activationKeysByStatusPaginatedChecked
				filterCheckedActivationKeys
				hasComplimentaryKey={hasComplimentaryKey}
				identifier
				oAuthToken={oAuthToken}
				productGroupName={productGroupName}
				selectedKeyData={selectedKeyData}
				setExpirationRenewDate={setExpirationRenewDate}
				setHasComplimentaryKey={setHasComplimentaryKey}
				setLicenseEntryTypeName={setLicenseEntryTypeName}
				setSelectedKeyData={setSelectedKeyData}
				setStartRenewDate={setStartRenewDate}
				setStep={setStep}
				setSubmitKeyAction={setSubmitKeyAction}
				state={state}
				urlPreviousPage={urlPreviousPage}
			/>
		),
		[STEP_TYPES.selectInfoComplimentaryKey]: (
			<ComplimentaryDate
				accountKey={project?.accountKey}
				deactivateKeysStatus={status.deactivate}
				filterCheckedActivationKeys
				oAuthToken={oAuthToken}
				productGroupName={productGroupName}
				purposeDescription={purposeDescription}
				selectedKeyData={selectedKeyData}
				setDeactivateKeysStatus={(value) =>
					setStatus((previousStatus) => ({
						...previousStatus,
						deactivate: value,
					}))
				}
				setPurposeDescription={setPurposeDescription}
				setSelectedKeyData={setSelectedKeyData}
				setStep={setStep}
				state={state}
				urlPreviousPage={urlPreviousPage}
			/>
		),
	};

	return StepLayout[step];
};

GenerateNewKey.Skeleton = GenerateNewKeySkeleton;

export default GenerateNewKey;
