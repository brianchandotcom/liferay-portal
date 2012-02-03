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

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.myfaces.context.servlet.ServletFacesContextImpl;

/**
 * @author Brian Myunghun Kim
 */
public class MyFacesContextFactoryImpl extends FacesContextFactory {

	@Override
	public FacesContext getFacesContext(
			Object context, Object request, Object response,
			Lifecycle lifecycle)
		throws FacesException {

		if (context == null) {
			throw new NullPointerException("context");
		}

		if (request == null) {
			throw new NullPointerException("request");
		}

		if (response == null) {
			throw new NullPointerException("response");
		}

		if (lifecycle == null) {
			throw new NullPointerException("lifecycle");
		}

		if (context instanceof ServletContext) {
			return new ServletFacesContextImpl(
				(ServletContext)context, (ServletRequest)request,
				(ServletResponse)response);
		}

		if (context instanceof PortletContext) {
			return new MyFacesContextImpl(
				(PortletContext)context, (PortletRequest)request,
				(PortletResponse)response);
		}

		throw new FacesException(
			"Unsupported context type " + getClass().getName());
	}

}