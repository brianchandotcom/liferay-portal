/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.spring.license;

import com.liferay.portal.ee.license.shared.KeyGenerator;
import com.liferay.portal.ee.license.shared.LicenseType;
import com.liferay.portal.ee.license.shared.ProductVersion;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import java.nio.charset.StandardCharsets;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;

/**
 * @author Amos Fong
 */
@Component
public class LicenseKeyExporter {

	public String aggregateXMLs(String[] xmls) throws Exception {
		Document document = _newDocument();

		Element rootElement = document.createElement("licenses");

		document.appendChild(rootElement);

		DocumentBuilder documentBuilder = _newDocumentBuilder();

		for (String xml : xmls) {
			Document curDocument = documentBuilder.parse(
				new InputSource(new StringReader(xml)));

			Node imported = document.importNode(
				curDocument.getDocumentElement(), true);

			rootElement.appendChild(imported);
		}

		return _formattedString(document);
	}

	public String getFileName(
		String productName, String productVersion, String licenseKeyName) {

		StringBuilder sb = new StringBuilder();

		productName = _extractChars(productName);

		sb.append("activation-key-");
		sb.append(productName);
		sb.append('-');
		sb.append(productVersion);
		sb.append('-');
		sb.append(licenseKeyName);

		return _formatFileName(sb.toString());
	}

	public String getFileName(String[] productNames, String[] licenseKeyNames) {
		StringBuilder sb = new StringBuilder();

		sb.append("activation-key");

		for (String productName : productNames) {
			sb.append('-');
			sb.append(_extractChars(productName));
		}

		for (String licenseKeyName : licenseKeyNames) {
			sb.append('-');
			sb.append(licenseKeyName);
		}

		return _formatFileName(sb.toString());
	}

	public String toEncodedLicenseFile(String serverId, String key) {
		Properties licenseProperties = new Properties();

		licenseProperties.setProperty("serverId", serverId);
		licenseProperties.setProperty("licenseKey", key);

		String licenseFileDecoded = _propertiesToString(licenseProperties);

		try {
			return _objectToString(licenseFileDecoded);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
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

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				Base64.getEncoder(
				).wrap(
					byteArrayOutputStream
				))) {

			objectOutputStream.writeInt(4);
			objectOutputStream.writeUTF(_nullToEmpty(accountName));
			objectOutputStream.writeUTF(_nullToEmpty(description));
			objectOutputStream.writeObject(_split(domains));
			objectOutputStream.writeObject(expirationDate);

			String[] hostNames;

			if (_isNotNull(hostName)) {
				hostNames = new String[] {hostName};
			}
			else {
				hostNames = new String[0];
			}

			objectOutputStream.writeObject(hostNames);

			objectOutputStream.writeObject(_split(ipAddresses));
			objectOutputStream.writeUTF(_nullToEmpty(key));
			objectOutputStream.writeLong(System.currentTimeMillis());
			objectOutputStream.writeUTF(_nullToEmpty(licenseEntryName));
			objectOutputStream.writeUTF(_nullToEmpty(licenseType));
			objectOutputStream.writeUTF(String.valueOf(licenseVersion));
			objectOutputStream.writeObject(_split(macAddresses));

			if (Objects.equals(LicenseType.VIRTUAL_CLUSTER, licenseType)) {
				objectOutputStream.writeInt(maxClusterNodes);
			}

			objectOutputStream.writeInt(maxHttpSessions);
			objectOutputStream.writeInt(maxServers);
			objectOutputStream.writeLong(maxConcurrentUsers);
			objectOutputStream.writeLong(maxUsers);
			objectOutputStream.writeUTF(sizing);
			objectOutputStream.writeUTF(_nullToEmpty(owner));
			objectOutputStream.writeUTF(_nullToEmpty(productName));
			objectOutputStream.writeUTF(_nullToEmpty(productId));
			objectOutputStream.writeUTF(productVersion);

			String[] serverIds;

			if (_isNotNull(serverId)) {
				serverIds = new String[] {serverId};
			}
			else {
				serverIds = new String[0];
			}

			objectOutputStream.writeObject(serverIds);

			objectOutputStream.writeObject(startDate);

			objectOutputStream.flush();
		}

		return new String(
			byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
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
			licenseType.equals(LicenseType.PRODUCTION)) {

			properties.put("maxServers", String.valueOf(serverIds.length));
		}

		Document document = _toXMLVersion3_4(properties, "", true);

		Element rootElement = document.getDocumentElement();

		List<String> allHostNames = new ArrayList<>();
		List<String> allIpAddresses = new ArrayList<>();
		List<String> allMacAddresses = new ArrayList<>();

		Element serversElement = document.createElement("servers");

		rootElement.appendChild(serversElement);

		for (int i = 0; i < serverIds.length; i++) {
			Map<String, String> curProperties = _getProperties(
				accountName, licenseEntryName, licenseType, licenseVersion,
				productName, productId, productVersion, owner, maxClusterNodes,
				maxServers, maxHttpSessions, maxConcurrentUsers, maxUsers,
				sizing, description, domains, hostNames[i], ipAddresses[i],
				macAddresses[i], serverIds[i], startDate, expirationDate,
				createDate);

			Element serverElement = document.createElement("server");

			serversElement.appendChild(serverElement);

			_exportServerToXML(serverElement, curProperties);

			String curHostName = curProperties.get("hostNames");

			if (_isNotNull(curHostName)) {
				allHostNames.add(curHostName);
			}

			Collections.addAll(
				allIpAddresses, _split(curProperties.get("ipAddresses")));

			Collections.addAll(
				allMacAddresses, _split(curProperties.get("macAddresses")));
		}

		properties.put("hostNames", String.join(",", allHostNames));
		properties.put("ipAddresses", String.join(",", allIpAddresses));
		properties.put("macAddresses", String.join(",", allMacAddresses));

		String key = KeyGenerator.generate(properties);

		_addElement(rootElement, "key", key);

		return _formattedString(document);
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

		Document document;

		Map<String, String> properties = _getProperties(
			accountName, licenseEntryName, licenseType, licenseVersion,
			productName, productId, productVersion, owner, maxClusterNodes,
			maxServers, maxHttpSessions, maxConcurrentUsers, maxUsers, sizing,
			description, domains, hostNames, ipAddresses, macAddresses,
			serverIds, startDate, expirationDate, createDate);

		if (licenseVersion >= 3) {
			document = _toXMLVersion3_4(properties, key, false);
		}
		else {
			document = _toXMLVersion2(properties, key);
		}

		return _formattedString(document);
	}

	private void _addElement(Element parent, String name, String value) {
		Element child = parent.getOwnerDocument(
		).createElement(
			name
		);

		if (value != null) {
			child.setTextContent(value);
		}

		parent.appendChild(child);
	}

	private void _exportServerToXML(
		Element element, Map<String, String> properties) {

		Document document = element.getOwnerDocument();

		Element hostNamesElement = document.createElement("host-names");

		element.appendChild(hostNamesElement);

		for (String hostName : _split(properties.get("hostNames"))) {
			_addElement(hostNamesElement, "host-name", hostName);
		}

		Element ipAddressesElement = document.createElement("ip-addresses");

		element.appendChild(ipAddressesElement);

		for (String ipAddress : _split(properties.get("ipAddresses"))) {
			_addElement(ipAddressesElement, "ip-address", ipAddress);
		}

		Element macAddressesElement = document.createElement("mac-addresses");

		element.appendChild(macAddressesElement);

		for (String macAddress : _split(properties.get("macAddresses"))) {
			_addElement(macAddressesElement, "mac-address", macAddress);
		}

		String[] serverIds = _split(properties.get("serverIds"));

		if (serverIds.length > 0) {
			Element serverIdElement = document.createElement("server-ids");

			element.appendChild(serverIdElement);

			for (String serverId : serverIds) {
				_addElement(serverIdElement, "server-id", serverId);
			}
		}
	}

	private String _extractChars(String s) {
		if (s == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder(s.length());

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (Character.isLetter(c)) {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	private String _formatFileName(String fileName) {
		fileName = StringUtil.replace(fileName, ' ', '\0');
		fileName = StringUtil.removeSubstring(fileName, "\0");
		fileName = fileName.toLowerCase(LocaleUtil.ROOT);
		fileName = fileName.substring(0, Math.min(fileName.length(), 251));

		return fileName.concat(".xml");
	}

	private String _formattedString(Document document) throws Exception {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(
			"{http://xml.apache.org/xslt}indent-amount", "4");

		StringWriter stringWriter = new StringWriter();

		transformer.transform(
			new DOMSource(document), new StreamResult(stringWriter));

		return stringWriter.toString();
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
			accountName, licenseEntryName, licenseType, licenseVersion,
			productName, productId, productVersion, owner, maxClusterNodes,
			maxServers, maxHttpSessions, maxConcurrentUsers, maxUsers, sizing,
			description, domains, hostNames, ipAddresses, macAddresses,
			new String[] {serverIds}, startDate, expirationDate);

		// See LRDCOM-2568

		if (productVersion.equals(ProductVersion.PORTAL_VERSION_6_1_GA1) ||
			productVersion.equals("6.1 GA 1")) {

			Calendar calendar = Calendar.getInstance();

			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.MONTH, 6);
			calendar.set(Calendar.YEAR, 2012);

			if (createDate.before(calendar.getTime())) {
				properties.put("productVersion", "6.1");
			}
		}

		return properties;
	}

	private boolean _isNotNull(String s) {
		if ((s != null) && !s.isEmpty() &&
			!StringUtil.equalsIgnoreCase(s, "null")) {

			return true;
		}

		return false;
	}

	private boolean _isNull(String s) {
		return !_isNotNull(s);
	}

	private Document _newDocument() throws Exception {
		return _newDocumentBuilder().newDocument();
	}

	private DocumentBuilder _newDocumentBuilder() throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

		documentBuilderFactory.setNamespaceAware(true);

		return documentBuilderFactory.newDocumentBuilder();
	}

	private String _nullToEmpty(String s) {
		if (s == null) {
			return "";
		}

		return s;
	}

	private String _objectToString(Object object) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream)) {

			objectOutputStream.writeObject(object);
		}

		return Base64.getEncoder(
		).encodeToString(
			byteArrayOutputStream.toByteArray()
		);
	}

	private long _parseLong(String value) {
		if ((value == null) || value.isEmpty()) {
			return 0;
		}

		try {
			return Long.parseLong(value.trim());
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isDebugEnabled()) {
				_log.debug(numberFormatException);
			}

			return 0;
		}
	}

	private String _propertiesToString(Properties properties) {
		StringBuilder sb = new StringBuilder();

		for (String name : properties.stringPropertyNames()) {
			String value = properties.getProperty(name);

			if (value != null) {
				sb.append(name);
				sb.append('=');
				sb.append(value);
				sb.append('\n');
			}
		}

		return sb.toString();
	}

	private String[] _split(String s) {
		if ((s == null) || s.isEmpty()) {
			return new String[0];
		}

		return s.split(",");
	}

	private Document _toXMLVersion2(Map<String, String> properties, String key)
		throws Exception {

		Document document = _newDocument();

		Element rootElement = document.createElement("license");

		document.appendChild(rootElement);

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

		Date startDate = new Date(_parseLong(properties.get("startDate")));

		_addElement(
			rootElement, "start-date",
			longDateFormatDateTime.format(startDate));

		Date expirationDate = new Date(
			_parseLong(properties.get("expirationDate")));

		_addElement(
			rootElement, "expiration-date",
			longDateFormatDateTime.format(expirationDate));

		if (licenseEntryType.equals(LicenseType.CLUSTER) ||
			licenseEntryType.equals(LicenseType.DEVELOPER_CLUSTER)) {

			_addElement(
				rootElement, "max-servers", properties.get("maxServers"));
		}

		if (licenseEntryType.equals(LicenseType.DEVELOPER) ||
			licenseEntryType.equals(LicenseType.DEVELOPER_CLUSTER)) {

			_addElement(
				rootElement, "max-http-sessions",
				properties.get("maxHttpSessions"));
		}

		if (licenseEntryType.equals(LicenseType.PRODUCTION)) {
			Element serverIdElement = document.createElement("server-ids");

			rootElement.appendChild(serverIdElement);

			for (String serverId : _split(properties.get("serverIds"))) {
				_addElement(serverIdElement, "server-id", serverId);
			}
		}

		_addElement(rootElement, "key", key);

		return document;
	}

	private Document _toXMLVersion3_4(
			Map<String, String> properties, String key, boolean aggregate)
		throws Exception {

		Document document = _newDocument();

		Element rootElement = document.createElement("license");

		document.appendChild(rootElement);

		String productId = properties.get("productId");
		String licenseEntryType = properties.get("type");
		long licenseVersion = _parseLong(properties.get("version"));

		if (_isNull(productId)) {
			_addElement(
				rootElement, "account-name",
				properties.get("accountEntryName"));
		}

		_addElement(rootElement, "owner", properties.get("owner"));
		_addElement(rootElement, "description", properties.get("description"));
		_addElement(
			rootElement, "product-name", properties.get("productEntryName"));

		if (_isNotNull(productId)) {
			_addElement(rootElement, "product-id", productId);
		}

		_addElement(
			rootElement, "product-version", properties.get("productVersion"));

		if (_isNull(productId)) {
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

		Date startDate = new Date(_parseLong(properties.get("startDate")));

		_addElement(
			rootElement, "start-date",
			longDateFormatDateTime.format(startDate));

		Date expirationDate = new Date(
			_parseLong(properties.get("expirationDate")));

		_addElement(
			rootElement, "expiration-date",
			longDateFormatDateTime.format(expirationDate));

		if (licenseEntryType.equals(LicenseType.FREE) ||
			licenseEntryType.equals(LicenseType.VIRTUAL_CLUSTER)) {

			_addElement(
				rootElement, "max-cluster-nodes",
				properties.get("maxClusterNodes"));
		}

		if (licenseEntryType.equals(LicenseType.CLUSTER) ||
			((licenseVersion >= 4) &&
			 (licenseEntryType.equals(LicenseType.LIMITED) ||
			  licenseEntryType.equals(LicenseType.PRODUCTION)))) {

			_addElement(
				rootElement, "max-servers", properties.get("maxServers"));
		}

		if (licenseEntryType.equals(LicenseType.DEVELOPER) ||
			licenseEntryType.equals(LicenseType.DEVELOPER_CLUSTER)) {

			_addElement(
				rootElement, "max-http-sessions",
				properties.get("maxHttpSessions"));
		}

		if (licenseEntryType.equals(LicenseType.FREE)) {
			Element domainsElement = document.createElement("domains");

			rootElement.appendChild(domainsElement);

			for (String domain : _split(properties.get("domains"))) {
				_addElement(domainsElement, "domain", domain);
			}
		}

		if (licenseEntryType.equals(LicenseType.PER_USER)) {
			String maxConcurrentUsers = properties.get("maxConcurrentUsers");

			if (_isNotNull(maxConcurrentUsers)) {
				_addElement(
					rootElement, "max-concurrent-users", maxConcurrentUsers);
			}

			String maxUsers = properties.get("maxUsers");

			if (_isNotNull(maxUsers)) {
				_addElement(rootElement, "max-users", maxUsers);
			}
		}

		String instanceSize = properties.get("instanceSize");

		if (_isNotNull(instanceSize)) {
			_addElement(rootElement, "instance-size", instanceSize);
		}

		if (!aggregate) {
			if (licenseEntryType.equals(LicenseType.CLUSTER) ||
				licenseEntryType.equals(LicenseType.LIMITED) ||
				licenseEntryType.equals(LicenseType.PER_USER) ||
				licenseEntryType.equals(LicenseType.PRODUCTION)) {

				_exportServerToXML(rootElement, properties);
			}

			_addElement(rootElement, "key", key);
		}

		return document;
	}

	private static final Log _log = LogFactory.getLog(LicenseKeyExporter.class);

}