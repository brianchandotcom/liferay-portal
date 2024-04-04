<#assign
	groupFriendlyURL = themeDisplay.getScopeGroup().getFriendlyURL()
	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + groupFriendlyURL
	journalArticleId = .vars["reserved-article-id"].data
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())
	taxonomyCategoriesMap = {}
	taxonomyCategoryBriefs = restClient.get("/headless-delivery/v1.0/sites/${groupId}/structured-contents/by-key/${journalArticleId}?nestedFields=embeddedTaxonomyCategory").taxonomyCategoryBriefs
	taxonomyVocabularies = []

	childrenJSONArray = navigationJSONObject.getJSONArray("children")
	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
	showChildrenCards = showChildrenCards.getData()?boolean
/>

<#list taxonomyCategoryBriefs as taxonomyCategoryBrief>
	<#assign taxonomyVocabularyName = taxonomyCategoryBrief.embeddedTaxonomyCategory.parentTaxonomyVocabulary.name />

	<#if !taxonomyVocabularies?seq_contains(taxonomyVocabularyName)>
		<#assign taxonomyVocabularies = taxonomyVocabularies + [taxonomyVocabularyName] />
	</#if>

	<#if taxonomyCategoriesMap[taxonomyVocabularyName]?has_content>
		<#assign taxonomyCategoriesMap = taxonomyCategoriesMap +
			{
				taxonomyVocabularyName:
					taxonomyCategoriesMap[taxonomyVocabularyName] + [{
						"categoryId": taxonomyCategoryBrief.taxonomyCategoryId,
						"categoryName": taxonomyCategoryBrief.taxonomyCategoryName
					}]
			}
		/>
	<#else>
		<#assign taxonomyCategoriesMap = taxonomyCategoriesMap +
			{
				taxonomyVocabularyName:
					[{
						"categoryId": taxonomyCategoryBrief.taxonomyCategoryId,
						"categoryName": taxonomyCategoryBrief.taxonomyCategoryName
					}]
			}
		/>
	</#if>
</#list>

<div class="documentation">
	<div class="doc-body">
		<div class="border-bottom-0 h-auto p-0">
			<div>
				<div class="align-items-baseline d-flex justify-content-between">
					<ul
						aria-label="breadcrumb navigation"
						class="article-breadcrumb"
						role="navigation"
					>
						<li>
							<a href="${groupPathFriendlyURLPublic}"><@clay["icon"] symbol="home-full" /></a>
						</li>

						<#if breadcrumbJSONArray?has_content>
							<#list breadcrumbJSONArray.length()-1..0 as i>
								<li>
									<a href='${breadcrumbJSONArray.getJSONObject(i).getString("url")}'>${breadcrumbJSONArray.getJSONObject(i).getString("title")}</a>
								</li>
							</#list>
						</#if>

						<li>
							${navigationJSONObject.getJSONObject("self").getString("title")}
						</li>
					</ul>

					<div id="submit-feedback">
						<a
							class="text-decoration-none"
							href="https://liferay.dev/c/portal/login?redirect=https://liferay.dev/ask/questions/liferay-learn-feedback/new"
						>
							${languageUtil.get(locale, "submit-feedback", "Submit Feedback")}
							<@clay["icon"] symbol="message-boards" />
						</a>
					</div>
				</div>
			</div>
		</div>

		<div class="doc-content d-flex mt-0 p-0 ${showChildrenCards?then('landing-page-container', '')}" id="docContent">
			<div class="article-body language-log p-0">
				<#if (content.getData())??>
					${content.getData()}
				</#if>

				<#if showChildrenCards && childrenJSONArray.length() gt 0>
					<#list childrenJSONArray.length()-1..0 as i>
						<a href='${childrenJSONArray.getJSONObject(i).getString("url")}'>${childrenJSONArray.getJSONObject(i).getString("title")}</a>
					</#list>
				</#if>

				<#list taxonomyVocabularies as vocabulary>
					<div class="align-items-baseline col-10 d-flex mt-2 pl-0">
						<div class="align-items-baseline d-flex flex-wrap mr-2">
							${vocabulary}:
						</div>

						<div class="d-flex font-weight-bold mr-2 tags-container">
							<#list taxonomyCategoriesMap[vocabulary]?sort_by("categoryName") as taxonomyCategory>
								<div class="d-flex">
									<a
										class="align-items-center d-flex label label-primary tag-container"
										href="/search?category=${taxonomyCategory.categoryId}"
									>
										<span class="label-item label-item-expand">${taxonomyCategory.categoryName}</span>
									</a>
								</div>
							</#list>
						</div>
					</div>
				</#list>
			</div>

			<div class="article-page-nav d-none d-sm-block">
				<ul class="nav nav-stacked toc" id="articleTOC"></ul>
			</div>
		</div>
	</div>
</div>