/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import i18n from '../../../../../../../common/I18n';
import Button from '../../../../../../../common/components/Button';
import {ALERT_DOWNLOAD_TYPE} from '../../../../../utils/constants';

const DeactivateKeysModal = ({
	deactivateKeysConfirm,
	deactivateKeysStatus,
	isDeactivating,
	observer,
	onClose,
}) => {
	return (
		<ClayModal center observer={observer}>
			<div className="pt-4 px-4">
				<div className="flex-row mb-1">
					<div className="d-flex justify-content-between">
						<h2 className="text-neutral-10">
							{i18n.translate('confirm-deactivation-terms')}
						</h2>

						<Button
							appendIcon="times"
							aria-label="close"
							className="align-self-start"
							displayType="unstyled"
							onClick={onClose}
						/>
					</div>

					<p className="mb-6 mt-5 text-neutral-10">
						{i18n.translate(
							'i-certify-that-the-instance-s-activated-with-the-selected-activation-keys-have-been-shut-down-and-that-there-is-no-liferay-software-installed-deployed-used-or-executed-that-is-activated-with-the-selected-activation-keys'
						)}
					</p>
				</div>

				<div className="d-flex justify-content-end my-4">
					<Button displayType="secondary" onClick={onClose}>
						{i18n.translate('cancel')}
					</Button>

					<Button
						className={classNames('bg-danger d-flex ml-2', {
							'cp-deactivate-loading': isDeactivating,
						})}
						onClick={deactivateKeysConfirm}
					>
						{isDeactivating ? (
							<>
								<span className="cp-spinner mr-2 mt-1 spinner-border spinner-border-sm"></span>
								{i18n.translate('deactivating')}...
							</>
						) : (
							i18n.translate('confirm-deactivate-keys')
						)}
					</Button>
				</div>
			</div>

			{!isDeactivating &&
				deactivateKeysStatus === ALERT_DOWNLOAD_TYPE.danger && (
					<div className="allign cp-error-alert d-flex px-4 py-3">
						<ClayIcon
							className="mr-2 mt-1 text-danger"
							symbol="info-circle"
						/>

						<p className="m-0 text-danger text-paragraph">
							{i18n.translate(
								'there-was-an-unexpected-error-while-attempting-to-deactivate-keys-please-try-again-in-a-few-moments'
							)}
						</p>
					</div>
				)}
		</ClayModal>
	);
};

export default DeactivateKeysModal;
