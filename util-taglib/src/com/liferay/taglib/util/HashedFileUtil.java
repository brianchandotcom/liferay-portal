/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class HashedFileUtil {

	public static String getURL(
		HttpServletRequest httpServletRequest, String resourcePath) {

		String prefix = PortalUtil.getPathModule();

		String proxyPath = PortalUtil.getPathProxy();

		prefix = prefix.substring(proxyPath.length());

		String unhashedFileURI = prefix + StringPool.SLASH + resourcePath;

		String hashedFileURI = HashedFilesRegistryUtil.getHashedFileURI(
			unhashedFileURI);

		String uri = (hashedFileURI == null) ? unhashedFileURI : hashedFileURI;

		try {
			return PortalUtil.getCDNHost(httpServletRequest) + proxyPath + uri;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

}