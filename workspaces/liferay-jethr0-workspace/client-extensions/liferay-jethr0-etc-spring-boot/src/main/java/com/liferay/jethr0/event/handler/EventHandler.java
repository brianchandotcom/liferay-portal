/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

/**
 * @author Michael Hashimoto
 */
public interface EventHandler {

	public String process() throws Exception;

	public enum EventType {

		BUILD_COMPLETED, BUILD_STARTED, COMPUTER_BUSY, COMPUTER_IDLE,
		COMPUTER_OFFLINE, COMPUTER_ONLINE, COMPUTER_TEMPORARILY_OFFLINE,
		COMPUTER_TEMPORARILY_ONLINE, CREATE_BUILD, CREATE_JENKINS_COHORT,
		CREATE_JOB, QUEUE_ITEM_ENTER_BLOCKED, QUEUE_ITEM_ENTER_BUILDABLE,
		QUEUE_ITEM_ENTER_WAITING, QUEUE_ITEM_LEAVE_BLOCKED,
		QUEUE_ITEM_LEAVE_BUILDABLE, QUEUE_ITEM_LEAVE_WAITING, QUEUE_ITEM_LEFT,
		QUEUE_JOB

	}

}