/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.client.dto.v1_0;

import com.liferay.osb.faro.rest.client.function.UnsafeSupplier;
import com.liferay.osb.faro.rest.client.serdes.v1_0.IndividualSegmentSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public class IndividualSegment implements Cloneable, Serializable {

	public static IndividualSegment toDTO(String json) {
		return IndividualSegmentSerDes.toDTO(json);
	}

	public Long getActiveIndividualCount() {
		return activeIndividualCount;
	}

	public void setActiveIndividualCount(Long activeIndividualCount) {
		this.activeIndividualCount = activeIndividualCount;
	}

	public void setActiveIndividualCount(
		UnsafeSupplier<Long, Exception> activeIndividualCountUnsafeSupplier) {

		try {
			activeIndividualCount = activeIndividualCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long activeIndividualCount;

	public Long getAnonymousIndividualCount() {
		return anonymousIndividualCount;
	}

	public void setAnonymousIndividualCount(Long anonymousIndividualCount) {
		this.anonymousIndividualCount = anonymousIndividualCount;
	}

	public void setAnonymousIndividualCount(
		UnsafeSupplier<Long, Exception>
			anonymousIndividualCountUnsafeSupplier) {

		try {
			anonymousIndividualCount =
				anonymousIndividualCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long anonymousIndividualCount;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setChannelId(
		UnsafeSupplier<String, Exception> channelIdUnsafeSupplier) {

		try {
			channelId = channelIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String channelId;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateCreated;

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateModified;

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setFilter(
		UnsafeSupplier<String, Exception> filterUnsafeSupplier) {

		try {
			filter = filterUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String filter;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<String, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String id;

	public Boolean getIncludeAnonymousUsers() {
		return includeAnonymousUsers;
	}

	public void setIncludeAnonymousUsers(Boolean includeAnonymousUsers) {
		this.includeAnonymousUsers = includeAnonymousUsers;
	}

	public void setIncludeAnonymousUsers(
		UnsafeSupplier<Boolean, Exception>
			includeAnonymousUsersUnsafeSupplier) {

		try {
			includeAnonymousUsers = includeAnonymousUsersUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean includeAnonymousUsers;

	public Long getIndividualCount() {
		return individualCount;
	}

	public void setIndividualCount(Long individualCount) {
		this.individualCount = individualCount;
	}

	public void setIndividualCount(
		UnsafeSupplier<Long, Exception> individualCountUnsafeSupplier) {

		try {
			individualCount = individualCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long individualCount;

	public Long getKnownIndividualCount() {
		return knownIndividualCount;
	}

	public void setKnownIndividualCount(Long knownIndividualCount) {
		this.knownIndividualCount = knownIndividualCount;
	}

	public void setKnownIndividualCount(
		UnsafeSupplier<Long, Exception> knownIndividualCountUnsafeSupplier) {

		try {
			knownIndividualCount = knownIndividualCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long knownIndividualCount;

	public Date getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public void setLastActivityDate(
		UnsafeSupplier<Date, Exception> lastActivityDateUnsafeSupplier) {

		try {
			lastActivityDate = lastActivityDateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date lastActivityDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public SegmentType getSegmentType() {
		return segmentType;
	}

	public String getSegmentTypeAsString() {
		if (segmentType == null) {
			return null;
		}

		return segmentType.toString();
	}

	public void setSegmentType(SegmentType segmentType) {
		this.segmentType = segmentType;
	}

	public void setSegmentType(
		UnsafeSupplier<SegmentType, Exception> segmentTypeUnsafeSupplier) {

		try {
			segmentType = segmentTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SegmentType segmentType;

	public State getState() {
		return state;
	}

	public String getStateAsString() {
		if (state == null) {
			return null;
		}

		return state.toString();
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setState(UnsafeSupplier<State, Exception> stateUnsafeSupplier) {
		try {
			state = stateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected State state;

	public Status getStatus() {
		return status;
	}

	public String getStatusAsString() {
		if (status == null) {
			return null;
		}

		return status.toString();
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(
		UnsafeSupplier<Status, Exception> statusUnsafeSupplier) {

		try {
			status = statusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Status status;

	@Override
	public IndividualSegment clone() throws CloneNotSupportedException {
		return (IndividualSegment)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof IndividualSegment)) {
			return false;
		}

		IndividualSegment individualSegment = (IndividualSegment)object;

		return Objects.equals(toString(), individualSegment.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return IndividualSegmentSerDes.toJSON(this);
	}

	public static enum SegmentType {

		BATCH("BATCH"), REAL_TIME("REAL_TIME");

		public static SegmentType create(String value) {
			for (SegmentType segmentType : values()) {
				if (Objects.equals(segmentType.getValue(), value) ||
					Objects.equals(segmentType.name(), value)) {

					return segmentType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private SegmentType(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum State {

		DISABLED("DISABLED"), IN_PROGRESS("IN_PROGRESS"), READY("READY");

		public static State create(String value) {
			for (State state : values()) {
				if (Objects.equals(state.getValue(), value) ||
					Objects.equals(state.name(), value)) {

					return state;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private State(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Status {

		ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

		public static Status create(String value) {
			for (Status status : values()) {
				if (Objects.equals(status.getValue(), value) ||
					Objects.equals(status.name(), value)) {

					return status;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Status(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:-2102082135