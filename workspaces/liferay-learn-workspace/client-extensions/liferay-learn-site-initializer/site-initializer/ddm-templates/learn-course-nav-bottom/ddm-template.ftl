<#assign
	navigationJSONObject = jsonFactoryUtil.createJSONObject(navigation.getData())

	nextJSONObject = navigationJSONObject.getJSONObject("next")
	previousJSONObject = navigationJSONObject.getJSONObject("previous")
/>

<a href=${nextJSONObject.url}>
	<div class="course-nav-bottom__banner d-flex">
		<div class="banner-options d-flex">
			<div class="banner-next-container">
				Up next
			</div>

			<div class="banner-title">
				${nextJSONObject.title}
			</div>
		</div>

		<div class="banner-icon">
			<svg
				class="lexicon-icon lexicon-icon-order-arrow-right"
				role="presentation"
				viewBox="0 0 512 512"
			>
				<use xlink:href="/o/admin-theme/images/clay/icons.svg#order-arrow-right"></use>
			</svg>
		</div>
	</div>
</a>

<div class="course-nav-bottom__menu d-flex">
	<div class="menu-previous-lesson d-flex">
			<a href=${previousJSONObject.url}>
			<div class="previous-lesson-icon">
				<svg
				class="lexicon-icon lexicon-icon-order-arrow-left"
				role="presentation"
				viewBox="0 0 512 512"
				>
					<use xlink:href="/o/admin-theme/images/clay/icons.svg#order-arrow-left"></use>
				</svg>
			</div>
		</a>

		<div class="previous-lesson-title">
			Previous Lesson
		</div>
	</div>

	<#if !themeDisplay.isSignedIn()>
		<div class="menu-sign-in">
			<a href="${htmlUtil.escape(themeDisplay.getURLSignIn())}">Sign in</a> to save your progress!
		</div>
	</#if>
</div>

<script>
	document.querySelector('.course-nav-bottom__banner').style.height = document.querySelector('.course-nav-bottom__banner').offsetHeight + "px";
</script>