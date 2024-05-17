/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.testsuite.TestSuiteEntity;
import com.liferay.jethr0.util.EntityUtil;

import java.net.URL;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JobEntity extends Entity {

	public void addBuildEntities(Set<BuildEntity> buildEntities);

	public void addBuildEntity(BuildEntity buildEntity);

	public void addJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities);

	public void addJenkinsCohortEntity(JenkinsCohortEntity jenkinsCohortEntity);

	public void addTaskEntities(Set<TaskEntity> taskEntities);

	public void addTaskEntity(TaskEntity taskEntity);

	public void addTestSuiteEntities(Set<TestSuiteEntity> testSuiteEntities);

	public void addTestSuiteEntity(TestSuiteEntity testSuiteEntity);

	public boolean getBlessed();

	public Set<BuildEntity> getBuildEntities();

	public GitCommitEntity getGitCommitEntity();

	public long getGitCommitEntityId();

	public Set<BuildEntity> getInitialBuildEntities();

	public List<JSONObject> getInitialBuildJSONObjects();

	public URL getJenkinsBranchURL();

	public Set<JenkinsCohortEntity> getJenkinsCohortEntities();

	public String getName();

	public Map<String, String> getParameters();

	public String getParameterValue(String name);

	public int getPriority();

	public RoutineEntity getRoutineEntity();

	public long getRoutineEntityId();

	public Date getStartDate();

	public State getState();

	public Set<TaskEntity> getTaskEntities();

	public Set<TestSuiteEntity> getTestSuiteEntities();

	public Type getType();

	public void removeBuildEntities(Set<BuildEntity> buildEntities);

	public void removeBuildEntity(BuildEntity buildEntity);

	public void removeJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities);

	public void removeJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity);

	public void removeTaskEntities(Set<TaskEntity> taskEntities);

	public void removeTaskEntity(TaskEntity taskEntity);

	public void removeTestSuiteEntities(Set<TestSuiteEntity> testSuiteEntities);

	public void removeTestSuiteEntity(TestSuiteEntity testSuiteEntity);

	public void setBlessed(boolean blessed);

	public void setGitCommitEntity(GitCommitEntity gitCommitEntity);

	public void setJenkinsBranchURL(URL jenkinsGitHubURL);

	public void setName(String name);

	public void setParameters(Map<String, String> parameters);

	public void setParameterValue(String name, String value);

	public void setPriority(int priority);

	public void setRoutineEntity(RoutineEntity routineEntity);

	public void setStartDate(Date startDate);

	public void setState(State state);

	public enum State {

		BLOCKED("blocked", "Blocked"), COMPLETED("completed", "Completed"),
		OPENED("opened", "Opened"), QUEUED("queued", "Queued"),
		RUNNING("running", "Running");

		public static State get(Object picklistValue) {
			return _states.get(
				EntityUtil.getKeyFromPicklistValue(picklistValue));
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", getKey()
			).put(
				"name", getName()
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		public String getName() {
			return _name;
		}

		private State(String key, String name) {
			_key = key;
			_name = name;
		}

		private static final Map<String, State> _states = new HashMap<>();

		static {
			for (State state : values()) {
				_states.put(state.getKey(), state);
			}
		}

		private final String _key;
		private final String _name;

	}

	public enum Type {

		ARCHIVE_CI_BUILD_DATA("archiveCIBuildData", "Archive CI Build Data"),
		DEFAULT("default", "Default"),
		FILE_PROPAGATOR("filePropagator", "File Propagator"),
		FIXPACK_BUILDER_PULL_REQUEST(
			"fixpackBuilderPullRequest", "Fixpack Builder Pull Request"),
		FORWARD_PULL_REQUEST("forwardPullRequest", "Forward Pull Request"),
		GENERATE_CI_SYSTEM_HISTORY_REPORT(
			"generateCISystemHistoryReport",
			"Generate CI System History Report"),
		GENERATE_CI_SYSTEM_STATUS_REPORT(
			"generateCISystemStatusReport", "Generate CI System Status Report"),
		GENERATE_REPORTS("generateReports", "Generate Reports"),
		GENERATE_TEST_DURATION_METRICS(
			"generateTestDurationMetrics", "Generate Test Duration Metrics"),
		GENERATE_TESTRAY_CSV("generateTestrayCSV", "Generate Testray CSV"),
		JENKINS_PULL_REQUEST("jenkinsPullRequest", "Jenkins Pull Request"),
		LEGACY_DATABASE_DUMP("legacyDatabaseDump", "Legacy Database Dump"),
		LIFERAY_BINARIES_CACHE_UPDATER(
			"liferayBinariesCacheUpdater", "Liferay Binaries Cache Updater"),
		MAINTENANCE_DAILY("maintenanceDaily", "Maintenance Daily"),
		MAINTENANCE_MATRIX_JOBS(
			"maintenanceMatrixJobs", "Maintenance Matrix Jobs"),
		MAINTENANCE_STALE_ARTIFACTS(
			"maintenanceStaleArtifacts", "Maintenance Stale Artifacts"),
		MAINTENANCE_WEEKLY("maintenanceWeekly", "Maintenance Weekly"),
		MAINTENANCE_WEEKLY_NODE(
			"maintenanceWeeklyNode", "Maintenance Weekly Node"),
		MERGE_CENTRAL_SUBREPOSITORY(
			"mergeCentralSubrepository", "Merge Central Subrepository"),
		MERGE_PORTAL_SUBREPOSITORY(
			"mergePortalSubrepository", "Merge Portal Subrepository"),
		MIRRORS_LOCAL_CACHE_PROPAGATOR(
			"mirrorsLocalCachePropagator", "Mirrors Local Cache Propagator"),
		PLUGINS_EXTRA_APPS("pluginsExtraApps", "Plugins Extra Apps"),
		PLUGINS_MARKETPLACE_APP(
			"pluginsMarketplaceApp", "Plugins Marketplace App"),
		PLUGINS_PULL_REQUEST("pluginsPullRequest", "Plugins Pull Request"),
		PLUGINS_RELEASE("pluginsRelease", "Plugins Release"),
		PLUGINS_UPSTREAM("pluginsUpstream", "Plugins Upstream"),
		PORTAL_APP_RELEASE("portalAppRelease", "Portal App Release"),
		PORTAL_BUILD_OPTIMIZATION(
			"portalBuildOptimization", "Portal Build Optimization"),
		PORTAL_EVALUATE_PULL_REQUEST(
			"portalEvaluatePullRequest", "Portal Evaluate Pull Request"),
		PORTAL_FIXPACK_RELEASE(
			"portalFixpackRelease", "Portal Fixpack Release"),
		PORTAL_HOTFIX_RELEASE("portalHotfixRelease", "Portal Hotfix Release"),
		PORTAL_PULL_REQUEST("portalPullRequest", "Portal Pull Request"),
		PORTAL_PULL_REQUEST_SF("portalPullRequestSF", "Portal Pull Request SF"),
		PORTAL_RELEASE("portalRelease", "Portal Release"),
		PORTAL_UPSTREAM("portalUpstream", "Portal Upstream"),
		PORTAL_UPSTREAM_ACCEPTANCE(
			"portalUpstreamAcceptance", "Portal Upstream Acceptance"),
		PORTAL_UPSTREAM_TEST_SUITE(
			"portalUpstreamTestSuite", "Portal Upstream Test Suite"),
		POSHI_RELEASE("poshiRelease", "Poshi Release"),
		PUBLISH_PORTAL_DOCKER_IMAGE(
			"publishPortalDockerImage", "Publish Portal Docker Image"),
		PUBLISH_TESTRAY_REPORT(
			"publishTestrayReport", "Publish Testray Report"),
		QA_WEBSITES_DAILY("qaWebsitesDaily", "QA Websites Daily"),
		QA_WEBSITES_PULL_REQUEST_SF(
			"qaWebsitesPullRequestSF", "QA Websites Pull Request SF"),
		QA_WEBSITES_WEEKLY("qaWebsitesWeekly", "QA Websites Weekly"),
		REPOSITORY_ARCHIVE("repositoryArchive", "Repository Archive"),
		ROOT_CAUSE_ANALYSIS_TOOL(
			"rootCauseAnalysisTool", "Root Cause Analysis Tool"),
		SANITIZE_LANGUAGE("sanitizeLanguage", "Sanitize Language"),
		SCANCODE_PIPELINES("scancodePipelines", "Scancode Pipelines"),
		SUBREPOSITORY_PULL_REQUEST(
			"subrepositoryPullRequest", "Subrepository Pull Request"),
		VERIFICATION("verification", "Verification"),
		VERIFICATION_NODE("verificationNode", "Verification Node"),
		VERIFICATION_SERVERS_DAILY(
			"verificationServersDaily", "verificationServersDaily");

		public static Type get(Object picklistValue) {
			return _types.get(
				EntityUtil.getKeyFromPicklistValue(picklistValue));
		}

		public static Set<String> getKeys() {
			return _types.keySet();
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", getKey()
			).put(
				"name", getName()
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		public String getName() {
			return _name;
		}

		private Type(String key, String name) {
			_key = key;
			_name = name;
		}

		private static final Map<String, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getKey(), type);
			}
		}

		private final String _key;
		private final String _name;

	}

}