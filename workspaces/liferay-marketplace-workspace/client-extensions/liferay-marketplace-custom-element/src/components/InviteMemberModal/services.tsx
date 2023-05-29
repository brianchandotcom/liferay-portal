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
      headers: {
        accept: 'application/json',
        'Content-Type': 'application/json',
        'x-csrf-token': Liferay.authToken,
      },
      method: 'POST',
      body: JSON.stringify(requestBody),
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
      if (data.items.length > 0) {
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
    acceptInviteStatus: acceptInviteStatus,
    r_userToUserAddInfo_userId: r_userToUserAddInfo_userId,
    inviteURL: inviteURL,
    publisherName: publisherName,
    r_accountToUserAdditionalInfos_accountEntryId: publisherId,
    emailOfMember: emailOfMember,
    mothersName: mothersName,
    userFirstName: userFirstName,
    inviterName: inviterName,
    roles: roles,
  };

  const response = await fetch(`/o/c/useradditionalinfos/`, {
    headers: {
      accept: 'application/json',
      'Content-Type': 'application/json',
      'x-csrf-token': Liferay.authToken,
    },
    method: 'POST',
    body: JSON.stringify(additionalInfoBody),
  });
}
