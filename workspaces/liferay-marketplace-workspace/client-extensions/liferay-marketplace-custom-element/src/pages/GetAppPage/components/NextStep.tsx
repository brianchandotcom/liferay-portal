/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from "@clayui/button";
import ClayIcon from "@clayui/icon";
import { ReactNode, useState } from "react";

import { AccountAndAppCard } from "../../../components/Card/AccountAndAppCard";
import { Header } from "../../../components/Header/Header";
import { Liferay } from "../../../liferay/liferay";
import {
  baseURL,
  getAccountInfoFromCommerce,
  getCart,
  getCartItems,
} from "../../../utils/api";
import { showAccountImage, showAppImage } from "../../../utils/util";
import catalogIcon from "../../assets/icons/catalog_icon.svg";
import { TypeLicense } from "../enums/TypeLicense";

type TypeNextStepBody = {
  [key in string]?: ReactNode;
};

export function NextStep() {
  const queryString = window.location.search;

  const urlParams = new URLSearchParams(queryString);

  const orderId = 12345;

  const [accountLogo, setAccountLogo] = useState(urlParams.get("logoURL"));
  const [accountName, setAccountName] = useState(urlParams.get("accountName"));
  const [appName, setAppName] = useState(urlParams.get("appName"));
  const appLogo = urlParams.get("appLogoURL");

  const licenseType = "purchaseOrder";

  let cart;
  let cartItems;

  const getCartInfo = async () => {
    if (!appName) {
      cart = await getCart(Number(orderId));
      cartItems = await getCartItems(Number(orderId));

      const item = cartItems.items[0];

      setAppName(item.name);

      const currentAccountCommerce = await getAccountInfoFromCommerce(
        cart.accountId
      );

      setAccountLogo(currentAccountCommerce.logoURL);
      setAccountName(currentAccountCommerce.name);
    }
  };

  getCartInfo();

  const nextStepBody: TypeNextStepBody = {
    [TypeLicense.PAID]: (
      <Header
        description={
          <>
            <p>
              Congratulations on the purchase of <b>{appName}</b>. You will need
              to create a license your app before deploying to your DXP
              instance.
            </p>
            <p>
              {orderId && (
                <span>
                  Your Order ID is: <strong>{orderId}</strong>
                </span>
              )}
            </p>
            <p>
              To license your app, you can click Continue Configuration below.
              Find your Order ID and choose Create License Key. To create a
              license, you must have at least one your instance details
              available - IP address, MAC address or hostname.
            </p>
          </>
        }
        title="Next steps"
      />
    ),
    [TypeLicense.FREE]: (
      <Header
        description={
          <>
            <p>
              Your <b>{appName}</b> app is ready for download.
            </p>
            <p>
              {orderId && (
                <span>
                  Your Order ID is: <strong>{orderId}</strong>
                </span>
              )}
            </p>
            <p>
              To download your app, you can click Continue Configuration below.
              To find your app download, find your Order ID and choose Manage →
              Download LPKG.
            </p>
          </>
        }
        title="Next steps"
      />
    ),
    [TypeLicense.TRIAL]: (
      <Header
        description={
          <>
            <p>
              You will need to create a license for your app before deploying it
              to your DXP instance.
            </p>
            <p>
              {orderId && (
                <span>
                  Your Order ID is: <strong>{orderId}</strong>
                </span>
              )}
            </p>
            <p>
              To license your app, you can click Continue Configuration below.
              Find your Order ID and choose Create License Key. To create a
              license, you must have at least one your instance details
              available - IP address, MAC address or hostname.
            </p>
          </>
        }
        title="Next steps"
      />
    ),
    [TypeLicense.PAYMENT_PENDING]: (
      <Header
        description={
          <>
            <p>
              Congratulations on agreeing to purchase <b>{appName}</b> . Payment
              is required before licensing the app. An invoice will be sent to
              the email address listed in the order. Once payment is processed,
              you will be notified as to the next steps to license your app.
              Your <b>{appName}</b> app is ready for download.
            </p>
            <p>
              {orderId && (
                <span>
                  Your Order ID is: <strong>{orderId}</strong>
                </span>
              )}
            </p>
          </>
        }
        title="Next steps"
      />
    ),
  };

  return (
    <div>
      <div className="align-items-baseline d-flex justify-content-between">
        <AccountAndAppCard
          category="Application"
          logo={
            !appLogo
              ? showAppImage(appLogo as string).replace(
                  (appLogo as string)?.split("/o")[0],
                  baseURL
                )
              : catalogIcon
          }
          title={appName ?? ""}
        ></AccountAndAppCard>

        <ClayIcon
          className="next-step-page-icon px-1"
          symbol="arrow-right-full"
        />

        <AccountAndAppCard
          category="DXP Console"
          logo={showAccountImage(accountLogo as string)}
          title={accountName ?? ""}
        ></AccountAndAppCard>
      </div>

      <div className="border-bottom next-step-page-text">
        {nextStepBody[String(licenseType) || ""]}
      </div>

      <div className="d-flex justify-content-end">
        <ClayButton
          className="new-app-page-footer-button-back"
          onClick={() => {
            window.location.href = `${Liferay.ThemeDisplay.getPortalURL().replace(
              `/next-steps`,
              ""
            )}/customer-dashboard`;
          }}
        >
          Go to Dashboard
        </ClayButton>

        {String(licenseType) !== TypeLicense.PAYMENT_PENDING && (
          <ClayButton className="ml-5 new-app-page-footer-button-continue">
            Continue Configuration
          </ClayButton>
        )}
      </div>
    </div>
  );
}
