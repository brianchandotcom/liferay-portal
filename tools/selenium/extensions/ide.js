var gotoLabels= {};

Selenium.prototype.continueFromRow = function( row_num ) {
	if(row_num == undefined || row_num == null || row_num < 0) {
		throw new Error( "Invalid row_num specified." );
	}
	testCase.debugContext.debugIndex = row_num;
}

Selenium.prototype.doDisplayError = function(value, testName, error) {
}

Selenium.prototype.doDownloadTempFile = function(value) {
};

Selenium.prototype.doGoto = Selenium.prototype.doGotolabel;

Selenium.prototype.doGotoIf = function( condition, label ) {
	if( eval(condition) ) this.doGotolabel( label );
}

Selenium.prototype.doGotolabel = function( label ) {
	if( undefined == gotoLabels[label] ) {
		throw new Error( "Specified label '" + label + "' is not found." );
	}
	this.continueFromRow( gotoLabels[ label ] );
};

ZSelenium.prototype.doLabel = function() {
};

Selenium.prototype.doLogDebug = function(value, varName) {
	LOG.debug("DEBUG: " + value);
}

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

Selenium.prototype.doUploadFile = function(locator, value) {
	this.doType(locator, value);
};

Selenium.prototype.doUploadTempFile = function(locator, value) {
	this.doType(locator, "${selenium.output.dir}" + value);
};

Selenium.prototype.doUploadCommonFile = function(locator, value) {
	this.doType(locator, "${basedir.fixed}" + 
		"\\test\\com\\liferay\\portalweb\\dependencies\\" + value);
};

Selenium.prototype.firstNumber = function(value) {
	return parseInt(value.replace(/.*?(\d+).*$/, '$1'), 10);
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

Selenium.prototype.getCurrentDay = function() {
	var date = new Date();

	return d.getDate();
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

Selenium.prototype.isPartialText = function(locator, value) {
	var locationValue = this.getText(locator);
	var index = locationValue.search(value);

	return (index != -1);
};

Selenium.prototype.initialiseLabels = function() {
	gotoLabels = {};
	whileLabels = { ends: {}, whiles: {} };
	var command_rows = [];
	var numCommands = testCase.commands.length;
	for (var i = 0; i < numCommands; ++i) {
		var x = testCase.commands[i];
		command_rows.push(x);
	}
	var cycles = [];
	for( var i = 0; i < command_rows.length; i++ ) {
		if (command_rows[i].type == 'command')
		switch( command_rows[i].command.toLowerCase() ) {
			case "label":
				gotoLabels[ command_rows[i].target ] = i;
				break;
			case "while":
			case "endwhile":
				cycles.push( [command_rows[i].command.toLowerCase(), i] )
				break; 
		}
	}
	var i = 0; 
	while( cycles.length ) {
		if( i >= cycles.length ) {
			throw new Error( "non-matching while/endWhile found" );
		}
		switch( cycles[i][0] ) {
			case "while":
				if( ( i+1 < cycles.length ) &&
					( "endwhile" == cycles[i+1][0] ) ) {
					// pair found
					whileLabels.ends[ cycles[i+1][1] ] = cycles[i][1];
					whileLabels.whiles[ cycles[i][1] ] = cycles[i+1][1];
					cycles.splice( i, 2 );
					i = 0;
				} else ++i;
				break;
			case "endwhile":
				++i;
				break;
		}
	} 
}

// overload the original Selenium reset function
Selenium.prototype.reset = function() {
	// reset the labels
	this.initialiseLabels();
	// proceed with original reset code
	this.defaultTimeout = Selenium.DEFAULT_TIMEOUT;
	this.browserbot.selectWindow("null"); 
	this.browserbot.resetPopups();
}
