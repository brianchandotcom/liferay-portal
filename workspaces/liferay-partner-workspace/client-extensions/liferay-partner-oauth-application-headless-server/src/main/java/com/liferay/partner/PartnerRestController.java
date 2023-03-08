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
import com.liferay.partner.services.SalesforceService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("type", "Testing 4444");

		try {
			_objectDefinitionService.getSalesforceObjectDefinitionsPage();
			_salesforceService.getBulkObjects();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		PartnerRestController.class);

	@Autowired
	private ObjectDefinitionService _objectDefinitionService;

	@Autowired
	private SalesforceService _salesforceService;

}