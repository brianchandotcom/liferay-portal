package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
public class CategorizationFilterTag extends IncludeTag{

	public void setAssetType(String assetType) {
		_assetType = assetType;
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	@Override
	protected void cleanUp() {
		_assetType = null;
		_portletURL = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:categorization-filter:assetType", _assetType);
		request.setAttribute(
			"liferay-ui:categorization-filter:portletURL", _portletURL);
	}

	private static final String _PAGE =
		"/html/taglib/ui/categorization_filter/page.jsp";

	private String _assetType;
	private PortletURL _portletURL;

}