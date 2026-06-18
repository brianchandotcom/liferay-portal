<div class="lo-search-results">
	<div class="color-neutral-8 lo-search-results-count mb-3 text-right">
		<strong>${searchContainer.getTotal()}</strong> ${languageUtil.get(locale, "results")}
	</div>

	<ul class="list-unstyled lo-search-list">
		<#if entries?has_content>
			<#list entries as entry>
				<li>
					<a class="d-block lo-search-card mb-3 one-card p-4 text-decoration-none" href="${entry.getViewURL()}">
						<h5 class="color-neutral-10 font-weight-semi-bold lo-search-card-title mb-2">
							${entry.getHighlightedTitle()}
						</h5>

						<#if entry.isContentVisible()>
							<p class="lo-search-card-content mb-3">
								${entry.getContent()}
							</p>
						</#if>

						<div class="align-items-center d-flex flex-wrap lo-search-card-meta">
							<#if entry.isCreationDateVisible()>
								<span class="lo-search-card-date">
									${entry.getCreationDateString()}
								</span>
							</#if>

							<#if entry.isAssetCategoriesOrTagsVisible()>
								<#assign
									structuredContent = restClient.get("/headless-delivery/v1.0/structured-contents/" + entry.getClassPK()?c + "?fields=taxonomyCategoryBriefs")
								/>

								<#if structuredContent.taxonomyCategoryBriefs?has_content>
									<span class="lo-badge d-inline-flex flex-wrap">
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