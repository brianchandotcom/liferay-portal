package com.liferay.support.resin;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;

import java.util.ArrayList;

public class EnvironmentClassLoader
	extends com.caucho.loader.EnvironmentClassLoader {

	public EnvironmentClassLoader(ClassLoader parent, String id) {
		super(parent, id);

		_id = id;
	}

	@Override
	public ArrayList<Permission> getPermissions() {
		if (_SECURITY_ENABLED && (_id != null) && _id.startsWith("web-app:") &&
			(!_id.endsWith("/ROOT"))) {

			return new ArrayList<Permission>();
		}

		return super.getPermissions();
	}

	@Override
	protected PermissionCollection getPermissions(CodeSource codeSource) {
		if (_SECURITY_ENABLED && (_id != null) && _id.startsWith("web-app:") &&
			(!_id.endsWith("/ROOT"))) {

			return new Permissions();
		}

		return super.getPermissions(codeSource);
	}

	private static boolean _SECURITY_ENABLED =
		(System.getSecurityManager() != null);

	private String _id;

}