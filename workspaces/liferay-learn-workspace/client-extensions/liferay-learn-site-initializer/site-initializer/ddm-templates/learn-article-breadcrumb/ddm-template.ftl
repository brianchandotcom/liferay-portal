<#assign
	navigationJSONObject = jsonFactoryUtil.createJSONObject(htmlUtil.unescape(navigation.getData()?trim))

	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
/>

<#macro renderBreadcrumbItem i>
	<#assign breadcrumbItem = breadcrumbJSONArray.getJSONObject(i)!"" />

	<#if breadcrumbItem?? && breadcrumbItem?has_content>
		<li>
			<a href='${breadcrumbItem.getString("url")!"#"}'>
				${breadcrumbItem.getString("title")!""}
			</a>
		</li>
	</#if>
</#macro>

<div class="learn-article-nav learn-article-nav-breadcrumb-container">
	<div class="learn-article-breadcrumbs">
		<div class="learn-article-breadcrumbs-content">
			<div class="align-breadcrumbItems-baseline d-flex justify-content-between">
					<#if breadcrumbJSONArray?? && breadcrumbJSONArray?has_content>
						<ul aria-label="breadcrumb navigation" class="learn-article-breadcrumb" role="navigation">
							<#if breadcrumbJSONArray.length() lt 2>
								<li>
									<a href="/"><@clay["icon"] symbol="home-full" /></a>
								</li>

								<#list 0..(breadcrumbJSONArray.length() - 1) as i>
									<@renderBreadcrumbItem i=i />
								</#list>
							<#else>
								<li>
									<a class="ellipsis-breadcrumb" href='${breadcrumbJSONArray.getJSONObject(0).getString("url")}'>
										<@clay["icon"] symbol="ellipsis-h" />
									</a>
								</li>

								<#list (0..1)?reverse as i>
									<@renderBreadcrumbItem i=i />
								</#list>
							</#if>

							<li>
								${navigationJSONObject.getJSONObject("self").getString("title")}
							</li>
						</ul>
					</#if>
			</div>
		</div>
	</div>

	<div class="submit-feedback-button">
		<a class="text-decoration-none" href="https://discuss.liferay.com/">
			${languageUtil.get(locale, "submit-feedback", "Submit Feedback")}
			<@clay["icon"] symbol="message-boards" />
		</a>
	</div>
</div>

<style>
	.ellipsis-breadcrumb svg {
		height: 1rem;
		width: 1rem;
	}

	.learn-article-breadcrumb {
		display: flex;
		flex-wrap: wrap;
		gap: 2px;
	}

	.learn-article-breadcrumb li {
		color: #6C6C75;
		font-size: 13px;
		font-weight: 400;
	}

	.learn-article-breadcrumb li a {
		color: #6C6C75;
		font-size: 13px;
		font-weight: 400;
	}

	.learn-article-breadcrumb li + li::before {
		content: "/";
		padding: 0 8px;
	}

	.learn-article-breadcrumb li:nth-child(2),
	.learn-article-breadcrumb li:nth-child(2) a {
		font-weight: 600;
	}

	.learn-article-nav-breadcrumb-container {
		align-items: center;
		display: flex;
		justify-content: space-between;
		padding: 0 1.75rem;
	}

	@media(max-width: 1024px) {
		.learn-article-nav-breadcrumb-container {
			margin-bottom: 2.5rem;
			padding: 0;
		}
	}
</style>