/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.xml.descriptor;

/**
 * @author Shuyang Zhou
 */
public class WebXML30Descriptor extends WebXML24Descriptor {

	public WebXML30Descriptor() {
		orderedChildren.put(
			"servlet",
			new String[] {
				"icon", "servlet-name", "display-name", "description",
				"servlet-class", "jsp-file", "init-param", "load-on-startup",
				"async-supported", "run-as", "security-role-ref"
			});
	}

}