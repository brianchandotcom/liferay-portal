function handleRecipePage() {
	function formatDate() {
		const dateString = document
			.querySelector('.publish-date .component-html')
			.textContent.replace(/\s/g, '');
		const date = new Date(dateString);

		const year = date.getFullYear();
		const monthIndex = date.getMonth();
		const months = [
			'Jan',
			'Feb',
			'Mar',
			'Apr',
			'May',
			'Jun',
			'Jul',
			'Aug',
			'Sep',
			'Oct',
			'Nov',
			'Dec',
		];
		const monthName = months[monthIndex];
		const day = date.getDate();

		const formattedDate = `${monthName}. ${day} ${year}`;

		document.querySelector(
			'.publish-date .component-html'
		).textContent = `Published ${formattedDate}`;
	}

	formatDate();

	const getTimeToComplete = document
		.querySelector('.sidemenu-time .component-html')
		.textContent.replace(/\s/g, '');

	document.querySelector(
		'.sidemenu-time .component-html'
	).textContent = `${getTimeToComplete} minutes`;
}

document.addEventListener('DOMContentLoaded', handleRecipePage);
