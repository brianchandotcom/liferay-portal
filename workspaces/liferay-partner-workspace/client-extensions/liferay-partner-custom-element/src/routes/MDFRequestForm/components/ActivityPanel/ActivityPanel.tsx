/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import {useFormikContext} from 'formik';

import MDFRequest from '../../../../common/interfaces/mdfRequest';
import MDFRequestActivity from '../../../../common/interfaces/mdfRequestActivity';
import getIntlNumberFormat from '../../../../common/utils/getIntlNumberFormat';

interface IProps {
	activity: MDFRequestActivity;
	children?: React.ReactNode;
	detail?: boolean;
	hasErrors?: boolean;
	onEdit?: () => void;
	onRemove?: () => void;
	overallCampaignName: string;
}

const ActivityPanel = ({
	activity,
	children,
	detail,
	hasErrors,
	onEdit,
	onRemove,
	overallCampaignName,
}: IProps) => {
	const {values} = useFormikContext<MDFRequest>();

	return (
		<ClayPanel
			className={classNames({
				'border-brand-primary-lighten-4': !hasErrors,
				'border-danger': hasErrors,
			})}
			collapsable={detail}
			displayTitle={
				<ClayPanel.Title
					className={classNames('text-dark', {
						'p-4': !detail,
						'py-2': detail,
					})}
				>
					<div className="d-flex justify-content-between">
						<div className="text-truncate">
							<div className="mb-1 text-neutral-7 text-paragraph-sm">
								{overallCampaignName}
							</div>

							<h5 className="mb-1 text-truncate">
								{activity.name}
							</h5>
						</div>

						<div className="ml-5">
							{!detail && (
								<div className="d-flex">
									<ClayButtonWithIcon
										displayType={null}
										onClick={onEdit}
										small
										symbol="pencil"
									/>

									<ClayButtonWithIcon
										displayType={null}
										onClick={onRemove}
										small
										symbol="trash"
									/>
								</div>
							)}
						</div>
					</div>

					<div className="align-items-center d-flex justify-content-between">
						<div className="font-weight-semi-bold text-neutral-7 text-paragraph-sm">
							MDF Requested:
						</div>

						<h5 className="mr-2">
							{getIntlNumberFormat(values.currency).format(
								activity.mdfRequestAmount
							)}
						</h5>
					</div>
				</ClayPanel.Title>
			}
			displayType="secondary"
			showCollapseIcon={detail}
		>
			{detail && <ClayPanel.Body>{children}</ClayPanel.Body>}
		</ClayPanel>
	);
};

export default ActivityPanel;
