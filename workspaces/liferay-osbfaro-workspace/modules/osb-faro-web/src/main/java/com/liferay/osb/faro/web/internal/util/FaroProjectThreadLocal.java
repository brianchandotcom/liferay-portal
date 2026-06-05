/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Marcellus Tavares
 */
public class FaroProjectThreadLocal {

	public static FaroProject getFaroProject() {
		return _faroProject.get();
	}

	public static void removeFaroProject() {
		_faroProject.remove();
	}

	public static void setFaroProject(FaroProject faroProject) {
		_faroProject.set(faroProject);
	}

	private static final ThreadLocal<FaroProject> _faroProject =
		new CentralizedThreadLocal<>(
			FaroProjectThreadLocal.class + "._faroProject");

}