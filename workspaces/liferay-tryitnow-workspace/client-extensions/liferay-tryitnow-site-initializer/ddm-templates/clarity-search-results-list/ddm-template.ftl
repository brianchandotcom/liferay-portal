<#-- v3.1 (Switched to use clay table, capitalize User Name -->
<div class="search-total-label">
	${languageUtil.format(locale, "x-results-for-x", [searchContainer.getTotal(), "<strong>" + htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()) + "</strong>"], false)}
</div>

<table class="table table-autofit table-valign-top table-sm" id="search-results-display-list">
	<#if entries?has_content>
		<tbody>
			<#list entries as entry>
				<tr>
					<#if !entry.isTemporarilyUnavailable()>
						<td>
							<#if entry.isThumbnailVisible()>
								<span class="sticker">
									<span class="sticker-overlay">
										<img
											alt="${languageUtil.get(locale, "thumbnail")}"
											class="sticker-img"
											src="${entry.getThumbnailURLString()}"
										/>
									</span>
								</span>
							<#elseif entry.isUserPortraitVisible() && stringUtil.equals(entry.getClassName(), userClassName)>
								<@liferay_ui["user-portrait"] userId=entry.getAssetEntryUserId() />
							<#elseif entry.isIconVisible()>
								<span class="sticker sticker-rounded sticker-secondary sticker-static">
									<@clay.icon symbol="${entry.getIconId()}" />
								</span>
							</#if>
						</td>

						<td class="table-cell-expand">
							<section class="autofit-section">
								<h6 class="text-neutral-10">
									<a href="${entry.getViewURL()}">
										${entry.getHighlightedTitle()}
									</a>
								</h6>

								<div class="search-results-metadata">
									<p class="list-group-subtext">

										<#if entry.isLocaleReminderVisible()>
											<@liferay_ui["icon"]
												icon="../language/${entry.getLocaleLanguageId()}"
												message=entry.getLocaleReminder()
											/>
										</#if>

									</p>

									<#if entry.isContentVisible()>
										<p class="text-paragraph-sm">
											<span class="subtext-item">
												${entry.getContent()}
											</span>
										</p>
									</#if>

								</div>
							</section>
						</td>
						<td>
							<span class="sticker sticker-secondary sticker-user-icon">
								<span class="sticker-overlay">
									<#if entry.isUserPortraitVisible() >
										<img
												 alt="thumbnail"
												 class="sticker-img"
												 src="${entry.getUserPortraitURLString()}"
												 />
										<#else>
											<@clay.icon symbol="user" />
										</#if>
									</span>
								</span>							
						</td>
						<td class="table-cell-expand-small">
							<div class="autofit-col">
								<#if serviceLocator?? >
									<#assign assetEntryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetEntryLocalService") />
									<#assign assetEntry = assetEntryLocalService.fetchEntry(entry.getClassName(), entry.getClassPK()) />
									<span class="text-paragraph-xs">
										<strong>${assetEntry.getUserName()}</strong>
									</span>
									<span class="text-paragraph-xs">
										Last Modified: ${assetEntry.getModifiedDate()?string("MMM dd, yyyy")}
									</span>
								<#else>
									<#if entry.isCreatorVisible()>
										<span class="text-paragraph-xs">
											<strong>${htmlUtil.escape(entry.getCreatorUserName()?capitalize)}</strong>
										</span>
									</#if>
									<#if entry.isCreationDateVisible()>
										<span class="text-paragraph-xs">
											Created: ${entry.getCreationDateString()?date("MMM dd, yyyy")}
										</span>
									</#if>
								</#if>
							</div>
						</td>
					<#else>
						<td>
							<div class="alert alert-danger">
								<@liferay.language_format
									arguments="result"
									key="is-temporarily-unavailable"
								/>
							</div>
						</td>
					</#if>
				</tr>
			</#list>
		</tbody>
	</#if>
</table>