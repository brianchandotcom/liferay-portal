/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.taglib;

import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.aui.ValidatorTagImpl;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public abstract class BaseValidatorTagSupport extends IncludeTag {

	public void addRequiredValidatorTag() {
		ValidatorTag validatorTag = new ValidatorTagImpl(
			"required", null, null, false);

		addValidatorTag("required", validatorTag);
	}

	public void addValidatorTag(
		String validatorName, ValidatorTag validatorTag) {

		if (_validatorTags == null) {
			_validatorTags = new HashMap<>();
		}

		_validatorTags.put(validatorName, validatorTag);
	}

	@Override
	public int doEndTag() throws JspException {
		updateFormValidatorTags();

		return super.doEndTag();
	}

	public abstract String getInputName();

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_validatorTags = null;
	}

	protected Map<String, ValidatorTag> getValidatorTags() {
		return _validatorTags;
	}

	protected void updateFormValidatorTags() {
		if (_validatorTags == null) {
			return;
		}

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		Map<String, List<ValidatorTag>> validatorTagsMap =
			(Map<String, List<ValidatorTag>>)httpServletRequest.getAttribute(
				"LIFERAY_SHARED_aui:form:validatorTagsMap");

		if (validatorTagsMap != null) {
			List<ValidatorTag> validatorTags = ListUtil.fromMapValues(
				_validatorTags);

			validatorTagsMap.put(getInputName(), validatorTags);
		}
	}

	private Map<String, ValidatorTag> _validatorTags;

}