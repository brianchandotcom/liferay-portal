/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.client.dto.v1_0;

import com.liferay.osb.faro.rest.client.function.UnsafeSupplier;
import com.liferay.osb.faro.rest.client.serdes.v1_0.PageMetricSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Leslie Wong
 * @generated
 */
@Generated("")
public class PageMetric implements Cloneable, Serializable {

	public static PageMetric toDTO(String json) {
		return PageMetricSerDes.toDTO(json);
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public void setAssetId(
		UnsafeSupplier<String, Exception> assetIdUnsafeSupplier) {

		try {
			assetId = assetIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String assetId;

	public String getAssetTitle() {
		return assetTitle;
	}

	public void setAssetTitle(String assetTitle) {
		this.assetTitle = assetTitle;
	}

	public void setAssetTitle(
		UnsafeSupplier<String, Exception> assetTitleUnsafeSupplier) {

		try {
			assetTitle = assetTitleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String assetTitle;

	public Double getAvgTimeOnPage() {
		return avgTimeOnPage;
	}

	public void setAvgTimeOnPage(Double avgTimeOnPage) {
		this.avgTimeOnPage = avgTimeOnPage;
	}

	public void setAvgTimeOnPage(
		UnsafeSupplier<Double, Exception> avgTimeOnPageUnsafeSupplier) {

		try {
			avgTimeOnPage = avgTimeOnPageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double avgTimeOnPage;

	public Double getBounceRate() {
		return bounceRate;
	}

	public void setBounceRate(Double bounceRate) {
		this.bounceRate = bounceRate;
	}

	public void setBounceRate(
		UnsafeSupplier<Double, Exception> bounceRateUnsafeSupplier) {

		try {
			bounceRate = bounceRateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double bounceRate;

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public void setDataSourceId(
		UnsafeSupplier<String, Exception> dataSourceIdUnsafeSupplier) {

		try {
			dataSourceId = dataSourceIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String dataSourceId;

	public Double getDirectAccess() {
		return directAccess;
	}

	public void setDirectAccess(Double directAccess) {
		this.directAccess = directAccess;
	}

	public void setDirectAccess(
		UnsafeSupplier<Double, Exception> directAccessUnsafeSupplier) {

		try {
			directAccess = directAccessUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double directAccess;

	public Double getEntrances() {
		return entrances;
	}

	public void setEntrances(Double entrances) {
		this.entrances = entrances;
	}

	public void setEntrances(
		UnsafeSupplier<Double, Exception> entrancesUnsafeSupplier) {

		try {
			entrances = entrancesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double entrances;

	public Double getExitRate() {
		return exitRate;
	}

	public void setExitRate(Double exitRate) {
		this.exitRate = exitRate;
	}

	public void setExitRate(
		UnsafeSupplier<Double, Exception> exitRateUnsafeSupplier) {

		try {
			exitRate = exitRateUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double exitRate;

	public Double getIndirectAccess() {
		return indirectAccess;
	}

	public void setIndirectAccess(Double indirectAccess) {
		this.indirectAccess = indirectAccess;
	}

	public void setIndirectAccess(
		UnsafeSupplier<Double, Exception> indirectAccessUnsafeSupplier) {

		try {
			indirectAccess = indirectAccessUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double indirectAccess;

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public void setUrls(
		UnsafeSupplier<String[], Exception> urlsUnsafeSupplier) {

		try {
			urls = urlsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] urls;

	public Double getViews() {
		return views;
	}

	public void setViews(Double views) {
		this.views = views;
	}

	public void setViews(
		UnsafeSupplier<Double, Exception> viewsUnsafeSupplier) {

		try {
			views = viewsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double views;

	public Double getViewsTrendPercentage() {
		return viewsTrendPercentage;
	}

	public void setViewsTrendPercentage(Double viewsTrendPercentage) {
		this.viewsTrendPercentage = viewsTrendPercentage;
	}

	public void setViewsTrendPercentage(
		UnsafeSupplier<Double, Exception> viewsTrendPercentageUnsafeSupplier) {

		try {
			viewsTrendPercentage = viewsTrendPercentageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double viewsTrendPercentage;

	public Double getVisitors() {
		return visitors;
	}

	public void setVisitors(Double visitors) {
		this.visitors = visitors;
	}

	public void setVisitors(
		UnsafeSupplier<Double, Exception> visitorsUnsafeSupplier) {

		try {
			visitors = visitorsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double visitors;

	public Double getVisitorsTrendPercentage() {
		return visitorsTrendPercentage;
	}

	public void setVisitorsTrendPercentage(Double visitorsTrendPercentage) {
		this.visitorsTrendPercentage = visitorsTrendPercentage;
	}

	public void setVisitorsTrendPercentage(
		UnsafeSupplier<Double, Exception>
			visitorsTrendPercentageUnsafeSupplier) {

		try {
			visitorsTrendPercentage =
				visitorsTrendPercentageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Double visitorsTrendPercentage;

	@Override
	public PageMetric clone() throws CloneNotSupportedException {
		return (PageMetric)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageMetric)) {
			return false;
		}

		PageMetric pageMetric = (PageMetric)object;

		return Objects.equals(toString(), pageMetric.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PageMetricSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:431801719