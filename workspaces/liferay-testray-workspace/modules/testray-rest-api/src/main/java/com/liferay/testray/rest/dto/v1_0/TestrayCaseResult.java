package com.liferay.testray.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Nilton Vieira
 * @generated
 */
@Generated("")
@GraphQLName("TestrayCaseResult")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayCaseResult")
public class TestrayCaseResult implements Serializable {

	public static TestrayCaseResult toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayCaseResult.class, json);
	}

	public static TestrayCaseResult unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(TestrayCaseResult.class, json);
	}

	@Schema
	public String getComment() {
		if (_commentSupplier != null) {
			comment = _commentSupplier.get();

			_commentSupplier = null;
		}

		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;

		_commentSupplier = null;
	}

	@JsonIgnore
	public void setComment(
		UnsafeSupplier<String, Exception> commentUnsafeSupplier) {

		_commentSupplier = () -> {
			try {
				return commentUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String comment;

	@JsonIgnore
	private Supplier<String> _commentSupplier;

	@Schema
	public Long getDuration() {
		if (_durationSupplier != null) {
			duration = _durationSupplier.get();

			_durationSupplier = null;
		}

		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;

		_durationSupplier = null;
	}

	@JsonIgnore
	public void setDuration(
		UnsafeSupplier<Long, Exception> durationUnsafeSupplier) {

		_durationSupplier = () -> {
			try {
				return durationUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long duration;

	@JsonIgnore
	private Supplier<Long> _durationSupplier;

	@Schema
	public String getError() {
		if (_errorSupplier != null) {
			error = _errorSupplier.get();

			_errorSupplier = null;
		}

		return error;
	}

	public void setError(String error) {
		this.error = error;

		_errorSupplier = null;
	}

	@JsonIgnore
	public void setError(
		UnsafeSupplier<String, Exception> errorUnsafeSupplier) {

		_errorSupplier = () -> {
			try {
				return errorUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String error;

	@JsonIgnore
	private Supplier<String> _errorSupplier;

	@Schema
	public String getExecutionDate() {
		if (_executionDateSupplier != null) {
			executionDate = _executionDateSupplier.get();

			_executionDateSupplier = null;
		}

		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;

		_executionDateSupplier = null;
	}

	@JsonIgnore
	public void setExecutionDate(
		UnsafeSupplier<String, Exception> executionDateUnsafeSupplier) {

		_executionDateSupplier = () -> {
			try {
				return executionDateUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String executionDate;

	@JsonIgnore
	private Supplier<String> _executionDateSupplier;

	@Schema
	public Boolean getFlaky() {
		if (_flakySupplier != null) {
			flaky = _flakySupplier.get();

			_flakySupplier = null;
		}

		return flaky;
	}

	public void setFlaky(Boolean flaky) {
		this.flaky = flaky;

		_flakySupplier = null;
	}

	@JsonIgnore
	public void setFlaky(
		UnsafeSupplier<Boolean, Exception> flakyUnsafeSupplier) {

		_flakySupplier = () -> {
			try {
				return flakyUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean flaky;

	@JsonIgnore
	private Supplier<Boolean> _flakySupplier;

	@Schema
	public String getGitHash() {
		if (_gitHashSupplier != null) {
			gitHash = _gitHashSupplier.get();

			_gitHashSupplier = null;
		}

		return gitHash;
	}

	public void setGitHash(String gitHash) {
		this.gitHash = gitHash;

		_gitHashSupplier = null;
	}

	@JsonIgnore
	public void setGitHash(
		UnsafeSupplier<String, Exception> gitHashUnsafeSupplier) {

		_gitHashSupplier = () -> {
			try {
				return gitHashUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String gitHash;

	@JsonIgnore
	private Supplier<String> _gitHashSupplier;

	@Schema
	public String getIssues() {
		if (_issuesSupplier != null) {
			issues = _issuesSupplier.get();

			_issuesSupplier = null;
		}

		return issues;
	}

	public void setIssues(String issues) {
		this.issues = issues;

		_issuesSupplier = null;
	}

	@JsonIgnore
	public void setIssues(
		UnsafeSupplier<String, Exception> issuesUnsafeSupplier) {

		_issuesSupplier = () -> {
			try {
				return issuesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String issues;

	@JsonIgnore
	private Supplier<String> _issuesSupplier;

	@Schema
	public Long getPriority() {
		if (_prioritySupplier != null) {
			priority = _prioritySupplier.get();

			_prioritySupplier = null;
		}

		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;

		_prioritySupplier = null;
	}

	@JsonIgnore
	public void setPriority(
		UnsafeSupplier<Long, Exception> priorityUnsafeSupplier) {

		_prioritySupplier = () -> {
			try {
				return priorityUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long priority;

	@JsonIgnore
	private Supplier<Long> _prioritySupplier;

	@Schema
	public String getStatus() {
		if (_statusSupplier != null) {
			status = _statusSupplier.get();

			_statusSupplier = null;
		}

		return status;
	}

	public void setStatus(String status) {
		this.status = status;

		_statusSupplier = null;
	}

	@JsonIgnore
	public void setStatus(
		UnsafeSupplier<String, Exception> statusUnsafeSupplier) {

		_statusSupplier = () -> {
			try {
				return statusUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String status;

	@JsonIgnore
	private Supplier<String> _statusSupplier;

	@Schema
	public Long getTestrayBuildId() {
		if (_testrayBuildIdSupplier != null) {
			testrayBuildId = _testrayBuildIdSupplier.get();

			_testrayBuildIdSupplier = null;
		}

		return testrayBuildId;
	}

	public void setTestrayBuildId(Long testrayBuildId) {
		this.testrayBuildId = testrayBuildId;

		_testrayBuildIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayBuildId(
		UnsafeSupplier<Long, Exception> testrayBuildIdUnsafeSupplier) {

		_testrayBuildIdSupplier = () -> {
			try {
				return testrayBuildIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long testrayBuildId;

	@JsonIgnore
	private Supplier<Long> _testrayBuildIdSupplier;

	@Schema
	public String getTestrayCaseName() {
		if (_testrayCaseNameSupplier != null) {
			testrayCaseName = _testrayCaseNameSupplier.get();

			_testrayCaseNameSupplier = null;
		}

		return testrayCaseName;
	}

	public void setTestrayCaseName(String testrayCaseName) {
		this.testrayCaseName = testrayCaseName;

		_testrayCaseNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseName(
		UnsafeSupplier<String, Exception> testrayCaseNameUnsafeSupplier) {

		_testrayCaseNameSupplier = () -> {
			try {
				return testrayCaseNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String testrayCaseName;

	@JsonIgnore
	private Supplier<String> _testrayCaseNameSupplier;

	@Schema
	public Long getTestrayCaseResultId() {
		if (_testrayCaseResultIdSupplier != null) {
			testrayCaseResultId = _testrayCaseResultIdSupplier.get();

			_testrayCaseResultIdSupplier = null;
		}

		return testrayCaseResultId;
	}

	public void setTestrayCaseResultId(Long testrayCaseResultId) {
		this.testrayCaseResultId = testrayCaseResultId;

		_testrayCaseResultIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseResultId(
		UnsafeSupplier<Long, Exception> testrayCaseResultIdUnsafeSupplier) {

		_testrayCaseResultIdSupplier = () -> {
			try {
				return testrayCaseResultIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long testrayCaseResultId;

	@JsonIgnore
	private Supplier<Long> _testrayCaseResultIdSupplier;

	@Schema
	public String getTestrayCaseTypeName() {
		if (_testrayCaseTypeNameSupplier != null) {
			testrayCaseTypeName = _testrayCaseTypeNameSupplier.get();

			_testrayCaseTypeNameSupplier = null;
		}

		return testrayCaseTypeName;
	}

	public void setTestrayCaseTypeName(String testrayCaseTypeName) {
		this.testrayCaseTypeName = testrayCaseTypeName;

		_testrayCaseTypeNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayCaseTypeName(
		UnsafeSupplier<String, Exception> testrayCaseTypeNameUnsafeSupplier) {

		_testrayCaseTypeNameSupplier = () -> {
			try {
				return testrayCaseTypeNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String testrayCaseTypeName;

	@JsonIgnore
	private Supplier<String> _testrayCaseTypeNameSupplier;

	@Schema
	public String getTestrayComponentName() {
		if (_testrayComponentNameSupplier != null) {
			testrayComponentName = _testrayComponentNameSupplier.get();

			_testrayComponentNameSupplier = null;
		}

		return testrayComponentName;
	}

	public void setTestrayComponentName(String testrayComponentName) {
		this.testrayComponentName = testrayComponentName;

		_testrayComponentNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayComponentName(
		UnsafeSupplier<String, Exception> testrayComponentNameUnsafeSupplier) {

		_testrayComponentNameSupplier = () -> {
			try {
				return testrayComponentNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String testrayComponentName;

	@JsonIgnore
	private Supplier<String> _testrayComponentNameSupplier;

	@Schema
	public String getTestrayProductVersionName() {
		if (_testrayProductVersionNameSupplier != null) {
			testrayProductVersionName =
				_testrayProductVersionNameSupplier.get();

			_testrayProductVersionNameSupplier = null;
		}

		return testrayProductVersionName;
	}

	public void setTestrayProductVersionName(String testrayProductVersionName) {
		this.testrayProductVersionName = testrayProductVersionName;

		_testrayProductVersionNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayProductVersionName(
		UnsafeSupplier<String, Exception>
			testrayProductVersionNameUnsafeSupplier) {

		_testrayProductVersionNameSupplier = () -> {
			try {
				return testrayProductVersionNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String testrayProductVersionName;

	@JsonIgnore
	private Supplier<String> _testrayProductVersionNameSupplier;

	@Schema
	public Long getTestrayRoutineId() {
		if (_testrayRoutineIdSupplier != null) {
			testrayRoutineId = _testrayRoutineIdSupplier.get();

			_testrayRoutineIdSupplier = null;
		}

		return testrayRoutineId;
	}

	public void setTestrayRoutineId(Long testrayRoutineId) {
		this.testrayRoutineId = testrayRoutineId;

		_testrayRoutineIdSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRoutineId(
		UnsafeSupplier<Long, Exception> testrayRoutineIdUnsafeSupplier) {

		_testrayRoutineIdSupplier = () -> {
			try {
				return testrayRoutineIdUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long testrayRoutineId;

	@JsonIgnore
	private Supplier<Long> _testrayRoutineIdSupplier;

	@Schema
	public String getTestrayRoutineName() {
		if (_testrayRoutineNameSupplier != null) {
			testrayRoutineName = _testrayRoutineNameSupplier.get();

			_testrayRoutineNameSupplier = null;
		}

		return testrayRoutineName;
	}

	public void setTestrayRoutineName(String testrayRoutineName) {
		this.testrayRoutineName = testrayRoutineName;

		_testrayRoutineNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRoutineName(
		UnsafeSupplier<String, Exception> testrayRoutineNameUnsafeSupplier) {

		_testrayRoutineNameSupplier = () -> {
			try {
				return testrayRoutineNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String testrayRoutineName;

	@JsonIgnore
	private Supplier<String> _testrayRoutineNameSupplier;

	@Schema
	public String getTestrayRunName() {
		if (_testrayRunNameSupplier != null) {
			testrayRunName = _testrayRunNameSupplier.get();

			_testrayRunNameSupplier = null;
		}

		return testrayRunName;
	}

	public void setTestrayRunName(String testrayRunName) {
		this.testrayRunName = testrayRunName;

		_testrayRunNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRunName(
		UnsafeSupplier<String, Exception> testrayRunNameUnsafeSupplier) {

		_testrayRunNameSupplier = () -> {
			try {
				return testrayRunNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String testrayRunName;

	@JsonIgnore
	private Supplier<String> _testrayRunNameSupplier;

	@Schema
	public Long getTestrayRunNumber() {
		if (_testrayRunNumberSupplier != null) {
			testrayRunNumber = _testrayRunNumberSupplier.get();

			_testrayRunNumberSupplier = null;
		}

		return testrayRunNumber;
	}

	public void setTestrayRunNumber(Long testrayRunNumber) {
		this.testrayRunNumber = testrayRunNumber;

		_testrayRunNumberSupplier = null;
	}

	@JsonIgnore
	public void setTestrayRunNumber(
		UnsafeSupplier<Long, Exception> testrayRunNumberUnsafeSupplier) {

		_testrayRunNumberSupplier = () -> {
			try {
				return testrayRunNumberUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long testrayRunNumber;

	@JsonIgnore
	private Supplier<Long> _testrayRunNumberSupplier;

	@Schema
	public String getTestrayTeamName() {
		if (_testrayTeamNameSupplier != null) {
			testrayTeamName = _testrayTeamNameSupplier.get();

			_testrayTeamNameSupplier = null;
		}

		return testrayTeamName;
	}

	public void setTestrayTeamName(String testrayTeamName) {
		this.testrayTeamName = testrayTeamName;

		_testrayTeamNameSupplier = null;
	}

	@JsonIgnore
	public void setTestrayTeamName(
		UnsafeSupplier<String, Exception> testrayTeamNameUnsafeSupplier) {

		_testrayTeamNameSupplier = () -> {
			try {
				return testrayTeamNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String testrayTeamName;

	@JsonIgnore
	private Supplier<String> _testrayTeamNameSupplier;

	@Schema
	public String getUserName() {
		if (_userNameSupplier != null) {
			userName = _userNameSupplier.get();

			_userNameSupplier = null;
		}

		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;

		_userNameSupplier = null;
	}

	@JsonIgnore
	public void setUserName(
		UnsafeSupplier<String, Exception> userNameUnsafeSupplier) {

		_userNameSupplier = () -> {
			try {
				return userNameUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String userName;

	@JsonIgnore
	private Supplier<String> _userNameSupplier;

	@Schema
	public String getUserPortraitUrl() {
		if (_userPortraitUrlSupplier != null) {
			userPortraitUrl = _userPortraitUrlSupplier.get();

			_userPortraitUrlSupplier = null;
		}

		return userPortraitUrl;
	}

	public void setUserPortraitUrl(String userPortraitUrl) {
		this.userPortraitUrl = userPortraitUrl;

		_userPortraitUrlSupplier = null;
	}

	@JsonIgnore
	public void setUserPortraitUrl(
		UnsafeSupplier<String, Exception> userPortraitUrlUnsafeSupplier) {

		_userPortraitUrlSupplier = () -> {
			try {
				return userPortraitUrlUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String userPortraitUrl;

	@JsonIgnore
	private Supplier<String> _userPortraitUrlSupplier;

	@Schema
	public Integer getWarning() {
		if (_warningSupplier != null) {
			warning = _warningSupplier.get();

			_warningSupplier = null;
		}

		return warning;
	}

	public void setWarning(Integer warning) {
		this.warning = warning;

		_warningSupplier = null;
	}

	@JsonIgnore
	public void setWarning(
		UnsafeSupplier<Integer, Exception> warningUnsafeSupplier) {

		_warningSupplier = () -> {
			try {
				return warningUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer warning;

	@JsonIgnore
	private Supplier<Integer> _warningSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayCaseResult)) {
			return false;
		}

		TestrayCaseResult testrayCaseResult = (TestrayCaseResult)object;

		return Objects.equals(toString(), testrayCaseResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String comment = getComment();

		if (comment != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"comment\": ");

			sb.append("\"");

			sb.append(_escape(comment));

			sb.append("\"");
		}

		Long duration = getDuration();

		if (duration != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"duration\": ");

			sb.append(duration);
		}

		String error = getError();

		if (error != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"error\": ");

			sb.append("\"");

			sb.append(_escape(error));

			sb.append("\"");
		}

		String executionDate = getExecutionDate();

		if (executionDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"executionDate\": ");

			sb.append("\"");

			sb.append(_escape(executionDate));

			sb.append("\"");
		}

		Boolean flaky = getFlaky();

		if (flaky != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"flaky\": ");

			sb.append(flaky);
		}

		String gitHash = getGitHash();

		if (gitHash != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"gitHash\": ");

			sb.append("\"");

			sb.append(_escape(gitHash));

			sb.append("\"");
		}

		String issues = getIssues();

		if (issues != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"issues\": ");

			sb.append("\"");

			sb.append(_escape(issues));

			sb.append("\"");
		}

		Long priority = getPriority();

		if (priority != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(priority);
		}

		String status = getStatus();

		if (status != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"status\": ");

			sb.append("\"");

			sb.append(_escape(status));

			sb.append("\"");
		}

		Long testrayBuildId = getTestrayBuildId();

		if (testrayBuildId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayBuildId\": ");

			sb.append(testrayBuildId);
		}

		String testrayCaseName = getTestrayCaseName();

		if (testrayCaseName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseName\": ");

			sb.append("\"");

			sb.append(_escape(testrayCaseName));

			sb.append("\"");
		}

		Long testrayCaseResultId = getTestrayCaseResultId();

		if (testrayCaseResultId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseResultId\": ");

			sb.append(testrayCaseResultId);
		}

		String testrayCaseTypeName = getTestrayCaseTypeName();

		if (testrayCaseTypeName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayCaseTypeName\": ");

			sb.append("\"");

			sb.append(_escape(testrayCaseTypeName));

			sb.append("\"");
		}

		String testrayComponentName = getTestrayComponentName();

		if (testrayComponentName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayComponentName\": ");

			sb.append("\"");

			sb.append(_escape(testrayComponentName));

			sb.append("\"");
		}

		String testrayProductVersionName = getTestrayProductVersionName();

		if (testrayProductVersionName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayProductVersionName\": ");

			sb.append("\"");

			sb.append(_escape(testrayProductVersionName));

			sb.append("\"");
		}

		Long testrayRoutineId = getTestrayRoutineId();

		if (testrayRoutineId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRoutineId\": ");

			sb.append(testrayRoutineId);
		}

		String testrayRoutineName = getTestrayRoutineName();

		if (testrayRoutineName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRoutineName\": ");

			sb.append("\"");

			sb.append(_escape(testrayRoutineName));

			sb.append("\"");
		}

		String testrayRunName = getTestrayRunName();

		if (testrayRunName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRunName\": ");

			sb.append("\"");

			sb.append(_escape(testrayRunName));

			sb.append("\"");
		}

		Long testrayRunNumber = getTestrayRunNumber();

		if (testrayRunNumber != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayRunNumber\": ");

			sb.append(testrayRunNumber);
		}

		String testrayTeamName = getTestrayTeamName();

		if (testrayTeamName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"testrayTeamName\": ");

			sb.append("\"");

			sb.append(_escape(testrayTeamName));

			sb.append("\"");
		}

		String userName = getUserName();

		if (userName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userName\": ");

			sb.append("\"");

			sb.append(_escape(userName));

			sb.append("\"");
		}

		String userPortraitUrl = getUserPortraitUrl();

		if (userPortraitUrl != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"userPortraitUrl\": ");

			sb.append("\"");

			sb.append(_escape(userPortraitUrl));

			sb.append("\"");
		}

		Integer warning = getWarning();

		if (warning != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"warning\": ");

			sb.append(warning);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayCaseResult",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}