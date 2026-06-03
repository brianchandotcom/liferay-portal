/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence;

import com.liferay.osb.faro.exception.NoSuchFaroPreferencesException;
import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the faro preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @see FaroPreferencesUtil
 * @generated
 */
@ProviderType
public interface FaroPreferencesPersistence
	extends BasePersistence<FaroPreferences> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FaroPreferencesUtil} to access the faro preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the faro preferenceses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro preferenceses
	 */
	public java.util.List<FaroPreferences> findByGroupId(long groupId);

	/**
	 * Returns a range of all the faro preferenceses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @return the range of matching faro preferenceses
	 */
	public java.util.List<FaroPreferences> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the faro preferenceses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro preferenceses
	 */
	public java.util.List<FaroPreferences> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro preferenceses where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro preferenceses
	 */
	public java.util.List<FaroPreferences> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro preferences
	 * @throws NoSuchFaroPreferencesException if a matching faro preferences could not be found
	 */
	public FaroPreferences findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
				orderByComparator)
		throws NoSuchFaroPreferencesException;

	/**
	 * Returns the first faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	public FaroPreferences fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
			orderByComparator);

	/**
	 * Returns the last faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro preferences
	 * @throws NoSuchFaroPreferencesException if a matching faro preferences could not be found
	 */
	public FaroPreferences findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
				orderByComparator)
		throws NoSuchFaroPreferencesException;

	/**
	 * Returns the last faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	public FaroPreferences fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
			orderByComparator);

	/**
	 * Returns the faro preferenceses before and after the current faro preferences in the ordered set where groupId = &#63;.
	 *
	 * @param faroPreferencesId the primary key of the current faro preferences
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro preferences
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	public FaroPreferences[] findByGroupId_PrevAndNext(
			long faroPreferencesId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
				orderByComparator)
		throws NoSuchFaroPreferencesException;

	/**
	 * Removes all the faro preferenceses where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of faro preferenceses where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro preferenceses
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the faro preferences where groupId = &#63; and ownerId = &#63; or throws a <code>NoSuchFaroPreferencesException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the matching faro preferences
	 * @throws NoSuchFaroPreferencesException if a matching faro preferences could not be found
	 */
	public FaroPreferences findByG_O(long groupId, long ownerId)
		throws NoSuchFaroPreferencesException;

	/**
	 * Returns the faro preferences where groupId = &#63; and ownerId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	public FaroPreferences fetchByG_O(long groupId, long ownerId);

	/**
	 * Returns the faro preferences where groupId = &#63; and ownerId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching faro preferences, or <code>null</code> if a matching faro preferences could not be found
	 */
	public FaroPreferences fetchByG_O(
		long groupId, long ownerId, boolean useFinderCache);

	/**
	 * Removes the faro preferences where groupId = &#63; and ownerId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the faro preferences that was removed
	 */
	public FaroPreferences removeByG_O(long groupId, long ownerId)
		throws NoSuchFaroPreferencesException;

	/**
	 * Returns the number of faro preferenceses where groupId = &#63; and ownerId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param ownerId the owner ID
	 * @return the number of matching faro preferenceses
	 */
	public int countByG_O(long groupId, long ownerId);

	/**
	 * Caches the faro preferences in the entity cache if it is enabled.
	 *
	 * @param faroPreferences the faro preferences
	 */
	public void cacheResult(FaroPreferences faroPreferences);

	/**
	 * Caches the faro preferenceses in the entity cache if it is enabled.
	 *
	 * @param faroPreferenceses the faro preferenceses
	 */
	public void cacheResult(java.util.List<FaroPreferences> faroPreferenceses);

	/**
	 * Creates a new faro preferences with the primary key. Does not add the faro preferences to the database.
	 *
	 * @param faroPreferencesId the primary key for the new faro preferences
	 * @return the new faro preferences
	 */
	public FaroPreferences create(long faroPreferencesId);

	/**
	 * Removes the faro preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences that was removed
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	public FaroPreferences remove(long faroPreferencesId)
		throws NoSuchFaroPreferencesException;

	public FaroPreferences updateImpl(FaroPreferences faroPreferences);

	/**
	 * Returns the faro preferences with the primary key or throws a <code>NoSuchFaroPreferencesException</code> if it could not be found.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences
	 * @throws NoSuchFaroPreferencesException if a faro preferences with the primary key could not be found
	 */
	public FaroPreferences findByPrimaryKey(long faroPreferencesId)
		throws NoSuchFaroPreferencesException;

	/**
	 * Returns the faro preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroPreferencesId the primary key of the faro preferences
	 * @return the faro preferences, or <code>null</code> if a faro preferences with the primary key could not be found
	 */
	public FaroPreferences fetchByPrimaryKey(long faroPreferencesId);

	/**
	 * Returns all the faro preferenceses.
	 *
	 * @return the faro preferenceses
	 */
	public java.util.List<FaroPreferences> findAll();

	/**
	 * Returns a range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @return the range of faro preferenceses
	 */
	public java.util.List<FaroPreferences> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro preferenceses
	 */
	public java.util.List<FaroPreferences> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
			orderByComparator);

	/**
	 * Returns an ordered range of all the faro preferenceses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro preferenceses
	 * @param end the upper bound of the range of faro preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro preferenceses
	 */
	public java.util.List<FaroPreferences> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FaroPreferences>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the faro preferenceses from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of faro preferenceses.
	 *
	 * @return the number of faro preferenceses
	 */
	public int countAll();

}