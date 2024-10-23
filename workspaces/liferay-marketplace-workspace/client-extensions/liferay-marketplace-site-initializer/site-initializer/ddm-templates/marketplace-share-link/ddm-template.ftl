<style>
	.help-and-support-link-icon {
		color: rgb(133, 140, 148);
	}
</style>

<a class="copy-text ml-1 text-decoration-none font-weight-bold text-primary" href="#copy-share-link" onclick="copyToClipboard(Liferay.ThemeDisplay.getLayoutURL())" style="align-items: center;color: #272833;cursor: pointer;display: flex;font-size: 18px;justify-content: space-between;text-decoration: none;">
	<span class="help-and-support-link-icon mr-1">
		<@clay["icon"] symbol="link" />
	</span>

	Copy &amp; Share

	<svg class="link-arrow" style="margin-left:auto;" width="16" height="16" fill="none" xmlns="http://www.w3.org/2000/svg"><mask id="arrow" style="mask-type:alpha" maskUnits="userSpaceOnUse" x="5" y="4" width="6" height="8"><path d="m6 10.584 2.587-2.587L6 5.41a.664.664 0 1 1 .94-.94L10 7.53c.26.26.26.68 0 .94l-3.06 3.06c-.26.26-.68.26-.94 0a.678.678 0 0 1 0-.946Z" fill="#000"></path></mask><g mask="url(#arrow)"><path fill="#858c94" d="M0 0h16v16H0z"></path></g></svg>
</a>

<script>
	var target = document.querySelector('[href="#copy-share-link"]');

	function copyToClipboard(text) {
		if (navigator && navigator.clipboard && navigator.clipboard.writeText) {
			navigator.clipboard.writeText(text)

			Liferay.Util.openToast({message: "Copied link to the clipboard"})
		};

		if (target) {
			target.popover('show');

			setTimeout(
				function() {
					target.popover('hide')
				},
				1000
			);
		}
	}
</script>