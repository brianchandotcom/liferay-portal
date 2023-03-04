/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.partner;

import com.liferay.partner.services.ObjectDefinitionService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jair Medeiros
 * @author Thaynam Lázaro
 * @author Raymond Augé
 */
@RestController
public class PartnerRestController {

	@GetMapping("/")
	public ResponseEntity<String> trigger() {
		if (_log.isInfoEnabled()) {
			_log.info("Hello World");
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "Testing 4444");

		_objectDefinitionService.getSalesforceObjectDefinitions(
		).subscribe(
			objectDefinitions -> {
				if (_log.isInfoEnabled()) {
					_log.info("Sucess: " + objectDefinitions);
				}
			}
		);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		PartnerRestController.class);

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

	@Autowired
	private ObjectDefinitionService _objectDefinitionService;

}