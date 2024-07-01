<#assign
	courseData = ""
	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + themeDisplay.getScopeGroup().getFriendlyURL()
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())

	childrenJSONArray1 = navigationJSONObject.getJSONArray("children")
	siblingsJSONArray = navigationJSONObject.getJSONArray("siblings")
/>

<#list 0..siblingsJSONArray.length()-1 as i>
	<#assign sibling = siblingsJSONArray.getJSONObject(i) />
	<#if sibling.getString("title") == "${course.getData()}">
		<#assign courseData = sibling />
		<#break>
	</#if>
</#list>

<div class="learn-article-nav">
	<div class="learn-article-nav-content">
		<#if childrenJSONArray1.length() gt 0>
			<ul class="m-0 p-2">
				<li class="learn-article-nav-item ${(navigationJSONObject.getJSONObject("self").url == courseData.url)?then("selected", "")}">
					<a class="liferay-nav-item" href="${courseData.url}">
						<span>Introduction</span>
					</a>
				</li>

				<#list 0..childrenJSONArray1.length()-1 as i>
					<div>
						<#assign
							child = childrenJSONArray1.getJSONObject(i)

							childrenJSONArray2 = child.getJSONArray("children")
						/>

						<div class="panel-group">
							<div class="panel panel-secondary">
								<button
									aria-controls= "collapsePanel${i}"
									aria-expanded="false"
									class="btn btn-unstyled panel-header panel-header-link collapse-icon collapse-icon-middle collapsed"
									data-target= "#collapsePanel${i}"
									data-toggle="liferay-collapse"
									onclick="togglePanel(this)"
								>
									<span class="panel-title">
										<li class="learn-article-nav-item">
											<div
												class="liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == child.url)?then("selected", "")}"
												href="${child.url}"
												style="display: flex; justify-content: space-between;"
											>
												<div class="nav-item-number-title">
													<div>
														<span class="course-module-number">${i+1}</span>
													</div>

													<span class="course-module-title">${child.getString("title")}</span>
												</div>
											</div>
										</li>
									</span>
									<span class="collapse-icon-closed">
										<svg
											class="lexicon-icon lexicon-icon-angle-right"
											role="presentation"
										>
											<use xlink:href="/o/admin-theme/images/clay/icons.svg#angle-right"></use>
										</svg>
									</span>
									<span class="collapse-icon-open">
										<svg
											class="lexicon-icon lexicon-icon-angle-down"
											role="presentation"
										>
											<use xlink:href="/o/admin-theme/images/clay/icons.svg#angle-down"></use>
										</svg>
									</span>
								</button>

								<div class="panel-collapse collapse" id="collapsePanel${i}">
									<div class="panel-body">
										<#assign lessons = childrenJSONArray2?eval_json />

										<#list lessons as lesson>
											<div class="container-lesson"><div class="course-module-transparent" ></div><a href="${lesson.url}">${lesson.title}</a></div>
										</#list>
									</div>
								</div>
							</div>
						</div>
					</div>
				</#list>
			</ul>
		</#if>
	</div>
</div>

<script>
function togglePanel(button) {
	const courseModuleNumber = button.querySelector('.course-module-number');
	const liferayNavItem = button.querySelector('.liferay-nav-item');

	if (button.getAttribute('aria-expanded') === 'true') {
		button.setAttribute('aria-expanded', 'false');
		courseModuleNumber.classList.remove('highlighted');
		liferayNavItem.classList.remove('highlightedNavItem');
	}
	else {
		button.setAttribute('aria-expanded', 'true');
		courseModuleNumber.classList.add('highlighted');
		liferayNavItem.classList.add('highlightedNavItem');
	}
}
</script>