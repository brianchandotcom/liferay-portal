<#assign
	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + themeDisplay.getScopeGroup().getFriendlyURL()
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())

	courseJSONObject = navigationJSONObject.getJSONObject("course")
	modulesJSONArray = navigationJSONObject.getJSONArray("modules")
/>

<div class="learn-course-side-nav">
	<div class="learn-course-nav-content">
		<#if modulesJSONArray.length() gt 0>
			<ul class="m-0 p-2">
				<li class="learn-course-nav-item ${(navigationJSONObject.getJSONObject("self").url == courseJSONObject.url)?then("selected", "")}">
					<a class="liferay-nav-item" href="${courseJSONObject.url}">
						<span>Introduction</span>
					</a>
				</li>

				<#list 0..modulesJSONArray.length()-1 as i>
					<div>
						<#assign modulesJSONObject = modulesJSONArray.getJSONObject(i) />

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
										<li class="learn-course-nav-item">
											<div
												class="liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == modulesJSONObject.url)?then("selected", "")}"
												href="${modulesJSONObject.url}"
												style="display: flex; justify-content: space-between;"
											>
												<div class="nav-item-number-title">
													<div>
														<span class="course-module-number">${i+1}</span>
													</div>

													<span class="course-module-title">${modulesJSONObject.getString("title")}</span>
												</div>
											</div>

											<span class="collapse-icon-closed">
												<svg
													class="lexicon-icon lexicon-icon-angle-right"
													role="presentation"
												>
													<use xlink:href="/o/admin-theme/images/clay/icons.svg#angle-right" />
												</svg>
											</span>
											<span class="collapse-icon-open">
												<svg
													class="lexicon-icon lexicon-icon-angle-down"
													role="presentation"
												>
													<use xlink:href="/o/admin-theme/images/clay/icons.svg#angle-down" />
												</svg>
											</span>
										</li>
									</span>
								</button>

								<div class="panel-collapse collapse" id="collapsePanel${i}">
									<div class="panel-body">
										<#assign lessonsJSONArray = modulesJSONObject.getJSONArray("lessons")?eval_json />
										<#list lessonsJSONArray as lesson>
											<div class="container-lesson">
												<div class="course-module-transparent" />

												<a href="${lesson.url}">${lesson.title}</a>
											</div>
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