<#assign
	groupFriendlyURL = themeDisplay.getScopeGroup().getFriendlyURL()

	groupPathFriendlyURLPublic = themeDisplay.getPathFriendlyURLPublic() + groupFriendlyURL
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())

	childrenJSONArray = navigationJSONObject.getJSONArray("children")

	childrenArrayLength = childrenJSONArray.length()
/>

<div>
	<#if childrenArrayLength gt 0>
		<ul class="m-0 p-2">
			<#list 0..childrenArrayLength-1 as i>
				<#assign child = childrenJSONArray.getJSONObject(i) />

				<#if i = 0>
					<li class="course-bottom-nav-item">
						<a class="d-flex justify-content-between" ${(navigationJSONObject.getJSONObject("self").url == child.url)?then("selected", "")} href="${child.url}">
							<div>
								<span class="course-bottom-nav-cta">Start the Course!</span>
								<span>${child.getString("title")}</span>
							</div>

							<svg fill="none" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg">
								<mask id="right_arrow" height="10" maskUnits="userSpaceOnUse" style="mask-type:alpha" width="19" x="2" y="7">
									<path d="M16.8645 16.4842L20.2207 13.1279C20.727 12.5701 20.7457 11.5858 20.2207 10.9811L16.8645 7.62482C15.2895 6.22795 13.3535 8.36545 14.7176 9.7717L15.5004 10.5545H4.11914C2.12695 10.5545 2.12695 13.5545 4.11914 13.5545H15.5004L14.7176 14.3373C13.316 15.8279 15.416 17.8764 16.8645 16.4842Z" fill="#6B6C7E" />
								</mask>

								<g mask="url(#right_arrow)">
									<rect fill="white" height="24" width="24" />
								</g>
							</svg>
						</a>
					</li>
				</#if>
			</#list>
		</ul>
	</#if>
</div>