/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from "@clayui/button";

import infoCircleIcon from "../../../assets/icons/info_circle_icon.svg";
import { getSiteURL } from "../../../components/InviteMemberModal/services";
import { Liferay } from "../../../liferay/liferay";
import { StepType } from "../enums/stepType";


interface ProductFooterProps {
  addresses: BillingAddress[];
  cartId?: number;
  enablePurchaseButton: boolean;
  handleGetApp: () => void;
  isFreeApp: boolean;
  sectionProperties: SectionPropertiesType;
  selectedAccount?: Account;
  selectedPaymentMethod: PaymentMethodSelector;
  setStep: (nextStep: StepType) => void;
  sku: SKU;
  step: StepType;
};

type SectionPropertiesType = {
  [key in StepType]: {
    backStep: StepType;
    nextStep: StepType;
  }
}

const ProductFooter = ({
  addresses,
  enablePurchaseButton,
  handleGetApp,
  isFreeApp,
  sectionProperties,
  selectedAccount,
  selectedPaymentMethod,
  setStep,
  sku,
  step,
}: ProductFooterProps) => {
  const getButtonText = () => {
    const isAccountOrLicenseStep = step === "account" || step === "licenses";
    const isPayMethodSelected = selectedPaymentMethod === "pay";
    const isTrialMethodSelected = selectedPaymentMethod === "trial";
    const isOrderMethodSelected = selectedPaymentMethod === "order";

    if (isFreeApp) {
      return "Get This App";
    }
    if (isAccountOrLicenseStep) {
      return "Continue";
    }
    if (isPayMethodSelected) {
      return `Pay $${sku?.price} Now`;
    }
    if (isTrialMethodSelected) {
      return "Start Free Trial";
    }
    if (isOrderMethodSelected) {
      return `Create PO for $${sku.price}`;
    }
  };

  const onCancel = () => {
    Liferay.Util.navigate(getSiteURL());
  };

  const onPrevious = async (previousStep: StepType) => {
    setStep(previousStep);

    return;
  };


  const onContinue = async (nextStep: StepType) => {
    const isAccountStep = step === "account";
    const isPaymentStep = step === "payment";
    const isLicenseStep = step === "licenses";

    if ((!isFreeApp && isAccountStep && selectedAccount) || isLicenseStep) {
      setStep(nextStep);

      return;
    }

   
    if (
      (isFreeApp && selectedAccount) ||
      (isPaymentStep && enablePurchaseButton && addresses)
    ) {
      

      handleGetApp();
    };
  };

  return (
    <div className="d-flex justify-content-between mt-5 pt-2">
      <ClayButton displayType={null} onClick={() => onCancel()}>
        Cancel
      </ClayButton>
      <div className="align-self-end">
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
            {getButtonText()}
          </ClayButton>
        )}
      </div>

      {!isFreeApp && step === "payment" && selectedPaymentMethod === "pay" && (
        <div className="d-flex flex-column">
          <span>You will be redirected to PayPal to complete payment</span>
          <div>
            <img
              alt="Account icon"
              className="get-app-modal-info-icon"
              src={infoCircleIcon}
            />

            <span className="get-app-modal-use-terms">
              Terms, privacy, returns, or contact support. All costs are in US
              Dollars
            </span>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProductFooter;
