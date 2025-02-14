/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type CICType = {
	category?: {
		[key: string]: string;
	};
	email: string;
	filterId?: number;
	id: number;
	key: number;
	label: string;
	value: number;
};

type CICCategoryType = {
	contactsCategory: {[key: string]: string};
};

type roleIDType = {
	__typename: string;
	displayName: string;
	id: number;
	roleId: number;
};

const useHighPriorityContacts = ({
	addContactList,
	currentHighPriorityContacts,
	highPriorityContactsCategory,
	removedContactList,
	rolesId,
}: {
	addContactList: (newValue: CICType[]) => void;
	currentHighPriorityContacts: CICType[];
	highPriorityContactsCategory: CICCategoryType;
	removedContactList: (newValue: CICType[]) => void;
	rolesId: roleIDType[];
}) => {
	const addContacts = (contacts: CICType[], currentContacts: CICType[]) => {
		const contactsWithoutCategory = contacts.filter(
			(contact) =>
				!currentContacts.some(
					(currentContact) => currentContact.id === contact?.id
				)
		);

		return contactsWithoutCategory.map((newContact) => ({
			...newContact,
			category: highPriorityContactsCategory.contactsCategory,
			filterId: rolesId?.filter(
				(role) =>
					role.displayName ===
					highPriorityContactsCategory.contactsCategory.role
			)[0]?.id,
		}));
	};

	const deleteContacts = (
		currentContactsList: CICType[],
		newContactsList: CICType[]
	) => {
		return currentContactsList.filter(
			(currentContact) =>
				!newContactsList.some(
					(newContact) => currentContact.id === newContact?.id
				)
		);
	};

	const updateContacts = (contacts: CICType[]) => {
		const addedContacts = addContacts(
			contacts,
			currentHighPriorityContacts
		);

		const removedContacts = deleteContacts(
			currentHighPriorityContacts,
			contacts
		);

		addContactList(addedContacts);
		removedContactList(removedContacts);
	};

	return {
		updateContacts,
	};
};

export {useHighPriorityContacts};
