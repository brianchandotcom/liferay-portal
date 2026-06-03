const DEFINE_QUERY_PARAMS = `
var queryParams = '';

if (window.faroConstants && window.faroConstants.locale) {
	queryParams = '?languageId=' + encodeURIComponent(window.faroConstants.locale);
}
`;

class BundleQueryStringPlugin {
	apply(compiler) {
		compiler.hooks.compilation.tap(
			'BundleQueryStringPlugin',
			compilation => {
				if (compilation.mainTemplate.hooks.jsonpScript) {
					compilation.mainTemplate.hooks.jsonpScript.tap(
						'BundleQueryStringPlugin',
						result =>
							DEFINE_QUERY_PARAMS +
							result.replace(
								/(script\.src.*);/g,
								'$1 + queryParams;'
							)
					);
				}
			}
		);
	}
}

module.exports = BundleQueryStringPlugin;