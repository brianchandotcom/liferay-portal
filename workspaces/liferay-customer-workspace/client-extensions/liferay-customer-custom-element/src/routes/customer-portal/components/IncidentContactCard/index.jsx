/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import i18n from '../../../../common/I18n';
import useCurrentKoroneikiAccount from '../../../../common/hooks/useCurrentKoroneikiAccount';
import {useCustomerPortal} from '../../context';
import useMyUserAccountByAccountExternalReferenceCode from '../../pages/Project/TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';
import useUserAccountsByAccountExternalReferenceCode from '../../pages/Project/TeamMembers/components/TeamMembersTable/hooks/useUserAccountsByAccountExternalReferenceCode';
import {
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	getContactRoleByFilter,
} from '../../utils/getHighPriorityContacts';
import IncidentContactEditForm from './components/IncidentContactEditModal';
import IncidentContactsButton from './components/IncidentContactsButton';

const IncidentContactCard = ({
	accountSubscriptionGroupsNames,
	hasActiveProduct,
}) => {
	const [{project}] = useCustomerPortal();
	const incidentContactStandard = 2;
	const {loading} = useCurrentKoroneikiAccount();

	const {
		data: myUserAccountData,
	} = useMyUserAccountByAccountExternalReferenceCode(
		loading,
		project?.accountKey
	);
	const [
		,
		{data: userAccountsData, refetch},
	] = useUserAccountsByAccountExternalReferenceCode(project?.accountKey);

	const [userAccount, setUserAccount] = useState(userAccountsData);
	const loggedUserAccount = myUserAccountData?.myUserAccount;
	const hasAdministratorRole =
		loggedUserAccount?.selectedAccountSummary.hasAdministratorRole;

	const [
		currentHighPriorityContacts,
		setCurrentHighPriorityContacts,
	] = useState({
		criticalIncidentContact: [],
		privacyBreachContact: [],
		securityBreachContact: [],
	});

	const [modalFilter, setModalFilter] = useState();
	const [modalMonitoring, setModalMonitoring] = useState();
	const {observer, onOpenChange, open} = useModal();

	const openModal = () => {
		onOpenChange(true);
		setModalMonitoring(true);
	};
	const closeModal = () => {
		onOpenChange(false);
		refetch();
		setModalMonitoring(false);
	};

	const isLXCEnvironment = accountSubscriptionGroupsNames?.includes(
		'Liferay SaaS'
	);

	useEffect(() => {
		setUserAccount(userAccountsData);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [modalMonitoring, userAccountsData, project?.accountKey]);

	const getHighPriorityContactsByFilterRAYSOURCE = async (filter) => {
		return userAccount?.accountUserAccountsByExternalReferenceCode?.items
			.filter((account) => {
				return account?.selectedAccountSummary?.roleBriefs?.some(
					(role) => role?.name === filter
				);
			})
			.map((account) => {
				const {
					emailAddress,
					id,
					name,
					selectedAccountSummary,
					userAccountContactInformation,
				} = account;
				const primaryPhoneNumber = userAccountContactInformation?.telephones.map(
					(phone) => (phone.primary ? phone.phoneNumber : [])
				);

				return {
					contact: primaryPhoneNumber ?? [],
					email: emailAddress,
					id,
					name,
					role: selectedAccountSummary?.roleBriefs.filter(
						({name}) => name === filter
					)[0].name,
					value: id,
				};
			});
	};

	useEffect(() => {
		const fetchHighPriorityContacts = async () => {
			try {
				const updatedFilteredContacts = {};

				for (const filter of Object.keys(
					HIGH_PRIORITY_CONTACT_CATEGORIES
				)) {
					const contacts = await getHighPriorityContactsByFilterRAYSOURCE(
						getContactRoleByFilter(filter)
					);
					updatedFilteredContacts[filter] = contacts;
				}
				setCurrentHighPriorityContacts(updatedFilteredContacts);
			} catch (error) {
				console.error(
					i18n.translate('error-fetching-high-priority-contacts'),
					error
				);
			}
		};

		fetchHighPriorityContacts();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [modalMonitoring, !project?.accountKey, userAccount]);

	const generateContactBody = ({contact, email, name}) => (
		<>
			{userAccount?.accountUserAccountsByExternalReferenceCode?.items
				.length > 0 && (
				<div className="customer-portal-cards" key={email}>
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
			)}
		</>
	);

	const criticalIncidentContacts = currentHighPriorityContacts.criticalIncident?.map(
		generateContactBody
	);

	const privacyBreachContacts = currentHighPriorityContacts?.privacyBreach?.map(
		generateContactBody
	);

	const securityBreachContacts = currentHighPriorityContacts?.securityBreach?.map(
		generateContactBody
	);

	const hasCriticalIncidentContact = !!currentHighPriorityContacts
		.criticalIncident?.length;

	const hasPrivacyBreachContact = !!currentHighPriorityContacts.privacyBreach
		?.length;

	const hasSecurityBreachContact = !!currentHighPriorityContacts
		.securityBreach?.length;

	const handleOnClick = (highPriorityContactsCategory) => {
		setModalFilter(highPriorityContactsCategory);
		openModal();
	};

	return (
		<>
			{hasActiveProduct &&
				userAccount?.accountUserAccountsByExternalReferenceCode?.items
					.length > 0 && (
					<div
						className={classNames('customer-portal-card-footer', {
							'customer-portal-card-footer-style-ac': !isLXCEnvironment,
							'customer-portal-card-footer-style-lxc': isLXCEnvironment,
						})}
					>
						<div className="customer-portal-card-footer-title">
							<h1>{i18n.translate('incident-contacts')}</h1>
						</div>

						<>
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
										<h3 className="pb-1">
											{i18n.translate(
												'critical-incident-contacts'
											)}

											{hasCriticalIncidentContact &&
												hasAdministratorRole && (
													<ClayIcon
														onClick={() =>
															handleOnClick(
																HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident
															)
														}
														symbol="pencil"
													/>
												)}
										</h3>

										<div
											className={classNames('pr-1', {
												'customer-portal-card-description-scroll scroller':
													currentHighPriorityContacts
														.criticalIncident
														?.length >
													incidentContactStandard,
											})}
										>
											{hasCriticalIncidentContact
												? criticalIncidentContacts
												: hasAdministratorRole && (
														<IncidentContactsButton
															onClick={() =>
																handleOnClick(
																	HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident
																)
															}
														/>
												  )}
										</div>
									</div>

									{isLXCEnvironment && (
										<>
											<div className="col customer-portal-card-description pl-4">
												<h3 className="pb-1">
													{i18n.translate(
														'security-breach-contact'
													)}

													{hasSecurityBreachContact &&
														hasAdministratorRole && (
															<ClayIcon
																onClick={() =>
																	handleOnClick(
																		HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach
																	)
																}
																symbol="pencil"
															/>
														)}
												</h3>

												<div
													className={classNames(
														'pr-1',
														{
															'customer-portal-card-description-scroll scroller':
																currentHighPriorityContacts
																	.securityBreach
																	?.length >
																incidentContactStandard,
														}
													)}
												>
													{hasSecurityBreachContact
														? securityBreachContacts
														: hasAdministratorRole && (
																<IncidentContactsButton
																	onClick={() =>
																		handleOnClick(
																			HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach
																		)
																	}
																/>
														  )}
												</div>
											</div>

											<div className="col customer-portal-card-description pl-4">
												<h3 className="pb-1">
													{i18n.translate(
														'privacy-breach-contact'
													)}

													{hasPrivacyBreachContact &&
														hasAdministratorRole && (
															<ClayIcon
																onClick={() =>
																	handleOnClick(
																		HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach
																	)
																}
																symbol="pencil"
															/>
														)}
												</h3>

												<div
													className={classNames(
														'pr-1',
														{
															'customer-portal-card-description-scroll scroller':
																currentHighPriorityContacts
																	.privacyBreach
																	?.length >
																incidentContactStandard,
														}
													)}
												>
													{hasPrivacyBreachContact
														? privacyBreachContacts
														: hasAdministratorRole && (
																<IncidentContactsButton
																	onClick={() =>
																		handleOnClick(
																			HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach
																		)
																	}
																/>
														  )}
												</div>
											</div>
										</>
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
												hasCriticalIncidentContact={
													hasCriticalIncidentContact
												}
												hasPrivacyBreachContact={
													hasPrivacyBreachContact
												}
												hasSecurityBreachContact={
													hasSecurityBreachContact
												}
												leftButton={i18n.translate(
													'cancel'
												)}
												modalFilter={modalFilter}
											/>
										</ClayModal>
									)}
								</div>
							</div>
						</>
					</div>
				)}
		</>
	);
};

export default IncidentContactCard;
