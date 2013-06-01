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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.taglib.aui.base.BaseInputTag;
import com.liferay.util.PwdGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class InputTag extends BaseInputTag {

	@Override
	public int doEndTag() throws JspException {
		updateFormValidators();

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		addModelValidatorTags();
		addRequiredValidatorTag();

		return super.doStartTag();
	}

	protected void addModelValidatorTags() {
		Class<?> model = getModel();

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		if ((model == null) || Validator.isNotNull(getType())) {
			return;
		}

		String field = getField();

		if (Validator.isNull(field)) {
			field = getName();
		}

		List<Tuple> modelValidators = ModelHintsUtil.getValidators(
			model.getName(), field);

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
	protected void cleanUp() {
		super.cleanUp();

		_forLabel = null;
		_validators = null;
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

		Class<?> model = getModel();

		if (model == null) {
			model = (Class<?>)pageContext.getAttribute(
				"aui:model-context:model");
		}

		String defaultLanguageId = getDefaultLanguageId();

		if (Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = (String)pageContext.getAttribute(
				"aui:model-context:defaultLanguageId");
		}

		if (Validator.isNull(defaultLanguageId)) {
			Locale defaultLocale = LocaleUtil.getDefault();

			defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);
		}

		String name = getName();

		int pos = name.indexOf(StringPool.DOUBLE_DASH);

		if (pos != -1) {
			name = name.substring(pos + 2, name.length() - 2);
		}

		String field = getField();

		if (Validator.isNull(field)) {
			field = getName();
		}

		String formName = getFormName();

		if (formName == null) {
			FormTag formTag = (FormTag)findAncestorWithClass(
				this, FormTag.class);

			if (formTag != null) {
				formName = formTag.getName();
			}
		}

		String id = getId();
		String type = getType();

		if (Validator.isNull(id)) {
			String fieldParam = getFieldParam();

			if ((model != null) && Validator.isNull(type) &&
				Validator.isNotNull(fieldParam)) {

				id = fieldParam;
			}
			else if (!Validator.equals(type, "assetTags") &&
					 !Validator.equals(type, "radio")) {

				id = name;
			}
			else {
				id = PwdGenerator.getPassword(PwdGenerator.KEY3, 4);
			}
		}

		String label = getLabel();

		if (label == null) {
			label = TextFormatter.format(name, TextFormatter.K);
		}

		_forLabel = id;
		_inputName = getName();

		String baseType = null;

		if ((model != null) && Validator.isNull(type)) {
			baseType = ModelHintsUtil.getType(model.getName(), field);

			String fieldParam = getFieldParam();

			if (Validator.isNotNull(fieldParam)) {
				_inputName = fieldParam;
			}
		}
		else if (Validator.isNotNull(type)) {
			if (Validator.equals(type, "checkbox") ||
				Validator.equals(type, "radio")) {

				baseType = type;
			}
		}

		if (Validator.isNull(baseType)) {
			baseType = "text";
		}

		setNamespacedAttribute(request, "baseType", baseType);
		setNamespacedAttribute(request, "bean", bean);
		setNamespacedAttribute(request, "defaultLanguageId", defaultLanguageId);
		setNamespacedAttribute(request, "field", field);
		setNamespacedAttribute(request, "forLabel", _forLabel);
		setNamespacedAttribute(request, "formName", formName);
		setNamespacedAttribute(request, "id", id);
		setNamespacedAttribute(request, "label", label);
		setNamespacedAttribute(request, "model", model);

		request.setAttribute(getAttributeNamespace() + "value", getValue());

		if ((_validators != null) && (_validators.get("required") != null)) {
			setNamespacedAttribute(
				request, "required", Boolean.TRUE.toString());
		}
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

			validatorTagsMap.put(_inputName, validatorTags);
		}
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private String _forLabel;
	private String _inputName;
	private Map<String, ValidatorTag> _validators;

}