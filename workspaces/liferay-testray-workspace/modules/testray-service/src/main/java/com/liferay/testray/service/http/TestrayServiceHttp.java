/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.testray.service.TestrayServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>TestrayServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Nilton Vieira
 * @generated
 */
public class TestrayServiceHttp {

	public static java.util.List<java.util.Map<String, Object>>
			compareTestrayRuns(
				HttpPrincipal httpPrincipal, long companyId,
				String testrayCasePriorities, long testrayRun1Id,
				long testrayRun2Id, long testrayTeamId)
		throws Exception {

		try {
			MethodKey methodKey = new MethodKey(
				TestrayServiceUtil.class, "compareTestrayRuns",
				_compareTestrayRunsParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, testrayCasePriorities, testrayRun1Id,
				testrayRun2Id, testrayTeamId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof Exception) {
					throw (Exception)exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List<java.util.Map<String, Object>>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TestrayServiceHttp.class);

	private static final Class<?>[] _compareTestrayRunsParameterTypes0 =
		new Class[] {
			long.class, String.class, long.class, long.class, long.class
		};

}