<#include "${templatesPath}/SVG">

<script>
	let href = window.location.href;

	if (href.endsWith("/")) {
		href = href.substring(0, href.length - 1);
		window.location.assign(href);
	}
</script>

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
		<div class="col-12 col-md-2 doc-nav-wrapper mobile-nav-hide">
			<div class="doc-nav-wrapper-inner">
				<div class="d-md-none mobile-doc-nav-toggler" id="mobileDocNavToggler">${languageUtil.get(locale, "documentation-menu", "Documentation Menu")}
					<button
						aria-label="Expand Documentation Menu" class="btn expand-btn" onclick="javascript:;"
						title="Expand Documentation Menu" type="button">
						<@clay["icon"] symbol="angle-down-small" />
					</button>

					<button
						aria-label="Close Documentation Menu" class="btn collapse-btn" onclick="javascript:;"
						title="Close Documentation Menu" type="button">
						<@clay["icon"] symbol="angle-up-small" />
					</button>
				</div>

				<div class="doc-nav">
					<div class="admonition hide hilighting-alert important" id="highlightAlert">
						<p class="admonition-title">
							<span class="title-text">
								${languageUtil.get(locale, "highlighting", "Highlighting")}

								<span id="highlightTextMatch"></span>
							</span>
						</p>

						<a class="remove-link" href="javascript:;" id="removeHighlightLink">
							${languageUtil.get(locale, "remove-highlighting", "Remove Highlighting")}
						</a>
					</div>

					<#if !topLevelArticle>
						<a class="back-link btn btn-link btn-monospaced d-flex flex-row justify-content-start" href="${breadcrumbLinksJSONArray.getJSONObject(0).url}" id="backLink">
							<svg class="lexicon-icon lexicon-icon-angle-left" role="presentation" viewBox="0 0 512 512">
								<use xlink:href="#angle-left" />
							</svg>
							${languageUtil.get(locale, "go-back", "Go Back")}
						</a>
					</#if>

					<#if (navigationLinks.getData())??>

						<#assign urlTitleLastDirectory =.vars['reserved-article-url-title'].getData()?split("/")?last />

						<ul class="current">
							<#if !topLevelArticle>
								<li class="current parent-level toctree-l1">
									<a class="reference internal" href="${breadcrumbLinksJSONArray.getJSONObject(0).url}">${breadcrumbLinksJSONArray.getJSONObject(0).title}</a>
								</li>
							</#if>

							<#assign navigationLinksJSONArray = jsonFactoryUtil.createJSONArray(navigationLinks.getData()) />

							<#if navigationLinksJSONArray.length() gt 0>
								<#list 0..navigationLinksJSONArray.length()-1 as i>
									<li class="${topLevelArticle?then("toctree-l1", "toctree-l2")} ${(urlTitleLastDirectory == navigationLinksJSONArray.getJSONObject(i).url)?then("current", "")}">
										<a class="reference internal" href="${navigationLinksJSONArray.getJSONObject(i).url}">${navigationLinksJSONArray.getJSONObject(i).title}</a>
									</li>
								</#list>
							</#if>
						</ul>
					</#if>
				</div>
			</div>
		</div>

		<div class="col-12 col-md-10 doc-body">
			<div class="col-12 general-info p-md-0">
				<div class="col-12 info-bar p-0">
					<div class="col-12 col-md-7 offset-md-1 p-0">
						<#if breadcrumbLinksJSONArray??>
							<ul aria-label="breadcrumb navigation" class="article-breadcrumb" role="navigation">
								<li>
									<a href="${groupFriendlyURL}">Liferay Learn</a>
								</li>
								<#if breadcrumbLinksJSONArray.length() gt 0>
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
						</#if>
					</div>

					<div class="actions col-md-2 d-md-block d-none offset-md-1">
						<a
							aria-label="${languageUtil.get(locale, 'give-feedback', 'Give Feedback')}"
							href="https://liferay.dev/c/portal/login?redirect=https://liferay.dev/ask/questions/liferay-learn-feedback/new"
							title="${languageUtil.get(locale, 'give-feedback', 'Give Feedback')}">
							<svg>
								<use xlink:href="#edit"></use>
							</svg>
						</a>
					</div>
				</div>
			</div>

			<div class="col-12 doc-content ${isLandingPage?then("landing-page-container", "")}" id="docContent">
				<div class="row">
					<div class="article-body col-12 col-md-8 language-log">
						<#if (content.getData())??>
							${content.getData()}
						</#if>
						<#if isLandingPage>
							<#include "${templatesPath}/LANDING-PAGE">
						</#if>
						<div class="autofit-padded-no-gutters-x autofit-row help-center-footer">
							<div class="autofit-col">
								<div class="icon-container">
									<svg class="lexicon-icon liferay-waffle-icon" focusable="false" role="presentation" viewBox="0 0 512 512">
										<use xlink:href="#liferay-waffle" />
									</svg>
								</div>
							</div>

							<div class="autofit-col autofit-col-expand">
								<h3>${languageUtil.get(locale, "not-finding-what-you-are-looking-for", "Not finding what you're looking for?")}</h3>

								<p>${languageUtil.get(locale, "pardon-our-dust-as-we-revamp", "Pardon our dust as we revamp and transition our product documentation to this site. If something seems missing, please check Liferay Help Center documentation for Liferay DXP 7.2 and previous versions.")}</p>

								<a href="https://help.liferay.com/hc/en-us/categories/360001749912">
									<strong>${languageUtil.get(locale, "try-liferays-help-center", "Try Liferay's Help Center")}</strong>

									<svg class="lexicon-icon lexicon-icon-shortcut" focusable="false" role="presentation" viewBox="0 0 512 512">
										<use xlink:href="#shortcut" />
									</svg>
								</a>
							</div>
						</div>
					</div>

					<div class="col-md-4 d-none d-sm-block toc-container">
						<ul class="nav nav-stacked toc" id="articleTOC"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>