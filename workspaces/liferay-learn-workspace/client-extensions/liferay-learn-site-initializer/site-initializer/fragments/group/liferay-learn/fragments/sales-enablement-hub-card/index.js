/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const cardConfigurations = {
	nonTrainer: {
		class: 'sales-resources-card-icon',
		description:
			'Find the latest presentation decks, battle cards, reports, and other essential resources to effectively position and sell Liferay solutions.',
		goToText: 'Go to the Sales Enablement Hub',
		href: '/web/sales-enablement/sales-resources',
		title: 'Sales Resources',
	},
	trainer: {
		class: 'sales-enablement-hub-card-icon',
		description:
			'Access exclusive assets, tools, and materials designed to help our partners and staff succeed in their roles, from sales to certified training.',
		goToText: 'Go to the Enablement Hub',
		href: '/web/sales-enablement/home',
		title: 'Enablement Hub',
	},
};

const getElementByClass = (className) =>
	document.querySelector(`.${className}`);

const elementClassMap = {
	containerEnablementHubCard: 'container-enablement-hub-card',
	salesEnablementHubCardLink: 'sales-enablement-hub-card-link',
	salesPageCard: 'sales-page-card',
	salesPageCardDescription: 'sales-page-card-description',
	salesPageCardGoToText: 'sales-page-card-go-to-text',
	salesPageCardTitle: 'sales-page-card-title',
};

const domElements = Object.fromEntries(
	Object.entries(elementClassMap).map(([key, className]) => [
		key,
		getElementByClass(className),
	])
);

const getUserAccount = async () => {
	const response = await Liferay.Util.fetch(
		`/o/headless-admin-user/v1.0/user-accounts/${Liferay.ThemeDisplay.getUserId()}`
	);

	return response.json();
};

const hasUserGroup = (userAccount, targetGroups = []) => {
	if (!Array.isArray(userAccount?.userGroupBriefs)) {
		return false;
	}

	return userAccount.userGroupBriefs.some((group) =>
		targetGroups.includes(group.name)
	);
};

const renderCardByRole = (isTrainer) => {
	const {salesPageCard} = domElements;

	salesPageCard.classList.remove(
		cardConfigurations.nonTrainer.class,
		cardConfigurations.trainer.class
	);

	const config = isTrainer
		? cardConfigurations.trainer
		: cardConfigurations.nonTrainer;

	salesPageCard.classList.add(config.class);
	setCardInfo(config);
};

const setCardInfo = ({description, goToText, href, title}) => {
	const {
		salesEnablementHubCardLink,
		salesPageCardDescription,
		salesPageCardGoToText,
		salesPageCardTitle,
	} = domElements;

	salesEnablementHubCardLink.href = href;
	salesPageCardDescription.textContent = description;
	salesPageCardGoToText.textContent = goToText;
	salesPageCardTitle.textContent = title;
};

document.addEventListener('DOMContentLoaded', async () => {
	const userAccount = await getUserAccount();

	if (hasUserGroup(userAccount, ['Employees', 'Partners'])) {
		domElements.containerEnablementHubCard.classList.remove('hide');

		renderCardByRole(
			userAccount.roleBriefs?.some((role) =>
				[
					'TRAINERS-LOUNGE-CONTENT-ADMIN',
					'TRAINERS-LOUNGE-USER',
				].includes(role.externalReferenceCode)
			)
		);
	}
});
