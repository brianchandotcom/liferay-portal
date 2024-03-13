/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.testray.service.base.TestrayServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Nilton Vieira
 */
@Component(
	property = {
		"json.web.service.context.name=testray",
		"json.web.service.context.path=Testray"
	},
	service = AopService.class
)
public class TestrayServiceImpl extends TestrayServiceBaseImpl {
}