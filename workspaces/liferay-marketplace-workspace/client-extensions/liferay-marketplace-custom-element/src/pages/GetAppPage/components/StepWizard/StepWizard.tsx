/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import './StepWizard.scss';

type StepWizardProps = {
	className: string;
	currentStep: string;
	stepsInformation: {[keys: string]: {stepTitle: string}};
	wizardSteps: {
		[keys: string]: boolean;
	};
};

const StepWizard = ({
	className,
	currentStep,
	stepsInformation,
	wizardSteps,
}: StepWizardProps) => {
	const stepIcon = (selectedStep: string) => {
		if (selectedStep === currentStep) {
			return 'radio-button';
		}

		if (wizardSteps[selectedStep]) {
			return 'check';
		}

		return 'simple-circle';
	};

	return (
		<div
			className={`d-flex justify-content-between step-wizard ${className}`}
		>
			{Object.keys(stepsInformation).map((step, index) => (
				<div
					className={classNames('step', {
						done: wizardSteps[step],
						selected: step === currentStep,
					})}
					key={index}
				>
					<ClayIcon
						className={classNames('mr-2 step', {
							done: wizardSteps[step],
							selected: step === currentStep,
						})}
						symbol={stepIcon(step)}
					/>

					{stepsInformation[step].stepTitle}
				</div>
			))}
		</div>
	);
};

export default StepWizard;
