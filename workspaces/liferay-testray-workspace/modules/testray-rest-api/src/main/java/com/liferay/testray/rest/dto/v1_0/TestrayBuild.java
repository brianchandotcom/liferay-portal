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
@GraphQLName("TestrayBuild")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TestrayBuild")
public class TestrayBuild implements Serializable {

	public static TestrayBuild toDTO(String json) {
		return ObjectMapperUtil.readValue(TestrayBuild.class, json);
	}

	public static TestrayBuild unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(TestrayBuild.class, json);
	}

	@Schema
	public Boolean getArchived() {
		if (_archivedSupplier != null) {
			archived = _archivedSupplier.get();

			_archivedSupplier = null;
		}

		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;

		_archivedSupplier = null;
	}

	@JsonIgnore
	public void setArchived(
		UnsafeSupplier<Boolean, Exception> archivedUnsafeSupplier) {

		_archivedSupplier = () -> {
			try {
				return archivedUnsafeSupplier.get();
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
	protected Boolean archived;

	@JsonIgnore
	private Supplier<Boolean> _archivedSupplier;

	@Schema
	public Integer getCaseResultBlocked() {
		if (_caseResultBlockedSupplier != null) {
			caseResultBlocked = _caseResultBlockedSupplier.get();

			_caseResultBlockedSupplier = null;
		}

		return caseResultBlocked;
	}

	public void setCaseResultBlocked(Integer caseResultBlocked) {
		this.caseResultBlocked = caseResultBlocked;

		_caseResultBlockedSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultBlocked(
		UnsafeSupplier<Integer, Exception> caseResultBlockedUnsafeSupplier) {

		_caseResultBlockedSupplier = () -> {
			try {
				return caseResultBlockedUnsafeSupplier.get();
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
	protected Integer caseResultBlocked;

	@JsonIgnore
	private Supplier<Integer> _caseResultBlockedSupplier;

	@Schema
	public Integer getCaseResultDidNotRun() {
		if (_caseResultDidNotRunSupplier != null) {
			caseResultDidNotRun = _caseResultDidNotRunSupplier.get();

			_caseResultDidNotRunSupplier = null;
		}

		return caseResultDidNotRun;
	}

	public void setCaseResultDidNotRun(Integer caseResultDidNotRun) {
		this.caseResultDidNotRun = caseResultDidNotRun;

		_caseResultDidNotRunSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultDidNotRun(
		UnsafeSupplier<Integer, Exception> caseResultDidNotRunUnsafeSupplier) {

		_caseResultDidNotRunSupplier = () -> {
			try {
				return caseResultDidNotRunUnsafeSupplier.get();
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
	protected Integer caseResultDidNotRun;

	@JsonIgnore
	private Supplier<Integer> _caseResultDidNotRunSupplier;

	@Schema
	public Integer getCaseResultFailed() {
		if (_caseResultFailedSupplier != null) {
			caseResultFailed = _caseResultFailedSupplier.get();

			_caseResultFailedSupplier = null;
		}

		return caseResultFailed;
	}

	public void setCaseResultFailed(Integer caseResultFailed) {
		this.caseResultFailed = caseResultFailed;

		_caseResultFailedSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultFailed(
		UnsafeSupplier<Integer, Exception> caseResultFailedUnsafeSupplier) {

		_caseResultFailedSupplier = () -> {
			try {
				return caseResultFailedUnsafeSupplier.get();
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
	protected Integer caseResultFailed;

	@JsonIgnore
	private Supplier<Integer> _caseResultFailedSupplier;

	@Schema
	public Integer getCaseResultInProgress() {
		if (_caseResultInProgressSupplier != null) {
			caseResultInProgress = _caseResultInProgressSupplier.get();

			_caseResultInProgressSupplier = null;
		}

		return caseResultInProgress;
	}

	public void setCaseResultInProgress(Integer caseResultInProgress) {
		this.caseResultInProgress = caseResultInProgress;

		_caseResultInProgressSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultInProgress(
		UnsafeSupplier<Integer, Exception> caseResultInProgressUnsafeSupplier) {

		_caseResultInProgressSupplier = () -> {
			try {
				return caseResultInProgressUnsafeSupplier.get();
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
	protected Integer caseResultInProgress;

	@JsonIgnore
	private Supplier<Integer> _caseResultInProgressSupplier;

	@Schema
	public Integer getCaseResultIncomplete() {
		if (_caseResultIncompleteSupplier != null) {
			caseResultIncomplete = _caseResultIncompleteSupplier.get();

			_caseResultIncompleteSupplier = null;
		}

		return caseResultIncomplete;
	}

	public void setCaseResultIncomplete(Integer caseResultIncomplete) {
		this.caseResultIncomplete = caseResultIncomplete;

		_caseResultIncompleteSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultIncomplete(
		UnsafeSupplier<Integer, Exception> caseResultIncompleteUnsafeSupplier) {

		_caseResultIncompleteSupplier = () -> {
			try {
				return caseResultIncompleteUnsafeSupplier.get();
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
	protected Integer caseResultIncomplete;

	@JsonIgnore
	private Supplier<Integer> _caseResultIncompleteSupplier;

	@Schema
	public Integer getCaseResultPassed() {
		if (_caseResultPassedSupplier != null) {
			caseResultPassed = _caseResultPassedSupplier.get();

			_caseResultPassedSupplier = null;
		}

		return caseResultPassed;
	}

	public void setCaseResultPassed(Integer caseResultPassed) {
		this.caseResultPassed = caseResultPassed;

		_caseResultPassedSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultPassed(
		UnsafeSupplier<Integer, Exception> caseResultPassedUnsafeSupplier) {

		_caseResultPassedSupplier = () -> {
			try {
				return caseResultPassedUnsafeSupplier.get();
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
	protected Integer caseResultPassed;

	@JsonIgnore
	private Supplier<Integer> _caseResultPassedSupplier;

	@Schema
	public Integer getCaseResultTestFix() {
		if (_caseResultTestFixSupplier != null) {
			caseResultTestFix = _caseResultTestFixSupplier.get();

			_caseResultTestFixSupplier = null;
		}

		return caseResultTestFix;
	}

	public void setCaseResultTestFix(Integer caseResultTestFix) {
		this.caseResultTestFix = caseResultTestFix;

		_caseResultTestFixSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultTestFix(
		UnsafeSupplier<Integer, Exception> caseResultTestFixUnsafeSupplier) {

		_caseResultTestFixSupplier = () -> {
			try {
				return caseResultTestFixUnsafeSupplier.get();
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
	protected Integer caseResultTestFix;

	@JsonIgnore
	private Supplier<Integer> _caseResultTestFixSupplier;

	@Schema
	public Integer getCaseResultUntested() {
		if (_caseResultUntestedSupplier != null) {
			caseResultUntested = _caseResultUntestedSupplier.get();

			_caseResultUntestedSupplier = null;
		}

		return caseResultUntested;
	}

	public void setCaseResultUntested(Integer caseResultUntested) {
		this.caseResultUntested = caseResultUntested;

		_caseResultUntestedSupplier = null;
	}

	@JsonIgnore
	public void setCaseResultUntested(
		UnsafeSupplier<Integer, Exception> caseResultUntestedUnsafeSupplier) {

		_caseResultUntestedSupplier = () -> {
			try {
				return caseResultUntestedUnsafeSupplier.get();
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
	protected Integer caseResultUntested;

	@JsonIgnore
	private Supplier<Integer> _caseResultUntestedSupplier;

	@Schema
	public String getDateArchived() {
		if (_dateArchivedSupplier != null) {
			dateArchived = _dateArchivedSupplier.get();

			_dateArchivedSupplier = null;
		}

		return dateArchived;
	}

	public void setDateArchived(String dateArchived) {
		this.dateArchived = dateArchived;

		_dateArchivedSupplier = null;
	}

	@JsonIgnore
	public void setDateArchived(
		UnsafeSupplier<String, Exception> dateArchivedUnsafeSupplier) {

		_dateArchivedSupplier = () -> {
			try {
				return dateArchivedUnsafeSupplier.get();
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
	protected String dateArchived;

	@JsonIgnore
	private Supplier<String> _dateArchivedSupplier;

	@Schema
	public String getDescription() {
		if (_descriptionSupplier != null) {
			description = _descriptionSupplier.get();

			_descriptionSupplier = null;
		}

		return description;
	}

	public void setDescription(String description) {
		this.description = description;

		_descriptionSupplier = null;
	}

	@JsonIgnore
	public void setDescription(
		UnsafeSupplier<String, Exception> descriptionUnsafeSupplier) {

		_descriptionSupplier = () -> {
			try {
				return descriptionUnsafeSupplier.get();
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
	protected String description;

	@JsonIgnore
	private Supplier<String> _descriptionSupplier;

	@Schema
	public String getDueDate() {
		if (_dueDateSupplier != null) {
			dueDate = _dueDateSupplier.get();

			_dueDateSupplier = null;
		}

		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;

		_dueDateSupplier = null;
	}

	@JsonIgnore
	public void setDueDate(
		UnsafeSupplier<String, Exception> dueDateUnsafeSupplier) {

		_dueDateSupplier = () -> {
			try {
				return dueDateUnsafeSupplier.get();
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
	protected String dueDate;

	@JsonIgnore
	private Supplier<String> _dueDateSupplier;

	@Schema
	public String getDueStatus() {
		if (_dueStatusSupplier != null) {
			dueStatus = _dueStatusSupplier.get();

			_dueStatusSupplier = null;
		}

		return dueStatus;
	}

	public void setDueStatus(String dueStatus) {
		this.dueStatus = dueStatus;

		_dueStatusSupplier = null;
	}

	@JsonIgnore
	public void setDueStatus(
		UnsafeSupplier<String, Exception> dueStatusUnsafeSupplier) {

		_dueStatusSupplier = () -> {
			try {
				return dueStatusUnsafeSupplier.get();
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
	protected String dueStatus;

	@JsonIgnore
	private Supplier<String> _dueStatusSupplier;

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
	public String getGithubCompareURLs() {
		if (_githubCompareURLsSupplier != null) {
			githubCompareURLs = _githubCompareURLsSupplier.get();

			_githubCompareURLsSupplier = null;
		}

		return githubCompareURLs;
	}

	public void setGithubCompareURLs(String githubCompareURLs) {
		this.githubCompareURLs = githubCompareURLs;

		_githubCompareURLsSupplier = null;
	}

	@JsonIgnore
	public void setGithubCompareURLs(
		UnsafeSupplier<String, Exception> githubCompareURLsUnsafeSupplier) {

		_githubCompareURLsSupplier = () -> {
			try {
				return githubCompareURLsUnsafeSupplier.get();
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
	protected String githubCompareURLs;

	@JsonIgnore
	private Supplier<String> _githubCompareURLsSupplier;

	@Schema
	public String getName() {
		if (_nameSupplier != null) {
			name = _nameSupplier.get();

			_nameSupplier = null;
		}

		return name;
	}

	public void setName(String name) {
		this.name = name;

		_nameSupplier = null;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		_nameSupplier = () -> {
			try {
				return nameUnsafeSupplier.get();
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
	protected String name;

	@JsonIgnore
	private Supplier<String> _nameSupplier;

	@Schema
	public Boolean getPromoted() {
		if (_promotedSupplier != null) {
			promoted = _promotedSupplier.get();

			_promotedSupplier = null;
		}

		return promoted;
	}

	public void setPromoted(Boolean promoted) {
		this.promoted = promoted;

		_promotedSupplier = null;
	}

	@JsonIgnore
	public void setPromoted(
		UnsafeSupplier<Boolean, Exception> promotedUnsafeSupplier) {

		_promotedSupplier = () -> {
			try {
				return promotedUnsafeSupplier.get();
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
	protected Boolean promoted;

	@JsonIgnore
	private Supplier<Boolean> _promotedSupplier;

	@Schema
	public Long getR_productVersionToBuilds_c_productVersionId() {
		if (_r_productVersionToBuilds_c_productVersionIdSupplier != null) {
			r_productVersionToBuilds_c_productVersionId =
				_r_productVersionToBuilds_c_productVersionIdSupplier.get();

			_r_productVersionToBuilds_c_productVersionIdSupplier = null;
		}

		return r_productVersionToBuilds_c_productVersionId;
	}

	public void setR_productVersionToBuilds_c_productVersionId(
		Long r_productVersionToBuilds_c_productVersionId) {

		this.r_productVersionToBuilds_c_productVersionId =
			r_productVersionToBuilds_c_productVersionId;

		_r_productVersionToBuilds_c_productVersionIdSupplier = null;
	}

	@JsonIgnore
	public void setR_productVersionToBuilds_c_productVersionId(
		UnsafeSupplier<Long, Exception>
			r_productVersionToBuilds_c_productVersionIdUnsafeSupplier) {

		_r_productVersionToBuilds_c_productVersionIdSupplier = () -> {
			try {
				return r_productVersionToBuilds_c_productVersionIdUnsafeSupplier.
					get();
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
	protected Long r_productVersionToBuilds_c_productVersionId;

	@JsonIgnore
	private Supplier<Long> _r_productVersionToBuilds_c_productVersionIdSupplier;

	@Schema
	public Long getR_projectToBuilds_c_projectId() {
		if (_r_projectToBuilds_c_projectIdSupplier != null) {
			r_projectToBuilds_c_projectId =
				_r_projectToBuilds_c_projectIdSupplier.get();

			_r_projectToBuilds_c_projectIdSupplier = null;
		}

		return r_projectToBuilds_c_projectId;
	}

	public void setR_projectToBuilds_c_projectId(
		Long r_projectToBuilds_c_projectId) {

		this.r_projectToBuilds_c_projectId = r_projectToBuilds_c_projectId;

		_r_projectToBuilds_c_projectIdSupplier = null;
	}

	@JsonIgnore
	public void setR_projectToBuilds_c_projectId(
		UnsafeSupplier<Long, Exception>
			r_projectToBuilds_c_projectIdUnsafeSupplier) {

		_r_projectToBuilds_c_projectIdSupplier = () -> {
			try {
				return r_projectToBuilds_c_projectIdUnsafeSupplier.get();
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
	protected Long r_projectToBuilds_c_projectId;

	@JsonIgnore
	private Supplier<Long> _r_projectToBuilds_c_projectIdSupplier;

	@Schema
	public Long getR_routineToBuilds_c_routineId() {
		if (_r_routineToBuilds_c_routineIdSupplier != null) {
			r_routineToBuilds_c_routineId =
				_r_routineToBuilds_c_routineIdSupplier.get();

			_r_routineToBuilds_c_routineIdSupplier = null;
		}

		return r_routineToBuilds_c_routineId;
	}

	public void setR_routineToBuilds_c_routineId(
		Long r_routineToBuilds_c_routineId) {

		this.r_routineToBuilds_c_routineId = r_routineToBuilds_c_routineId;

		_r_routineToBuilds_c_routineIdSupplier = null;
	}

	@JsonIgnore
	public void setR_routineToBuilds_c_routineId(
		UnsafeSupplier<Long, Exception>
			r_routineToBuilds_c_routineIdUnsafeSupplier) {

		_r_routineToBuilds_c_routineIdSupplier = () -> {
			try {
				return r_routineToBuilds_c_routineIdUnsafeSupplier.get();
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
	protected Long r_routineToBuilds_c_routineId;

	@JsonIgnore
	private Supplier<Long> _r_routineToBuilds_c_routineIdSupplier;

	@Schema
	public Boolean getTemplate() {
		if (_templateSupplier != null) {
			template = _templateSupplier.get();

			_templateSupplier = null;
		}

		return template;
	}

	public void setTemplate(Boolean template) {
		this.template = template;

		_templateSupplier = null;
	}

	@JsonIgnore
	public void setTemplate(
		UnsafeSupplier<Boolean, Exception> templateUnsafeSupplier) {

		_templateSupplier = () -> {
			try {
				return templateUnsafeSupplier.get();
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
	protected Boolean template;

	@JsonIgnore
	private Supplier<Boolean> _templateSupplier;

	@Schema
	public Long getTemplateTestrayBuildId() {
		if (_templateTestrayBuildIdSupplier != null) {
			templateTestrayBuildId = _templateTestrayBuildIdSupplier.get();

			_templateTestrayBuildIdSupplier = null;
		}

		return templateTestrayBuildId;
	}

	public void setTemplateTestrayBuildId(Long templateTestrayBuildId) {
		this.templateTestrayBuildId = templateTestrayBuildId;

		_templateTestrayBuildIdSupplier = null;
	}

	@JsonIgnore
	public void setTemplateTestrayBuildId(
		UnsafeSupplier<Long, Exception> templateTestrayBuildIdUnsafeSupplier) {

		_templateTestrayBuildIdSupplier = () -> {
			try {
				return templateTestrayBuildIdUnsafeSupplier.get();
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
	protected Long templateTestrayBuildId;

	@JsonIgnore
	private Supplier<Long> _templateTestrayBuildIdSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TestrayBuild)) {
			return false;
		}

		TestrayBuild testrayBuild = (TestrayBuild)object;

		return Objects.equals(toString(), testrayBuild.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean archived = getArchived();

		if (archived != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"archived\": ");

			sb.append(archived);
		}

		Integer caseResultBlocked = getCaseResultBlocked();

		if (caseResultBlocked != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultBlocked\": ");

			sb.append(caseResultBlocked);
		}

		Integer caseResultDidNotRun = getCaseResultDidNotRun();

		if (caseResultDidNotRun != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultDidNotRun\": ");

			sb.append(caseResultDidNotRun);
		}

		Integer caseResultFailed = getCaseResultFailed();

		if (caseResultFailed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultFailed\": ");

			sb.append(caseResultFailed);
		}

		Integer caseResultInProgress = getCaseResultInProgress();

		if (caseResultInProgress != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultInProgress\": ");

			sb.append(caseResultInProgress);
		}

		Integer caseResultIncomplete = getCaseResultIncomplete();

		if (caseResultIncomplete != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultIncomplete\": ");

			sb.append(caseResultIncomplete);
		}

		Integer caseResultPassed = getCaseResultPassed();

		if (caseResultPassed != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultPassed\": ");

			sb.append(caseResultPassed);
		}

		Integer caseResultTestFix = getCaseResultTestFix();

		if (caseResultTestFix != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultTestFix\": ");

			sb.append(caseResultTestFix);
		}

		Integer caseResultUntested = getCaseResultUntested();

		if (caseResultUntested != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"caseResultUntested\": ");

			sb.append(caseResultUntested);
		}

		String dateArchived = getDateArchived();

		if (dateArchived != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dateArchived\": ");

			sb.append("\"");

			sb.append(_escape(dateArchived));

			sb.append("\"");
		}

		String description = getDescription();

		if (description != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"description\": ");

			sb.append("\"");

			sb.append(_escape(description));

			sb.append("\"");
		}

		String dueDate = getDueDate();

		if (dueDate != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueDate\": ");

			sb.append("\"");

			sb.append(_escape(dueDate));

			sb.append("\"");
		}

		String dueStatus = getDueStatus();

		if (dueStatus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"dueStatus\": ");

			sb.append("\"");

			sb.append(_escape(dueStatus));

			sb.append("\"");
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

		String githubCompareURLs = getGithubCompareURLs();

		if (githubCompareURLs != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"githubCompareURLs\": ");

			sb.append("\"");

			sb.append(_escape(githubCompareURLs));

			sb.append("\"");
		}

		String name = getName();

		if (name != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(name));

			sb.append("\"");
		}

		Boolean promoted = getPromoted();

		if (promoted != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"promoted\": ");

			sb.append(promoted);
		}

		Long r_productVersionToBuilds_c_productVersionId =
			getR_productVersionToBuilds_c_productVersionId();

		if (r_productVersionToBuilds_c_productVersionId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"r_productVersionToBuilds_c_productVersionId\": ");

			sb.append(r_productVersionToBuilds_c_productVersionId);
		}

		Long r_projectToBuilds_c_projectId = getR_projectToBuilds_c_projectId();

		if (r_projectToBuilds_c_projectId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"r_projectToBuilds_c_projectId\": ");

			sb.append(r_projectToBuilds_c_projectId);
		}

		Long r_routineToBuilds_c_routineId = getR_routineToBuilds_c_routineId();

		if (r_routineToBuilds_c_routineId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"r_routineToBuilds_c_routineId\": ");

			sb.append(r_routineToBuilds_c_routineId);
		}

		Boolean template = getTemplate();

		if (template != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"template\": ");

			sb.append(template);
		}

		Long templateTestrayBuildId = getTemplateTestrayBuildId();

		if (templateTestrayBuildId != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"templateTestrayBuildId\": ");

			sb.append(templateTestrayBuildId);
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.testray.rest.dto.v1_0.TestrayBuild",
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