/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * @author Alejo Ceballos
 */
public class CharsetDetector {

	public Charset detect(File file) throws IOException {
		UniversalDetector universalDetector = new UniversalDetector();

		try (InputStream fileInputStream = new FileInputStream(file)) {
			byte[] bytes = new byte[4096];
			int length = 0;

			while (((length = fileInputStream.read(bytes)) > 0) &&
				   !universalDetector.isDone()) {

				universalDetector.handleData(bytes, 0, length);
			}

			universalDetector.dataEnd();

			try {
				return Charset.forName(universalDetector.getDetectedCharset());
			}
			catch (IllegalArgumentException illegalArgumentException) {
				_log.error(illegalArgumentException);

				return null;
			}
		}
		finally {
			universalDetector.reset();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CharsetDetector.class);

}