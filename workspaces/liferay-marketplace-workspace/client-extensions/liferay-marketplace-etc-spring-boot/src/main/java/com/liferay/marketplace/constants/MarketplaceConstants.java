/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.constants;

/**
 * @author Keven Leone
 */
public class MarketplaceConstants {

	public static final int ORDER_PAYMENT_STATUS_COMPLETED = 0;

	public static final int ORDER_PAYMENT_STATUS_NOT_REQUIRED = 23;

	public static final int ORDER_STATUS_CANCELLED = 8;

	public static final String ORDER_STATUS_CANCELLED_LABEL = "Cancelled";

	public static final int ORDER_STATUS_COMPLETED = 0;

	public static final String ORDER_STATUS_COMPLETED_LABEL = "Completed";

	public static final int ORDER_STATUS_IN_PROGRESS = 6;

	public static final String ORDER_STATUS_IN_PROGRESS_LABEL = "In Progress";

	public static final int ORDER_STATUS_ON_HOLD = 20;

	public static final String ORDER_STATUS_ON_HOLD_LABEL = "On Hold";

	public static final int ORDER_STATUS_OPEN = 2;

	public static final String ORDER_STATUS_OPEN_LABEL = "Open";

	public static final int ORDER_STATUS_PENDING = 1;

	public static final String ORDER_STATUS_PENDING_LABEL = "Pending";

	public static final int ORDER_STATUS_PROCESSING = 10;

	public static final String ORDER_STATUS_PROCESSING_LABEL = "Processing";

	public static String getOrderStatusLabel(int orderStatus) {
		if (orderStatus == ORDER_STATUS_CANCELLED) {
			return ORDER_STATUS_CANCELLED_LABEL;
		}

		if (orderStatus == ORDER_STATUS_COMPLETED) {
			return ORDER_STATUS_COMPLETED_LABEL;
		}

		if (orderStatus == ORDER_STATUS_IN_PROGRESS) {
			return ORDER_STATUS_IN_PROGRESS_LABEL;
		}

		if (orderStatus == ORDER_STATUS_ON_HOLD) {
			return ORDER_STATUS_ON_HOLD_LABEL;
		}

		if (orderStatus == ORDER_STATUS_OPEN) {
			return ORDER_STATUS_OPEN_LABEL;
		}

		if (orderStatus == ORDER_STATUS_PENDING) {
			return ORDER_STATUS_PENDING_LABEL;
		}

		if (orderStatus == ORDER_STATUS_PROCESSING) {
			return ORDER_STATUS_PROCESSING_LABEL;
		}

		return null;
	}

}