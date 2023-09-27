/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Header} from '../../components/Header/Header';
import {Section} from '../../components/Section/Section';

import './SetAppPrivacyPage.scss';

import {useState} from 'react';

import {
	BaseCheckboxDataCard,
	CheckboxDataCard,
} from '../../components/CheckboxDataCard/CheckboxDataCard';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {initialCheckboxDataCardsValues} from './SetAppPrivacyUtil';

interface SetAppPrivacyPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function SetAppPrivacyPage({
	onClickBack,
	onClickContinue,
}: SetAppPrivacyPageProps) {

	// const [collectUsersData, setCollectUsersData] = useState(false);

	const [checkboxDataCardsValues, setCheckboxDataCardsValues] = useState(
		initialCheckboxDataCardsValues
	);

	const handleSelectCheckbox = (
		cardName: string,
		selectedCheckboxName: string
	) => {
		const newCheckboxDataCardsValues = checkboxDataCardsValues.map(
			(dataCard) => {
				const {checkboxItems, name} = dataCard;
				if (name === cardName) {
					const newCheckboxItems = checkboxItems.map(
						(checkboxItem) => {
							if (checkboxItem.name === selectedCheckboxName) {
								return {
									...checkboxItem,
									checked: !checkboxItem.checked,
								};
							}

							return checkboxItem;
						}
					);

					return {
						...dataCard,
						checkboxItems: newCheckboxItems,
					};
				}

				return dataCard;
			}
		);

		setCheckboxDataCardsValues(
			newCheckboxDataCardsValues as BaseCheckboxDataCard[]
		);
	};

	return (
		<div className="set-app-privacy-page-container">
			<Header
				description="This app will be used to collect users data."
				title="Collect users data"
			/>

			<Section
				label="App Privacy"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				{/* <RadioCard 
                    toggle
                    description='This app will be used to collect users data.'
                    title='Collect users data'
                    tooltip='More Info'
                    selected={collectUsersData === true ? 'collectUsersData' : ""}
                    icon={analyticsIcon}
                    setSelected={() => setCollectUsersData(!collectUsersData)}
                />
                <span className='set-app-privacy-page-text'>
                    By collecting users data or not, you agree to the <a href="#">Content Policy.</a> Liferay's  <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a> apply to your use of this service
                </span> */}
			</Section>

			{
				/* collectUsersData && */ <Section
					label="Select all data this publisher will collect from this app"
					required
					tooltip="More Info"
					tooltipText="More Info"
				>
					{checkboxDataCardsValues.map(
						({
							checkboxItems,
							icon,
							name,
							title,
							tooltip,
							tooltipText,
						}) => (
							<CheckboxDataCard
								checkboxItems={checkboxItems}
								icon={icon}
								key={name}
								name={name}
								onChange={handleSelectCheckbox}
								title={title}
								tooltip={tooltip}
								tooltipText={tooltipText}
							/>
						)
					)}
				</Section>
			}

			<NewAppPageFooterButtons
				onClickBack={() => onClickBack()}
				onClickContinue={() => onClickContinue()}
			/>
		</div>
	);
}
