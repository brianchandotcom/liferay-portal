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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseValidatorTagImpl;
import com.liferay.util.PwdGenerator;

import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class ValidatorTagImpl
	extends BaseValidatorTagImpl implements BodyTag, ValidatorTag {

	public ValidatorTagImpl() {
	}

	public ValidatorTagImpl(String name, String errorMessage, String body) {
		setName(name);
		setErrorMessage(errorMessage);

		_body = body;

		processCustom();
	}

	public void cleanUp() {
		super.cleanUp();

		_body = null;
		_custom = false;
	}

	@Override
	public int doAfterBody() {
		BodyContent bodyContent = getBodyContent();

		if (bodyContent != null) {
			_body = bodyContent.getString();
		}

		return SKIP_BODY;
	}

	@Override
	public int doEndTag() {
		InputTag inputTag = (InputTag)findAncestorWithClass(
			this, InputTag.class);

		String name = getName();

		ValidatorTag validatorTag = new ValidatorTagImpl(
			name, getErrorMessage(), _body);

		inputTag.addValidatorTag(name, validatorTag);

		return EVAL_BODY_BUFFERED;
	}

	public String getBody() {
		if (Validator.isNull(_body)) {
			return StringPool.DOUBLE_APOSTROPHE;
		}

		return _body.trim();
	}

	public String getErrorMessage() {
		String errorMessage = super.getErrorMessage();

		if (errorMessage == null) {
			return StringPool.BLANK;
		}

		return errorMessage;
	}

	public boolean isCustom() {
		return _custom;
	}

	public void setBody(String body) {
		_body = body;
	}

	protected void processCustom() {
		String name = getName();

		if (name.equals("custom")) {
			_custom = true;

			setName(name.concat(StringPool.UNDERLINE).concat(
				PwdGenerator.getPassword(PwdGenerator.KEY3, 4)));
		}
	}

	private String _body;
	private boolean _custom = false;

}