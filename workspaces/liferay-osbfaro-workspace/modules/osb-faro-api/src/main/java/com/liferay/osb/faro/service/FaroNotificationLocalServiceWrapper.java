/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FaroNotificationLocalService}.
 *
 * @author Matthew Kong
 * @see FaroNotificationLocalService
 * @generated
 */
public class FaroNotificationLocalServiceWrapper
	implements FaroNotificationLocalService,
			   ServiceWrapper<FaroNotificationLocalService> {

	public FaroNotificationLocalServiceWrapper() {
		this(null);
	}

	public FaroNotificationLocalServiceWrapper(
		FaroNotificationLocalService faroNotificationLocalService) {

		_faroNotificationLocalService = faroNotificationLocalService;
	}

	/**
	 * Adds the faro notification to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroNotification the faro notification
	 * @return the faro notification that was added
	 */
	@Override
	public com.liferay.osb.faro.model.FaroNotification addFaroNotification(
		com.liferay.osb.faro.model.FaroNotification faroNotification) {

		return _faroNotificationLocalService.addFaroNotification(
			faroNotification);
	}

	@Override
	public com.liferay.osb.faro.model.FaroNotification addFaroNotification(
		long userId, long groupId, long ownerId, String scope, String type,
		String subtype) {

		return _faroNotificationLocalService.addFaroNotification(
			userId, groupId, ownerId, scope, type, subtype);
	}

	@Override
	public void clearDismissedNotifications() {
		_faroNotificationLocalService.clearDismissedNotifications();
	}

	/**
	 * Creates a new faro notification with the primary key. Does not add the faro notification to the database.
	 *
	 * @param faroNotificationId the primary key for the new faro notification
	 * @return the new faro notification
	 */
	@Override
	public com.liferay.osb.faro.model.FaroNotification createFaroNotification(
		long faroNotificationId) {

		return _faroNotificationLocalService.createFaroNotification(
			faroNotificationId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroNotificationLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the faro notification from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroNotification the faro notification
	 * @return the faro notification that was removed
	 */
	@Override
	public com.liferay.osb.faro.model.FaroNotification deleteFaroNotification(
		com.liferay.osb.faro.model.FaroNotification faroNotification) {

		return _faroNotificationLocalService.deleteFaroNotification(
			faroNotification);
	}

	/**
	 * Deletes the faro notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification that was removed
	 * @throws PortalException if a faro notification with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroNotification deleteFaroNotification(
			long faroNotificationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroNotificationLocalService.deleteFaroNotification(
			faroNotificationId);
	}

	@Override
	public void deleteFaroNotifications(
		long groupId, String type, String subtype, long userId) {

		_faroNotificationLocalService.deleteFaroNotifications(
			groupId, type, subtype, userId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroNotificationLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deleteUnreadFaroNotifications(
		long groupId, String type, String subtype, long userId) {

		_faroNotificationLocalService.deleteUnreadFaroNotifications(
			groupId, type, subtype, userId);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _faroNotificationLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _faroNotificationLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _faroNotificationLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _faroNotificationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _faroNotificationLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _faroNotificationLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _faroNotificationLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _faroNotificationLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.faro.model.FaroNotification fetchFaroNotification(
		long faroNotificationId) {

		return _faroNotificationLocalService.fetchFaroNotification(
			faroNotificationId);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroNotification>
		findFaroNotificationsLast30Days(
			long groupId, String type, long userId) {

		return _faroNotificationLocalService.findFaroNotificationsLast30Days(
			groupId, type, userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _faroNotificationLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the faro notification with the primary key.
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification
	 * @throws PortalException if a faro notification with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroNotification getFaroNotification(
			long faroNotificationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroNotificationLocalService.getFaroNotification(
			faroNotificationId);
	}

	/**
	 * Returns a range of all the faro notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of faro notifications
	 */
	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroNotification>
		getFaroNotifications(int start, int end) {

		return _faroNotificationLocalService.getFaroNotifications(start, end);
	}

	/**
	 * Returns the number of faro notifications.
	 *
	 * @return the number of faro notifications
	 */
	@Override
	public int getFaroNotificationsCount() {
		return _faroNotificationLocalService.getFaroNotificationsCount();
	}

	@Override
	public long getFaroNotificationsLast30DaysCount(
		long groupId, String subtype, String type, long userId) {

		return _faroNotificationLocalService.
			getFaroNotificationsLast30DaysCount(groupId, subtype, type, userId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _faroNotificationLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _faroNotificationLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroNotificationLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the faro notification in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroNotificationLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroNotification the faro notification
	 * @return the faro notification that was updated
	 */
	@Override
	public com.liferay.osb.faro.model.FaroNotification updateFaroNotification(
		com.liferay.osb.faro.model.FaroNotification faroNotification) {

		return _faroNotificationLocalService.updateFaroNotification(
			faroNotification);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _faroNotificationLocalService.getBasePersistence();
	}

	@Override
	public FaroNotificationLocalService getWrappedService() {
		return _faroNotificationLocalService;
	}

	@Override
	public void setWrappedService(
		FaroNotificationLocalService faroNotificationLocalService) {

		_faroNotificationLocalService = faroNotificationLocalService;
	}

	private FaroNotificationLocalService _faroNotificationLocalService;

}