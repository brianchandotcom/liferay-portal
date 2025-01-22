var mode = getParameterByName("p_l_mode");

if (mode && mode == "edit") {
	console.debug("edit mode found");
}
else {
	console.debug("redirecting => ", configuration.redirectLocation);
  window.location.href=configuration.redirectLocation;
}

function getParameterByName(name, url = window.location.href) {
	name = name.replace(/[\[\]]/g, '\\$&');
  
	var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'), results = regex.exec(url);
	
	if (!results) 
		return null;
	if (!results[2]) return '';
		return decodeURIComponent(results[2].replace(/\+/g, ' '));
}