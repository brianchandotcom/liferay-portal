/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import {SolutionTypes} from '../../../../../enums/Product';
import {ProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import ActivationKeyFormCMP from './ActivationKeyFormCMP';
import ActivationKeyFormDXP from './ActivationKeyFormDXP';

export default function ActivationKeyForm() {
	const {solutionTypeSpecificationValue} =
		useOutletContext<ProductPurchaseOutletContext>();

	if (solutionTypeSpecificationValue === SolutionTypes.CMP) {
		return <ActivationKeyFormCMP />;
	}

	if (solutionTypeSpecificationValue === SolutionTypes.DXP) {
		return <ActivationKeyFormDXP />;
	}

	return null;
}
