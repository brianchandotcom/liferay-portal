<#assign
	journalArticleId = .vars["reserved-article-id"].data
	taxonomyCategoriesMap = {}
	taxonomyCategoryBriefs = restClient.get("/headless-delivery/v1.0/sites/${groupId}/structured-contents/by-key/${journalArticleId}?nestedFields=embeddedTaxonomyCategory").taxonomyCategoryBriefs
	taxonomyVocabularies = []
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

<div class="learn-recipe-categories-tags">
	<#list taxonomyVocabularies as vocabulary>
		<#if vocabulary = "Capability" || vocabulary = "Feature">

			<#assign
				formattedVocabulary = (vocabulary?lower_case?replace(" ", ""))
				searchTerm = request.getParameter("q")!
			/>

			<div class="align-items-baseline ${formattedVocabulary}-tag d-flex mt-2">
				<div class="learn-recipe-category-title mr-2">
					${vocabulary}:
				</div>
				<#list taxonomyCategoriesMap[vocabulary]?sort_by("categoryName") as taxonomyCategory>
					<div class="learn-recipe-category-tag mr-2">
						<a
							class="label tag-container"
							href="/web/liferay-learn/search?q=${searchTerm}&${formattedVocabulary}=${taxonomyCategory.categoryId}"
						>
							<span>${taxonomyCategory.categoryName}</span>
						</a>
					</div>
				</#list>
			</div>
		</#if>

		<#if vocabulary = "Deployment Approach">
			<#assign formattedVocabulary = (vocabulary?lower_case?replace(" ", "")) />

			<div class="align-items-baseline ${formattedVocabulary}-tag d-flex mt-2">
				<#list taxonomyCategoriesMap[vocabulary]?sort_by("categoryName") as taxonomyCategory>
					<div class="learn-recipe-category-tag mr-2">
						<a
							class="label tag-container"
							href="/web/liferay-learn/search?q=${searchTerm}&deployment-approach=${taxonomyCategory.categoryId}"
						>
							<span>${taxonomyCategory.categoryName}</span>
						</a>
					</div>
				</#list>
			</div>
		</#if>
	</#list>
</div>