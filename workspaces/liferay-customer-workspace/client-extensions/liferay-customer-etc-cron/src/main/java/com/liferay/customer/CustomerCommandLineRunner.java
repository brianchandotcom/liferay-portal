/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.osb.spring.boot.client.zendesk.model.ZendeskTicket;
import com.liferay.osb.spring.boot.client.zendesk.search.SearchHits;
import com.liferay.osb.spring.boot.client.zendesk.search.ZendeskTicketQuery;
import com.liferay.osb.spring.boot.client.zendesk.service.ZendeskWebService;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Amos Fong
 */
@Component
@ComponentScan(basePackages = "com.liferay.osb")
public class CustomerCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Cleaning up Zendesk ticket large file attachments");
		}

		ZendeskTicketQuery zendeskTicketQuery = new ZendeskTicketQuery();

		zendeskTicketQuery.addCriterion("status:closed");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = new Date(
			System.currentTimeMillis() -
				((_zendeskTicketClosedDays + 7) * 24 * 60 * 60 * 1000));

		zendeskTicketQuery.addCriterion(
			"updated>" + simpleDateFormat.format(startDate));

		Date endDate = new Date(
			System.currentTimeMillis() -
				(_zendeskTicketClosedDays * 24 * 60 * 60 * 1000));

		zendeskTicketQuery.addCriterion(
			"updated<" + simpleDateFormat.format(endDate));

		int page = 1;

		while (page > 0) {
			zendeskTicketQuery.setPage(page);

			SearchHits<ZendeskTicket> searchHits = _zendeskWebService.search(
				zendeskTicketQuery);

			for (ZendeskTicket zendeskTicket : searchHits.getResults()) {
				_deleteTicketAttachments(zendeskTicket.getZendeskTicketId());
			}

			page = searchHits.getNextPage();
		}
	}

	private void _deleteTicketAttachments(long zendeskTicketId)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).get(
			).uri(
				"/o/c/ticketattachments?filter=zendeskTicketId eq " +
					zendeskTicketId
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject ticketAttachmentJSONObject = jsonArray.getJSONObject(i);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Deleting ticket attachment " +
						ticketAttachmentJSONObject.getString("id"));
			}

			WebClient.create(
				_etcSpringBootClientExtensionURL
			).delete(
			).uri(
				"/ticket-attachments/" + ticketAttachmentJSONObject.getInt("id")
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).retrieve(
			).bodyToMono(
				String.class
			).block();
		}
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oauth-application-headless-" +
				"server");
	}

	private static final Log _log = LogFactory.getLog(
		CustomerCommandLineRunner.class);

	@Value("${liferay.customer.etc.spring.boot.client.extension.url}")
	private String _etcSpringBootClientExtensionURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${liferay.customer.zendesk.ticket.closed.days}")
	private int _zendeskTicketClosedDays;

	@Autowired
	private ZendeskWebService _zendeskWebService;

}