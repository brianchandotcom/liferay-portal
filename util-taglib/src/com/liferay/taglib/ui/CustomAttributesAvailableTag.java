/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.servlet.taglib.TagSupport;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.permission.ExpandoColumnPermissionUtil;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 */
public class CustomAttributesAvailableTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long companyId = _companyId;

			if (companyId == 0) {
				companyId = themeDisplay.getCompanyId();
			}

			ExpandoBridge expandoBridge = null;

			if (_classPK == 0) {
				expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
					companyId, _className);
			}
			else {
				expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
					companyId, _className, _classPK);
			}

			Enumeration<String> enu = expandoBridge.getAttributeNames();

			if (!enu.hasMoreElements()) {
				return SKIP_BODY;
			}

			if (_classPK == 0) {
				return EVAL_BODY_INCLUDE;
			}

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			while (enu.hasMoreElements()) {
				String attributeName = enu.nextElement();

				Serializable value = expandoBridge.getAttribute(attributeName);

				if (Validator.isNull(value)) {
					continue;
				}

				UnicodeProperties properties =
					expandoBridge.getAttributeProperties(attributeName);

				boolean propertyHidden = GetterUtil.getBoolean(
					properties.get(ExpandoColumnConstants.PROPERTY_HIDDEN));
				boolean propertyVisibleWithUpdatePermission =
					GetterUtil.getBoolean(
						properties.get(
							ExpandoColumnConstants.
								PROPERTY_VISIBLE_WITH_UPDATE_PERMISSION));

				if (_editable && propertyVisibleWithUpdatePermission) {
					if (ExpandoColumnPermissionUtil.contains(
							permissionChecker, companyId, _className,
							ExpandoTableConstants.DEFAULT_TABLE_NAME,
							attributeName, ActionKeys.UPDATE)) {

						propertyHidden = false;
					}
					else {
						propertyHidden = true;
					}
				}

				if (!propertyHidden &&
					ExpandoColumnPermissionUtil.contains(
						permissionChecker, companyId, _className,
						ExpandoTableConstants.DEFAULT_TABLE_NAME, attributeName,
						ActionKeys.VIEW)) {

					return EVAL_BODY_INCLUDE;
				}
			}

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_className = null;
				_classPK = 0;
				_companyId = 0;
				_editable = false;
			}
		}
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}

	private String _className;
	private long _classPK;
	private long _companyId;
	private boolean _editable;

}