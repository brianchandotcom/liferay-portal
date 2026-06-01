/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.pagespeed.scanner;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.seo.studio.pagespeed.PageSpeedConstants;

import jakarta.annotation.PreDestroy;

import java.net.http.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Service;

/**
 * @author Kiana Suetani
 */
@Service
public class PageSpeedScanner {

	public PageSpeedScanResult scan(
			String authToken, String domainHostname, HttpClient httpClient,
			String pageSpeedAPIKey, String portalBaseURL,
			Consumer<PageSpeedScanResult> progressConsumer, String strategy)
		throws Exception {

		if (Validator.isNull(pageSpeedAPIKey)) {
			PageSpeedScanResult pageSpeedScanResult = new PageSpeedScanResult(
				null, "Google PageSpeed API key is not configured", 0, 0, 0,
				PageSpeedScanResult.STATUS_FAILED, strategy);

			_publishProgress(pageSpeedScanResult, progressConsumer);

			return pageSpeedScanResult;
		}

		LiferayHeadlessClient liferayHeadlessClient = new LiferayHeadlessClient(
			authToken, httpClient, portalBaseURL);

		List<String> urls = liferayHeadlessClient.getPageURLs(
			domainHostname, 100);

		if (ListUtil.isEmpty(urls)) {
			PageSpeedScanResult pageSpeedScanResult = new PageSpeedScanResult(
				new PageSpeedScores(0, 0, 0, 0), null, 0, 0, 0,
				PageSpeedScanResult.STATUS_COMPLETED, strategy);

			_publishProgress(pageSpeedScanResult, progressConsumer);

			return pageSpeedScanResult;
		}

		PageSpeedScoreProvider pageSpeedScoreProvider =
			new PageSpeedScoreProvider(httpClient, pageSpeedAPIKey, strategy);

		return _scanURLs(
			pageSpeedScoreProvider, progressConsumer, strategy, urls);
	}

	public void scanAsync(
		String domainHostname, HttpClient httpClient,
		LiferayOAuth2AccessTokenManager liferayOAuth2AccessTokenManager,
		Consumer<PageSpeedScanResult> onComplete, Consumer<String> onError,
		String pageSpeedAPIKey, String portalBaseURL,
		Supplier<Consumer<PageSpeedScanResult>> progressConsumerSupplier,
		String strategy) {

		_asyncExecutorService.submit(
			() -> {
				try {
					String authToken =
						liferayOAuth2AccessTokenManager.getTokenValue(
							PageSpeedConstants.OAHS_EXTERNAL_REFERENCE_CODE);

					Consumer<PageSpeedScanResult> progressConsumer =
						progressConsumerSupplier.get();

					PageSpeedScanResult pageSpeedScanResult = scan(
						authToken, domainHostname, httpClient, pageSpeedAPIKey,
						portalBaseURL, progressConsumer, strategy);

					if (PageSpeedScanResult.STATUS_FAILED.equals(
							pageSpeedScanResult.getStatus())) {

						onError.accept(pageSpeedScanResult.getErrorMessage());
					}
					else {
						onComplete.accept(pageSpeedScanResult);
					}
				}
				catch (Exception exception) {
					if (exception instanceof InterruptedException) {
						Thread.currentThread(
						).interrupt();
					}

					_log.error("PageSpeed scan failed", exception);

					onError.accept(exception.getMessage());
				}
			});
	}

	private PageSpeedScores _computeAverageScores(
		int count, int totalAccessibility, int totalBestPractices,
		int totalPerformance, int totalSeo) {

		if (count == 0) {
			return new PageSpeedScores(0, 0, 0, 0);
		}

		return new PageSpeedScores(
			Math.round((float)totalAccessibility / count),
			Math.round((float)totalBestPractices / count),
			Math.round((float)totalPerformance / count),
			Math.round((float)totalSeo / count));
	}

	@PreDestroy
	private void _destroy() {
		_asyncExecutorService.shutdown();
		_scanExecutorService.shutdown();

		try {
			if (!_asyncExecutorService.awaitTermination(30, TimeUnit.SECONDS)) {
				_asyncExecutorService.shutdownNow();
			}

			if (!_scanExecutorService.awaitTermination(30, TimeUnit.SECONDS)) {
				_scanExecutorService.shutdownNow();
			}
		}
		catch (InterruptedException interruptedException) {
			_asyncExecutorService.shutdownNow();
			_scanExecutorService.shutdownNow();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Executor shutdown interrupted", interruptedException);
			}

			Thread.currentThread(
			).interrupt();
		}
	}

	private void _publishProgress(
		PageSpeedScanResult pageSpeedScanResult,
		Consumer<PageSpeedScanResult> progressConsumer) {

		if (progressConsumer != null) {
			progressConsumer.accept(pageSpeedScanResult);
		}
	}

	private PageSpeedScanResult _scanURLs(
		PageSpeedScoreProvider pageSpeedScoreProvider,
		Consumer<PageSpeedScanResult> progressConsumer, String strategy,
		List<String> urls) {

		int pagesTotal = urls.size();

		AtomicInteger pagesErrored = new AtomicInteger(0);
		AtomicInteger pagesScanned = new AtomicInteger(0);
		AtomicBoolean quotaExceeded = new AtomicBoolean(false);
		AtomicInteger totalAccessibility = new AtomicInteger(0);
		AtomicInteger totalBestPractices = new AtomicInteger(0);
		AtomicInteger totalPerformance = new AtomicInteger(0);
		AtomicInteger totalSeo = new AtomicInteger(0);

		_publishProgress(
			new PageSpeedScanResult(
				null, null, 0, 0, pagesTotal,
				PageSpeedScanResult.STATUS_RUNNING, strategy),
			progressConsumer);

		List<Future<?>> futures = new ArrayList<>();

		for (String url : urls) {
			futures.add(
				_scanExecutorService.submit(
					() -> {
						if (quotaExceeded.get()) {
							return;
						}

						try {
							PageSpeedScores pageSpeedScores =
								pageSpeedScoreProvider.getScores(url);

							totalAccessibility.addAndGet(
								pageSpeedScores.getAccessibility());
							totalBestPractices.addAndGet(
								pageSpeedScores.getBestPractices());
							totalPerformance.addAndGet(
								pageSpeedScores.getPerformance());
							totalSeo.addAndGet(pageSpeedScores.getSeo());

							int scanned = pagesScanned.incrementAndGet();

							_publishProgress(
								new PageSpeedScanResult(
									_computeAverageScores(
										scanned, totalAccessibility.get(),
										totalBestPractices.get(),
										totalPerformance.get(), totalSeo.get()),
									null, pagesErrored.get(), scanned,
									pagesTotal,
									PageSpeedScanResult.STATUS_RUNNING,
									strategy),
								progressConsumer);
						}
						catch (PageSpeedScoreProvider.
									PageSpeedScoreProviderException
										pageSpeedScoreProviderException) {

							if (pageSpeedScoreProviderException.
									isQuotaExceeded()) {

								quotaExceeded.set(true);
							}
							else {
								pagesErrored.incrementAndGet();

								if (_log.isDebugEnabled()) {
									_log.debug(
										"PageSpeed scores error: " + url,
										pageSpeedScoreProviderException);
								}
							}
						}
					}));
		}

		long scanDeadline = System.currentTimeMillis() + 600000;

		for (Future<?> future : futures) {
			long remainingMillis = scanDeadline - System.currentTimeMillis();

			if (remainingMillis <= 0) {
				for (Future<?> remainingFuture : futures) {
					remainingFuture.cancel(true);
				}

				pagesErrored.addAndGet(
					pagesTotal - pagesErrored.get() - pagesScanned.get());

				break;
			}

			try {
				future.get(remainingMillis, TimeUnit.MILLISECONDS);
			}
			catch (TimeoutException timeoutException) {
				pagesErrored.incrementAndGet();

				future.cancel(true);

				if (_log.isDebugEnabled()) {
					_log.debug(
						"PageSpeed scan task timed out", timeoutException);
				}
			}
			catch (ExecutionException executionException) {
				pagesErrored.incrementAndGet();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"PageSpeed scan task failed", executionException);
				}
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"PageSpeed scan interrupted", interruptedException);
				}

				for (Future<?> remainingFuture : futures) {
					remainingFuture.cancel(true);
				}

				Thread.currentThread(
				).interrupt();

				break;
			}
		}

		String errorMessage = null;
		String status = PageSpeedScanResult.STATUS_COMPLETED;

		int errored = pagesErrored.get();
		int scanned = pagesScanned.get();

		if (quotaExceeded.get()) {
			errored = pagesTotal - scanned;

			errorMessage = StringBundler.concat(
				"Google PageSpeed API quota exceeded after scanning ", scanned,
				" of ", pagesTotal, " pages");
			status = PageSpeedScanResult.STATUS_FAILED;
		}
		else if ((scanned == 0) && (errored > 0)) {
			errorMessage = "All pages failed to scan";
			status = PageSpeedScanResult.STATUS_FAILED;
		}

		PageSpeedScanResult pageSpeedScanResult = new PageSpeedScanResult(
			_computeAverageScores(
				scanned, totalAccessibility.get(), totalBestPractices.get(),
				totalPerformance.get(), totalSeo.get()),
			errorMessage, errored, scanned, pagesTotal, status, strategy);

		_publishProgress(pageSpeedScanResult, progressConsumer);

		return pageSpeedScanResult;
	}

	private static final Log _log = LogFactory.getLog(PageSpeedScanner.class);

	private final ExecutorService _asyncExecutorService =
		Executors.newCachedThreadPool(
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable runnable) {
					Thread thread = new Thread(
						runnable,
						"pagespeed-async-" + _counter.getAndIncrement());

					thread.setDaemon(true);

					return thread;
				}

				private final AtomicInteger _counter = new AtomicInteger(0);

			});

	private final ExecutorService _scanExecutorService =
		Executors.newFixedThreadPool(
			5,
			new ThreadFactory() {

				@Override
				public Thread newThread(Runnable runnable) {
					Thread thread = new Thread(
						runnable,
						"pagespeed-scan-" + _counter.getAndIncrement());

					thread.setDaemon(true);

					return thread;
				}

				private final AtomicInteger _counter = new AtomicInteger(0);

			});

}