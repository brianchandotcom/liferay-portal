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

package com.liferay.util.bridges.alloy;

/**
 * @author Ethan Bustad
 */
public abstract class PermissionedAlloyControllerImpl
	extends BaseAlloyControllerImpl {

	@Override
	public void execute() throws Exception {
		if (!AlloyPermission.contains(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portlet.getRootPortletId(),
				controllerPath, actionPath)) {

			renderError("you-do-not-have-permission-to-access-this-resource");
		}

		super.execute();
	}

}