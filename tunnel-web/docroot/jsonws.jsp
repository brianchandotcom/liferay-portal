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

<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping" %>
<%@ page import="com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>

<html>
<head>
	<title>JSONWS | online api</title>
	<script type="text/javascript" src="yui-min.js"></script>
	<style type="text/css">
* {padding: 0; margin: 0;}
body {background-color: #FFF; text-align: center; font-family: Tahoma, Arial, sans-serif; font-size: 14px;}
#header {line-height: 80px; background-color: #003e7e; height: 80px; margin: 20px 0 0; border-bottom: 15px solid #5780AA;}
#header-content {width: 760px; text-align: left; margin: 0 auto;}
#content {width: 760px; text-align: left; margin: 0 auto; background-color: #FFF; padding: 20px 30px;}
h1 {width:760px; color: #FFF; font-size: 2.5em; font-weight: normal; margin: 0 auto;}
h1 a {color: #FFF; text-decoration: none;}
h1 a:visited {color: #FFF}
h1 a:hover {color: #FFF}
h2 {color:#003e73; font-size: 2.0em; margin-top: 20px; margin-bottom:5px; font-weight: normal;}
h3 {color:#003e73; font-size: 1.6em; font-weight: normal; margin: 20px 0 0 0 ;}
.action {font-size: 1.1em; padding: 2px;}
.action a {color: #047;}
.action a:visited {color: #047;}
.action span.params {color: #777; font-size: 0.7em; display: none; padding-left: 6px;}
div.actionmethod {font-size: 1.2em; color:#555;}
div.actionmethod span.classname {font-weight: bold;}
div.actionmethod span.methodname {font-weight: bold; color: #b00;}
div.method {font-size: 1.2em; font-weight: bold; color: #0A0;}
div.param {font-size: 1.2em;  margin-left: 10px;}
div.param span {color:#888; font-size: 0.7em; padding-left: 4px;}
#footer {background-color: #ddd; color:#777; padding: 40px; margin-top: 20px; border-top: 20px solid #eee;}
#footer-content {width: 760px; text-align: left; margin: 0 auto;}
form#execute {margin-left: 20px;}
form#execute input {padding: 4px;}
form#execute input[type="text"] {border: 1px solid #aaa;}
form#execute label {color: #666;}
form#execute label span {color: #777; font-size: 0.7em;}
form#execute #submit {border: 2px solid #a60; background-color: #f90; font-weight: bold; color:#444; font-size: 1.1em; cursor: pointer;}
	</style>
</head>
<body>

	<div id="header">
		<div id="header-content">
			<h1><a href="jsonws">Online JSONWS API</a></h1>
		</div>
	</div>

	<div id="content">
<%
String action = ParamUtil.getString(request, "action");

if (action.equals(StringPool.BLANK)) {
%>
<script type="text/javascript">
YUI().use('node', function (Y) {
	Y.all('div.action').on({
		"mouseover": function(e) {
			var node = e.currentTarget;
			node.setStyle("backgroundColor", "#f5f5f5");
			node.one("span").setStyle("display", "inline")
		},
		"mouseout" : function(e) {
			var node = e.currentTarget;
			node.setStyle("backgroundColor", "#fff");
			node.one("span").setStyle("display", "none")
		}
	});
});
</script>

	<%
	List<JSONWebServiceActionMapping> mappings = JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMappings();
	%>

	<p>Total methods: <b><%= mappings.size() %></b></p>

	<%

	String previousActionClass = "";

	for (JSONWebServiceActionMapping actionMapping : mappings) {
		String actionClass = actionMapping.getActionClass().getSimpleName();

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

		String path = actionMapping.getPath();

		int slashIndex = path.lastIndexOf('/');

		path = path.substring(slashIndex + 1);

		String parameters = "";

		String[] parameterNames = actionMapping.getParameterNames();

		for (int i = 0; i < parameterNames.length; i++) {
			if (i != 0) {
				parameters += ", ";
			}
			parameters += parameterNames[i];
		}
	%>

		<div class="action">
			<a href="?action=<%= actionMapping.getSignature() %>"><%=path%></a>
			<span class="params"><%= parameters %></span>
		</div>

	<%
	}
	%>

<%
}
else {
%>
	<%
	JSONWebServiceActionMapping actionMapping = JSONWebServiceActionsManagerUtil.getJSONWebServiceActionMapping(action);
	%>

	<h2><%= actionMapping.getPath() %></h2>

	<h3>Method</h3>

	<div class="method"><%= actionMapping.getMethod() %></div>

	<h3>Mapped method</h3>

	<%
	String actionClassName = actionMapping.getActionClass().getName();
	int lastDotIndex = actionClassName.lastIndexOf('.');
	String actionPackage = actionClassName.substring(0, lastDotIndex);
	String actionClassShortName = actionClassName.substring(lastDotIndex + 1);
	%>

	<div class="actionmethod"><%= actionPackage %>.<span class="classname"><%= actionClassShortName %></span>#<span class="methodName"><%= actionMapping.getActionMethod().getName() %></span></div>


	<h3>Parameters</h3>
	<%
	String[] parameterNames = actionMapping.getParameterNames();
	Class<?>[] parameterTypes = actionMapping.getParameterTypes();
	for (int i = 0; i < parameterNames.length; i++) {
		String parameterName = parameterNames[i];
		Class<?> parameterType = parameterTypes[i];
	%>
		<div class="param">&bull; <%= parameterName %> <span><%= parameterType.getName() %></span></div>
	<%
	}
	%>

	<h3>Return type</h3>

	<div><%= actionMapping.getActionMethod().getReturnType().getName() %></div>

	<h3>Execute</h3>
	<%
	boolean isMultipart = false;

	for (int i = 0; i < parameterNames.length; i++) {
		Class<?> parameterType = parameterTypes[i];

		if (parameterType.equals(File.class)) {
			isMultipart = true;
			break;
		}
	}

	String enctype = "";
	if (isMultipart) {
		enctype = "enctype=\"multipart/form-data\"";
	}
	%>

	<form id="execute" action="/tunnel-web/secure/jsonws<%= actionMapping.getPath() %>" method="<%= actionMapping.getMethod() %>" <%=enctype%>>

	<%
	for (int i = 0; i < parameterNames.length; i++) {
		String parameterName = parameterNames[i];
		Class<?> parameterType = parameterTypes[i];

		if (parameterName.equals("serviceContext")) {
			continue;
		}

		int size = 10;

		if (parameterType.equals(String.class)) {
			size = 60;
		}
	%>
		<label for="field<%= i %>"><%= parameterName %> <span>(<%= parameterType.getName() %>)</span></label><br/>
	<%
		if (parameterType.equals(File.class)) {
	%>
		<input id="field<%= i %>" type="file" name="<%= parameterName %>"/><br/>
	<%
		}
		else if (parameterType.equals(boolean.class)) {
	%>
		<input id="field<%=i%>" type="radio" name="<%= parameterName %>" value="false" checked="checked"/>false &nbsp;
		<input id="field<%=i%>" type="radio" name="<%= parameterName %>" value="true"/>true
		<br/>
	<%
		}
		else {
	%>
		<input id="field<%=i%>" type="text" name="<%= parameterName %>" size="<%= size %>"/><br/>
	<%
		}
	%>
		<br/>
	<%
	}
	%>
		<input type="submit" value="invoke" id="submit"/>

	</form>

<%
}
%>
	</div>

	<div id="footer">
		<div id="footer-content">
 			Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
		</div>
	</div>

</body>
</html>