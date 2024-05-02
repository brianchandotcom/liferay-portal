/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public interface EventProcessor {

	public void receiveMessage(String message);

	public void sendMessage(String message);

	public void sendMessage(
		String message, Map<String, String> messageProperties);

}