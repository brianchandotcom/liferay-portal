/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.service;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.utils.Serialization;

import jakarta.annotation.PreDestroy;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author José Abelenda
 */
@Service
public class KubernetesJobService {

	public KubernetesJobService(
		@Value("${liferay.ai.hub.crawler.elasticsearch.host}") String
			elasticsearchHost,
		@Value("${liferay.ai.hub.crawler.elasticsearch.port}") int
			elasticsearchPort,
		@Value("${liferay.ai.hub.crawler.k8s.image.name}") String imageName,
		@Value("${liferay.ai.hub.crawler.k8s.namespace}") String namespace) {

		_elasticsearchHost = elasticsearchHost;
		_elasticsearchPort = elasticsearchPort;
		_imageName = imageName;
		_namespace = namespace;
	}

	@PreDestroy
	public void close() {
		_kubernetesClient.close();
	}

	public String createJob(String indexName, String url) {
		URI uri = URI.create(url);

		String suffix = UUID.randomUUID(
		).toString(
		).substring(
			0, 8
		);

		String jobName = "aihub-crawler-" + suffix;

		Job job = new JobBuilder(
			_loadJobTemplate()
		).editMetadata(
		).withName(
			jobName
		).endMetadata(
		).editSpec(
		).editTemplate(
		).editSpec(
		).editFirstContainer(
		).withImage(
			_imageName
		).withEnv(
			_createEnvVar(
				"CRAWLER_DOMAIN_URL",
				uri.getScheme() + "://" + uri.getAuthority()),
			_createEnvVar("CRAWLER_OUTPUT_INDEX", indexName),
			_createEnvVar("CRAWLER_SEED_URL", url),
			_createEnvVar("ELASTICSEARCH_HOST", _elasticsearchHost),
			_createEnvVar(
				"ELASTICSEARCH_PORT", String.valueOf(_elasticsearchPort))
		).endContainer(
		).endSpec(
		).endTemplate(
		).endSpec(
		).build();

		_kubernetesClient.batch(
		).v1(
		).jobs(
		).inNamespace(
			_namespace
		).resource(
			job
		).create();

		if (_log.isInfoEnabled()) {
			_log.info("Kubernetes job dispatched: " + jobName);
		}

		return jobName;
	}

	private EnvVar _createEnvVar(String name, String value) {
		return new EnvVarBuilder(
		).withName(
			name
		).withValue(
			value
		).build();
	}

	private Job _loadJobTemplate() {
		try (InputStream inputStream = getClass().getResourceAsStream(
				"/crawler-job-template.yaml")) {

			return Serialization.unmarshal(inputStream, Job.class);
		}
		catch (IOException ioException) {
			throw new IllegalStateException(
				"Unable to load \"/crawler-job-template.yaml\"", ioException);
		}
	}

	private static final Log _log = LogFactory.getLog(
		KubernetesJobService.class);

	private final String _elasticsearchHost;
	private final int _elasticsearchPort;
	private final String _imageName;
	private final KubernetesClient _kubernetesClient =
		new KubernetesClientBuilder(
		).build();
	private final String _namespace;

}