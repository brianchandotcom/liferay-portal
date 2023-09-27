/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.apache.catalina.util;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;

/**
 * @author Shuyang Zhou
 */
public class ExtensionValidator {

	public static void addSystemResource(File file) throws IOException {
	}

	public static boolean validateApplication(
			WebResourceRoot webResourceRoot, Context context)
		throws IOException {

		return true;
	}

}