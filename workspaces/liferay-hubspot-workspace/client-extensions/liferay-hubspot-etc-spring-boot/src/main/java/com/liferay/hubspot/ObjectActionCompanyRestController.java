/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.hubspot.service.HubSpotService;
import com.liferay.hubspot.service.LiferayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keven Leone
 * @author Ricardo Mariz
 */
@RestController
public class ObjectActionCompanyRestController extends BaseRestController {

	@PostMapping("/object/action/company")
	public void post(@RequestBody String json) throws Exception {
		_liferayService.patchObjectEntry(
			_hubSpotService.postCompany(
				_liferayService.getObjectEntryValuesJSONObject(json)),
			"o/c/h1s4companies/" + _liferayService.getClassPK(json));
	}

	@Autowired
	private HubSpotService _hubSpotService;

	@Autowired
	private LiferayService _liferayService;

}