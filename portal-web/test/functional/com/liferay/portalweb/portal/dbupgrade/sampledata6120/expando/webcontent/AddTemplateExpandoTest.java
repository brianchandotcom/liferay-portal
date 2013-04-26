/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portal.dbupgrade.sampledata6120.expando.webcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTemplateExpandoTest extends BaseTestCase {
	public void testAddTemplateExpando() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/expando-web-content-community/");
				selenium.clickAt("link=Web Content Display Page",
					RuntimeVariables.replace("Web Content Display Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Templates",
					RuntimeVariables.replace("Templates"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Template']",
					RuntimeVariables.replace("Add Template"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_name_en_US']",
					RuntimeVariables.replace("Expando Template Test"));
				selenium.type("//textarea[@id='_15_description_en_US']",
					RuntimeVariables.replace(
						"This is an expando template test."));

				boolean cacheableChecked = selenium.isChecked(
						"_15_cacheableCheckbox");

				if (!cacheableChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_15_cacheableCheckbox']",
					RuntimeVariables.replace("Cacheable Checkbox"));

			case 2:
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				selenium.waitForVisible(
					"//iframe[contains(@src,'select_structure')]");
				selenium.selectFrame(
					"//iframe[contains(@src,'select_structure')]");
				selenium.waitForVisible("//td[2]/a");
				assertEquals(RuntimeVariables.replace("Expando Structure Test"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("Expando Structure Test"));
				selenium.selectFrame("relative=top");
				selenium.waitForText("//a[@id='_15_structureName']",
					"Expando Structure Test");
				assertEquals(RuntimeVariables.replace("Expando Structure Test"),
					selenium.getText("//a[@id='_15_structureName']"));
				selenium.clickAt("//input[@value='Launch Editor']",
					RuntimeVariables.replace("Launch Editor"));
				selenium.waitForVisible("//iframe[@id='_15_xslContentIFrame']");
				selenium.selectFrame("//iframe[@id='_15_xslContentIFrame']");
				Thread.sleep(5000);
				selenium.type("//textarea[@id='_15_plainEditorField']",
					RuntimeVariables.replace(
						"##\n## Do some request handling setup.\n##\n\n#set ($companyId = $getterUtil.getLong($request.theme-display.company-id))\n#set ($locale = $localeUtil.fromLanguageId($request.locale))\n#set ($dateFormatDateTime = $dateFormats.getDateTime($locale))\n\n#set ($renderUrl = $request.render-url)\n#set ($pns = $request.portlet-namespace)\n#set ($cmd = $getterUtil.getString($request.parameters.cmd))\n\n#set ($cur = $getterUtil.getInteger($request.parameters.cur, 1))\n#set ($delta = $getterUtil.getInteger($request.parameters.delta, 5))\n\n#set ($end = $cur * $delta)\n#set ($start = $end - $delta)\n\n<h1>First Expando Bank</h1>\n\n##\n## Define the \"name\" for our ExpandoTable.\n##\n\n#set ($accountsTableName = \"AccountsTable\")\n\n##\n## Get/Create the ExpandoTable to hold our data.\n##\n\n#set ($accountsTable = $expandoTableLocalService.getTable($companyId, $accountsTableName, $accountsTableName))\n#set ($accountsTableId = $accountsTable.getTableId())\n\n#if (!$accountsTable)\n	#set ($accountsTable = $expandoTableLocalService.addTable($companyId, $accountsTableName, $accountsTableName))\n	#set ($accountsTableId = $accountsTable.getTableId())\n\n	#set ($VOID = $expandoColumnLocalService.addColumn($accountsTableId, \"firstName\", 15)) ## STRING\n	#set ($VOID = $expandoColumnLocalService.addColumn($accountsTableId, \"lastName\", 15)) ## STRING\n	#set ($VOID = $expandoColumnLocalService.addColumn($accountsTableId, \"balance\", 5)) ## DOUBLE\n	#set ($VOID = $expandoColumnLocalService.addColumn($accountsTableId, \"modifiedDate\", 3)) ## DATE\n#end\n\n#set ($accountsTableClassNameId = $accountsTable.getClassNameId())\n#set ($columns = $expandoColumnLocalService.getColumns($accountsTableId))\n\n##\n## Check to see if a classPK was passed in the request.\n##\n\n#set ($classPK = $getterUtil.getLong($request.parameters.classPK))\n\n##\n## Check if we have received a form submission?\n##\n\n#if ($cmd.equals('add') || $cmd.equals('update'))\n	##\n	## Let's get the form values from the request.\n	##\n\n	#set ($firstName = $getterUtil.getString($request.parameters.firstName, ''))\n	#set ($lastName = $getterUtil.getString($request.parameters.lastName, ''))\n	#set ($balance = $getterUtil.getDouble($request.parameters.balance, 0.0))\n	#set ($date = $dateTool.getDate())\n\n	##\n	## Validate the params to see if we should proceed.\n	##\n\n	#if ($balance < 50)\n		Please fill the form completely in order to create an account. The minimum amount of cash required to create an account is $50.\n	#elseif (!$firstName.equals('') && !$lastName.equals(''))\n		##\n		## Check to see if it's a new Account.\n		##\n\n		#if ($classPK <= 0)\n			#set ($classPK = $dateTool.getDate().getTime())\n		#end\n\n		#set ($VOID = $expandoValueLocalService.addValues($accountsTableClassNameId, $accountsTableId, $columns, $classPK, {'firstName':$firstName, 'lastName':$lastName, 'balance':\"$balance\", 'modifiedDate':\"${date.getTime()}\"}))\n\n		##\n		## Show a response.\n		##\n\n		#if ($cmd.equals('update'))\n			Thank you, ${firstName}, for updating your account with our bank!\n		#else\n			Thank you, ${firstName}, for creating an account with our bank!\n		#end\n	#else\n		Please fill the form completely in order to create an account. Make sure to till both first and last name fields.\n	#end\n#elseif ($cmd.equals('delete'))\n	##\n	## Delete the specified Row.\n	##\n\n	#if ($classPK > 0)\n		#set ($VOID = $expandoRowLocalService.deleteRow($accountsTableId, $classPK))\n\n		Account deleted!\n\n		#set ($classPK = 0)\n	#end\n#elseif ($cmd.equals('edit'))\n	##\n	## Edit the specified Row.\n	##\n\n	Editting...\n#end\n\n<span style=\"display: block; border-top: 1px solid #CCC; margin: 5px 0px 5px 0px;\"></span>\n\n#if (!$cmd.equals('edit'))\n	##\n	## Now we're into the display logic.\n	##\n\n	<input type=\"button\" value=\"Create Account\" onClick=\"self.location = '${renderUrl}&${pns}cmd=edit';\" />\n\n	\n\n\n\n	<table class=\"taglib-search-iterator\">\n	<tr class=\"results-header\">\n		<th class=\"col-1\">Account Number</th>\n		<th class=\"col-2\">First Name</th>\n		<th class=\"col-3\">Last Name</th>\n		<th class=\"col-4\">Balance</th>\n		<th class=\"col-5\">Modified Date</th>\n		<th class=\"col-6\"><!----></th>\n	</tr>\n\n	##\n	## Get all the current records in our ExpandoTable. We can paginate by passing a\n	## \"begin\" and \"end\" params.\n	##\n\n	#set ($total = $expandoRowLocalService.getRowsCount($accountsTableId))\n	#set ($rows = $expandoRowLocalService.getRows($accountsTableId, $start, $end))\n\n	#foreach($row in $rows)\n		#set ($cssClass = 'results-row')\n\n		#if ($velocityCount % 2 == 0)\n			#set ($cssClass = \"${cssClass} alt\")\n		#end\n\n		#if ($velocityCount == 1)\n			#set ($cssClass = \"${cssClass} first\")\n		#elseif ($velocityCount == $rows.size())\n			#set ($cssClass = \"${cssClass} last\")\n		#end\n\n		##\n		## Get the classPK of this row.\n		##\n\n		#set ($currentClassPK = $row.getClassPK())\n\n		#set ($rowValues = $expandoValueLocalService.getRowValues($row.getRowId()))\n\n		#set ($values = {})\n\n		#foreach ($value in $rowValues)\n			#foreach ($column in $columns)\n				#if ($value.columnId == $column.columnId)\n					#set ($VOID = $values.put($column.name, $value))\n				#end\n			#end\n		#end\n\n		#set ($currentFirstName = $values.firstName.string)\n		#set ($currentLastName = $values.lastName.string)\n		#set ($currentBalance = $values.balance.double)\n		#set ($currentModifiedDate = $values.modifiedDate.date)\n\n		<tr class=\"${cssClass}\">\n			<td class=\"align-left col-1 valign-left\">${currentClassPK}</td>\n\n			<td class=\"align-left col-2 valign-middle\">${currentFirstName}</td>\n\n			<td class=\"align-left col-3 valign-middle\">${currentLastName}</td>\n\n			<td class=\"align-right col-4 valign-middle\">${numberTool.currency($currentBalance)}</td>\n\n			<td class=\"align-left col-5 valign-middle\">${dateFormatDateTime.format($currentModifiedDate)}</td>\n\n			<td class=\"align-right col-6 valign-middle\">\n				<a href=\"${renderUrl}&${pns}cmd=edit&${pns}classPK=${currentClassPK}\">Edit</a> |\n				<a href=\"${renderUrl}&${pns}cmd=delete&${pns}classPK=${currentClassPK}\">Delete</a>\n			</td>\n		</tr>\n	#end\n\n	#if ($total <= 0)\n		<tr>\n			<td colspan=\"5\">No Accounts were found.</td>\n		</tr>\n	#end\n\n	</table>\n\n	<br/>\n\n	#if ($total > $delta)\n		<div style=\"float: right;\">\n			<div>\n				#set ($previous = $cur - 1)\n				#set ($next = $cur + 1)\n\n				#if ($previous > 0)\n					<a href=\"${renderUrl}&${pns}cur=${previous}\" class=\"previous\">\u2039\u2039 #language('previous')</a>\n				#else\n					<span class=\"previous\">\u2039\u2039 #language('previous')</span>\n				#end\n\n				#set ($pagesIteratorBegin = 1)\n				#set ($pagesIteratorEnd = $total / $delta)\n				#if (($total % $delta) > 0)\n					#set ($pagesIteratorEnd = $pagesIteratorEnd + 1)\n				#end\n\n				#foreach ($index in [$pagesIteratorBegin..$pagesIteratorEnd])\n					#if ($index == $cur)\n						#set ($pageNumber = \"<strong>${index}</strong>\")\n					#else\n						#set ($pageNumber = $index)\n					#end\n\n					<a href=\"${renderUrl}&${pns}cur=${index}\" class=\"previous\">${pageNumber}</a>\n				#end\n\n				#if ($next > $cur && $next <= $pagesIteratorEnd)\n					<a href=\"${renderUrl}&${pns}cur=${next}\" class=\"next\">#language('next') \u203a\u203a</a>\n				#else\n					<span class=\"next\">#language('next') \u203a\u203a</span>\n				#end\n			</div>\n		</div>\n	#end\n\n	# of Accounts: ${total}\n#else\n	##\n	## Here we have our input form.\n	##\n\n	#if ($classPK > 0)\n		##\n		## Get the account specific values\n		##\n\n		#set ($rowValues = $expandoValueLocalService.getRowValues($companyId, $accountsTableName, $accountsTableName, $classPK, -1, -1))\n\n		#set ($values = {})\n\n		#foreach ($value in $rowValues)\n			#foreach ($column in $columns)\n				#if ($value.columnId == $column.columnId)\n					#set ($VOID = $values.put($column.name, $value))\n				#end\n			#end\n		#end\n\n		#set ($currentFirstName = $values.firstName.string)\n		#set ($currentLastName = $values.lastName.string)\n		#set ($currentBalance = $values.balance.double)\n	#end\n\n	<form action=\"$renderUrl\" method=\"post\" name=\"${pns}fm10\">\n	<input type=\"hidden\" name=\"${pns}classPK\" value=\"$!{classPK}\" />\n	<input type=\"hidden\" name=\"${pns}cmd\" #if ($classPK > 0) value=\"update\" #else value=\"add\" #end/>\n\n	<table class=\"lfr-table\">\n	<tr>\n		<td>First Name:</td>\n		<td>\n			<input type=\"text\" name=\"${pns}firstName\" value=\"$!{currentFirstName}\" />\n		</td>\n	</tr>\n	<tr>\n		<td>Last Name:</td>\n		<td>\n			<input type=\"text\" name=\"${pns}lastName\" value=\"$!{currentLastName}\" />\n		</td>\n	</tr>\n	<tr>\n		<td>Balance:</td>\n		<td>\n			<input type=\"text\" name=\"${pns}balance\" value=\"$!{numberTool.format($currentBalance)}\" />\n		</td>\n	</tr>\n	</table>\n\n	\n\n\n	<input type=\"submit\" value=\"Save\" />\n	<input type=\"button\" value=\"Cancel\" onclick=\"self.location = '${renderUrl}'\" />\n	</form>\n#end\n\n\n\n\n"));
				selenium.click("//input[@value='Update']");
				selenium.selectFrame("relative=top");
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Expando Template Test"),
					selenium.getText("//tr[3]/td[3]/a"));
				assertEquals(RuntimeVariables.replace(
						"This is an expando template test."),
					selenium.getText("//tr[3]/td[4]/a"));

			case 100:
				label = -1;
			}
		}
	}
}