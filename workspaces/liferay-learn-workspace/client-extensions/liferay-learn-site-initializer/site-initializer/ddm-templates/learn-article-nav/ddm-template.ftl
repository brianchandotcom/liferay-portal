<#assign
	navigationJSONObject = jsonFactoryUtil.createJSONObject(htmlUtil.unescape(navigation.getData()?trim))

	breadcrumbJSONArray = navigationJSONObject.getJSONArray("breadcrumb")
	childrenJSONArray = navigationJSONObject.getJSONArray("children")
	parentJSONObject = navigationJSONObject.getJSONObject("parent")
	siblingsJSONArray = navigationJSONObject.getJSONArray("siblings")
/>

<div class="learn-article-nav">
	<div class="learn-article-nav-content">
		<#if parentJSONObject?has_content && parentJSONObject.getString("url")?has_content>
			<#if breadcrumbJSONArray.length() gt 1>
				<div class="learn-article-nav-item learn-article-nav-parent menu-trigger menu-trigger-capabilities">
					<a class="primary-item-toc" href='${parentJSONObject.getString("url")}'>
						<@clay["icon"] symbol="angle-left" />

						<span>${parentJSONObject.getString("title")}</span>
						<span class="liferay-nav-item-right-arrow"></span>
					</a>
				</div>
			</#if>
		</#if>

		<#if childrenJSONArray.length() gt 0>
			<#if breadcrumbJSONArray.length() lt 2>
				<div class="menu-trigger menu-trigger-documentation">
					<div class="table-of-contents-documentation">
						<span>
							${languageUtil.get(locale, "table-of-contents", "Table of Contents")}
						</span>
						<span class="liferay-nav-item-right-arrow"></span>
					</div>
				</div>
			</#if>

			<ul class="m-0 menu-nav-items-documentation p-2">
				<#list 0..childrenJSONArray.length()-1 as i>
					<li class="learn-article-nav-item">
						<a
							class='liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == childrenJSONArray.getJSONObject(i).url)?then("selected", "")}'
							href="${childrenJSONArray.getJSONObject(i).url}"
						>
							<span>${childrenJSONArray.getJSONObject(i).getString("title")}</span>

							<#if breadcrumbJSONArray.length() lt 2>
								<span class="liferay-nav-item-right-arrow "></span>
							</#if>
						</a>
					</li>
				</#list>
			</ul>
		<#elseif siblingsJSONArray.length() gt 0>
			<ul class="m-0 menu-nav-items-capabilities p-2">
				<#list 0..siblingsJSONArray.length()-1 as i>
					<li class="learn-article-nav-item">
						<a
							class='liferay-nav-item ${(navigationJSONObject.getJSONObject("self").url == siblingsJSONArray.getJSONObject(i).url)?then("selected", "")}'
							href="${siblingsJSONArray.getJSONObject(i).url}"
						>
							<span>${siblingsJSONArray.getJSONObject(i).getString("title")}</span>
						</a>
					</li>
				</#list>
			</ul>
		</#if>
	</div>
</div>

<script>
	const triggers = document.querySelectorAll(
		'.menu-trigger-capabilities, .menu-trigger-documentation'
	);

	triggers.forEach(trigger => {
		trigger.addEventListener('click', event => {
			if (window.innerWidth <= 1024) {
				event.preventDefault();

				const parent = trigger.closest('.learn-article-nav-content');

				const targetMenu = parent ? parent.querySelector('ul') : null;

				trigger.classList.toggle('show');
				if (targetMenu) {
					targetMenu.classList.toggle('show');
				}

				trigger.classList.toggle('liferay-nav-item-border',
				trigger.classList.contains('show'));
			}
		});
	});
</script>

<style>
	.learn-article-nav-item .liferay-nav-item {
		align-items: center;
		display: flex;
		justify-content: space-between;
		width: 100%;
	}

	.learn-article-nav-parent {
		border-bottom: 1px solid #E2E2E4;
		padding: 8px;
	}

	.learn-article-nav-parent span {
		font-weight: 700;
	}

	.learn-article-page-container {
		padding: 0 1.5rem 1.5rem 1.5rem;
	}

	.liferay-nav-item {
		margin-top: 5px;
	}

	.liferay-nav-item-border {
		border-bottom: 1px solid #E2E2E4;
	}

	.liferay-nav-item-right-arrow {
		background-color: currentColor;
		display: inline-block;
		height: 16px;
		mask-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath d='M10.5593 7.32801L6.44914 3.27799C5.60571 2.48472 4.46015 3.56307 5.2847 4.34085L9.00776 8.00043L5.26267 11.7003C4.49477 12.4223 5.67809 13.4821 6.44914 12.757L10.5687 8.69764C11.1006 8.20804 11.1887 7.91057 10.5593 7.32801Z'/%3E%3C/svg%3E");
		mask-repeat: no-repeat;
		mask-size: contain;
		transition: transform 0.3s ease-in-out;
		width: 16px;
	}

	.menu-nav-items-capabilities.show,
	.menu-nav-items-documentation.show {
		display: flex !important;
		flex-direction: column;
	}

	.menu-trigger {
		cursor: pointer;
	}

	.menu-trigger.show .liferay-nav-item-right-arrow {
		transform: rotate(90deg);
	}

	.primary-item-toc {
		align-items: center;
		border-radius: 8px;
		display: flex;
		gap: 12px;
		padding: 8px 12px !important;
		width: 100% !important;
	}

	.primary-item-toc:hover {
		background-color: var(--color-action-neutral-hover-10, #eaecee);
		color: var(--color-action-primary-hover, #0053f0) !important;
		transition: box-shadow 0.1s linear, background-color 0.1s linear;
	}

	.primary-item-toc .liferay-nav-item-right-arrow {
		display: none;
	}

	.primary-item-toc svg {
		margin-top: 0;
	}

	.table-of-contents-documentation {
		display: none;
	}

	.table-of-contents-documentation,
	.menu-trigger-capabilities {
		align-items: center;
		justify-content: space-between;
	}

	.table-of-contents-documentation span {
		font-weight: 700;
	}

	@media (max-width: 1024px) {
		.learn-article {
			padding: 0 !important;
		}

		.learn-article-content-container,
		.learn-article-page-container {
			flex-direction: column !important;
		}

		.learn-article-nav-container {
			max-width: 100% !important;
		}

		.learn-article-nav-content {
			margin-bottom: 40px;
		}

		.learn-article-nav-parent .primary-item-toc svg {
			display: none;
		}

		.learn-article-page-container {
			padding: 0;
		}

		.menu-nav-items-capabilities,
		.menu-nav-items-documentation {
			display: none;
		}

		.menu-trigger-capabilities {
			justify-content: space-between !important;
			padding: 8px;
		}

		.menu-trigger-capabilities .liferay-nav-item-right-arrow {
			display: flex !important;
		}

		.primary-item-toc {
			justify-content: space-between;
		}

		.primary-item-toc:hover {
			background: none;
			color: var(--color-neutral-10, #282934) !important;
		}

		.primary-item-toc .liferay-nav-item-right-arrow {
			display: flex;
		}

		.table-of-contents-documentation {
			display: flex;
			padding: 16px 18px;
		}
	}

	@media (min-width: 1025px) {
		.learn-article-nav-parent .primary-item-toc {
			display: flex;
		}

		.menu-nav-items-capabilities,
		.menu-nav-items-documentation {
			display: flex !important;
			flex-direction: column;
		}

		.menu-trigger-capabilities .liferay-nav-item-right-arrow,
		.menu-trigger-documentation .liferay-nav-item-right-arrow {
			display: none !important;
		}
	}
</style>