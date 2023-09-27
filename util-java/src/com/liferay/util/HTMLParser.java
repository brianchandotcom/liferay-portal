/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util;

import java.io.IOException;
import java.io.Reader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @author Brian Wing Shun Chan
 */
public class HTMLParser {

	public HTMLParser(Reader reader) throws IOException {
		DefaultParser defaultParser = new DefaultParser();

		HTMLEditorKit.Parser parser = defaultParser.getParser();

		parser.parse(reader, new HTMLCallback(), true);
	}

	public List<String> getImages() {
		return _images;
	}

	public List<String> getLinks() {
		return _links;
	}

	private final List<String> _images = new ArrayList<>();
	private final List<String> _links = new ArrayList<>();

	private static class DefaultParser extends HTMLEditorKit {

		@Override
		public HTMLEditorKit.Parser getParser() {
			return super.getParser();
		}

	}

	private class HTMLCallback extends HTMLEditorKit.ParserCallback {

		@Override
		public void handleComment(char[] data, int pos) {
		}

		@Override
		public void handleEndTag(HTML.Tag tag, int pos) {
		}

		@Override
		public void handleError(String errorMsg, int pos) {
		}

		@Override
		public void handleSimpleTag(
			HTML.Tag tag, MutableAttributeSet attributes, int pos) {

			if (tag.equals(HTML.Tag.A)) {
				String href = (String)attributes.getAttribute(
					HTML.Attribute.HREF);

				if (href != null) {
					_links.add(href);
				}
			}
			else if (tag.equals(HTML.Tag.IMG)) {
				String src = (String)attributes.getAttribute(
					HTML.Attribute.SRC);

				if (src != null) {
					_images.add(src);
				}
			}
		}

		@Override
		public void handleStartTag(
			HTML.Tag tag, MutableAttributeSet attributes, int pos) {

			if (tag.equals(HTML.Tag.A)) {
				String href = (String)attributes.getAttribute(
					HTML.Attribute.HREF);

				if (href != null) {
					_links.add(href);
				}
			}
			else if (tag.equals(HTML.Tag.IMG)) {
				String src = (String)attributes.getAttribute(
					HTML.Attribute.SRC);

				if (src != null) {
					_images.add(src);
				}
			}
		}

		@Override
		public void handleText(char[] data, int pos) {
		}

	}

}