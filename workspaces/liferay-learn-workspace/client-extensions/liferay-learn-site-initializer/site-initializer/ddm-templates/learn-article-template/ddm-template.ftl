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

<#assign
	groupFriendlyURL = themeDisplay.getScopeGroup().getFriendlyURL()
	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + groupFriendlyURL
	isLandingPage = false
	topLevelArticle = true
/>

<#if (breadcrumbLinks.getData())??>
	<#assign breadcrumbLinksJSONArray = jsonFactoryUtil.createJSONArray(breadcrumbLinks.getData()) />

	<#if breadcrumbLinksJSONArray.length() gt 0>
		<#assign topLevelArticle = false />
	</#if>
</#if>

<#if (landingPage.getData())?? && (landingPage.getData() == "true")>
	<#assign isLandingPage = true />
</#if>

<div class="container-fluid documentations main-content" role="main">
	<div class="m-0 row">
		<div class="col-12 col-md-2 mobile-nav-hide p-0">
			<div class="doc-nav-wrapper-inner">
				<#if !topLevelArticle>
					<#assign
						productTitle = breadcrumbLinksJSONArray.getJSONObject(breadcrumbLinksJSONArray.length()-1).title
						productUrl = breadcrumbLinksJSONArray.getJSONObject(breadcrumbLinksJSONArray.length()-1).url
					/>
				<#else>
					<#assign productTitle =.vars["reserved-article-title"].data />
				</#if>

				<#assign
					navigationMenuItems =
						{
							"analytics-cloud": {
								"title": "Analytics Cloud",
								"url": "analytics-cloud",
								"image": "/documents/d${groupFriendlyURL}/analytics_c-svg"
							},
							"commerce": {
								"title": "Commerce",
								"url": "commerce",
								"image": "/documents/d${groupFriendlyURL}/commerce_product-svg"
							},
							"dxp": {
								"title": "DXP / Portal",
								"url": "dxp",
								"image": "/documents/d${groupFriendlyURL}/dxp_p-svg"
							},
							"liferay-cloud": {
								"title": "DXP Cloud",
								"url": "liferay-cloud",
								"image": "/documents/d${groupFriendlyURL}/dxp_c-svg"
							},
							"reference": {
								"title": "Reference",
								"url": "reference",
								"image": "/documents/d${groupFriendlyURL}/reference-svg"
							}
						}

					currentProduct = {}
					product = product.getData()
				/>

				<#if navigationMenuItems[product]?has_content && navigationMenuItems[product].title?has_content>
					<div class="dropdown">
						<div
							class="adt-nav-item bg-color-1 br-5 ml-0 w-100"
							data-toggle="liferay-dropdown"
						>
							<div
								class="adt-nav-text align-items-center br-5 d-flex justify-content-between p-3"
								id="dropdown-products"
							>
								<div>
									<span
										aria-expanded="false"
										aria-haspopup="true"
										class="adt-nav-title align-items-center d-flex"
									>
										<div
											class="align-items-center br-20 d-flex mr-1"
											id="productIcon"
										>
											<img
												class="lexicon-icon lexicon-icon-caret-bottom product-icon mt-0 p-2"
												role="presentation"
												src="${navigationMenuItems[product].image}"
												viewBox="0 0 512 512"
											/>
										</div>

										<div>${navigationMenuItems[product].title}</div>
									</span>
								</div>

								<div>
									<svg
										class="lexicon-icon lexicon-icon-caret-bottom"
										role="presentation"
										viewBox="0 0 512 512"
									>
										<use xlink:href="/o/admin-theme/images/clay/icons.svg#caret-bottom"></use>
									</svg>
								</div>
							</div>
						</div>

						<div class="br-13 dropdown-menu m-0 p-0">
							<div class="m-0 p-3 row">
								<#list navigationMenuItems as key, value>
									<a
										class="adt-submenu-item-link color-black text-decoration-none"
										href="${groupPathFriendlyURLPublic}/w/${navigationMenuItems[key].url}/index"
										tabindex="4"
									>
										<div class="align-items-center br-13 br-5 col-sm-12 d-flex dropdown-item justify-content-between ml-0 mr-0">
											<div>
												<div class="align-items-center d-flex">
													<div
														class="align-items-center br-20 d-flex mr-1"
														id="productsIcon"
													>
														<img
															class="lexicon-icon lexicon-icon-caret-bottom product-icon mt-0 mr-2"
															role="presentation"
															src="${value.image}"height: 25px; margin-left: 5px; max-width: none; width: 25px;
															viewBox="0 0 512 512"
														/>
													</div>

													<b>${value.title}</b>
												</div>
											</div>

											<#if navigationMenuItems[product].url == value.url>
												<div>
													<@clay["icon"] symbol="check" />
												</div>
											</#if>
										</div>
									</a>
								</#list>
							</div>
						</div>
					</div>
				</#if>

				<#assign navigationLinksJSONArray = jsonFactoryUtil.createJSONArray(navigationLinks.getData()) />

				<#if navigationLinksJSONArray.length() gt 0>
					<div class="bg-color-1 br-5 doc-nav mt-3">
						<#if !topLevelArticle>
							<div class="align-items-center d-flex">
								<div class="m-2">
									<a
										class="br-5 p-2"
										href="${breadcrumbLinksJSONArray.getJSONObject(0).url}"
										id="backLink"
									>
										<svg
											class="lexicon-icon lexicon-icon-angle-left"
											role="presentation"
											viewBox="0 0 512 512"
										>
											<use xlink:href="#angle-left" />
										</svg>
									</a>
								</div>

								<div class="align-self-center">
									<a
										class="pl-0 pr-0"
										id="parentTitle"
									>
										${breadcrumbLinksJSONArray.getJSONObject(0).title}
									</a>
								</div>
							</div>
						</#if>

						<#if (navigationLinks.getData())??>
							<#assign urlTitleLastDirectory =.vars['reserved-article-url-title'].getData()?split("/")?last />

							<ul class="current">
								<#if navigationLinksJSONArray.length() gt 0>
									<#list 0..navigationLinksJSONArray.length()-1 as i>
										<li class="br-5 d-flex side-nav ${topLevelArticle?then("toctree", "")} ${(urlTitleLastDirectory == navigationLinksJSONArray.getJSONObject(i).url)?then("current-level", "other-level")}">
											<a
												class="align-items-center br-5 d-flex internal justify-content-between p-2 reference ${(urlTitleLastDirectory == navigationLinksJSONArray.getJSONObject(i).url)?then("current-level", "other-level")}"
												href="${navigationLinksJSONArray.getJSONObject(i).url}"
											>
												${navigationLinksJSONArray.getJSONObject(i).title}
												<#if breadcrumbLinksJSONArray.length() lt 1>
													<div class="d-flex">
														<svg
															class="lexicon-icon lexicon-icon-angle-left"
															role="presentation"
															viewBox="0 0 512 512"
														>
															<use xlink:href="#angle-left" />
														</svg>
													</div>
												</#if>
											</a>
										</li>
									</#list>
								</#if>
							</ul>
						</#if>
					</div>
				</#if>
			</div>
		</div>

		<div class="col-12 col-md-10 justify-content-around p-0 row">
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

								<#if !topLevelArticle>
									<#list breadcrumbLinksJSONArray.length()-1..0 as i>
										<#assign breadcrumbLink = breadcrumbLinksJSONArray.getJSONObject(i)?eval />

										<li>
											<a href="${breadcrumbLink.url}">${breadcrumbLink.title}</a>
										</li>
									</#list>
								</#if>

								<li>
									${.vars['reserved-article-title'].getData()}
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

				<div
					class="doc-content d-flex mt-0 p-0 ${isLandingPage?then("landing-page-container", "")}"
					id="docContent"
				>
					<div class="article-body language-log p-0">
						<#if (content.getData())??>
							${content.getData()}
						</#if>

						<#if isLandingPage>
							<#include "${templatesPath}/LANDING-PAGE">
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
	</div>
</div>