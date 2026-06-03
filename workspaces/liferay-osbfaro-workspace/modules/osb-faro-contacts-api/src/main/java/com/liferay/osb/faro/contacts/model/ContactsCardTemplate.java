/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ContactsCardTemplate service. Represents a row in the &quot;OSBFaro_ContactsCardTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * @author Shinn Lok
 * @see ContactsCardTemplateModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateImpl"
)
@ProviderType
public interface ContactsCardTemplate
	extends ContactsCardTemplateModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.faro.contacts.model.impl.ContactsCardTemplateImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ContactsCardTemplate, Long>
		CONTACTS_CARD_TEMPLATE_ID_ACCESSOR =
			new Accessor<ContactsCardTemplate, Long>() {

				@Override
				public Long get(ContactsCardTemplate contactsCardTemplate) {
					return contactsCardTemplate.getContactsCardTemplateId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ContactsCardTemplate> getTypeClass() {
					return ContactsCardTemplate.class;
				}

			};

}