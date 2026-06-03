/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Matthew Kong
 * @generated
 */
@ProviderType
public interface FaroUserFinder {

	public int countByChannelKeywords(
		long channelGroupId, boolean available, String query,
		java.util.List<Integer> statuses, long workspaceGroupId);

	public int countByKeywords(
		long groupId, String query, java.util.List<Integer> statuses);

	public java.util.List<com.liferay.osb.faro.model.FaroUser>
		findByChannelKeywords(
			long channelGroupId, boolean available, String query,
			java.util.List<Integer> statuses, long workspaceGroupId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.osb.faro.model.FaroUser> orderByComparator);

	public java.util.List<com.liferay.osb.faro.model.FaroUser> findByKeywords(
		long groupId, String query, java.util.List<Integer> statuses, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.osb.faro.model.FaroUser> orderByComparator);

}