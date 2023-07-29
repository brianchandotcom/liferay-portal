/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.build.parameter;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.entity.Entity;

/**
 * @author Michael Hashimoto
 */
public interface BuildParameter extends Entity {

	public Build getBuild();

	public long getBuildId();

	public String getName();

	public String getValue();

	public void setBuild(Build build);

}