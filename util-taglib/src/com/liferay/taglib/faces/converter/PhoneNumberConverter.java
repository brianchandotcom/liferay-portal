/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * <p>
 * This class is a JSF converter that can be used to convert phone numbers.
 * Since phone numbers in the United States of America always have 10 digits,
 * this converter provides automatic conversion of 10 digit phone numbers to a
 * desired format. The format is specified by the unitedStatesFormat component
 * attribute.
 * </p>
 *
 * @author Neil Griffin
 */
public class PhoneNumberConverter implements Converter, StateHolder {

	public Object getAsObject(
		FacesContext facesContext, UIComponent uiComponent, String value) {

		if (value == null) {
			return null;
		}

		StringBuilder integerChars = new StringBuilder(value.length());
		StringBuilder invalidChars = new StringBuilder(value.length());

		for (int i = 0; i < value.length(); i++) {
			char curChar = value.charAt(i);

			if (Character.isDigit(curChar)) {
				integerChars.append(curChar);
			}
			else if ((curChar != '-') && (curChar != '(') &&
					 (curChar != ')') && (curChar != '.') &&
					 (curChar != '+') && (curChar != ' ')) {

				invalidChars.append(curChar);
			}
		}

		if (invalidChars.length() > 0) {
			ExternalContext externalContext = facesContext.getExternalContext();

			Locale locale = externalContext.getRequestLocale();

			String summary = LanguageUtil.get(
				locale, "the-following-are-invalid-characters");

			summary += " " + invalidChars.toString();

			FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_ERROR, summary, null);

			throw new ConverterException(facesMessage);
		}
		else if (integerChars.length() == 10) {
			StringBuilder unitedStatesPhoneNumber = new StringBuilder(
				_unitedStatesFormat.length());

			int integerDigitIndex = 0;

			for (int i = 0; i < _unitedStatesFormat.length(); i++) {
				char curChar = _unitedStatesFormat.charAt(i);

				if (curChar == '#') {
					unitedStatesPhoneNumber.append(
						integerChars.charAt(integerDigitIndex++));
				}
				else {
					unitedStatesPhoneNumber.append(curChar);
				}
			}

			return unitedStatesPhoneNumber.toString();
		}

		return value;
	}

	public String getAsString(
			FacesContext facesContext, UIComponent uiComponent, Object value)
		throws ConverterException {

		// ICE-1537

		return (String)value;
	}

	public String getUnitedStatesFormat() {
		return _unitedStatesFormat;
	}

	public boolean isTransient() {
		return _transient;
	}

	public void restoreState(FacesContext facesContext, Object obj) {
		Object[] values = (Object[])obj;

		_unitedStatesFormat = (String)values[0];
	}

	public Object saveState(FacesContext facesContext) {
		Object[] values = new Object[1];

		values[0] = _unitedStatesFormat;

		return values;
	}

	public void setTransient(boolean value) {
		_transient = value;
	}

	public void setUnitedStatesFormat(String unitedStatesFormat) {
		_unitedStatesFormat = unitedStatesFormat;
	}

	private boolean _transient;
	private String _unitedStatesFormat;

}