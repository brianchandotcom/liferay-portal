export default {

	//
	// LXC
	//

	'com.liferay.lxc.dxp.domains': 'localhost:8080',
	'com.liferay.lxc.dxp.mainDomain': 'localhost:8080',
	'com.liferay.lxc.dxp.server.protocol': 'http',

	//
	// OAuth
	//

	'liferay.oauth.application.external.reference.codes':
		'liferay-sample-node-oauth-application-user-agent',

	//
	// Other
	//

	'configTreePath': '/etc/liferay/lxc',
	'readyPath': '/ready',
	'server.port': 3001,
};
