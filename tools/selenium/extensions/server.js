Selenium.prototype.doSendKeys = function(locator, value) {
   if (this.browserbot.controlKeyDown || this.browserbot.altKeyDown || this.browserbot.metaKeyDown) {
        throw new SeleniumError("type not supported immediately after call to controlKeyDown() or altKeyDown() or metaKeyDown()");
    }
        
    var element = this.browserbot.findElement(locator);

        bot.action.type(element, value);
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

Selenium.prototype.isPartialText = function(locator, value) {
	var locationValue = this.getText(locator);
	var index = locationValue.search(value);

	return (index != -1);
};