/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Formik} from 'formik';
import {useState} from 'react';
import i18n from '~/common/I18n';
import SetupHighPriorityContactForm from '~/common/components/HighPriorityContacts/SetupHighPriorityContact';
import Layout from '~/common/containers/setup-forms/Layout';
import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';
import openToast from '~/common/utils/getToast';
import {STATUS_CODE} from '~/routes/customer-portal/utils/constants';
import {Button} from '../../../../../../common/components';
import {getOrRequestToken} from '../../../../../../common/services/liferay/security/auth/getOrRequestToken';
import getKebabCase from '../../../../../../common/utils/getKebabCase';
import {useCustomerPortal} from '../../../../context';
import {
	addContactRoleLiferay,
	addContactRoleRaysource,
	HIGH_PRIORITY_CONTACT_CATEGORIES,
	removeContactRoleLiferay,
	removeContactRoleRaysource,
	updateLiferayContact,
	updateRaysourceContact
} from '../../../../utils/getHighPriorityContacts';

const IncidentContactEditModal = ({
	close,
	hasCriticalIncidentContact,
	hasPrivacyBreachContact,
	hasSecurityBreachContact,
	leftButton,
	modalFilter,
}) => {
	const [{project}] = useCustomerPortal();

	const [addHighPriorityContact, setAddHighPriorityContacts] = useState([]);

	const [
		removeHighPriorityContacts,
		setRemoveHighPriorityContacts,
	] = useState([]);

	const [isMultiSelectEmpty, setIsMultiSelectEmpty] = useState(false);

	const {client, provisioningServerAPI} = useAppPropertiesContext();
	const [isLoadingSaveButton, setIsLoadingSaveButton] = useState(false);

	const updateMultiSelectEmpty = (error) => {
		setIsMultiSelectEmpty(error);
	};

	const handleSubmit = async () => {
		const handleToastOpening = (contacts, actionType) => {
			contacts?.map((item) => {
				openToast(
					`${item.label}`,
					`${i18n.translate(`high-priority-contact-${actionType}`)}
					<b>${i18n.translate(
						`${getKebabCase(
							actionType === 'added'
								? item.category.name
								: item.labelRole
						)}-contact`
					)}</b>`
				);
			});
		};

		try {
			setIsLoadingSaveButton(true);

			const oAuthToken = await getOrRequestToken();

			try {
				await updateRaysourceContact(
					addContactRoleRaysource,
					addHighPriorityContact,
					oAuthToken,
					project,
					provisioningServerAPI
				);

				await updateLiferayContact(
					addHighPriorityContact,
					addContactRoleLiferay,
					project,
					client
				);
			}
			catch (error) {
				if (error.cause === STATUS_CODE.conflict) {
					await updateLiferayContact(
						addHighPriorityContact,
						addContactRoleLiferay,
						project,
						client
					);
				}
				else {
					throw new Error('Error', {cause: error.cause});
				}
			}

			await updateRaysourceContact(
				removeContactRoleRaysource,
				removeHighPriorityContacts,
				oAuthToken,
				project,
				provisioningServerAPI
			);

			await updateLiferayContact(
				removeHighPriorityContacts,
				removeContactRoleLiferay,
				project,
				client
			);

			handleToastOpening(addHighPriorityContact, 'added');
			handleToastOpening(removeHighPriorityContacts, 'removed');

			setIsLoadingSaveButton(false);
			close();
		}
		catch (error) {
			setIsLoadingSaveButton(false);

			openToast('error', 'an-unexpected-error-occurred', {
				type: 'danger'
			});
		}
	};

	const highPriorityContactCategorySelected = Object.values(
		HIGH_PRIORITY_CONTACT_CATEGORIES
	).find((category) => category === modalFilter);

	const hasHighPriorityContactByCategory = {
		[HIGH_PRIORITY_CONTACT_CATEGORIES.criticalIncident]: hasCriticalIncidentContact,
		[HIGH_PRIORITY_CONTACT_CATEGORIES.privacyBreach]: hasPrivacyBreachContact,
		[HIGH_PRIORITY_CONTACT_CATEGORIES.securityBreach]: hasSecurityBreachContact,
	};

	const highPriorityContactsModalTitle = () => {
		const translationPrefix = !hasHighPriorityContactByCategory[modalFilter]
			? 'select'
			: 'edit';

		return `${i18n.translate(
			translationPrefix
		)} ${highPriorityContactCategorySelected} ${i18n.translate(
			'contacts'
		)}`;
	};

	const highPriorityContactsModalHelper = () => {
		const translationPrefix = !hasHighPriorityContactByCategory[modalFilter]
			? 'add-contacts-to-be-notified-in-the-event-of-a'
			: 'add-or-remove-contacts-to-be-notified-in-the-event-of-a';

		return `${i18n.translate(
			translationPrefix
		)} ${highPriorityContactCategorySelected.toLowerCase()}`;
	};

	return (
		<Layout
			className="pt-1 px-3"
			footerProps={{
				leftButton: (
					<Button
						borderless
						className="text-neutral-10"
						onClick={close}
					>
						{leftButton}
					</Button>
				),
				middleButton: (
					<Button
						disabled={isMultiSelectEmpty || isLoadingSaveButton}
						displayType="primary"
						isLoading={isLoadingSaveButton}
						onClick={handleSubmit}
					>
						{i18n.translate('save')}
					</Button>
				),
			}}
			headerProps={{
				helper: highPriorityContactsModalHelper(),
				title: highPriorityContactsModalTitle(),
			}}
		>
			<SetupHighPriorityContactForm
				addContactList={setAddHighPriorityContacts}
				disableSubmit={updateMultiSelectEmpty}
				filter={modalFilter}
				isCriticalIncidentCard
				removedContactList={setRemoveHighPriorityContacts}
			/>
		</Layout>
	);
};

const IncidentContactEditForm = ({
	close,
	hasCriticalIncidentContact,
	hasPrivacyBreachContact,
	hasSecurityBreachContact,
	leftButton,
	modalFilter,
	props,
}) => {
	return (
		<Formik validateOnChange>
			{(formikProps) => (
				<IncidentContactEditModal
					close={close}
					hasCriticalIncidentContact={hasCriticalIncidentContact}
					hasPrivacyBreachContact={hasPrivacyBreachContact}
					hasSecurityBreachContact={hasSecurityBreachContact}
					leftButton={leftButton}
					modalFilter={modalFilter}
					{...props}
					{...formikProps}
				/>
			)}
		</Formik>
	);
};

export default IncidentContactEditForm;
