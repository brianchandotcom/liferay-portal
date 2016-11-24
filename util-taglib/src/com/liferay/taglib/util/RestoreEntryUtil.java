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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.model.TrashEntryConstants;
import com.liferay.trash.kernel.service.TrashEntryLocalServiceUtil;

import javax.portlet.PortletRequest;

/**
 * @author     Eudaldo Alonso
 * @deprecated As of 2.6.0, with no direct replacement
 */
@Deprecated
public class RestoreEntryUtil {

	public static JSONObject checkEntry(PortletRequest portletRequest)
		throws PortalException {

		long trashEntryId = ParamUtil.getLong(portletRequest, "trashEntryId");

		String newName = ParamUtil.getString(portletRequest, "newName");

		TrashEntry entry = TrashEntryLocalServiceUtil.fetchTrashEntry(
			trashEntryId);

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			entry.getClassName());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			trashHandler.checkRestorableEntry(
				entry, TrashEntryConstants.DEFAULT_CONTAINER_ID, newName);

			jsonObject.put("success", true);
		}
		catch (RestoreEntryException ree) {
			jsonObject.put("duplicateEntryId", ree.getDuplicateEntryId());
			jsonObject.put("errorMessage", ree.getErrorMessage());
			jsonObject.put("oldName", ree.getOldName());
			jsonObject.put("overridable", ree.isOverridable());
			jsonObject.put("success", false);
			jsonObject.put("trashEntryId", ree.getTrashEntryId());
		}

		return jsonObject;
	}

}