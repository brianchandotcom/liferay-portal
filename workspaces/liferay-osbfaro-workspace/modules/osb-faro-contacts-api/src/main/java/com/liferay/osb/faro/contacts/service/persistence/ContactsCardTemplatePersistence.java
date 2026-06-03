/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.service.persistence;

import com.liferay.osb.faro.contacts.exception.NoSuchContactsCardTemplateException;
import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the contacts card template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see ContactsCardTemplateUtil
 * @generated
 */
@ProviderType
public interface ContactsCardTemplatePersistence
	extends BasePersistence<ContactsCardTemplate> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ContactsCardTemplateUtil} to access the contacts card template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the contacts card templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findByGroupId(long groupId);

	/**
	 * Returns a range of all the contacts card templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @return the range of matching contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the contacts card templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContactsCardTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the contacts card templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContactsCardTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts card template
	 * @throws NoSuchContactsCardTemplateException if a matching contacts card template could not be found
	 */
	public ContactsCardTemplate findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContactsCardTemplate> orderByComparator)
		throws NoSuchContactsCardTemplateException;

	/**
	 * Returns the first contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching contacts card template, or <code>null</code> if a matching contacts card template could not be found
	 */
	public ContactsCardTemplate fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ContactsCardTemplate>
			orderByComparator);

	/**
	 * Returns the last contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts card template
	 * @throws NoSuchContactsCardTemplateException if a matching contacts card template could not be found
	 */
	public ContactsCardTemplate findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContactsCardTemplate> orderByComparator)
		throws NoSuchContactsCardTemplateException;

	/**
	 * Returns the last contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching contacts card template, or <code>null</code> if a matching contacts card template could not be found
	 */
	public ContactsCardTemplate fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ContactsCardTemplate>
			orderByComparator);

	/**
	 * Returns the contacts card templates before and after the current contacts card template in the ordered set where groupId = &#63;.
	 *
	 * @param contactsCardTemplateId the primary key of the current contacts card template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next contacts card template
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	public ContactsCardTemplate[] findByGroupId_PrevAndNext(
			long contactsCardTemplateId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<ContactsCardTemplate> orderByComparator)
		throws NoSuchContactsCardTemplateException;

	/**
	 * Removes all the contacts card templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of contacts card templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching contacts card templates
	 */
	public int countByGroupId(long groupId);

	/**
	 * Caches the contacts card template in the entity cache if it is enabled.
	 *
	 * @param contactsCardTemplate the contacts card template
	 */
	public void cacheResult(ContactsCardTemplate contactsCardTemplate);

	/**
	 * Caches the contacts card templates in the entity cache if it is enabled.
	 *
	 * @param contactsCardTemplates the contacts card templates
	 */
	public void cacheResult(
		java.util.List<ContactsCardTemplate> contactsCardTemplates);

	/**
	 * Creates a new contacts card template with the primary key. Does not add the contacts card template to the database.
	 *
	 * @param contactsCardTemplateId the primary key for the new contacts card template
	 * @return the new contacts card template
	 */
	public ContactsCardTemplate create(long contactsCardTemplateId);

	/**
	 * Removes the contacts card template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template that was removed
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	public ContactsCardTemplate remove(long contactsCardTemplateId)
		throws NoSuchContactsCardTemplateException;

	public ContactsCardTemplate updateImpl(
		ContactsCardTemplate contactsCardTemplate);

	/**
	 * Returns the contacts card template with the primary key or throws a <code>NoSuchContactsCardTemplateException</code> if it could not be found.
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template
	 * @throws NoSuchContactsCardTemplateException if a contacts card template with the primary key could not be found
	 */
	public ContactsCardTemplate findByPrimaryKey(long contactsCardTemplateId)
		throws NoSuchContactsCardTemplateException;

	/**
	 * Returns the contacts card template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contactsCardTemplateId the primary key of the contacts card template
	 * @return the contacts card template, or <code>null</code> if a contacts card template with the primary key could not be found
	 */
	public ContactsCardTemplate fetchByPrimaryKey(long contactsCardTemplateId);

	/**
	 * Returns all the contacts card templates.
	 *
	 * @return the contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findAll();

	/**
	 * Returns a range of all the contacts card templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @return the range of contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the contacts card templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContactsCardTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the contacts card templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ContactsCardTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of contacts card templates
	 * @param end the upper bound of the range of contacts card templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of contacts card templates
	 */
	public java.util.List<ContactsCardTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ContactsCardTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the contacts card templates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of contacts card templates.
	 *
	 * @return the number of contacts card templates
	 */
	public int countAll();

}