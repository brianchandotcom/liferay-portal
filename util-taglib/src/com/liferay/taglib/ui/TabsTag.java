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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Brian Wing Shun Chan
 */
public class TabsTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			include(getEndPage());

			request.removeAttribute("liferay-ui:tabs:names");
			request.removeAttribute("liferay-ui:tabs:values");
			request.removeAttribute("liferay-ui:tabs:formName");
			request.removeAttribute("liferay-ui:tabs:param");
			request.removeAttribute("liferay-ui:tabs:value");
			request.removeAttribute("liferay-ui:tabs:portletURL");
			request.removeAttribute("liferay-ui:tabs:url");
			request.removeAttribute("liferay-ui:tabs:url0");
			request.removeAttribute("liferay-ui:tabs:url1");
			request.removeAttribute("liferay-ui:tabs:url2");
			request.removeAttribute("liferay-ui:tabs:url3");
			request.removeAttribute("liferay-ui:tabs:url4");
			request.removeAttribute("liferay-ui:tabs:url5");
			request.removeAttribute("liferay-ui:tabs:url6");
			request.removeAttribute("liferay-ui:tabs:url7");
			request.removeAttribute("liferay-ui:tabs:url8");
			request.removeAttribute("liferay-ui:tabs:url9");
			request.removeAttribute("liferay-ui:tabs:backLabel");
			request.removeAttribute("liferay-ui:tabs:backURL");
			request.removeAttribute("liferay-ui:tabs:refresh");
			request.removeAttribute("liferay-ui:tabs:onClick");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_startPage = null;
				_endPage = null;
				_names = null;
				_namesPos = 0;
				_tabsValues = null;
				_formName = StringPool.BLANK;
				_param = "tabs1";
				_value = null;
				_portletURL = null;
				_url = null;
				_url0 = null;
				_url1 = null;
				_url2 = null;
				_url3 = null;
				_url4 = null;
				_url5 = null;
				_url6 = null;
				_url7 = null;
				_url8 = null;
				_url9 = null;
				_backLabel = null;
				_backURL = null;
				_refresh = true;
				_onClick = null;
			}
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			request.setAttribute("liferay-ui:tabs:names", _names);

			if ((_tabsValues == null) || (_tabsValues.length < _names.length)) {
				_tabsValues = _names;
			}

			request.setAttribute("liferay-ui:tabs:values", _tabsValues);

			request.setAttribute("liferay-ui:tabs:formName", _formName);
			request.setAttribute("liferay-ui:tabs:param", _param);

			if (_value == null) {
				if (_tabsValues.length > 0) {
					_value = ParamUtil.getString(
						request, _param, _tabsValues[0]);
				}
			}

			if (Validator.isNull(_value)) {
				if (_tabsValues.length > 0) {
					_value = _tabsValues[0];
				}
				else {
					_value = StringPool.BLANK;
				}
			}

			boolean match = false;

			if (ArrayUtil.contains(_tabsValues, _value)) {
				match = true;
			}

			if (!match) {
				if (_tabsValues.length > 0) {
					_value = _tabsValues[0];
				}
				else {
					_value = StringPool.BLANK;
				}
			}

			request.setAttribute("liferay-ui:tabs:value", _value);
			request.setAttribute("liferay-ui:tabs:portletURL", _portletURL);
			request.setAttribute("liferay-ui:tabs:url", _url);

			if (_url0 != null) {
				request.setAttribute("liferay-ui:tabs:url0", _url0);
			}

			if (_url1 != null) {
				request.setAttribute("liferay-ui:tabs:url1", _url1);
			}

			if (_url2 != null) {
				request.setAttribute("liferay-ui:tabs:url2", _url2);
			}

			if (_url3 != null) {
				request.setAttribute("liferay-ui:tabs:url3", _url3);
			}

			if (_url4 != null) {
				request.setAttribute("liferay-ui:tabs:url4", _url4);
			}

			if (_url5 != null) {
				request.setAttribute("liferay-ui:tabs:url5", _url5);
			}

			if (_url6 != null) {
				request.setAttribute("liferay-ui:tabs:url6", _url6);
			}

			if (_url7 != null) {
				request.setAttribute("liferay-ui:tabs:url7", _url7);
			}

			if (_url8 != null) {
				request.setAttribute("liferay-ui:tabs:url8", _url8);
			}

			if (_url9 != null) {
				request.setAttribute("liferay-ui:tabs:url9", _url9);
			}

			request.setAttribute("liferay-ui:tabs:backLabel", _backLabel);
			request.setAttribute("liferay-ui:tabs:backURL", _backURL);
			request.setAttribute(
				"liferay-ui:tabs:refresh", String.valueOf(_refresh));
			request.setAttribute(
				"liferay-ui:tabs:onClick", String.valueOf(_onClick));

			include(getStartPage());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getOnClick() {
		return _onClick;
	}

	public String getParam() {
		return _param;
	}

	public String getSectionName() {
		if (_names.length > _namesPos) {
			return _names[_namesPos];
		}
		else {
			return StringPool.BLANK;
		}
	}

	public boolean getSectionSelected() {
		if ((_names.length == 0) ||
			((_names.length > _namesPos) &&
			 _names[_namesPos].equals(_value))) {

			return true;
		}
		else {
			return false;
		}
	}

	public void incrementSection() {
		_namesPos++;
	}

	public boolean isRefresh() {
		return _refresh;
	}

	public void setBackLabel(String backLabel) {
		_backLabel = backLabel;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setNames(String names) {
		_names = StringUtil.split(names);
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setParam(String param) {
		_param = param;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setRefresh(boolean refresh) {
		_refresh = refresh;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	public void setTabsValues(String tabsValues) {
		_tabsValues = StringUtil.split(tabsValues);
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUrl0(String url0) {
		_url0 = url0;
	}

	public void setUrl1(String url1) {
		_url1 = url1;
	}

	public void setUrl2(String url2) {
		_url2 = url2;
	}

	public void setUrl3(String url3) {
		_url3 = url3;
	}

	public void setUrl4(String url4) {
		_url4 = url4;
	}

	public void setUrl5(String url5) {
		_url5 = url5;
	}

	public void setUrl6(String url6) {
		_url6 = url6;
	}

	public void setUrl7(String url7) {
		_url7 = url7;
	}

	public void setUrl8(String url8) {
		_url8 = url8;
	}

	public void setUrl9(String url9) {
		_url9 = url9;
	}

	public void setValue(String value) {
		_value = value;
	}

	@Override
	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	@Override
	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	private static final String _END_PAGE = "/html/taglib/ui/tabs/end.jsp";

	private static final String _START_PAGE = "/html/taglib/ui/tabs/start.jsp";

	private String _backLabel;
	private String _backURL;
	private String _endPage;
	private String _formName;
	private String[] _names;
	private int _namesPos;
	private String _onClick;
	private String _param = "tabs1";
	private PortletURL _portletURL;
	private boolean _refresh = true;
	private String _startPage;
	private String[] _tabsValues;
	private String _url;
	private String _url0;
	private String _url1;
	private String _url2;
	private String _url3;
	private String _url4;
	private String _url5;
	private String _url6;
	private String _url7;
	private String _url8;
	private String _url9;
	private String _value;

}