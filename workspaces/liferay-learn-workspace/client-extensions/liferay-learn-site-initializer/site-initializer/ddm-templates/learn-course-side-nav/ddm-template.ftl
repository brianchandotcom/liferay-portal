<#assign
	courseData = ""
	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + themeDisplay.getScopeGroup().getFriendlyURL()
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())

	childrenJSONArray = navigationJSONObject.getJSONArray("children")
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
		<#if childrenJSONArray.length() gt 0>
			<ul class="m-0 p-2">
			  	<li class="learn-article-nav-item ${(navigationJSONObject.getJSONObject("self").url == courseData.url)?then("selected", "")}">
					<a class="liferay-nav-item" href="${courseData.url}">
						<span>Introduction</span>
					</a>
				</li>
				<#list 0..childrenJSONArray.length()-1 as i>
					<#assign child = childrenJSONArray.getJSONObject(i) />

					<li class="learn-article-nav-item">
						<a class="liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == child.url)?then("selected", "")}" href="${child.url}">
							<span class="course-module-number">${i + 1}</span>
							<span>${child.getString("title")}</span>
						</a>
					</li>
				</#list>
			</ul>
		</#if>
	</div>
</div>