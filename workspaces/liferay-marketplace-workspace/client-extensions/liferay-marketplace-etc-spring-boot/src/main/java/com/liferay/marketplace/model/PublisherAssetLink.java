/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

/**
 * @author Keven Leone
 */
public class PublisherAssetLink {

	public PublisherAssetLink(
		long attachmentId, String fileName, String href, String version) {

		_attachmentId = attachmentId;
		_fileName = fileName;
		_href = href;
		_version = version;
	}

	public long getAttachmentId() {
		return _attachmentId;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getHREF() {
		return _href;
	}

	public String getVersion() {
		return _version;
	}

	private final long _attachmentId;
	private final String _fileName;
	private final String _href;
	private final String _version;

}