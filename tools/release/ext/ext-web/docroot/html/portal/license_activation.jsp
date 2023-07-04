<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.license.messaging.LCSPortletState" %>

<%
int lcsLicenseState = GetterUtil.getInteger(request.getAttribute("LCS_LICENSE_STATE"));
%>

<c:choose>
	<c:when test="<%= lcsLicenseState == 6 %>">

		<%
		String portalLicenseType = StringPool.BLANK;

		List<Map<String, String>> licenseProperties = LicenseManagerUtil.getLicenseProperties();

		if ((licenseProperties != null) && (licenseProperties.size() > 0)) {
			Map<String, String> portalLicenseProperties = licenseProperties.get(0);

			String productId = GetterUtil.getString(portalLicenseProperties.get("productId"));

			if (productId.equals("Portal")) {
				portalLicenseType = portalLicenseProperties.get("type");
			}
		}

		String clusterGracePeriodMessage = GetterUtil.getString(request.getAttribute("CLUSTER_GRACE_PERIOD_MESSAGE"));
		%>

		<div class="alert alert-danger" role="alert">
			<span class="alert-indicator">
				<svg class="lexicon-icon lexicon-icon-exclamation-full" focusable="false" role="presentation" viewBox="0 0 512 512">
					<path class="lexicon-icon-outline" d="M256,0C114.6,0,0,114.6,0,256s114.6,256,256,256s256-114.6,256-256S397.4,0,256,0z M256,384c-17.7,0-32-14.3-32-32
					s14.3-32,32-32s32,14.3,32,32S273.7,384,256,384z M272,288h-32l-16-160h64L272,288z"></path>
				</svg>
			</span>
			<strong class="lead">Error:</strong>
			<c:choose>
				<c:when test='<%= Validator.isNotNull(clusterGracePeriodMessage) %>'>
					<%=HtmlUtil.escape(clusterGracePeriodMessage)%>
				</c:when>
				<c:when test='<%= portalLicenseType.equals("virtual-cluster") %>'>
					You have exceeded the maximum number of cluster nodes in cluster, please contact system admin.
				</c:when>
				<c:when test='<%= !portalLicenseType.equals("limited") && !portalLicenseType.equals("production") %>'>
					You have exceeded the developer mode connection limit. Click <a href="<%= themeDisplay.getPathMain() %>/portal/license?cmd=resetState&resetToken=<%= com.liferay.portal.ee.license.LCSLicenseManager.getResetToken() %>">here</a> to reset all connections.
				</c:when>
			</c:choose>
		</div>

		To register your production server, please go to <a href="https://lcs.liferay.com">https://lcs.liferay.com</a>.
	</c:when>
</c:choose>

<%
int lcsPortletState = GetterUtil.getInteger(request.getAttribute("LCS_PORTLET_STATE"));
%>

<c:choose>
	<c:when test="<%= (lcsPortletState == LCSPortletState.PLUGIN_ABSENT.intValue()) || (lcsPortletState == LCSPortletState.NOT_REGISTERED.intValue()) %>">
		<div class="alert alert-danger" role="alert">
			<span class="alert-indicator">
				<svg class="lexicon-icon lexicon-icon-exclamation-full" focusable="false" role="presentation" viewBox="0 0 512 512">
					<path class="lexicon-icon-outline" d="M256,0C114.6,0,0,114.6,0,256s114.6,256,256,256s256-114.6,256-256S397.4,0,256,0z M256,384c-17.7,0-32-14.3-32-32
					s14.3-32,32-32s32,14.3,32,32S273.7,384,256,384z M272,288h-32l-16-160h64L272,288z"></path>
				</svg>
			</span>
			<strong class="lead">Error:</strong>
			This instance is not registered.
		</div>

		<div class="sheet">
			<div class="sheet-header">
				<h1 class="sheet-title">
					Liferay DXP Activation
				</h1>

				<div class="sheet-text">
					Administrators can use one of the options below to activate this Liferay DXP instance.
				</div>
			</div>

			<div class="sheet-section">
				<h2 class="sheet-subtitle">
					Servers
				</h2>

				<div class="sheet-text">
					For non-production, backup and production instances, please navigate to the License Manager and deploy the activation key provided for this instance.
				</div>

				<a class="btn btn-secondary" href="<%= themeDisplay.getPathMain() %>/portal/license" target="_blank">License Manager</a>
			</div>

			<div class="sheet-section">
				<h2 class="sheet-subtitle">
					Local Workstations
				</h2>

				<div class="sheet-text">
					For local workstations, deploy your developer type activation key.
				</div>

				<a class="btn btn-secondary" href="//customer.liferay.com/en_US/activation-key" target="_blank">
					<svg class="lexicon-icon lexicon-icon-download" viewBox="0 0 512 512">
						<path class="download-arrow-down lexicon-icon-outline" d="M233.2,374.5c13.1,13.2,33.5,12.2,45.6,0l71.3-71.6c29.8-29.9-14.3-77.2-45.6-45.8l-16.6,16.7V32.1c0-42.5-63.7-43-63.7,0v241.7l-16.6-16.7c-30.8-30.9-75.5,15.8-45.6,45.8L233.2,374.5z"></path>
						<path class="download-border lexicon-icon-outline" d="M384,384.5v63.8H128v-63.8c0-43.8-64-41.8-64,0V512h384V384.5C448,340.7,384,341.7,384,384.5z"></path>
					</svg>
					Developer Key
				</a>
			</div>

			<div class="sheet-section">
				<h2 class="sheet-subtitle">
					I Have A DXP Trial
				</h2>

				<div class="sheet-text">
					For DXP trials, please deploy the provided trial activation key or request one from your Sales Representative at <a href="mailto:sales@liferay.com">sales@liferay.com</a>
				</div>

				<a class="btn btn-secondary" href="mailto:sales@liferay.com">Contact Sales</a>
			</div>

			<div class="sheet-section">
				<h2 class="sheet-subtitle">
					Further Assistance
				</h2>

				<div class="sheet-text">
					You may also contact Liferay Support for further assistance.
				</div>

				<a class="btn btn-secondary" href="//help.liferay.com/hc/en-us/requests/new" target="_blank">Contact Support</a>
			</div>
		</div>
	</c:when>
	<c:when test="<%= lcsPortletState == LCSPortletState.GOOD.intValue() %>">
		<div class="alert alert-success" role="alert">
			<span class="alert-indicator">
				<svg class="lexicon-icon lexicon-icon-check-circle-full" focusable="false" role="presentation" viewBox="0 0 512 512">
					<path class="lexicon-icon-outline" d="M484.1,139.7c-54.4,62.6-167,190-203.8,233c-5.9,6.8-14.6,11.2-24.3,11.2c-8.8,0-16.8-3.6-22.6-9.4c-45.3-45.3-50.7-50.7-96-96c-5.8-5.8-9.4-13.8-9.4-22.6c0-17.7,14.3-32,32-32c8.8,0,16.8,3.6,22.6,9.4c35.6,35.6,46.6,46.6,71.6,71.6c47-54.8,150.9-171.7,192.5-219.7C399.8,32.9,331.8,0,256,0C114.6,0,0,114.6,0,256s114.6,256,256,256s256-114.6,256-256C512,214.1,501.9,174.6,484.1,139.7z"></path>
				</svg>
			</span>
			<strong class="lead">Success:</strong>
			This server is registered. Go to <a href="http://lcs.liferay.com">http://lcs.liferay.com</a> to manage your activation keys.
		</div>

		<div class="sheet">
			<div class="sheet-header">
				<h1 class="sheet-title">
					Liferay DXP Activation
				</h1>

				<div class="sheet-text">
					This server is registered. You can manage your activation key from LCS site.
				</div>
			</div>

			<a class="btn btn-primary" href="//lcs.liferay.com" target="_blank">Liferay Connected Services</a>
		</div>
	</c:when>
	<c:when test="<%= lcsPortletState == LCSPortletState.NO_CONNECTION.intValue() %>">
		<div class="alert alert-danger" role="alert">
			<span class="alert-indicator">
				<svg class="lexicon-icon lexicon-icon-exclamation-full" focusable="false" role="presentation" viewBox="0 0 512 512">
					<path class="lexicon-icon-outline" d="M256,0C114.6,0,0,114.6,0,256s114.6,256,256,256s256-114.6,256-256S397.4,0,256,0z M256,384c-17.7,0-32-14.3-32-32
					s14.3-32,32-32s32,14.3,32,32S273.7,384,256,384z M272,288h-32l-16-160h64L272,288z"></path>
				</svg>
			</span>
			<strong class="lead">Error:</strong>
			Unable to connect to <a href="http://lcs.liferay.com">http://lcs.liferay.com</a>. Please check your connection settings and restart.
		</div>

		<div class="sheet">
			<div class="sheet-header">
				<h1 class="sheet-title">
					Liferay DXP Activation
				</h1>

				<div class="sheet-text">
					Administrators can use one of the options below to activate this Liferay DXP instance.
				</div>
			</div>

			<div class="sheet-section">
				<h2 class="sheet-subtitle">
					Troubleshooting LCS
				</h2>

				<div class="sheet-text">
					Review Help Center documentation in order to find solutions to the current LCS connection issue.
				</div>

				<a class="btn btn-secondary" href="//help.liferay.com" target="_blank">Help Center</a>
			</div>

			<div class="sheet-section">
				<h2 class="sheet-subtitle">
					Further Assistance
				</h2>

				<div class="sheet-text">
					You may also contact Liferay Support for further assistance.
				</div>

				<a class="btn btn-secondary" href="//help.liferay.com/hc/en-us/requests/new" target="_blank">Contact Support</a>
			</div>
		</div>
	</c:when>
</c:choose>