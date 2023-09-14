/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import { useState } from 'react';

import { getSiteURL } from '../../components/InviteMemberModal/services';
import { Liferay } from '../../liferay/liferay';
import { StepType } from './enums/stepType';

type StepComponent = {
  [key in StepType]?: JSX.Element;
};
const sectionProperties = {
  [StepType.ACCOUNT]: {
    backStep: StepType.ACCOUNT,
    nextStep: StepType.LICENSES,
    title: 'Account Selection',
  },
  [StepType.LICENSES]: {
    backStep: StepType.ACCOUNT,
    nextStep: StepType.PAYMENT,
    title: 'License Selection',
  },
  [StepType.PAYMENT]: {
    backStep: StepType.LICENSES,
    nextStep: StepType.PAYMENT,
    title: 'Payment Method',
  },
};

const GetAPPFlow = () => {
  const [step, setStep] = useState<StepType>(StepType.ACCOUNT);

  const onCancel = () => {
    Liferay.Util.navigate(getSiteURL());
  };

  const onContinue = async (nextStep: StepType) => {
    setStep(nextStep);

    return;
  };

  const onPrevious = async (previousStep: StepType) => {
    setStep(previousStep);

    return;
  };

  const StepFormComponent: StepComponent = {
    [StepType.ACCOUNT]: <h1>aaa</h1>,
    [StepType.LICENSES]: <h1>AAAAAAAAA</h1>,
  };

  return (
    <div className="border d-flex justify-content-center p-5 rounded">
      <div className="">
        <div className="h1 mb-6">{sectionProperties[step].title}</div>
        <div>{StepFormComponent[step]}</div>
        <div className="d-flex justify-content-between mt-5">
          <ClayButton displayType="unstyled" onClick={() => onCancel()}>
            Cancel
          </ClayButton>
          <div>
            {sectionProperties[step].backStep !== step && (
              <ClayButton
                displayType="secondary"
                onClick={() => onPrevious(sectionProperties[step].backStep)}
              >
                Back
              </ClayButton>
            )}
            {sectionProperties[step].nextStep && (
              <ClayButton
                className="ml-5"
                onClick={() => {
                  onContinue(sectionProperties[step].nextStep);
                }}
              >
                Continue
              </ClayButton>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default GetAPPFlow;
