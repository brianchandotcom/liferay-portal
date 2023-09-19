package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortalPullRequestSFJobEntity extends BaseJobEntity {

	@Override
	public List<JSONObject> getInitialBuildJSONObjects() {
		List<JSONObject> initialBuildJSONObjects = new ArrayList<>();

		initialBuildJSONObjects.add(_getInitialBuildJSONObject());

		return initialBuildJSONObjects;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("pullRequestURL", getPullRequestURL());

		return jsonObject;
	}

	public URL getPullRequestURL() {
		if (_pullRequestURL != null) {
			return _pullRequestURL;
		}

		for (BuildEntity initialBuildEntity : getInitialBuildEntities()) {
			System.out.println("initialBuildEntity=" + initialBuildEntity);

			BuildParameterEntity buildParameterEntity =
				initialBuildEntity.getBuildParameterEntity("PULL_REQUEST_URL");

			System.out.println("buildParameterEntity=" + buildParameterEntity);

			if (buildParameterEntity != null) {
				_pullRequestURL = StringUtil.toURL(
					buildParameterEntity.getValue());

				return _pullRequestURL;
			}
		}

		return null;
	}

	protected PortalPullRequestSFJobEntity(JSONObject jsonObject) {
		super(jsonObject);

		_initialBuildParameters.put("BUILD_PRIORITY", _BUILD_PRIORITY);
		_initialBuildParameters.put(
			"PULL_REQUEST_URL", jsonObject.optString("pullRequestURL"));
	}

	private JSONObject _getInitialBuildJSONObject() {
		JSONObject initialBuildJSONObject = new JSONObject();

		initialBuildJSONObject.put(
			"buildParameters", _getInitialBuildParametersJSONArray()
		).put(
			"initialBuild", true
		).put(
			"jenkinsJobName", _JENKINS_JOB_NAME
		).put(
			"name", _JENKINS_JOB_NAME
		).put(
			"state", BuildEntity.State.OPENED
		);

		return initialBuildJSONObject;
	}

	private JSONArray _getInitialBuildParametersJSONArray() {
		JSONArray initialBuildParametersJSONArray = new JSONArray();

		for (Map.Entry<String, String> initialBuildParameter :
				_initialBuildParameters.entrySet()) {

			JSONObject initialBuildParameterJSONObject = new JSONObject();

			initialBuildParameterJSONObject.put(
				"name", initialBuildParameter.getKey()
			).put(
				"value", initialBuildParameter.getValue()
			);

			initialBuildParametersJSONArray.put(initialBuildParameterJSONObject);
		}

		return initialBuildParametersJSONArray;
	}

	private URL _pullRequestURL;

	private static final String _BUILD_PRIORITY = "4";
	private static final String _JENKINS_JOB_NAME = "test-portal-source-format";

	private final Map<String, String> _initialBuildParameters = new HashMap<>();

}
