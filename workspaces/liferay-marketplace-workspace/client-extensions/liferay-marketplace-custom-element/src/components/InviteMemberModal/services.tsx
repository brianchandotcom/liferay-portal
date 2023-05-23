import ClayAlert from '@clayui/alert';
import { Liferay } from '../../liferay/liferay';

type requestBody = {
  alternateName: string;
  emailAddress: string;
  familyName: string;
  givenName: string;
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

export async function createNewUserIntoAccount(
  accountId: number,
  requestBody: requestBody
) {
  try {
    const response = await fetch(
      `/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts`,
      {
        headers: {
          accept: 'application/json',
          'Content-Type': 'application/json',
          'x-csrf-token': Liferay.authToken,
        },
        method: 'POST',
        body: JSON.stringify(requestBody),
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

export async function addExistentUserIntoAccount(
  accountId: number,
  userEmail: string,
  requestBody: requestBody
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
        body: JSON.stringify(requestBody),
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

export async function callRolesApi(accountId: number,roleId: number, userId: string) {
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