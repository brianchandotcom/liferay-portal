/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.customer.constants.ExternalLinkConstants;
import com.liferay.customer.service.KoroneikiService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskOrganization;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskTicket;
import com.liferay.osb.spring.boot.client.zendesk.search.SearchHits;
import com.liferay.osb.spring.boot.client.zendesk.search.ZendeskTicketQuery;
import com.liferay.osb.spring.boot.client.zendesk.service.ZendeskService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.PrincipalException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jenny Chen
 */
@RestController
public class BusinessEventRestController extends BaseRestController {

	@RequestMapping(
		method = RequestMethod.GET,
		path = "/accounts/{externalReferenceCode}/tickets/{ticketId}"
	)
	public ResponseEntity<String> getZendeskTicket(
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@PathVariable("ticketId") long ticketId)
		throws Exception {

		try {
			ZendeskOrganization zendeskOrganization = _getZendeskOrganization(
				externalReferenceCode);

			ZendeskTicket zendeskTicket = _zendeskService.getZendeskTicket(
				ticketId);

			if (zendeskOrganization.getZendeskOrganizationId() !=
					zendeskTicket.getZendeskOrganizationId()) {

				throw new PrincipalException();
			}

			JSONObject jsonObject = _transformTicket(zendeskTicket);

			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(
		method = RequestMethod.GET,
		path = "/accounts/{externalReferenceCode}/tickets"
	)
	public ResponseEntity<String> getZendeskTickets(
			@PathVariable("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		try {
			JSONArray jsonArray = _getAccountTicketsJSONArray(
				externalReferenceCode);

			return new ResponseEntity<>(jsonArray.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(
		method = RequestMethod.POST,
		path = "/accounts/{externalReferenceCode}/business-events"
	)
	public ResponseEntity<String> post(
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@RequestBody String json)
		throws Exception {

		try {
			_updateZendesk(externalReferenceCode, new JSONObject(json));

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private List<String> _convertToList(JSONObject jsonObject) {
		List<String> stringList = new ArrayList<>();

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (key.equals("impactedTickets")) {
				continue;
			}

			if (Validator.isNotNull(jsonObject.optString(key))) {
				stringList.add(
					StringBundler.concat(
						key, StringPool.COLON, StringPool.SPACE,
						jsonObject.getString(key)));
			}
		}

		return stringList;
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

	private JSONArray _getAccountTicketsJSONArray(String externalReferenceCode)
		throws Exception {

		ZendeskOrganization zendeskOrganization = _getZendeskOrganization(
			externalReferenceCode);

		ZendeskTicketQuery zendeskTicketQuery = new ZendeskTicketQuery();

		zendeskTicketQuery.addCriterion(
			"organization:" + zendeskOrganization.getZendeskOrganizationId());
		zendeskTicketQuery.addCriterion("status<closed");

		int page = 1;

		JSONArray jsonArray = new JSONArray();

		while (page > 0) {
			zendeskTicketQuery.setPage(page);

			SearchHits<ZendeskTicket> searchHits = _zendeskService.search(
				zendeskTicketQuery);

			for (ZendeskTicket zendeskTicket : searchHits.getResults()) {
				jsonArray.put(_transformTicket(zendeskTicket));
			}

			page = searchHits.getNextPage();
		}

		return jsonArray;
	}

	private Long[] _getImpactedTickets(JSONArray jsonArray) {
		Set<Long> ticketList = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			JSONArray impactedTicketsJSONArray = jsonObject.getJSONArray(
				"impactedTickets");

			for (int j = 0; j < impactedTicketsJSONArray.length(); j++) {
				ticketList.add(impactedTicketsJSONArray.getLong(j));
			}
		}

		return ticketList.toArray(new Long[0]);
	}

	private ZendeskOrganization _getZendeskOrganization(
			String externalReferenceCode)
		throws Exception {

		long zendeskOrganizationId = _fetchZendeskOrganizationId(
			externalReferenceCode);

		if (zendeskOrganizationId <= 0) {
			throw new Exception(
				"Unable to get Koroneiki external link for " +
					externalReferenceCode);
		}

		ZendeskOrganization zendeskOrganization =
			_zendeskService.getZendeskOrganization(zendeskOrganizationId);

		if (zendeskOrganization == null) {
			throw new Exception(
				"Unable to get Zendesk organization for " +
					externalReferenceCode);
		}

		return zendeskOrganization;
	}

	private String _parseBusinessEvent(JSONObject jsonObject) {
		List<String> stringList = _convertToList(jsonObject);

		if (stringList.isEmpty()) {
			return null;
		}

		return StringUtil.merge(
			stringList, StringPool.COMMA + StringPool.NEW_LINE);
	}

	private String _parseBusinessEvents(JSONArray jsonArray) {
		List<String> stringList = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			stringList.add(_parseBusinessEvent(jsonArray.getJSONObject(i)));
		}

		return StringUtil.merge(
			stringList, StringPool.NEW_LINE + StringPool.NEW_LINE);
	}

	private JSONObject _transformTicket(ZendeskTicket zendeskTicket) {
		return new JSONObject(
		).put(
			"link",
			_zendeskURL + "/requests/" + zendeskTicket.getZendeskTicketId()
		).put(
			"status", zendeskTicket.getStatus()
		).put(
			"subject", zendeskTicket.getSubject()
		).put(
			"ticketId", zendeskTicket.getZendeskTicketId()
		);
	}

	private void _updateZendesk(
			String externalReferenceCode, JSONObject jsonObject)
		throws Exception {

		try {
			ZendeskOrganization zendeskOrganization = _getZendeskOrganization(
				externalReferenceCode);

			JSONArray jsonArray = jsonObject.getJSONArray("businessEvents");

			String businessEvents = _parseBusinessEvents(jsonArray);

			_zendeskService.updateZendeskOrganization(
				zendeskOrganization, businessEvents);

			ZendeskTicketQuery zendeskTicketQuery = new ZendeskTicketQuery();

			zendeskTicketQuery.addCriterion(
				"organization:" +
					zendeskOrganization.getZendeskOrganizationId());
			zendeskTicketQuery.addCriterion("status<closed");

			int page = 1;

			Long[] impactedTickets = _getImpactedTickets(jsonArray);

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

					if (ArrayUtil.contains(
							impactedTickets,
							zendeskTicket.getZendeskTicketId())) {

						tags.add("impacting_business_event");
					}

					_zendeskService.updateZendeskTicket(
						zendeskTicket, customFields, tags);
				}

				page = searchHits.getNextPage();
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to update Zendesk business events for " +
						externalReferenceCode,
					exception);
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		BusinessEventRestController.class);

	@Autowired
	private KoroneikiService _koroneikiService;

	@Value("${liferay.customer.zendesk.business.event.ticket.field.id}")
	private long _zendeskBusinessEventTicketFieldId;

	@Autowired
	private ZendeskService _zendeskService;

	@Value("${liferay.osb.spring.boot.client.zendesk.url}")
	private String _zendeskURL;

}