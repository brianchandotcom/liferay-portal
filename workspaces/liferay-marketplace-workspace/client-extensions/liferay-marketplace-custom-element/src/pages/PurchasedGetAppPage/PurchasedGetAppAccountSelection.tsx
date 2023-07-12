/* eslint-disable @typescript-eslint/no-unused-vars */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from "@clayui/button";
import ClayForm, { ClayInput, ClayRadio, ClayRadioGroup } from "@clayui/form";
import ClayIcon from "@clayui/icon";
import ClayLink from "@clayui/link";
import { InputHTMLAttributes, useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

import { Header } from "../../components/Header/Header";
import BaseWrapper from "../../components/Input/base/BaseWrapper";
import zodSchema, { zodResolver } from "../../schema/zod";
import {
	getListTypeDefinitionByExternalReferenceCode,
	getUserAccount,
} from "../../utils/api";

import "./PurchasedGetAppPage.scss";

import ClaySticker from "@clayui/sticker";
import classNames from "classnames";

import { Radio } from "../../components/Input/Radio";
import fetcher from "../../services/fetcher";
import { getPhones } from "./PurchasedGetAppPageUtil";

type Steps = {
	page: "accountCreation" | "accountSelection" | "projectCreated";
};

type UserForm = z.infer<typeof zodSchema.accountCreator>;

type InputProps = {
	boldLabel?: boolean;
	className?: string;
	description?: string;
	disabled?: boolean;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	options?: { label: string; value: string } | [];
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const { origin } = window.location;

type PurchasedGetAppAccountSelectionProps = {
	setStep: React.Dispatch<Steps>;
};

const PurchasedGetAppAccountSelection: React.FC<
	PurchasedGetAppAccountSelectionProps
> = ({ setStep }) => {
	const [acounts, setAccounts] = useState<any[]>();
	const [radio, setRadio] = useState<any>();

	useEffect(() => {
		(async () => {
			await fetcher(
				"http://localhost:18080/o/headless-commerce-admin-account/v1.0/accounts",
			).then((response) => setAccounts(response?.items));
		})();
	}, []);

	const userEmail = "mauren.hall@acme.com";

	return (
		<div className="align-items-center d-flex flex-column justify-content-center purchased-get-app-page-container">
			<div className="border p-8 purchased-get-app-page-body rounded">
				<span className="d-flex justify-content-center">
					<Header description title="Account Selection" />
				</span>

				<div className="mb-4">
					<span>{`Accounts available for ${userEmail} (you)`}</span>
				</div>

				<ClayForm>
					<ClayForm.Group>
						<div className="d-flex justify-content-between">
							<div className="form-group mb-0 pr-3 w-100">
								{acounts?.map((account, index) => (
									<div
										className={classNames(
											"align-items-center d-flex form-control justify-content-between mb-5 cursor-pointer",
											{
												testchecked: radio === index,
											},
										)}
										key={index}
										onClick={() => setRadio(index)}
									>
										<span className="align-items-center d-flex p-2">
											<ClaySticker shape="circle" size="lg">
												<ClaySticker.Image
													alt="placeholder"
													src={account.logoURL}
												/>
											</ClaySticker>

											<h5 className="mb-0 ml-3">{account.name}</h5>
										</span>

										<div className="pr-4">
											<ClayRadio
												checked={radio === index}
												className="mr-5"
												type="radio"
												value="um"
											/>
										</div>
									</div>
								))}
							</div>
						</div>

						<div>
							<span>Not seeing a specific Account? </span>

							<ClayLink>Account? Contact Support</ClayLink>
						</div>

						<div className="mt-6 purchased-get-app-page-button-container">
							<div className="align-items-center d-flex justify-content-between w-100">
								<div>
									<ClayButton
										className="font-weight-bold"
										displayType="unstyled"
										onClick={() => {
											setStep({ page: "accountCreation" });
										}}
									>
										Cancel
									</ClayButton>
								</div>

								<ClayButton
									disabled={false}
									onClick={() => setStep({ page: "projectCreated" })}
								>
									Continue
								</ClayButton>
							</div>
						</div>
					</ClayForm.Group>
				</ClayForm>
			</div>
		</div>
	);
};

export default PurchasedGetAppAccountSelection;
