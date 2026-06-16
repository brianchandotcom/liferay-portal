/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Leslie Wong
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetWorkspaceGroupChannelPagesPageResponse {

	public PageMetricBag getPageMetricBag() {
		return _pageMetricBag;
	}

	public void setPageMetricBag(PageMetricBag pageMetricBag) {
		_pageMetricBag = pageMetricBag;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Metric {

		public Trend getTrend() {
			return _trend;
		}

		public Double getValue() {
			return _value;
		}

		public void setTrend(Trend trend) {
			_trend = trend;
		}

		public void setValue(Double value) {
			_value = value;
		}

		private Trend _trend;
		private Double _value;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PageMetric {

		public String getAssetId() {
			return _assetId;
		}

		public String getAssetTitle() {
			return _assetTitle;
		}

		public String getAssetType() {
			return _assetType;
		}

		public Metric getAvgTimeOnPageMetric() {
			return _avgTimeOnPageMetric;
		}

		public Metric getBounceRateMetric() {
			return _bounceRateMetric;
		}

		public String getDataSourceId() {
			return _dataSourceId;
		}

		public Metric getDirectAccessMetric() {
			return _directAccessMetric;
		}

		public Metric getEntrancesMetric() {
			return _entrancesMetric;
		}

		public Metric getExitRateMetric() {
			return _exitRateMetric;
		}

		public Metric getIndirectAccessMetric() {
			return _indirectAccessMetric;
		}

		public List<String> getUrls() {
			return _urls;
		}

		public Metric getViewsMetric() {
			return _viewsMetric;
		}

		public Metric getVisitorsMetric() {
			return _visitorsMetric;
		}

		public void setAssetId(String assetId) {
			_assetId = assetId;
		}

		public void setAssetTitle(String assetTitle) {
			_assetTitle = assetTitle;
		}

		public void setAssetType(String assetType) {
			_assetType = assetType;
		}

		public void setAvgTimeOnPageMetric(Metric avgTimeOnPageMetric) {
			_avgTimeOnPageMetric = avgTimeOnPageMetric;
		}

		public void setBounceRateMetric(Metric bounceRateMetric) {
			_bounceRateMetric = bounceRateMetric;
		}

		public void setDataSourceId(String dataSourceId) {
			_dataSourceId = dataSourceId;
		}

		public void setDirectAccessMetric(Metric directAccessMetric) {
			_directAccessMetric = directAccessMetric;
		}

		public void setEntrancesMetric(Metric entrancesMetric) {
			_entrancesMetric = entrancesMetric;
		}

		public void setExitRateMetric(Metric exitRateMetric) {
			_exitRateMetric = exitRateMetric;
		}

		public void setIndirectAccessMetric(Metric indirectAccessMetric) {
			_indirectAccessMetric = indirectAccessMetric;
		}

		public void setUrls(List<String> urls) {
			_urls = urls;
		}

		public void setViewsMetric(Metric viewsMetric) {
			_viewsMetric = viewsMetric;
		}

		public void setVisitorsMetric(Metric visitorsMetric) {
			_visitorsMetric = visitorsMetric;
		}

		private String _assetId;
		private String _assetTitle;
		private String _assetType;
		private Metric _avgTimeOnPageMetric;
		private Metric _bounceRateMetric;
		private String _dataSourceId;
		private Metric _directAccessMetric;
		private Metric _entrancesMetric;
		private Metric _exitRateMetric;
		private Metric _indirectAccessMetric;
		private List<String> _urls;
		private Metric _viewsMetric;
		private Metric _visitorsMetric;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class PageMetricBag {

		public List<PageMetric> getPageMetrics() {
			return _pageMetrics;
		}

		public Integer getTotal() {
			return _total;
		}

		public void setPageMetrics(List<PageMetric> pageMetrics) {
			_pageMetrics = pageMetrics;
		}

		public void setTotal(Integer total) {
			_total = total;
		}

		private List<PageMetric> _pageMetrics;
		private Integer _total;

	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Trend {

		public Double getPercentage() {
			return _percentage;
		}

		public void setPercentage(Double percentage) {
			_percentage = percentage;
		}

		private Double _percentage;

	}

	private PageMetricBag _pageMetricBag;

}