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
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseSelectTag;

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

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void addRequiredValidatorTag() {
		super.addRequiredValidatorTag();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void addValidatorTag(
		String validatorName, ValidatorTag validatorTag) {

		super.addValidatorTag(validatorName, validatorTag);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		addModelValidatorTags();

		if (getRequired()) {
			addRequiredValidatorTag();
		}

		return super.doStartTag();
	}

	@Override
	public String getField() {
		String field = super.getField();

		if (Validator.isNull(field)) {
			field = getName();
		}

		return field;
	}

	@Override
	public String getInputName() {
		return getName();
	}

	@Override
	public Class<?> getModel() {
		Class<?> model = super.getModel();

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		return model;
	}

	protected void addModelValidatorTags() {
		Class<?> model = getModel();

		if (model == null) {
			return;
		}

		List<Tuple> modelValidators = ModelHintsUtil.getValidators(
			model.getName(), getField());

		if (modelValidators == null) {
			return;
		}

		for (Tuple modelValidator : modelValidators) {
			String validatorName = (String)modelValidator.getObject(1);
			String validatorErrorMessage = (String)modelValidator.getObject(2);
			String validatorValue = (String)modelValidator.getObject(3);
			boolean customValidator = (Boolean)modelValidator.getObject(4);

			ValidatorTag validatorTag = new ValidatorTagImpl(
				validatorName, validatorErrorMessage, validatorValue,
				customValidator);

			addValidatorTag(validatorName, validatorTag);
		}
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

		String field = getField();

		if (Validator.isNull(field)) {
			field = name;
		}

		String id = getId();

		if (Validator.isNull(id)) {
			id = AUIUtil.normalizeId(name);
		}

		String label = getLabel();

		if (label == null) {
			label = TextFormatter.format(name, TextFormatter.K);
		}

		String listType = getListType();
		String listTypeFieldName = getListTypeFieldName();

		if (Validator.isNotNull(listType) &&
			Validator.isNull(listTypeFieldName)) {

			listTypeFieldName = "typeId";
		}

		Class<?> model = getModel();

		String title = getTitle();

		if ((title == null) && Validator.isNull(label)) {
			title = TextFormatter.format(name, TextFormatter.K);
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
		setNamespacedAttribute(request, "field", field);
		setNamespacedAttribute(request, "id", id);
		setNamespacedAttribute(request, "label", label);
		setNamespacedAttribute(request, "listTypeFieldName", listTypeFieldName);
		setNamespacedAttribute(request, "model", model);
		setNamespacedAttribute(request, "title", String.valueOf(title));
		setNamespacedAttribute(request, "value", value);

		Map<String, ValidatorTag> validatorTags = getValidatorTags();

		if ((validatorTags != null) &&
			(validatorTags.get("required") != null)) {

			setNamespacedAttribute(
				request, "required", Boolean.TRUE.toString());
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	protected void updateFormValidators() {
		super.updateFormValidatorTags();
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

}