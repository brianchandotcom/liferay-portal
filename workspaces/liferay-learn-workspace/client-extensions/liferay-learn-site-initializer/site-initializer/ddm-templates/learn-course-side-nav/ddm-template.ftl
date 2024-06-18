<#assign
	groupFriendlyURL = themeDisplay.getScopeGroup().getFriendlyURL()

	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + groupFriendlyURL
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())

	childrenJSONArray = navigationJSONObject.getJSONArray("children")
/>

<div class="learn-article-nav">
	<div class="learn-article-nav-content">
		<#if childrenJSONArray.length() gt 0>
			<ul class="m-0 p-2">
				<#list 0..childrenJSONArray.length()-1 as i>
					<#assign child = childrenJSONArray.getJSONObject(i) />

					<#if i != 0>
						<li class="learn-article-nav-item">
							<a
								class="liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == child.url)?then("selected", "")}"
								href="${child.url}">
								<span class="course-module-number">${i}</span>
								<span>${child.getString("title")}</span>
							</a>
						</li>
					<#else>
						<li class="learn-article-nav-item">
							<a
								class="liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == child.url)?then("selected", "")}"
								href="${child.url}">
								<span>${child.getString("title")}</span>
							</a>
						</li>
					</#if>
				</#list>
			</ul>
		</#if>
	</div>
</div>