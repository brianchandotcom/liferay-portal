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

		JobInfo jobInfo = new JobInfo();

		jobInfo.setOperation(OperationEnum.query);
		jobInfo.setObject(objectType);
		jobInfo.setConcurrencyMode(ConcurrencyMode.Parallel);
		jobInfo.setContentType(ContentType.CSV);

		jobInfo = _bulkConnection.createJob(jobInfo);

		BatchInfo batchInfo = _createBatch(
			objectType, objectFields, _bulkConnection, jobInfo);

		_closeJob(_bulkConnection, jobInfo.getId());
		_awaitCompletion(_bulkConnection, jobInfo, batchInfo);

		return _getResultJSONArray(_bulkConnection, jobInfo, batchInfo);
	}

	private void _awaitCompletion(
			BulkConnection connection, JobInfo job, BatchInfo batchInfo)
		throws Exception {

		while (true) {
			BatchInfo batchInfoStatus = connection.getBatchInfo(
				job.getId(), batchInfo.getId());

			if (batchInfoStatus.getState() == BatchStateEnum.Completed) {
				break;
			}

			if (batchInfoStatus.getState() == BatchStateEnum.Failed) {
				throw new AsyncApiException();
			}

			Thread.sleep(1000);
		}
	}

	private void _closeJob(BulkConnection connection, String jobId)
		throws Exception {

		JobInfo job = new JobInfo();

		job.setId(jobId);
		job.setState(JobStateEnum.Closed);

		connection.updateJob(job);
	}

	private BatchInfo _createBatch(
			String objectType, String[] objectFields, BulkConnection connection,
			JobInfo jobInfo)
		throws Exception {

		String query = StringBundler.concat(
			"SELECT ", StringUtil.merge(objectFields, ", "), " FROM ",
			objectType);

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(query.getBytes())) {

			return connection.createBatchFromStream(
				jobInfo, byteArrayInputStream);
		}
	}

	private JSONArray _getResultJSONArray(
			BulkConnection connection, JobInfo job, BatchInfo batchInfo)
		throws Exception {

		QueryResultList queryResultList = connection.getQueryResultList(
			job.getId(), batchInfo.getId());

		String[] queryResults = queryResultList.getResult();

		JSONArray jsonArray = new JSONArray();

		for (String resultId : queryResults) {
			JSONTokener jsonTokener = new JSONTokener(
				connection.getQueryResultStream(
					job.getId(), batchInfo.getId(), resultId));

			jsonArray = CDL.toJSONArray(jsonTokener);
		}

		return jsonArray;
	}

	@Autowired
	private BulkConnection _bulkConnection;

}