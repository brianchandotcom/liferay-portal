/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.routine;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseCronRoutineEntity
	extends BaseRoutineEntity implements CronRoutineEntity {

	@Override
	public String getCron() {
		return _cron;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("cron", getCron());

		return jsonObject;
	}

	@Override
	public void setCron(String cron) {
		_cron = cron;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_cron = jsonObject.optString("cron");
	}

	protected BaseCronRoutineEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private String _cron;

}