/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

import AccountSelection from '../../../components/Checkout/AccountSelection';
import {AccountSelectionStepType} from '../types';

const AccountSelectionStep = ({
	myUserAccount,
	selectedAccount,
	setSelectedAccount,
}: AccountSelectionStepType) => {
	const uniqueAccount = myUserAccount?.accountBriefs?.length === 1;
	const navigate = useNavigate();

	useEffect(() => {
		if (uniqueAccount) {
			navigate('/project-selection');
		}
	}, [uniqueAccount, navigate]);

	return (
		<div className="border mt-2 p-4 pt-2 rounded">
			<h1 className="align-items-center d-flex flex-column mt-2 p-2 pb-5">
				User Accounts Selection
			</h1>
			<div>
				<p className="secondary-text">
					Please select the account you wish to link to your Liferay
					DXP below
				</p>
				<div className="d-flex flex-column justify-content-between">
					<AccountSelection
						checkPersonalAccount
						onSelectAccount={setSelectedAccount}
						selectedAccount={selectedAccount}
						showContactSupport={false}
						userAccount={myUserAccount}
					/>
				</div>
				<div className="d-flex justify-content-end mt-3">
					<ClayButton
						disabled={!selectedAccount}
						onClick={() => navigate('/project-selection')}
					>
						Continue
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default AccountSelectionStep;
