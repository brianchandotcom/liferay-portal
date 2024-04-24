/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.commit;

import com.liferay.jethr0.entity.Entity;

/**
 * @author Michael Hashimoto
 */
public interface GitCommitEntity extends Entity {

	public String getSHA();

	public void setSHA(String sha);

}