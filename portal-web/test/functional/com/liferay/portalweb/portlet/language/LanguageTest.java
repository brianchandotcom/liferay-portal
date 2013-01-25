/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.language;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LanguageTest extends BaseTestCase {
	public void testLanguage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Language Test Page",
			RuntimeVariables.replace("Language Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//img[@alt='\u0627\u0644\u0639\u0631\u0628\u064a\u0629 (\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629) [Beta]']",
			RuntimeVariables.replace(
				"\u0628\u064a\u0629 (\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629 (\u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u0623\u0636\u0641"));
		selenium.clickAt("//img[@alt='Basque (Spain) [Beta]']",
			RuntimeVariables.replace("Basque (Spain) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Gehitu"));
		selenium.clickAt("//img[@alt='\u0431\u044a\u043b\u0433\u0430\u0440\u0441\u043a\u0438 (\u0411\u044a\u043b\u0433\u0430\u0440\u0438\u044f) [Beta]']",
			RuntimeVariables.replace(
				"\u0431\u044a\u043b\u0433\u0430\u0440\u0441\u043a\u0438 (\u0411\u044a\u043b\u0433\u0430\u0440\u0438\u044f) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u0414\u043e\u0431\u0430\u0432\u044f\u043d\u0435"));
		selenium.clickAt("//img[@alt='catal\u00e0 (Andorra) [Beta]']",
			RuntimeVariables.replace("catal\u00e0 (Andorra) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Afegeix"));
		selenium.clickAt("//img[@alt='catal\u00e0 (Espanya) [Beta]']",
			RuntimeVariables.replace("catal\u00e0 (Espanya) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Afegeix"));
		selenium.clickAt("//img[@alt='\u4e2d\u6587 (\u4e2d\u56fd)']",
			RuntimeVariables.replace("\u4e2d\u6587 (\u4e2d\u56fd)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u6dfb\u52a0"));
		selenium.clickAt("//img[@alt='\u4e2d\u6587 (\u53f0\u7063) [Beta]']",
			RuntimeVariables.replace("\u4e2d\u6587 (\u53f0\u7063) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u589e\u52a0"));
		selenium.clickAt("//img[@alt='\u010de\u0161tina (\u010cesk\u00e1 republika) [Beta]']",
			RuntimeVariables.replace(
				"\u010de\u0161tina (\u010cesk\u00e1 republika) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("P\u0159idat"));
		selenium.clickAt("//img[@alt='Nederlands (Nederland) [Beta]']",
			RuntimeVariables.replace("Nederlands (Nederland) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Toevoegen"));
		selenium.clickAt("//img[@alt='English (United States)']",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Add"));
		selenium.clickAt("//img[@alt='English (United Kingdom) [Beta]']",
			RuntimeVariables.replace("English (United Kingdom) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Add"));
		selenium.clickAt("//img[@alt='Eesti (Eesti) [Beta]']",
			RuntimeVariables.replace("Eesti (Eesti) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Lisa"));
		selenium.clickAt("//img[@alt='suomi (Suomi) [Beta]']",
			RuntimeVariables.replace("suomi (Suomi) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Lis\u00e4\u00e4"));
		selenium.clickAt("//img[@alt='fran\u00e7ais (France) [Beta]']",
			RuntimeVariables.replace("fran\u00e7ais (France) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Ajouter"));
		selenium.clickAt("//img[@alt='Deutsch (Deutschland)']",
			RuntimeVariables.replace("Deutsch (Deutschland)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Hinzuf\u00fcgen"));
		selenium.clickAt("//img[@alt='\u0395\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac (\u0395\u03bb\u03bb\u03ac\u03b4\u03b1) [Beta]']",
			RuntimeVariables.replace(
				"\u0395\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac (\u0395\u03bb\u03bb\u03ac\u03b4\u03b1) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u03a0\u03c1\u03bf\u03c3\u03b8\u03ae\u03ba\u03b7"));
		selenium.clickAt("//img[@alt='\u05e2\u05d1\u05e8\u05d9\u05ea (\u05d9\u05e9\u05e8\u05d0\u05dc) [Beta]']",
			RuntimeVariables.replace(
				"\u05e2\u05d1\u05e8\u05d9\u05ea (\u05d9\u05e9\u05e8\u05d0\u05dc) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u05d4\u05d5\u05e1\u05e3"));
		selenium.clickAt("//img[@alt='magyar (Magyarorsz\u00e1g)']",
			RuntimeVariables.replace("magyar (Magyarorsz\u00e1g)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u00daj"));
		selenium.clickAt("//img[@alt='Bahasa Indonesia (Indonesia) [Beta]']",
			RuntimeVariables.replace("Bahasa Indonesia (Indonesia) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Menambahkan"));
		selenium.clickAt("//img[@alt='italiano (Italia) [Beta]']",
			RuntimeVariables.replace("italiano (Italia) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Aggiungi"));
		selenium.clickAt("//img[@alt='\u65e5\u672c\u8a9e (\u65e5\u672c) [Beta]']",
			RuntimeVariables.replace("\u65e5\u672c\u8a9e (\u65e5\u672c) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("\u8ffd\u52a0"));
		selenium.clickAt("//img[@alt='\ud55c\uad6d\uc5b4 (\ub300\ud55c\ubbfc\uad6d) [Beta]']",
			RuntimeVariables.replace(
				"\ud55c\uad6d\uc5b4 (\ub300\ud55c\ubbfc\uad6d) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\ucd94\uac00\ud558\uc2ed\uc2dc\uc694"));
		selenium.clickAt("//img[@alt='Norwegian Bokm\u00e5l (Norge) [Beta]']",
			RuntimeVariables.replace("Norwegian Bokm\u00e5l (Norge) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Legg til"));
		selenium.clickAt("//img[@alt='Persian (Iran) [Beta]']",
			RuntimeVariables.replace("Persian (Iran) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u0627\u0636\u0627\u0641\u0647 \u06a9\u0631\u062f\u0646"));
		selenium.clickAt("//img[@alt='polski (Polska) [Beta]']",
			RuntimeVariables.replace("polski (Polska) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Dodaj"));
		selenium.clickAt("//img[@alt='portugu\u00eas (Brasil)']",
			RuntimeVariables.replace("portugu\u00eas (Brasil)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Adicionar"));
		selenium.clickAt("//img[@alt='portugu\u00eas (Portugal) [Beta]']",
			RuntimeVariables.replace("portugu\u00eas (Portugal) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Adicione"));
		selenium.clickAt("//img[@alt='\u0440\u0443\u0441\u0441\u043a\u0438\u0439 (\u0420\u043e\u0441\u0441\u0438\u044f) [Beta]']",
			RuntimeVariables.replace(
				"\u0440\u0443\u0441\u0441\u043a\u0438\u0439 (\u0420\u043e\u0441\u0441\u0438\u044f) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c"));
		selenium.clickAt("//img[@alt='Sloven\u010dina (Slovensk\u00e1 republika) [Beta]']",
			RuntimeVariables.replace(
				"Sloven\u010dina (Slovensk\u00e1 republika) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Prida\u0165"));
		selenium.clickAt("//img[@alt='espa\u00f1ol (Espa\u00f1a)']",
			RuntimeVariables.replace("espa\u00f1ol (Espa\u00f1a)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("A\u00f1adir"));
		selenium.clickAt("//img[@alt='svenska (Sverige) [Beta]']",
			RuntimeVariables.replace("svenska (Sverige) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("L\u00e4gg till"));
		selenium.clickAt("//img[@alt='T\u00fcrk\u00e7e (T\u00fcrkiye) [Beta]']",
			RuntimeVariables.replace("T\u00fcrk\u00e7e (T\u00fcrkiye) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Ekle"));
		selenium.clickAt("//img[@alt='\u0443\u043a\u0440\u0430\u0457\u043d\u0441\u044c\u043a\u0430 (\u0423\u043a\u0440\u0430\u0457\u043d\u0430) [Beta]']",
			RuntimeVariables.replace(
				"\u0443\u043a\u0440\u0430\u0457\u043d\u0441\u044c\u043a\u0430 (\u0423\u043a\u0440\u0430\u0457\u043d\u0430) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u0438"));
		selenium.clickAt("//img[@alt='Ti\u1ebfng Vi\u1ec7t (Vi\u1ec7t Nam) [Beta]']",
			RuntimeVariables.replace(
				"Ti\u1ebfng Vi\u1ec7t (Vi\u1ec7t Nam) [Beta]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Th\u00eam"));
		selenium.clickAt("//img[@alt='English (United States)']",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Add"));
	}
}