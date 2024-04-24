/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.pull;

import com.liferay.jethr0.entity.Entity;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public interface GitPullEntity extends Entity {

	public URL getURL();

	public void setURL(URL url);

}