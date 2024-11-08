/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.langchain4j.descriptions;

import dev.langchain4j.model.output.structured.Description;

/**
 * @author Keven Leone
 * @author Brian Wing Shun Chan
 */
public class SitePageDescriptions {

	@Description(
		"where each object has a \"name\" key or a \"components\" key. \n" +
			"When an object has only one element, it should use the \"name\" key to define it, like \"name\": \"heading\" or \"name\": \"carousel\".\n" +
				"For cases where multiple elements are involved, the \"components\" key is used. \n" +
					"This key holds an array of objects with their own \"name\" keys that specify the type of the component, such as \"name\": \"paragraph\" or \"name\": \"card\". \n" +
						"When a group has social components, they must be consolidated into a single object with the \"name\" key set to \"social\", rather than listing multiple social elements. This ensures that all social elements are grouped together under one \"social\" entry.\n" +
							"When components of the same type appear more than once, they are represented as individual objects within the \"components\" array, as seen with repeated \"card\" components."
	)
	public Structure[] structures;

	@Description("The page title")
	public String title;

	public class Component {

		@Description("The name of this component")
		public String name;

	}

	public class Structure {

		@Description(
			"An array of components in this structure. Use the \"pageComponents\" key when there are multiple elements."
		)
		public Component[] components;

		@Description("The name of this structure")
		public StructureName name;

	}

	public enum StructureName {

		@Description(
			"A small, dark rectangle with rounded corners, typically placed next to a paragraph or text."
		)
		button,
		card,
		@Description(
			"A component located at the bottom of the page, usually containing links."
		)
		carousel,
		@Description(
			"A component containing an image (square or circular), with a title beneath the image, a paragraph under the title, and a button after the paragraph. Multiple cards can appear in the same row."
		)
		footer,
		@Description(
			"A component located at the top of the page, usually consisting of links, buttons, and sometimes an image."
		)
		header,
		@Description(
			"A block that spans the full width of the page, containing images and text."
		)
		heading,
		@Description(
			"A block of content with controls for user navigation."
		)
		hero_banner,
		@Description("A simple image component.")
		image,
		@Description("A component that displays multiline text.")
		paragraph,
		@Description("A group of social media icons with links.")
		social,
		@Description("A video element.")
		video

	}

}