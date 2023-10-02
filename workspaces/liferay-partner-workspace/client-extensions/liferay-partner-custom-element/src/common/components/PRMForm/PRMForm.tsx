/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Checkbox from './components/fields/Checkbox';
import CheckboxGroup from './components/fields/CheckboxGroup';
import DatePicker from './components/fields/DatePicker';
import InputCurrency from './components/fields/InputCurrency';
import InputFile from './components/fields/InputFile';
import InputMultipleFiles from './components/fields/InputMultipleFiles';
import InputText from './components/fields/InputText';
import RadioGroup from './components/fields/RadioGroup';
import Select from './components/fields/Select';
import Footer from './components/layout/Footer';
import Group from './components/layout/Group';
import Section from './components/layout/Section';

interface IProps {
	children?: React.ReactNode;
	description?: string;
	name: string;
	title: string;
}

const PRMForm = ({
	children,
	className,
	description,
	name,
	title,
}: IProps & React.HTMLAttributes<HTMLDivElement>) => (
	<div className="border-0 pb-3 pt-5 px-2 px-lg-5 px-md-4 sheet">
		<div className={className}>
			<div className="font-weight-bold mb-1 text-primary text-small-caps">
				{name}
			</div>

			<h2 className="mb-0">{title}</h2>

			{description && (
				<div className="mt-1 text-paragraph-sm">{description}</div>
			)}
		</div>

		{children}
	</div>
);

PRMForm.Footer = Footer;
PRMForm.Group = Group;
PRMForm.Section = Section;
PRMForm.Checkbox = Checkbox;
PRMForm.CheckboxGroup = CheckboxGroup;
PRMForm.DatePicker = DatePicker;
PRMForm.InputCurrency = InputCurrency;
PRMForm.InputFile = InputFile;
PRMForm.InputMultipleFiles = InputMultipleFiles;
PRMForm.InputText = InputText;
PRMForm.RadioGroup = RadioGroup;
PRMForm.Select = Select;

export default PRMForm;
