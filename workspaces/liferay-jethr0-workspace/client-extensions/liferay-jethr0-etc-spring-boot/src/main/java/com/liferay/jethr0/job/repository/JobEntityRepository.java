/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.repository;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.git.pullrequest.GitPullRequestEntity;
import com.liferay.jethr0.git.repository.GitPullRequestEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.job.dalo.JobEntityDALO;
import com.liferay.jethr0.job.dalo.JobToBuildsEntityRelationshipDALO;
import com.liferay.jethr0.job.queue.JobQueue;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.routine.UpstreamBranchRoutineEntity;
import com.liferay.jethr0.routine.repository.RoutineEntityRepository;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Michael Hashimoto
 */
@Configuration
@EnableScheduling
public class JobEntityRepository extends BaseEntityRepository<JobEntity> {

	@Override
	public JobEntity add(JSONObject jsonObject) {
		JobEntity jobEntity = super.add(jsonObject);

		if (!(jobEntity instanceof PullRequestJobEntity)) {
			return jobEntity;
		}

		PullRequestJobEntity pullRequestJobEntity =
			(PullRequestJobEntity)jobEntity;

		Object parametersObject = jsonObject.opt("parameters");

		if (parametersObject != null) {
			jsonObject.put("parameters", String.valueOf(parametersObject));

			GitPullRequestEntity gitPullRequestEntity =
				_createGitPullRequestEntity(parametersObject);

			if (gitPullRequestEntity != null) {
				jsonObject.put(
					"r_gitPullRequestToJobs_c_gitPullRequestId",
					gitPullRequestEntity.getId());

				pullRequestJobEntity.setGitPullRequestEntity(
					gitPullRequestEntity);

				update(pullRequestJobEntity);
			}
		}

		return pullRequestJobEntity;
	}

	@Override
	public JobEntity create(JSONObject jsonObject) {
		Object parametersObject = jsonObject.opt("parameters");

		if (parametersObject != null) {
			jsonObject.put("parameters", String.valueOf(parametersObject));

			GitPullRequestEntity gitPullRequestEntity =
				_createGitPullRequestEntity(parametersObject);

			if (gitPullRequestEntity != null) {
				jsonObject.put(
					"r_gitPullRequestToJobs_c_gitPullRequestId",
					gitPullRequestEntity.getId());
			}
		}

		return super.create(jsonObject);
	}

	public JobEntity create(
		RoutineEntity routineEntity, String name,
		Map<String, String> parameters, int priority, Date startDate,
		JobEntity.State state, JobEntity.Type type) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		if (parameters != null) {
			JSONArray parametersJSONArray = new JSONArray();

			for (Map.Entry<String, String> parameter : parameters.entrySet()) {
				JSONObject parameterJSONObject = new JSONObject();

				parameterJSONObject.put(
					"key", parameter.getKey()
				).put(
					"value", parameter.getValue()
				);

				parametersJSONArray.put(parameterJSONObject);
			}

			jsonObject.put("parameters", parametersJSONArray.toString());
		}

		GitPullRequestEntity gitPullRequestEntity = _createGitPullRequestEntity(
			parameters);

		if (gitPullRequestEntity != null) {
			jsonObject.put(
				"r_gitPullRequestToJobs_c_gitPullRequestId",
				gitPullRequestEntity.getId());
		}

		jsonObject.put("priority", priority);

		if (routineEntity != null) {
			jsonObject.put(
				"r_routineToJobs_c_routineId", routineEntity.getId());

			if (routineEntity instanceof UpstreamBranchRoutineEntity) {
				UpstreamBranchRoutineEntity upstreamBranchRoutineEntity =
					(UpstreamBranchRoutineEntity)routineEntity;

				GitCommitEntity previousGitCommitEntity =
					upstreamBranchRoutineEntity.getPreviousGitCommitEntity();

				jsonObject.put(
					"r_gitCommitToJobs_c_gitCommitId",
					previousGitCommitEntity.getId());
			}
		}

		if (startDate != null) {
			jsonObject.put("startDate", StringUtil.toString(startDate));
		}

		if (state == null) {
			state = JobEntity.State.OPENED;
		}

		jsonObject.put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return create(jsonObject);
	}

	public Set<JobEntity> getByState(JobEntity.State... states) {
		Set<JobEntity> jobEntities = new HashSet<>();

		for (JobEntity jobEntity : getAll()) {
			for (JobEntity.State state : states) {
				if (state == jobEntity.getState()) {
					jobEntities.add(jobEntity);
				}
			}
		}

		return jobEntities;
	}

	@Override
	public JobEntityDALO getEntityDALO() {
		return _jobEntityDALO;
	}

	public JobQueue getJobQueue() {
		return _jobQueue;
	}

	@Override
	public void initialize() {
		Date date = new Date(System.currentTimeMillis() - _getJobArchiveAge());

		addAll(_jobEntityDALO.getAllAfterModifiedDate(date));

		addAll(
			_jobEntityDALO.getJobsByState(
				JobEntity.State.OPENED, JobEntity.State.QUEUED,
				JobEntity.State.RUNNING));
	}

	@Override
	public synchronized void initializeRelationships() {
	}

	public void relateJobToBuild(JobEntity jobEntity, BuildEntity buildEntity) {
		jobEntity.addBuildEntity(buildEntity);

		buildEntity.setJobEntity(jobEntity);
	}

	@Override
	public void remove(JobEntity jobEntity) {
		JobQueue jobQueue = getJobQueue();

		jobQueue.removeJobEntities(Collections.singleton(jobEntity));

		super.remove(jobEntity);
	}

	@Override
	public void remove(Set<JobEntity> jobEntities) {
		JobQueue jobQueue = getJobQueue();

		jobQueue.removeJobEntities(jobEntities);

		super.remove(jobEntities);
	}

	@Scheduled(cron = "${liferay.jethr0.job.archive.cron}")
	public void scheduledArchive() {
		Date date = new Date(System.currentTimeMillis() - _getJobArchiveAge());

		Set<Long> jobEntityIds = new HashSet<>();

		for (JobEntity jobEntity : getAll()) {
			if (date.before(jobEntity.getModifiedDate()) ||
				(jobEntity.getState() != JobEntity.State.COMPLETED)) {

				continue;
			}

			jobEntityIds.add(jobEntity.getId());
		}

		Map<Long, JobEntity> entitiesMap = getEntitiesMap();

		long jobCount = entitiesMap.size();

		for (Long jobEntityId : jobEntityIds) {
			entitiesMap.remove(jobEntityId);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Archived ", jobEntityIds.size(), " of ", jobCount,
					" jobs"));
		}
	}

	public void setBuildEntityRepository(
		BuildEntityRepository buildEntityRepository) {

		_buildEntityRepository = buildEntityRepository;
	}

	public void setGitPullRequestEntityRepository(
		GitPullRequestEntityRepository gitPullRequestEntityRepository) {

		_gitPullRequestEntityRepository = gitPullRequestEntityRepository;
	}

	public void setJobQueue(JobQueue jobQueue) {
		_jobQueue = jobQueue;
	}

	public void setRoutineEntityRepository(
		RoutineEntityRepository routineEntityRepository) {

		_routineEntityRepository = routineEntityRepository;
	}

	@Override
	protected JobEntity updateRelationshipsFromDALO(JobEntity jobEntity) {
		long routineEntityId = jobEntity.getRoutineEntityId();

		if (routineEntityId > 0) {
			_routineEntityRepository.relateRoutineToJob(
				_routineEntityRepository.getById(routineEntityId), jobEntity);
		}

		return _updateJobToBuildsRelationshipsFromDALO(jobEntity);
	}

	@Override
	protected JobEntity updateRelationshipsToDALO(JobEntity jobEntity) {
		_jobToBuildsEntityRelationshipDALO.updateChildEntities(jobEntity);

		return jobEntity;
	}

	private GitPullRequestEntity _createGitPullRequestEntity(
		Map<String, String> parameters) {

		URL pullRequestURL = _getParameterValue(parameters, "pullRequestURL");

		if (pullRequestURL == null) {
			return null;
		}

		return _gitPullRequestEntityRepository.create(pullRequestURL);
	}

	private GitPullRequestEntity _createGitPullRequestEntity(
		Object parametersObject) {

		return _createGitPullRequestEntity(_getParameters(parametersObject));
	}

	private long _getJobArchiveAge() {
		return Long.valueOf(_jobArchiveAgeInDays) * 1000 * 60 * 60 * 24;
	}

	private Map<String, String> _getParameters(Object parametersObject) {
		Map<String, String> parameters = new HashMap<>();

		if (parametersObject == null) {
			return parameters;
		}

		JSONArray parametersJSONArray = new JSONArray(
			String.valueOf(parametersObject));

		for (int i = 0; i < parametersJSONArray.length(); i++) {
			JSONObject parameterJSONObject = parametersJSONArray.getJSONObject(
				i);

			parameters.put(
				parameterJSONObject.getString("key"),
				parameterJSONObject.getString("value"));
		}

		return parameters;
	}

	private URL _getParameterValue(
		Map<String, String> parameters, String parameterKey) {

		if (parameters == null) {
			return null;
		}

		for (Map.Entry<String, String> parameter : parameters.entrySet()) {
			String key = parameter.getKey();

			if (key.equals(parameterKey)) {
				return StringUtil.toURL(parameter.getValue());
			}
		}

		return null;
	}

	private JobEntity _updateJobToBuildsRelationshipsFromDALO(
		JobEntity parentJobEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentJobEntity, _jobToBuildsEntityRelationshipDALO,
			_buildEntityRepository,
			(jobEntity, buildEntity) -> relateJobToBuild(
				jobEntity, buildEntity),
			jobEntity -> jobEntity.getBuildEntities(),
			(jobEntity, buildEntity) -> jobEntity.removeBuildEntity(
				buildEntity));
	}

	private static final Log _log = LogFactory.getLog(
		JobEntityRepository.class);

	private BuildEntityRepository _buildEntityRepository;
	private GitPullRequestEntityRepository _gitPullRequestEntityRepository;

	@Value("${JETHR0_JOB_ARCHIVE_AGE_IN_DAYS:7}")
	private String _jobArchiveAgeInDays;

	@Autowired
	private JobEntityDALO _jobEntityDALO;

	private JobQueue _jobQueue;

	@Autowired
	private JobToBuildsEntityRelationshipDALO
		_jobToBuildsEntityRelationshipDALO;

	private RoutineEntityRepository _routineEntityRepository;

}