import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';

import magnifyingGlass from '../../assets/images/magnifying-glass.svg';
import menu from '../../assets/images/menu.svg';
import {GateCard} from '../../components/Card/GateCard';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';

import './CustomerGatePage.scss';

export function CustomerGatePage() {
	const {origin} = window.location;

	return (
		<div className="customer-gate-page-container">
			<div className="customer-gate-page-body">
				<Header
					description="We are happy to have you interested in the Liferay Marketplace. At the moment, we are working on enhancing the experience for our customers in the Marketplace and access is invite only. If you are an existing Liferay customer, please keep an eye out for an announcement related to the new Marketplace in the coming months!"
					title="Becoming a Liferay Marketplace Customer"
				/>

				<GateCard
					description="Explore over 800 apps available in the Liferay Marketplace from a variety of publishers. Apps allow you to accelerate your Liferay development get to market faster. "
					image={{
						description: 'Magnifying Glass',
						svg: magnifyingGlass,
					}}
					title="Discover and customize "
				/>

				<GateCard
					description="Manage all your app purchases and subscriptions in one place, read other users reviews, get notifications when updates are available and get the most out of our Apps catalog."
					image={{
						description: 'Menu ',
						svg: menu,
					}}
					title="Manage All Your Apps in One Place"
				/>

				<hr className="customer-gate-page-divider" />

				<div className="customer-gate-page-button-container">
					<ClayButton
						className="customer-gate-page-button"
						onClick={() => {
							window.location.href =
								'http://marketplace.liferay.com/';
						}}
					>
						Go Back to Marketplace
					</ClayButton>

					<div>
						<span className="customer-gate-page-text">
							Already a Liferay Experience Cloud customer? &nbsp;
						</span>

						<ClayLink
							className="customer-gate-page-link"
							href={`${origin}/c/portal/login`}
						>
							Sign In
						</ClayLink>
					</div>
				</div>
			</div>

			<Footer />
		</div>
	);
}
