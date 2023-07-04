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

package com.liferay.portal.ee.license;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.ee.license.classloader.DecryptorClassLoader;
import com.liferay.portal.events.StartupAction;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.encryptor.EncryptorUtil;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.license.License;
import com.liferay.portal.license.LicenseConstants;
import com.liferay.portal.license.LicenseManager;
import com.liferay.portal.struts.AuthPublicPathRegistry;
import com.liferay.portal.util.LicenseUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.io.File;

import java.security.Key;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Amos Fong
 * @author Igor Beslic
 */
public class LCSLicenseManager {

	public static void checkUserLicense() throws PortalException {
		License license = LicenseManager.getLicense(
			LicenseConstants.PRODUCT_ID_PORTAL);

		if (license == null) {
			return;
		}

		long maxUsersCount = license.getMaxUsers();

		if (maxUsersCount <= 0) {
			return;
		}

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from User_ where (defaultUser = ?) " +
					"and (status = ?)")) {

			preparedStatement.setBoolean(1, false);
			preparedStatement.setLong(2, WorkflowConstants.STATUS_APPROVED);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long count = resultSet.getLong(1);

					if (count >= maxUsersCount) {
						throw new CompanyMaxUsersException();
					}
				}
			}
		}
		catch (SQLException sqlException) {
			throw new PortalException(sqlException);
		}
	}

	public static synchronized int[] getLCSStates(
		HttpServletRequest httpServletRequest) {

		int lcsPortletState = _lcsPortletState.intValue();

		int lcsLicenseState = 0;

		if (lcsPortletState != LCSPortletState.GOOD.intValue()) {

			// Fall back to old license mechanism

			lcsLicenseState = getLicenseState(httpServletRequest);

			if (lcsLicenseState != LicenseConstants.STATE_GOOD) {

				// Check if within grace period

				if (_lastActiveTime > 0) {
					long graceTimeLeft =
						_lastActiveTime + _GRACE_TIME -
							System.currentTimeMillis();

					if (_displayGracePeriodError(lcsPortletState)) {
						String message = _getGracePeriodMessage(graceTimeLeft);

						if ((_lastErrorLogTime <= 0) ||
							((_lastErrorLogTime + (10 * Time.MINUTE)) <
								System.currentTimeMillis())) {

							_log.error(message);

							_lastErrorLogTime = System.currentTimeMillis();
						}

						if (_isOmniAdmin(httpServletRequest)) {
							httpServletRequest.setAttribute(
								"LCS_NOTIFICATION_MESSAGE", message);
						}
					}

					if (graceTimeLeft >= 0) {
						lcsPortletState = LCSPortletState.GOOD.intValue();
					}
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"LCS portlet state: " + _lcsPortletState + " License state: " +
					lcsLicenseState);
		}

		return new int[] {lcsPortletState, lcsLicenseState};
	}

	public static int getLicenseState(HttpServletRequest httpServletRequest) {
		int lcsLicenseState = LicenseManager.getLicenseState(
			httpServletRequest, LicenseConstants.PRODUCT_ID_PORTAL);

		if (lcsLicenseState != LicenseConstants.STATE_GOOD) {
			return lcsLicenseState;
		}

		License license = LicenseManager.getLicense(
			LicenseConstants.PRODUCT_ID_PORTAL);

		String licenseEntryType = license.getLicenseEntryType();

		if (licenseEntryType.equals(LicenseConstants.TYPE_VIRTUAL_CLUSTER) &&
			ClusterExecutorUtil.isEnabled() &&
			_isLocalClusterNodeOverload(license, httpServletRequest)) {

			lcsLicenseState = LicenseConstants.STATE_OVERLOAD;
		}
		else if (licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER) ||
				 licenseEntryType.equals(
					 LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			if (httpServletRequest != null) {
				String remoteAddr = httpServletRequest.getRemoteAddr();

				_clientIPAddresses.putIfAbsent(remoteAddr, remoteAddr);

				_httpSessions.add(httpServletRequest.getSession());
			}

			if (_clientIPAddresses.size() > license.getMaxHttpSessions()) {
				LicenseManager.setLicense(
					license, LicenseConstants.STATE_OVERLOAD);

				if (Validator.isNull(_resetToken)) {
					_resetToken = UUID.randomUUID(
					).toString();
				}

				for (HttpSession httpSession : _httpSessions) {
					try {
						httpSession.invalidate();
					}
					catch (Exception exception) {
						if (_log.isDebugEnabled()) {
							_log.debug(exception);
						}
					}
				}

				_httpSessions.clear();

				lcsLicenseState = LicenseConstants.STATE_OVERLOAD;
			}
		}

		return lcsLicenseState;
	}

	public static LifecycleAction getLifecycleAction() {
		ClassLoader classLoader = new DecryptorClassLoader();

		try {
			Class<?> lifecycleActionClass = classLoader.loadClass(
				"com.liferay.portal.ee.license.LifecycleAction");

			return (LifecycleAction)lifecycleActionClass.newInstance();
		}
		catch (Exception exception) {
			_log.error(exception);

			throw new RuntimeException(exception);
		}
	}

	public static String getResetToken() {
		return _resetToken;
	}

	public static StartupAction getStartupAction() throws Exception {
		ClassLoader classLoader = new DecryptorClassLoader();

		Class<?> startupActionClass = classLoader.loadClass(
			"com.liferay.portal.ee.license.StartupAction");

		return (StartupAction)startupActionClass.newInstance();
	}

	public static void init() {
		AuthPublicPathRegistry.register("/portal/license_activation");

		Properties lcsStateProperties = _getLCSStateProperties();

		_lastActiveTime = GetterUtil.getLong(
			lcsStateProperties.getProperty("lastActiveTime"));
		_noConnectionTime = GetterUtil.getLong(
			lcsStateProperties.getProperty("noConnectionTime"));

		if (PropsValues.CLUSTER_LINK_ENABLED) {
			ClusterStatusWatcher clusterStatusWatcher =
				new ClusterStatusWatcher();

			clusterStatusWatcher.start();
		}
	}

	public static void resetState(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		String resetToken = ParamUtil.getString(
			httpServletRequest, "resetToken");

		if (Validator.isNotNull(_resetToken) &&
			resetToken.equals(_resetToken)) {

			_clientIPAddresses.clear();

			_resetToken = null;

			LicenseManager.checkBinaryLicense(
				LicenseConstants.PRODUCT_ID_PORTAL);
		}
	}

	public static synchronized void setLCSPortletState(
		LCSPortletState lcsPortletState) {

		if (_log.isDebugEnabled()) {
			_log.debug("Setting LCS state " + lcsPortletState);
		}

		_lcsPortletState = lcsPortletState;

		if (_lcsPortletInitTime <= 0) {
			_lcsPortletInitTime = System.currentTimeMillis();
		}

		if (lcsPortletState == LCSPortletState.NO_CONNECTION) {
			if (_noConnectionTime <= 0) {
				_noConnectionTime = System.currentTimeMillis();
			}
		}
		else {
			_noConnectionTime = 0;
		}

		Properties lcsStateProperties = _getLCSStateProperties();

		if (lcsPortletState == LCSPortletState.GOOD) {
			_lastActiveTime = System.currentTimeMillis();

			lcsStateProperties.setProperty(
				"lastActiveTime", String.valueOf(_lastActiveTime));

			lcsStateProperties.setProperty(
				"noConnectionTime", String.valueOf(_noConnectionTime));
		}
		else {
			long noConnectionTime = GetterUtil.getLong(
				lcsStateProperties.getProperty("noConnectionTime"));

			if (_noConnectionTime != noConnectionTime) {
				lcsStateProperties.setProperty(
					"noConnectionTime", String.valueOf(_noConnectionTime));
			}
			else {
				return;
			}
		}

		try {
			File lcsStateFile = new File(
				LicenseUtil.LICENSE_REPOSITORY_DIR + "/server/lcsState");

			String lcsStatePropertiesString = PropertiesUtil.toString(
				lcsStateProperties);

			byte[] bytes = lcsStatePropertiesString.getBytes(StringPool.UTF8);

			for (int i = _keys.length - 1; i >= 0; i--) {
				bytes = EncryptorUtil.encryptUnencoded(_keys[i], bytes);
			}

			FileUtil.write(lcsStateFile, bytes);
		}
		catch (Exception exception) {
			_log.error("Unable to write LCSState", exception);
		}
	}

	private static boolean _displayGracePeriodError(int lcsPortletState) {
		if (lcsPortletState == LCSPortletState.PLUGIN_ABSENT.intValue()) {
			return true;
		}

		// Give LCS portlet 1 minute to successfully connect to LCS gateway
		// before logging an error.

		if (_lcsPortletInitTime >= (System.currentTimeMillis() - Time.MINUTE)) {
			return false;
		}

		// Give a 1 hour buffer for no connection issues before logging an
		// error.

		if ((System.currentTimeMillis() - _noConnectionTime) >
				_NO_CONNECTION_GRACE_BUFFER_TIME) {

			return true;
		}

		return false;
	}

	private static String _getGracePeriodMessage(long graceTimeLeft) {
		StringBundler sb = new StringBundler(15);

		sb.append("Unable to validate subscription. Please check if the LCS ");
		sb.append("portlet is deployed and can connect to the LCS gateway.");

		if (graceTimeLeft >= 0) {
			sb.append(" You have a grace period of ");
		}
		else {
			sb.append(" Your grace period expired ");
		}

		graceTimeLeft = Math.abs(graceTimeLeft);

		long daysLeft = graceTimeLeft / Time.DAY;

		sb.append(daysLeft);
		sb.append(" day");

		if ((daysLeft == 0) || (daysLeft > 1)) {
			sb.append("s");
		}

		sb.append(" and ");

		long hoursLeft = (graceTimeLeft - (daysLeft * Time.DAY)) / Time.HOUR;

		sb.append(hoursLeft);
		sb.append(" hour");

		if ((hoursLeft == 0) || (hoursLeft > 1)) {
			sb.append("s");
		}

		if ((daysLeft == 0) && (hoursLeft <= 12)) {
			sb.append(" and ");

			long minutesLeft =
				graceTimeLeft - (daysLeft * Time.DAY) - (hoursLeft * Time.HOUR);

			minutesLeft = minutesLeft / Time.MINUTE;

			sb.append(minutesLeft);
			sb.append(" minute");

			if ((minutesLeft == 0) || (minutesLeft > 1)) {
				sb.append("s");
			}
		}

		if (graceTimeLeft >= 0) {
			sb.append(StringPool.PERIOD);
		}
		else {
			sb.append(" ago.");
		}

		return sb.toString();
	}

	private static synchronized Properties _getLCSStateProperties() {
		Properties lcsStateProperties = new Properties();

		try {
			File lcsStateFile = new File(
				LicenseUtil.LICENSE_REPOSITORY_DIR + "/server/lcsState");

			if (!lcsStateFile.exists()) {
				return lcsStateProperties;
			}

			byte[] bytes = FileUtil.getBytes(lcsStateFile);

			for (Key key : _keys) {
				bytes = EncryptorUtil.decryptUnencodedAsBytes(key, bytes);
			}

			PropertiesUtil.load(lcsStateProperties, new String(bytes));
		}
		catch (Exception exception) {
			_log.error("Unable to read LCSState", exception);
		}

		return lcsStateProperties;
	}

	private static long _getTimestamp() {
		return _CLUSTER_GRACE_PERIOD_TIMESTAMP;
	}

	private static void _initKeys() {
		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		_keys = new Key[3];

		String content = null;

		try {
			content = StringUtil.read(
				classLoader, "com/liferay/portal/license/classloader/keys.txt");
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		String contentDigest = DigesterUtil.digestBase64(content);

		String[] keys = StringUtil.split(content, StringPool.NEW_LINE);

		int count = 0;
		int marker = 3;
		int pos = 0;

		char[] charArray = contentDigest.toCharArray();

		for (char c : charArray) {
			count++;

			if ((count % marker) == 0) {
				_keys[(marker / 3) - 1] = (Key)Base64.stringToObject(keys[pos]);

				count = 0;
				marker = marker + 3;
				pos = 0;
			}
			else {
				pos += (int)c;
			}
		}
	}

	private static boolean _isLocalClusterNodeOverload(
		License license, HttpServletRequest httpServletRequest) {

		_setClusterGracePeriodStatusWithRetry(license, httpServletRequest);

		if ((_clusterGracePeriodEndTime == 0) ||
			(_localClusterNodeIndex < _maxClusterNodes) ||
			((System.currentTimeMillis() <= _clusterGracePeriodEndTime) &&
			 (_localClusterNodeIndex < (2 * _maxClusterNodes)))) {

			return false;
		}

		return true;
	}

	private static boolean _isOmniAdmin(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}

		User user = null;

		try {
			user = PortalUtil.getUser(httpServletRequest);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		if ((user != null) && OmniadminUtil.isOmniadmin(user)) {
			return true;
		}

		return false;
	}

	private static void _logClusterGracePeriodMessage(
		HttpServletRequest httpServletRequest) {

		if ((_maxClusterNodes == 0) || (_clusterGracePeriodEndTime == 0)) {
			return;
		}

		StringBundler sb = new StringBundler(20);

		sb.append("The maximum number of ");
		sb.append(_maxClusterNodes);
		sb.append(" node");

		if (_maxClusterNodes > 1) {
			sb.append("s");
		}

		sb.append(" licensed for this cluster has been exceeded. Please shut ");
		sb.append("down excess nodes as soon as possible. An additional ");
		sb.append(_maxClusterNodes);
		sb.append(" node");

		if (_maxClusterNodes > 1) {
			sb.append("s");
		}

		sb.append(" are temporarily permitted during the grace period ");

		long graceTimeLeft =
			_clusterGracePeriodEndTime - System.currentTimeMillis();

		boolean error = false;

		if (graceTimeLeft > 0) {
			sb.append("which expires in ");

			long hoursLeft = graceTimeLeft / Time.HOUR;

			if (hoursLeft == 2) {
				sb.append("2 hours");
			}
			else if (hoursLeft == 1) {
				sb.append("1 hour");
			}

			long minutesLeft =
				(graceTimeLeft - (hoursLeft * Time.HOUR)) / Time.MINUTE;

			if (minutesLeft > 1) {
				sb.append(minutesLeft);
				sb.append(" minutes");
			}
			else if (minutesLeft == 1) {
				sb.append("1 minute");
			}

			sb.append(". This current node is ");

			if (_localClusterNodeIndex < _maxClusterNodes) {
				sb.append("within the licensed node count and will not be ");
				sb.append("automatically deactivated nor shut down after the ");
				sb.append("grace period expires.");
			}
			else if (_localClusterNodeIndex < (2 * _maxClusterNodes)) {
				sb.append("within the temporarily permitted node count and ");
				sb.append("will be automatically deactivated ");

				if (_CLUSTER_OVERLOAD_NODE_AUTO_SHUT_DOWN) {
					sb.append("and shut down ");
				}

				sb.append("after the grace period expires.");
			}
			else {
				sb.append("beyond the temporarily permitted node count and ");
				sb.append("is deactivated");

				if (_CLUSTER_OVERLOAD_NODE_AUTO_SHUT_DOWN) {
					sb.append("and will automatically shut down ");
					sb.append("after the grace period expires");
				}

				sb.append(".");

				error = true;
			}
		}
		else {
			sb.append("which already ended. This current node is ");

			if (_localClusterNodeIndex <= _maxClusterNodes) {
				sb.append("within the licensed node count and is not ");
				sb.append("deactivated and will not automatically shut down.");
			}
			else {
				sb.append("beyond the licensed node count and is deactivated");

				if (_CLUSTER_OVERLOAD_NODE_AUTO_SHUT_DOWN) {
					sb.append(" and will automatically shut down");
				}

				sb.append(".");

				error = true;
			}
		}

		String clusterGracePeriodMessage = sb.toString();

		if ((_lastClusterGracePeriodLogTime <= 0) ||
			((_lastClusterGracePeriodLogTime + (10 * Time.MINUTE)) <
				System.currentTimeMillis())) {

			if (error) {
				_log.error(clusterGracePeriodMessage);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(clusterGracePeriodMessage);
			}

			_lastClusterGracePeriodLogTime = System.currentTimeMillis();
		}

		if (_isOmniAdmin(httpServletRequest)) {
			httpServletRequest.setAttribute(
				"CLUSTER_GRACE_PERIOD_MESSAGE", clusterGracePeriodMessage);
		}
	}

	private static synchronized void _resetClusterGracePeriodStatus() {
		if (_clusterGracePeriodEndTime != 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Finished shutting down overloaded nodes");
			}

			_clusterGracePeriodEndTime = 0;
		}

		if (_lastClusterGracePeriodLogTime != 0) {
			_lastClusterGracePeriodLogTime = 0;
		}
	}

	private static void _setClusterGracePeriodStatus(
			License license, HttpServletRequest httpServletRequest)
		throws Exception {

		_maxClusterNodes = license.getMaxClusterNodes();

		List<ClusterNode> clusterNodes = ClusterExecutorUtil.getClusterNodes();

		_totalClusterNodes = clusterNodes.size();

		if ((_totalClusterNodes - _maxClusterNodes) <= 0) {
			_resetClusterGracePeriodStatus();

			return;
		}

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(
				ClusterRequest.createMulticastRequest(
					new MethodHandler(_getTimestampMethodKey)));

		ClusterNodeResponses clusterNodeResponses;

		try {
			clusterNodeResponses = futureClusterResponses.get(
				20000, TimeUnit.MILLISECONDS);
		}
		catch (Exception exception) {
			throw new Exception(
				"Unable to get cluster node responses", exception);
		}

		List<ClusterNodeResponse> clusterNodeResponseList = new ArrayList<>(
			clusterNodeResponses.getClusterResponses());

		clusterNodeResponseList.sort(_clusterNodeResponseComparator);

		for (int i = 0; i < clusterNodeResponseList.size(); i++) {
			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponseList.get(i);

			if (Objects.equals(
					ClusterExecutorUtil.getLocalClusterNode(),
					clusterNodeResponse.getClusterNode())) {

				_localClusterNodeIndex = i;

				break;
			}
		}

		if (_clusterGracePeriodEndTime == 0) {
			ClusterNodeResponse clusterNodeResponse =
				clusterNodeResponseList.get(_maxClusterNodes);

			_clusterGracePeriodEndTime =
				(long)clusterNodeResponse.getResult() + _CLUSTER_GRACE_TIME;
		}

		_logClusterGracePeriodMessage(httpServletRequest);
	}

	private static synchronized void _setClusterGracePeriodStatusWithRetry(
		License license, HttpServletRequest httpServletRequest) {

		for (int i = 0; i < _MAX_RETRY; i++) {
			try {
				_setClusterGracePeriodStatus(license, httpServletRequest);

				return;
			}
			catch (Exception exception) {
				if ((i + 1) == _MAX_RETRY) {
					_log.error(exception);
				}
			}
		}
	}

	private static final long _CLUSTER_GRACE_PERIOD_TIMESTAMP =
		System.currentTimeMillis();

	private static final long _CLUSTER_GRACE_TIME = 2 * Time.HOUR;

	private static final boolean _CLUSTER_OVERLOAD_NODE_AUTO_SHUT_DOWN =
		GetterUtil.getBoolean(
			PropsUtil.get("license.cluster.overload.node.auto.shut.down"),
			true);

	private static final long _GRACE_TIME;

	private static final int _MAX_RETRY = GetterUtil.getInteger(
		PropsUtil.get("license.cluster.max.retry"), 5);

	private static final long _NO_CONNECTION_GRACE_BUFFER_TIME =
		GetterUtil.getLong(
			PropsUtil.get("license.no.connection.grace.buffer.time"),
			1 * Time.HOUR);

	private static final Log _log = LogFactoryUtil.getLog(
		LCSLicenseManager.class);

	private static final ConcurrentMap<String, String> _clientIPAddresses =
		new ConcurrentHashMap<>();
	private static volatile long _clusterGracePeriodEndTime;
	private static final ClusterNodeResponseComparator
		_clusterNodeResponseComparator = new ClusterNodeResponseComparator();
	private static final MethodKey _getTimestampMethodKey = new MethodKey(
		LCSLicenseManager.class, "_getTimestamp");
	private static final Set<HttpSession> _httpSessions = new HashSet<>();
	private static Key[] _keys;
	private static long _lastActiveTime;
	private static volatile long _lastClusterGracePeriodLogTime;
	private static long _lastErrorLogTime;
	private static long _lcsPortletInitTime;
	private static LCSPortletState _lcsPortletState =
		LCSPortletState.PLUGIN_ABSENT;
	private static volatile long _localClusterNodeIndex;
	private static volatile int _maxClusterNodes;
	private static long _noConnectionTime;
	private static String _resetToken;
	private static volatile long _totalClusterNodes;

	static {
		_initKeys();

		long graceTime = GetterUtil.getLong(
			PropsUtil.get("license.active.check.grace.time"), 30 * Time.DAY);

		if (graceTime > (60 * Time.DAY)) {
			graceTime = 60 * Time.DAY;
		}

		_GRACE_TIME = graceTime;
	}

	private static class ClusterNodeResponseComparator
		implements Comparator<ClusterNodeResponse> {

		@Override
		public int compare(
			ClusterNodeResponse clusterNodeResponse1,
			ClusterNodeResponse clusterNodeResponse2) {

			long result1 = (long)clusterNodeResponse1.getResult();
			long result2 = (long)clusterNodeResponse2.getResult();

			if (result1 > result2) {
				return 1;
			}
			else if (result1 < result2) {
				return -1;
			}

			ClusterNode clusterNode1 = clusterNodeResponse1.getClusterNode();
			ClusterNode clusterNode2 = clusterNodeResponse2.getClusterNode();

			String clusterNodeId1 = clusterNode1.getClusterNodeId();
			String clusterNodeId2 = clusterNode2.getClusterNodeId();

			return clusterNodeId1.compareTo(clusterNodeId2);
		}

	}

	private static class ClusterStatusWatcher extends Thread {

		public void close() {
			interrupt();

			try {
				join(_INTERVAL);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(interruptedException);
				}
			}
		}

		@Override
		public void run() {
			while (!interrupted()) {
				try {
					Thread.sleep(_INTERVAL);

					if (!ClusterExecutorUtil.isEnabled()) {
						continue;
					}

					int lcsLicenseState = LicenseManager.getLicenseState(
						null, LicenseConstants.PRODUCT_ID_PORTAL);

					if (lcsLicenseState != LicenseConstants.STATE_GOOD) {
						continue;
					}

					License license = LicenseManager.getLicense(
						LicenseConstants.PRODUCT_ID_PORTAL);

					if (!Objects.equals(
							license.getLicenseEntryType(),
							LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {

						continue;
					}

					_setClusterGracePeriodStatusWithRetry(license, null);

					if (_CLUSTER_OVERLOAD_NODE_AUTO_SHUT_DOWN &&
						(_clusterGracePeriodEndTime > 0) &&
						(System.currentTimeMillis() >
							_clusterGracePeriodEndTime) &&
						(_localClusterNodeIndex == (_totalClusterNodes - 1))) {

						if (_log.isInfoEnabled()) {
							_log.info(
								"Shutting down current node as it is the " +
									"latest one");
						}

						System.exit(0);
					}
				}
				catch (InterruptedException interruptedException) {
					if (_log.isDebugEnabled()) {
						_log.debug(interruptedException);
					}

					interrupt();

					return;
				}
				catch (Throwable throwable) {
					_log.error(throwable, throwable);
				}
			}
		}

		private ClusterStatusWatcher() {
			super("Cluster Status Watcher");

			Class<?> clazz = getClass();

			setContextClassLoader(clazz.getClassLoader());

			setDaemon(true);
			setPriority(MIN_PRIORITY);
		}

		private static final long _INTERVAL = 10 * Time.SECOND;

	}

}