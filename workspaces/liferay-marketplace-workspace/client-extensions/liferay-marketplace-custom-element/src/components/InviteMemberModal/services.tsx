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

import ClayAlert from '@clayui/alert';

import { Liferay } from '../../liferay/liferay';

type requestBody = {
  alternateName: string;
  emailAddress: string;
  familyName: string;
  givenName: string;
  password: string;
};

export const getSiteURL = () => {
  const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

  if (layoutRelativeURL.includes('web')) {
    return layoutRelativeURL.split('/').slice(0, 3).join('/');
  }

  return '';
};

export async function getAccountRolesOnAPI(accountId: number) {
  const accountRoles = await fetch(
    `/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles`,
    {
      headers: {
        accept: 'application/json',
        'x-csrf-token': Liferay.authToken,
      },
    }
  );
  if (accountRoles.ok) {
    const data = await accountRoles.json();

    return data.items;
  }
}

export async function createNewUser(requestBody: requestBody) {
  try {
    const response = await fetch(`/o/headless-admin-user/v1.0/user-accounts`, {
      body: JSON.stringify(requestBody),
      headers: {
        accept: 'application/json',
        'Content-Type': 'application/json',
        'x-csrf-token': Liferay.authToken,
      },
      method: 'POST',
    });
  } catch (error) {
    <ClayAlert.ToastContainer>
      <ClayAlert
        autoClose={5000}
        displayType="danger"
        title="error"
      ></ClayAlert>
    </ClayAlert.ToastContainer>;
  }
}

export async function addExistentUserIntoAccount(
  accountId: number,
  userEmail: string
) {
  try {
    const response = await fetch(
      `/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/by-email-address/${userEmail}`,
      {
        headers: {
          accept: 'application/json',
          'x-csrf-token': Liferay.authToken,
        },
        method: 'POST',
      }
    );
  } catch (error) {
    <ClayAlert.ToastContainer>
      <ClayAlert
        autoClose={5000}
        displayType="danger"
        title="error"
      ></ClayAlert>
    </ClayAlert.ToastContainer>;
  }
}

export async function getUserByEmail(userEmail: String) {
  try {
    const responseFilteredUserList = await fetch(
      `/o/headless-admin-user/v1.0/user-accounts?filter=emailAddress eq '${userEmail}'`,
      {
        headers: {
          accept: 'application/json',
          'x-csrf-token': Liferay.authToken,
        },
      }
    );

    if (responseFilteredUserList.ok) {
      const data = await responseFilteredUserList.json();
      if (data.items.length) {
        return data.items[0];
      }
    }
  } catch (error) {
    <ClayAlert.ToastContainer>
      <ClayAlert
        autoClose={5000}
        displayType="danger"
        title="error"
      ></ClayAlert>
    </ClayAlert.ToastContainer>;
  }
}

export async function callRolesApi(
  accountId: number,
  roleId: number,
  userId: number
) {
  const response = await fetch(
    `/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${userId}`,
    {
      headers: {
        accept: 'application/json',
        'Content-Type': 'application/json',
        'x-csrf-token': Liferay.authToken,
      },
      method: 'POST',
    }
  );
  if (response.ok) {
    return;
  }
}

export async function addAdditionalInfo(
  acceptInviteStatus: boolean,
  r_userToUserAddInfo_userId: number,
  publisherName: string,
  publisherId: number,
  emailOfMember: string,
  mothersName: string,
  userFirstName: string,
  inviterName: string,
  inviteURL: string,
  roles: string
) {
  const additionalInfoBody = {
    acceptInviteStatus,
    emailOfMember,
    inviteURL,
    inviterName,
    mothersName,
    publisherName,
    r_accountEntryToUserAdditionalInfo_accountEntryId: publisherId,
    r_userToUserAddInfo_userId,
    roles,
    userFirstName,
  };

  await fetch(`/o/c/useradditionalinfos/`, {
    body: JSON.stringify(additionalInfoBody),
    headers: {
      'Content-Type': 'application/json',
      accept: 'application/json',
      'x-csrf-token': Liferay.authToken,
    },
    method: 'POST',
  });
}
