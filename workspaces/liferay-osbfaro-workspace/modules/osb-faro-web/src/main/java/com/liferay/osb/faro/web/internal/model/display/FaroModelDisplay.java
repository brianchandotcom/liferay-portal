/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display;

import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Shinn Lok
 */
public abstract class FaroModelDisplay {

	public FaroModelDisplay() {
	}

	public FaroModelDisplay(BaseModel<?> baseModel) {
		_id = (Long)baseModel.getPrimaryKeyObj();
	}

	public long getId() {
		return _id;
	}

	private long _id;

}