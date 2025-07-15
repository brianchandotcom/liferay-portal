/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.portal.kernel.util.Validator;

/**
 * @author Felipe Veloso
 */
public class TicketAttachmentIdentifier {

	public TicketAttachmentIdentifier(Long id) {
		_id = id;
	}

	public TicketAttachmentIdentifier(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public Long getId() {
		return _id;
	}

	public boolean isByExternalReferenceCode() {
		return Validator.isNotNull(_externalReferenceCode);
	}

	public boolean isById() {
		return Validator.isNotNull(_id);
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public void setId(Long id) {
		_id = id;
	}

	private String _externalReferenceCode;
	private Long _id;

}