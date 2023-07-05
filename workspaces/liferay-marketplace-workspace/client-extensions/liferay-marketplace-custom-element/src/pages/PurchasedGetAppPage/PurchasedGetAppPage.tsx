/* eslint-disable @typescript-eslint/no-unused-vars */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayAlert from "@clayui/alert";
import ClayButton from "@clayui/button";
import ClayForm, { ClayCheckbox, ClayInput } from "@clayui/form";
import ClayIcon from "@clayui/icon";
import ClayLink from "@clayui/link";
import { InputHTMLAttributes, useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

import emptyPictureIcon from "../../assets/icons/avatar.svg";
import { Header } from "../../components/Header/Header";
import BaseWrapper from "../../components/Input/base/BaseWrapper";
import zodSchema, { zodResolver } from "../../schema/zod";
import {
  getUserAccount,
  updateMyUserAccount,
  updateUserImage,
} from "../../utils/api";

import "./PurchasedGetAppPage.scss";
import Select from "../../components/Select/Select";
import { getPhones } from "./PurchasedGetAppPageUtil";

type Steps = {
  page: "onboarding" | "customerGateForm";
};

type PurchasedGetAppPage = {
  setStep: React.Dispatch<Steps>;
  user?: UserAccount;
};

type UserForm = z.infer<typeof zodSchema.newCustomer>;

type InputProps = {
  boldLabel?: boolean;
  className?: string;
  disabled?: boolean;
  errors?: any;
  id?: string;
  label?: string;
  name: string;
  options?: { label: string; value: string }[];
  register?: any;
  required?: boolean;
  type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const { origin } = window.location;

const Input: React.FC<InputProps> = ({
  boldLabel,
  className,
  disabled = false,
  errors = {},
  label,
  name,
  register = () => {},
  id = name,
  type,
  value,
  required = false,
  onBlur,
  options,
  ...otherProps
}) => {
  return (
    <BaseWrapper
      boldLabel={boldLabel}
      disabled={disabled}
      error={errors[name]?.message}
      id={id}
      label={label}
      required={required}
    >
      <ClayInput
        className="rounded-xs"
        component={type === "textarea" ? "textarea" : "input"}
        disabled={disabled}
        id={id}
        name={name}
        type={type}
        value={value}
        {...otherProps}
        {...register(name, { onBlur, required })}
      />
    </BaseWrapper>
  );
};

export function PurchasedGetAppPage({ setStep, user }: PurchasedGetAppPage) {
  const inputRef = useRef<HTMLInputElement>(null);

  const {
    clearErrors,
    formState: { errors },
    handleSubmit,
    register,
    setError,
    setValue,
    watch,
  } = useForm<UserForm>({
    defaultValues: {
      ...user,
      accountBriefs: user?.accountBriefs,
      emailAddress: user?.emailAddress,
      familyName: user?.familyName,
      givenName: user?.givenName,
      image: user?.image ?? emptyPictureIcon,
      imageBlob: "",
      newsSubscription: user?.newsSubscription,
    },
    resolver: zodResolver(zodSchema.newCustomer),
  });

  const _submit = async (form: UserForm) => {
    try {
      if (form.imageBlob) {
        const formData = new FormData();

        formData.append("image", form.imageBlob);

        await updateUserImage(Number(user?.id), formData);
      }

      delete form.imageBlob;
      delete form.image;

      await updateMyUserAccount(Number(user?.id), form);

      window.location.href = `${origin}/web/marketplace/loading`;
    } catch (error) {
      console.error(error);
    }
  };

  const inputProps = {
    errors,
    register,
    required: true,
  };

  const newsSubscription = watch("newsSubscription");

  const [phonesFlags, setPhonesFlags] = useState<PhonesFlags[]>();

  // const [currentUserAccount, setCurrentUserAccount] = useState<UserAccount>();

  useEffect(() => {
    // const myUserAccount = async () => {
    //   const items = await getUserAccount();

    //   setCurrentUserAccount(items);
    // };

    const flags = getPhones();

    setPhonesFlags(flags);

    // myUserAccount();
  }, []);

  return (
    <div className="align-items-center d-flex flex-column justify-content-center purchased-get-app-page-container w-100">
      <div className="border p-8 purchased-get-app-page-body rounded">
        <Header description title="Marketplace Account Creation" />

        <ClayForm>
          <div className="align-items-baseline d-flex">
            <div className="align-items-center d-flex">
              <label className="font-weight-bold mr-4 title-label">
                Profile Info
              </label>
            </div>
          </div>

          <hr className="solid" />

          {errors?.image?.message && (
            <ClayAlert displayType="danger">
              {errors?.image?.message.toString()}
            </ClayAlert>
          )}

          <ClayForm.Group>
            <div className="d-flex justify-content-between">
              <div className="form-group mb-0 pr-3 w-50">
                <Input
                  disabled
                  {...inputProps}
                  boldLabel
                  label="First Name"
                  name="givenName"
                />
              </div>

              <div className="form-group mb-0 pl-3 w-50">
                <Input
                  disabled
                  {...inputProps}
                  boldLabel
                  label="Last Name"
                  name="familyName"
                />
              </div>
            </div>

            <div className="form-group mb-5">
              <Input
                {...inputProps}
                boldLabel
                label="Company"
                name="company"
                placeholder="Enter company name"
              />
            </div>

            <div className="form-group mb-5">
              <Input
                {...inputProps}
                boldLabel
                className="p-2"
                label="Industry"
                name="industry"
                options={[
                  { label: "Option 1", value: "option1" },
                  { label: "Option 2", value: "option2" },
                  { label: "Option 3", value: "option3" },
                ]}
                placeholder="Enter job description p-2"
                type="select"
              />
            </div>

            <ClayForm.Group>
              <div className="align-items-baseline d-flex">
                <div className="align-items-center d-flex">
                  <label
                    className="font-weight-bold mr-4 title-label"
                    htmlFor="emailAddress"
                  >
                    Contact Info
                  </label>
                </div>
              </div>

              <hr className="solid" />

              <div className="form-group mb-5">
                <Input
                  disabled
                  {...inputProps}
                  boldLabel
                  label="Email"
                  name="emailAddress"
                  type="email"
                />
              </div>

              <label className="required" htmlFor="phone">
                Phone
              </label>

              <div className="align-items-center d-flex justify-content-between purchased-get-app-page-phone">
                <div className="col-4">
                  <Select
                    {...inputProps}
                    id="phone"
                    name="phoneCode"
                    options={phonesFlags}
                    type="select"
                  />

                  <div className="form-feedback-group">
                    <div className="form-text">Intl. code</div>
                  </div>
                </div>

                <div className="col-4">
                  <Input
                    {...inputProps}
                    className="w-100"
                    name="phoneNumber"
                    placeholder="___–___–____"
                  />

                  <div className="form-feedback-group">
                    <div className="form-text">Phone number</div>
                  </div>
                </div>

                <div className="col-4">
                  <Input
                    {...inputProps}
                    className="mr-0 w-75"
                    name="extension"
                    placeholder="Enter +ext"
                  />

                  <div className="form-feedback-group">
                    <div className="form-text">Extension (optional)</div>
                  </div>
                </div>
              </div>
            </ClayForm.Group>

            <ClayForm.Group>
              <div className="d-flex flex-row-reverse justify-content-end">
                <label
                  className="control-label ml-3 pb-1"
                  htmlFor="newsSubscription"
                >
                  I agree to the <ClayLink>Terms & Conditions</ClayLink>
                </label>

                <ClayCheckbox
                  checked={newsSubscription}
                  id="newsSubscription"
                  onChange={() =>
                    setValue("newsSubscription", !newsSubscription)
                  }
                />
              </div>
            </ClayForm.Group>

            <div className="purchased-get-app-page-button-container">
              <div className="align-items-center d-flex justify-content-between mb-4 w-100">
                <div>
                  <ClayButton
                    displayType="unstyled"
                    onClick={() => {
                      window.location.href = origin;
                    }}
                  >
                    Cancel
                  </ClayButton>
                </div>

                <ClayButton onClick={handleSubmit(_submit)}>
                  Continue
                </ClayButton>
              </div>
            </div>
          </ClayForm.Group>
        </ClayForm>
      </div>
    </div>
  );
}
