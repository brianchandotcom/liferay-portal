/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence;

import com.liferay.osb.faro.exception.NoSuchFaroNotificationException;
import com.liferay.osb.faro.model.FaroNotification;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the faro notification service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @see FaroNotificationUtil
 * @generated
 */
@ProviderType
public interface FaroNotificationPersistence
	extends BasePersistence<FaroNotification> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FaroNotificationUtil} to access the faro notification persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the faro notifications where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @return the matching faro notifications
	 */
	public java.util.List<FaroNotification> findByLtCreateTime(long createTime);

	/**
	 * Returns a range of all the faro notifications where createTime &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByLtCreateTime(
		long createTime, int start, int end);

	/**
	 * Returns an ordered range of all the faro notifications where createTime &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByLtCreateTime(
		long createTime, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications where createTime &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByLtCreateTime(
		long createTime, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByLtCreateTime_First(
			long createTime,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the first faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByLtCreateTime_First(
		long createTime,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the last faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByLtCreateTime_Last(
			long createTime,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the last faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByLtCreateTime_Last(
		long createTime,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	public FaroNotification[] findByLtCreateTime_PrevAndNext(
			long faroNotificationId, long createTime,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Removes all the faro notifications where createTime &lt; &#63; from the database.
	 *
	 * @param createTime the create time
	 */
	public void removeByLtCreateTime(long createTime);

	/**
	 * Returns the number of faro notifications where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @return the number of matching faro notifications
	 */
	public int countByLtCreateTime(long createTime);

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @return the matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type);

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type, int start,
		int end);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByG_GtC_O_T_First(
			long groupId, long createTime, long ownerId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByG_GtC_O_T_First(
		long groupId, long createTime, long ownerId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByG_GtC_O_T_Last(
			long groupId, long createTime, long ownerId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByG_GtC_O_T_Last(
		long groupId, long createTime, long ownerId, String type,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	public FaroNotification[] findByG_GtC_O_T_PrevAndNext(
			long faroNotificationId, long groupId, long createTime,
			long ownerId, String type,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @return the matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type);

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type, int start,
		int end);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 */
	public void removeByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type);

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @return the number of matching faro notifications
	 */
	public int countByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type);

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @return the number of matching faro notifications
	 */
	public int countByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type);

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype);

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype, int start, int end);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByG_GtC_O_T_S_First(
			long groupId, long createTime, long ownerId, String type,
			String subtype,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByG_GtC_O_T_S_First(
		long groupId, long createTime, long ownerId, String type,
		String subtype,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByG_GtC_O_T_S_Last(
			long groupId, long createTime, long ownerId, String type,
			String subtype,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByG_GtC_O_T_S_Last(
		long groupId, long createTime, long ownerId, String type,
		String subtype,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	public FaroNotification[] findByG_GtC_O_T_S_PrevAndNext(
			long faroNotificationId, long groupId, long createTime,
			long ownerId, String type, String subtype,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype);

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype, int start, int end);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 */
	public void removeByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype);

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	public int countByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype);

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	public int countByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype);

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype);

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, int start, int end);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByG_GtC_O_R_T_S_First(
			long groupId, long createTime, long ownerId, boolean read,
			String type, String subtype,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByG_GtC_O_R_T_S_First(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	public FaroNotification findByG_GtC_O_R_T_S_Last(
			long groupId, long createTime, long ownerId, boolean read,
			String type, String subtype,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	public FaroNotification fetchByG_GtC_O_R_T_S_Last(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	public FaroNotification[] findByG_GtC_O_R_T_S_PrevAndNext(
			long faroNotificationId, long groupId, long createTime,
			long ownerId, boolean read, String type, String subtype,
			com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
				orderByComparator)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype);

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype, int start, int end);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	public java.util.List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 */
	public void removeByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype);

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	public int countByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype);

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	public int countByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype);

	/**
	 * Caches the faro notification in the entity cache if it is enabled.
	 *
	 * @param faroNotification the faro notification
	 */
	public void cacheResult(FaroNotification faroNotification);

	/**
	 * Caches the faro notifications in the entity cache if it is enabled.
	 *
	 * @param faroNotifications the faro notifications
	 */
	public void cacheResult(java.util.List<FaroNotification> faroNotifications);

	/**
	 * Creates a new faro notification with the primary key. Does not add the faro notification to the database.
	 *
	 * @param faroNotificationId the primary key for the new faro notification
	 * @return the new faro notification
	 */
	public FaroNotification create(long faroNotificationId);

	/**
	 * Removes the faro notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification that was removed
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	public FaroNotification remove(long faroNotificationId)
		throws NoSuchFaroNotificationException;

	public FaroNotification updateImpl(FaroNotification faroNotification);

	/**
	 * Returns the faro notification with the primary key or throws a <code>NoSuchFaroNotificationException</code> if it could not be found.
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	public FaroNotification findByPrimaryKey(long faroNotificationId)
		throws NoSuchFaroNotificationException;

	/**
	 * Returns the faro notification with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification, or <code>null</code> if a faro notification with the primary key could not be found
	 */
	public FaroNotification fetchByPrimaryKey(long faroNotificationId);

	/**
	 * Returns all the faro notifications.
	 *
	 * @return the faro notifications
	 */
	public java.util.List<FaroNotification> findAll();

	/**
	 * Returns a range of all the faro notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of faro notifications
	 */
	public java.util.List<FaroNotification> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the faro notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro notifications
	 */
	public java.util.List<FaroNotification> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro notifications
	 */
	public java.util.List<FaroNotification> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroNotification>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the faro notifications from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of faro notifications.
	 *
	 * @return the number of faro notifications
	 */
	public int countAll();

}