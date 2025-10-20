<#assign
	navigationJSONObject = jsonFactoryUtil.createJSONObject(htmlUtil.unescape(navigation.getData()?trim))

	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
	childrenJSONArray = navigationJSONObject.getJSONArray("children")
	parentJSONObject = navigationJSONObject.getJSONObject("parent")
	siblingsJSONArray = navigationJSONObject.getJSONArray("siblings")
/>

<div class="learn-article-nav">
	<div class="learn-article-nav-content">
		<#if parentJSONObject?has_content && parentJSONObject.getString("url")?has_content>
			<#if breadcrumbJSONArray.length() gt 1>
				<div class="learn-article-nav-item learn-article-nav-parent menu-trigger menu-trigger-capabilities">
					<div class="mr-2 menu-toggler-icon">
						<a href='${parentJSONObject.getString("url")}'>
							<svg
								class="lexicon-icon lexicon-icon-angle-left"
								role="presentation"
								viewBox="0 0 512 512">
								<use xlink:href="/o/admin-theme/images/clay/icons.svg#angle-left"></use>
							</svg>
						</a>
					</div>

					<span>
						${parentJSONObject.getString("title")}
					</span>
					<span class="liferay-nav-item-right-arrow"></span>
				</div>
			</#if>
		</#if>

		<#if childrenJSONArray.length() gt 0>
			<#if breadcrumbJSONArray.length() lt 2>
				<div class="menu-trigger menu-trigger-documentation">
					<div class="table-of-contents-documentation">
						<span>
							${languageUtil.get(locale, "table-of-contents", "Table of Contents")}
						</span>
						<span class="liferay-nav-item-right-arrow"></span>
					</div>
				</div>
			</#if>

			<ul class="m-0 menu-nav-items-documentation p-2">
				<#list 0..childrenJSONArray.length()-1 as i>
					<li class="learn-article-nav-item">
						<a
							class='liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == childrenJSONArray.getJSONObject(i).url)?then("selected", "")}'
							href="${childrenJSONArray.getJSONObject(i).url}"
						>
							<span>${childrenJSONArray.getJSONObject(i).getString("title")}</span>

							<#if breadcrumbJSONArray.length() lt 2>
								<span class="liferay-nav-item-right-arrow "></span>
							</#if>
						</a>
					</li>
				</#list>
			</ul>
		<#elseif siblingsJSONArray.length() gt 0>
			<ul class="m-0 menu-nav-items-capabilities p-2">
				<#list 0..siblingsJSONArray.length()-1 as i>
					<li class="learn-article-nav-item">
						<a
							class='liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == siblingsJSONArray.getJSONObject(i).url)?then("selected", "")}'
							href="${siblingsJSONArray.getJSONObject(i).url}"
						>
							<span>${siblingsJSONArray.getJSONObject(i).getString("title")}</span>
						</a>
					</li>
				</#list>
			</ul>
		</#if>
	</div>
</div>

<script>
	const triggers = document.querySelectorAll(
		'.menu-trigger-capabilities, .menu-trigger-documentation'
	);

	triggers.forEach(trigger => {
		trigger.addEventListener('click', event => {
			if (window.innerWidth <= 1024) {
				event.preventDefault();

				const parent = trigger.closest('.learn-article-nav-content');

				const targetMenu = parent ? parent.querySelector('ul') : null;

				trigger.classList.toggle('show');
				if (targetMenu) {
					targetMenu.classList.toggle('show');
				}

				trigger.classList.toggle('liferay-nav-item-border',
				trigger.classList.contains('show'));
			}
		});
	});
</script>