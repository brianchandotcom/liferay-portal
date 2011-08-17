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
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<html>
<head>
	<title><decorator:title default="JSONWS | online api" /></title>
	<script type="text/javascript" src="doc/jquery.js"></script>
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
.action {font-size: 1.1em; margin-top: 4px;}
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
<decorator:head/>
</head>
<body>

	<div id="header">
		<div id="header-content">
			<h1><a href="jsonws">Online JSONWS API</a></h1>
		</div>
	</div>

	<div id="content">
		<decorator:body/>
	</div>

	<div id="footer">
		<div id="footer-content">
			Copyright @ Liferay
		</div>
	</div>

</body>
</html>