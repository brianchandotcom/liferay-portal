import { Header } from "../../components/Header/Header";
import { RadioCard } from "../../components/RadioCard/RadioCard";
import { Section } from "../../components/Section/Section";

import creditCardIcon from "../../assets/icons/credit-card.svg";
import brightnessEmptyIcon from "../../assets/icons/brightness-empty.svg";

import "./ChoosePricingModelPage.scss";
import { useState } from "react";
import { NewAppPageFooterButtons } from "../../components/NewAppPageFooterButtons/NewAppPageFooterButtons";
import { useAppContext } from "../../manage-app-state/AppManageState";
import { TYPES } from "../../manage-app-state/actionTypes";

interface ChoosePricingModelPageProps {
  onClickBack: () => void;
  onClickContinue: () => void;
}

export function ChoosePricingModelPage({
  onClickBack,
  onClickContinue,
}: ChoosePricingModelPageProps) {
  const [{ priceModel }, dispatch] = useAppContext();

  return (
    <div className="choose-pricing-model-page-container">
      <Header
        title="Choose pricing model"
        description="Select one of the pricing models for your app. This will define how much users will pay and their acquisition experience."
      />

      <Section
        label="App Price"
        required
        tooltip="More Info"
        tooltipText="More Info"
      >
        <div className="choose-pricing-model-page-radio-container">
          <RadioCard
            description="The app is offered in the Marketplace with no charge."
            selected={priceModel === "free"}
            onChange={() => {
              dispatch({
                payload: { value: "free" },
                type: TYPES.UPDATE_APP_PRICE_MODEL,
              });
            }}
            title="FREE"
            tooltip="More Info"
            icon={brightnessEmptyIcon}
          />

          <RadioCard
            description="To enable paid apps, you must be a business and enter payment information in your Marketplace account profile."
            selected={priceModel === "paid"}
            onChange={() => {
              dispatch({
                payload: { value: "paid" },
                type: TYPES.UPDATE_APP_PRICE_MODEL,
              });
            }}
            title="Paid"
            tooltip="More Info"
            icon={creditCardIcon}
          />
        </div>
      </Section>

      <NewAppPageFooterButtons
        onClickBack={() => onClickBack()}
        onClickContinue={() => onClickContinue()}
      />
    </div>
  );
}
