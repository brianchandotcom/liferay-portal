/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.util.StringUtil;

import java.util.Objects;

/**
 * @author Michael Hashimoto
 */
public interface JRPEventHandler extends EventHandler {

	public enum EventType {

		CREATE_BUILD, CREATE_BUILD_RUN, CREATE_JENKINS_COHORT, CREATE_JOB,
		QUEUE_JOB;

		public static EventType get(String eventTypeString) {
			if (StringUtil.isNullOrEmpty(eventTypeString)) {
				return null;
			}

			for (EventType eventType : values()) {
				if (Objects.equals(eventTypeString, eventType.toString())) {
					return eventType;
				}
			}

			return null;
		}

	}

}