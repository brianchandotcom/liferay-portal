<#assign
	navigationJSONObject = jsonFactoryUtil.createJSONObject(htmlUtil.unescape(navigation.getData()?trim))

	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
/>

<div class="learn-article-nav learn-article-nav-breadcrumb-container">
	<div class="learn-article-breadcrumbs">
		<div class="learn-article-breadcrumbs-content">
			<div class="align-breadcrumbItems-baseline d-flex justify-content-between">
				<ul aria-label="breadcrumb navigation" class="learn-article-breadcrumb" role="navigation">
					<#if breadcrumbJSONArray?? && breadcrumbJSONArray?has_content>

						<#if breadcrumbJSONArray.length() lt 2>
							<li>
								<a href="/"><@clay["icon"] symbol="home-full" /></a>
							</li>

							<#list 0..(breadcrumbJSONArray.length() - 1) as i>
								<#assign breadcrumbItem = breadcrumbJSONArray.getJSONObject(i)!"" />

								<#if breadcrumbItem?? && breadcrumbItem?has_content>
									<li>
										<a href='${(breadcrumbItem.getString("url"))!"#"}'>
											${(breadcrumbItem.getString("title"))!""}
										</a>
									</li>
								</#if>
							</#list>
						<#else>
							<li>
								<a href='${breadcrumbJSONArray.getJSONObject(0).getString("url")}'>
									<span class="ellipsis-breadcrumb"></span>
								</a>
							</li>

							<#list (0..1)?reverse as i>
								<#assign breadcrumbItem = breadcrumbJSONArray.getJSONObject(i)! />

								<#if breadcrumbItem?? && breadcrumbItem?has_content>
									<li>
										<a href='${(breadcrumbItem.getString("url"))!"#"}'>
											${(breadcrumbItem.getString("title"))!""}
										</a>
									</li>
								</#if>
							</#list>
						</#if>
					</#if>
					<li>
						${navigationJSONObject.getJSONObject("self").getString("title")}
					</li>
				</ul>
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