/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.ui;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.servlet.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
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
				icon = "trash";
			}

			if (!isLabel()) {
				setLinkCssClass("component-action");
			}
		}

		setIcon(icon);

		if (Validator.isNull(getMessage())) {
			setMessage(LanguageUtil.get(_getResourceBundle(), "delete"));
		}

		String url = getUrl();

		if (url.startsWith(
				"javascript:Liferay.Util.openConfirmModal({message: '")) {

			return super.getPage();
		}

		if (url.startsWith("javascript:")) {
			url = url.substring(11);
		}

		if (url.startsWith(Http.HTTP_WITH_SLASH) ||
			url.startsWith(Http.HTTPS_WITH_SLASH)) {

			url = StringBundler.concat(
				"submitForm(document.hrefFm, '", HtmlUtil.escapeJS(url), "');");
		}

		if (!_trash) {
			StringBundler sb = new StringBundler(5);

			sb.append("javascript:Liferay.Util.openConfirmModal({message: '");

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

			sb.append("', onConfirm: (isConfirmed) => {if (isConfirmed) {");
			sb.append(url);
			sb.append(" } else { self.focus(); }}});");

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