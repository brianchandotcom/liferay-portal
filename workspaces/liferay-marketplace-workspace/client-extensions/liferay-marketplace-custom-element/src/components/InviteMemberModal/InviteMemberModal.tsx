import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal, { useModal } from '@clayui/modal';
import ClayForm, { ClayInput, ClayCheckbox } from '@clayui/form';
import { useEffect, useState } from 'react';
import './inviteMemberModal.scss';
import {
  addExistentUserIntoAccount,
  callRolesApi,
  createNewUserIntoAccount,
  getAccountRolesOnAPI,
  getUserByEmail,
} from './services';

interface InviteMemberModalProps {
  handleClose: () => void;
  selectedAccount: Account;
}

interface CheckboxRole {
  roleName: string;
  isChecked: boolean;
}

export function InviteMemberModal({
  handleClose,
  selectedAccount,
}: InviteMemberModalProps) {
  const { observer, onClose } = useModal({
    onClose: () => handleClose(),
  });

  const [formFields, setFormFields] = useState({
    firstName: '',
    lastName: '',
    email: '',
  });
  const [checkboxRoles, setCheckboxRoles] = useState<CheckboxRole[]>([]);
  const [formValid, setFormValid] = useState<boolean>(false);

  const [accountRoles, setAccountRoles] = useState<AccountRole[]>();

  const listOfRoles = ['Account Administrator', 'App Editor'];

  useEffect(() => {
    const mapRoles = listOfRoles.map((role) => {
      return { roleName: role, isChecked: false };
    });
    setCheckboxRoles(mapRoles);
    getAccountRoles();
  }, []);

  const jsonBody = {
    alternateName: formFields.email.replace('@', '-'),
    emailAddress: formFields.email,
    familyName: formFields.lastName,
    givenName: formFields.firstName,
  };

  const getAccountRoles = async () => {
    const roles = await getAccountRolesOnAPI(selectedAccount.id);
    setAccountRoles(roles);
  };

  const addAccountRolesToUser = async (user: any) => {
    for (const checkboxRole of checkboxRoles) {
      if (checkboxRole.isChecked) {
        const matchingAccountRole = accountRoles?.find(
          (accountRole: AccountRole) => accountRole.name == 'Invited Member'
        );
        console.log(matchingAccountRole);
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
    let form = event.target as HTMLFormElement;
    let user = '';
    if (formValid) {
      user = await getUserByEmail(formFields.email);
      if (!user) {
        await createNewUserIntoAccount(selectedAccount.id, jsonBody);
      } else {
        await addExistentUserIntoAccount(
          selectedAccount.id,
          formFields.email,
          jsonBody
        );
      }
      user = await getUserByEmail(formFields.email);
      await addAccountRolesToUser(user);
      setTimeout(() => location.reload(), 200);
    }
  };

  const handleCheck = (selectedRoleName: String) => {
    const rolesChecked = checkboxRoles.map((role, index) => {
      if (selectedRoleName === role.roleName) {
        role.isChecked = !role.isChecked;
        return role;
      }
      return role;
    }, []);
    setCheckboxRoles(rolesChecked);
    validateForm(rolesChecked);
  };

  const validateForm = (checkboxValues: CheckboxRole[]) => {
    const isValid = checkboxValues.some(
      (checkbox: CheckboxRole) => checkbox.isChecked
    );
    setFormValid(isValid);
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
              <div className="pr-3 w-50 form-group">
                <label className="pb-1 control-label" htmlFor="firstName">
                  First Name
                </label>
                <ClayInput
                  id="firstName"
                  type="text"
                  required={true}
                  onChange={(event) => {
                    setFormFields({
                      ...formFields,
                      firstName: event.target.value,
                    });
                  }}
                />
              </div>
              <div className="form-group pl-3 w-50">
                <label className="control-label pb-1" htmlFor="lastName">
                  Last Name
                </label>
                <ClayInput
                  id="lastName"
                  type="text"
                  required={true}
                  onChange={(event) => {
                    setFormFields({
                      ...formFields,
                      lastName: event.target.value,
                    });
                  }}
                />
              </div>
            </div>
            <div className="form-group">
              <label className="control-label pb-1" htmlFor="emailAddress">
                Email
              </label>
              <ClayInput
                id="emailAddress"
                type="text"
                required={true}
                onChange={(event) => {
                  setFormFields({
                    ...formFields,
                    email: event.target.value,
                  });
                }}
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
                    label={role}
                    checked={checkboxRoles[index]?.isChecked}
                    value={role}
                    onChange={() => handleCheck(role)}
                    required={!formValid}
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
              outline={true}
              type="button"
              onClick={() => onClose()}
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
