/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.service;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.ScalableResource;
import io.fabric8.kubernetes.client.utils.Serialization;

import jakarta.annotation.PreDestroy;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Brooke Dalton
 */
@Service
public class KubernetesJobService {

	public KubernetesJobService(
		@Value("${liferay.seo.studio.crawler.elasticsearch.host}") String
			elasticsearchHost,
		@Value("${liferay.seo.studio.crawler.elasticsearch.port}") int
			elasticsearchPort,
		@Value("${liferay.seo.studio.crawler.k8s.image.name}") String imageName,
		@Value("${liferay.seo.studio.crawler.local.network.allowed}") boolean
			localNetworkAllowed,
		@Value("${liferay.seo.studio.crawler.k8s.namespace}") String
			namespace) {

		_elasticsearchHost = elasticsearchHost;
		_elasticsearchPort = elasticsearchPort;
		_imageName = imageName;
		_localNetworkAllowed = localNetworkAllowed;
		_namespace = namespace;

		_jobTemplate = _loadJobTemplate();
	}

	@PreDestroy
	public void close() {
		_kubernetesClient.close();
	}

	public Job createJob(
		long accountEntryId, String domainURL, int maxCrawlDepth,
		int maxDuration, String outputIndex, String sitemapURL) {

		Job job = _kubernetesClient.batch(
		).v1(
		).jobs(
		).inNamespace(
			_namespace
		).resource(
			new JobBuilder(
				_jobTemplate
			).editMetadata(
			).addToLabels(
				"account-entry-id", String.valueOf(accountEntryId)
			).endMetadata(
			).editSpec(
			).editTemplate(
			).editMetadata(
			).addToLabels(
				"account-entry-id", String.valueOf(accountEntryId)
			).endMetadata(
			).editSpec(
			).editFirstContainer(
			).withImage(
				_imageName
			).withEnv(
				_createEnvVar(
					"ACCOUNT_ENTRY_ID", String.valueOf(accountEntryId)),
				_createEnvVar("CRAWLER_DOMAIN_URL", domainURL),
				_createEnvVar(
					"CRAWLER_LOOPBACK_ALLOWED",
					String.valueOf(_localNetworkAllowed)),
				_createEnvVar(
					"CRAWLER_MAX_CRAWL_DEPTH", String.valueOf(maxCrawlDepth)),
				_createEnvVar(
					"CRAWLER_MAX_DURATION", String.valueOf(maxDuration)),
				_createEnvVar("CRAWLER_OUTPUT_INDEX", outputIndex),
				_createEnvVar(
					"CRAWLER_PRIVATE_NETWORKS_ALLOWED",
					String.valueOf(_localNetworkAllowed)),
				_createEnvVar("CRAWLER_SEED_URL", domainURL),
				_createEnvVar("CRAWLER_SITEMAP_URL", sitemapURL),
				_createEnvVar("ELASTICSEARCH_HOST", _elasticsearchHost),
				_createEnvVar(
					"ELASTICSEARCH_PORT", String.valueOf(_elasticsearchPort))
			).endContainer(
			).endSpec(
			).endTemplate(
			).endSpec(
			).build()
		).create();

		if (_log.isInfoEnabled()) {
			ObjectMeta objectMeta = job.getMetadata();

			_log.info("Kubernetes job dispatched: " + objectMeta.getName());
		}

		return job;
	}

	public Job getJob(String name) {
		ScalableResource<Job> scalableResource = _getJobScalableResource(name);

		return scalableResource.get();
	}

	private EnvVar _createEnvVar(String name, String value) {
		return new EnvVarBuilder(
		).withName(
			name
		).withValue(
			value
		).build();
	}

	private ScalableResource<Job> _getJobScalableResource(String name) {
		return _kubernetesClient.batch(
		).v1(
		).jobs(
		).inNamespace(
			_namespace
		).withName(
			name
		);
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
	private final Job _jobTemplate;
	private final KubernetesClient _kubernetesClient =
		new KubernetesClientBuilder(
		).build();
	private final boolean _localNetworkAllowed;
	private final String _namespace;

}