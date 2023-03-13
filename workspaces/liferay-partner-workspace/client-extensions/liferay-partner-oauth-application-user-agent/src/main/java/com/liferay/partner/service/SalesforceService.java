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

package com.liferay.partner.service;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import com.sforce.async.AsyncApiException;
import com.sforce.async.BatchInfo;
import com.sforce.async.BatchStateEnum;
import com.sforce.async.BulkConnection;
import com.sforce.async.ConcurrencyMode;
import com.sforce.async.ContentType;
import com.sforce.async.JobInfo;
import com.sforce.async.JobStateEnum;
import com.sforce.async.OperationEnum;
import com.sforce.async.QueryResultList;

import java.io.ByteArrayInputStream;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONTokener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jair Medeiros
 */
@Service
public class SalesforceService {

	public JSONArray getBulkObjects(String objectType, String[] objectFields)
		throws Exception {

		JobInfo jobInfo1 = new JobInfo();

		jobInfo1.setOperation(OperationEnum.query);
		jobInfo1.setObject(objectType);
		jobInfo1.setConcurrencyMode(ConcurrencyMode.Parallel);
		jobInfo1.setContentType(ContentType.CSV);

		jobInfo1 = _bulkConnection.createJob(jobInfo1);

		String query = StringBundler.concat(
			"SELECT ", StringUtil.merge(objectFields, ", "), " FROM ",
			objectType);

		BatchInfo batchInfo;

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(query.getBytes())) {

			batchInfo = _bulkConnection.createBatchFromStream(
				jobInfo1, byteArrayInputStream);
		}

		JobInfo jobInfo2 = new JobInfo();

		jobInfo2.setId(jobInfo1.getId());
		jobInfo2.setState(JobStateEnum.Closed);

		_bulkConnection.updateJob(jobInfo2);

		_awaitCompletion(_bulkConnection, jobInfo2.getId(), batchInfo);

		return _getResultJSONArray(
			_bulkConnection, jobInfo2.getId(), batchInfo);
	}

	private void _awaitCompletion(
			BulkConnection connection, String jobId, BatchInfo batchInfo)
		throws Exception {

		while (true) {
			BatchInfo batchInfoStatus = connection.getBatchInfo(
				jobId, batchInfo.getId());

			if (batchInfoStatus.getState() == BatchStateEnum.Completed) {
				break;
			}

			if (batchInfoStatus.getState() == BatchStateEnum.Failed) {
				throw new AsyncApiException();
			}

			Thread.sleep(1000);
		}
	}

	private JSONArray _getResultJSONArray(
			BulkConnection connection, String jobId, BatchInfo batchInfo)
		throws Exception {

		QueryResultList queryResultList = connection.getQueryResultList(
			jobId, batchInfo.getId());

		String[] queryResults = queryResultList.getResult();

		JSONArray jsonArray = new JSONArray();

		for (String resultId : queryResults) {
			JSONTokener jsonTokener = new JSONTokener(
				connection.getQueryResultStream(
					jobId, batchInfo.getId(), resultId));

			jsonArray = CDL.toJSONArray(jsonTokener);
		}

		return jsonArray;
	}

	@Autowired
	private BulkConnection _bulkConnection;

}