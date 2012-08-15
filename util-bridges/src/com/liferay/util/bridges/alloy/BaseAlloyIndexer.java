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

package com.liferay.util.bridges.alloy;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.BaseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseAlloyIndexer extends BaseIndexer {

	public BaseAlloyIndexer(
		String portletId, String className, String serviceClassName) {

		this.portletId = portletId;
		classNames = new String[] {className};

		String[] classNameParameters = StringUtil.split(
			className, StringPool.PERIOD);

		String methodName = classNameParameters[classNameParameters.length - 1];

		getModelMethodKey = new MethodKey(
			serviceClassName, "get" + methodName, long.class);
		getModelsCountMethodKey = new MethodKey(
			serviceClassName,
			"get" + TextFormatter.formatPlural(methodName) + "Count");
		getModelsMethodKey = new MethodKey(
			serviceClassName, "get" + TextFormatter.formatPlural(methodName),
			int.class, int.class);

		try {
			MethodCache.get(getModelMethodKey);
			MethodCache.get(getModelsCountMethodKey);
			MethodCache.get(getModelsMethodKey);
		}
		catch (Exception e) {
		}
	}

	public String[] getClassNames() {
		return classNames;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_ANY);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		BaseModel<?> baseModel = (BaseModel<?>)obj;

		Document document = new DocumentImpl();

		document.addUID(
			portletId, String.valueOf(baseModel.getPrimaryKeyObj()));

		AuditedModel auditedModel = (AuditedModel)obj;

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), auditedModel.getCompanyId(),
			document.get(Field.UID));
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		Document document = getDocument(obj);

		AuditedModel auditedModel = (AuditedModel)obj;

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), auditedModel.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		MethodHandler methodHandler = new MethodHandler(
			getModelMethodKey, classPK);

		Object model = methodHandler.invoke(false);

		doReindex(model);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexModels(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return portletId;
	}

	protected void reindexModels(long companyId) throws Exception {
		MethodHandler methodHandler = new MethodHandler(
			getModelsCountMethodKey);

		int count = (Integer)methodHandler.invoke(false);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reindexModels(companyId, start, end);
		}
	}

	protected void reindexModels(long companyId, int start, int end)
		throws Exception {

		MethodHandler methodHandler = new MethodHandler(
			getModelsMethodKey, start, end);

		List<Object> models = (List<Object>)methodHandler.invoke(false);

		if (models.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(models.size());

		for (Object model : models) {
			Document document = getDocument(model);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	protected String[] classNames;
	protected MethodKey getModelMethodKey;
	protected MethodKey getModelsCountMethodKey;
	protected MethodKey getModelsMethodKey;
	protected String portletId;

}