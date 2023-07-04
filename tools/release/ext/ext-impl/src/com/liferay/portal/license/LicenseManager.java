/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.license;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.LicenseInfo;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.license.util.Base64InputStream;
import com.liferay.portal.license.util.Base64OutputStream;
import com.liferay.portal.license.validator.DeveloperValidator;
import com.liferay.portal.license.validator.InstanceSizeValidator;
import com.liferay.portal.license.validator.KeyValidator;
import com.liferay.portal.license.validator.LicenseTypeValidator;
import com.liferay.portal.license.validator.LicenseValidator;
import com.liferay.portal.license.validator.LiferayVersionValidator;
import com.liferay.portal.license.validator.LimitedValidator;
import com.liferay.portal.license.validator.PerUserValidator;
import com.liferay.portal.license.validator.ProductionValidator;
import com.liferay.portal.util.LicenseUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.security.Key;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 * @author Amos Fong
 */
public class LicenseManager {

	public static void checkBinaryLicense(String productId) {
		List<License> licenses = _getBinaryLicenses(productId);

		for (License license : licenses) {
			_checkBinaryLicense(license, false);
		}
	}

	public static void checkBinaryLicenses() {
		Set<License> licenses = _getBinaryLicenses();

		if (licenses.isEmpty()) {
			if (_log.isInfoEnabled()) {
				_log.info("No binary licenses found");
			}

			return;
		}

		for (License license : licenses) {
			_checkBinaryLicense(license, false);
		}
	}

	public static List<Map<String, String>> getClusterLicenseProperties(
		String clusterNodeId) {

		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		ClusterNode clusterNode = null;

		for (ClusterNode curClusterNode : clusterNodes) {
			String curClusterNodeId = curClusterNode.getClusterNodeId();

			if (curClusterNodeId.equals(clusterNodeId)) {
				clusterNode = curClusterNode;

				break;
			}
		}

		if (clusterNode == null) {
			return null;
		}

		try {
			if (clusterNode.equals(ClusterExecutorUtil.getLocalClusterNode())) {
				return getLicenseProperties();
			}

			ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
				_getLicensePropertiesMethodHandler, clusterNodeId);

			FutureClusterResponses futureClusterResponses =
				ClusterExecutorUtil.execute(clusterRequest);

			ClusterNodeResponses clusterNodeResponses =
				futureClusterResponses.get(20000, TimeUnit.MILLISECONDS);

			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponses.getClusterResponse(
					clusterNode.getClusterNodeId());

			return (List<Map<String, String>>)clusterNodeResponse.getResult();
		}
		catch (Exception e) {
			_log.error(e);

			return null;
		}
	}

	public static License getLicense(String productId) {
		AtomicStampedReference<License> licenseStampedReference =
			_licenseStampedReferences.get(productId);

		if (licenseStampedReference == null) {
			return null;
		}

		int[] stampHolder = new int[1];

		return licenseStampedReference.get(stampHolder);
	}

	public static LicenseInfo getLicenseInfo(String productId) {
		License license = getLicense(productId);

		if (license == null) {
			return null;
		}

		return new LicenseInfo(
			license.getOwner(), license.getDescription(),
			license.getProductEntryName(), license.getProductId(),
			license.getProductVersion(), license.getLicenseEntryType(),
			license.getLicenseVersion(), license.getStartDate(),
			license.getExpirationDate(), license.getMaxUsers(),
			license.getHostNames(), license.getIpAddresses(),
			license.getMacAddresses());
	}

	public static List<Map<String, String>> getLicenseProperties() {
		List<Map<String, String>> licenseProperties = new ArrayList<>();

		for (Map.Entry<String, AtomicStampedReference<License>> entry :
				_licenseStampedReferences.entrySet()) {

			String productId = entry.getKey();

			AtomicStampedReference<License> licenseStampedReference =
				entry.getValue();

			int[] stampHolder = new int[1];

			License license = licenseStampedReference.get(stampHolder);

			int licenseState = stampHolder[0];

			if ((license == null) ||
				(licenseState == LicenseConstants.STATE_ABSENT)) {

				continue;
			}

			Map<String, String> curLicenseProperties = license.getProperties();

			String instanceSize = curLicenseProperties.get("instanceSize");

			if (Validator.isNotNull(instanceSize)) {
				if (instanceSize.equals("Sizing 4")) {
					curLicenseProperties.put("maxProcessorCores", "17+");
				}
				else {
					curLicenseProperties.put(
						"maxProcessorCores",
						String.valueOf(license.getMaxProcessorCores()));
				}
			}

			curLicenseProperties.put(
				"licenseState", String.valueOf(licenseState));
			curLicenseProperties.put("productId", productId);

			if (productId.equals(LicenseConstants.PRODUCT_ID_PORTAL)) {
				licenseProperties.add(0, curLicenseProperties);
			}
			else {
				licenseProperties.add(curLicenseProperties);
			}
		}

		return licenseProperties;
	}

	public static Map<String, String> getLicenseProperties(String productId) {
		Map<String, String> properties = new HashMap<>();

		AtomicStampedReference<License> licenseStampedReference =
			_licenseStampedReferences.get(productId);

		if (licenseStampedReference == null) {
			return properties;
		}

		int[] stampHolder = new int[1];

		License license = licenseStampedReference.get(stampHolder);

		int licenseState = stampHolder[0];

		if ((license == null) ||
			(licenseState == LicenseConstants.STATE_ABSENT)) {

			return properties;
		}

		return license.getProperties();
	}

	public static int getLicenseState() {
		return getLicenseState(null, LicenseConstants.PRODUCT_ID_PORTAL);
	}

	public static int getLicenseState(HttpServletRequest request) {
		return getLicenseState(request, LicenseConstants.PRODUCT_ID_PORTAL);
	}

	public static int getLicenseState(
		HttpServletRequest request, String productId) {

		AtomicStampedReference<License> licenseStampedReference =
			_licenseStampedReferences.get(productId);

		int[] stampHolder = new int[1];

		License license = null;

		if (licenseStampedReference != null) {
			license = licenseStampedReference.get(stampHolder);
		}

		int licenseState = stampHolder[0];

		if (license == null) {
			return LicenseConstants.STATE_ABSENT;
		}

		if (licenseState != LicenseConstants.STATE_GOOD) {
			return licenseState;
		}

		if (license.isExpired()) {
			_setLicense(license, LicenseConstants.STATE_EXPIRED, false, true);

			return LicenseConstants.STATE_EXPIRED;
		}

		// Good license state

		return licenseState;
	}

	public static int getLicenseState(String productId) {
		return getLicenseState(null, productId);
	}

	public static String getServerId() throws Exception {
		if (Validator.isNotNull(_serverId)) {
			return _serverId;
		}

		Properties serverProperties = _getServerProperties();

		_serverId = serverProperties.getProperty("serverId");

		if (Validator.isNull(_serverId)) {
			_serverId = _generateServerId();

			serverProperties.put("serverId", _serverId);

			_writeServerProperties(serverProperties);
		}
		else {
			byte[] serverIdBytes = (byte[])Base64.stringToObject(_serverId);

			for (Key key : _keys) {
				serverIdBytes = EncryptorUtil.decryptUnencodedAsBytes(
					key, serverIdBytes);
			}

			Properties serverIdProperties = new Properties();

			PropertiesUtil.load(serverIdProperties, new String(serverIdBytes));

			String serverIdHostName = GetterUtil.getString(
				serverIdProperties.getProperty("hostName"));

			if (serverIdHostName.equalsIgnoreCase(
					PortalUtil.getComputerName())) {

				return _serverId;
			}

			List<String> serverIdIpAddresses = ListUtil.fromArray(
				StringUtil.split(
					serverIdProperties.getProperty("ipAddresses")));

			serverIdIpAddresses.retainAll(LicenseUtil.getIpAddresses());

			if (!serverIdIpAddresses.isEmpty()) {
				return _serverId;
			}

			List<String> serverIdMacAddresses = ListUtil.fromArray(
				StringUtil.split(
					serverIdProperties.getProperty("macAddresses")));

			serverIdMacAddresses.retainAll(LicenseUtil.getMacAddresses());

			if (!serverIdMacAddresses.isEmpty()) {
				return _serverId;
			}

			_serverId = _generateServerId();

			serverProperties.put("serverId", _serverId);

			_writeServerProperties(serverProperties);
		}

		return _serverId;
	}

	public static void init() {
		checkBinaryLicenses();

		PortalLifecycle portalLifecycle = new BasePortalLifecycle() {

			@Override
			protected void doPortalDestroy() {
				try {
					if (_scheduledThreadPoolExecutor != null) {
						_scheduledThreadPoolExecutor.shutdown();
					}
				}
				catch (Exception e) {
					_log.error(e);
				}
			}

			@Override
			protected void doPortalInit() {
			}

		};

		PortalLifecycleUtil.register(
			portalLifecycle, PortalLifecycle.METHOD_DESTROY);
	}

	public static void registerLicense(String licenseXML) {
		try {
			for (License license : _parseLicenseFile(licenseXML)) {
				if (!KeyValidator.validate(license)) {
					_log.error(
						"Corrupt license file. License was not registered: " +
							license);

					continue;
				}

				File binaryLicenseFile = _buildBinaryFile(license);

				if (binaryLicenseFile.exists() &&
					Objects.equals(
						license, _readBinaryLicenseFile(binaryLicenseFile))) {

					if (_log.isDebugEnabled()) {
						_log.debug(
							"License has been registered for " +
								license.getProductEntryName());
					}

					continue;
				}

				writeBinaryLicense(license);

				for (License internalLicense :
						_getBinaryLicenses(license.getProductId())) {

					_checkBinaryLicense(internalLicense, true);
				}

				if (_log.isInfoEnabled()) {
					_log.info(
						"License registered for " +
							license.getProductEntryName());
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to register license", e);
		}
	}

	public static void setLicense(
		License license, int licenseState, boolean notify) {

		_setLicense(license, licenseState, notify, false);
	}

	public static void writeBinaryLicense(License license) throws Exception {
		File licenseRepositoryDir = new File(
			LicenseUtil.LICENSE_REPOSITORY_DIR);

		if (!licenseRepositoryDir.exists()) {
			licenseRepositoryDir.mkdirs();
		}

		File binaryLicenseFile = _buildBinaryFile(license);

		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(binaryLicenseFile);

			objectOutputStream = new ObjectOutputStream(
				new Base64OutputStream(fileOutputStream));

			license.setLastAccessedTime(System.currentTimeMillis());

			objectOutputStream.writeInt(4);
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getAccountEntryName()));
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getDescription()));
			objectOutputStream.writeObject(license.getExpirationDate());
			objectOutputStream.writeObject(license.getHostNames());
			objectOutputStream.writeObject(license.getIpAddresses());
			objectOutputStream.writeUTF(GetterUtil.getString(license.getKey()));
			objectOutputStream.writeLong(license.getLastAccessedTime());
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getLicenseEntryName()));
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getLicenseEntryType()));
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getLicenseVersion()));
			objectOutputStream.writeObject(license.getMacAddresses());

			if (Objects.equals(
					LicenseConstants.TYPE_VIRTUAL_CLUSTER,
					license.getLicenseEntryType())) {

				objectOutputStream.writeInt(license.getMaxClusterNodes());
			}

			objectOutputStream.writeInt(license.getMaxHttpSessions());
			objectOutputStream.writeInt(license.getMaxServers());
			objectOutputStream.writeLong(license.getMaxConcurrentUsers());
			objectOutputStream.writeLong(license.getMaxUsers());
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getInstanceSize()));
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getOwner()));
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getProductEntryName()));
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getProductId()));
			objectOutputStream.writeUTF(
				GetterUtil.getString(license.getProductVersion()));
			objectOutputStream.writeObject(license.getServerIds());
			objectOutputStream.writeObject(license.getStartDate());
		}
		finally {
			if (objectOutputStream != null) {
				objectOutputStream.close();
			}

			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
	}

	private static void _appendChildElementsArray(
		List<String> list, Element element, String childName) {

		if (element != null) {
			List<Element> childElements = element.elements(childName);

			for (Element childElement : childElements) {
				list.add(childElement.getTextTrim());
			}
		}
	}

	private static File _buildBinaryFile(License license) {
		StringBundler sb = new StringBundler(6);

		String productId = license.getProductId();

		if (productId.equals(LicenseConstants.PRODUCT_ID_PORTAL)) {
			sb.append(StringUtil.extractChars(license.getAccountEntryName()));
			sb.append("_");
		}

		sb.append(StringUtil.extractChars(license.getProductEntryName()));
		sb.append("_");
		sb.append(StringUtil.extractChars(license.getLicenseEntryType()));
		sb.append(".li");

		return new File(LicenseUtil.LICENSE_REPOSITORY_DIR, sb.toString());
	}

	private static void _checkBinaryLicense(License license, boolean notify) {
		try {
			String serverId = getServerId();

			if (license == null) {
				return;
			}

			if (!notify) {
				AtomicStampedReference<License> licenseStampedReference =
					_licenseStampedReferences.get(license.getProductId());

				if (licenseStampedReference != null) {
					License currentLicense = licenseStampedReference.get(
						new int[1]);

					if (System.currentTimeMillis() <
							(currentLicense.getLastAccessedTime() +
								_INITIAL_DELAY)) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Avoid checking binary license more than " +
									"one time in " + _INITIAL_DELAY + " ms");
						}

						return;
					}
				}
			}

			String[] serverIds = license.getServerIds();

			if ((serverIds != null) && (serverIds.length > 0)) {
				if (!ArrayUtil.contains(serverIds, serverId)) {
					throw new Exception(
						"Server id matching failed. Allowed server ids: " +
							StringUtil.merge(serverIds));
				}

				if (!_isActiveLicense(license, false)) {
					setLicense(
						license, LicenseConstants.STATE_INACTIVE, notify);

					_log.error(
						license.getProductEntryName() + " license is inactive");

					return;
				}
			}

			if (license.isExpired()) {
				setLicense(license, LicenseConstants.STATE_EXPIRED, notify);

				_log.error(
					license.getProductEntryName() + " license is expired");

				return;
			}

			_validatorChain.validate(license);

			setLicense(license, LicenseConstants.STATE_GOOD, notify);

			if (_log.isInfoEnabled()) {
				_log.info(
					license.getProductEntryName() +
						" license validation passed");
			}
		}
		catch (CompanyMaxUsersException cmue) {
			setLicense(license, LicenseConstants.STATE_OVERLOAD, notify);

			_log.error(
				license.getProductEntryName() + " license validation failed",
				cmue);
		}
		catch (Exception e) {
			setLicense(license, LicenseConstants.STATE_INVALID, notify);

			_log.error(
				license.getProductEntryName() + " license validation failed",
				e);
		}
	}

	private static String _generateServerId() throws Exception {
		String hostName = PortalUtil.getComputerName();
		Set<String> ipAddresses = LicenseUtil.getIpAddresses();
		Set<String> macAddresses = LicenseUtil.getMacAddresses();

		Properties serverIdProperties = new Properties();

		serverIdProperties.put("hostName", hostName);
		serverIdProperties.put("ipAddresses", StringUtil.merge(ipAddresses));
		serverIdProperties.put("macAddresses", StringUtil.merge(macAddresses));
		serverIdProperties.put(
			"salt",
			UUID.randomUUID(
			).toString());

		String propertiesString = PropertiesUtil.toString(serverIdProperties);

		byte[] bytes = propertiesString.getBytes(StringPool.UTF8);

		for (int i = _keys.length - 1; i >= 0; i--) {
			bytes = EncryptorUtil.encryptUnencoded(_keys[i], bytes);
		}

		return Base64.objectToString(bytes);
	}

	private static TreeSet<License> _getBinaryLicenses() {
		TreeSet<License> licenses = new TreeSet<>();

		File licenseRepositoryDir = new File(
			LicenseUtil.LICENSE_REPOSITORY_DIR);

		if (!licenseRepositoryDir.exists() ||
			!licenseRepositoryDir.isDirectory()) {

			if (_log.isInfoEnabled()) {
				_log.info("Failed to find directory " + licenseRepositoryDir);
			}

			return licenses;
		}

		File[] binaryLicenseFiles = licenseRepositoryDir.listFiles();

		if ((binaryLicenseFiles == null) || (binaryLicenseFiles.length == 0)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Failed to find license files in directory " +
						licenseRepositoryDir);
			}

			return licenses;
		}

		for (File binaryLicenseFile : binaryLicenseFiles) {
			if (binaryLicenseFile.isDirectory()) {
				continue;
			}

			License license = _readBinaryLicenseFile(binaryLicenseFile);

			if (license != null) {
				licenses.add(license);
			}
		}

		long currentTime = System.currentTimeMillis();

		Iterator<License> licenseIterator = licenses.iterator();

		while (licenseIterator.hasNext()) {
			License license = licenseIterator.next();

			// Remove corrupted licenses

			if (!KeyValidator.validate(license)) {
				licenseIterator.remove();

				File file = _buildBinaryFile(license);

				file.delete();

				_log.error(
					"Corrupt license file. Removing license file " + license);

				continue;
			}

			// Skip licenses with future last accessed times

			long lastAccessedTime = license.getLastAccessedTime();

			if ((currentTime + (Time.DAY * 2)) < lastAccessedTime) {
				licenseIterator.remove();

				_log.error(
					"A license modified in the future was detected. License " +
						"Modified: " + lastAccessedTime + ". Current time: " +
							currentTime + ". Skipping license file " + license);

				continue;
			}

			// Touch license file

			try {
				writeBinaryLicense(license);
			}
			catch (Exception e) {
			}

			// Skip licenses that have not started yet

			Date startDate = license.getStartDate();

			if ((currentTime + (Time.DAY * 2)) < startDate.getTime()) {
				licenseIterator.remove();

				_log.error(
					"License has not reached start date yet. Skipping " +
						"license file " + license);

				continue;
			}
		}

		return licenses;
	}

	private static List<License> _getBinaryLicenses(String productId) {
		List<License> licenses = new ArrayList<>();

		TreeSet<License> binaryLicenses = _getBinaryLicenses();

		if (binaryLicenses.isEmpty()) {
			if (productId.equals(LicenseConstants.PRODUCT_ID_PORTAL)) {
				return licenses;
			}
		}

		for (License binaryLicense : binaryLicenses) {
			String curProductId = binaryLicense.getProductId();

			if (curProductId.equals(productId)) {
				licenses.add(binaryLicense);
			}
		}

		return licenses;
	}

	private static License _getBinaryLicenseVersion(
			int binaryLicenseVersion, ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		String accountEntryName = objectInputStream.readUTF();
		String description = objectInputStream.readUTF();
		Date expirationDate = (Date)objectInputStream.readObject();
		String[] hostNames = (String[])objectInputStream.readObject();
		String[] ipAddresses = (String[])objectInputStream.readObject();
		String key = objectInputStream.readUTF();
		long lastAccessedTime = objectInputStream.readLong();
		String licenseEntryName = objectInputStream.readUTF();
		String licenseEntryType = objectInputStream.readUTF();
		String licenseVersion = objectInputStream.readUTF();
		String[] macAddresses = (String[])objectInputStream.readObject();

		int maxClusterNodes = 0;

		if (licenseEntryType.equals(LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {
			maxClusterNodes = objectInputStream.readInt();
		}

		int maxHttpSessions = objectInputStream.readInt();
		int maxServers = objectInputStream.readInt();
		long maxConcurrentUsers = objectInputStream.readLong();
		long maxUsers = objectInputStream.readLong();

		String instanceSize = StringPool.BLANK;

		if (binaryLicenseVersion >= 4) {
			instanceSize = objectInputStream.readUTF();
		}

		String owner = objectInputStream.readUTF();
		String productEntryName = objectInputStream.readUTF();
		String productId = objectInputStream.readUTF();
		String productVersion = objectInputStream.readUTF();
		String[] serverIds = (String[])objectInputStream.readObject();
		Date startDate = (Date)objectInputStream.readObject();

		License license = new License(
			accountEntryName, owner, description, productEntryName, productId,
			productVersion, licenseEntryName, licenseEntryType, licenseVersion,
			startDate, expirationDate, maxClusterNodes, maxServers,
			maxHttpSessions, maxConcurrentUsers, maxUsers, instanceSize,
			hostNames, ipAddresses, macAddresses, serverIds, key);

		license.setLastAccessedTime(lastAccessedTime);

		return license;
	}

	private static String _getSafePropertyKey(String key) {
		key = StringUtil.replace(key, StringPool.COLON, "_SAFE_COLON_");
		key = StringUtil.replace(key, StringPool.EQUAL, "_SAFE_EQUAL_");
		key = StringUtil.replace(key, StringPool.SPACE, "_SAFE_SPACE_");

		return key;
	}

	private static Properties _getServerProperties() throws Exception {
		Properties serverProperties = new Properties();

		File serverIdFile = new File(
			LicenseUtil.LICENSE_REPOSITORY_DIR + "/server/serverId");

		if (!serverIdFile.exists()) {
			return serverProperties;
		}

		byte[] bytes = FileUtil.getBytes(serverIdFile);

		for (Key key : _keys) {
			bytes = EncryptorUtil.decryptUnencodedAsBytes(key, bytes);
		}

		PropertiesUtil.load(serverProperties, new String(bytes));

		return serverProperties;
	}

	private static void _initKeys() {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		if (_keys == null) {
			_keys = new Key[3];

			String content = null;

			try {
				content = StringUtil.read(
					classLoader,
					"com/liferay/portal/license/classloader/keys.txt");
			}
			catch (Exception e) {
				_log.error(e);
			}

			String contentDigest = DigesterUtil.digestBase64(content);

			String[] keys = StringUtil.split(content, StringPool.NEW_LINE);

			int count = 0;
			int marker = 3;
			int pos = 0;

			char[] charArray = contentDigest.toCharArray();

			for (char c : charArray) {
				int x = c;

				count++;

				if ((count % marker) == 0) {
					_keys[(marker / 3) - 1] = (Key)Base64.stringToObject(
						keys[pos]);

					count = 0;
					marker = marker + 3;
					pos = 0;
				}
				else {
					pos += x;
				}
			}
		}
	}

	private static boolean _isActiveLicense(
			License license, boolean scheduledCheck)
		throws Exception {

		long now = System.currentTimeMillis();

		Properties serverProperties = _getServerProperties();

		String productId = license.getProductId();

		String lastActiveKey = _getSafePropertyKey(
			productId + "_lastActiveTime");

		if (_activeLicenses.contains(productId)) {
			serverProperties.put(lastActiveKey, String.valueOf(now));

			_writeServerProperties(serverProperties);

			return true;
		}

		_scheduleActiveCheckDaily();

		String serverId = serverProperties.getProperty("serverId");

		String randomUuid = UUID.randomUUID(
		).toString();

		JSONObject jsonObject = new JSONObjectImpl();

		jsonObject.put("cmd", "VALIDATE");
		jsonObject.put("key", license.getKey());
		jsonObject.put("productId", productId);
		jsonObject.put("randomUuid", randomUuid);
		jsonObject.put("serverId", serverId);
		jsonObject.put("version", 1);

		try {
			String response = LicenseUtil.sendRequest(jsonObject.toString());

			JSONObject responseJSONObject = new JSONObjectImpl(response);

			boolean active = responseJSONObject.getBoolean("active");
			String responseRandomUuid = responseJSONObject.getString(
				"randomUuid");

			if (active && responseRandomUuid.equals(randomUuid)) {
				serverProperties.put(lastActiveKey, String.valueOf(now));

				_writeServerProperties(serverProperties);

				_activeLicenses.add(productId);

				return true;
			}

			return false;
		}
		catch (Exception e) {
			long graceTime = _LICENSE_ACTIVE_CHECK_GRACE_TIME;

			if (graceTime > _LICENSE_ACTIVE_CHECK_GRACE_MAX_TIME) {
				graceTime = _LICENSE_ACTIVE_CHECK_GRACE_MAX_TIME;
			}

			StringBundler sb = new StringBundler(6);

			sb.append("Unable to communicate with ");
			sb.append(LicenseUtil.LICENSE_SERVER_URL);
			sb.append(". Please check the connection.");

			long lastActiveTime = GetterUtil.getLong(
				serverProperties.getProperty(lastActiveKey));

			long diff = now - lastActiveTime;

			if ((lastActiveTime <= 0) || (diff > graceTime)) {
				throw new Exception(sb.toString());
			}

			sb.append(" You have a grace period of ");
			sb.append((graceTime - diff) / Time.DAY);
			sb.append(" days.");

			_log.error(sb.toString(), e);

			if (scheduledCheck) {
				throw e;
			}
		}

		return true;
	}

	private static License _parseLicenseElement(Element licenseElement)
		throws Exception {

		String accountEntryName = GetterUtil.getString(
			licenseElement.elementTextTrim("account-name"));
		String owner = GetterUtil.getString(
			licenseElement.elementTextTrim("owner"));
		String description = GetterUtil.getString(
			licenseElement.elementTextTrim("description"));
		String productEntryName = GetterUtil.getString(
			licenseElement.elementTextTrim("product-name"));
		String productId = GetterUtil.getString(
			licenseElement.elementTextTrim("product-id"),
			LicenseConstants.PRODUCT_ID_PORTAL);
		String productVersion = GetterUtil.getString(
			licenseElement.elementTextTrim("product-version"));
		String licenseEntryName = GetterUtil.getString(
			licenseElement.elementTextTrim("license-name"));
		String licenseEntryType = GetterUtil.getString(
			licenseElement.elementTextTrim("license-type"));
		String licenseVersion = GetterUtil.getString(
			licenseElement.elementTextTrim("license-version"));

		DateFormat longDateFormatDateTime = new SimpleDateFormat(
			"EEEE, MMMM d, yyyy hh:mm:ss a z", Locale.US);

		Date startDate = null;

		if (licenseEntryType.equals(LicenseConstants.TYPE_TRIAL)) {
			startDate = new Date();
		}
		else {
			startDate = longDateFormatDateTime.parse(
				licenseElement.elementTextTrim("start-date"));
		}

		Date expirationDate = null;

		if (licenseEntryType.equals(LicenseConstants.TYPE_TRIAL)) {
			long lifetime = GetterUtil.getLong(
				licenseElement.elementTextTrim("lifetime"));

			expirationDate = new Date(startDate.getTime() + lifetime);
		}
		else {
			expirationDate = longDateFormatDateTime.parse(
				licenseElement.elementTextTrim("expiration-date"));
		}

		int maxClusterNodes = 0;

		if (licenseEntryType.equals(LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {
			maxClusterNodes = GetterUtil.getInteger(
				licenseElement.elementTextTrim("max-cluster-nodes"));
		}

		int maxServers = GetterUtil.getInteger(
			licenseElement.elementTextTrim("max-servers"));
		int maxHttpSessions = GetterUtil.getInteger(
			licenseElement.elementTextTrim("max-http-sessions"));
		long maxConcurrentUsers = GetterUtil.getLong(
			licenseElement.elementTextTrim("max-concurrent-users"));
		long maxUsers = GetterUtil.getLong(
			licenseElement.elementTextTrim("max-users"));
		String instanceSize = GetterUtil.getString(
			licenseElement.elementTextTrim("instance-size"));

		List<String> hostNames = new ArrayList<>();
		List<String> ipAddresses = new ArrayList<>();
		List<String> macAddresses = new ArrayList<>();
		List<String> serverIds = new ArrayList<>();

		_appendChildElementsArray(
			hostNames, licenseElement.element("host-names"), "host-name");
		_appendChildElementsArray(
			ipAddresses, licenseElement.element("ip-addresses"), "ip-address");
		_appendChildElementsArray(
			macAddresses, licenseElement.element("mac-addresses"),
			"mac-address");
		_appendChildElementsArray(
			serverIds, licenseElement.element("server-ids"), "server-id");

		Element serversElements = licenseElement.element("servers");

		if (serversElements != null) {
			List<Element> serverElements = serversElements.elements("server");

			for (Element serverElement : serverElements) {
				_appendChildElementsArray(
					hostNames, serverElement.element("host-names"),
					"host-name");
				_appendChildElementsArray(
					ipAddresses, serverElement.element("ip-addresses"),
					"ip-address");
				_appendChildElementsArray(
					macAddresses, serverElement.element("mac-addresses"),
					"mac-address");
				_appendChildElementsArray(
					serverIds, serverElement.element("server-ids"),
					"server-id");
			}
		}

		String key = licenseElement.elementTextTrim("key");

		License license = new License(
			accountEntryName, owner, description, productEntryName, productId,
			productVersion, licenseEntryName, licenseEntryType, licenseVersion,
			startDate, expirationDate, maxClusterNodes, maxServers,
			maxHttpSessions, maxConcurrentUsers, maxUsers, instanceSize,
			hostNames.toArray(new String[0]),
			ipAddresses.toArray(new String[0]),
			macAddresses.toArray(new String[0]),
			serverIds.toArray(new String[0]), key);

		if (licenseEntryType.equals(LicenseConstants.TYPE_TRIAL)) {
			license = KeyValidator.registerTrial(license);
		}

		return license;
	}

	private static List<License> _parseLicenseFile(String licenseXML)
		throws Exception {

		Document document = SAXReaderUtil.read(licenseXML);

		Element rootElement = document.getRootElement();

		String rootElementName = rootElement.getName();

		List<License> licenses = new ArrayList<>();

		if (rootElementName.equals("licenses")) {
			for (Element licenseElement : rootElement.elements("license")) {
				licenses.add(_parseLicenseElement(licenseElement));
			}
		}
		else {
			licenses.add(_parseLicenseElement(rootElement));
		}

		return licenses;
	}

	private static License _readBinaryLicenseFile(File binaryLicenseFile) {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		try {
			fileInputStream = new FileInputStream(binaryLicenseFile);

			objectInputStream = new ObjectInputStream(
				new Base64InputStream(fileInputStream));

			int licenseVersion = objectInputStream.readInt();

			return _getBinaryLicenseVersion(licenseVersion, objectInputStream);
		}
		catch (Exception e) {
			_log.error("Failed to read license file " + binaryLicenseFile, e);
		}
		finally {
			if (objectInputStream != null) {
				try {
					objectInputStream.close();
				}
				catch (IOException ioe) {
				}
			}

			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				}
				catch (IOException ioe) {
				}
			}
		}

		return null;
	}

	private static void _scheduleActiveCheckDaily() {
		if (_scheduledThreadPoolExecutor != null) {
			return;
		}

		_scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
			1,
			new ThreadFactory() {

				public Thread newThread(Runnable runnable) {
					Thread thread = new Thread(runnable, StringPool.BLANK);

					thread.setDaemon(true);

					return thread;
				}

			});

		_scheduledThreadPoolExecutor.scheduleAtFixedRate(
			new Runnable() {

				public void run() {
					_verifyActiveLicenses();
				}

			},
			_INITIAL_DELAY, _LICENSE_ACTIVE_CHECK_TIME, TimeUnit.MILLISECONDS);
	}

	private static void _sendEmail() throws PortalException {
		String subject = "[$PORTAL_URL$] License Unable to Validate";

		StringBundler sb = new StringBundler(8);

		sb.append("Dear [$TO_NAME$],<br /><br />");
		sb.append("Your Liferay Portal instance with host name, ");
		sb.append("[$HOST_NAME$], is unable to contact [$SERVER_URL$]. ");
		sb.append("Please check its internet connection and make sure it is ");
		sb.append("able to connect to [$SERVER_URL$] otherwise your license ");
		sb.append("will become inactive.<br /><br />");
		sb.append("Sincerely,<br />[$FROM_NAME$]<br />[$FROM_ADDRESS$]<br />");
		sb.append("[$PORTAL_URL$]<br />");

		String body = sb.toString();

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setBody(body);
		subscriptionSender.setCompanyId(PortalInstances.getDefaultCompanyId());
		subscriptionSender.setContextAttributes(
			"[$HOST_NAME$]", PortalUtil.getComputerName(), "[$SERVER_URL$]",
			LicenseUtil.LICENSE_SERVER_URL);
		subscriptionSender.setFrom(
			PropsValues.ADMIN_EMAIL_FROM_ADDRESS,
			PropsValues.ADMIN_EMAIL_FROM_NAME);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setMailId("license", LicenseUtil.LICENSE_SERVER_URL);
		subscriptionSender.setReplyToAddress(
			PropsValues.ADMIN_EMAIL_FROM_ADDRESS);
		subscriptionSender.setSubject(subject);

		if (PropsValues.OMNIADMIN_USERS.length > 0) {
			for (long userId : PropsValues.OMNIADMIN_USERS) {
				try {
					User user = UserLocalServiceUtil.getUserById(userId);

					if (user.getCompanyId() ==
							PortalInstances.getDefaultCompanyId()) {

						subscriptionSender.addRuntimeSubscribers(
							user.getEmailAddress(), user.getFullName());
					}
				}
				catch (Exception e) {
				}
			}
		}
		else {
			Role role = RoleLocalServiceUtil.getRole(
				PortalInstances.getDefaultCompanyId(),
				RoleConstants.ADMINISTRATOR);

			List<User> users = UserLocalServiceUtil.getRoleUsers(
				role.getRoleId());

			for (User user : users) {
				subscriptionSender.addRuntimeSubscribers(
					user.getEmailAddress(), user.getFullName());
			}
		}

		subscriptionSender.flushNotificationsAsync();
	}

	private static void _setLicense(
		License license, int licenseState, boolean notify, boolean overWrite) {

		boolean[] added = {false};

		AtomicStampedReference<License> licenseStampedReference =
			_licenseStampedReferences.computeIfAbsent(
				license.getProductId(),
				key -> {
					added[0] = true;

					return new AtomicStampedReference<>(license, licenseState);
				});

		if (added[0]) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					license.getProductId() + " license " + license.getKey() +
						" state is " + licenseState);
			}

			return;
		}

		int[] stampHolder = new int[1];

		License curLicense = licenseStampedReference.get(stampHolder);

		int curLicenseState = stampHolder[0];

		if (overWrite ||
			((licenseState == LicenseConstants.STATE_GOOD) &&
			 (curLicenseState != LicenseConstants.STATE_GOOD)) ||
			((licenseState == curLicenseState) &&
			 (license.compareTo(curLicense) > 0))) {

			licenseStampedReference.set(license, licenseState);

			if (_log.isDebugEnabled()) {
				_log.debug(
					license.getProductId() + " license " + license.getKey() +
						" state is " + licenseState);
			}
		}
	}

	private static void _verifyActiveLicenses() {
		boolean connectionFailed = false;

		for (Map.Entry<String, AtomicStampedReference<License>> entry :
				_licenseStampedReferences.entrySet()) {

			AtomicStampedReference<License> licenseStampedReference =
				entry.getValue();

			int[] stampHolder = new int[1];

			License license = licenseStampedReference.get(stampHolder);

			try {
				_isActiveLicense(license, true);
			}
			catch (Exception e) {
				connectionFailed = true;
			}
		}

		if (connectionFailed) {
			try {
				_sendEmail();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	private static void _writeServerProperties(Properties serverProperties)
		throws Exception {

		File serverIdFile = new File(
			LicenseUtil.LICENSE_REPOSITORY_DIR + "/server/serverId");

		String serverPropertiesString = PropertiesUtil.toString(
			serverProperties);

		byte[] bytes = serverPropertiesString.getBytes(StringPool.UTF8);

		for (int i = _keys.length - 1; i >= 0; i--) {
			bytes = EncryptorUtil.encryptUnencoded(_keys[i], bytes);
		}

		FileUtil.write(serverIdFile, bytes);
	}

	private static final long _INITIAL_DELAY = 300 * Time.SECOND;

	private static final long _LICENSE_ACTIVE_CHECK_GRACE_MAX_TIME =
		60 * Time.DAY;

	private static final long _LICENSE_ACTIVE_CHECK_GRACE_TIME =
		GetterUtil.getLong(
			PropsUtil.get("license.active.check.grace.time"), 30 * Time.DAY);

	private static final long _LICENSE_ACTIVE_CHECK_TIME = GetterUtil.getLong(
		PropsUtil.get("license.active.check.time"), Time.DAY);

	private static final Log _log = LogFactoryUtil.getLog(LicenseManager.class);

	private static final Set<String> _activeLicenses = new HashSet<>();
	private static final MethodHandler _getLicensePropertiesMethodHandler =
		new MethodHandler(
			new MethodKey(LicenseManager.class, "getLicenseProperties"));
	private static Key[] _keys;
	private static final ConcurrentMap<String, AtomicStampedReference<License>>
		_licenseStampedReferences = new ConcurrentHashMap<>();
	private static ScheduledThreadPoolExecutor _scheduledThreadPoolExecutor;
	private static String _serverId;
	private static final LicenseValidator _validatorChain;

	static {
		_initKeys();

		LicenseTypeValidator licenseTypeValidator = new LicenseTypeValidator();

		LiferayVersionValidator liferayVersionValidator =
			new LiferayVersionValidator();
		ProductionValidator productionValidator = new ProductionValidator();
		LimitedValidator limitedValidator = new LimitedValidator();
		PerUserValidator perUserValidator = new PerUserValidator();
		DeveloperValidator developerValidator = new DeveloperValidator();
		InstanceSizeValidator instanceSizeValidator =
			new InstanceSizeValidator();

		licenseTypeValidator.setNextValidator(liferayVersionValidator);
		liferayVersionValidator.setNextValidator(productionValidator);
		productionValidator.setNextValidator(limitedValidator);
		limitedValidator.setNextValidator(perUserValidator);
		perUserValidator.setNextValidator(developerValidator);
		developerValidator.setNextValidator(instanceSizeValidator);

		_validatorChain = licenseTypeValidator;
	}

}