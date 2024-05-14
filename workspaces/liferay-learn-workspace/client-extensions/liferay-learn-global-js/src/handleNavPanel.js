function handleNavPanel() {
	function toggleNavPanel(tabSelector, panelSelector, initialState = false) {
		const tab = document.querySelector(tabSelector);
		const panel = document.getElementById(panelSelector);

		let isPanelOpened = initialState;

		tab.addEventListener('click', () => {
			panel.style.display = isPanelOpened ? 'none' : 'block';
			isPanelOpened = !isPanelOpened;
		});
	}

	toggleNavPanel('#tab1-eive', 'tabPanel1-eive');
	toggleNavPanel('#tab2-eive', 'tabPanel2-eive');
}

document.addEventListener('DOMContentLoaded', handleNavPanel);
