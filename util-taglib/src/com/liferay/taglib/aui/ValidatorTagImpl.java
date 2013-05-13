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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsUtil;
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

	public ValidatorTagImpl(
		String name, String errorMessage, String body, boolean custom) {

		setName(name);
		setErrorMessage(errorMessage);

		_body = body;
		_custom = custom;
	}

	@Override
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

		_custom = ModelHintsUtil.isCustomValidator(name);

		if (_custom) {
			name = ModelHintsUtil.buildCustomValidatorName(name);
		}

		ValidatorTag validatorTag = new ValidatorTagImpl(
			name, getErrorMessage(), _body, _custom);

		inputTag.addValidatorTag(name, validatorTag);

		return EVAL_BODY_BUFFERED;
	}

	@Override
	public String getBody() {
		if (Validator.isNull(_body)) {
			return StringPool.DOUBLE_APOSTROPHE;
		}

		return _body.trim();
	}

	@Override
	public String getErrorMessage() {
		String errorMessage = super.getErrorMessage();

		if (errorMessage == null) {
			return StringPool.BLANK;
		}

		return errorMessage;
	}

	@Override
	public boolean isCustom() {
		return _custom;
	}

	public void setBody(String body) {
		_body = body;
	}

	protected String processCustom(String name) {
		if (name.equals("custom")) {
			_custom = true;

			return name.concat(
				StringPool.UNDERLINE).concat(
					PwdGenerator.getPassword(PwdGenerator.KEY3, 4));
		}

		return name;
	}

	private String _body;
	private boolean _custom;

}