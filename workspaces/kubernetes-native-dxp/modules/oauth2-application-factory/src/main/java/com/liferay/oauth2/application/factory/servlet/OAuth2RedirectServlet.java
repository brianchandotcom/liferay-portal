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

package com.liferay.oauth2.application.factory.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.context.path=/",
		"osgi.http.whiteboard.servlet.pattern=/builtin/oauth2/redirect"
	},
	service = Servlet.class
)
public class OAuth2RedirectServlet extends HttpServlet {

	@Activate
	public OAuth2RedirectServlet(@Reference Portal portal) {
		_portal = portal;
	}

	@Override
	public void init() throws ServletException {
		if (_log.isInfoEnabled()) {
			_log.info("OAuth2 Redirect Servlet init");
		}

		super.init();
	}

	@Override
	protected void doGet(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		httpServletResponse.setCharacterEncoding(StringPool.UTF8);
		httpServletResponse.setContentType(ContentTypes.TEXT_HTML_UTF8);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		try {
			String clientId = ParamUtil.get(httpServletRequest, "state", "");
			String code = ParamUtil.get(httpServletRequest, "code", "");

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"User ID: ", _portal.getUserId(httpServletRequest),
						", Company ID: ",
						_portal.getCompanyId(httpServletRequest),
						" Client ID: ", clientId, ", Authorization Code: ",
						code));
			}

			PrintWriter writer = httpServletResponse.getWriter();

			writer.write(_generateHTML(clientId, code));
		}
		catch (Exception exception) {
			_log.warn(exception.getMessage(), exception);

			httpServletResponse.setStatus(
				HttpServletResponse.SC_PRECONDITION_FAILED);
		}
	}

	private String _generateHTML(String clientId, String code) {
		return StringBundler.concat(
			"<html>",
			"<head><title>OAuth2 Redirect Servlet</title></head>",
			"<body>",
			"<script type=\"text/javascript\">",
			"  var origin = (window.location != window.parent.location) ?",
			"    document.referrer : document.location.href;",
			"  var urlParams = new URLSearchParams(",
			"    window['location'].search);",
			"  var error = urlParams.get('error');",
			"  var code = urlParams.get('code');",
			"  var message = {",
			"    client_id: \"", clientId, "\"",
			"  };",
			"  if (error === 'login_required' ||",
			"      error === 'interaction_required') {",
			"    message.interaction_required = true;",
			"  }",
			"  else {",
			"    message.code = \"", code, "\";",
			"  }",
			"  window.postMessage(message, origin);",
			"</script>",
			"</body>",
			"</html>");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2RedirectServlet.class);

	private static final long serialVersionUID = 1L;

	private final Portal _portal;

}