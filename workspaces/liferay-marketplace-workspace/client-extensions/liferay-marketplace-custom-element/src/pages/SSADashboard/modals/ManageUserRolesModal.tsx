/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {zodResolver} from '@hookform/resolvers/zod';
import {useForm} from 'react-hook-form';
import {KeyedMutator} from 'swr';

import MultiSelect from '../../../components/MultiSelect/MultiSelect';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {z} from '../../../schema/zod';
import HeadlessAdminUser from '../../../services/rest/HeadlessAdminUser';
import {ssaRoles} from '../constants';
import {getFilteredItems} from '../utils';

const formSchema = z.object({
	roles: z
		.array(z.object({value: z.string()}))
		.nonempty(i18n.translate('at-least-one-role-must-be-provided')),
});

type FormValues = z.infer<typeof formSchema>;

type ManageUserModalProps = {
	accountERC: string;
	mutate: KeyedMutator<any>;
	onClose: () => void;
	user: UserAccount;
};

const ManageUserModal = ({
	accountERC,
	mutate,
	onClose,
	user,
}: ManageUserModalProps) => {
	const ssaAccount = user.accountBriefs.find(
		(account) => account.externalReferenceCode === accountERC
	);

	const currentRoles =
		ssaAccount?.roleBriefs
			.filter((role) =>
				ssaRoles.some((ssaRole) => ssaRole.key === role.name)
			)
			.map((role) => ({
				key: role.name,
				label: role.name,
				value: role.name,
			})) || [];

	const {formState, handleSubmit, setValue, watch} = useForm<FormValues>({
		defaultValues: {roles: currentRoles},
		resolver: zodResolver(formSchema),
	});

	const selectedRoles = watch('roles');

	const onSubmit = async (formData: FormValues) => {
		try {
			const {items: roles} =
				await HeadlessAdminUser.getAccountRoles(accountERC);

			const intialSet = new Set(currentRoles.map((role) => role.value));
			const newSet = new Set(formData.roles.map((role) => role.value));

			const rolesToAdd = roles.filter(
				(role) => !intialSet.has(role.name) && newSet.has(role.name)
			);

			const rolesToRemove = roles.filter(
				(role) => intialSet.has(role.name) && !newSet.has(role.name)
			);

			await Promise.all([
				...rolesToRemove.map((role) =>
					HeadlessAdminUser.deleteRoleAccountUser(
						Number(Liferay.CommerceContext.account?.accountId),
						role.id,
						user.id
					)
				),
				...rolesToAdd.map((role) =>
					HeadlessAdminUser.sendRoleAccountUser(
						Number(Liferay.CommerceContext.account?.accountId),
						role.id,
						user.id
					)
				),
			]);

			const updatedRoleBriefs = roles.filter((role) =>
				newSet.has(role.name)
			);

			mutate(
				(users?: APIResponse<UserAccount>) => {
					return {
						...users,
						items: users?.items.map((prevUser) => {
							if (prevUser.id !== user.id) {
								return prevUser;
							}

							return {
								...prevUser,
								accountBriefs: prevUser.accountBriefs.map(
									(account) => {
										if (
											account.externalReferenceCode !==
											accountERC
										) {
											return account;
										}

										return {
											...account,
											roleBriefs: updatedRoleBriefs,
										};
									}
								),
							};
						}),
					};
				},
				{revalidate: false}
			);
		}
		catch {
			Liferay.Util.openToast({
				message: i18n.translate('unable-to-assign-roles'),
				title: i18n.translate('error'),
				type: 'danger',
			});
		}

		Liferay.Util.openToast({
			message: i18n.translate('user-roles-successfully-updated'),
			title: i18n.translate('success'),
		});

		onClose();
	};

	return (
		<form id="manage-roles" onSubmit={handleSubmit(onSubmit)}>
			<p>
				{i18n.translate(
					'manage-the-roles-associated-with-this-user-roles-determine-what-features-permissions-and-areas-of-the-platform-the-user-can-access-so-updating-them-allows-you-to-control-their-level-of-access-and-responsibilities'
				)}
			</p>

			<MultiSelect
				disabledClearAll
				errorMessage={formState.errors.roles?.message}
				inputName={i18n.translate('roles')}
				multiselectKey={`roles-${selectedRoles.length}`}
				onItemsChange={(roles) => {
					setValue('roles', roles);
				}}
				selectedItems={selectedRoles}
				sourceItems={getFilteredItems(selectedRoles, ssaRoles)}
			/>
		</form>
	);
};

export default ManageUserModal;
