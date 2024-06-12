/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {useFormikContext} from 'formik';

import PRMForm from '../../../common/components/PRMForm';
import PRMFormik from '../../../common/components/PRMFormik';
import PRMFormikPageProps from '../../../common/components/PRMFormik/interfaces/prmFormikPageProps';
import MDFClaim from '../../../common/interfaces/mdfClaim';

interface IProps {
	currencyExchangeRate?: number;
	onClose: () => void;
}

export default function ClaimPaidModalContent({
	currencyExchangeRate,
	onClose,
}: PRMFormikPageProps & IProps) {
	const {dirty, isSubmitting, isValid, setFieldValue} =
		useFormikContext<MDFClaim>();

	return (
		<PRMForm name="MDF Claim" title="Status Change">
			<PRMFormik.Field
				component={PRMForm.InputCurrency}
				label="Amount Paid"
				name="claimPaid"
				onAccept={(value: number) => {
					setFieldValue(
						`convertedClaimPaid`,
						currencyExchangeRate && value / currencyExchangeRate
					);
					setFieldValue(`claimPaid`, value);
				}}
				required
			/>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Check Number"
				name="checkNumber"
				required
			/>
			<PRMFormik.Field
				component={PRMForm.DatePicker}
				label="Payment Date"
				name="paymentDate"
				required
			/>

			<PRMForm.Footer>
				<div className="d-flex justify-content-between mr-auto">
					<Button
						className="mr-4"
						displayType="secondary"
						onClick={onClose}
					>
						Cancel
					</Button>
				</div>

				<div className="d-flex justify-content-between px-2 px-md-0">
					<Button
						disabled={isSubmitting || !isValid || !dirty}
						type="submit"
					>
						Submit
					</Button>
				</div>
			</PRMForm.Footer>
		</PRMForm>
	);
}
