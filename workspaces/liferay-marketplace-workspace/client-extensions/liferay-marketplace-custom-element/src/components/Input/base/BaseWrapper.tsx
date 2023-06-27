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

import ClayForm from "@clayui/form";
import classNames from "classnames";
import { ReactNode } from "react";
import BaseWarning from "./BaseWarning";

type BaseWrapperProps = {
	boldLabel?: boolean;
	children: ReactNode;
	description?: string;
	disabled?: boolean;
	error?: string;
	id?: string;
	label?: string;
	required?: boolean;
};

const BaseWrapper: React.FC<BaseWrapperProps> = ({
	boldLabel = false,
	children,
	description,
	disabled,
	error,
	id,
	label,
	required,
}) => {
	return (
		<ClayForm.Group
			className={classNames({
				"has-error": error,
			})}
		>
			{label && (
				<label
					className={classNames("font-weight-normal mb-1 mx-0 text-paragraph", {
						"font-weight-bold": boldLabel,
						disabled,
						required,
					})}
					htmlFor={id}
				>
					{label}
				</label>
			)}

			{children}

			{description && (
				<small className="form-text text-muted" id="Help">
					{description}
				</small>
			)}

			{error && <BaseWarning>{error}</BaseWarning>}
		</ClayForm.Group>
	);
};

export default BaseWrapper;
