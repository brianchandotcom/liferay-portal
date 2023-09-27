/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.slf4j.impl;

import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.spi.MDCAdapter;

/**
 * @author Michael C. Han
 */
public class StaticMDCBinder {

	public static final StaticMDCBinder SINGLETON = new StaticMDCBinder();

	public MDCAdapter getMDCA() {
		return new BasicMDCAdapter();
	}

	public String getMDCAdapterClassStr() {
		return BasicMDCAdapter.class.getName();
	}

	private StaticMDCBinder() {
	}

}