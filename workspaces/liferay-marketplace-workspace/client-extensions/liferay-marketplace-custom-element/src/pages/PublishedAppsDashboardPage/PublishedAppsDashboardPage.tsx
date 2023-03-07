import {DashboardPage} from '../DashBoardPage/DashboardPage'

export function PublishedAppsDashboardPage() {

	const messages = {
		description: 'Manage and publish apps on the Marketplace',
		emptyStateMessage: {
			description1:"Publish apps and they will show up here.",
			description2:"Click on “New App” to start.",
			title:"No apps yet"},
		title: 'Apps',
	}
	
	return (
		<DashboardPage buttonMessage="+ New App" messages={messages}></DashboardPage>
	);
}
