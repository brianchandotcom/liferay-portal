/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.util.strategy;

import com.liferay.marketplace.util.TrialProvisioningContext;

/**
 * @author Eduardo Diniz
 */
public interface TrialStrategy {

	public void configureContext(
		TrialProvisioningContext.TrialProvisioningContextBuilder
			trialProvisioningContextBuilder);

	public boolean supports(String orderTypeExternalReferenceCode);

}