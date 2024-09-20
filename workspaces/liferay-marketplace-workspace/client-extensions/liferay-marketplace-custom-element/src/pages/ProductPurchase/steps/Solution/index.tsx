/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import {SOLUTION_TYPES} from '../../../../enums/Product';
import {ProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';
import AnalyticsProvisioning from './AnalyticsProvisioningForm';
import PreBuiltTrialProvisioning from './PreBuiltTrialProvisioningForm';

const SolutionProvisioningForm = () => {
	const {accounts, selectedAccount, solutionTypeSpecificationValue} =
		useOutletContext<ProductPurchaseOutletContext>();

	const navigate = useNavigate();

	useEffect(() => {
		if (accounts.length > 1 && !selectedAccount) {
			navigate('/', {replace: true});
		}
	}, [selectedAccount, accounts.length, navigate]);

	if (solutionTypeSpecificationValue === SOLUTION_TYPES.ANALYTICS) {
		return <AnalyticsProvisioning />;
	}

	if (solutionTypeSpecificationValue === SOLUTION_TYPES.PRE_BUILT_TRIAL) {
		return <PreBuiltTrialProvisioning />;
	}

	return null;
};

export default SolutionProvisioningForm;
