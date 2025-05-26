<div class="search-results" id="searchResults">
	<#if entries?has_content>
		<#list entries as searchEntry>
			<#assign
				className=searchEntry.getClassName()!""
				classPK=searchEntry.getClassPK()!""
				isJournalArticle=className=="com.liferay.journal.model.JournalArticle"
				isObjectDefinition=className?contains("com.liferay.object.model.ObjectDefinition")
				restArticle=restClient.get("/headless-delivery/v1.0/structured-contents/${classPK}?fields=taxonomyCategoryBriefs&nestedFields=embeddedTaxonomyCategory")
				restObject=restClient.get("/c/p2s3knowledgearticles/${classPK}?nestedFields=embeddedTaxonomyCategory")
				searchEntryContent=searchEntry.getContent()!languageUtil.get(locale, "no-content-preview" , "No content preview" )
				searchEntryTitle=searchEntry.getTitle()!""
			/>

			<#if searchEntryTitle?has_content>
				<div class="align-items-stretch pb-4 search-results-entry">
					<a class="font-weight-bold search-results-entry-title text-decoration-none unstyled" href="${searchEntry.getViewURL()}&highlight=${htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()?url('ISO-8859-1'))}">
						<div class="search-results-entry-header d-flex justify-content-between">
							${searchEntryTitle}
							<div class="search-results-entry-tags">
								<#if isJournalArticle && restArticle.taxonomyCategoryBriefs?has_content>
									<#list restArticle.taxonomyCategoryBriefs as taxonomyCategoryBrief>
										<#assign taxonomyVocabularyName=taxonomyCategoryBrief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name!"N/A" />
										<#if taxonomyVocabularyName=="Resource Type">
											<span class="font-weight-normal label label-secondary label-inverse-light m-0 px-2 text-paragraph-sm">
												${taxonomyCategoryBrief.taxonomyCategoryName}
											</span>
										</#if>
									</#list>
									<#elseif isObjectDefinition>
										<#if restObject.legacy?? && restObject.legacy == true>
											<span class="font-weight-normal label label-secondary label-inverse-light m-0 px-2 text-paragraph-sm">
												<@liferay_ui["message"] key="legacy" />
											</span>
										</#if>
										<#if restObject.taxonomyCategoryBriefs?? && restObject.taxonomyCategoryBriefs?has_content>
											<#list restObject.taxonomyCategoryBriefs as taxonomyCategoryBrief>
												<#assign taxonomyVocabularyName=taxonomyCategoryBrief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name!"N/A" />
												<#if taxonomyVocabularyName=="Resource Type">
													<span class="font-weight-normal label label-secondary label-inverse-light m-0 px-2 text-paragraph-sm">
														${taxonomyCategoryBrief.taxonomyCategoryName}
													</span>
												</#if>
											</#list>
										</#if>
								</#if>
							</div>
						</div>
						<div class="description search-results-entry-content">
							${searchEntryContent}
						</div>
						<#if searchEntry.getPublishedDateString()?has_content>
							<div class="pt-2 published-date">
								${languageUtil.get(locale, "published-date")}: ${searchEntry.getPublishedDateString()}
							</div>
						</#if>
					</a>
				</div>
			</#if>
		</#list>
		<hr class="solid">
		<#else>
			<p class="search-results-empty">
				${languageUtil.format(locale, "no-results-were-found-that-matched-the-keywords-x", htmlUtil.escape(searchResultsPortletDisplayContext.getKeywords()), false)}
			</p>
	</#if>
</div>
		
<style>
.label-inverse-light {
	background-color: var(--color-state-neutral-lighten-2);
	border-color: var(--color-state-neutral-lighten-2);
	color: var(--color-neutral-8);
}

.search-results-entry-tags {
	display: flex;
	gap: 0.5rem;
}
</style>