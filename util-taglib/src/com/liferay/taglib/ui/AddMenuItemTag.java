package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ambrin Chaudhary
 */
public class AddMenuItemTag extends IncludeTag {

	public void setTitle(String _title) {
		this._title = _title;
	}

	public void setUrl(String _url) {
		this._url = _url;
	}

	@Override
	protected void cleanUp() {
		_title = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return "/html/taglib/ui/add_menu_item/page.jsp";
	}

	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:add-menu-item:title", _title);
		request.setAttribute("liferay-ui:add-menu-item:url", _url);
	}

	private String _title;
	private String _url;

}