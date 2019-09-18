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

package com.liferay.taglib.ui;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.ResourceBundle;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconDeleteTag extends IconTag {

	public String getConfirmation() {
		return _confirmation;
	}

	public boolean isShowIcon() {
		return _showIcon;
	}

	public boolean isTrash() {
		return _trash;
	}

	public void setConfirmation(String confirmation) {
		_confirmation = confirmation;
	}

	public void setShowIcon(boolean showIcon) {
		_showIcon = showIcon;
	}

	public void setTrash(boolean trash) {
		_trash = trash;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_confirmation = null;
		_resourceBundle = null;
		_showIcon = false;
		_trash = false;
	}

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(getServletContext(), _PAGE)) {
			return _PAGE;
		}

		String cssClass = GetterUtil.getString(getCssClass());

		setCssClass(cssClass.concat(" item-remove"));

		String icon = StringPool.BLANK;

		if (_showIcon) {
			icon = getIcon();

			if (Validator.isNull(icon)) {
				if (_trash) {
					icon = "trash";
				}
				else {
					icon = "times-circle";
				}
			}
		}

		setIcon(icon);

		setMarkupView("lexicon");

		if (Validator.isNull(getMessage())) {
			if (_trash) {
				setMessage(
					LanguageUtil.get(
						_getResourceBundle(), "move-to-recycle-bin"));
			}
			else {
				setMessage(LanguageUtil.get(_getResourceBundle(), "delete"));
			}
		}

		String url = getUrl();

		if (url.startsWith("javascript:if (confirm('")) {
			return super.getPage();
		}

		if (url.startsWith("javascript:")) {
			url = url.substring(11);
		}

		if (url.startsWith(Http.HTTP_WITH_SLASH) ||
			url.startsWith(Http.HTTPS_WITH_SLASH)) {

			url = "submitForm(document.hrefFm, '".concat(
				HtmlUtil.escapeJS(url)
			).concat(
				"');"
			);
		}

		if (!_trash) {
			StringBundler sb = new StringBundler(5);

			sb.append("javascript:if (confirm('");

			if (Validator.isNotNull(_confirmation)) {
				sb.append(
					UnicodeLanguageUtil.get(
						_getResourceBundle(), _confirmation));
			}
			else {
				String confirmation = "are-you-sure-you-want-to-delete-this";

				sb.append(
					UnicodeLanguageUtil.get(
						_getResourceBundle(), confirmation));
			}

			sb.append("')) { ");
			sb.append(url);
			sb.append(" } else { self.focus(); }");

			url = sb.toString();
		}
		else {
			url = "javascript:".concat(url);
		}

		setUrl(url);

		return super.getPage();
	}

	private ResourceBundle _getResourceBundle() {
		if (_resourceBundle == null) {
			_resourceBundle = TagResourceBundleUtil.getResourceBundle(
				pageContext);
		}

		return _resourceBundle;
	}

	private static final String _PAGE = "/html/taglib/ui/icon_delete/page.jsp";

	private String _confirmation;
	private ResourceBundle _resourceBundle;
	private boolean _showIcon;
	private boolean _trash;

}