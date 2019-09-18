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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ValidatorTag;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.aui.base.BaseInputTag;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
		updateFormCheckboxNames();

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

	public String getBaseType() {
		Class<?> model = getModel();

		String type = getType();

		String baseType = null;

		if ((model != null) && Validator.isNull(type)) {
			baseType = ModelHintsUtil.getType(model.getName(), getField());
		}
		else if (Validator.isNotNull(type)) {
			if (Objects.equals(type, "checkbox") ||
				Objects.equals(type, "radio") ||
				Objects.equals(type, "resource")) {

				baseType = type;
			}
			else if (Objects.equals(type, "toggle-card") ||
					 Objects.equals(type, "toggle-switch")) {

				baseType = "checkbox";
			}
		}

		if (Validator.isNull(baseType)) {
			baseType = "text";
		}

		return baseType;
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
		String inputName = getName();

		Class<?> model = getModel();

		if ((model != null) && Validator.isNull(getType())) {
			String fieldParam = getFieldParam();

			if (Validator.isNotNull(fieldParam)) {
				inputName = fieldParam;
			}
		}

		return inputName;
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

		if ((model == null) || Validator.isNotNull(getType())) {
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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		Object bean = getBean();

		if (bean == null) {
			bean = pageContext.getAttribute("aui:model-context:bean");
		}

		Class<?> model = getModel();

		String defaultLanguageId = getDefaultLanguageId();

		if (Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = (String)pageContext.getAttribute(
				"aui:model-context:defaultLanguageId");
		}

		if (Validator.isNull(defaultLanguageId) && (model != null) &&
			ModelHintsUtil.hasField(model.getName(), "groupId")) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			defaultLanguageId = LocaleUtil.toLanguageId(
				themeDisplay.getSiteDefaultLocale());
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

				id = AUIUtil.normalizeId(fieldParam);
			}
			else if (!Objects.equals(type, "assetTags") &&
					 !Objects.equals(type, "radio")) {

				id = AUIUtil.normalizeId(name);
			}
			else {
				id = PortalUtil.getUniqueElementId(
					httpServletRequest, StringPool.BLANK,
					AUIUtil.normalizeId(name));
			}
		}

		String forLabel = id;

		if (Objects.equals(type, "assetTags")) {
			forLabel = forLabel.concat("assetTagNames");
		}

		String languageId = getLanguageId();

		if (Validator.isNotNull(languageId)) {
			forLabel = LocalizationUtil.getLocalizedName(forLabel, languageId);
		}

		String label = getLabel();

		if (label == null) {
			label = TextFormatter.format(name, TextFormatter.K);
		}

		String title = getTitle();

		if ((title == null) &&
			(Validator.isNull(label) || Objects.equals(type, "image"))) {

			title = TextFormatter.format(name, TextFormatter.K);
		}

		boolean wrappedField = getWrappedField();

		FieldWrapperTag fieldWrapper = (FieldWrapperTag)findAncestorWithClass(
			this, FieldWrapperTag.class);

		if (fieldWrapper != null) {
			wrappedField = true;
		}

		setNamespacedAttribute(httpServletRequest, "baseType", getBaseType());
		setNamespacedAttribute(httpServletRequest, "bean", bean);
		setNamespacedAttribute(
			httpServletRequest, "defaultLanguageId", defaultLanguageId);
		setNamespacedAttribute(httpServletRequest, "field", field);
		setNamespacedAttribute(httpServletRequest, "forLabel", forLabel);
		setNamespacedAttribute(httpServletRequest, "formName", formName);
		setNamespacedAttribute(httpServletRequest, "id", id);
		setNamespacedAttribute(httpServletRequest, "label", label);
		setNamespacedAttribute(httpServletRequest, "model", model);
		setNamespacedAttribute(
			httpServletRequest, "title", String.valueOf(title));
		setNamespacedAttribute(
			httpServletRequest, "wrappedField", wrappedField);

		httpServletRequest.setAttribute(
			getAttributeNamespace() + "value", getValue());

		Map<String, ValidatorTag> validatorTags = getValidatorTags();

		if ((validatorTags != null) &&
			(validatorTags.get("required") != null)) {

			setNamespacedAttribute(
				httpServletRequest, "required", Boolean.TRUE.toString());
		}
	}

	protected void updateFormCheckboxNames() {
		if (!Objects.equals(getBaseType(), "checkbox")) {
			return;
		}

		HttpServletRequest httpServletRequest = getRequest();

		List<String> checkboxNames =
			(List<String>)httpServletRequest.getAttribute(
				"LIFERAY_SHARED_aui:form:checkboxNames");

		if (checkboxNames != null) {
			String inputName = getInputName();

			String languageId = getLanguageId();

			if (Validator.isNotNull(languageId)) {
				inputName = LocalizationUtil.getLocalizedName(
					inputName, languageId);
			}

			checkboxNames.add(inputName);
		}
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

}