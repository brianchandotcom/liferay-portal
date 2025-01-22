if (!themeDisplay.isSignedIn()) {
	document.addEventListener("DOMContentLoaded", function () {
		const bannerSignIn = document.querySelector(".banner-sign-in");
		const iconX = document.querySelector(".icon-x");
		const itemNavContainer = document.querySelector(".lfr-layout-structure-item-navigation-container");
		const learnBanner = document.querySelector(".learn-banner");
		const navigationContainer = document.querySelector(".navigation-container");
		const warpper = document.getElementById("wrapper");

		iconX.addEventListener('click', event => {
			bannerSignIn.style.display = 'none';
			learnBanner.style.marginTop = "0";
			navigationContainer.style.marginTop = "0";
			navigationContainer.style.position = "inherit";
		})
	});
}