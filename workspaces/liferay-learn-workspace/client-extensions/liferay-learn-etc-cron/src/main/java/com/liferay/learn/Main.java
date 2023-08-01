/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionResource;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.admin.user.client.dto.v1_0.Site;
import com.liferay.headless.admin.user.client.resource.v1_0.SiteResource;
import com.liferay.headless.delivery.client.dto.v1_0.ContentField;
import com.liferay.headless.delivery.client.dto.v1_0.ContentFieldValue;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.client.dto.v1_0.StructuredContentFolder;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.permission.Permission;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentFolderResource;
import com.liferay.headless.delivery.client.resource.v1_0.StructuredContentResource;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;

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
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterNode;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterVisitorExt;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.net.URL;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

import org.json.JSONArray;
import org.json.JSONObject;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Brian Wing Shun Chan
 * @author Rich Sezov
 * @author Allen Ziegenfus
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		File markdownImportDirFile = new File(
			System.getenv("LIFERAY_LEARN_ETC_CRON_MARKDOWN_IMPORT_DIR"));

		Main main = new Main(
			System.getenv("LIFERAY_LEARN_ETC_CRON_LIFERAY_DATA_DEFINITION_KEY"),
			System.getenv(
				"LIFERAY_LEARN_ETC_CRON_LIFERAY_LEARN_RESOURCES_DOMAIN"),
			System.getenv("LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID"),
			System.getenv("LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET"),
			System.getenv(
				"LIFERAY_LEARN_ETC_CRON_LIFERAY_SITE_FRIENDLY_URL_PATH"),
			new URL(System.getenv("LIFERAY_LEARN_ETC_CRON_LIFERAY_URL")),
			markdownImportDirFile.getCanonicalPath(),
			GetterUtil.getBoolean(
				System.getenv("LIFERAY_LEARN_ETC_CRON_OFFLINE")));

		main.uploadToLiferay();
	}

	public Main(
			String liferayDataDefinitionKey, String liferayLearnResourcesDomain,
			String liferayOAuthClientId, String liferayOAuthClientSecret,
			String liferaySiteFriendlyUrlPath, URL liferayURL,
			String markdownImportDirName, boolean offline)
		throws Exception {

		_liferayLearnResourcesDomain = liferayLearnResourcesDomain;
		_liferayOAuthClientId = liferayOAuthClientId;
		_liferayOAuthClientSecret = liferayOAuthClientSecret;
		_liferayURL = liferayURL;
		_markdownImportDirName = markdownImportDirName;
		_offline = offline;

		System.out.println("Liferay URL: " + _liferayURL);

		_addFileNames(_markdownImportDirName);

		_initFlexmark();

		if (_offline) {
			_liferayContentStructureId = 0;
			_liferaySiteId = 0;
		}
		else {
			_initResourceBuilders(_getOAuthAuthorization());

			Site site = _siteResource.getSiteByFriendlyUrlPath(
				liferaySiteFriendlyUrlPath);

			_liferaySiteId = site.getId();

			System.out.println("Liferay site ID: " + site.getId());
			System.out.println("Liferay site name: " + site.getName());

			DataDefinition dataDefinition =
				_dataDefinitionResource.
					getSiteDataDefinitionByContentTypeByDataDefinitionKey(
						site.getId(), "journal", liferayDataDefinitionKey);

			_liferayContentStructureId = dataDefinition.getId();

			_loadTaxonomyCategories();
		}
	}

	public void uploadToLiferay() throws Exception {
		if (!_validateUUIDs()) {
			System.exit(1);
		}

		long start = System.currentTimeMillis();

		int addedStructuredContentCount = 0;
		Set<Long> existingStructuredContentIds = new HashSet<>();
		Map<String, StructuredContent> externalReferenceCodeStructuredContents =
			new HashMap<>();
		Map<String, StructuredContent> friendlyUrlPathStructuredContents =
			new HashMap<>();
		Map<Long, StructuredContent> idStructuredContents = new HashMap<>();
		Set<Long> importedStructuredContentIds = new HashSet<>();
		int updatedStructuredContentCount = 0;

		List<StructuredContent> siteStructuredContents =
			_getSiteStructuredContents(_liferaySiteId);

		System.out.println(
			"Site has " + siteStructuredContents.size() +
				" structured contents");

		for (StructuredContent siteStructuredContent : siteStructuredContents) {
			if (siteStructuredContent.getContentStructureId() !=
					_liferayContentStructureId) {

				continue;
			}

			existingStructuredContentIds.add(siteStructuredContent.getId());
			externalReferenceCodeStructuredContents.put(
				siteStructuredContent.getExternalReferenceCode(),
				siteStructuredContent);
			friendlyUrlPathStructuredContents.put(
				siteStructuredContent.getFriendlyUrlPath(),
				siteStructuredContent);
			idStructuredContents.put(
				siteStructuredContent.getId(), siteStructuredContent);
		}

		for (String fileName : _fileNames) {
			if (!fileName.contains("/en/") || !fileName.endsWith(".md")) {
				continue;
			}

			System.out.println(fileName);

			if (_offline) {
				JSONObject jsonObject = new JSONObject(
					_toStructuredContent(fileName));

				_write(
					jsonObject.toString(4), "build/structured-content",
					new File(fileName));

				continue;
			}

			long delta = System.currentTimeMillis() - start;

			if (delta > (_oauthExpirationMillis - 100000)) {
				_initResourceBuilders(_getOAuthAuthorization());

				start = System.currentTimeMillis();
			}

			try {
				StructuredContent importedStructuredContent = null;

				StructuredContent structuredContent = _toStructuredContent(
					fileName);

				if (externalReferenceCodeStructuredContents.containsKey(
						structuredContent.getExternalReferenceCode())) {

					StructuredContent siteStructuredContent =
						externalReferenceCodeStructuredContents.get(
							structuredContent.getExternalReferenceCode());

					importedStructuredContentIds.add(
						siteStructuredContent.getId());

					System.out.println(
						"Updating structured content " +
							structuredContent.getFriendlyUrlPath());

					importedStructuredContent =
						_structuredContentResource.putStructuredContent(
							siteStructuredContent.getId(), structuredContent);

					_setVisibility(fileName, siteStructuredContent);

					updatedStructuredContentCount++;
				}
				else {
					if (friendlyUrlPathStructuredContents.containsKey(
							structuredContent.getFriendlyUrlPath())) {

						StructuredContent siteStructuredContent =
							friendlyUrlPathStructuredContents.get(
								structuredContent.getFriendlyUrlPath());

						importedStructuredContentIds.add(
							siteStructuredContent.getId());

						System.out.println(
							"Deleting structured content " +
								structuredContent.getFriendlyUrlPath());

						_structuredContentResource.deleteStructuredContent(
							siteStructuredContent.getId());
					}

					System.out.println(
						"Adding structured content " +
							structuredContent.getFriendlyUrlPath());

					importedStructuredContent =
						_structuredContentResource.
							postStructuredContentFolderStructuredContent(
								structuredContent.
									getStructuredContentFolderId(),
								structuredContent);

					_setVisibility(fileName, structuredContent);

					addedStructuredContentCount++;
				}

				if (!Objects.equals(
						importedStructuredContent.getFriendlyUrlPath(),
						structuredContent.getFriendlyUrlPath())) {

					_structuredContentResource.deleteStructuredContent(
						importedStructuredContent.getId());

					throw new Exception(
						"Modified friendly URL path " +
							importedStructuredContent.getFriendlyUrlPath());
				}
			}
			catch (Exception exception) {
				_error(fileName + ": " + exception.getMessage());
			}
		}

		existingStructuredContentIds.removeAll(importedStructuredContentIds);

		for (Long existingStructuredContentId : existingStructuredContentIds) {
			StructuredContent structuredContent = idStructuredContents.get(
				existingStructuredContentId);

			try {
				System.out.println(
					"Deleting orphaned structured content " +
						structuredContent.getFriendlyUrlPath());

				_structuredContentResource.deleteStructuredContent(
					existingStructuredContentId);
			}
			catch (Exception exception) {
				_error(
					structuredContent.getFriendlyUrlPath() + ": " +
						exception.getMessage());
			}
		}

		System.out.println(
			addedStructuredContentCount + " structured contents were added.");
		System.out.println(
			existingStructuredContentIds.size() +
				" structured contents were deleted.");
		System.out.println(
			updatedStructuredContentCount +
				" structured contents were updated.");

		if (!_warningMessages.isEmpty()) {
			System.out.println(_warningMessages.size() + " warning messages:");

			for (String warningMessage : _warningMessages) {
				System.out.println(warningMessage);
			}
		}

		if (!_errorMessages.isEmpty()) {
			System.out.println(_errorMessages.size() + " error messages:");

			for (String errorMessage : _errorMessages) {
				System.out.println(errorMessage);
			}

			System.exit(1);
		}
	}

	private void _addFileNames(String fileName) {
		File file = new File(fileName);

		if (file.isDirectory() &&
			!Objects.equals(file.getName(), "resources") &&
			!Objects.equals(file.getName(), "_snippets")) {

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

	private void _error(String errorMessage) {
		System.out.println(errorMessage);

		_errorMessages.add(errorMessage);
	}

	private JSONArray _getBreadcrumbLinksJSONArray(File file) throws Exception {
		JSONArray breadcrumbLinksJSONArray = new JSONArray();

		File originalFile = file;
		File parentMarkdownFile;

		while ((parentMarkdownFile = _getParentMarkdownFile(file)) != null) {
			JSONObject linkJSONObject = new JSONObject();

			linkJSONObject.put(
				"title",
				_getTitle(
					FileUtils.readFileToString(
						parentMarkdownFile, StandardCharsets.UTF_8)));

			Path originalFilePath = Paths.get(originalFile.getParent());
			Path parentMarkdownFilePath = Paths.get(parentMarkdownFile.toURI());

			String parentMarkdownFilePathString = String.valueOf(
				originalFilePath.relativize(parentMarkdownFilePath));

			linkJSONObject.put(
				"url",
				FilenameUtils.removeExtension(parentMarkdownFilePathString));

			file = parentMarkdownFile;

			breadcrumbLinksJSONArray.put(linkJSONObject);
		}

		return breadcrumbLinksJSONArray;
	}

	private String _getDescription(String text) {
		TextCollectingVisitor textCollectingVisitor =
			new TextCollectingVisitor();

		return textCollectingVisitor.collectAndGetText(_parser.parse(text));
	}

	private String[] _getDirNames(String fileName) {
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

	private JSONArray _getNavigationLinksJSONArray(
			File navigationFile, File file, String text)
		throws Exception {

		JSONArray navigationLinksJSONArray = new JSONArray();

		Document document = _parser.parse(text);

		SnakeYamlFrontMatterVisitor snakeYamlFrontMatterVisitor =
			new SnakeYamlFrontMatterVisitor();

		snakeYamlFrontMatterVisitor.visit(document);

		Map<String, Object> data = snakeYamlFrontMatterVisitor.getData();

		if ((data == null) || !data.containsKey("toc")) {
			return navigationLinksJSONArray;
		}

		Object toc = data.get("toc");

		if (!(toc instanceof ArrayList)) {
			return navigationLinksJSONArray;
		}

		for (Object tocEntry : (ArrayList)toc) {
			if (!(tocEntry instanceof String)) {
				continue;
			}

			Matcher matcher = _markdownLinkPattern.matcher((String)tocEntry);

			if (matcher.find()) {
				JSONObject linkJSONObject = new JSONObject();

				linkJSONObject.put(
					"title", matcher.group(1)
				).put(
					"url", matcher.group(2)
				);

				navigationLinksJSONArray.put(linkJSONObject);

				continue;
			}

			String tocFileName = (String)tocEntry;

			String filePathString =
				navigationFile.getParent() + File.separator + tocFileName;

			File tocFile = new File(filePathString);

			if (!tocFile.exists() || tocFile.isDirectory()) {
				_warn("Nonexistent or invalid TOC file " + tocFile.getPath());

				continue;
			}

			JSONObject linkJSONObject = new JSONObject();

			linkJSONObject.put(
				"title",
				_getTitle(
					FileUtils.readFileToString(
						tocFile, StandardCharsets.UTF_8)));

			Path filePath = Paths.get(file.getParent());
			Path tocPath = Paths.get(tocFile.toURI());

			linkJSONObject.put(
				"url",
				FilenameUtils.removeExtension(
					String.valueOf(filePath.relativize(tocPath))));

			navigationLinksJSONArray.put(linkJSONObject);
		}

		return navigationLinksJSONArray;
	}

	private JSONArray _getNavigationLinksJSONArray(File file, String text)
		throws Exception {

		JSONArray navigationLinksJSONArray = _getNavigationLinksJSONArray(
			file, file, text);

		if (navigationLinksJSONArray.isEmpty()) {
			File parentMarkdownFile = _getParentMarkdownFile(file);

			if (parentMarkdownFile != null) {
				navigationLinksJSONArray = _getNavigationLinksJSONArray(
					parentMarkdownFile, file,
					FileUtils.readFileToString(
						parentMarkdownFile, StandardCharsets.UTF_8));
			}
		}

		if (navigationLinksJSONArray.isEmpty()) {
			_warn("Missing navigation for " + file.getPath());
		}

		return navigationLinksJSONArray;
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

	private File _getParentMarkdownFile(File file) throws Exception {
		if (Objects.equals(file.getName(), "index.md")) {
			return null;
		}

		File parentFile = file.getParentFile();

		File parentMarkdownFile = new File(parentFile.getPath() + ".md");

		while (!parentMarkdownFile.exists()) {
			parentFile = parentFile.getParentFile();

			if (Objects.equals(parentFile.getPath(), _markdownImportDirName)) {
				break;
			}

			parentMarkdownFile = new File(parentFile.getPath() + ".md");
		}

		if (!parentMarkdownFile.exists()) {
			parentFile = file.getParentFile();

			parentMarkdownFile = new File(
				parentFile.getPath() + File.separator + "index.md");
		}

		if (!parentMarkdownFile.exists()) {
			_warn(
				"Missing parent markdown for " + parentMarkdownFile.getPath());

			return null;
		}

		return parentMarkdownFile;
	}

	private String _getProduct(File file) {
		String filePathString = file.getPath();

		String relativeFilePathString = filePathString.substring(
			_markdownImportDirName.length() + 1);

		String[] dirNames = _getDirNames(relativeFilePathString);

		return dirNames[0];
	}

	private List<StructuredContent> _getSiteStructuredContents(long siteId)
		throws Exception {

		if (_offline) {
			return Collections.emptyList();
		}

		List<StructuredContent> structuredContents = new ArrayList<>();

		for (int page = 1;; page++) {
			Page<StructuredContent> structuredContentsPage =
				_structuredContentResource.getSiteStructuredContentsPage(
					siteId, true, null, null, null, Pagination.of(page, 50),
					null);

			structuredContents.addAll(structuredContentsPage.getItems());

			if (structuredContentsPage.getLastPage() == page) {
				break;
			}
		}

		return structuredContents;
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
						_liferaySiteId, null, null, null,
						"name eq '" + dirName + "'", null, null);

			structuredContentFolder = page.fetchFirstItem();

			if (structuredContentFolder == null) {
				structuredContentFolder =
					_structuredContentFolderResource.
						postSiteStructuredContentFolder(
							_liferaySiteId,
							new StructuredContentFolder() {
								{
									description = "";
									name = dirName;
									viewableBy = ViewableBy.ANYONE;
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
									viewableBy = ViewableBy.ANYONE;
								}
							});
			}
		}

		structuredContentFolderId = structuredContentFolder.getId();

		_structuredContentFolderIds.put(key, structuredContentFolderId);

		return structuredContentFolderId;
	}

	private Long[] _getTaxonomyCategoryIds(String text) {
		SnakeYamlFrontMatterVisitor snakeYamlFrontMatterVisitor =
			new SnakeYamlFrontMatterVisitor();

		snakeYamlFrontMatterVisitor.visit(_parser.parse(text));

		Map<String, Object> data = snakeYamlFrontMatterVisitor.getData();

		if ((data == null) || !data.containsKey("taxonomy-category-names")) {
			return new Long[0];
		}

		Object taxonomyCategoryNames = data.get("taxonomy-category-names");

		if (!(taxonomyCategoryNames instanceof ArrayList)) {
			return new Long[0];
		}

		List<Long> taxonomyCategoryIds = new ArrayList<>();

		for (Object taxonomyCategoryNameObject :
				(ArrayList)taxonomyCategoryNames) {

			if (!(taxonomyCategoryNameObject instanceof String)) {
				continue;
			}

			String taxonomyCategoryName = (String)taxonomyCategoryNameObject;

			if (!_taxonomyCategoriesJSONObject.has(taxonomyCategoryName)) {
				_warn(
					"No taxonomy category exists with the name: " +
						taxonomyCategoryName);

				continue;
			}

			taxonomyCategoryIds.add(
				_taxonomyCategoriesJSONObject.getLong(taxonomyCategoryName));
		}

		if (taxonomyCategoryIds.isEmpty()) {
			return new Long[0];
		}

		return taxonomyCategoryIds.toArray(new Long[0]);
	}

	private String _getTitle(String text) {
		int x = text.indexOf("#");

		int y = text.indexOf(StringPool.NEW_LINE, x);

		String title = text.substring(x + 1, y);

		return title.trim();
	}

	private String _getUuid(String text) {
		Document document = _parser.parse(text);

		SnakeYamlFrontMatterVisitor snakeYamlFrontMatterVisitor =
			new SnakeYamlFrontMatterVisitor();

		snakeYamlFrontMatterVisitor.visit(document);

		Map<String, Object> data = snakeYamlFrontMatterVisitor.getData();

		if ((data == null) || !data.containsKey("uuid")) {
			return StringPool.BLANK;
		}

		Object uuid = data.get("uuid");

		if (!(uuid instanceof String)) {
			return StringPool.BLANK;
		}

		return uuid.toString();
	}

	private void _initFlexmark() {
		MutableDataSet mutableDataSet = new MutableDataSet(
		).set(
			AdmonitionExtension.QUALIFIER_TYPE_MAP,
			HashMapBuilder.put(
				"error", "error"
			).put(
				"important", "important"
			).put(
				"note", "note"
			).put(
				"tip", "tip"
			).put(
				"warning", "warning"
			).build()
		).set(
			AdmonitionExtension.TYPE_SVG_MAP, new HashMap<String, String>()
		).set(
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
		DataDefinitionResource.Builder dataDefinitionResourceBuilder =
			DataDefinitionResource.builder();

		_dataDefinitionResource = dataDefinitionResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();

		SiteResource.Builder siteResourceBuilder = SiteResource.builder();

		_siteResource = siteResourceBuilder.header(
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

		StructuredContentResource.Builder structuredContentResourceBuilder =
			StructuredContentResource.builder();

		_structuredContentResource = structuredContentResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();

		TaxonomyCategoryResource.Builder taxonomyCategoryResourceBuilder =
			TaxonomyCategoryResource.builder();

		_taxonomyCategoryResource = taxonomyCategoryResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();

		TaxonomyVocabularyResource.Builder taxonomyVocabularyResourceBuilder =
			TaxonomyVocabularyResource.builder();

		_taxonomyVocabularyResource = taxonomyVocabularyResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();
	}

	private void _loadTaxonomyCategories() throws Exception {
		_taxonomyCategoriesJSONObject = new JSONObject();

		com.liferay.headless.admin.taxonomy.client.pagination.Page
			<TaxonomyVocabulary> taxonomyVocabulariesPage =
				_taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					_liferaySiteId, null, null, null,
					com.liferay.headless.admin.taxonomy.client.pagination.
						Pagination.of(-1, -1),
					null);

		for (TaxonomyVocabulary taxonomyVocabulary :
				taxonomyVocabulariesPage.getItems()) {

			com.liferay.headless.admin.taxonomy.client.pagination.Page
				<TaxonomyCategory> taxonomyCategoriesPage =
					_taxonomyCategoryResource.
						getTaxonomyVocabularyTaxonomyCategoriesPage(
							taxonomyVocabulary.getId(), null, null, null, null,
							com.liferay.headless.admin.taxonomy.client.
								pagination.Pagination.of(-1, -1),
							null);

			for (TaxonomyCategory taxonomyCategory :
					taxonomyCategoriesPage.getItems()) {

				_taxonomyCategoriesJSONObject.put(
					taxonomyCategory.getName(), taxonomyCategory.getId());

				_loadTaxonomyCategories(taxonomyCategory);
			}
		}
	}

	private void _loadTaxonomyCategories(
			TaxonomyCategory parentTaxonomyCategory)
		throws Exception {

		if (parentTaxonomyCategory.getNumberOfTaxonomyCategories() == 0) {
			return;
		}

		com.liferay.headless.admin.taxonomy.client.pagination.Page
			<TaxonomyCategory> taxonomyCategoriesPage =
				_taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategory.getId(), null, null, null,
						com.liferay.headless.admin.taxonomy.client.pagination.
							Pagination.of(-1, -1),
						null);

		for (TaxonomyCategory taxonomyCategory :
				taxonomyCategoriesPage.getItems()) {

			_taxonomyCategoriesJSONObject.put(
				taxonomyCategory.getName(), taxonomyCategory.getId());

			_loadTaxonomyCategories(taxonomyCategory);
		}
	}

	private String _processAbsoluteZipURLs(String line) {
		Matcher matcher = _absoluteZipURLPattern.matcher(line);

		if (matcher.find()) {
			line = matcher.replaceFirst(_liferayLearnResourcesDomain + "/$1");
		}

		return line;
	}

	private String _processGridBlock(List<String> gridLines, int columns) {
		List<GridCard> gridCards = new ArrayList<>();

		GridCard currentGridCard = new GridCard();

		for (String gridLine : gridLines) {
			if (gridLine.equals(":::")) {
				gridCards.add(currentGridCard);

				currentGridCard = new GridCard();
			}
			else if (gridLine.startsWith(":::{grid-item-card}")) {
				int index = gridLine.indexOf(StringPool.CLOSE_CURLY_BRACE);

				currentGridCard.setTitle(gridLine.substring(index + 2));
			}
			else if (gridLine.startsWith(":gutter")) {
			}
			else if (gridLine.startsWith(":link:")) {
				String link = gridLine.substring(7);

				currentGridCard.setLink(
					StringUtil.removeSubstring(link, ".md"));
			}
			else {
				currentGridCard.addContentLine(gridLine);
			}
		}

		StringBundler sb = new StringBundler(4 + gridCards.size());

		sb.append("<div class=\"landing-page landing-page-grid-");
		sb.append(String.valueOf(columns));
		sb.append("\">");

		for (GridCard gridCard : gridCards) {
			sb.append(gridCard);
		}

		sb.append("</div>");

		return sb.toString();
	}

	private String _processGridBlocks(
			BufferedReader bufferedReader, String line, File markdownFile)
		throws Exception {

		String trimmedLine = line.trim();

		if (!trimmedLine.startsWith("::::{grid}")) {
			return line;
		}

		List<String> gridLines = new ArrayList<>();

		int index = line.indexOf(StringPool.CLOSE_CURLY_BRACE);

		int columns = Integer.valueOf(
			StringUtil.trim(line.substring(index + 2)));

		while (true) {
			String gridLine = bufferedReader.readLine();

			if (gridLine == null) {
				_warn(
					"Unclosed grid block found in " +
						markdownFile.getCanonicalPath());

				break;
			}

			String trimmedGridLine = gridLine.trim();

			if (trimmedGridLine.startsWith("::::")) {
				break;
			}

			gridLines.add(gridLine);
		}

		return _processGridBlock(gridLines, columns);
	}

	private String _processInclude(String includeFileName, File markdownFile)
		throws Exception {

		File file = null;

		String markdownFileName = markdownFile.getCanonicalPath();

		if (includeFileName.startsWith(File.separator)) {
			String dirName = markdownFileName.substring(
				_markdownImportDirName.length() + 1);

			String[] dirNameParts = StringUtil.split(
				dirName, File.separatorChar);

			if (dirNameParts.length < 3) {
				throw new Exception("Invalid directory " + dirName);
			}

			StringBuilder sb = new StringBuilder();

			sb.append(_markdownImportDirName);
			sb.append(File.separator);
			sb.append(dirNameParts[0]);
			sb.append(File.separator);
			sb.append(dirNameParts[1]);
			sb.append(File.separator);
			sb.append(dirNameParts[2]);
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
			_warn("Nonexistent literal include " + file);

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
				for (String lineRange :
						StringUtil.split(value, CharPool.COMMA)) {

					Tuple tuple = null;

					String[] lineRangeParts = StringUtil.split(
						lineRange, CharPool.DASH);

					if (lineRangeParts.length == 1) {
						tuple = new Tuple(
							GetterUtil.getInteger(lineRangeParts[0]),
							GetterUtil.getInteger(lineRangeParts[0]));
					}
					else if (lineRangeParts.length == 2) {
						tuple = new Tuple(
							GetterUtil.getInteger(lineRangeParts[0]),
							GetterUtil.getInteger(lineRangeParts[1]));
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
			line = _processAbsoluteZipURLs(line);
			line = _processGridBlocks(bufferedReader, line, markdownFile);
			line = _processMySTDirectiveBlocks(
				bufferedReader, line, markdownFile);
			line = _processSphinxBadges(line);

			sb.append(line);

			sb.append("\n");
		}

		return sb.toString();
	}

	private String _processMySTDirectiveBlocks(
			BufferedReader bufferedReader, String line, File markdownFile)
		throws Exception {

		String trimmedLine = line.trim();

		if (!trimmedLine.startsWith(_MYST_DIRECTIVE_BLOCK_START)) {
			return line;
		}

		String leadingWhitespace = line.substring(
			0, line.indexOf(_MYST_DIRECTIVE_BLOCK_START));

		List<String> mySTDirectiveLines = new ArrayList<>();

		int index = line.indexOf(StringPool.CLOSE_CURLY_BRACE);

		String directiveName = line.substring(
			line.indexOf(StringPool.OPEN_CURLY_BRACE) + 1, index);

		while (true) {
			String mySTDirectiveLine = bufferedReader.readLine();

			if (mySTDirectiveLine == null) {
				_warn(
					"Unclosed MyST directive block found in " +
						markdownFile.getCanonicalPath());

				break;
			}

			if (mySTDirectiveLine.startsWith(
					leadingWhitespace + _MYST_DIRECTIVE_BLOCK_END)) {

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
		else if (directiveName.equals("raw")) {
			for (String mySTDirectiveLine : mySTDirectiveLines) {
				Matcher matcher = _literalIncludeParameterPattern.matcher(
					mySTDirectiveLine.trim());

				if (!matcher.find()) {
					continue;
				}

				String name = matcher.group(1);

				if (name.equals("file")) {
					String value = matcher.group(2);

					if (value.contains("landingpage_template.html")) {
						_landingPageFiles.add(markdownFile);

						return StringPool.BLANK;
					}

					return _processInclude(value.trim(), markdownFile);
				}
			}

			_warn(
				"Invalid parameters found for raw directive block in " +
					markdownFile.getCanonicalPath());

			return StringPool.BLANK;
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
		sb.append(" \"");
		sb.append(directiveName);
		sb.append("\" \n");

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
			line = matcher.replaceFirst("<span class=\"bdg bdg-$1\">$2</span>");
		}

		return line;
	}

	private void _setVisibility(
			String fileName, StructuredContent structuredContent)
		throws Exception {

		SnakeYamlFrontMatterVisitor snakeYamlFrontMatterVisitor =
			new SnakeYamlFrontMatterVisitor();

		File file = new File(fileName);

		snakeYamlFrontMatterVisitor.visit(
			_parser.parse(
				_processMarkdown(
					FileUtils.readFileToString(file, StandardCharsets.UTF_8),
					file)));

		Map<String, Object> data = snakeYamlFrontMatterVisitor.getData();

		if ((data == null) || !data.containsKey("visibility")) {
			Permission[] permissions = {
				new Permission() {
					{
						actionIds = new String[] {"VIEW"};
						roleName = "Guest";
					}
				}
			};

			_structuredContentResource.putStructuredContentPermissionsPage(
				structuredContent.getId(), permissions);

			return;
		}

		Object roleNames = data.get("visibility");

		if (!(roleNames instanceof ArrayList)) {
			return;
		}

		List<Permission> permissions = new ArrayList<>();

		for (Object roleNamesObject : (ArrayList)roleNames) {
			if (!(roleNamesObject instanceof String)) {
				continue;
			}

			permissions.add(
				new Permission() {
					{
						actionIds = new String[] {"ADD_DISCUSSION", "VIEW"};
						roleName = (String)roleNamesObject;
					}
				});
		}

		if (permissions.isEmpty()) {
			return;
		}

		permissions.add(
			new Permission() {
				{
					actionIds = new String[0];
					roleName = "Guest";
				}
			});

		_structuredContentResource.putStructuredContentPermissionsPage(
			structuredContent.getId(), permissions.toArray(new Permission[0]));
	}

	private String _toFriendlyURLPath(File file) {
		String filePathString = file.getPath();

		String relativeFilePathString = filePathString.substring(
			_markdownImportDirName.length() + 1);

		String friendlyURLPathString = StringUtil.merge(
			_getDirNames(relativeFilePathString), StringPool.FORWARD_SLASH);

		return FilenameUtils.removeExtension(friendlyURLPathString);
	}

	private String _toHTML(File file, String text) throws Exception {
		_write(text, "build/markdown", file);

		Document document = _parser.parse(text);

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

		ContentFieldValue englishBreadcrumbLinksContentFieldValue =
			new ContentFieldValue() {
				{
					data = String.valueOf(
						_getBreadcrumbLinksJSONArray(englishFile));
				}
			};

		String englishText = _processMarkdown(
			FileUtils.readFileToString(englishFile, StandardCharsets.UTF_8),
			englishFile);

		ContentFieldValue englishContentContentFieldValue =
			new ContentFieldValue() {
				{
					data = _toHTML(englishFile, englishText);
				}
			};

		ContentFieldValue englishLandingPageContentFieldValue =
			new ContentFieldValue() {
				{
					data = String.valueOf(
						_landingPageFiles.contains(englishFile));
				}
			};
		ContentFieldValue englishNavigationLinksContentFieldValue =
			new ContentFieldValue() {
				{
					data = String.valueOf(
						_getNavigationLinksJSONArray(englishFile, englishText));
				}
			};
		ContentFieldValue englishProductContentFieldValue =
			new ContentFieldValue() {
				{
					data = _getProduct(englishFile);
				}
			};

		String englishTitle = _getTitle(englishText);

		File japaneseFile = new File(
			StringUtil.replace(fileName, "/en/", "/ja/"));

		if (japaneseFile.exists()) {
			String japaneseText = _processMarkdown(
				FileUtils.readFileToString(
					japaneseFile, StandardCharsets.UTF_8),
				japaneseFile);

			structuredContent.setContentFields(
				new ContentField[] {
					new ContentField() {
						{
							contentFieldValue =
								englishBreadcrumbLinksContentFieldValue;
							contentFieldValue_i18n = HashMapBuilder.put(
								"en-US", englishBreadcrumbLinksContentFieldValue
							).put(
								"ja-JP",
								new ContentFieldValue() {
									{
										data = String.valueOf(
											_getBreadcrumbLinksJSONArray(
												japaneseFile));
									}
								}
							).build();
							name = "breadcrumbLinks";
						}
					},
					new ContentField() {
						{
							contentFieldValue = englishContentContentFieldValue;
							contentFieldValue_i18n = HashMapBuilder.put(
								"en-US", englishContentContentFieldValue
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
					},
					new ContentField() {
						{
							contentFieldValue =
								englishLandingPageContentFieldValue;
							contentFieldValue_i18n = HashMapBuilder.put(
								"en-US", englishLandingPageContentFieldValue
							).put(
								"ja-JP", englishLandingPageContentFieldValue
							).build();
							name = "landingPage";
						}
					},
					new ContentField() {
						{
							contentFieldValue =
								englishNavigationLinksContentFieldValue;
							contentFieldValue_i18n = HashMapBuilder.put(
								"en-US", englishNavigationLinksContentFieldValue
							).put(
								"ja-JP",
								new ContentFieldValue() {
									{
										data = String.valueOf(
											_getNavigationLinksJSONArray(
												japaneseFile, japaneseText));
									}
								}
							).build();
							name = "navigationLinks";
						}
					},
					new ContentField() {
						{
							contentFieldValue = englishProductContentFieldValue;
							contentFieldValue_i18n = HashMapBuilder.put(
								"en-US", englishProductContentFieldValue
							).put(
								"ja-JP",
								new ContentFieldValue() {
									{
										data = _getProduct(japaneseFile);
									}
								}
							).build();
							name = "product";
						}
					}
				});
			structuredContent.setDescription_i18n(
				HashMapBuilder.put(
					"en-US", _getDescription(englishText)
				).put(
					"ja-JP", _getDescription(japaneseText)
				).build());

			structuredContent.setFriendlyUrlPath_i18n(
				HashMapBuilder.put(
					"en-US", _toFriendlyURLPath(englishFile)
				).put(
					"ja-JP", _toFriendlyURLPath(japaneseFile)
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
							contentFieldValue =
								englishBreadcrumbLinksContentFieldValue;
							name = "breadcrumbLinks";
						}
					},
					new ContentField() {
						{
							contentFieldValue = englishContentContentFieldValue;
							name = "content";
						}
					},
					new ContentField() {
						{
							contentFieldValue =
								englishLandingPageContentFieldValue;
							name = "landingPage";
						}
					},
					new ContentField() {
						{
							contentFieldValue =
								englishNavigationLinksContentFieldValue;
							name = "navigationLinks";
						}
					},
					new ContentField() {
						{
							contentFieldValue = englishProductContentFieldValue;
							name = "product";
						}
					}
				});
			structuredContent.setDescription(_getDescription(englishText));
		}

		structuredContent.setContentStructureId(_liferayContentStructureId);
		structuredContent.setExternalReferenceCode(_getUuid(englishText));
		structuredContent.setFriendlyUrlPath(_toFriendlyURLPath(englishFile));
		structuredContent.setTaxonomyCategoryIds(
			_getTaxonomyCategoryIds(englishText));

		if (!_offline) {
			structuredContent.setStructuredContentFolderId(
				_getStructuredContentFolderId(
					FilenameUtils.getPathNoEndSeparator(
						fileName.substring(_markdownImportDirName.length()))));
		}

		structuredContent.setTitle(englishTitle);

		return structuredContent;
	}

	private boolean _validateUUIDs() throws Exception {
		Set<String> uuids = new HashSet<>();

		for (String fileName : _fileNames) {
			if (!fileName.contains("/en/") || !fileName.endsWith(".md")) {
				continue;
			}

			File englishFile = new File(fileName);

			String englishText = FileUtils.readFileToString(
				englishFile, StandardCharsets.UTF_8);

			String uuid = _getUuid(englishText);

			if (Validator.isNull(uuid)) {
				System.out.println("Missing UUID in " + fileName);

				return false;
			}

			if (uuids.contains(uuid)) {
				System.out.println(
					StringBundler.concat(
						"Duplicate UUID ", uuid, " in ", fileName));

				return false;
			}

			uuids.add(uuid);

			File japaneseFile = new File(
				StringUtil.replace(fileName, "/en/", "/ja/"));

			if (japaneseFile.exists()) {
				String japaneseText = FileUtils.readFileToString(
					japaneseFile, StandardCharsets.UTF_8);

				if (Validator.isNotNull(_getUuid(japaneseText))) {
					System.out.println(
						"Irrelevant UUID in " + japaneseFile.getPath());

					return false;
				}
			}
		}

		return true;
	}

	private void _visit(Image image) throws Exception {
		BasedSequence basedSequence = image.getUrl();

		if (basedSequence.startsWith("http")) {
			return;
		}

		String fileName =
			FilenameUtils.getFullPath(_markdownFile.getPath()) + basedSequence;

		fileName = fileName.replaceAll("/ja/", "/en/");

		File file = new File(fileName);

		if (!file.exists()) {
			_warn(
				_markdownFile.getCanonicalPath() +
					" references nonexistent image file " +
						file.getCanonicalPath());

			return;
		}

		String filePathString = file.getCanonicalPath();

		image.setUrl(
			BasedSequence.of(
				_liferayLearnResourcesDomain + "/images" +
					filePathString.substring(_markdownImportDirName.length())));

		_nodeVisitor.visitChildren(image);
	}

	private void _visit(Link link) {
		BasedSequence basedSequence = link.getUrl();

		link.setUrl(basedSequence.replace(".md", StringPool.BLANK));

		String url = basedSequence.toString();

		if (url.contains(".zip") && url.startsWith("./")) {
			try {
				String markdownFilePathString = _markdownFile.getParent();

				String dirName = markdownFilePathString.substring(
					_markdownImportDirName.length());

				link.setUrl(
					BasedSequence.of(
						_liferayLearnResourcesDomain + dirName +
							url.substring(1)));
			}
			catch (Exception exception) {
				_error(_markdownFile.getPath() + ": " + exception.getMessage());
			}
		}
	}

	private void _warn(String warningMessage) {
		System.out.println(warningMessage);

		_warningMessages.add(warningMessage);
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

	private static final String _MYST_DIRECTIVE_BLOCK_END = "```";

	private static final String _MYST_DIRECTIVE_BLOCK_START = "```{";

	private static final Pattern _absoluteZipURLPattern = Pattern.compile(
		"https:\\/\\/learn\\.liferay\\.com\\/(.*liferay-....\\.zip)");
	private static final Pattern _literalIncludeParameterPattern =
		Pattern.compile(":(.*): (.*)");
	private static final Pattern _markdownLinkPattern = Pattern.compile(
		"\\[(.*)\\]\\((.*)\\)");
	private static final Pattern _sphinxBadgePattern = Pattern.compile(
		"\\{bdg-(.*)\\}`(.*)`");

	private DataDefinitionResource _dataDefinitionResource;
	private final List<String> _errorMessages = new ArrayList<>();
	private final Set<String> _fileNames = new TreeSet<>();
	private final Set<File> _landingPageFiles = new HashSet<>();
	private final long _liferayContentStructureId;
	private final String _liferayLearnResourcesDomain;
	private final String _liferayOAuthClientId;
	private final String _liferayOAuthClientSecret;
	private final long _liferaySiteId;
	private final URL _liferayURL;
	private File _markdownFile;
	private final String _markdownImportDirName;

	private final NodeVisitor _nodeVisitor = new NodeVisitor(
		new VisitHandler<Image>(
			Image.class,
			new Visitor<Image>() {

				@Override
				public void visit(Image image) {
					try {
						_visit(image);
					}
					catch (Exception exception) {
						_error(
							_markdownFile.getPath() + ": " +
								exception.getMessage());
					}
				}

			}),
		new VisitHandler<Link>(
			Link.class,
			new Visitor<Link>() {

				@Override
				public void visit(Link link) {
					_visit(link);
				}

			}));

	private long _oauthExpirationMillis;
	private final boolean _offline;
	private Parser _parser;
	private HtmlRenderer _renderer;
	private SiteResource _siteResource;
	private final Map<String, Long> _structuredContentFolderIds =
		new HashMap<>();
	private StructuredContentFolderResource _structuredContentFolderResource;
	private StructuredContentResource _structuredContentResource;
	private JSONObject _taxonomyCategoriesJSONObject;
	private TaxonomyCategoryResource _taxonomyCategoryResource;
	private TaxonomyVocabularyResource _taxonomyVocabularyResource;
	private final List<String> _warningMessages = new ArrayList<>();
	private final Yaml _yaml = new Yaml();

	private class GridCard {

		public void addContentLine(String contentLine) {
			_contentLines.add(contentLine);
		}

		public String getTitleHTML(boolean link) {
			StringBundler sb = new StringBundler(3);

			if (link) {
				sb.append("<h4 class=\"primary-heading-link title\">");
			}
			else {
				sb.append("<h4 class=\"title\">");
			}

			sb.append(_title);
			sb.append("</h4>");

			return sb.toString();
		}

		public void setLink(String link) {
			_link = link;
		}

		public void setTitle(String title) {
			_title = title;
		}

		public String toString() {
			StringBundler sb = new StringBundler(16);

			sb.append("<div class=\"section-card\">");
			sb.append("<div class=\"autofit-row autofit-row-center\">");
			sb.append("<div class=\"autofit-col autofit-col-expand\">");

			if (Validator.isNotNull(_link)) {
				sb.append("<a href=\"");
				sb.append(_link);
				sb.append("\">");

				if (Validator.isNotNull(_title)) {
					sb.append(getTitleHTML(true));
				}

				sb.append("</a>");
			}
			else if (Validator.isNotNull(_title)) {
				sb.append(getTitleHTML(false));
			}

			if (!_contentLines.isEmpty()) {
				sb.append("<div class=\"subsection-wrapper\">");

				for (String contentLine : _contentLines) {
					sb.append(contentLine);
					sb.append(StringPool.NEW_LINE);
				}

				sb.append("</div>");
			}

			sb.append("</div>");
			sb.append("</div>");
			sb.append("</div>");

			return sb.toString();
		}

		private List<String> _contentLines = new ArrayList<>();
		private String _link = StringPool.BLANK;
		private String _title = StringPool.BLANK;

	}

	private class SnakeYamlFrontMatterVisitor
		implements YamlFrontMatterVisitor {

		public Map<String, Object> getData() {
			return _data;
		}

		public void visit(Node node) {
			_yamlFrontMatterVisitor.visit(node);
		}

		@Override
		public void visit(YamlFrontMatterBlock yamlFrontMatterBlock) {
			String yamlString = String.valueOf(yamlFrontMatterBlock.getChars());

			yamlString = yamlString.replaceAll("---", "");

			_data = _yaml.load(yamlString);
		}

		@Override
		public void visit(YamlFrontMatterNode yamlFrontMatterNode) {
		}

		private Map<String, Object> _data;
		private final NodeVisitor _yamlFrontMatterVisitor = new NodeVisitor(
			YamlFrontMatterVisitorExt.VISIT_HANDLERS(this));

	}

}