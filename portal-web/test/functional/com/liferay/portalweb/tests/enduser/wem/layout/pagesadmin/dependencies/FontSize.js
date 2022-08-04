Liferay.once('endNavigate', () => {
    document.evaluate("//*[@data-qa-id='headerTitle']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.style.fontSize = "20px";
});

window.addEventListener(
	'load',
	() => {
        document.evaluate("//*[@data-qa-id='headerTitle']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.style.fontSize = "20px";
    },
	{once: true}
);