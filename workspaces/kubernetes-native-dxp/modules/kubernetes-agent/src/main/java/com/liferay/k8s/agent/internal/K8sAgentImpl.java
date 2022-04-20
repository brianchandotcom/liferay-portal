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

package com.liferay.k8s.agent.internal;

import com.liferay.k8s.agent.K8sAgent;
import com.liferay.k8s.agent.configuration.v1.K8sAgentConfiguration;
import com.liferay.k8s.agent.properties.ConfigurationProperties;
import com.liferay.k8s.agent.properties.ConfigurationPropertiesFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.util.PropsValues;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerEventListener;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;

import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.k8s.agent.configuration.v1.K8sAgentConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = K8sAgent.class
)
public class K8sAgentImpl implements K8sAgent {

	@Activate
	public K8sAgentImpl(
			@Reference ConfigurationAdmin configurationAdmin,
			Map<String, Object> properties)
		throws Exception {

		_configurationAdmin = configurationAdmin;

		_k8sAgentConfiguration = ConfigurableUtil.createConfigurable(
			K8sAgentConfiguration.class, properties);

		_log.info(
			StringBundler.concat(
				"Initializing ", _AGENT_NAME, ": ",
				_k8sAgentConfiguration.namespace(), " ",
				_k8sAgentConfiguration.labelSelector()));

		Config config = Config.autoConfigure(null);

		_kubernetesClient = new DefaultKubernetesClient(config);

		SharedInformerFactory sharedInformerFactory =
			_kubernetesClient.informers();

		sharedInformerFactory.addSharedInformerEventListener(
			new SharedInformerEventListener() {

				@Override
				public void onException(Exception exception) {
					_log.error(exception);
				}

			});

		_sharedIndexInformer = _kubernetesClient.configMaps(
		).inNamespace(
			_k8sAgentConfiguration.namespace()
		).withLabel(
			_k8sAgentConfiguration.labelSelector()
		).inform(new ResourceEventHandler<ConfigMap>() {

			@Override
			public void onAdd(ConfigMap configMap) {
				_add(configMap);
			}

			@Override
			public void onDelete(
				ConfigMap configMap, boolean deletedFinalStateUnknown) {

				_delete(configMap);
			}

			@Override
			public void onUpdate(
				ConfigMap oldConfigMap, ConfigMap newConfigMap) {

				_modified(oldConfigMap, newConfigMap);
			}
		});

		if (_log.isDebugEnabled()) {
			_log.debug("Initialized " + _AGENT_NAME);
		}
	}

	@Override
	public void createOrUpdateConfigMap(
		Map<String, String> properties, String name,
		Map<String, String> labels) {

		ConfigMap configMap = new ConfigMapBuilder().withNewMetadata(
		).withName(
			name
		).withLabels(
			labels
		).endMetadata(
		).addToData(
			properties
		).build();

		if (_log.isDebugEnabled()) {
			_log.debug(StringBundler.concat("Creating ", configMap));
		}

		configMap = _kubernetesClient.configMaps(
		).inNamespace(
			_k8sAgentConfiguration.namespace()
		).withName(
			name
		).createOrReplace(
			configMap
		);

		if (_log.isDebugEnabled()) {
			_log.debug(StringBundler.concat("Created ", configMap));
		}
	}

	@Deactivate
	public void deactivate() {
		if (_log.isDebugEnabled()) {
			_log.debug("Deactivating " + _AGENT_NAME);
		}

		_sharedIndexInformer.close();
		_kubernetesClient.close();

		if (_log.isDebugEnabled()) {
			_log.debug("Deactivated " + _AGENT_NAME);
		}
	}

	@Override
	public void deleteConfigMap(String name) {
		_kubernetesClient.configMaps(
		).inNamespace(
			_k8sAgentConfiguration.namespace()
		).withName(
			name
		).delete();

		if (_log.isDebugEnabled()) {
			_log.debug(StringBundler.concat("Delted ", name));
		}
	}

	private void _add(ConfigMap configMap) {
		Map<String, String> data = configMap.getData();

		Set<Map.Entry<String, String>> entrySet = data.entrySet();

		for (Map.Entry<String, String> entry : entrySet) {
			String configName = entry.getKey();

			if (!configName.endsWith(_FILE_EXT)) {
				continue;
			}

			try {
				_processConfigMapConfigFileEntry(
					configName, entry.getValue(), configMap.getMetadata());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _delete(ConfigMap configMap) {
		Map<String, String> data = configMap.getData();

		Set<Map.Entry<String, String>> entrySet = data.entrySet();

		for (Map.Entry<String, String> entry : entrySet) {
			String configName = entry.getKey();

			if (!configName.endsWith(_FILE_EXT)) {
				continue;
			}

			try {
				_uninstallConfigMapConfigFileEntry(configName);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private Configuration _findExistingConfiguration(String fileName)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, _KUBERNETES_CONFIG_KEY,
				StringPool.EQUAL, fileName, StringPool.CLOSE_PARENTHESIS));

		if ((configurations != null) && (configurations.length > 0)) {
			return configurations[0];
		}

		return null;
	}

	private Configuration _getConfiguration(String pid, String name)
		throws Exception {

		if (name != null) {
			return _configurationAdmin.getFactoryConfiguration(
				pid, name, StringPool.QUESTION);
		}

		return _configurationAdmin.getConfiguration(pid, StringPool.QUESTION);
	}

	private void _modified(ConfigMap onlyConfigMap, ConfigMap newConfigMap) {
		Map<String, String> data = newConfigMap.getData();
		ObjectMeta metadata = newConfigMap.getMetadata();

		Set<Map.Entry<String, String>> entrySet = data.entrySet();

		for (Map.Entry<String, String> entry : entrySet) {
			String configName = entry.getKey();

			if (!configName.endsWith(_FILE_EXT)) {
				continue;
			}

			try {
				_processConfigMapConfigFileEntry(
					configName, entry.getValue(), newConfigMap.getMetadata());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		// Remove left over configurations which were deleted from the ConfigMap

		try {
			Configuration[] configurations =
				_configurationAdmin.listConfigurations(
					StringBundler.concat(
						StringPool.OPEN_PARENTHESIS, _KUBERNETES_CONFIG_UID,
						StringPool.EQUAL, metadata.getUid(),
						StringPool.CLOSE_PARENTHESIS));

			if (configurations != null) {
				for (Configuration configuration : configurations) {
					Dictionary<String, Object> properties =
						configuration.getProperties();

					String configKey = GetterUtil.getString(
						properties.get(_KUBERNETES_CONFIG_KEY));

					if (!data.containsKey(configKey)) {
						configuration.delete();
					}
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private String[] _parsePid(String path) {
		String pid = path.substring(0, path.lastIndexOf(CharPool.PERIOD));

		int index = pid.indexOf(CharPool.TILDE);

		if (index <= 0) {
			index = pid.indexOf(CharPool.UNDERLINE);

			if (index <= 0) {
				index = pid.indexOf(CharPool.DASH);
			}
		}

		if (index > 0) {
			String name = pid.substring(index + 1);

			pid = pid.substring(0, index);

			return new String[] {pid, name};
		}

		return new String[] {pid, null};
	}

	private void _processConfigMapConfigFileEntry(
			String configName, String configurationContent, ObjectMeta metadata)
		throws Exception {

		String resourceVersion = metadata.getResourceVersion();

		String[] pid = _parsePid(configName);

		Configuration configuration = _findExistingConfiguration(configName);

		if (configuration == null) {
			configuration = _getConfiguration(pid[0], pid[1]);
		}
		else {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			String existingResourceVersion = GetterUtil.getString(
				properties.get(_KUBERNETES_CONFIG_RESOURCE_VERSION));

			if (Objects.equals(resourceVersion, existingResourceVersion)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"The resourceVersion of the configuration (",
							existingResourceVersion,
							") is same as that of Kubernetes (",
							resourceVersion,
							") so this action will be ignored"));
				}

				return;
			}
		}

		Set<Configuration.ConfigurationAttribute> configurationAttributes =
			configuration.getAttributes();

		if (configurationAttributes.contains(
				Configuration.ConfigurationAttribute.READ_ONLY)) {

			configuration.removeAttributes(
				Configuration.ConfigurationAttribute.READ_ONLY);
		}

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		ConfigurationProperties configurationProperties =
			ConfigurationPropertiesFactory.create(
				configName, configurationContent,
				PropsValues.MODULE_FRAMEWORK_FILE_INSTALL_CONFIG_ENCODING);

		for (String key : configurationProperties.keySet()) {
			dictionary.put(key, configurationProperties.get(key));
		}

		dictionary.put(_KUBERNETES_CONFIG_KEY, configName);
		dictionary.put(_KUBERNETES_CONFIG_UID, metadata.getUid());
		dictionary.put(_KUBERNETES_CONFIG_RESOURCE_VERSION, resourceVersion);

		configuration.updateIfDifferent(dictionary);

		configuration.addAttributes(
			Configuration.ConfigurationAttribute.READ_ONLY);
	}

	private void _uninstallConfigMapConfigFileEntry(String configName)
		throws Exception {

		String[] pid = _parsePid(configName);

		String logString = StringPool.BLANK;

		if (pid[1] != null) {
			logString = StringPool.TILDE + pid[1];
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Deleting configuration from ", pid[0], logString,
					".config"));
		}

		Configuration configuration = _findExistingConfiguration(configName);

		if (configuration != null) {
			configuration.delete();
		}
	}

	private static final String _AGENT_NAME = "Kubernetes Configuration Agent";

	private static final String _FILE_EXT = ".config";

	private static final String _KUBERNETES_CONFIG_KEY =
		".kubernetes.config.key";

	private static final String _KUBERNETES_CONFIG_RESOURCE_VERSION =
		".kubernetes.config.resource.version";

	private static final String _KUBERNETES_CONFIG_UID =
		".kubernetes.config.uid";

	private static final Log _log = LogFactoryUtil.getLog(K8sAgentImpl.class);

	private final ConfigurationAdmin _configurationAdmin;
	private final SharedIndexInformer<ConfigMap> _sharedIndexInformer;
	private final K8sAgentConfiguration _k8sAgentConfiguration;
	private final KubernetesClient _kubernetesClient;

}