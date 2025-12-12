/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.customer.constants.HeatTagConstants;
import com.liferay.customer.constants.JiraIssueConstants;
import com.liferay.customer.model.AccountUsage;
import com.liferay.customer.model.JiraSupportIssue;
import com.liferay.customer.permission.BusinessEventPermission;
import com.liferay.customer.service.GoogleCloudFunctionService;
import com.liferay.customer.service.JiraService;
import com.liferay.customer.service.KoroneikiService;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jenny Chen
 * @author Amos Fong
 */
@RequestMapping("/accounts")
@RestController
public class AccountsRestController extends BaseRestController {

	@GetMapping("/{externalReferenceCode}/jira/object-key")
	public ResponseEntity<String> getJiraObjectKey(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			return new ResponseEntity<>(
				_jiraService.getAccountObjectKey(externalReferenceCode),
				HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(
				"Error getting JIRA object key for " + externalReferenceCode,
				exception);

			return new ResponseEntity<>(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{externalReferenceCode}/tickets")
	public ResponseEntity<String> getTickets(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestParam(defaultValue = "", required = false) String[]
				ticketIds)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.VIEW);

			return _getJSMTickets(externalReferenceCode, ticketIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{externalReferenceCode}/usage")
	public ResponseEntity<String> getUsage(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			_checkPermissions(jwt, externalReferenceCode);

			List<ProductPurchase> productPurchases =
				_koroneikiService.searchProductPurchases(
					"accountKey eq '" + externalReferenceCode +
						"' and state eq 'Active'",
					1, 1000, StringPool.BLANK);

			JSONObject jsonObject =
				_googleCloudFunctionService.fetchCustomerAccountUsage(
					externalReferenceCode);

			AccountUsage accountUsage = new AccountUsage(
				productPurchases, jsonObject);

			JSONObject accountUsageJSONObject = accountUsage.toJSONObject();

			return new ResponseEntity<>(
				accountUsageJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{externalReferenceCode}/sync-business-events")
	public ResponseEntity<String> postSyncBusinessEvents(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestBody String json)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.UPDATE);

			JSONObject jsonObject = new JSONObject(json);

			JSONArray businessEventsJSONArray = jsonObject.getJSONArray(
				"businessEvents");

			_updateJSM(
				externalReferenceCode,
				_getBusinessEventsSummary(businessEventsJSONArray),
				_getJSMAssociatedTicketsHeatTags(businessEventsJSONArray));

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to update JSM business events for " +
					externalReferenceCode,
				exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Scheduled(cron = "0 0 0 * * *")
	public void scheduledHeatTagUpdate() throws Exception {
		int page = 1;

		Set<String> syncedAccountExternalReferenceCodes = new HashSet<>();

		while (page > 0) {
			JSONObject jsonObject = _getBusinessEventsJSONObject(
				"eventStatus ne 'canceled' and eventStatus ne 'completed'",
				page, 500, StringPool.BLANK);

			JSONArray jsonArray = jsonObject.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject businessEventJSONObject = jsonArray.getJSONObject(i);

				String externalReferenceCode =
					businessEventJSONObject.getString(
						"accountEntryToBusinessEventERC");

				if (!syncedAccountExternalReferenceCodes.contains(
						externalReferenceCode)) {

					_updateAccountHeatTags(externalReferenceCode);

					syncedAccountExternalReferenceCodes.add(
						externalReferenceCode);
				}
			}

			if (jsonObject.getInt("lastPage") == page) {
				page = 0;
			}
			else {
				page += 1;
			}
		}
	}

	private void _checkPermissions(Jwt jwt, String externalReferenceCode)
		throws Exception {

		AccountResource accountResource = AccountResource.builder(
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).build();

		accountResource.getAccountByExternalReferenceCode(
			externalReferenceCode);
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private JSONObject _getBusinessEventsJSONObject(
			String filterString, int page, int pageSize, String sortString)
		throws Exception {

		return new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/c/businessevents"
				).queryParam(
					"filter", filterString
				).queryParam(
					"page", page
				).queryParam(
					"pageSize", pageSize
				).queryParam(
					"sort", sortString
				).build(
				).toUri()));
	}

	private String _getBusinessEventsSummary(
		JSONArray businessEventsJSONArray) {

		List<String> businessEvents = new ArrayList<>();

		for (int i = 0; i < businessEventsJSONArray.length(); i++) {
			JSONObject businessEventsJSONObject =
				businessEventsJSONArray.getJSONObject(i);

			List<String> businessEventFieldValues = new ArrayList<>();

			Iterator<String> iterator = businessEventsJSONObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

				if (key.equals("associatedTickets")) {
					continue;
				}

				if (key.equals("eventType")) {
					JSONObject typeJSONObject =
						businessEventsJSONObject.optJSONObject(key);

					businessEventFieldValues.add(
						"type: " + typeJSONObject.getString("name"));
				}
				else if (Validator.isNotNull(
							businessEventsJSONObject.optString(key))) {

					businessEventFieldValues.add(
						key + ": " + businessEventsJSONObject.getString(key));
				}
			}

			if (!businessEventFieldValues.isEmpty()) {
				businessEvents.add(
					StringUtil.merge(businessEventFieldValues, ",\n"));
			}
		}

		return StringUtil.merge(businessEvents, "\n\n");
	}

	private Map<String, String> _getJSMAssociatedTicketsHeatTags(
		JSONArray businessEventsJSONArray) {

		Map<String, String> associatedTicketsHeatTags = new HashMap<>();

		for (int i = 0; i < businessEventsJSONArray.length(); i++) {
			JSONObject businessEventsJSONObject =
				businessEventsJSONArray.getJSONObject(i);

			String heatTag = _getJSMHeatTag(businessEventsJSONObject);

			JSONArray associatedTicketIdsJSONArray = new JSONArray(
				businessEventsJSONObject.getString("associatedTickets"));

			for (int j = 0; j < associatedTicketIdsJSONArray.length(); j++) {
				String associatedTicketId =
					associatedTicketIdsJSONArray.getString(j);

				String highestHeatTag = associatedTicketsHeatTags.get(
					associatedTicketId);

				if (Validator.isNull(highestHeatTag) ||
					(HeatTagConstants.getScore(highestHeatTag) <=
						HeatTagConstants.getScore(heatTag))) {

					associatedTicketsHeatTags.put(associatedTicketId, heatTag);
				}
			}
		}

		return associatedTicketsHeatTags;
	}

	private String _getJSMHeatTag(JSONObject businessEventsJSONObject) {
		JSONArray associatedTicketIdsJSONArray = new JSONArray(
			businessEventsJSONObject.getString("associatedTickets"));

		if (associatedTicketIdsJSONArray.length() == 0) {
			return StringPool.BLANK;
		}

		JSONObject eventTypeJSONObject = businessEventsJSONObject.getJSONObject(
			"eventType");
		String targetGoLiveDateTime = businessEventsJSONObject.getString(
			"targetGoLiveDateTime");

		return HeatTagConstants.getHeatTag(
			eventTypeJSONObject.getString("key"),
			ChronoUnit.DAYS.between(
				LocalDate.now(),
				LocalDate.parse(targetGoLiveDateTime.substring(0, 10))));
	}

	private String _getJSMHeatTag(String[] issueLabels) {
		for (String label : issueLabels) {
			if (ArrayUtil.contains(
					HeatTagConstants.SUPPORT_ISSUE_LABELS, label)) {

				return label;
			}
		}

		return StringPool.BLANK;
	}

	private ResponseEntity<String> _getJSMTickets(
			String externalReferenceCode, String[] ticketIds)
		throws Exception {

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
			sb.toString(), new String[] {"key", "labels", "status", "summary"});

		JSONArray jsonArray = new JSONArray();

		for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
			jsonArray.put(_toJSONObject(jiraSupportIssue));
		}

		return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
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

	private void _updateAccountHeatTags(String externalReferenceCode)
		throws Exception {

		int page = 1;

		while (page > 0) {
			JSONObject jsonObject = _getBusinessEventsJSONObject(
				StringBundler.concat(
					"eventStatus ne 'canceled' and eventStatus ne 'completed' ",
					"and r_accountEntryToBusinessEvents_accountEntryERC eq '",
					externalReferenceCode, "'"),
				page, 500, "targetGoLiveDateTime:asc");

			_updateJSMTickets(
				externalReferenceCode,
				_getJSMAssociatedTicketsHeatTags(
					jsonObject.getJSONArray("items")));

			if (jsonObject.getInt("lastPage") == page) {
				page = 0;
			}
			else {
				page += 1;
			}
		}
	}

	private void _updateJSM(
			String koroneikiAccountKey, String businessEvents,
			Map<String, String> associatedTicketsHeatTags)
		throws Exception {

		_jiraService.updateAccountObject(koroneikiAccountKey, businessEvents);

		StringBundler sb = new StringBundler(5);

		sb.append("Organization in aqlFunction('\"External Key\" = \"");
		sb.append(koroneikiAccountKey);
		sb.append("\"') and (status not in ('");
		sb.append(StringUtil.merge(JiraIssueConstants.STATUSES_CLOSED, "','"));
		sb.append("'))");

		List<JiraSupportIssue> jiraSupportIssues = _jiraService.search(
			sb.toString(), new String[] {"key", "labels", "status", "summary"});

		for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
			List<String> addLabels = new ArrayList<>();
			List<String> removeLabels = new ArrayList<>();

			if (associatedTicketsHeatTags.containsKey(
					jiraSupportIssue.getKey())) {

				addLabels.add("impacting_business_event");

				String heatTag = _getJSMHeatTag(jiraSupportIssue.getLabels());

				String highestHeatTag = associatedTicketsHeatTags.get(
					jiraSupportIssue.getKey());

				if ((HeatTagConstants.getScore(heatTag) <=
						HeatTagConstants.getScore(highestHeatTag)) &&
					!heatTag.equals(highestHeatTag)) {

					addLabels.add(
						highestHeatTag + _JSM_AUTOMATION_HEAT_TAG_SUFFIX);
				}
			}
			else {
				removeLabels.add("impacting_business_event");
			}

			_jiraService.updateIssue(
				jiraSupportIssue.getKey(), businessEvents,
				addLabels.toArray(new String[0]),
				removeLabels.toArray(new String[0]));
		}
	}

	private void _updateJSMTickets(
			String koroneikiAccountKey,
			Map<String, String> associatedTicketsHeatTags)
		throws Exception {

		StringBundler sb = new StringBundler(9);

		sb.append("Organization in aqlFunction('\"External Key\" = \"");
		sb.append(koroneikiAccountKey);
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

		List<JiraSupportIssue> jiraSupportIssues = _jiraService.search(
			sb.toString(), new String[] {"key", "labels", "status", "summary"});

		for (JiraSupportIssue jiraSupportIssue : jiraSupportIssues) {
			if (!associatedTicketsHeatTags.containsKey(
					jiraSupportIssue.getKey())) {

				continue;
			}

			String heatTag = _getJSMHeatTag(jiraSupportIssue.getLabels());

			String highestHeatTag = associatedTicketsHeatTags.get(
				jiraSupportIssue.getKey());

			if ((HeatTagConstants.getScore(heatTag) > HeatTagConstants.getScore(
					highestHeatTag)) ||
				heatTag.equals(highestHeatTag)) {

				continue;
			}

			_jiraService.updateIssue(
				jiraSupportIssue.getKey(), null,
				new String[] {highestHeatTag + _JSM_AUTOMATION_HEAT_TAG_SUFFIX},
				new String[0]);
		}
	}

	private static final String _JSM_AUTOMATION_HEAT_TAG_SUFFIX = "_be";

	private static final Log _log = LogFactory.getLog(
		AccountsRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Autowired
	private GoogleCloudFunctionService _googleCloudFunctionService;

	@Autowired
	private JiraService _jiraService;

	@Value("${liferay.customer.jira.support.hc.field.request.type}")
	private String _jiraSupportHCFieldRequestType;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}