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

package com.liferay.support.websphere;

import com.ibm.ws.security.policy.DynamicPolicy;
import com.ibm.ws.security.policy.DynamicPolicyFactory;

import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public class DynamicPolicyHelper {

	protected void _start() {
		_original = DynamicPolicyFactory.getInstance();

		final DynamicPolicy original = _original;

		DynamicPolicy dynamicPolicy = new DynamicPolicy() {

			@Override
			public ProtectionDomain getProtectionDomain(
				CodeSource codeSource) {

				if (original == null) {
					return null;
				}

				return original.getProtectionDomain(codeSource);
			}

			@Override
			public PermissionCollection getPermissions(
				CodeSource codeSource, Map map) {

				return java.security.Policy.getPolicy().getPermissions(
					codeSource);
			}

			@Override
			public void getSecurityPolicy(Map map1, Map map2) {
				if (original == null) {
					return;
				}

				original.getSecurityPolicy(map1, map2);
			}

			@Override
			public void removePolicy(Map map) {
				if (original == null) {
					return;
				}

				original.removePolicy(map);
			}

			@Override
			public void setupPolicy(Map map) {
				if (original == null) {
					return;
				}

				original.setupPolicy(map);
			}

		};

		DynamicPolicyFactory.setInstance(dynamicPolicy);
	}

	private DynamicPolicy _original;

	private static DynamicPolicyHelper _instance = new DynamicPolicyHelper();

	static {
		_instance._start();
	}

}