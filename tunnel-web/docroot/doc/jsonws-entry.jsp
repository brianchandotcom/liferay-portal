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
<%@ page import="com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionMapping" %>
<%@ page import="java.io.File" %>

<html>
<body>
<%
JSONWebServiceActionMapping action = (JSONWebServiceActionMapping)request.getAttribute("action");
%>

<h2><%= action.getPath() %></h2>

<h3>Method</h3>

<div class="method"><%= action.getMethod() %></div>

<h3>Mapped method</h3>

<%
String actionClassName = action.getActionClass().getName();
int lastDotIndex = actionClassName.lastIndexOf('.');
String actionPackage = actionClassName.substring(0, lastDotIndex);
String actionClassShortName = actionClassName.substring(lastDotIndex + 1);
%>

<div class="actionmethod"><%= actionPackage %>.<span class="classname"><%= actionClassShortName %></span>#<span class="methodName"><%= action.getActionMethod().getName() %></span></div>


<h3>Parameters</h3>
<%
String[] parameterNames = action.getParameterNames();
Class<?>[] parameterTypes = action.getParameterTypes();
for (int i = 0; i < parameterNames.length; i++) {
	String parameterName = parameterNames[i];
	Class<?> parameterType = parameterTypes[i];
%>
	<div class="param">&bull; <%= parameterName %> <span><%= parameterType.getName() %></span></div>
<%
}
%>

<h3>Return type</h3>

<div><%= action.getActionMethod().getReturnType().getName() %></div>

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

<form id="execute" action="/tunnel-web/secure/jsonws<%= action.getPath() %>" method="<%= action.getMethod() %>" <%=enctype%>>

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


</body>
</html>