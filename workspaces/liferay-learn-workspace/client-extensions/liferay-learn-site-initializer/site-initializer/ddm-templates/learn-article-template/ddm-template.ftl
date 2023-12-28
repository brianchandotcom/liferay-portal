<#include "${templatesPath}/SVG">

<script>
	let href = window.location.href;

	if (href.endsWith("/")) {
		href = href.substring(0, href.length - 1);
		window.location.assign(href);
	}
</script>

<style>
	.adt-nav-title.align-items-center.d-flex {
		color: #282934;
		font-weight: 700;
	}

	.adt-submenu-item-link {
		color: #282934;
		display: contents;
	}

	.align-items-baseline .col-10 {
		gap: 1rem;
	}

	.bg-color-1 {
		background-color: #F7F7F8;
	}

	.br-5 {
		border-radius: 0.5rem;
	}

	.br-5.side-nav {
		margin: 0.3rem 1rem;
	}

	.br-13.dropdown-menu {
		overflow-x:hidden;
		width: max-content;
		will-change: transform;
		z-index: 1;
	}

	.br-20 {
		border-radius: 2.0rem;
	}

	.callout-title {
		color: var(--Neutral-Neutral-10, #272833);
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 1.5rem;
		font-style: normal;
		font-weight: 600;
		line-height: 1.75rem;
	}

	.current-level {
		color: #004AD7 !important;
		background-color: #E6EDFB;
	}

	.current-level:hover a {
		background-color: #EDF3FE !important;
		color: ##004AD7 !important;
	}

	.d-flex>.lexicon-icon-angle-left {
		display: block;
		height: 0.6rem;
		transform: rotate(180deg);
		width: 0.6rem;
	}

	.doc-nav>.align-items-center {
		border-bottom: solid;
		border-color: #EAECEE;
	}

	.dropdown-item {
		align-items: center;
		align-self: stretch;
		display: flex;
		gap: 0.75rem;
		padding: 0.75rem;
	}

	.dropdown-item:hover {
		background-color: #EDF3FE;
	}

	.dropdown-menu .row {
		margin: 0 !important;
	}

	.reference:hover {
		color: #0053F0 !important;
	}

	.rounded-10{
	  border-radius: 10px;
	}

	.show #dropdown-products {
		background-color: #EDF3FE !important;
	}

	.show #dropdown-products svg {
		color: var(--color-action-primary-hover);
		transform: rotate(180deg);
	}

	.side-nav>.other-level{
		color: #282934 !important;
		font-size: 1rem;
		font-weight:600;
		width: 100%;
	}

	.side-nav:hover {
		background-color: #EDF3FE !important;
		color: #0053F0 !important;
	}

	.tag-container {
		border-radius: 1.5rem;
		border: 1px solid #0B5FFF;
		background: var(--action-primary-inverted, #FFF);
		padding: 0.25rem 0.75rem;
		gap: 0.25rem;
	}

	.tags-container {
		flex-wrap: wrap;
		font-size: 0.875rem;
	}

	.toctree:hover a {
		background-image: clay-icon(angle-right, $color-action-primary-hover);
		background-position: right 0.8rem top $spacing-md;
		background-repeat: no-repeat;
		background-size: 0.65rem;
		color: $color-neutral-6;
		color: var(--color-action-primary-hover) !important;
	}

	#backLink {
		border-left-width: 0px;
		color: #282934;
	}

	#backLink:hover {
		background-color: #EAECEE;
		transition: box-shadow 0.1s linear, background-color 0.1s linear;
	}

	#dropdown-products:hover {
		background-color: #EDF3FE !important;
	}

	#dropdown-products:hover svg {
		color: var(--color-action-primary-hover);
	}

	#parentTitle {
		color: #282934;
		font-weight: 700;
	}

	#productIcon {
		background-color: #E7EFFF;
		border: 1px solid;
		border-color: #FFFFFF;
		height: 3.25rem;
		width: 3.25rem;
	}

	#productIcon>img {
		height: 3.5rem;
		margin-left: -0.125rem;
		max-width: none;
		width: 3.5rem;
	}

	#productsIcon {
		border: 1px solid;
		border-color: #F7F7F8;
		height: 2.25rem;
		width: 2.25rem;
	}

	#productsIcon>img {
		height: 25px;
		margin-left: 5px;
		max-width: none;
		width: 25px;
	}

	#send-feedback {
		color: #0B5FFF;
		font-family: 'Source Sans Pro', sans-serif;
		font-size: 1rem;
		font-style: normal;
		font-weight: 600;
		line-height: 1.5rem;
		padding-right: 3rem;
		text-align: center;
	}
</style>

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
	groupFriendlyURL = "/web" + themeDisplay.getScopeGroup().getFriendlyURL()
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
	<div class="row">
		<div class="col-12 col-md-2 mobile-nav-hide mt-5">
			<div class="doc-nav-wrapper-inner">
				<div
					class="d-md-none mobile-doc-nav-toggler"
					id="mobileDocNavToggler"
				>
					${languageUtil.get(locale, "documentation-menu", "Documentation Menu")}
					<button
						aria-label="Expand Documentation Menu"
						class="btn expand-btn"
						onclick="javascript:;"
						title="Expand Documentation Menu"
						type="button"
					>
						<@clay["icon"] symbol="angle-down-small" />
					</button>

					<button
						aria-label="Close Documentation Menu"
						class="btn collapse-btn"
						onclick="javascript:;"
						title="Close Documentation Menu"
						type="button"
					>
						<@clay["icon"] symbol="angle-up-small" />
					</button>
				</div>

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
								"image": "/documents/d/guest/analytics_c-svg"
							},
							"commerce": {
								"title": "Commerce",
								"url": "commerce",
								"image": "/documents/d/guest/commerce_product-svg"
							},
							"dxp": {
								"title": "DXP / Portal",
								"url": "dxp",
								"image": "/documents/d/guest/dxp_p-svg"
							},
							"liferay-cloud": {
								"title": "DXP Cloud",
								"url": "liferay-cloud",
								"image": "/documents/d/guest/dxp_c-svg"
							},
							"reference": {
								"title": "Reference",
								"url": "reference",
								"image": "/documents/d/guest/reference-svg"
							}
						}

					currentProduct = {}
					product = product.getData()
				/>

				<#if navigationMenuItems[product].title?has_content>
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
										href="/w/${navigationMenuItems[key].url}/index"
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

	<div class="col-12 col-md-10 doc-body">
		<div class="border-bottom-0 h-auto p-0">
			<div class="mt-3 offset-1">
				<div class="align-items-baseline d-flex justify-content-between">
					<ul
						aria-label="breadcrumb navigation"
						class="article-breadcrumb"
						role="navigation"
					>
						<li>
							<a href="${groupFriendlyURL}"><@clay["icon"] symbol="home-full" /></a>
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

					<div id="send-feedback">
						<a
							class="text-decoration-none"
							href="https://liferay.dev/c/portal/login?redirect=https://liferay.dev/ask/questions/liferay-learn-feedback/new"
						>
							${languageUtil.get(locale, "send-feedback", "Send Feedback")}
							<@clay["icon"] symbol="message-boards" />
						</a>
					</div>
				</div>

				<#list taxonomyVocabularies as vocabulary>
					<div class="align-items-baseline col-10 d-flex mt-2 pl-0">
						<div class="align-items-baseline d-flex flex-wrap mr-2">
							${vocabulary}
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
		</div>

		<div
			class="col-12 doc-content mt-0 ${isLandingPage?then("landing-page-container", "")}"
			id="docContent"
		>
			<div class="overflow-hidden row">
				<div class="article-body col-12 col-md-10 language-log">
					<#if (content.getData())??>
						${content.getData()}
					</#if>

					<#if isLandingPage>
						<#include "${templatesPath}/LANDING-PAGE">
					</#if>

					<hr class="mt-4 separator solid">
					<div class="autofit-padded-no-gutters-x autofit-row border help-center-footer pb-3 pl-3 rounded-10">
						<div class="autofit-col autofit-col-expand">
							<h3 class="callout-title">${languageUtil.get(locale, "not-finding-what-you-are-looking-for", "Not finding what you're looking for?")}</h3>

							<p class="w-50 overflow-auto">${languageUtil.get(locale, "pardon-our-dust-as-we-revamp", "Pardon our dust as we are in the process of revamping the documentation to the site. If something seems missing, cross reference the Liferay Help Center for a more thorough set of documentation on Liferay DXP 7.2 and previous versions.")}</p>

							<a class="text-decoration-none" href="https://help.liferay.com/hc/en-us/categories/360001749912">
								<strong >${languageUtil.get(locale, "try-liferays-help-center", "Try Liferay’s Help Center")}</strong>
								<@clay["icon"] symbol="order-arrow-right" />
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>