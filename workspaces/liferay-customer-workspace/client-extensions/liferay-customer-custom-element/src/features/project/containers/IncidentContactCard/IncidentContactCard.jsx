/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {useAppContext} from '~/features/project/context';
import useMyUserAccountByAccountExternalReferenceCode from '~/features/project/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';
import useUserAccountsByAccountExternalReferenceCode from '~/features/project/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useUserAccountsByAccountExternalReferenceCode';
import {PRODUCT_TYPES} from '~/features/project/utils/constants';
import {
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	getContactRoleByFilter,
} from '~/features/project/utils/getHighPriorityContacts';
import i18n from '~/utils/I18n';

import IncidentContactEditForm from './components/IncidentContactEditModal';
import IncidentContactsButton from './components/IncidentContactsButton';

import './IncidentContactCard.css';

const INCIDENT_CONTACT_LIMIT = 2;

const filterContactsByRole = (items, roleName) =>
	items
		?.filter((account) =>
			account?.selectedAccountSummary?.roleBriefs?.some(
				(role) => role?.name === roleName
			)
		)
		.map(({emailAddress, id, name, userAccountContactInformation}) => ({
			contact:
				userAccountContactInformation?.telephones.map((phone) =>
					phone.primary ? phone.phoneNumber : []
				) ?? [],
			email: emailAddress,
			id,
			name,
		})) ?? [];

const ContactItem = ({contact, email, name}) => (
	<div className="customer-portal-cards">
		<h4>{email}</h4>

		<h5>{name}</h5>

		{contact.length ? (
			<h5>{contact}</h5>
		) : (
			<p className="text-warning">
				<ClayIcon symbol="warning-full" />
				&nbsp;
				{i18n.translate('phone-number-is-missing')}
			</p>
		)}
	</div>
);

const ContactSection = ({contacts, hasAdministratorRole, onEdit, title}) => {
	const hasContacts = !!contacts?.length;

	return (
		<>
			<h3 className="pb-1">
				{title}

				{hasContacts && hasAdministratorRole && (
					<ClayIcon onClick={onEdit} symbol="pencil" />
				)}
			</h3>

			<div
				className={classNames('pr-1', {
					'customer-portal-card-description-scroll scroller':
						contacts?.length > INCIDENT_CONTACT_LIMIT,
				})}
			>
				{hasContacts
					? contacts.map((contact) => (
							<ContactItem key={contact.id} {...contact} />
						))
					: hasAdministratorRole && (
							<IncidentContactsButton onClick={onEdit} />
						)}
			</div>
		</>
	);
};

const IncidentContactCard = ({
	accountSubscriptionGroupsNames,
	hasActiveProduct,
	hasPaaSExperience,
}) => {
	const [{project}] = useAppContext();

	const {data: myUserAccountData, loading: myUserAccountLoading} =
		useMyUserAccountByAccountExternalReferenceCode(
			project?.accountKey,
			!project?.accountKey
		);
	const [, {data: userAccountsData, loading: userAccountsLoading, refetch}] =
		useUserAccountsByAccountExternalReferenceCode(
			project?.accountKey,
			!project?.accountKey
		);

	const hasAdministratorRole =
		myUserAccountData?.myUserAccount?.selectedAccountSummary
			.hasAdministratorRole;

	const [currentHighPriorityContacts, setCurrentHighPriorityContacts] =
		useState({
			cloudNative: [],
			criticalIncident: [],
			privacyBreach: [],
			securityBreach: [],
		});

	const [modalFilter, setModalFilter] = useState();
	const {observer, onOpenChange, open} = useModal();

	const openModal = () => {
		onOpenChange(true);
	};

	const closeModal = () => {
		onOpenChange(false);
		refetch();
	};

	const isLXCEnvironment = accountSubscriptionGroupsNames?.some((name) =>
		[
			PRODUCT_TYPES.liferayCloud,
			PRODUCT_TYPES.liferayExperienceCloud,
		].includes(name)
	);

	const isCloudNative = accountSubscriptionGroupsNames?.some(
		(name) => name === PRODUCT_TYPES.cloudNative
	);

	const showCloudNativeCard = hasPaaSExperience && !isLXCEnvironment;

	useEffect(() => {
		if (!userAccountsData) {
			return;
		}

		const items =
			userAccountsData.accountUserAccountsByExternalReferenceCode?.items;

		try {
			const updated = {};

			for (const key of Object.keys(HIGH_PRIORITY_CONTACT_CATEGORIES)) {
				updated[key] = filterContactsByRole(
					items,
					getContactRoleByFilter(key)
				);
			}

			setCurrentHighPriorityContacts(updated);
		}
		catch (error) {
			console.error(
				i18n.translate('error-fetching-high-priority-contacts'),
				error
			);
		}
	}, [userAccountsData]);

	const hasCloudNativeContact =
		!!currentHighPriorityContacts.cloudNative?.length;
	const hasCriticalIncidentContact =
		!!currentHighPriorityContacts.criticalIncident?.length;
	const hasPrivacyBreachContact =
		!!currentHighPriorityContacts.privacyBreach?.length;
	const hasSecurityBreachContact =
		!!currentHighPriorityContacts.securityBreach?.length;

	const handleOnClick = (category) => {
		setModalFilter(category);
		openModal();
	};

	return (
		<>
			{userAccountsLoading || myUserAccountLoading ? (
				<ClayLoadingIndicator />
			) : (
				hasActiveProduct &&
				userAccountsData?.accountUserAccountsByExternalReferenceCode
					?.items.length > 0 && (
					<>
						{!isCloudNative && (
							<div
								className={classNames(
									'customer-portal-card-footer',
									{
										'customer-portal-card-footer-style-ac':
											!isLXCEnvironment,
										'customer-portal-card-footer-style-lxc':
											isLXCEnvironment,
									}
								)}
							>
								<div className="customer-portal-card-footer-title">
									<h1>
										{i18n.translate('incident-contacts')}
									</h1>
								</div>

								<div className="customer-portal-card-footer-description">
									<p>
										{i18n.translate(
											'team-members-who-can-be-contacted-with-high-priority-messages'
										)}
									</p>
								</div>

								<div className="w-100">
									<div className="customer-portal-card-title row">
										<div
											className={classNames(
												'customer-portal-card-description',
												{
													'col': !isLXCEnvironment,
													'col-4': isLXCEnvironment,
												}
											)}
										>
											<ContactSection
												contacts={
													currentHighPriorityContacts.criticalIncident
												}
												hasAdministratorRole={
													hasAdministratorRole
												}
												onEdit={() =>
													handleOnClick(
														HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident
													)
												}
												title={i18n.translate(
													'critical-incident-contacts'
												)}
											/>
										</div>

										{isLXCEnvironment && (
											<>
												<div className="col customer-portal-card-description pl-4">
													<ContactSection
														contacts={
															currentHighPriorityContacts.securityBreach
														}
														hasAdministratorRole={
															hasAdministratorRole
														}
														onEdit={() =>
															handleOnClick(
																HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach
															)
														}
														title={i18n.translate(
															'security-breach-contact'
														)}
													/>
												</div>

												<div className="col customer-portal-card-description pl-4">
													<ContactSection
														contacts={
															currentHighPriorityContacts.privacyBreach
														}
														hasAdministratorRole={
															hasAdministratorRole
														}
														onEdit={() =>
															handleOnClick(
																HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach
															)
														}
														title={i18n.translate(
															'privacy-breach-contact'
														)}
													/>
												</div>
											</>
										)}
									</div>
								</div>
							</div>
						)}

						{showCloudNativeCard && (
							<div className="customer-portal-card-footer customer-portal-card-footer-style-ac">
								<div className="customer-portal-card-footer-title">
									<h1>
										{i18n.translate(
											'cloud-native-contacts'
										)}
									</h1>
								</div>

								<div className="customer-portal-card-footer-description">
									<p>
										{i18n.translate(
											'paas-users-will-have-access-to-paas-experience-applications'
										)}
									</p>
								</div>

								<div className="w-100">
									<div className="customer-portal-card-title row">
										<div className="col customer-portal-card-description">
											<ContactSection
												contacts={
													currentHighPriorityContacts.cloudNative
												}
												hasAdministratorRole={
													hasAdministratorRole
												}
												onEdit={() =>
													handleOnClick(
														HIGH_PRIORITY_CONTACT_CATEGORIES.cloudNative
													)
												}
												title={i18n.translate(
													'cloud-native-contacts'
												)}
											/>
										</div>
									</div>
								</div>
							</div>
						)}

						{open && (
							<ClayModal
								center
								className="high-priority-contacts-modal"
								observer={observer}
								onClose={closeModal}
							>
								<IncidentContactEditForm
									close={closeModal}
									hasCloudNativeContact={
										hasCloudNativeContact
									}
									hasCriticalIncidentContact={
										hasCriticalIncidentContact
									}
									hasPrivacyBreachContact={
										hasPrivacyBreachContact
									}
									hasSecurityBreachContact={
										hasSecurityBreachContact
									}
									leftButton={i18n.translate('cancel')}
									modalFilter={modalFilter}
								/>
							</ClayModal>
						)}
					</>
				)
			)}
		</>
	);
};

export default IncidentContactCard;
