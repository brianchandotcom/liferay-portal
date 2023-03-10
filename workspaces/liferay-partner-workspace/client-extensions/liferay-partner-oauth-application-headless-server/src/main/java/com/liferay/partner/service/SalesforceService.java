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

import com.liferay.portal.kernel.util.StringBundler;
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.CDL;
import org.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jair Medeiros
 * @author Thaynam Lázaro
 */
@Service
public class SalesforceService {

	public JSONArray getBulkObjects(String objectType, String[] objectFields)
		throws AsyncApiException, InterruptedException, IOException {

		JobInfo job = _createJob(objectType, _bulkConnection);

		BatchInfo batchInfo = _createBatch(
			objectType, objectFields, _bulkConnection, job);

		_closeJob(_bulkConnection, job.getId());
		_awaitCompletion(_bulkConnection, job, batchInfo);

		return _getResultJSONArray(_bulkConnection, job, batchInfo);
	}

	private void _awaitCompletion(
			BulkConnection connection, JobInfo job, BatchInfo batchInfo)
		throws AsyncApiException, InterruptedException {

		while (true) {
			BatchInfo batchInfoStatus = connection.getBatchInfo(
				job.getId(), batchInfo.getId());

			System.out.println(batchInfoStatus);

			if (batchInfoStatus.getState() == BatchStateEnum.Completed) {
				System.out.println("Batch Completed");

				break;
			}

			if (batchInfoStatus.getState() == BatchStateEnum.Failed) {
				throw new AsyncApiException();
			}

			Thread.sleep(1000);
		}
	}

	private void _closeJob(BulkConnection connection, String jobId)
		throws AsyncApiException {

		JobInfo job = new JobInfo();

		job.setId(jobId);
		job.setState(JobStateEnum.Closed);

		connection.updateJob(job);

		System.out.println("Job Closed");
	}

	private BatchInfo _createBatch(
			String objectType, String[] objectFields, BulkConnection connection,
			JobInfo jobInfo)
		throws AsyncApiException, IOException {

		String query = StringBundler.concat(
			"SELECT ", StringUtil.merge(objectFields, ", "), " FROM ",
			objectType);

		try (ByteArrayInputStream byteArrayInputStream =
				new ByteArrayInputStream(query.getBytes())) {

			return connection.createBatchFromStream(
				jobInfo, byteArrayInputStream);
		}
	}

	private JobInfo _createJob(String objectType, BulkConnection connection)
		throws AsyncApiException {

		JobInfo job = new JobInfo();

		job.setOperation(OperationEnum.query);
		job.setObject(objectType);
		job.setConcurrencyMode(ConcurrencyMode.Parallel);
		job.setContentType(ContentType.CSV);

		return connection.createJob(job);
	}

	private JSONArray _getResultJSONArray(
			BulkConnection connection, JobInfo job, BatchInfo batchInfo)
		throws AsyncApiException {

		QueryResultList queryResultList = connection.getQueryResultList(
			job.getId(), batchInfo.getId());

		String[] queryResults = queryResultList.getResult();

		JSONArray jsonArray = new JSONArray();

		for (String resultId : queryResults) {
			InputStreamReader inputStreamReader = new InputStreamReader(
				connection.getQueryResultStream(
					job.getId(), batchInfo.getId(), resultId));

			Stream<String> bufferedReaderLines = new BufferedReader(
				inputStreamReader
			).lines();

			jsonArray.put(
				CDL.toJSONArray(
					bufferedReaderLines.collect(Collectors.joining("\n"))));
		}

		return jsonArray;
	}

	@Autowired
	private BulkConnection _bulkConnection;

}