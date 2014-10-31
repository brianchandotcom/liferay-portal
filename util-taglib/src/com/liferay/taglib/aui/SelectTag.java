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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseSelectTag;

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
public class SelectTag extends BaseSelectTag {

	@Override
	public int doEndTag() throws JspException {
		updateFormValidators();

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		addRequiredValidatorTag();

		return super.doStartTag();
	}

	protected void addRequiredValidatorTag() {
		if (!getRequired()) {
			return;
		}

		ValidatorTag validatorTag = new ValidatorTagImpl(
			"required", null, null, false);

		addValidatorTag("required", validatorTag);
	}

	protected void addValidatorTag(
		String validatorName, ValidatorTag validatorTag) {

		if (_validators == null) {
			_validators = new HashMap<String, ValidatorTag>();
		}

		_validators.put(validatorName, validatorTag);
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		Object bean = getBean();

		if (bean == null) {
			bean = pageContext.getAttribute("aui:model-context:bean");
		}

		String name = getName();

		int pos = name.indexOf(StringPool.DOUBLE_DASH);

		if (pos != -1) {
			name = name.substring(pos + 2, name.length() - 2);
		}

		String id = getId();

		if (Validator.isNull(id)) {
			id = AUIUtil.normalizeId(name);
		}

		String label = getLabel();

		if (label == null) {
			label = TextFormatter.format(name, TextFormatter.P);
		}

		String listType = getListType();
		String listTypeFieldName = getListTypeFieldName();

		if (Validator.isNotNull(listType) &&
			Validator.isNull(listTypeFieldName)) {

			listTypeFieldName = "typeId";
		}

		String title = getTitle();

		if ((title == null) && Validator.isNull(label)) {
			title = TextFormatter.format(name, TextFormatter.P);
		}

		String value = String.valueOf(getValue());

		if (Validator.isNull(listType)) {
			if (bean != null) {
				value = BeanPropertiesUtil.getStringSilent(bean, name, value);
			}

			if (!getIgnoreRequestValue()) {
				value = ParamUtil.getString(request, name, value);
			}
		}

		setNamespacedAttribute(request, "bean", bean);
		setNamespacedAttribute(request, "id", id);
		setNamespacedAttribute(request, "label", label);
		setNamespacedAttribute(request, "listTypeFieldName", listTypeFieldName);
		setNamespacedAttribute(request, "title", String.valueOf(title));
		setNamespacedAttribute(request, "value", value);
	}

	protected void updateFormValidators() {
		if (_validators == null) {
			return;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		Map<String, List<ValidatorTag>> validatorTagsMap =
			(Map<String, List<ValidatorTag>>)request.getAttribute(
				"aui:form:validatorTagsMap");

		if (validatorTagsMap != null) {
			List<ValidatorTag> validatorTags = ListUtil.fromMapValues(
				_validators);

			validatorTagsMap.put(getName(), validatorTags);
		}
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private Map<String, ValidatorTag> _validators;

}