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

package com.liferay.support.websphere;

import com.ibm.ws.security.policy.DynamicPolicy;
import com.ibm.ws.security.policy.DynamicPolicyFactory;

import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.ProtectionDomain;

import java.util.Map;

/**
 * @author Raymond Augé
 */
public class DynamicPolicyHelper {

	protected void start() {
		_originalDynamicPolicy = DynamicPolicyFactory.getInstance();

		final DynamicPolicy originalDynamicPolicy = _originalDynamicPolicy;

		DynamicPolicy dynamicPolicy = new DynamicPolicy() {

			@Override
			public PermissionCollection getPermissions(
				CodeSource codeSource, Map map) {

				Policy policy = Policy.getPolicy();

				return policy.getPermissions(codeSource);
			}

			@Override
			public ProtectionDomain getProtectionDomain(CodeSource codeSource) {
				if (originalDynamicPolicy == null) {
					return null;
				}

				return originalDynamicPolicy.getProtectionDomain(codeSource);
			}

			@Override
			public void getSecurityPolicy(Map map1, Map map2) {
				if (originalDynamicPolicy == null) {
					return;
				}

				originalDynamicPolicy.getSecurityPolicy(map1, map2);
			}

			@Override
			public void removePolicy(Map map) {
				if (originalDynamicPolicy == null) {
					return;
				}

				originalDynamicPolicy.removePolicy(map);
			}

			@Override
			public void setupPolicy(Map map) {
				if (originalDynamicPolicy == null) {
					return;
				}

				originalDynamicPolicy.setupPolicy(map);
			}

		};

		DynamicPolicyFactory.setInstance(dynamicPolicy);
	}

	private static final DynamicPolicyHelper _dynamicPolicyHelper =
		new DynamicPolicyHelper();

	static {
		_dynamicPolicyHelper.start();
	}

	private DynamicPolicy _originalDynamicPolicy;

}