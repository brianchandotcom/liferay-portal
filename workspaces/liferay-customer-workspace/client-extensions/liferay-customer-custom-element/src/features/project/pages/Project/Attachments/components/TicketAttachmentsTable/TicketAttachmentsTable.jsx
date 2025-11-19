/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/core';
import {useState} from 'react';
import i18n from '~/utils/I18n';
import ActionTable from '~/components/ActionTable';
import useJiraTicketURL from '~/hooks/useJiraTicketURL';
import useMyUserAccountByAccountExternalReferenceCode from '~/features/project/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';
import DeleteTicketAttachmentModal from './components/DeleteTicketAttachmentModal/DeleteTicketAttachmentModal';
import TicketAttachmentsTableEmpty from './components/TicketAttachmentsTableEmpty';
import OptionsColumn from './components/columns/OptionsColumn';
import useDelete from './hooks/useDeleteTicketAttachment';
import useDownload from './hooks/useDownloadTicketAttachment';
import getAttachmentFormattedDateTime from './utils/getAttachmentFormattedDateTime';
import {getColumns} from './utils/getColumns';

import './TicketAttachmentsTable.css';
import useGetTicketAttachments from './hooks/useGetTicketAttachments';
import useFilters from './hooks/useFilters';

const TicketAttachmentsTable = ({
	koroneikiAccount,
	loading: koroneikiAccountLoading,
}) => {
	const {
		data: myUserAccountData,
		loading: myUserAccountLoading,
	} = useMyUserAccountByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey,
		koroneikiAccountLoading
	);
	const loggedUserAccount = myUserAccountData?.myUserAccount;
	const {filterQuery, handleSortChange, paginationConfig} = useFilters(koroneikiAccount);
	const {fetchTicketAttachments, loading, ticketAttachments, totalCount} = useGetTicketAttachments(filterQuery, myUserAccountLoading);

	const [selectedTicketAttachment, setSelectedTicketAttachment] = useState();

	const {onDownload} = useDownload();
	const {isDeleting, onDelete} = useDelete(fetchTicketAttachments);
	const {observer, onOpenChange, open} = useModal();
	const siteURL = Liferay.ThemeDisplay.getLayoutURL().split('/project')[0];

	return (
		<>
			{open && (
				<DeleteTicketAttachmentModal
					modalTitle={i18n.translate('delete-attached-file')}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onDelete={() => {
						onDelete(selectedTicketAttachment?.id);
						onOpenChange(false);
						Liferay.Util.openToast({
							message: i18n.translate('was-deleted-successfully'),
							title: selectedTicketAttachment?.fileName,
							type: 'success',
						});
					}}
					removing={isDeleting}
				>
					<p className="my-0 text-neutral-10">
						{i18n.translate(
							'are-you-sure-you-want-to-delete-this-attached-file'
						)}
					</p>

					<p className="font-weight-bold mt-4 text-neutral-10">
						- {selectedTicketAttachment?.fileName}
					</p>
				</DeleteTicketAttachmentModal>
			)}

			{ticketAttachments &&
			totalCount > 0 &&
			!loading ? (
				<div className="cp-ticket-attachments-table-wrapper">
					<ActionTable
						className="border-0"
						columns={getColumns()}
						handleSortChange={handleSortChange}
						hasPagination
						hasSorting
						isLoading={loading}
						paginationConfig={{...paginationConfig, totalCount}}
						rows={ticketAttachments?.map(
							(ticketAttachment) => ({
								attached: (
									<div className="d-flex flex-column">
										<div className="m-0 text-neutral-10 text-truncate">
											{getAttachmentFormattedDateTime(
												ticketAttachment?.dateCreated
											)}
										</div>

										<div className="m-0 text-neutral-7 text-paragraph-sm text-truncate">
											{`${i18n.translate('by')} ${
												ticketAttachment?.creator.name
											}`}
										</div>
									</div>
								),
								fileName: (
									<a
										className="m-0 text-truncate"
										href={`${siteURL}/ticket-attachments/#/id/${ticketAttachment.id}`}
									>
										{ticketAttachment?.fileName}
									</a>
								),
								fileSize: (
									<div className="m-0 text-neutral-10 text-paragraph text-truncate">
										{ticketAttachment?.fileSize}
									</div>
								),
								options: (
									<OptionsColumn
										hasDeletePermissions={
											loggedUserAccount
												?.selectedAccountSummary
												.hasAdministratorRole ||
											loggedUserAccount?.id ===
												ticketAttachment.creator.id
										}
										onDelete={onDelete}
										onDownload={onDownload}
										onOpenChange={onOpenChange}
										setSelectedTicketAttachment={
											setSelectedTicketAttachment
										}
										ticketAttachment={{...ticketAttachment, downloadUrl: `${siteURL}/ticket-attachments/#/id/${ticketAttachment.id}`}}
									/>
								),
								ticket: (
									<a
										className="m-0 text-truncate"
										href={`${useJiraTicketURL(ticketAttachment?.jiraIssueKey)}`}
									>
										{'#' + ticketAttachment?.jiraIssueKey}
									</a>
								),
							})
						)}
					/>
				</div>
			) : (
				!ticketAttachments ||
				(totalCount === 0 && !loading && (
					<TicketAttachmentsTableEmpty
						description={i18n.translate(
							'there-are-no-items-to-display'
						)}
						title={i18n.translate('no-attachments-yet')}
					/>
				))
			)}
		</>
	);
};

export default TicketAttachmentsTable;
