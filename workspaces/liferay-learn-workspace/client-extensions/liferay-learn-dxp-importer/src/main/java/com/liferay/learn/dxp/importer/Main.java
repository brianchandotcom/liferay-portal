/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.learn.dxp.importer;

import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Tuple;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.net.URL;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

/**
 * @author Brian Wing Shun Chan
 * @author Rich Sezov
 * @author Allen Ziegenfus
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		Properties mainProperties = new Properties();

		try (InputStream inputStream = Main.class.getResourceAsStream(
				"dependencies/main.properties")) {

			mainProperties.load(inputStream);
		}

		Properties tokenProperties = new Properties();

		try (InputStream inputStream = Main.class.getResourceAsStream(
				"dependencies/token.properties")) {

			tokenProperties.load(inputStream);
		}

		File markdownImportDirFile = new File(
			mainProperties.getProperty("markdown.import.dir"));

		Main main = new Main(
			GetterUtil.getLong(
				mainProperties.getProperty("liferay.content.structure.id")),
			GetterUtil.getLong(mainProperties.getProperty("liferay.group.id")),
			mainProperties.getProperty("liferay.oauth.client.id"),
			mainProperties.getProperty("liferay.oauth.client.secret"),
			new URL(mainProperties.getProperty("liferay.url")),
			markdownImportDirFile.getCanonicalPath(), tokenProperties);

		main.uploadToLiferay();
	}

	public Main(
			long liferayContentStructureId, long liferayGroupId,
			String liferayOAuthClientId, String liferayOAuthClientSecret,
			URL liferayURL, String markdownImportDirName,
			Properties tokenProperties)
		throws Exception {

		_liferayContentStructureId = liferayContentStructureId;
		_liferayGroupId = liferayGroupId;
		_liferayOAuthClientId = liferayOAuthClientId;
		_liferayOAuthClientSecret = liferayOAuthClientSecret;
		_liferayURL = liferayURL;
		_markdownImportDirName = markdownImportDirName;

		Enumeration<String> enumeration =
			(Enumeration<String>)tokenProperties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			if (key.endsWith("_TOKEN")) {
				_tokens.put(
					tokenProperties.getProperty(key),
					tokenProperties.getProperty(key + "_VALUE"));
			}
		}

		_addFileNames(_markdownImportDirName);

		_initFlexmark();
		_initResourceBuilders(_getOAuthAuthorization());
	}

	public void uploadToLiferay() throws Exception {
		long start = System.currentTimeMillis();

		for (String fileName : _fileNames) {
			if (!fileName.contains("/en/") || !fileName.endsWith(".md")) {
				continue;
			}

			long delta = System.currentTimeMillis() - start;

			if (delta > (_oauthExpirationMillis - 10000)) {
				_initResourceBuilders(_getOAuthAuthorization());

				start = System.currentTimeMillis();
			}

			System.out.println(fileName);

			_structuredContentResource.
				postStructuredContentFolderStructuredContent(
					_getStructuredContentFolderId(
						FilenameUtils.getPathNoEndSeparator(
							fileName.substring(
								_markdownImportDirName.length()))),
					_toStructuredContent(fileName));
		}
	}

	private void _addFileNames(String fileName) {
		File file = new File(fileName);

		if (file.isDirectory() &&
			!Objects.equals(file.getName(), "resources")) {

			for (String currentFileName : file.list()) {
				_addFileNames(fileName + "/" + currentFileName);
			}
		}

		_fileNames.add(fileName);
	}

	private String _dedent(int dedent, String line) {
		if (line == null) {
			return null;
		}

		int length = line.length();

		if (length == 0) {
			return line;
		}

		int index = 0;

		while ((index < length) && (index < dedent)) {
			char c = line.charAt(index);

			if (((c > CharPool.SPACE) && (c < 128)) ||
				!Character.isWhitespace(c)) {

				break;
			}

			index++;
		}

		if (index > 0) {
			return line.substring(index);
		}

		return line;
	}

	private String[] _getDirNames(String fileName) throws Exception {
		List<String> dirNames = new ArrayList<>();

		String[] parts = fileName.split(
			Matcher.quoteReplacement(File.separator));

		for (String part : parts) {
			if (part.equalsIgnoreCase("en") || part.equalsIgnoreCase("ja") ||
				part.equalsIgnoreCase("latest")) {

				continue;
			}

			String dirName = part;

			dirNames.add(dirName);
		}

		return dirNames.toArray(new String[0]);
	}

	private Long _getDocumentFolderId(String fileName) throws Exception {
		Long documentFolderId = 0L;

		for (String dirName : _getDirNames(fileName)) {
			documentFolderId = _getDocumentFolderId(dirName, documentFolderId);
		}

		return documentFolderId;
	}

	private Long _getDocumentFolderId(
			String dirName, Long parentDocumentFolderId)
		throws Exception {

		String key = parentDocumentFolderId + "#" + dirName;

		Long documentFolderId = _documentFolderIds.get(key);

		if (documentFolderId != null) {
			return documentFolderId;
		}

		DocumentFolder documentFolder = null;

		if (parentDocumentFolderId == 0) {
			Page<DocumentFolder> page =
				_documentFolderResource.getSiteDocumentFoldersPage(
					_liferayGroupId, null, null, null,
					"name eq '" + dirName + "'", null, null);

			documentFolder = page.fetchFirstItem();

			if (documentFolder == null) {
				documentFolder = _documentFolderResource.postSiteDocumentFolder(
					_liferayGroupId,
					new DocumentFolder() {
						{
							description = "";
							name = dirName;
						}
					});
			}
		}
		else {
			Page<DocumentFolder> page =
				_documentFolderResource.getDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, null,
					"name eq '" + dirName + "'", null, null);

			documentFolder = page.fetchFirstItem();

			if (documentFolder == null) {
				documentFolder =
					_documentFolderResource.postDocumentFolderDocumentFolder(
						parentDocumentFolderId,
						new DocumentFolder() {
							{
								description = "";
								name = dirName;
							}
						});
			}
		}

		documentFolderId = documentFolder.getId();

		_documentFolderIds.put(key, documentFolderId);

		return documentFolderId;
	}

	private String _getOAuthAuthorization() throws Exception {
		HttpPost httpPost = new HttpPost(_liferayURL + "/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair("client_id", _liferayOAuthClientId),
					new BasicNameValuePair(
						"client_secret", _liferayOAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject jsonObject = new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				_oauthExpirationMillis =
					jsonObject.getLong("expires_in") * 1000;

				return jsonObject.getString("token_type") + " " +
					jsonObject.getString("access_token");
			}

			throw new Exception("Unable to get OAuth authorization");
		}
	}

	private Long _getStructuredContentFolderId(String fileName)
		throws Exception {

		Long structuredContentFolderId = 0L;

		for (String dirName : _getDirNames(fileName)) {
			structuredContentFolderId = _getStructuredContentFolderId(
				dirName, structuredContentFolderId);
		}

		return structuredContentFolderId;
	}

	private Long _getStructuredContentFolderId(
			String dirName, Long parentStructuredContentFolderId)
		throws Exception {

		String key = parentStructuredContentFolderId + "#" + dirName;

		Long structuredContentFolderId = _structuredContentFolderIds.get(key);

		if (structuredContentFolderId != null) {
			return structuredContentFolderId;
		}

		StructuredContentFolder structuredContentFolder = null;

		if (parentStructuredContentFolderId == 0) {
			Page<StructuredContentFolder> page =
				_structuredContentFolderResource.
					getSiteStructuredContentFoldersPage(
						_liferayGroupId, null, null, null,
						"name eq '" + dirName + "'", null, null);

			structuredContentFolder = page.fetchFirstItem();

			if (structuredContentFolder == null) {
				structuredContentFolder =
					_structuredContentFolderResource.
						postSiteStructuredContentFolder(
							_liferayGroupId,
							new StructuredContentFolder() {
								{
									description = "";
									name = dirName;
								}
							});
			}
		}
		else {
			Page<StructuredContentFolder> page =
				_structuredContentFolderResource.
					getStructuredContentFolderStructuredContentFoldersPage(
						parentStructuredContentFolderId, null, null,
						"name eq '" + dirName + "'", null, null);

			structuredContentFolder = page.fetchFirstItem();

			if (structuredContentFolder == null) {
				structuredContentFolder =
					_structuredContentFolderResource.
						postStructuredContentFolderStructuredContentFolder(
							parentStructuredContentFolderId,
							new StructuredContentFolder() {
								{
									description = "";
									name = dirName;
								}
							});
			}
		}

		structuredContentFolderId = structuredContentFolder.getId();

		_structuredContentFolderIds.put(key, structuredContentFolderId);

		return structuredContentFolderId;
	}

	private String _getTitle(String text) {
		int x = text.indexOf("#");

		int y = text.indexOf(StringPool.NEW_LINE, x);

		String title = text.substring(x + 1, y);

		return title.trim();
	}

	private void _initFlexmark() {
		MutableDataSet mutableDataSet = new MutableDataSet().set(
			AsideExtension.ALLOW_LEADING_SPACE, true
		).set(
			AsideExtension.EXTEND_TO_BLANK_LINE, false
		).set(
			AsideExtension.IGNORE_BLANK_LINE, false
		).set(
			AsideExtension.INTERRUPTS_ITEM_PARAGRAPH, true
		).set(
			AsideExtension.INTERRUPTS_PARAGRAPH, true
		).set(
			AsideExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH, true
		).set(
			HtmlRenderer.GENERATE_HEADER_ID, true
		).set(
			Parser.EXTENSIONS,
			Arrays.asList(
				AdmonitionExtension.create(), AnchorLinkExtension.create(),
				AsideExtension.create(), AttributesExtension.create(),
				DefinitionExtension.create(), FootnoteExtension.create(),
				MediaTagsExtension.create(), StrikethroughExtension.create(),
				SuperscriptExtension.create(), TablesExtension.create(),
				TocExtension.create(), TypographicExtension.create(),
				YamlFrontMatterExtension.create())
		);

		_renderer = HtmlRenderer.builder(
			mutableDataSet
		).build();

		_parser = Parser.builder(
			mutableDataSet
		).build();
	}

	private void _initResourceBuilders(String authorization) throws Exception {
		DocumentFolderResource.Builder documentFolderResourceBuilder =
			DocumentFolderResource.builder();

		_documentFolderResource = documentFolderResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();

		DocumentResource.Builder documentResourceBuilder =
			DocumentResource.builder();

		_documentResource = documentResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();

		StructuredContentResource.Builder structuredContentResourceBuilder =
			StructuredContentResource.builder();

		_structuredContentResource = structuredContentResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();

		StructuredContentFolderResource.Builder
			structuredContentFolderResourceBuilder =
				StructuredContentFolderResource.builder();

		_structuredContentFolderResource =
			structuredContentFolderResourceBuilder.header(
				"Authorization", authorization
			).endpoint(
				_liferayURL.getHost(), _liferayURL.getPort(),
				_liferayURL.getProtocol()
			).build();
	}

	private String _processInclude(String includeFileName, File markdownFile)
		throws Exception {

		File file = null;

		String markdownFileName = markdownFile.getCanonicalPath();

		if (includeFileName.startsWith(File.separator)) {
			String dirName = markdownFileName.substring(
				_markdownImportDirName.length());

			List<String> dirNameParts = StringUtil.split(
				dirName, File.separatorChar);

			if (dirNameParts.size() < 3) {
				throw new Exception("Invalid directory " + dirName);
			}

			StringBuilder sb = new StringBuilder();

			sb.append(_markdownImportDirName);
			sb.append(File.separator);
			sb.append(dirNameParts.get(0));
			sb.append(File.separator);
			sb.append(dirNameParts.get(1));
			sb.append(File.separator);
			sb.append(dirNameParts.get(2));
			sb.append(includeFileName);

			file = new File(sb.toString());
		}
		else {
			file = new File(
				FilenameUtils.getFullPath(markdownFileName) + includeFileName);
		}

		if (!file.exists()) {
			throw new Exception("Nonexistent include " + file);
		}

		return _processMarkdown(
			FileUtils.readFileToString(file, StandardCharsets.UTF_8),
			markdownFile);
	}

	private String _processLiteralInclude(
			String literalIncludeFileName,
			List<Tuple> literalIncludeLineRangeTuples,
			Map<String, String> literalIncludeParameters, File markdownFile)
		throws Exception {

		String fileName =
			FilenameUtils.getFullPath(markdownFile.getPath()) +
				literalIncludeFileName;

		File file = new File(fileName);

		if (!file.exists()) {
			file = new File(fileName.replaceAll("/ja/", "/en/"));
		}

		if (!file.exists()) {
			System.out.println("Nonexistent literal include " + file);

			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("```");
		sb.append(
			GetterUtil.getString(
				literalIncludeParameters.get("language"), "java"));
		sb.append("\n");

		for (Tuple literalIncludeLineRangeTuple :
				literalIncludeLineRangeTuples) {

			sb.append(
				_processLiteralIncludeLineRange(
					file, literalIncludeLineRangeTuple,
					literalIncludeParameters));
		}

		sb.append("```");

		return sb.toString();
	}

	private String _processLiteralIncludeBlock(
			String literalIncludeFileName, File markdownFile,
			List<String> mySTDirectiveLines)
		throws Exception {

		Map<String, String> literalIncludeParameters = new HashMap<>();
		List<Tuple> literalIncludeLineRangeTuples = new ArrayList<>();

		for (String mySTDirectiveLine : mySTDirectiveLines) {
			Matcher matcher = _literalIncludeParameterPattern.matcher(
				mySTDirectiveLine.trim());

			if (!matcher.find()) {
				continue;
			}

			String name = matcher.group(1);
			String value = matcher.group(2);

			if (name.equals("lines")) {
				List<String> lineRanges = StringUtil.split(
					value, CharPool.COMMA);

				for (String lineRange : lineRanges) {
					Tuple tuple = null;

					List<String> lineRangeParts = StringUtil.split(
						lineRange, CharPool.DASH);

					if (lineRangeParts.size() == 1) {
						tuple = new Tuple(
							GetterUtil.getInteger(lineRangeParts.get(0)),
							GetterUtil.getInteger(lineRangeParts.get(0)));
					}
					else if (lineRangeParts.size() == 2) {
						tuple = new Tuple(
							GetterUtil.getInteger(lineRangeParts.get(0)),
							GetterUtil.getInteger(lineRangeParts.get(1)));
					}
					else {
						throw new Exception(
							"Invalid literal include lines value " + value);
					}

					literalIncludeLineRangeTuples.add(tuple);
				}
			}
			else {
				literalIncludeParameters.put(name, value);
			}
		}

		if (literalIncludeLineRangeTuples.isEmpty()) {
			literalIncludeLineRangeTuples.add(new Tuple(0, -1));
		}

		return _processLiteralInclude(
			literalIncludeFileName, literalIncludeLineRangeTuples,
			literalIncludeParameters, markdownFile);
	}

	private String _processLiteralIncludeLineRange(
			File file, Tuple literalIncludeLineRangeTuple,
			Map<String, String> literalIncludeParameters)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		int dedent = GetterUtil.getInteger(
			literalIncludeParameters.get("dedent"));
		int lineEnd = GetterUtil.getInteger(
			literalIncludeLineRangeTuple.getObject(1), -1);
		int lineStart = GetterUtil.getInteger(
			literalIncludeLineRangeTuple.getObject(0));

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(new FileInputStream(file)));
		int i = 0;
		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			if (i >= (lineStart - 1)) {
				sb.append(_dedent(dedent, line) + "\n");
			}

			if ((lineEnd != -1) && (i > (lineEnd - 2))) {
				break;
			}

			i++;
		}

		return sb.toString();
	}

	private String _processMarkdown(String markdown, File markdownFile)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		BufferedReader bufferedReader = new BufferedReader(
			new StringReader(markdown));
		String line = null;

		while ((line = bufferedReader.readLine()) != null) {
			line = _processMySTDirectiveBlocks(
				bufferedReader, line, markdownFile);
			line = _processSphinxBadges(line);
			line = _processTokens(line);

			sb.append(line);

			sb.append("\n");
		}

		return sb.toString();
	}

	private String _processMySTDirectiveBlocks(
			BufferedReader bufferedReader, String line, File markdownFile)
		throws Exception {

		String trimmedLine = line.trim();

		if (!trimmedLine.startsWith("```{")) {
			return line;
		}

		List<String> mySTDirectiveLines = new ArrayList<>();

		int index = line.indexOf(StringPool.CLOSE_CURLY_BRACE);

		String directiveName = line.substring(
			line.indexOf(StringPool.OPEN_CURLY_BRACE) + 1, index);

		while (true) {
			String mySTDirectiveLine = bufferedReader.readLine();

			if (mySTDirectiveLine == null) {
				System.out.println(
					"Unclosed MyST directive block found in " +
						markdownFile.getCanonicalPath());

				break;
			}

			mySTDirectiveLine = _processTokens(mySTDirectiveLine);

			String trimmedMySTDirectiveLine = mySTDirectiveLine.trim();

			if (trimmedMySTDirectiveLine.startsWith("```")) {
				break;
			}

			mySTDirectiveLines.add(mySTDirectiveLine);
		}

		String directiveArguments = line.substring(index + 1);

		directiveArguments = directiveArguments.trim();

		if (directiveName.equals("include")) {
			return _processInclude(directiveArguments, markdownFile);
		}
		else if (directiveName.equals("literalinclude")) {
			return _processLiteralIncludeBlock(
				directiveArguments, markdownFile, mySTDirectiveLines);
		}
		else if (directiveName.equals("toctree")) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < line.indexOf(trimmedLine); i++) {
			sb.append(" ");
		}

		sb.append("!!! ");
		sb.append(directiveName);
		sb.append(" \"\" \n");

		for (String mySTDirectiveLine : mySTDirectiveLines) {
			sb.append("    ");
			sb.append(mySTDirectiveLine);
			sb.append("\n");
		}

		return sb.toString();
	}

	private String _processSphinxBadges(String line) {
		Matcher matcher = _sphinxBadgePattern.matcher(line);

		if (matcher.find()) {
			line = matcher.replaceFirst("<span class=\"bdg-$1\">$2</span>");
		}

		return line;
	}

	private String _processTokens(String line) {
		for (Map.Entry<String, String> entry : _tokens.entrySet()) {
			line = StringUtil.replace(line, entry.getKey(), entry.getValue());
		}

		return line;
	}

	private BasedSequence _toBasedSequence(String string) {
		return CharSubSequence.of(string.toCharArray(), 0, string.length());
	}

	private String _toFriendlyURLPath(String fileName) {
		String friendlyURLPath = fileName.substring(
			_markdownImportDirName.length());

		if (friendlyURLPath.startsWith(File.separator)) {
			friendlyURLPath = friendlyURLPath.substring(1);
		}

		return FilenameUtils.removeExtension(friendlyURLPath) + ".html";
	}

	private String _toHTML(File file, String text) throws Exception {
		_write(text, "build/markdown", file);

		com.vladsch.flexmark.util.ast.Document document = _parser.parse(text);

		AbstractYamlFrontMatterVisitor abstractYamlFrontMatterVisitor =
			new AbstractYamlFrontMatterVisitor();

		abstractYamlFrontMatterVisitor.visit(document);

		/*Map<String, List<String>> data =
			abstractYamlFrontMatterVisitor.getData();*/

		_markdownFile = file;

		try {
			_nodeVisitor.visit(document);
		}
		finally {
			_markdownFile = null;
		}

		String html = _renderer.render(document);

		_write(html, "build/html", file);

		return html;
	}

	private StructuredContent _toStructuredContent(String fileName)
		throws Exception {

		StructuredContent structuredContent = new StructuredContent();

		File englishFile = new File(fileName);

		String englishText = _processMarkdown(
			FileUtils.readFileToString(englishFile, StandardCharsets.UTF_8),
			englishFile);

		ContentFieldValue englishContentFieldValue = new ContentFieldValue() {
			{
				data = _toHTML(englishFile, englishText);
			}
		};

		String englishTitle = _getTitle(englishText);

		File japaneseFile = new File(fileName.replace("/en/", "/ja/"));

		if (japaneseFile.exists()) {
			String japaneseText = _processMarkdown(
				FileUtils.readFileToString(
					japaneseFile, StandardCharsets.UTF_8),
				japaneseFile);

			structuredContent.setContentFields(
				new ContentField[] {
					new ContentField() {
						{
							contentFieldValue = englishContentFieldValue;
							contentFieldValue_i18n = HashMapBuilder.put(
								"en-US", englishContentFieldValue
							).put(
								"ja-JP",
								new ContentFieldValue() {
									{
										data = _toHTML(
											japaneseFile, japaneseText);
									}
								}
							).build();
							name = "content";
						}
					}
				});

			structuredContent.setFriendlyUrlPath_i18n(
				HashMapBuilder.put(
					"en-US", _toFriendlyURLPath(fileName)
				).put(
					"ja-JP", _toFriendlyURLPath(japaneseFile.getPath())
				).build());
			structuredContent.setTitle_i18n(
				HashMapBuilder.put(
					"en-US", englishTitle
				).put(
					"ja-JP", _getTitle(japaneseText)
				).build());
		}
		else {
			structuredContent.setContentFields(
				new ContentField[] {
					new ContentField() {
						{
							contentFieldValue = englishContentFieldValue;
							name = "content";
						}
					}
				});
		}

		structuredContent.setContentStructureId(_liferayContentStructureId);
		structuredContent.setFriendlyUrlPath(_toFriendlyURLPath(fileName));
		structuredContent.setTitle(englishTitle);

		return structuredContent;
	}

	private void _visit(Image image) throws Exception {
		BasedSequence basedSequence = image.getUrl();

		if (basedSequence.startsWith("http")) {
			return;
		}

		String fileName =
			FilenameUtils.getFullPath(_markdownFile.getPath()) + basedSequence;

		File file = new File(fileName);

		if (!file.exists()) {
			file = new File(fileName.replaceAll("/ja/", "/en/"));
		}

		if (!file.exists()) {
			System.out.println(
				_markdownFile.getCanonicalPath() +
					" references nonexistent image file " +
						file.getCanonicalPath());

			return;
		}

		File finalFile = file;

		String filePathString = file.getCanonicalPath();

		String imageURL = _imageURLs.get(filePathString);

		if (imageURL == null) {
			Document document = _documentResource.postDocumentFolderDocument(
				_getDocumentFolderId(
					FilenameUtils.getPathNoEndSeparator(
						filePathString.substring(
							_markdownImportDirName.length()))),
				new Document() {
					{
						title = finalFile.getName();
					}
				},
				new HashMap<>() {
					{
						put("file", finalFile);
					}
				});

			imageURL = document.getContentUrl();

			_imageURLs.put(filePathString, imageURL);
		}

		image.setUrl(_toBasedSequence(imageURL));

		_nodeVisitor.visitChildren(image);
	}

	private void _visit(Link link) throws Exception {
		BasedSequence basedSequence = link.getUrl();

		link.setUrl(basedSequence.replace(".md", ".html"));
	}

	private void _write(String content, String dirName, File markdownFile)
		throws Exception {

		String markdownFileName = markdownFile.getCanonicalPath();

		markdownFileName = markdownFileName.substring(
			_markdownImportDirName.length());

		File file = new File(dirName + markdownFileName);

		FileUtils.forceMkdirParent(file);

		FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
	}

	private Map<String, Long> _documentFolderIds = new HashMap<>();
	private DocumentFolderResource _documentFolderResource;
	private DocumentResource _documentResource;
	private Set<String> _fileNames = new TreeSet<>();
	private Map<String, String> _imageURLs = new HashMap<>();
	private final long _liferayContentStructureId;
	private final long _liferayGroupId;
	private final String _liferayOAuthClientId;
	private final String _liferayOAuthClientSecret;
	private final URL _liferayURL;
	private final Pattern _literalIncludeParameterPattern = Pattern.compile(
		":(.*): (.*)");
	private File _markdownFile;
	private final String _markdownImportDirName;

	private NodeVisitor _nodeVisitor = new NodeVisitor(
		new VisitHandler<Image>(
			Image.class,
			new Visitor<Image>() {

				@Override
				public void visit(Image image) {
					try {
						_visit(image);
					}
					catch (Exception exception) {
						exception.printStackTrace();
					}
				}

			}),
		new VisitHandler<Link>(
			Link.class,
			new Visitor<Link>() {

				@Override
				public void visit(Link link) {
					try {
						_visit(link);
					}
					catch (Exception exception) {
						exception.printStackTrace();
					}
				}

			}));

	private long _oauthExpirationMillis;
	private Parser _parser;
	private HtmlRenderer _renderer;
	private final Pattern _sphinxBadgePattern = Pattern.compile(
		"\\{bdg-(.*)\\}`(.*)`");
	private Map<String, Long> _structuredContentFolderIds = new HashMap<>();
	private StructuredContentFolderResource _structuredContentFolderResource;
	private StructuredContentResource _structuredContentResource;
	private Map<String, String> _tokens = new HashMap<>();

}