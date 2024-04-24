/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.git.user;

import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitUserEntity
	extends BaseEntity implements GitUserEntity {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("url", getURL());

		return jsonObject;
	}

	@Override
	public URL getURL() {
		return _url;
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_url = StringUtil.toURL(jsonObject.getString("url"));
	}

	@Override
	public void setURL(URL sha) {
		_url = sha;
	}

	protected BaseGitUserEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private URL _url;

}