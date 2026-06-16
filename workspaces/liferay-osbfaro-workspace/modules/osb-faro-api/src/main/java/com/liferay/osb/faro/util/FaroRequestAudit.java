/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shinn Lok
 */
public class FaroRequestAudit {

	public void addChildFaroRequestAudit(FaroRequestAudit faroRequestAudit) {
		_faroRequestAudits.add(faroRequestAudit);
	}

	public boolean isEnabled() {
		return _log.isDebugEnabled();
	}

	public void setEndTime(long endTime) {
		_endTime = endTime;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setStartTime(long startTime) {
		_startTime = startTime;
	}

	public void setStatusCode(int statusCode) {
		_statusCode = statusCode;
	}

	public void setURLPath(String urlPath) {
		_urlPath = urlPath;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(
			7 + (_faroRequestAudits.size() * 3));

		sb.append(_method);
		sb.append(StringPool.SPACE);
		sb.append(_urlPath);
		sb.append(" took ");
		sb.append(_endTime - _startTime);
		sb.append(" ms and returned ");
		sb.append(_statusCode);

		for (FaroRequestAudit faroRequestAudit : _faroRequestAudits) {
			sb.append(System.lineSeparator());
			sb.append("|-- ");
			sb.append(faroRequestAudit.toString());
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroRequestAudit.class);

	private long _endTime;
	private final List<FaroRequestAudit> _faroRequestAudits = new ArrayList<>();
	private String _method;
	private long _startTime;
	private int _statusCode;
	private String _urlPath;

}