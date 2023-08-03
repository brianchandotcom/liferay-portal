/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayModal, {useModal} from '@clayui/modal';
import {InputHTMLAttributes, useCallback, useEffect, useState} from 'react';

import './inviteMemberModal.scss';

import {DisplayType} from '@clayui/alert';
import ClayIcon from '@clayui/icon';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import {Liferay} from '../../liferay/liferay';
import zodSchema, {zodResolver} from '../../schema/zod';
import {getMyUserAccount} from '../../utils/api';
import {createPassword} from '../../utils/createPassword';
import BaseWarning from '../Input/base/BaseWarning';
import BaseWrapper from '../Input/base/BaseWrapper';
import {
	addAdditionalInfo,
	addAdminRegularRole,
	addExistentUserIntoAccount,
	createNewUser,
	getAccountRolesOnAPI,
	getSiteURL,
	getUserByEmail,
	sendRoleAccountUser,
} from './services';

interface InviteMemberModalProps {
	dashboardType: false | 'customer-dashboard' | 'publisher-dashboard';
	handleClose: () => void;
	listOfRoles: string[];
	renderToast: (message: string, title: string, type: DisplayType) => void;
	rolesPermissionDescription: {
		appPermissions: PermissionDescription[];
		dashboardPermissions: PermissionDescription[];
	};
	selectedAccount: Account;
}

export type InviteNewMemberForm = z.infer<typeof zodSchema.invitedNewMember>;

type InputProps = {
	boldLabel?: boolean;
	className?: string;
	description?: string;
	disabled?: boolean;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	options?: {label: string; value: string} | [];
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<InputProps> = ({
	boldLabel,
	className,
	description,
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
	...otherProps
}) => {
	return (
		<BaseWrapper
			boldLabel={boldLabel}
			description={description}
			disabled={disabled}
			error={errors[name]?.message}
			id={id}
			label={label}
			required={required}
		>
			<ClayInput
				className={`rounded-xs ${className}`}
				component={type === 'textarea' ? 'textarea' : 'input'}
				disabled={disabled}
				id={id}
				name={name}
				type={type}
				value={value}
				{...otherProps}
				{...register(name, {onBlur, required})}
			/>
		</BaseWrapper>
	);
};

export function InviteMemberModal({
	dashboardType,
	handleClose,
	listOfRoles,
	renderToast,
	rolesPermissionDescription,
	selectedAccount,
}: InviteMemberModalProps) {
	const {observer, onClose} = useModal({
		onClose: () => handleClose(),
	});

	const {
		clearErrors,
		formState: {errors},
		handleSubmit,
		register,
		setError,
		setValue,
	} = useForm<InviteNewMemberForm>({
		defaultValues: {
			emailAddress: '',
			firstName: '',
			lastName: '',
			roles: [],
		},
		resolver: zodResolver(zodSchema.invitedNewMember),
	});

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const [checkboxRoles, setCheckboxRoles] = useState<CheckboxRole[]>([]);
	const [formValid, setFormValid] = useState<boolean>(false);

	const [accountRoles, setAccountRoles] = useState<AccountRole[]>();
	const [userPassword, setUserPassword] = useState<string>('');

	const getAccountRoles = useCallback(async () => {
		const roles = await getAccountRolesOnAPI(selectedAccount.id);

		setAccountRoles(roles);
	}, [selectedAccount.id]);

	useEffect(() => {
		const mapRoles = listOfRoles.map((role) => {
			return {isChecked: false, roleName: role};
		});

		setCheckboxRoles(mapRoles);
		getAccountRoles();
		setUserPassword(createPassword());
	}, [getAccountRoles, listOfRoles]);

	const finalPathUrl = {
		'customer-dashboard': 'customer-gate',
		'publisher-dashboard': 'loading',
	};

	const emailInviteURL = `${Liferay.ThemeDisplay.getPortalURL()}/c/login?redirect=${getSiteURL()}/${
		finalPathUrl[dashboardType as keyof typeof finalPathUrl]
	}`;

	const getCheckedRoles = () => {
		let checkedRole = '';

		for (const checkboxRole of checkboxRoles) {
			if (checkboxRole.isChecked) {
				checkedRole = checkedRole + checkboxRole.roleName + '/';
			}
		}

		return checkedRole;
	};

	const checkIfUserIsInvited = (user: UserAccount, accountId: number) =>
		!!user.accountBriefs.find(
			(accountBrief) => accountBrief.id === accountId
		);

	const addAccountRolesToUser = async (user: UserAccount) => {
		for (const checkboxRole of checkboxRoles) {
			if (checkboxRole.isChecked) {
				const matchingAccountRole = accountRoles?.find(
					(accountRole: AccountRole) =>
						accountRole.name === 'Invited Member'
				);

				if (matchingAccountRole) {
					await sendRoleAccountUser(
						selectedAccount.id,
						matchingAccountRole.id,
						user.id
					);
				}
			}
		}
	};

	const _submit = async (form: InviteNewMemberForm) => {
		if (!formValid) {
			return;
		}

		const jsonBody = {
			alternateName: form.emailAddress.replace('@', '-'),
			emailAddress: form.emailAddress,
			familyName: form.lastName,
			givenName: form.firstName,
			password: userPassword,
		};

		// eslint-disable-next-line prefer-const
		let [user, myUser] = await Promise.all([
			getUserByEmail(form.emailAddress),
			getMyUserAccount(),
		]);

		if (user && checkIfUserIsInvited(user, selectedAccount.id)) {
			renderToast(
				"There's already a user with this email invited to this account",
				'',
				'danger'
			);

			return onClose();
		}

		if (!user) {
			user = await createNewUser(jsonBody);
		}
		if (
			checkboxRoles.some(
				(role) =>
					role.roleName === 'Account Administrator' && role.isChecked
			)
		) {
			await addAdminRegularRole(user.id);
		}

		await addExistentUserIntoAccount(selectedAccount.id, form.emailAddress);
		await addAccountRolesToUser(user);

		await addAdditionalInfo({
			acceptInviteStatus: false,
			accountName: selectedAccount.name,
			emailOfMember: form.emailAddress,
			inviteURL: emailInviteURL,
			inviterName: myUser.givenName,
			mothersName: userPassword,
			r_accountEntryToUserAdditionalInfo_accountEntryId:
				selectedAccount.id,
			r_userToUserAddInfo_userId: user.id,
			roles: getCheckedRoles(),
			sendType: {key: 'shipping', name: 'Shipping'},
			userFirstName: form.firstName,
		});

		renderToast(
			'invited succesfully',
			`${user.givenName} ${user.familyName}`,
			'success'
		);

		onClose();
	};

	const validateForm = (checkboxValues: CheckboxRole[]) => {
		const isValid = checkboxValues.some(
			(checkbox: CheckboxRole) => checkbox.isChecked
		);
		setFormValid(isValid);
	};

	function isRoleSelected(
		roleCheck: CheckboxRole,
		accountRoles: AccountRole[] | undefined
	) {
		return (
			roleCheck.isChecked &&
			(accountRoles?.some(
				(accountRole) => roleCheck.roleName === accountRole.name
			) ??
				false)
		);
	}

	const handleRoleSelection = (selectedRoleName: string) => {
		clearErrors('roles');
		const rolesChecked = checkboxRoles.map((role) => {
			if (selectedRoleName === role.roleName) {
				role.isChecked = !role.isChecked;

				return role;
			}

			return role;
		}, []);

		const rolesSelected = rolesChecked
			.filter((roleCheck) => isRoleSelected(roleCheck, accountRoles))
			.map((roleCheck) => roleCheck.roleName);

		if (!rolesChecked.length || !rolesSelected.length) {
			setError('roles', {message: 'Please select at least one role'});
		}

		setValue('roles', rolesSelected as any);

		setCheckboxRoles(rolesChecked);
		validateForm(rolesChecked);
	};

	return (
		<ClayModal
			className="modal-dialog-scrollable"
			observer={observer}
			size="lg"
		>
			<ClayModal.Header>Invite New Member</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm className="invite-member-modal">
					<ClayForm.Group>
						<div>
							<ClayModal.TitleSection>
								<ClayModal.Title>Invite</ClayModal.Title>
							</ClayModal.TitleSection>

							<hr className="solid"></hr>
						</div>

						<div className="d-flex justify-content-between">
							<div className="form-group pr-3 w-50">
								<label
									className="control-label pb-1"
									htmlFor="firstName"
								>
									First Name
								</label>

								<Input
									id="firstName"
									name="firstName"
									type="text"
									{...inputProps}
								/>
							</div>

							<div className="form-group pl-3 w-50">
								<label
									className="control-label pb-1"
									htmlFor="lastName"
								>
									Last Name
								</label>

								<Input
									id="lastName"
									name="lastName"
									type="text"
									{...inputProps}
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

							<Input
								id="emailAddress"
								name="emailAddress"
								type="text"
								{...inputProps}
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

							<hr className="solid" />
						</div>

						<div>
							{listOfRoles.map((role, index) => {
								return (
									<ClayCheckbox
										checked={
											checkboxRoles[index]?.isChecked
										}
										key={index}
										label={role}
										name={`roles-${index}`}
										onChange={() =>
											handleRoleSelection(role)
										}
										value={role}
									/>
								);
							})}

							{errors.roles && (
								<div>
									<BaseWarning>
										{errors.roles?.message}
									</BaseWarning>
								</div>
							)}
						</div>
					</ClayForm.Group>

					<ClayForm.Group>
						<ClayModal.TitleSection>
							<ClayModal.Title className="control-label">
								App & Solution Permissions
							</ClayModal.Title>
						</ClayModal.TitleSection>

						<hr className="solid"></hr>

						{rolesPermissionDescription.appPermissions.map(
							(rolePermission, index) => {
								const showCheckIcon = checkboxRoles.some(
									(checkedRole) =>
										checkedRole.isChecked &&
										rolePermission.permittedRoles.includes(
											checkedRole.roleName
										)
								);

								return (
									<div className="p-2 text-muted" key={index}>
										<ClayIcon
											className={
												showCheckIcon
													? 'text-success mr-2'
													: 'mr-2'
											}
											symbol={
												showCheckIcon
													? 'check'
													: 'block'
											}
										/>

										{rolePermission.permissionName}
									</div>
								);
							}
						)}
					</ClayForm.Group>

					<ClayForm.Group>
						<ClayModal.TitleSection>
							<ClayModal.Title className="control-label">
								Dashboard Permissions
							</ClayModal.Title>
						</ClayModal.TitleSection>

						<hr className="solid"></hr>

						{rolesPermissionDescription.dashboardPermissions.map(
							(rolePermission, index) => {
								const showCheckIcon = checkboxRoles.some(
									(checkedRole) =>
										checkedRole.isChecked &&
										rolePermission.permittedRoles.includes(
											checkedRole.roleName
										)
								);

								return (
									<div className="p-2 text-muted" key={index}>
										<ClayIcon
											className={
												showCheckIcon
													? 'text-success mr-2'
													: 'mr-2'
											}
											symbol={
												showCheckIcon
													? 'check'
													: 'block'
											}
										/>

										{rolePermission.permissionName}
									</div>
								);
							}
						)}
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

						<ClayButton
							onClick={handleSubmit(_submit)}
							type="button"
						>
							Send Invite
						</ClayButton>
					</ClayButton.Group>
				</ClayForm>
			</ClayModal.Body>
		</ClayModal>
	);
}
