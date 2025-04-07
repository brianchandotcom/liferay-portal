/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.customer.constants.ExternalLinkConstants;
import com.liferay.customer.permission.BusinessEventPermission;
import com.liferay.customer.service.KoroneikiService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskTicket;
import com.liferay.osb.spring.boot.client.zendesk.search.SearchHits;
import com.liferay.osb.spring.boot.client.zendesk.search.ZendeskTicketQuery;
import com.liferay.osb.spring.boot.client.zendesk.service.ZendeskService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jenny Chen
 */
@RestController
public class AccountBusinessEventsRestController extends BaseRestController {

	@RequestMapping(
		method = RequestMethod.POST,
		path = "/accounts/{externalReferenceCode}/business-events"
	)
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestBody String json)
		throws Exception {

		try {
			_businessEventPermission.check(
				jwt, externalReferenceCode, ActionKeys.UPDATE);

			JSONObject jsonObject = new JSONObject(json);

			JSONArray jsonArray = jsonObject.getJSONArray("businessEvents");

			_updateZendesk(
				_fetchZendeskOrganizationId(externalReferenceCode),
				_getBusinessEvents(jsonArray),
				_getAssociatedTicketIds(jsonArray));

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to update Zendesk business events for " +
					externalReferenceCode,
				exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private long _fetchZendeskOrganizationId(String externalReferenceCode)
		throws Exception {

		List<ExternalLink> externalLinks = _koroneikiService.fetchExternalLinks(
			externalReferenceCode, 1, 1000);

		for (ExternalLink externalLink : externalLinks) {
			String domain = externalLink.getDomain();
			String entityName = externalLink.getEntityName();

			if (domain.equals(ExternalLinkConstants.DOMAIN_ZENDESK) &&
				entityName.equals(
					ExternalLinkConstants.ENTITY_NAME_ZENDESK_ORGANIZATION)) {

				return GetterUtil.getLong(externalLink.getEntityId());
			}
		}

		return 0;
	}

	private Long[] _getAssociatedTicketIds(JSONArray jsonArray) {
		Set<Long> associatedTicketIds = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray associatedTicketIdsJSONArray = jsonObject.getJSONArray(
				"associatedTicketIds");

			for (int j = 0; j < associatedTicketIdsJSONArray.length(); j++) {
				associatedTicketIds.add(
					associatedTicketIdsJSONArray.getLong(j));
			}
		}

		return associatedTicketIds.toArray(new Long[0]);
	}

	private String _getBusinessEvents(JSONArray jsonArray) {
		List<String> businessEvents = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			List<String> businessEventFieldValues = new ArrayList<>();

			Iterator<String> iterator = jsonObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

				if (key.equals("associatedTicketIds")) {
					continue;
				}

				if (Validator.isNotNull(jsonObject.optString(key))) {
					businessEventFieldValues.add(
						key + ": " + jsonObject.getString(key));
				}
			}

			if (!businessEventFieldValues.isEmpty()) {
				businessEvents.add(
					StringUtil.merge(businessEventFieldValues, ",\n"));
			}
		}

		return StringUtil.merge(businessEvents, "\n\n");
	}

	private void _updateZendesk(
			long zendeskOrganizationId, String businessEvents,
			Long[] associatedTicketIds)
		throws Exception {

		_zendeskService.updateZendeskOrganization(
			zendeskOrganizationId, businessEvents);

		ZendeskTicketQuery zendeskTicketQuery = new ZendeskTicketQuery();

		zendeskTicketQuery.addCriterion(
			"organization:" + zendeskOrganizationId);
		zendeskTicketQuery.addCriterion("status<closed");

		int page = 1;

		while (page > 0) {
			zendeskTicketQuery.setPage(page);

			SearchHits<ZendeskTicket> searchHits = _zendeskService.search(
				zendeskTicketQuery);

			for (ZendeskTicket zendeskTicket : searchHits.getResults()) {
				Map<Long, String> customFields =
					zendeskTicket.getCustomFields();

				customFields.put(
					_zendeskBusinessEventTicketFieldId, businessEvents);

				Set<String> tags = zendeskTicket.getTags();

				tags.remove("impacting_business_event");

				if (ArrayUtil.contains(
						associatedTicketIds,
						zendeskTicket.getZendeskTicketId())) {

					tags.add("impacting_business_event");
				}

				_zendeskService.updateZendeskTicket(
					zendeskTicket.getZendeskTicketId(), zendeskOrganizationId,
					zendeskTicket.getRequesterId(), zendeskTicket.getStatus(),
					customFields, tags);
			}

			page = searchHits.getNextPage();
		}
	}

	private static final Log _log = LogFactory.getLog(
		AccountBusinessEventsRestController.class);

	@Autowired
	private BusinessEventPermission _businessEventPermission;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Value("${liferay.customer.zendesk.business.event.ticket.field.id}")
	private long _zendeskBusinessEventTicketFieldId;

	@Autowired
	private ZendeskService _zendeskService;

}