/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.theme;

import com.liferay.layout.utility.page.kernel.provider.util.LayoutUtilityPageEntryLayoutProviderUtil;
import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockBodyContent;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockJspWriter;
import org.springframework.mock.web.MockPageContext;

/**
 * @author Lourdes Fernández Besada
 */
public class MetaTagsTagTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_layoutUtilityPageEntryLayoutProviderUtilMockedStatic.close();
	}

	@Test
	public void testMetaTagsTagInternalServerErrorResponseStatus()
		throws Exception {

		String htmlDescription = RandomTestUtil.randomString();

		_assertMetaTagsTagResponseStatus(
			htmlDescription, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	@Test
	public void testMetaTagsTagLayoutRobots() throws Exception {
		String layoutRobots = RandomTestUtil.randomString();

		_assertRobotsMetaTagsTag(layoutRobots, layoutRobots, null, null);
	}

	@Test
	public void testMetaTagsTagLocalizedLayoutRobots() throws Exception {
		String localizedLayoutRobots = RandomTestUtil.randomString();

		_assertRobotsMetaTagsTag(
			localizedLayoutRobots, RandomTestUtil.randomString(),
			localizedLayoutRobots, null);
	}

	@Test
	public void testMetaTagsTagNotFoundResponseStatus() throws Exception {
		String htmlDescription = RandomTestUtil.randomString();

		_assertMetaTagsTagResponseStatus(
			htmlDescription, HttpServletResponse.SC_NOT_FOUND);
	}

	@Test
	public void testMetaTagsTagPageNoRobots() throws Exception {
		_assertRobotsMetaTagsTag(null, null, null, null);
	}

	@Test
	public void testMetaTagsTagPageRobotsRequestAttribute() throws Exception {
		String pageRobotsRequestAttribute = "noindex, nofollow";

		_assertRobotsMetaTagsTag(
			pageRobotsRequestAttribute, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), pageRobotsRequestAttribute);
	}

	private void _assertMetaTagsTag(
			String metaContent, String metaLang, String metaName)
		throws Exception {

		MetaTagsTag metaTagsTag = new MetaTagsTag();

		metaTagsTag.setPageContext(_pageContext);

		metaTagsTag.doStartTag();

		metaTagsTag.setBodyContent(_pageContext.pushBody());

		metaTagsTag.doEndTag();

		String content = _unsyncStringWriter.toString();

		if (Validator.isNull(metaContent)) {
			Assert.assertFalse(
				content,
				StringUtil.contains(
					content, " name=\"" + metaName + "\" />",
					StringPool.BLANK));
		}
		else if (Validator.isNotNull(metaLang)) {
			Assert.assertTrue(
				content,
				StringUtil.contains(
					content,
					StringBundler.concat(
						"<meta content=\"", metaContent, "\" lang=\"", metaLang,
						"\" name=\"", metaName, "\" />"),
					StringPool.BLANK));
		}
		else {
			Assert.assertTrue(
				content,
				StringUtil.contains(
					content,
					StringBundler.concat(
						"<meta content=\"", metaContent, "\" name=\"", metaName,
						"\" />"),
					StringPool.BLANK));
		}
	}

	private void _assertMetaTagsTagResponseStatus(
			String htmlDescription, int status)
		throws Exception {

		_setUpLanguageUtil();

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getDescription(Mockito.anyString(), Mockito.anyBoolean())
		).thenReturn(
			htmlDescription
		);

		_setUpPageContext(layout, RandomTestUtil.randomString(), status);

		_layoutUtilityPageEntryLayoutProviderUtilMockedStatic.when(
			() ->
				LayoutUtilityPageEntryLayoutProviderUtil.
					getDefaultLayoutUtilityPageEntryLayout(
						Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			layout
		);

		_assertMetaTagsTag(
			htmlDescription, LocaleUtil.toW3cLanguageId(LocaleUtil.SPAIN),
			"description");

		_assertMetaTagsTag(null, null, "robots");
	}

	private void _assertRobotsMetaTagsTag(
			String expectedRobotsMetaTagContent, String layoutRobots,
			String localizedLayoutRobots, String pageRobotsRequestAttribute)
		throws Exception {

		_setUpLanguageUtil();

		Layout layout = Mockito.mock(Layout.class);

		Mockito.when(
			layout.getRobots(Mockito.anyString(), Mockito.anyBoolean())
		).thenReturn(
			localizedLayoutRobots
		);

		Mockito.when(
			layout.getRobots(Mockito.anyString())
		).thenReturn(
			layoutRobots
		);

		_setUpPageContext(
			layout, pageRobotsRequestAttribute, HttpServletResponse.SC_OK);

		_assertMetaTagsTag(expectedRobotsMetaTagContent, null, "robots");
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.getLanguageId(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN)
		);

		Mockito.when(
			language.getLanguageId(Mockito.any(Locale.class))
		).thenReturn(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		);

		Mockito.when(
			language.getLanguageId((Locale)null)
		).thenReturn(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		);

		languageUtil.setLanguage(language);
	}

	private void _setUpPageContext(Layout layout, String robots, int status) {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			themeDisplay.getLayout()
		).thenReturn(
			layout
		);

		Mockito.when(
			themeDisplay.getLanguageId()
		).thenReturn(
			LocaleUtil.toLanguageId(LocaleUtil.SPAIN)
		);

		_unsyncStringWriter = new UnsyncStringWriter();

		final JspWriter jspWriter = new MockJspWriter(_unsyncStringWriter);

		_pageContext = new MockPageContext() {

			@Override
			public JspWriter getOut() {
				return jspWriter;
			}

			@Override
			public ServletRequest getRequest() {
				return new MockHttpServletRequest() {

					@Override
					public Object getAttribute(String name) {
						if (WebKeys.PAGE_ROBOTS.equals(name)) {
							return robots;
						}

						if (WebKeys.THEME_DISPLAY.equals(name)) {
							return themeDisplay;
						}

						if (!WebKeys.OUTPUT_DATA.equals(name)) {
							return null;
						}

						return new OutputData() {

							@Override
							public void addDataSB(
								String outputKey, String webKey,
								StringBundler sb) {

								try {
									jspWriter.write(sb.toString());
								}
								catch (IOException ioException) {
									ReflectionUtil.throwException(ioException);
								}
							}

						};
					}

				};
			}

			@Override
			public ServletResponse getResponse() {
				HttpServletResponse httpServletResponse =
					new MockHttpServletResponse();

				httpServletResponse.setStatus(status);

				return httpServletResponse;
			}

			@Override
			public BodyContent pushBody() {
				final UnsyncStringWriter unsyncStringWriter =
					new UnsyncStringWriter();

				return new MockBodyContent(
					StringPool.BLANK, unsyncStringWriter) {

					@Override
					public String getString() {
						return unsyncStringWriter.toString();
					}

				};
			}

		};
	}

	private static final MockedStatic<LayoutUtilityPageEntryLayoutProviderUtil>
		_layoutUtilityPageEntryLayoutProviderUtilMockedStatic =
			Mockito.mockStatic(LayoutUtilityPageEntryLayoutProviderUtil.class);

	private PageContext _pageContext;
	private UnsyncStringWriter _unsyncStringWriter;

}