/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.transport;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public interface Transport {

	public void connect() throws IOException;

	public void disconnect() throws IOException;

	public boolean isConnected();

	public void sendMessage(String message) throws IOException;

}