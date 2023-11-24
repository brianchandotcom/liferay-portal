<style>
	.portlet-rss .feed-entry-content {
		margin-bottom: 20px;
		padding-left: 0;
	}
</style>

<#if entries?has_content>
	<#assign dateFormat = "dd MMM yyyy - HH:mm:ss" />

	<div class="container-fluid">
		<div class="row" id="<@portlet.namespace />feedsTab">
			<ul class="col-xs-4">
				<#list entries as curEntry>
					<li><a href="#tab-${curEntry_index}">${htmlUtil.escape(curEntry.getTitle())}</a></li>
				</#list>
			</ul>

			<div class="col-xs-8 tab-content">
			entries as curEntry>
					<#assign rssFeedEntries = curEntry.getRSSFeedEntries(themeDisplay) />

					<#if rssFeedEntries??>
						<div class="tab-pane" id="tab-${curEntry_index}">
							<#list rssFeedEntries as rssFeedEntry>
								<#if (rssFeedEntry_index > entriesPerFeed?number)>
									<#break>
								</#if>

								<#assign syndEntry = rssFeedEntry.getSyndEntry() />

								<div class="feed-entry-content">
									<div class="feed-title">
										<@liferay_aui["a"] href="${htmlUtil.escapeJSLink(rssFeedEntry.getSyndEntryLink())}">${htmlUtil.escape(syndEntry.getTitle())}</@>
									</div>

									<#if getterUtil.getBoolean(showFeedItemAuthor) && syndEntry.getAuthor()??>