/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Size} from '@clayui/modal/lib/types';
import {useState} from 'react';
import {useForm} from 'react-hook-form';

import {Input} from '../../../components/Input/Input';
import {Label} from '../../../components/MarketplaceForm/Label';
import Modal from '../../../components/Modal';
import MultiSelect from '../../../components/MultiSelect/MultiSelect';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {UserRoleTypes} from '../../../enums/Account';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import zodSchema, {zodResolver} from '../../../schema/zod';
import marketplaceOAuth2 from '../../../services/oauth/Marketplace';
import HeadlessAdminUser from '../../../services/rest/HeadlessAdminUser';
import {ssaRoles} from '../constants';
import {getFilteredItems} from '../utils';

export type Item = {
	key: string;
	label: string;
	value: string;
};

type InviteUserModalPops = {
	modal: {
		observer: any;
		onClose: () => void;
		open: boolean;
	};
	mutate: any;
};

type ModalForm = {
	emailAddress: string;
	roles: Item[];
};

const InviteUserModal = ({modal, mutate}: InviteUserModalPops) => {
	const {properties} = useMarketplaceContext();
	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
	} = useForm<ModalForm>({
		resolver: zodResolver(zodSchema.ssaInviteUsers),
	});

	const [selectedItems, setSelectedItems] = useState<Item[]>([]);

	const onSubmit = async (formData: ModalForm) => {
		const [accountRoles, user, userRoles] = await Promise.all([
			HeadlessAdminUser.getAccountRoles(
				properties.accountExternalReferenceCode
			),
			HeadlessAdminUser.postAccountUserAccountByEmailAddress(
				properties.accountExternalReferenceCode,
				formData.emailAddress
			),
			HeadlessAdminUser.getRolesPage(
				new URLSearchParams({pageSize: '-1'})
			),
		]);

		const ssaUser = userRoles.items.find(
			(userRole) => userRole.name === UserRoleTypes.SSA_USER
		);

		const roles = formData.roles.map((role) =>
			accountRoles.items.find(
				(accountRole) => accountRole.name === role.value
			)
		);

		try {
			await Promise.all([
				marketplaceOAuth2.postAssignRoleUserAccount(
					ssaUser?.id as number,
					user.id
				),
				roles.map((role) =>
					HeadlessAdminUser.sendRoleAccountUser(
						Number(Liferay.CommerceContext.account?.accountId),
						role?.id as number,
						user.id
					)
				),
			]);
		}
		catch {
			return Liferay.Util.openToast({
				message: i18n.translate('unable-to-assign-roles'),
				title: i18n.translate('error'),
				type: 'danger',
			});
		}

		Liferay.Util.openToast({
			message: i18n.translate('your-request-completed-successfully'),
			title: i18n.translate('success'),
			type: 'success',
		});

		mutate({revalidate: true});

		return modal.onClose();
	};

	return (
		<Modal
			observer={modal.observer}
			size={'md' as Size}
			title={i18n.translate('add-new-trial')}
			visible={modal.open}
		>
			<form id="invite-form" onSubmit={handleSubmit(onSubmit)}>
				<p>
					{i18n.translate(
						'use-their-email-address-to-invite-them-as-an-ssa-user-or-admin-and-define-their-access-level'
					)}
				</p>

				<Label>{i18n.translate('email')}</Label>

				<Input
					{...register('emailAddress')}
					errorMessage={errors.emailAddress?.message}
					placeholder={i18n.translate('email')}
					required
				/>

				<Label>{i18n.translate('roles')}</Label>

				<MultiSelect
					disabledClearAll
					errorMessage={errors.roles?.message}
					inputName={i18n.translate('roles')}
					multiselectKey={`area-${
						getFilteredItems(selectedItems, ssaRoles).length
					}`}
					onItemsChange={(roles: Item[]) => {
						const filteredRoles = roles.filter((role) =>
							ssaRoles.some((ssaRole) => ssaRole.key === role.key)
						);

						setSelectedItems(filteredRoles);
						setValue('roles', roles);
					}}
					required
					selectedItems={selectedItems}
					sourceItems={getFilteredItems(selectedItems, ssaRoles)}
				/>
			</form>

			<hr />

			<div className="d-flex justify-content-end">
				<Button
					className="mr-2"
					disabled={isSubmitting}
					displayType="secondary"
					onClick={modal.onClose}
				>
					{i18n.translate('cancel')}
				</Button>

				<Button
					disabled={isSubmitting}
					displayType="primary"
					onClick={handleSubmit(onSubmit)}
				>
					<div className="align-items-center d-flex">
						{isSubmitting && (
							<ClayLoadingIndicator className="mr-3 my-0" />
						)}
						{i18n.translate('invite')}
					</div>
				</Button>
			</div>
		</Modal>
	);
};

export default InviteUserModal;
