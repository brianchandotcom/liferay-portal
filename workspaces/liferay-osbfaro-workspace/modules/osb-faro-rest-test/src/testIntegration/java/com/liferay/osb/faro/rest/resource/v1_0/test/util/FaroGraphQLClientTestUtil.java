/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test.util;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.GraphQLRequest;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Nilton Vieira
 */
public class FaroGraphQLClientTestUtil {

	public static GraphQLRequest getGraphQLRequest(
			Map<String, Object> data, Object resource,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		AtomicReference<GraphQLRequest> atomicReference =
			new AtomicReference<>();

		AcceptLanguage contextAcceptLanguage =
			ReflectionTestUtil.getAndSetFieldValue(
				resource, "contextAcceptLanguage",
				(AcceptLanguage)ProxyUtil.newProxyInstance(
					AcceptLanguage.class.getClassLoader(),
					new Class<?>[] {AcceptLanguage.class},
					(proxy, method, arguments) -> {
						if (method.getReturnType() == boolean.class) {
							return false;
						}

						return null;
					}));

		Object faroGraphQLClient = ReflectionTestUtil.getFieldValue(
			resource, "_faroGraphQLClient");

		ContactsEngineClient contactsEngineClient =
			ReflectionTestUtil.getAndSetFieldValue(
				faroGraphQLClient, "_contactsEngineClient",
				(ContactsEngineClient)ProxyUtil.newProxyInstance(
					ContactsEngineClient.class.getClassLoader(),
					new Class<?>[] {ContactsEngineClient.class},
					(proxy, method, arguments) -> {
						atomicReference.set((GraphQLRequest)arguments[4]);

						return Collections.singletonMap("data", data);
					}));

		FaroProjectLocalService faroProjectLocalService =
			ReflectionTestUtil.getAndSetFieldValue(
				resource, "_faroProjectLocalService",
				(FaroProjectLocalService)ProxyUtil.newProxyInstance(
					FaroProjectLocalService.class.getClassLoader(),
					new Class<?>[] {FaroProjectLocalService.class},
					(proxy, method, arguments) -> null));

		try {
			unsafeRunnable.run();
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				faroGraphQLClient, "_contactsEngineClient",
				contactsEngineClient);
			ReflectionTestUtil.setFieldValue(
				resource, "_faroProjectLocalService", faroProjectLocalService);
			ReflectionTestUtil.setFieldValue(
				resource, "contextAcceptLanguage", contextAcceptLanguage);
		}

		return atomicReference.get();
	}

}