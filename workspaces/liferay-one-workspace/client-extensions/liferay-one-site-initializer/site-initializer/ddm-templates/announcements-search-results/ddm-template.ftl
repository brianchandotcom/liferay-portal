<div class="lo-announcements-results">
	<div class="color-neutral-8 lo-announcements-results-count mb-3 text-right">
		<strong>${searchContainer.getTotal()}</strong> ${languageUtil.get(locale, "announcements")}
	</div>

	<ul class="list-unstyled lo-announcements-list">
		<#if entries?has_content>
			<#list entries as entry>
				<li>
					<a class="d-block lo-announcement-card mb-3 one-card p-4 text-decoration-none" href="${entry.getViewURL()}">
						<h5 class="color-neutral-10 font-weight-semi-bold lo-announcement-card-title mb-2">
							${entry.getHighlightedTitle()}
						</h5>

						<#if entry.isContentVisible()>
							<p class="lo-announcement-card-content mb-3">
								${entry.getContent()}
							</p>
						</#if>

						<div class="align-items-center d-flex flex-wrap lo-announcement-card-meta">
							<#if entry.isCreationDateVisible()>
								<span class="lo-announcement-card-date">
									${entry.getCreationDateString()}
								</span>
							</#if>

							<#if entry.isAssetCategoriesOrTagsVisible()>
								<#assign
									structuredContent = restClient.get("/headless-delivery/v1.0/structured-contents/" + entry.getClassPK()?c + "?fields=taxonomyCategoryBriefs")
								/>

								<#if structuredContent.taxonomyCategoryBriefs?has_content>
									<span class="d-inline-flex flex-wrap lo-badge">
										<#list structuredContent.taxonomyCategoryBriefs as taxonomyCategoryBrief>
											<span class="lo-badge-pill">${taxonomyCategoryBrief.taxonomyCategoryName}</span>
										</#list>
									</span>
								</#if>
							</#if>
						</div>
					</a>
				</li>
			</#list>
		</#if>
	</ul>
</div>