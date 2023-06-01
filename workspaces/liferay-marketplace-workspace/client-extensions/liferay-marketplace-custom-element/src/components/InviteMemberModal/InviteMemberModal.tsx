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

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import './inviteMemberModal.scss';
import { Liferay } from '../../liferay/liferay';
import { getMyUserAccount } from '../../utils/api';
import { createPassword } from '../../utils/createPassword';
import {
  addAdditionalInfo,
  addExistentUserIntoAccount,
  callRolesApi,
  createNewUser,
  getAccountRolesOnAPI,
  getSiteURL,
	getUserByEmail,
} from './services';

interface InviteMemberModalProps {
	handleClose: () => void;
	selectedAccount: Account;
}

interface CheckboxRole {
	isChecked: boolean;
	roleName: string;
}

export function InviteMemberModal({
	handleClose,
	selectedAccount,
}: InviteMemberModalProps) {
	const {observer, onClose} = useModal({
		onClose: () => handleClose(),
	});

	const [formFields, setFormFields] = useState({
		email: '',
		firstName: '',
		lastName: '',
	});
	const [checkboxRoles, setCheckboxRoles] = useState<CheckboxRole[]>([]);
	const [formValid, setFormValid] = useState<boolean>(false);

  const [accountRoles, setAccountRoles] = useState<AccountRole[]>();
  const [userPassword, setUserPassword] = useState<string>('');

	const listOfRoles = ['Account Administrator', 'App Editor'];

  useEffect(() => {
    const mapRoles = listOfRoles.map((role) => {
      return { roleName: role, isChecked: false };
    });
    setCheckboxRoles(mapRoles);
    getAccountRoles();
    setUserPassword(createPassword());
  }, []);

  const jsonBody = {
    alternateName: formFields.email.replace('@', '-'),
    emailAddress: formFields.email,
    familyName: formFields.lastName,
    givenName: formFields.firstName,
    password: userPassword,
  };

  const getCheckedRoles = () => {
    let checkedRole = '';
    for (const checkboxRole of checkboxRoles) {
      if (checkboxRole.isChecked) {
        checkedRole = checkedRole + checkboxRole.roleName + '/';
      }
    }

    return checkedRole;
  };

  const getAccountRoles = async () => {
    const roles = await getAccountRolesOnAPI(selectedAccount.id);
    setAccountRoles(roles);
  };

  const addAccountRolesToUser = async (user: UserAccount) => {
    for (const checkboxRole of checkboxRoles) {
      if (checkboxRole.isChecked) {
        const matchingAccountRole = accountRoles?.find(
          (accountRole: AccountRole) => accountRole.name == 'Invited Member'
        );
        if (matchingAccountRole) {
          await callRolesApi(
            selectedAccount.id,
            matchingAccountRole.id,
            user.id
          );
        }
      }
    }
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    const form = event.target as HTMLFormElement;
    let user: UserAccount;
    if (formValid) {
      user = await getUserByEmail(formFields.email);
      if (!user) {
        await createNewUser(jsonBody);
      }
      user = await getUserByEmail(formFields.email);
      await addExistentUserIntoAccount(selectedAccount.id, formFields.email);
      const myUser = await getMyUserAccount();
      await addAccountRolesToUser(user);
      await addAdditionalInfo(
        false,
        user.id,
        selectedAccount.name,
        selectedAccount.id,
        formFields.email,
        userPassword,
        formFields.firstName,
        myUser.givenName,
        Liferay.ThemeDisplay.getPortalURL() +
          '/c/login?redirect=' +
          getSiteURL(),
        getCheckedRoles()
      );
      onClose()
    }
  };

	const validateForm = (checkboxValues: CheckboxRole[]) => {
		const isValid = checkboxValues.some(
			(checkbox: CheckboxRole) => checkbox.isChecked
		);

		setFormValid(isValid);
	};

	const handleCheck = (selectedRoleName: String) => {
		const rolesChecked = checkboxRoles.map((role) => {
			if (selectedRoleName === role.roleName) {
				role.isChecked = !role.isChecked;

				return role;
			}

			return role;
		}, []);
    setCheckboxRoles(rolesChecked);
		validateForm(rolesChecked);
	};

	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>Invite New Member</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm onSubmit={handleSubmit}>
					<ClayForm.Group>
						<div>
							<ClayModal.TitleSection>
								<ClayModal.Title>Invite</ClayModal.Title>
							</ClayModal.TitleSection>

							<hr className="solid"></hr>
						</div>

						<div className="d-flex justify-content-between pb-5">
							<div className="form-group pr-3 w-50">
								<label
									className="control-label pb-1"
									htmlFor="firstName"
								>
									First Name
								</label>

								<ClayInput
									id="firstName"
									onChange={(event) => {
										setFormFields({
											...formFields,
											firstName: event.target.value,
										});
									}}
									required={true}
									type="text"
								/>
							</div>

							<div className="form-group pl-3 w-50">
								<label
									className="control-label pb-1"
									htmlFor="lastName"
								>
									Last Name
								</label>

								<ClayInput
									id="lastName"
									onChange={(event) => {
										setFormFields({
											...formFields,
											lastName: event.target.value,
										});
									}}
									required={true}
									type="text"
								/>
							</div>
						</div>

						<div className="form-group">
							<label
								className="control-label pb-1"
								htmlFor="emailAddress"
							>
								Email
							</label>

							<ClayInput
								id="emailAddress"
								onChange={(event) => {
									setFormFields({
										...formFields,
										email: event.target.value,
									});
								}}
								required={true}
								type="text"
							/>
						</div>
					</ClayForm.Group>

          <ClayForm.Group>
            <div className="pt-4">
              <ClayModal.TitleSection>
                <ClayModal.Title className="control-label">
                  Role
                </ClayModal.Title>
              </ClayModal.TitleSection>

              <hr className="solid"></hr>
            </div>

            <div>
              {listOfRoles.map((role, index) => {
                return (
                  <ClayCheckbox
                    checked={checkboxRoles[index]?.isChecked}
                    label={role}
                    onChange={() => handleCheck(role)}
                    required={!formValid}
                    value={role}
                  />
                );
              })}
            </div>
          </ClayForm.Group>

          <ClayButton.Group
            className="d-flex justify-content-between justify-content-lg-end modal-footer"
            spaced
          >
            <ClayButton
              className="cancelButton"
              onClick={() => onClose()}
              outline={true}
              type="button"
            >
              Cancel
            </ClayButton>

            <ClayButton type="submit">Send Invite</ClayButton>
          </ClayButton.Group>
        </ClayForm>
      </ClayModal.Body>
    </ClayModal>
  );
}
