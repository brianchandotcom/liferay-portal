/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

/**
 * @author Keven Leone
 */
public class PublisherAssetLink {

	public PublisherAssetLink(
		long attachmentId, String fileName, String href, String version) {

		this.attachmentId = attachmentId;
		this.fileName = fileName;
		this.href = href;
		this.version = version;
	}

	public long attachmentId;
	public String fileName;
	public String href;
	public String version;

}