if (!themeDisplay.isSignedIn()) {
	document.addEventListener("DOMContentLoaded", function () {
		const bannerSignIn = document.querySelector(".banner-sign-in-lesson");
		const iconX = document.querySelector(".icon-x-lesson");
		
		iconX.addEventListener('click', event => {
			bannerSignIn.style.display = 'none';
		});
	});
}
