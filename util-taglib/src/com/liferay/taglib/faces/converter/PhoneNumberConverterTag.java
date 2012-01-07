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

package com.liferay.taglib.faces.converter;

import com.liferay.taglib.faces.util.JSFTagUtil;

import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterTag;

import javax.servlet.jsp.JspException;

/**
 * @author Neil Griffin
 */
public class PhoneNumberConverterTag extends ConverterTag {

	public PhoneNumberConverterTag() {
		setConverterId(PhoneNumberConverter.class.getName());
	}

	@Override
	public void release() {
		_unitedStatesFormat = null;
	}

	public void setUnitedStatesFormat(String unitedStatesFormat) {
		_unitedStatesFormat = unitedStatesFormat;
	}

	@Override
	protected Converter createConverter() throws JspException {
		PhoneNumberConverter converter =
			(PhoneNumberConverter)super.createConverter();

		converter.setUnitedStatesFormat(JSFTagUtil.eval(_unitedStatesFormat));

		return converter;
	}

	private String _unitedStatesFormat = "(###) ###-####";

}