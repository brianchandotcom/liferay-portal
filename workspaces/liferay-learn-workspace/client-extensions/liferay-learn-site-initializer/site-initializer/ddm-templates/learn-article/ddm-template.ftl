<script>
	const _addEventListener = (selectors) => {
		var elements = document.querySelectorAll(selectors);

		elements.forEach((element) => {
			element.addEventListener("click", (event) => {
				event.preventDefault();

				const anchorElement = document.getElementById(element.getAttribute("id").replace("toc-", ""));

				if (anchorElement) {
					window.history.pushState(
						{},
						"",
						"#" + element.getAttribute("id").replace("toc-", "")
					);
					scrollToElement(anchorElement);
				}
			});
		});
	}

	const scrollToElement = (element) => {
		if (!element) return;

		window.scrollTo({
			behavior: "smooth",
			top: element.getBoundingClientRect().top + window.scrollY - 190,
		});
	};

	window.addEventListener('load', function() {
		_addEventListener("h1 a, h2 a, h3 a");
		_addEventListener(".toc li a");

		if (window.location.hash) {
			const hashLocation = document.getElementById(window.location.hash.substring(1));

			if (hashLocation) {
				setTimeout(() => {
					scrollToElement(hashLocation);
				}, 100);
			}
		}
	});
</script>

<#assign
	journalArticleId = .vars["reserved-article-id"].data

	structuredContent = restClient.get("/headless-delivery/v1.0/sites/${groupId}/structured-contents/by-key/${journalArticleId}?nestedFields=embeddedTaxonomyCategory")

	showChildrenCards = showChildrenCards.getData()?boolean
	taxonomyCategoriesMap = {}
	taxonomyVocabularies = []
	taxonomyCategoryBriefs = structuredContent.taxonomyCategoryBriefs

	navigationJSONObject = jsonFactoryUtil.createJSONObject(htmlUtil.unescape(navigation.getData()?trim))

	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
	childrenJSONArray = navigationJSONObject.getJSONArray("children")
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

								<#if childJSONObject.getJSONArray("children")?has_content>
									<#assign grandchildrenJSONArray = childJSONObject.getJSONArray("children") />

									<div class="mt-2 subsection">
										<#list 0..grandchildrenJSONArray.length()-1 as j>
											<#assign grandchildJSONObject = grandchildrenJSONArray.getJSONObject(j)! />

											<#if grandchildJSONObject?? && grandchildJSONObject["title"]?has_content && grandchildJSONObject["url"]?has_content>
												<a href="${grandchildJSONObject["url"]!}">
													${grandchildJSONObject["title"]!}
												</a>
											</#if>
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
										href="/search?${vocabulary?lower_case?replace(" ", "-", "r")}=${taxonomyCategory.categoryId}"
									>
										<span>${taxonomyCategory.categoryName}</span>
									</a>
								</div>
							</#list>
						</div>
					</#list>
				</div>

				<div class="article-related-how-to">
					<#setting url_escaping_charset='UTF-8' />

					<#if (structuredContent.keywords?has_content && structuredContent.keywords?size > 0)>
						<#assign
							queryParams = {
								"fields": "dateModified,id,title",
								"filter": "(knowledgeArticleType eq 'howTo') and (status eq 0) and (sourceTeam eq 'Enablement')",
								"pageSize": "3",
								"search": structuredContent.keywords[0],
								"sort": "dateModified:desc"
							}
							queryParts = []
						/>

						<#list queryParams?keys as key>
							<#assign
								value = queryParams[key]

								queryParts = queryParts + ["${key?url}=${value?url}"]
							/>
						</#list>

						<#assign knowledgeArticles = restClient.get("/c/p2s3knowledgearticles/?" + queryParts?join('&')) />

						<#if (knowledgeArticles.totalCount)?has_content && (knowledgeArticles.totalCount > 0)>
							<div class="how-to-container">
								<div class="how-to-container-header">
									${languageUtil.get(locale, 'how-to-related-to-this-article')}
								</div>

								<div class="how-to-cards-container" id="how-to-cards-container">
									<#list knowledgeArticles.items as knowledgeArticle>
											<a class="how-to-card" href="${themeDisplay.getCanonicalURL()}/l/${knowledgeArticle.id}/">
											<div class="how-to-card-header">
												${knowledgeArticle.title!}
											</div>

											<div class="how-to-card-date-published">
												<#assign date = knowledgeArticle.dateModified?datetime("yyyy-MM-dd'T'HH:mm:ss'Z'") />

												${languageUtil.get(locale, 'published-date')}: ${date?string["MMM dd, yy hh:mm a"]}
											</div>
										</a>
									</#list>
								</div>
							</div>
						</#if>
					</#if>
				</div>
			</div>
		</div>
	</div>
</article>