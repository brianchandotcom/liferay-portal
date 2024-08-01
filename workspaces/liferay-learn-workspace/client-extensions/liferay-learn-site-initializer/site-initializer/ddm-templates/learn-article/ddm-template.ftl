<#assign
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

<article class="learn-article">
	<div class="d-flex flex-column">
		<div class="learn-article-breadcrumbs">
			<div class="learn-article-breadcrumbs-content">
				<div class="align-items-baseline d-flex justify-content-between mb-3">
					<ul
						aria-label="breadcrumb navigation"
						class="learn-article-breadcrumb"
						role="navigation"
					>
						<li>
							<a href="/"><@clay["icon"] symbol="home-full" /></a>
						</li>

						<#if breadcrumbJSONArray.length() gt 0>
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

					<div class="submit-feedback">
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

		<div class="learn-article-wrapper">
			<div class="language-log learn-article-content">
				<#if (content.getData())??>
					${content.getData()}
				</#if>

				<#if showChildrenCards && childrenJSONArray.length() gt 0>
					<div class="learn-card-container">
						<#list 0..childrenJSONArray.length()-1 as i>
							<#assign childJSONObject = childrenJSONArray.getJSONObject(i) />

							<div class="learn-card">
								<a href="${childJSONObject.getString("url")}">
									<h4>${childJSONObject.getString("title")}</h4>
								</a>

								<#if childJSONObject.getJSONArray("children")?? && childJSONObject.getJSONArray("children").length() gt 0>
									<#assign grandchildrenJSONArray = childJSONObject.getJSONArray("children") />

									<div class="mt-2 subsection">
										<#list 0..grandchildrenJSONArray.length()-1 as j>
											<#assign grandchildJSONObject = grandchildrenJSONArray.getJSONObject(j) />

											<a href="${grandchildJSONObject.getString("url")}">
												${grandchildJSONObject.getString("title")}
											</a>
										</#list>
									</div>
								</#if>
							</div>
						</#list>
					</div>
				</#if>

				<div class="learn-article-categories-tags">
					<#list taxonomyVocabularies as vocabulary>
						<div class="align-items-baseline d-flex mt-2">
							<div class="learn-article-category-title mr-2">
								${vocabulary}:
							</div>
							<#list taxonomyCategoriesMap[vocabulary]?sort_by("categoryName") as taxonomyCategory>
								<div class="learn-article-category-tag mr-2">
									<a
										class="label tag-container"
										href="/search?category=${taxonomyCategory.categoryId}"
									>
										<span>${taxonomyCategory.categoryName}</span>
									</a>
								</div>
							</#list>
						</div>
					</#list>
				</div>
			</div>

			<div class="learn-article-page-nav">
				<ul class="nav nav-stacked toc" id="articleTOC"></ul>
			</div>
		</div>
	</div>
</article>