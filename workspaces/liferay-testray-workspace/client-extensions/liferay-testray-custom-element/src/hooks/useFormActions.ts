/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

import TestrayError from '../TestrayError';
import i18n from '../i18n';
import {Liferay} from '../services/liferay';

type OnSubmitOptionsRest<T = any> = {
	create: (data: any) => Promise<T>;
	update: (id: number, data: any) => Promise<T>;
};

export type FormOptions = {
	onChange: (
		state: any
	) => (event: React.FormEvent<HTMLInputElement>) => void;
	onClose: () => void;
	onError: (error?: any) => void;
	onSave: (param?: any) => void;
	onSubmit: <T = any>(
		data: any,
		options: OnSubmitOptionsRest<T>
	) => Promise<T>;
	onSubmitAndSave: (
		data: any,
		onSubmitOptions: OnSubmitOptionsRest<any>
	) => Promise<void>;
	onSuccess: () => void;
	submitting: boolean;
};

export type Form = {
	forceRefetch: number;
	form: FormOptions;
};

export type FormComponent = Omit<Form, 'forceRefetch'>;

const onError = (error: any) => {
	let errorMessage = i18n.translate('an-unexpected-error-occurred');

	if (
		['development', 'test'].includes(process.env.NODE_ENV as string) ||
		error instanceof TestrayError
	) {
		console.error(error);

		errorMessage = error.message;
	}

	Liferay.Util.openToast({
		message: errorMessage,
		type: 'danger',
	});
};

const onSuccess = () => {
	Liferay.Util.openToast({
		message: i18n.translate('your-request-completed-successfully'),
		type: 'success',
	});
};

const useFormActions = (): Form => {
	const [forceRefetch, setForceRefetch] = useState(0);
	const [submitting, setSubmitting] = useState(false);
	const navigate = useNavigate();

	const onClose = () => {
		navigate(-1);
	};

	const onSave = () => {
		onSuccess();

		setForceRefetch(new Date().getTime());

		navigate(-1);
	};

	const onSubmit = async <T = any>(
		data: any,
		{create, update}: OnSubmitOptionsRest<T>
	): Promise<T> => {
		setSubmitting(true);

		try {
			const form = {...data};

			delete form.id;

			const fn = data.id
				? () => update(data.id, form)
				: () => create(form);

			const response = await fn();

			setSubmitting(false);

			return response;
		}
		catch (error) {
			setSubmitting(false);

			throw error;
		}
	};

	const onSubmitAndSave = async (
		data: any,
		onSubmitOptions: OnSubmitOptionsRest<any>
	) => {
		await onSubmit(data, onSubmitOptions);
		await onSave();
	};

	return {
		forceRefetch,

		form: {
			onChange: ({form, setForm}: any) => (event: any) => {
				const {
					target: {checked, name, type},
				} = event;

				let {value} = event.target;

				if (type === 'checkbox') {
					value = checked;
				}

				setForm({
					...form,
					[name]: value,
				});
			},
			onClose,
			onError,
			onSave,
			onSubmit,
			onSubmitAndSave,
			onSuccess,
			submitting,
		},
	};
};

export default useFormActions;
