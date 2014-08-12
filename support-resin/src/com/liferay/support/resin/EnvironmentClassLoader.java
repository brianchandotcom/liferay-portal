/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.support.resin;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;

import java.util.ArrayList;

/**
 * @author Raymond Augé
 */
public class EnvironmentClassLoader
	extends com.caucho.loader.EnvironmentClassLoader {

	public EnvironmentClassLoader(ClassLoader classLoader, String id) {
		super(classLoader, id);

		_id = id;
	}

	@Override
	public ArrayList<Permission> getPermissions() {
		if (_SECURITY_ENABLED && (_id != null) && _id.startsWith("web-app:") &&
			!_id.endsWith("/ROOT")) {

			return new ArrayList<Permission>();
		}

		return super.getPermissions();
	}

	@Override
	protected PermissionCollection getPermissions(CodeSource codeSource) {
		if (_SECURITY_ENABLED && (_id != null) && _id.startsWith("web-app:") &&
			!_id.endsWith("/ROOT")) {

			return new Permissions();
		}

		return super.getPermissions(codeSource);
	}

	private static final boolean _SECURITY_ENABLED =
		(System.getSecurityManager() != null);

	private final String _id;

}