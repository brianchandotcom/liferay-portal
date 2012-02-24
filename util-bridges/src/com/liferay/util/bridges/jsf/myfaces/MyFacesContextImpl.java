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

package com.liferay.util.bridges.jsf.myfaces;

import javax.faces.context.ResponseWriter;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.apache.myfaces.context.ReleaseableExternalContext;
import org.apache.myfaces.context.servlet.ServletFacesContextImpl;

/**
 * @author Brian Myunghun Kim
 */
public class MyFacesContextImpl extends ServletFacesContextImpl {

	public MyFacesContextImpl(
		PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		super(portletContext, portletRequest, portletResponse);
	}

	@Override
	public ResponseWriter getResponseWriter() {
		return _responseWriter;
	}

	@Override
	public void release() {
		super.release();

		_responseWriter = null;
	}

	@Override
	public void setExternalContext(ReleaseableExternalContext extContext) {
		_responseWriter = null;

		super.setExternalContext(extContext);
	}

	@Override
	public void setResponseWriter(ResponseWriter responseWriter) {
		if (responseWriter == null) {
			throw new NullPointerException("responseWriter");
		}

		_responseWriter = responseWriter;
	}

	private ResponseWriter _responseWriter = null;

}