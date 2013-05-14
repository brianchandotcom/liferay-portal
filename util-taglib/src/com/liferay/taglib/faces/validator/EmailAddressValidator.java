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

package com.liferay.taglib.faces.validator;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.commons.validator.EmailValidator;

/**
 * @author Neil Griffin
 */
public class EmailAddressValidator
	implements StateHolder, javax.faces.validator.Validator {

	@Override
	public boolean isTransient() {
		return _transient;
	}

	@Override
	public void restoreState(FacesContext facesContext, Object obj) {
	}

	@Override
	public Object saveState(FacesContext facesContext) {
		return null;
	}

	@Override
	public void setTransient(boolean value) {
		_transient = value;
	}

	@Override
	public void validate(
			FacesContext facesContext, UIComponent uiComponent, Object obj)
		throws ValidatorException {

		ExternalContext externalContext = facesContext.getExternalContext();

		Locale locale = externalContext.getRequestLocale();

		if (obj instanceof String) {
			String emailAddress = (String)obj;

			if (Validator.isNotNull(emailAddress)) {
				if (!EmailValidator.getInstance().isValid(emailAddress)) {
					String summary = LanguageUtil.get(
						locale, "please-enter-a-valid-email-address");

					FacesMessage facesMessage = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, summary, null);

					throw new ValidatorException(facesMessage);
				}
			}
		}
		else {
			String summary = LanguageUtil.format(
				locale,
				"validator-expected-type-string,-but-instead-received-type-x",
				obj.getClass().getName());

			FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_ERROR, summary, null);

			throw new ValidatorException(facesMessage);
		}
	}

	private boolean _transient;

}