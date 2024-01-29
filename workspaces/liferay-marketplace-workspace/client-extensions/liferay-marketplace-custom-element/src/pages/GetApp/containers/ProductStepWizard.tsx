/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useGetAppContext} from '../GetAppContextProvider';
import {StepWizardRevamp} from '../components/StepWizard/StepWizard';

const ProductStepWizard = () => {
	const [{currentStep, isCloudApp, steps}] = useGetAppContext();

	return (
		<div className="d-flex justify-content-center mb-6">
			<StepWizardRevamp
				className={isCloudApp ? 'col-10' : 'col-8'}
				currentStep={steps[currentStep]}
				stepIndex={currentStep}
				steps={steps}
			/>
		</div>
	);
};

export default ProductStepWizard;
