/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link FaroProjectEmailDomainLocalService}.
 *
 * @author Matthew Kong
 * @see FaroProjectEmailDomainLocalService
 * @generated
 */
public class FaroProjectEmailDomainLocalServiceWrapper
	implements FaroProjectEmailDomainLocalService,
			   ServiceWrapper<FaroProjectEmailDomainLocalService> {

	public FaroProjectEmailDomainLocalServiceWrapper() {
		this(null);
	}

	public FaroProjectEmailDomainLocalServiceWrapper(
		FaroProjectEmailDomainLocalService faroProjectEmailDomainLocalService) {

		_faroProjectEmailDomainLocalService =
			faroProjectEmailDomainLocalService;
	}

	/**
	 * Adds the faro project email domain to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailDomain the faro project email domain
	 * @return the faro project email domain that was added
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
		addFaroProjectEmailDomain(
			com.liferay.osb.faro.model.FaroProjectEmailDomain
				faroProjectEmailDomain) {

		return _faroProjectEmailDomainLocalService.addFaroProjectEmailDomain(
			faroProjectEmailDomain);
	}

	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
		addFaroProjectEmailDomain(
			long groupId, long faroProjectId, String emailDomain) {

		return _faroProjectEmailDomainLocalService.addFaroProjectEmailDomain(
			groupId, faroProjectId, emailDomain);
	}

	@Override
	public void addFaroProjectEmailDomains(
		long groupId, long faroProjectId,
		java.util.List<String> emailAddressDomains) {

		_faroProjectEmailDomainLocalService.addFaroProjectEmailDomains(
			groupId, faroProjectId, emailAddressDomains);
	}

	/**
	 * Creates a new faro project email domain with the primary key. Does not add the faro project email domain to the database.
	 *
	 * @param faroProjectEmailDomainId the primary key for the new faro project email domain
	 * @return the new faro project email domain
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
		createFaroProjectEmailDomain(long faroProjectEmailDomainId) {

		return _faroProjectEmailDomainLocalService.createFaroProjectEmailDomain(
			faroProjectEmailDomainId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailDomainLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the faro project email domain from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailDomain the faro project email domain
	 * @return the faro project email domain that was removed
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
		deleteFaroProjectEmailDomain(
			com.liferay.osb.faro.model.FaroProjectEmailDomain
				faroProjectEmailDomain) {

		return _faroProjectEmailDomainLocalService.deleteFaroProjectEmailDomain(
			faroProjectEmailDomain);
	}

	/**
	 * Deletes the faro project email domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailDomainId the primary key of the faro project email domain
	 * @return the faro project email domain that was removed
	 * @throws PortalException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
			deleteFaroProjectEmailDomain(long faroProjectEmailDomainId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailDomainLocalService.deleteFaroProjectEmailDomain(
			faroProjectEmailDomainId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailDomainLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _faroProjectEmailDomainLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _faroProjectEmailDomainLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _faroProjectEmailDomainLocalService.dynamicQuery();
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

		return _faroProjectEmailDomainLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailDomainModelImpl</code>.
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

		return _faroProjectEmailDomainLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailDomainModelImpl</code>.
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

		return _faroProjectEmailDomainLocalService.dynamicQuery(
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

		return _faroProjectEmailDomainLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _faroProjectEmailDomainLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
		fetchFaroProjectEmailDomain(long faroProjectEmailDomainId) {

		return _faroProjectEmailDomainLocalService.fetchFaroProjectEmailDomain(
			faroProjectEmailDomainId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _faroProjectEmailDomainLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the faro project email domain with the primary key.
	 *
	 * @param faroProjectEmailDomainId the primary key of the faro project email domain
	 * @return the faro project email domain
	 * @throws PortalException if a faro project email domain with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
			getFaroProjectEmailDomain(long faroProjectEmailDomainId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailDomainLocalService.getFaroProjectEmailDomain(
			faroProjectEmailDomainId);
	}

	/**
	 * Returns a range of all the faro project email domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email domains
	 * @param end the upper bound of the range of faro project email domains (not inclusive)
	 * @return the range of faro project email domains
	 */
	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroProjectEmailDomain>
		getFaroProjectEmailDomains(int start, int end) {

		return _faroProjectEmailDomainLocalService.getFaroProjectEmailDomains(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroProjectEmailDomain>
		getFaroProjectEmailDomainsByFaroProjectId(long faroProjectId) {

		return _faroProjectEmailDomainLocalService.
			getFaroProjectEmailDomainsByFaroProjectId(faroProjectId);
	}

	@Override
	public java.util.List<com.liferay.osb.faro.model.FaroProjectEmailDomain>
		getFaroProjectEmailDomainsByGroupId(long groupId) {

		return _faroProjectEmailDomainLocalService.
			getFaroProjectEmailDomainsByGroupId(groupId);
	}

	/**
	 * Returns the number of faro project email domains.
	 *
	 * @return the number of faro project email domains
	 */
	@Override
	public int getFaroProjectEmailDomainsCount() {
		return _faroProjectEmailDomainLocalService.
			getFaroProjectEmailDomainsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _faroProjectEmailDomainLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _faroProjectEmailDomainLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailDomainLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the faro project email domain in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailDomain the faro project email domain
	 * @return the faro project email domain that was updated
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailDomain
		updateFaroProjectEmailDomain(
			com.liferay.osb.faro.model.FaroProjectEmailDomain
				faroProjectEmailDomain) {

		return _faroProjectEmailDomainLocalService.updateFaroProjectEmailDomain(
			faroProjectEmailDomain);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _faroProjectEmailDomainLocalService.getBasePersistence();
	}

	@Override
	public FaroProjectEmailDomainLocalService getWrappedService() {
		return _faroProjectEmailDomainLocalService;
	}

	@Override
	public void setWrappedService(
		FaroProjectEmailDomainLocalService faroProjectEmailDomainLocalService) {

		_faroProjectEmailDomainLocalService =
			faroProjectEmailDomainLocalService;
	}

	private FaroProjectEmailDomainLocalService
		_faroProjectEmailDomainLocalService;

}