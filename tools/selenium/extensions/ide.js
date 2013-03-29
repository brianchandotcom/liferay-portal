var gotoLabels = {};

Selenium.prototype.continueFromRow = function(rowNum) {
	if ((rowNum == null) || (rowNum == undefined) || (rowNum < 0)) {
		alert("Invalid row number");

		throw new Error;
	}

	testCase.debugContext.debugIndex = rowNum;
};

Selenium.prototype.doDownloadTempFile = function(value) {
};

Selenium.prototype.isXMLText = function(locator, text) {

	var source = this.browserbot.getDocument().xml;

	if (source == null) {
		source = new XMLSerializer().serializeToString(
			this.browserbot.getDocument());
	}

	var x = source.indexOf("<html");

	source = source.substring(x);

	var parser = new DOMParser();

	var xml = parser.parseFromString(source, "text/html");

	var xpathResult = xml.evaluate(
		locator, xml, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null);

	if (xpathResult.singleNodeValue) {
		var element = xpathResult.singleNodeValue;

		var nodeText = element.textContent;

		return nodeText == text;
	}
	else {
		return false;
	}
};

Selenium.prototype.doGotoIf = function(condition, label) {
	if (eval(condition)) {
		if (gotoLabels[label] == undefined) {
			alert("Unable to find label " + label);

			throw new Error;
		}

		this.continueFromRow(gotoLabels[label]);
	}
};

Selenium.prototype.doLabel = function() {
};

Selenium.prototype.doMakeVisible = function(locator) {
	var xPathResult = document.evaluate(locator, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null);

	if (xPathResult.singleNodeValue) {
		var element = xPathResult.singleNodeValue;

		element.style.display = "inline-block";
		element.style.overflow = "visible";
		element.style.visibility = "visible";
	}
};

Selenium.prototype.doSetBrowserOption = function(value) {
};

Selenium.prototype.doStoreFirstNumber = function(locator, value) {
	storedVars[value] = this.getFirstNumber(locator);
};

Selenium.prototype.doStoreFirstNumberIncrement = function(locator, value) {
	storedVars[value] = this.getFirstNumberIncrement(locator);
};

Selenium.prototype.doStoreNumberDecrement = function(expression, variableName) {
	storedVars[variableName] = this.getNumberDecrement(expression);
};

Selenium.prototype.doStoreNumberIncrement = function(expression, variableName) {
	storedVars[variableName] = this.getNumberIncrement(expression);
};

Selenium.prototype.doTypeFrame = function(locator, value) {
	this.doSelectFrame(locator);

	this.doRunScript("document.body.innerHTML = '" + value + "';");

	this.doSelectFrame("relative=top");
};

Selenium.prototype.doUploadCommonFile = function(locator, value) {
	this.doType(
		locator, "${basedir.double.slash}\\test\\functional\\com\\liferay\\" +
			"portalweb\\dependencies\\" + value);
};

Selenium.prototype.doUploadFile = function(locator, value) {
	this.doType(locator, value);
};

Selenium.prototype.doUploadTempFile = function(locator, value) {
	this.doType(locator, "${selenium.output.dir}" + value);
};

Selenium.prototype.firstNumber = function(value) {
	return parseInt(value.replace(/.*?(\d+).*$/, '$1'), 10);
};

Selenium.prototype.getCurrentDay = function() {
	var date = new Date();

	return date.getDate();
};

Selenium.prototype.getCurrentMonth = function() {
	var date = new Date();

	var month = new Array(12);

	month[0] = "January";
	month[1] = "February";
	month[2] = "March";
	month[3] = "April";
	month[4] = "May";
	month[5] = "June";
	month[6] = "July";
	month[7] = "August";
	month[8] = "September";
	month[9] = "October";
	month[10] = "November";
	month[11] = "December";

	return month[date.getMonth()];
};

Selenium.prototype.getCurrentYear = function() {
	var date = new Date();

	return date.getFullYear();
};

Selenium.prototype.getFirstNumber = function(locator) {
	var locationValue = this.getText(locator);

	return this.firstNumber(locationValue);
};

Selenium.prototype.getFirstNumberIncrement = function(locator) {
	var locationValue = this.getText(locator);

	return this.firstNumber(locationValue) + 1;
};

Selenium.prototype.getNumberDecrement = function(expression) {
	return parseInt(expression) - 1;
};

Selenium.prototype.getNumberIncrement = function(expression) {
	return parseInt(expression) + 1;
};

Selenium.prototype.initialiseLabels = function() {
	gotoLabels = {};

	var commands = testCase.commands.length;

	for (var i = 0; i < commands; i++) {
		var row = testCase.commands[i];

		if ((row.type == 'command') && (row.command.toLowerCase() == "label")) {
			gotoLabels[row.target] = i;
		}
	}
};

Selenium.prototype.isPartialText = function(locator, pattern) {
	var value = this.getText(locator);

	var index = value.search(pattern);

	if (index != -1) {
		return true;
	}

	return false;
};

Selenium.prototype.reset = function() {
	this.initialiseLabels();

	this.defaultTimeout = Selenium.DEFAULT_TIMEOUT;

	this.browserbot.selectWindow("null");

	this.browserbot.resetPopups();
};