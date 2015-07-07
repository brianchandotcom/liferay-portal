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

package com.liferay.taglib.ddm;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMFormValuesToFieldsConverterUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.taglib.ddm.base.BaseHTMLTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Basto
 */
public class HTMLTag extends BaseHTMLTag {

	protected DDMForm getDDMForm() {
		try {
			return DDMUtil.getDDMForm(getClassNameId(), getClassPK());
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(getLogMessage(), pe);
			}
		}

		return null;
	}

	protected String getDDMFormValuesInputName() {
		String fieldsNamespace = GetterUtil.getString(getFieldsNamespace());

		return fieldsNamespace + "ddmFormValues";
	}

	protected Fields getFields() {
		try {
			long ddmStructureId = getClassPK();

			if (getClassNameId() ==
					PortalUtil.getClassNameId(DDMTemplate.class)) {

				DDMTemplate ddmTemplate =
					DDMTemplateLocalServiceUtil.getTemplate(getClassPK());

				ddmStructureId = ddmTemplate.getClassPK();
			}

			DDMStructure ddmStructure = DDMStructureServiceUtil.getStructure(
				ddmStructureId);

			if (getDdmFormValues() != null) {
				return DDMFormValuesToFieldsConverterUtil.convert(
					ddmStructure, getDdmFormValues());
			}
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(getLogMessage(), pe);
			}
		}

		return null;
	}

	protected String getLogMessage() {
		if (getClassNameId() == PortalUtil.getClassNameId(DDMTemplate.class)) {
			return "Unable to retrieve DDM template with ID " + getClassPK();
		}

		return "Unable to retrieve DDM structure with ID " + getClassPK();
	}

	protected String getMode() {
		if (getClassNameId() != PortalUtil.getClassNameId(DDMTemplate.class)) {
			return null;
		}

		try {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				getClassPK());

			return ddmTemplate.getMode();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(getLogMessage(), pe);
			}
		}

		return null;
	}

	protected String getRandomNamespace() {
		return PortalUtil.generateRandomKey(request, "taglib_ddm_init-ext");
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(request, "ddmForm", getDDMForm());
		setNamespacedAttribute(
			request, "ddmFormValuesInputName", getDDMFormValuesInputName());
		setNamespacedAttribute(request, "fields", getFields());
		setNamespacedAttribute(request, "mode", getMode());
		setNamespacedAttribute(
			request, "randomNamespace", getRandomNamespace());
	}

	private static final Log _log = LogFactoryUtil.getLog(HTMLTag.class);

}