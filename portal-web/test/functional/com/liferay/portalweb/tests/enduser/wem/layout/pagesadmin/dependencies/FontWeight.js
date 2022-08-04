Liferay.once('endNavigate', () => {
    document.evaluate("//*[@data-qa-id='headerTitle']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.fontWeight = "200";
});

window.addEventListener(
	'load',
	() => {
        document.evaluate("//*[@data-qa-id='headerTitle']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.fontWeight = "200";
    },
	{once: true}
);