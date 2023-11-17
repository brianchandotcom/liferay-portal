const editMode = document.body.classList.contains('has-edit-mode-menu');
const signIn = Liferay.ThemeDisplay.isSignedIn();
var URLredirect = Liferay.ThemeDisplay.getURLHome();

if (!editMode && signIn) {
	window.location.href = '/web/'+ (configuration.groupKey == 'test'? 'test-1' : configuration.groupKey) +'/home';
} else if (editMode) {
	fragmentElement.querySelector('.url-div').classList.remove('d-none');
	fragmentElement.querySelector('.url-link').innerHTML = URLredirect;
}