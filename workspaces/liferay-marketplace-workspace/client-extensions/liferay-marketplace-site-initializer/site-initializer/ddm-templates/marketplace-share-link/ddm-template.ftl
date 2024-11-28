<style>
	.copy-text {
		color: #272833;
		cursor: pointer;
		font-size: 18px;
	}

	.help-and-support-link-icon {
		color: rgb(133, 140, 148);
	}
</style>

<a class="align-items-center copy-text d-flex font-weight-bold justify-content-between ml-1 text-decoration-none text-primary" href="#copy-share-link" onclick="copyToClipboard(Liferay.ThemeDisplay.getCanonicalURL())">
	<span class="help-and-support-link-icon mr-1">
		<@clay["icon"] symbol="link" />
	</span>

	Copy &amp; Share

	<svg class="link-arrow" fill="none" height="16" style="margin-left: auto;" width="16" xmlns="http://www.w3.org/2000/svg">
		<mask height="8" id="arrow" maskUnits="userSpaceOnUse" style="mask-type:alpha" width="6" x="5" y="4">
			<path d="m6 10.584 2.587-2.587L6 5.41a.664.664 0 1 1 .94-.94L10 7.53c.26.26.26.68 0 .94l-3.06 3.06c-.26.26-.68.26-.94 0a.678.678 0 0 1 0-.946Z" fill="#000"></path>
		</mask>

		<g mask="url(#arrow)">
			<path d="M0 0h16v16H0z" fill="#858c94"></path>
		</g>
	</svg>
</a>

<script>
	function copyToClipboard(text) {
		if (navigator && navigator.clipboard && navigator.clipboard.writeText) {
			navigator.clipboard.writeText(text)

			Liferay.Util.openToast({message: "Copied link to the clipboard"})
		};
	}
</script>