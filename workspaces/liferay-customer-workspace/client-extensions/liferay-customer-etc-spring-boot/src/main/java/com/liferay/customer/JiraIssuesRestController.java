/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.customer.service.JiraWebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jenny Chen
 */
@RestController
public class JiraIssuesRestController extends BaseRestController {

	@RequestMapping(method = RequestMethod.GET, path = "/jira/issue/{issueKey}")
	public ResponseEntity<String> get(@PathVariable("issueKey") String issueKey)
		throws Exception {

		try {
			return new ResponseEntity<>(
				_jiraWebService.getJiraIssue(issueKey), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(
		method = RequestMethod.GET,
		path = {"/jira/search/customer", "/jira/search/customer/{keywords}"}
	)
	public ResponseEntity<String> searchCustomer(
			@PathVariable(required = false, value = "keywords") String keywords)
		throws Exception {

		try {
			return new ResponseEntity<>(
				_jiraWebService.getJiraSearch(keywords, "customer"),
				HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(
		method = RequestMethod.GET,
		path = {"/jira/search/partner", "/jira/search/partner/{keywords}"}
	)
	public ResponseEntity<String> searchPartner(
			@PathVariable(required = false, value = "keywords") String keywords)
		throws Exception {

		try {
			return new ResponseEntity<>(
				_jiraWebService.getJiraSearch(keywords, "partner"),
				HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private static final Log _log = LogFactory.getLog(
		JiraIssuesRestController.class);

	@Autowired
	private JiraWebService _jiraWebService;

}