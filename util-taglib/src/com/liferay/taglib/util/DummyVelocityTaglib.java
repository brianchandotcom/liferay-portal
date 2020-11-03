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

package com.liferay.taglib.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.taglib.ui.BreadcrumbTag;
import com.liferay.taglib.ui.IconTag;

import javax.portlet.WindowState;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

/**
 * @author Daniel Reuther
 */
public class DummyVelocityTaglib implements VelocityTaglib {

	@Override
	public String actionURL(long plid, String portletName, String queryString) {
		return null;
	}

	@Override
	public String actionURL(String portletName, String queryString) {
		return null;
	}

	@Override
	public String actionURL(
		String windowState, String portletMode, Boolean secure,
		Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
		long plid, long refererPlid, String portletName, Boolean anchor,
		Boolean encrypt, long doAsGroupId, long doAsUserId,
		Boolean portletConfiguration, String queryString) {

		return null;
	}

	@Override
	public String actionURL(
		String windowState, String portletMode, long plid, String portletName,
		String queryString) {

		return null;
	}

	@Override
	public String actionURL(
		String windowState, String portletMode, String portletName,
		String queryString) {

		return null;
	}

	@Override
	public void breadcrumb() {
	}

	@Override
	public void breadcrumb(
		long ddmTemplateGroupId, String ddmTemplateKey, boolean showGuestGroup,
		boolean showParentGroups, boolean showLayout,
		boolean showPortletBreadcrumb) {
	}

	@Override
	public void doAsURL(long doAsUserId) {
	}

	@Override
	public BreadcrumbTag getBreadcrumbTag() {
		return null;
	}

	@Override
	public IconTag getIconTag() {
		return null;
	}

	@Override
	public PageContext getPageContext() {
		return null;
	}

	@Override
	public String getSetting(String name) {
		return null;
	}

	@Override
	public WindowState getWindowState(String windowState) {
		return null;
	}

	@Override
	public void icon(String image, boolean label, String message, String url) {
	}

	@Override
	public void iconHelp(String message) {
	}

	@Override
	public void include(ServletContext servletContext, String page) {
	}

	@Override
	public void include(String page) {
	}

	@Override
	public void language() {
	}

	@Override
	public void language(
		String formName, String formAction, String name, String displayStyle) {
	}

	@Override
	public void language(
		String formName, String formAction, String name, String[] languageIds,
		String displayStyle) {
	}

	@Override
	public void metaTags() {
	}

	@Override
	public String permissionsURL(
		String redirect, String modelResource, String modelResourceDescription,
		Object resourceGroupId, String resourcePrimKey, String windowState,
		int[] roleTypes) {

		return null;
	}

	@Override
	public void portletIconOptions() {
	}

	@Override
	public void portletIconOptions(String direction, String markupView) {
	}

	@Override
	public void portletIconPortlet() {
	}

	@Override
	public void portletIconPortlet(Portlet portlet) {
	}

	@Override
	public String renderURL(long plid, String portletName, String queryString) {
		return null;
	}

	@Override
	public String renderURL(String portletName, String queryString) {
		return null;
	}

	@Override
	public String renderURL(
		String windowState, String portletMode, Boolean secure,
		Boolean copyCurrentRenderParameters, Boolean escapeXml, long plid,
		long refererPlid, String portletName, Boolean anchor, Boolean encrypt,
		long doAsGroupId, long doAsUserId, Boolean portletConfiguration,
		String queryString) {

		return null;
	}

	@Override
	public String renderURL(
		String windowState, String portletMode, long plid, String portletName,
		String queryString) {

		return null;
	}

	@Override
	public String renderURL(
		String windowState, String portletMode, String portletName,
		String queryString) {

		return null;
	}

	@Override
	public void runtime(String portletName) {
	}

	@Override
	public void runtime(
		String portletProviderClassName,
		PortletProvider.Action portletProviderAction) {
	}

	@Override
	public void runtime(
		String portletProviderClassName,
		PortletProvider.Action portletProviderAction, String instanceId) {
	}

	@Override
	public void runtime(
		String portletProviderClassName,
		PortletProvider.Action portletProviderAction, String instanceId,
		String defaultPreferences) {
	}

	@Override
	public void runtime(String portletName, String queryString) {
	}

	@Override
	public void runtime(
		String portletName, String queryString, String defaultPreferences) {
	}

	@Override
	public void runtime(
		String portletName, String instanceId, String queryString,
		String defaultPreferences) {
	}

	@Override
	public void toggle(
		String id, String showImage, String hideImage, String showMessage,
		String hideMessage, boolean defaultShowContent) {
	}

	@Override
	public String wrapPortlet(String wrapPage, String portletPage) {
		return StringPool.BLANK;
	}

}