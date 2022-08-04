Liferay.once('endNavigate', () => {
    document.evaluate("//*[@data-qa-id='headerTitle']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.style.fontFamily = "Times New Roman";
});

window.addEventListener(
	'load',
	() => {
        document.evaluate("//*[@data-qa-id='headerTitle']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.style.fontFamily = "Times New Roman";
    },
	{once: true}
);