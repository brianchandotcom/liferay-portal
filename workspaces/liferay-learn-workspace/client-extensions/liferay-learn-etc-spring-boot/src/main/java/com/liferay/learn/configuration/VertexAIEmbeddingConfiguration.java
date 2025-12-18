/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn.configuration;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;

import java.io.ByteArrayInputStream;

import java.util.List;

import org.springframework.ai.vertexai.embedding.VertexAiEmbeddingConnectionDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nilton Vieira
 */
@Configuration
public class VertexAIEmbeddingConfiguration {

	@Bean
	public VertexAiEmbeddingConnectionDetails
			vertexAiEmbeddingConnectionDetails()
		throws Exception {

		FixedCredentialsProvider fixedCredentialsProvider =
			FixedCredentialsProvider.create(
				GoogleCredentials.fromStream(
					new ByteArrayInputStream(_googleCredentials.getBytes())
				).createScoped(
					List.of("https://www.googleapis.com/auth/cloud-platform")
				));

		return new VertexAiEmbeddingConnectionDetails(
			_vertexAIEmbeddingProjectId, _vertexAIEmbeddingLocation, "google",
			PredictionServiceSettings.newBuilder(
			).setCredentialsProvider(
				fixedCredentialsProvider
			).build());
	}

	@Value("${liferay.learn.google.credentials}")
	private String _googleCredentials;

	@Value("${spring.ai.vertex.ai.embedding.location}")
	private String _vertexAIEmbeddingLocation;

	@Value("${spring.ai.vertex.ai.embedding.project-id}")
	private String _vertexAIEmbeddingProjectId;

}