/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.aui.base;

import javax.servlet.jsp.JspException;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @generated
 */
public abstract class BaseScriptTag extends com.liferay.taglib.util.PositionTagSupport {

	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public boolean getAsync() {
		return _async;
	}

	public java.lang.String getBlocking() {
		return _blocking;
	}

	public java.lang.String getCrossorigin() {
		return _crossorigin;
	}

	public boolean getDefer() {
		return _defer;
	}

	public java.lang.String getFetchpriority() {
		return _fetchpriority;
	}

	public java.lang.String getId() {
		return _id;
	}

	public java.lang.String getIntegrity() {
		return _integrity;
	}

	public java.lang.String getReferrerpolicy() {
		return _referrerpolicy;
	}

	public java.lang.String getRequire() {
		return _require;
	}

	public boolean getSandbox() {
		return _sandbox;
	}

	public java.lang.String getSenna() {
		return _senna;
	}

	public java.lang.String getSrc() {
		return _src;
	}

	public java.lang.String getType() {
		return _type;
	}

	public java.lang.String getUse() {
		return _use;
	}

	public void setAsync(boolean async) {
		_async = async;
	}

	public void setBlocking(java.lang.String blocking) {
		_blocking = blocking;
	}

	public void setCrossorigin(java.lang.String crossorigin) {
		_crossorigin = crossorigin;
	}

	public void setDefer(boolean defer) {
		_defer = defer;
	}

	public void setFetchpriority(java.lang.String fetchpriority) {
		_fetchpriority = fetchpriority;
	}

	public void setId(java.lang.String id) {
		_id = id;
	}

	public void setIntegrity(java.lang.String integrity) {
		_integrity = integrity;
	}

	public void setReferrerpolicy(java.lang.String referrerpolicy) {
		_referrerpolicy = referrerpolicy;
	}

	public void setRequire(java.lang.String require) {
		_require = require;
	}

	public void setSandbox(boolean sandbox) {
		_sandbox = sandbox;
	}

	public void setSenna(java.lang.String senna) {
		_senna = senna;
	}

	public void setSrc(java.lang.String src) {
		_src = src;
	}

	public void setType(java.lang.String type) {
		_type = type;
	}

	public void setUse(java.lang.String use) {
		_use = use;
	}

	protected void cleanUp() {
		_async = false;
		_blocking = null;
		_crossorigin = null;
		_defer = false;
		_fetchpriority = null;
		_id = null;
		_integrity = null;
		_referrerpolicy = null;
		_require = null;
		_sandbox = false;
		_senna = null;
		_src = null;
		_type = null;
		_use = null;
	}

	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/aui/script/page.jsp";

	private boolean _async = false;
	private java.lang.String _blocking = null;
	private java.lang.String _crossorigin = null;
	private boolean _defer = false;
	private java.lang.String _fetchpriority = null;
	private java.lang.String _id = null;
	private java.lang.String _integrity = null;
	private java.lang.String _referrerpolicy = null;
	private java.lang.String _require = null;
	private boolean _sandbox = false;
	private java.lang.String _senna = null;
	private java.lang.String _src = null;
	private java.lang.String _type = null;
	private java.lang.String _use = null;

}