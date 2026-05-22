/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.license;

import com.liferay.one.constants.ProductVersion;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.ee.license.shared.KeyGenerator;
import com.liferay.portal.ee.license.shared.LicenseConstants;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class LicenseKeyExporter {

	public String aggregateXMLs(String[] xmls) throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("licenses");

		for (String xml : xmls) {
			Document curDocument = SAXReaderUtil.read(xml);

			rootElement.add(curDocument.getRootElement());
		}

		return document.formattedString();
	}

	public String getFileName(
		String productName, String productVersion, String licenseKeyName) {

		StringBundler sb = new StringBundler(6);

		productName = StringUtil.extractChars(productName);

		sb.append("activation-key-");
		sb.append(productName);
		sb.append(StringPool.DASH);
		sb.append(productVersion);
		sb.append(StringPool.DASH);
		sb.append(licenseKeyName);

		return formatFileName(sb.toString());
	}

	public String getFileName(String[] productNames, String[] licenseKeyNames) {
		StringBundler sb = new StringBundler(
			1 + (2 * productNames.length) + (2 * licenseKeyNames.length));

		sb.append("activation-key");

		for (String productName : productNames) {
			sb.append(StringPool.DASH);
			sb.append(StringUtil.extractChars(productName));
		}

		for (String licenseKeyName : licenseKeyNames) {
			sb.append(StringPool.DASH);
			sb.append(licenseKeyName);
		}

		return formatFileName(sb.toString());
	}

	public String toEncodedLicenseFile(String serverId, String key) {
		Properties licenseProperties = new Properties();

		licenseProperties.setProperty("serverId", serverId);
		licenseProperties.setProperty("licenseKey", key);

		String licenseFileDecoded = PropertiesUtil.toString(licenseProperties);

		return Base64.objectToString(licenseFileDecoded);
	}

	public String toLI(
			String key, String accountName, String licenseEntryName,
			String licenseType, int licenseVersion, String productName,
			String productId, String productVersion, String owner,
			int maxClusterNodes, int maxServers, int maxHttpSessions,
			long maxConcurrentUsers, long maxUsers, String sizing,
			String description, String domains, String hostName,
			String ipAddresses, String macAddresses, String serverId,
			Date startDate, Date expirationDate)
		throws IOException {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			objectOutputStream.writeInt(4);
			objectOutputStream.writeUTF(GetterUtil.getString(accountName));
			objectOutputStream.writeUTF(GetterUtil.getString(description));
			objectOutputStream.writeObject(StringUtil.split(domains));
			objectOutputStream.writeObject(expirationDate);

			String[] hostNames = null;

			if (Validator.isNotNull(hostName)) {
				hostNames = new String[] {hostName};
			}
			else {
				hostNames = new String[0];
			}

			objectOutputStream.writeObject(hostNames);

			objectOutputStream.writeObject(StringUtil.split(ipAddresses));
			objectOutputStream.writeUTF(GetterUtil.getString(key));
			objectOutputStream.writeLong(System.currentTimeMillis());
			objectOutputStream.writeUTF(GetterUtil.getString(licenseEntryName));
			objectOutputStream.writeUTF(GetterUtil.getString(licenseType));
			objectOutputStream.writeUTF(String.valueOf(licenseVersion));
			objectOutputStream.writeObject(StringUtil.split(macAddresses));

			if (Objects.equals(
					LicenseConstants.TYPE_VIRTUAL_CLUSTER, licenseType)) {

				objectOutputStream.writeInt(maxClusterNodes);
			}

			objectOutputStream.writeInt(maxHttpSessions);
			objectOutputStream.writeInt(maxServers);
			objectOutputStream.writeLong(maxConcurrentUsers);
			objectOutputStream.writeLong(maxUsers);
			objectOutputStream.writeUTF(sizing);
			objectOutputStream.writeUTF(GetterUtil.getString(owner));
			objectOutputStream.writeUTF(GetterUtil.getString(productName));
			objectOutputStream.writeUTF(GetterUtil.getString(productId));
			objectOutputStream.writeUTF(productVersion);

			String[] serverIds = null;

			if (Validator.isNotNull(serverId)) {
				serverIds = new String[] {serverId};
			}
			else {
				serverIds = new String[0];
			}

			objectOutputStream.writeObject(serverIds);

			objectOutputStream.writeObject(startDate);

			objectOutputStream.flush();

			return Base64.encode(unsyncByteArrayOutputStream.toByteArray());
		}
	}

	public String toXML(
			String accountName, String licenseEntryName, String licenseType,
			int licenseVersion, String productName, String productId,
			String productVersion, String owner, int maxClusterNodes,
			int maxServers, int maxHttpSessions, long maxConcurrentUsers,
			long maxUsers, String sizing, String description, String domains,
			String[] hostNames, String[] ipAddresses, String[] macAddresses,
			String[] serverIds, Date startDate, Date expirationDate,
			Date createDate)
		throws Exception {

		Map<String, String> properties = _getProperties(
			accountName, licenseEntryName, licenseType, licenseVersion,
			productName, productId, productVersion, owner, maxClusterNodes,
			maxServers, maxHttpSessions, maxConcurrentUsers, maxUsers, sizing,
			description, domains, hostNames[0], ipAddresses[0], macAddresses[0],
			serverIds[0], startDate, expirationDate, createDate);

		if ((licenseVersion >= 4) &&
			licenseType.equals(LicenseConstants.TYPE_PRODUCTION)) {

			properties.put("maxServers", String.valueOf(serverIds.length));
		}

		Document document = toXMLVersion3_4(properties, StringPool.BLANK, true);

		Element rootElement = document.getRootElement();

		List<String> allHostNames = new ArrayList<>();
		List<String> allIpAddresses = new ArrayList<>();
		List<String> allMacAddresses = new ArrayList<>();

		Element serversElement = rootElement.addElement("servers");

		for (int i = 0; i < serverIds.length; i++) {
			Map<String, String> curProperties = _getProperties(
				accountName, licenseEntryName, licenseType, licenseVersion,
				productName, productId, productVersion, owner, maxClusterNodes,
				maxServers, maxHttpSessions, maxConcurrentUsers, maxUsers,
				sizing, description, domains, hostNames[i], ipAddresses[i],
				macAddresses[i], serverIds[i], startDate, expirationDate,
				createDate);

			Element serverElement = serversElement.addElement("server");

			exportServerToXML(serverElement, curProperties);

			String curHostName = curProperties.get("hostNames");

			if (Validator.isNotNull(curHostName)) {
				allHostNames.add(curHostName);
			}

			List<String> curIpAddresses = ListUtil.fromArray(
				StringUtil.split(curProperties.get("ipAddresses")));

			allIpAddresses.addAll(curIpAddresses);

			List<String> curMacAddresses = ListUtil.fromArray(
				StringUtil.split(curProperties.get("macAddresses")));

			allMacAddresses.addAll(curMacAddresses);
		}

		properties.put("hostNames", StringUtil.merge(allHostNames));
		properties.put("ipAddresses", StringUtil.merge(allIpAddresses));
		properties.put("macAddresses", StringUtil.merge(allMacAddresses));

		String key = KeyGenerator.encrypt(properties);

		_addElement(rootElement, "key", key);

		return document.formattedString();
	}

	public String toXML(
			String key, String accountName, String licenseEntryName,
			String licenseType, int licenseVersion, String productName,
			String productId, String productVersion, String owner,
			int maxClusterNodes, int maxServers, int maxHttpSessions,
			long maxConcurrentUsers, long maxUsers, String sizing,
			String description, String domains, String hostNames,
			String ipAddresses, String macAddresses, String serverIds,
			Date startDate, Date expirationDate, Date createDate)
		throws Exception {

		Document document = null;

		Map<String, String> properties = _getProperties(
			accountName, licenseEntryName, licenseType, licenseVersion,
			productName, productId, productVersion, owner, maxClusterNodes,
			maxServers, maxHttpSessions, maxConcurrentUsers, maxUsers, sizing,
			description, domains, hostNames, ipAddresses, macAddresses,
			serverIds, startDate, expirationDate, createDate);

		if (licenseVersion >= 3) {
			document = toXMLVersion3_4(properties, key, false);
		}
		else {
			document = toXMLVersion2(properties, key);
		}

		return document.formattedString();
	}

	protected void exportServerToXML(
		Element element, Map<String, String> properties) {

		Element hostNamesElement = element.addElement("host-names");

		String[] hostNames = StringUtil.split(properties.get("hostNames"));

		for (String hostName : hostNames) {
			_addElement(hostNamesElement, "host-name", hostName);
		}

		Element ipAddressesElement = element.addElement("ip-addresses");

		String[] ipAddresses = StringUtil.split(properties.get("ipAddresses"));

		for (String ipAddress : ipAddresses) {
			_addElement(ipAddressesElement, "ip-address", ipAddress);
		}

		Element macAddressesElement = element.addElement("mac-addresses");

		String[] macAddresses = StringUtil.split(
			properties.get("macAddresses"));

		for (String macAddress : macAddresses) {
			_addElement(macAddressesElement, "mac-address", macAddress);
		}

		String[] serverIds = StringUtil.split(properties.get("serverIds"));

		if (serverIds.length > 0) {
			Element serverIdElement = element.addElement("server-ids");

			for (String serverId : serverIds) {
				_addElement(serverIdElement, "server-id", serverId);
			}
		}
	}

	protected String formatFileName(String fileName) {
		fileName = StringUtil.replace(
			fileName, CharPool.SPACE, StringPool.BLANK);
		fileName = StringUtil.toLowerCase(fileName);
		fileName = fileName.substring(0, Math.min(fileName.length(), 251));

		return fileName.concat(".xml");
	}

	protected Document toXMLVersion2(Map<String, String> properties, String key)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("license");

		String licenseEntryType = properties.get("type");
		String licenseVersion = properties.get("version");

		_addElement(
			rootElement, "account-name", properties.get("accountEntryName"));
		_addElement(rootElement, "owner", properties.get("owner"));
		_addElement(rootElement, "description", properties.get("description"));
		_addElement(
			rootElement, "product-name", properties.get("productEntryName"));
		_addElement(
			rootElement, "product-version", properties.get("productVersion"));
		_addElement(
			rootElement, "license-name", properties.get("licenseEntryName"));
		_addElement(rootElement, "license-type", licenseEntryType);
		_addElement(rootElement, "license-version", licenseVersion);

		DateFormat longDateFormatDateTime = DateFormat.getDateTimeInstance(
			DateFormat.FULL, DateFormat.FULL, LocaleUtil.US);

		longDateFormatDateTime.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date startDate = new Date(
			GetterUtil.getLong(properties.get("startDate")));

		_addElement(
			rootElement, "start-date",
			longDateFormatDateTime.format(startDate));

		Date expirationDate = new Date(
			GetterUtil.getLong(properties.get("expirationDate")));

		_addElement(
			rootElement, "expiration-date",
			longDateFormatDateTime.format(expirationDate));

		if (licenseEntryType.equals(LicenseConstants.TYPE_CLUSTER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			_addElement(
				rootElement, "max-servers", properties.get("maxServers"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			_addElement(
				rootElement, "max-http-sessions",
				properties.get("maxHttpSessions"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)) {
			Element serverIdElement = rootElement.addElement("server-ids");

			String[] serverIds = StringUtil.split(properties.get("serverIds"));

			for (String serverId : serverIds) {
				_addElement(serverIdElement, "server-id", serverId);
			}
		}

		_addElement(rootElement, "key", key);

		return document;
	}

	protected Document toXMLVersion3_4(
			Map<String, String> properties, String key, boolean aggregate)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("license");

		String productId = properties.get("productId");
		String licenseEntryType = properties.get("type");
		long licenseVersion = GetterUtil.getLong(properties.get("version"));

		if (Validator.isNull(productId)) {
			_addElement(
				rootElement, "account-name",
				properties.get("accountEntryName"));
		}

		_addElement(rootElement, "owner", properties.get("owner"));
		_addElement(rootElement, "description", properties.get("description"));
		_addElement(
			rootElement, "product-name", properties.get("productEntryName"));

		if (Validator.isNotNull(productId)) {
			_addElement(rootElement, "product-id", productId);
		}

		_addElement(
			rootElement, "product-version", properties.get("productVersion"));

		if (Validator.isNull(productId)) {
			_addElement(
				rootElement, "license-name",
				properties.get("licenseEntryName"));
		}

		_addElement(rootElement, "license-type", licenseEntryType);
		_addElement(
			rootElement, "license-version", String.valueOf(licenseVersion));

		DateFormat longDateFormatDateTime = DateFormat.getDateTimeInstance(
			DateFormat.FULL, DateFormat.FULL, LocaleUtil.US);

		longDateFormatDateTime.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date startDate = new Date(
			GetterUtil.getLong(properties.get("startDate")));

		_addElement(
			rootElement, "start-date",
			longDateFormatDateTime.format(startDate));

		Date expirationDate = new Date(
			GetterUtil.getLong(properties.get("expirationDate")));

		_addElement(
			rootElement, "expiration-date",
			longDateFormatDateTime.format(expirationDate));

		if (licenseEntryType.equals(LicenseConstants.TYPE_FREE) ||
			licenseEntryType.equals(LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {

			_addElement(
				rootElement, "max-cluster-nodes",
				properties.get("max-cluster-nodes"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_CLUSTER) ||
			((licenseVersion >= 4) &&
			 (licenseEntryType.equals(LicenseConstants.TYPE_LIMITED) ||
			  licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)))) {

			_addElement(
				rootElement, "max-servers", properties.get("maxServers"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			_addElement(
				rootElement, "max-http-sessions",
				properties.get("maxHttpSessions"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_FREE)) {
			Element domainsElement = rootElement.addElement("domains");

			String[] domains = StringUtil.split(properties.get("domains"));

			for (String domain : domains) {
				_addElement(domainsElement, "domain", domain);
			}
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_PER_USER)) {
			String maxConcurrentUsers = properties.get("maxConcurrentUsers");

			if (Validator.isNotNull(maxConcurrentUsers)) {
				_addElement(
					rootElement, "max-concurrent-users",
					properties.get("maxConcurrentUsers"));
			}

			String maxUsers = properties.get("maxUsers");

			if (Validator.isNotNull(maxUsers)) {
				_addElement(
					rootElement, "max-users", properties.get("maxUsers"));
			}
		}

		String instanceSize = properties.get("instanceSize");

		if (Validator.isNotNull(instanceSize)) {
			_addElement(rootElement, "instance-size", instanceSize);
		}

		if (!aggregate) {
			if (licenseEntryType.equals(LicenseConstants.TYPE_CLUSTER) ||
				licenseEntryType.equals(LicenseConstants.TYPE_LIMITED) ||
				licenseEntryType.equals(LicenseConstants.TYPE_PER_USER) ||
				licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)) {

				exportServerToXML(rootElement, properties);
			}

			_addElement(rootElement, "key", key);
		}

		return document;
	}

	private void _addElement(Element parentElement, String name, String value) {
		Element childElement = parentElement.addElement(name);

		if (value != null) {
			childElement.addText(value);
		}
	}

	private Map<String, String> _getProperties(
		String accountName, String licenseEntryName, String licenseType,
		int licenseVersion, String productName, String productId,
		String productVersion, String owner, int maxClusterNodes,
		int maxServers, int maxHttpSessions, long maxConcurrentUsers,
		long maxUsers, String sizing, String description, String domains,
		String hostNames, String ipAddresses, String macAddresses,
		String serverIds, Date startDate, Date expirationDate,
		Date createDate) {

		Map<String, String> properties = KeyGenerator.getProperties(
			accountName, description, StringUtil.split(domains), expirationDate,
			StringUtil.split(hostNames), sizing, StringUtil.split(ipAddresses),
			licenseEntryName, licenseType, String.valueOf(licenseVersion),
			StringUtil.split(macAddresses), maxClusterNodes, maxConcurrentUsers,
			maxHttpSessions, maxServers, maxUsers, owner, productName,
			productId, productVersion, new String[] {serverIds}, startDate);

		// See LRDCOM-2568

		if (productVersion.equals(ProductVersion.PORTAL_VERSION_6_1_10) ||
			productVersion.equals("6.1 GA 1")) {

			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.DAY_OF_MONTH, 31);
			cal.set(Calendar.MONTH, 6);
			cal.set(Calendar.YEAR, 2012);

			if (createDate.before(cal.getTime())) {
				properties.put("productVersion", "6.1");
			}
		}

		return properties;
	}

}