/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util.bridges.jsf.common;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import javax.faces.context.FacesContext;

/**
 * <p>
 * This class is designed to be a convenient JSF managed bean that can be used
 * to get portal related information (such as the user's time zone).
 * </p>
 *
 * @author Neil Griffin
 */
public class ThemeDisplayManagedBean {

	public User getUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		String remoteUser = facesContext.getExternalContext().getRemoteUser();

		try {
			long userId = GetterUtil.getLong(remoteUser);

			return UserLocalServiceUtil.getUserById(userId);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ThemeDisplayManagedBean.class);

}