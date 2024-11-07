/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.wizard.enums;

import dev.langchain4j.model.output.structured.Description;

/**
 * @author Keven Leone
 */
public enum StructureKeys {

	@Description(
		"A small, darker rectangle with rounded corners, typically placed next to a paragraph or text."
	)
	button,
	card,
	@Description(
		"A component located at the bottom of the page, usually containing links."
	)
	carousel,
	@Description(
		"A component containing an image (either square or circular), with a title beneath the image, a paragraph under the title, and a button after the paragraph. Multiple cards can appear in the same row."
	)
	footer,
	@Description(
		"Located at the top of the page, usually consisting of links, buttons, and sometimes an image."
	)
	header,
	@Description(
		"A block that spans the full width of the page, containing images and text."
	)
	heading,
	@Description(
		"A block of content with controls that allow users to navigate through it."
	)
	hero_banner,
	@Description("A simple image component.")
	image,
	@Description("A component designed for displaying multiline text.")
	paragraph,
	@Description("A group of social media icons with links.")
	social,
	@Description("A video element.")
	video

}