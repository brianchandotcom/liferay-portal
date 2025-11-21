/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.customer.constants.JiraIssueConstants;
import com.liferay.customer.model.JiraSupportIssue;
import com.liferay.customer.permission.BusinessEventPermission;
import com.liferay.customer.service.JiraService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jenny Chen
 */
@RestController
public class AccountsTicketsRestController extends BaseRestController {

	@RequestMapping(
		method = RequestMethod.GET,
		path = "/accounts/{externalReferenceCode}/tickets"
	)
	public ResponseEntity<String> getTickets(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestParam(defaultValue = "", required = false) String[]
				ticketIds)
		throws Exception {

		return _getJSMTickets(jwt, externalReferenceCode, ticketIds);
	}

	private ResponseEntity<String> _getJSMTickets(
			Jwt jwt, String externalReferenceCode, String[] ticketIds)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			StringBundler sb = new StringBundler(12);

			sb.append("Organization in aqlFunction('\"External Key\" = \"");
			sb.append(externalReferenceCode);
			sb.append("\"') and (status not in ('");
			sb.append(
				StringUtil.merge(
					JiraIssueConstants.STATUSES_SOLVED_AND_CLOSED, "','"));
			sb.append("')) and ");
			sb.append(
				JiraIssueConstants.toJQLCustomField(
					_jiraSupportHCFieldRequestType));
			sb.append(" = '");
			sb.append(JiraIssueConstants.TYPE_GENERAL_REQUEST);
			sb.append("'");

			if (ArrayUtil.isNotEmpty(ticketIds)) {
				sb.append(" or key in ('");
				sb.append(StringUtil.merge(ticketIds, "','"));
				sb.append("')");
			}

			List<JiraSupportIssue> jiraSupportIssues = _jiraService.search(
				sb.toString(),
				new String[] {"key", "labels", "status", "summary"});

			JSONArray jsonArray = new JSONArray();

			for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
				jsonArray.put(_toJSONObject(jiraSupportIssue));
			}

			return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private JSONObject _toJSONObject(JiraSupportIssue jiraSupportIssue) {
		return new JSONObject(
		).put(
			"link", jiraSupportIssue.getTicketURL()
		).put(
			"status", jiraSupportIssue.getStatus()
		).put(
			"subject", jiraSupportIssue.getSummary()
		).put(
			"ticketId", jiraSupportIssue.getKey()
		);
	}

	private static final Log _log = LogFactory.getLog(
		AccountsTicketsRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Autowired
	private JiraService _jiraService;

	@Value("${liferay.customer.jira.support.hc.field.request.type}")
	private String _jiraSupportHCFieldRequestType;

}