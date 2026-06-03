/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FaroEmailLocalService}.
 *
 * @author Matthew Kong
 * @see FaroEmailLocalService
 * @generated
 */
public class FaroEmailLocalServiceWrapper
	implements FaroEmailLocalService, ServiceWrapper<FaroEmailLocalService> {

	public FaroEmailLocalServiceWrapper() {
		this(null);
	}

	public FaroEmailLocalServiceWrapper(
		FaroEmailLocalService faroEmailLocalService) {

		_faroEmailLocalService = faroEmailLocalService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _faroEmailLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.ResourceBundle getResourceBundle(java.util.Locale locale) {
		return _faroEmailLocalService.getResourceBundle(locale);
	}

	@Override
	public String getTemplate(String name) throws Exception {
		return _faroEmailLocalService.getTemplate(name);
	}

	@Override
	public FaroEmailLocalService getWrappedService() {
		return _faroEmailLocalService;
	}

	@Override
	public void setWrappedService(FaroEmailLocalService faroEmailLocalService) {
		_faroEmailLocalService = faroEmailLocalService;
	}

	private FaroEmailLocalService _faroEmailLocalService;

}