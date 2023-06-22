<%--
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
--%>

<%@ include file="/html/common/themes/init.jsp" %>

<c:if test="<%= permissionChecker.isOmniadmin() %>">
	<c:if test='<%= GetterUtil.getBoolean(PropsUtil.get("license.show.lcs.notifications"), true) %>'>

		<%
		String lcsNotificationMessage = GetterUtil.getString(request.getAttribute("LCS_NOTIFICATION_MESSAGE"));
		%>

		<c:if test="<%= Validator.isNotNull(lcsNotificationMessage) %>">
			<% _showAlert(HtmlUtil.escape(lcsNotificationMessage), pageContext); %>
		</c:if>
	</c:if>

	<%
	List<Map<String, String>> licenseProperties = com.liferay.portal.license.LicenseManager.getLicenseProperties();

	if ((licenseProperties != null) && !licenseProperties.isEmpty()) {
		Map<String, String> portalLicenseProperties = licenseProperties.get(0);

		String productId = GetterUtil.getString(portalLicenseProperties.get("productId"));

		long now = System.currentTimeMillis();

		String accountEntryName = GetterUtil.getString(portalLicenseProperties.get("accountEntryName"));

		long expirationDate = GetterUtil.getLong(portalLicenseProperties.get("expirationDate"));

		long expirationDays = (expirationDate - now) / Time.DAY;

		long startDate = GetterUtil.getLong(portalLicenseProperties.get("startDate"));

		long lifetimeDays = (expirationDate - startDate) / Time.DAY;

		long postponeDate = Math.min(
			expirationDate - Time.DAY * 3,
			GetterUtil.getLong(
				SessionClicks.get(
					request,
					"com.liferay.portal.app.license.impl_postponeDate", null)));

		String clusterGracePeriodMessage = GetterUtil.getString(request.getAttribute("CLUSTER_GRACE_PERIOD_MESSAGE"));
	%>

		<c:if test='<%= productId.equals("Portal") && Validator.isNotNull(clusterGracePeriodMessage) %>'>
			<% _showAlert(HtmlUtil.escape(clusterGracePeriodMessage), pageContext); %>
		</c:if>

		<c:if test='<%= productId.equals("Portal") && (((lifetimeDays == 30) && (expirationDays < 7)) || ((lifetimeDays > 30) && (expirationDays < 30))) && (postponeDate <= now) %>'>
			<liferay-util:buffer var="alertMessage">
				<c:choose>
					<c:when test="<%= expirationDays <= 0 %>">
						Your <a class="alert-link" href="<%= themeDisplay.getPathMain() %>/portal/license">activation key</a> expired <%= expirationDays * -1 %> days ago.
					</c:when>
					<c:otherwise>
						Update your <a class="alert-link" href="<%= themeDisplay.getPathMain() %>/portal/license">activation key</a> because it will expire in <%= expirationDays %> days.

						<c:if test='<%= accountEntryName.equals("Liferay Trial") %>'>
							Visit <a class="alert-link" href="http://www.liferay.com/c/portal/license">your profile page at Liferay.com</a> to upgrade your trial license.
						</c:if>

						<c:if test="<%= now < (expirationDate - 3 * Time.DAY) %>">

							<%
							String taglibPostponeOnClick = StringBundler.concat(
								"Liferay.Util.Session.set('com.liferay.portal.app.license.impl_postponeDate', '",
								String.valueOf(now + Time.DAY * 7),
								"'); this.closest('.alert').querySelector('button.close').click();");
							%>

							<a class="alert-link" id="extendExpirationReminder" onclick="<%= taglibPostponeOnClick %>">Remind me later.</a>
						</c:if>

					</c:otherwise>
				</c:choose>
			</liferay-util:buffer>

			<% _showAlert(alertMessage, pageContext); %>
		</c:if>

	<%
	}
	%>

	<%!
	private static void _showAlert(String message, PageContext pageContext)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append(_ALERT_START);
		sb.append(HtmlUtil.escapeJS(message));
		sb.append(_ALERT_END);

		ScriptTag.doTag(
			null, null, "liferay-alert", sb.toString(), null, pageContext);
	}

	private static final String _ALERT_START =
		"new Liferay.Alert(\n\t{\n\t\tcloseable: true,\n\t\tdelay: {\n\t\t\t" +
			"hide: 0,\n\t\t\tshow: 0\n\t\t},\n\t\tduration: 500,\n\t\ticon: " +
				"'exclamation-full',\n\t\tmessage: '";
	private static final String _ALERT_END =
		"',\n\t\tnamespace: '',\n\t\ttitle: '',\n\t\ttype: 'danger'\n\t}\n)" +
			".render(A.one('#controlMenuAlertsContainer'));";
	%>

</c:if>
