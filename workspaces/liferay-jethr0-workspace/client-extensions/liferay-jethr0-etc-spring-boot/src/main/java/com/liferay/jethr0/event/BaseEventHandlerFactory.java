/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public abstract class BaseEventHandlerFactory implements EventHandlerFactory {

	protected EventHandlerContext getEventHandlerContext() {
		return _eventHandlerContext;
	}

	@Autowired
	private EventHandlerContext _eventHandlerContext;

}