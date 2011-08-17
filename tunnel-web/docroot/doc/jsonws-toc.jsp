<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping" %>

<html>
<head>
<script type="text/javascript">
$(function() {
	$('.action').hover(
		function() {
			$(this).css("background-color", "#f0f0f0");
			$("span", this).show();
		},
		function() {
			$(this).css("background-color", "#fff");
			$("span", this).hide();
		}
	);
})
</script>
</head>
<body>
<%
List<JSONWebServiceActionMapping> mappings =
	(List<JSONWebServiceActionMapping>) request.getAttribute("mappings");

%>

<p>Total methods: <b><%= mappings.size() %></b></p>

<%

String previousActionClass = "";

for (JSONWebServiceActionMapping action : mappings) {
	String actionClass = action.getActionClass().getSimpleName();

	if (actionClass.endsWith("Util")) {
		actionClass = actionClass.substring(0, actionClass.length() - 4);
	}
	if (actionClass.endsWith("Service")) {
		actionClass = actionClass.substring(0, actionClass.length() - 7);
	}

	if (!actionClass.equals(previousActionClass)) {
		previousActionClass = actionClass;
%>
	<h2><%= actionClass %></h2>
<%
	}

	String path = action.getPath();

	int slashIndex = path.lastIndexOf('/');

	path = path.substring(slashIndex + 1);

	String parameters = "";

	String[] parameterNames = action.getParameterNames();

	for (int i = 0; i < parameterNames.length; i++) {
		if (i != 0) {
			parameters += ", ";
		}
		parameters += parameterNames[i];
	}
%>

	<div class="action">
		<a href="?action=<%= action.getSignature() %>"><%=path%></a>
		<span class="params"><%= parameters %></span>
	</div>

<%
}
%>

</body>
</html>