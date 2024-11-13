/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {Fragment, useMemo, useState} from 'react';
import i18n from '~/common/I18n';
import getKebabCase from '~/common/utils/getKebabCase';
import {useOnboarding} from '~/routes/onboarding/context';
import {useCustomerPortal} from '../../context';
import RadioRoles from '../RadioRoles';

const RoleSelectorDropdown = ({
	isTeamMemberInviteForm,
	onClick,
	radioOptions,
	selectOnChange,
	selectedAccountRoleName,
	setRadioOptions,
	setRoleSelectorFilled,
	setSelectedAccountRoleName,
}) => {
	const [atLeastOneFieldIsFilled, setAtLeastOneFieldIsFilled] =
		useState(false);
	const [active, setActive] = useState(false);

	const projectPortal = useCustomerPortal();
	const projectOnboarding = useOnboarding();

	const project = useMemo(
		() => projectPortal?.[0].project || projectOnboarding?.[0].project,
		[projectOnboarding, projectPortal]
	);

	const isPartnerProject = project?.partner;

	const handleOnClick = (accountRoleItems) => {
		const isPartnerMember = accountRoleItems.partnerMemberRoles.active;

		if (isPartnerMember) {
			const memberRoles = accountRoleItems.partnerMemberRoles.roles;
			const updatedMemberRoles = memberRoles.filter(
				(role) => role.active
			);
			const roleLabelsList = updatedMemberRoles.map((role) => role.label);

			if (!isTeamMemberInviteForm) {
				onClick(updatedMemberRoles);
			}

			setSelectedAccountRoleName(roleLabelsList);
		}

		if (!isPartnerMember) {
			const accountRoleItem = Object.values(accountRoleItems).filter(
				(role) => role.active
			);

			if (accountRoleItem[0].label !== selectedAccountRoleName[0]) {
				if (!isTeamMemberInviteForm) {
					onClick(accountRoleItem[0]);
				}

				setSelectedAccountRoleName([accountRoleItem[0].label]);
			}
		}
	};

	const atLeastOnePartnerMemberSelected = useMemo(() => {
		if (radioOptions.partnerMemberRoles?.active) {
			if (
				radioOptions.partnerMemberRoles?.roles.some(
					({active}) => active
				)
			) {
				return true;
			}
		}

		if (!radioOptions.partnerMemberRoles?.active) {
			return true;
		}

		return false;
	}, [radioOptions]);

	return (
		<DropDown
			active={active}
			closeOnClickOutside
			menuWidth="shrink"
			onActiveChange={setActive}
			trigger={
				<Button
					aria-label={selectedAccountRoleName[0]}
					className="align-items-center bg-white d-flex justify-content-between w-100"
					displayType="secondary"
					outline
					small
				>
					<div className="text-truncate">
						{i18n.translate(
							getKebabCase(selectedAccountRoleName[0])
						)
							? i18n.translate(
									getKebabCase(selectedAccountRoleName[0])
								)
							: selectedAccountRoleName[0]}
					</div>

					<span className="inline-item inline-item-after mt-1">
						<ClayIcon symbol="caret-bottom" />
					</span>
				</Button>
			}
		>
			{Object.keys(radioOptions).map((key, index) => {
				const accountRole = radioOptions[key];

				return (
					<Fragment key={index}>
						{key === 'partnerMemberRoles' ? (
							<>
								{isPartnerProject && (
									<RadioRoles
										className="pr-6"
										key={index}
										onChange={() => {
											const newObject = {...radioOptions};

											Object.keys(radioOptions).forEach(
												(roleLabel) => {
													newObject[
														roleLabel
													].active =
														roleLabel === key;
												}
											);

											newObject.partnerMemberRoles.roles = newObject.partnerMemberRoles.roles.map(
												(role) => ({
													...role,
													active: false,
												})
											);

											setRadioOptions(newObject);
											setAtLeastOneFieldIsFilled(false);
										}}
										selected={accountRole.active}
									>
										{i18n.translate('partner-member')}
									</RadioRoles>
								)}

								{accountRole.roles.map(
									(role, accountRoleIndex) => (
										<ClayCheckbox
											checked={role.active}
											className="pr-6"
											disabled={
												role.disabled ||
												!radioOptions.partnerMemberRoles
													.active
											}
											key={accountRoleIndex}
											onChange={() => {
												const newObject = {
													...radioOptions,
												};

												const partnerMemberRole =
													newObject.partnerMemberRoles
														.roles[
														accountRoleIndex
													];

												partnerMemberRole.active =
													!partnerMemberRole.active;

												if (partnerMemberRole.active) {
													Object.keys(
														newObject
													).forEach((key) => {
														if (
															key !==
															'partnerMemberRoles'
														) {
															newObject[
																key
															].active = false;
														}
													});
												}

												setRadioOptions(newObject);

												const activeMemberRoles = (
													role
												) => role.active;
												const atLeastOneMemberIsFilled =
													radioOptions.partnerMemberRoles.roles.some(
														activeMemberRoles
													);

												setAtLeastOneFieldIsFilled(
													atLeastOneMemberIsFilled
												);
											}}
										>
											{i18n.translate(
												getKebabCase(role.label)
											)
												? i18n.translate(
														getKebabCase(role.label)
													)
												: role.label}
										</ClayCheckbox>
									)
								)}
							</>
						) : (
							<RadioRoles
								className="pr-6"
								disabled={accountRole.disabled}
								onChange={() => {
									const newObject = {...radioOptions};

									Object.keys(radioOptions).forEach(
										(roleLabel) => {
											newObject[roleLabel].active =
												roleLabel === key;
										}
									);

									newObject.partnerMemberRoles.roles =
										newObject.partnerMemberRoles.roles.map(
											(role) => ({...role, active: false})
										);

									setRadioOptions(newObject);

									const accountRoleActiveItem = Object.values(
										newObject
									).filter((role) => role.active);

									if (
										selectedAccountRoleName.includes(
											accountRoleActiveItem[0].label
										)
									) {
										setAtLeastOneFieldIsFilled(false);
									}
									else {
										setAtLeastOneFieldIsFilled(true);
									}
								}}
								selected={
									accountRole.active && accountRole.label
								}
							>
								{i18n.translate(getKebabCase(accountRole.label))
									? i18n.translate(
											getKebabCase(accountRole.label)
										)
									: accountRole.label}
							</RadioRoles>
						)}
					</Fragment>
				);
			})}

			<ClayTooltipProvider>
				<Button
					aria-label={i18n.translate('apply')}
					className="btn btn-sm px-2 py-2 w-100"
					data-tooltip-align="right"
					disabled={!atLeastOneFieldIsFilled}
					onClick={() => {
						if (isTeamMemberInviteForm) {
							selectOnChange(radioOptions);
							setRoleSelectorFilled(true);
						}

						handleOnClick(radioOptions);
						setActive(false);
					}}
					title={
						!atLeastOnePartnerMemberSelected
							? i18n.translate(
									'partner-members-must-have-at-least-one-role-assigned'
								)
							: undefined
					}
				>
					{i18n.translate('apply')}
				</Button>
			</ClayTooltipProvider>
		</DropDown>
	);
};

export default RoleSelectorDropdown;
