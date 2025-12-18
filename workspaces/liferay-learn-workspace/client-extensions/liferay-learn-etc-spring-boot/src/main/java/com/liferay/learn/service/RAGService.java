/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.OffsetDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nilton Vieira
 */
@Service
public class RAGService extends BaseService {

	public void addOrUpdateDocument(
		long assetEntryId, String assetEntryType, String content,
		String description, String friendlyUrlPath, String name) {

		Document document = new Document(
			content,
			HashMapBuilder.<String, Object>put(
				"assetEntryId", assetEntryId
			).put(
				"assetEntryType", assetEntryType
			).put(
				"dateModified", String.valueOf(OffsetDateTime.now())
			).put(
				"description", description
			).put(
				"friendlyUrlPath", friendlyUrlPath
			).put(
				"name", name
			).build());

		_qdrantVectorStore.delete("assetEntryId == '" + assetEntryId + "'");

		if (content.length() >= 3000) {
			TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();

			_qdrantVectorStore.doAdd(tokenTextSplitter.split(document));

			return;
		}

		_qdrantVectorStore.doAdd(ListUtil.fromArray(document));
	}

	public void deleteDocument(long assetEntryId) {
		_qdrantVectorStore.delete("assetEntryId == '" + assetEntryId + "'");
	}

	public Map<String, Object> search(String query) throws Exception {
		if (Validator.isNull(query)) {
			return _buildPayload(
				new ArrayList(), "No content was found for " + query);
		}

		List<Document> vectorStoreResult =
			_qdrantVectorStore.doSimilaritySearch(
				SearchRequest.builder(
				).query(
					query
				).topK(
					4
				).similarityThreshold(
					0.8
				).build());

		if (vectorStoreResult.isEmpty()) {
			return _buildPayload(
				new ArrayList(), "No content was found for " + query);
		}

		List<Long> assetEntryIds = new ArrayList<>();
		List<Map<String, Object>> references = new ArrayList<>();
		StringBundler sb = new StringBundler();

		for (Document document : vectorStoreResult) {
			sb.append(document.getText());
			sb.append(System.lineSeparator());

			Map<String, Object> metadata = document.getMetadata();

			if (assetEntryIds.contains(metadata.get("assetEntryId"))) {
				continue;
			}

			references.add(document.getMetadata());
			assetEntryIds.add(GetterUtil.getLong(metadata.get("assetEntryId")));
		}

		return _buildPayload(
			references,
			_chatClient.prompt(
			).user(
				StringUtil.replace(
					StringUtil.read(
						RAGService.class.getResourceAsStream("/prompt.md")),
					new String[] {"${documents}", "${query}"},
					new String[] {sb.toString(), query})
			).call(
			).content());
	}

	private Map<String, Object> _buildPayload(
		List<Map<String, Object>> references, String summary) {

		return HashMapBuilder.<String, Object>put(
			"references", references
		).put(
			"summary", summary
		).build();
	}

	@Autowired
	private ChatClient _chatClient;

	@Autowired
	private EmbeddingModel _embeddingModel;

	@Autowired
	private QdrantVectorStore _qdrantVectorStore;

}