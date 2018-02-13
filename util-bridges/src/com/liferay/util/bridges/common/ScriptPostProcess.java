/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util.bridges.common;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;

import javax.portlet.PortletURL;

/**
 * @author Gavin Wan
 * @author Brian Wing Shun Chan
 * @see    org.apache.portals.bridges.common.ScriptPostProcess
 */
public class ScriptPostProcess {

	public String getFinalizedPage() {
		if (_sb != null) {
			return _sb.toString();
		}

		return StringPool.BLANK;
	}

	public void postProcessPage(
		PortletURL actionURL, String actionParameterName) {

		processPage(
			"<a", StringPool.GREATER_THAN, "href=", actionURL,
			actionParameterName);
		processPage(
			"<A", StringPool.GREATER_THAN, "HREF=", actionURL,
			actionParameterName);
		processPage(
			"<area", StringPool.GREATER_THAN, "href=", actionURL,
			actionParameterName);
		processPage(
			"<AREA", StringPool.GREATER_THAN, "HREF=", actionURL,
			actionParameterName);
		processPage(
			"<FORM", StringPool.GREATER_THAN, "ACTION=", actionURL,
			actionParameterName);
		processPage(
			"<form", StringPool.GREATER_THAN, "action=", actionURL,
			actionParameterName);
	}

	public void processPage(
		String startTag, String endTag, String ref, PortletURL actionURL,
		String actionParameterName) {

		try {
			doProcessPage(
				startTag, endTag, ref, actionURL, actionParameterName);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void setInitalPage(StringBundler sb) {
		_sb = sb;
	}

	protected void doProcessPage(
		String startTag, String endTag, String ref, PortletURL actionURL,
		String actionParameterName) {

		StringBundler sb = new StringBundler();

		String content = _sb.toString();

		int startTagPos = content.indexOf(startTag);

		int endTagPos = 0;

		int startRefPos = 0;
		int endRefPos = 0;

		while (startTagPos != -1) {
			sb.append(content.substring(0, startTagPos));

			content = content.substring(startTagPos);

			endTagPos = content.indexOf(endTag);
			startRefPos = content.indexOf(ref);

			if ((startRefPos == -1) || (startRefPos > endTagPos)) {
				sb.append(content.substring(0, endTagPos));

				content = content.substring(endTagPos);
			}
			else {
				startRefPos = startRefPos + ref.length();

				sb.append(content.substring(0, startRefPos));

				content = content.substring(startRefPos);

				String quote = StringPool.BLANK;

				if (content.startsWith(StringPool.APOSTROPHE)) {
					quote = StringPool.APOSTROPHE;
				}
				else if (content.startsWith(StringPool.QUOTE)) {
					quote = StringPool.QUOTE;
				}

				String url = StringPool.BLANK;

				if (quote.length() > 0) {
					sb.append(quote);

					content = content.substring(1);

					endRefPos = content.indexOf(quote);

					url = content.substring(0, endRefPos);
				}
				else {
					endTagPos = content.indexOf(endTag);

					endRefPos = 0;

					StringBundler unquotedURLSB = new StringBundler();

					while (true) {
						char c = content.charAt(endRefPos);

						if (!Character.isSpaceChar(c) &&
							(endRefPos < endTagPos)) {

							endRefPos++;

							unquotedURLSB.append(c);
						}
						else {
							endRefPos--;

							break;
						}
					}

					url = unquotedURLSB.toString();
				}

				if ((url.charAt(0) == CharPool.POUND) ||
					url.startsWith("http")) {

					sb.append(url);
					sb.append(quote);
				}
				else {
					actionURL.setParameter(actionParameterName, url);

					sb.append(actionURL.toString());
					sb.append(quote);
				}

				content = content.substring(endRefPos + 1);
			}

			startTagPos = content.indexOf(startTag);
		}

		sb.append(content);

		_sb = sb;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ScriptPostProcess.class);

	private StringBundler _sb;

}