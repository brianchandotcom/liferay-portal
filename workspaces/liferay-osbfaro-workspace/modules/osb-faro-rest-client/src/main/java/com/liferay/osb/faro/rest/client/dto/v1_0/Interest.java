/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.client.dto.v1_0;

import com.liferay.osb.faro.rest.client.function.UnsafeSupplier;
import com.liferay.osb.faro.rest.client.serdes.v1_0.InterestSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public class Interest implements Cloneable, Serializable {

	public static Interest toDTO(String json) {
		return InterestSerDes.toDTO(json);
	}

	public Date getDateRecorded() {
		return dateRecorded;
	}

	public void setDateRecorded(Date dateRecorded) {
		this.dateRecorded = dateRecorded;
	}

	public void setDateRecorded(
		UnsafeSupplier<Date, Exception> dateRecordedUnsafeSupplier) {

		try {
			dateRecorded = dateRecordedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Date dateRecorded;

	public String getIndividualId() {
		return individualId;
	}

	public void setIndividualId(String individualId) {
		this.individualId = individualId;
	}

	public void setIndividualId(
		UnsafeSupplier<String, Exception> individualIdUnsafeSupplier) {

		try {
			individualId = individualIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String individualId;

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

	public Long getRelatedPagesCount() {
		return relatedPagesCount;
	}

	public void setRelatedPagesCount(Long relatedPagesCount) {
		this.relatedPagesCount = relatedPagesCount;
	}

	public void setRelatedPagesCount(
		UnsafeSupplier<Long, Exception> relatedPagesCountUnsafeSupplier) {

		try {
			relatedPagesCount = relatedPagesCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long relatedPagesCount;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setScore(
		UnsafeSupplier<Double, Exception> scoreUnsafeSupplier) {

		try {
			score = scoreUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double score;

	@Override
	public Interest clone() throws CloneNotSupportedException {
		return (Interest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Interest)) {
			return false;
		}

		Interest interest = (Interest)object;

		return Objects.equals(toString(), interest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return InterestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-2018513108