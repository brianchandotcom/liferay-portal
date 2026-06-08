/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {FieldArray, FormikTouched, FormikValues} from 'formik';
import React, {useRef} from 'react';
import {connect} from 'react-redux';
import PreferenceMutation from '~/settings/data-privacy/queries/PreferenceMutation';
import {addAlert} from '~/shared/actions/alerts';
import {close, modalTypes, open} from '~/shared/actions/modals';
import Card from '~/shared/components/Card';
import Loading, {Align} from '~/shared/components/Loading';
import Form, {
	validateMaxLength,
	validateRequired,
} from '~/shared/components/form';
import {compose, withHistory} from '~/shared/hoc';
import {WrapSafeResults} from '~/shared/hoc/util';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import PreferenceQuery from '~/shared/queries/PreferenceQuery';
import {Alert, Modal} from '~/shared/types';
import {sequence} from '~/shared/util/promise';
import {Routes, toRoute} from '~/shared/util/router';

const QUERY_STRING_SIZE_LIMIT = 512;
const SEARCH_QUERY_STRINGS_KEY = 'search-query-strings';

interface ISearchCardProps {
	addAlert: Alert.AddAlert;
	close: Modal.close;
	groupId: string;
	history: {
		push: (path: string) => void;
	};
	open: Modal.open;
}

const renderAddButton = (
	authorized: boolean,
	currentLength: number,
	props: React.ButtonHTMLAttributes<HTMLButtonElement>,
	index: number | null = null
) => {
	if (
		(!currentLength && authorized) ||
		(index === currentLength - 1 && currentLength <= 4 && authorized)
	) {
		return (
			<ClayButton
				aria-label={Liferay.Language.get('add')}
				borderless
				className="button-root ml-1"
				displayType="secondary"
				{...props}
			>
				<ClayIcon className="icon-root" symbol="plus" />
			</ClayButton>
		);
	}

	return null;
};

const removeSpecialCharacters = (originalValue: string): string =>
	originalValue.split('=')[0].replace(/[^\w\s]/gi, '');

export const SearchCard = function SearchCard({
	addAlert,
	close,
	groupId,
	history,
	open,
}: ISearchCardProps) {
	const currentUser = useCurrentUser();
	const {
		data: searchQueryStringsData,
		error,
		loading,
	} = useQuery(PreferenceQuery, {
		fetchPolicy: 'no-cache',
		variables: {key: SEARCH_QUERY_STRINGS_KEY},
	});

	const [updatePreference] = useMutation(PreferenceMutation);

	const _formRef = useRef<any>(null);

	const authorized = currentUser.isAdmin();

	const getQueryStringListInitialValue = (): Array<string> =>
		searchQueryStringsData && searchQueryStringsData.preference.value
			? JSON.parse(searchQueryStringsData.preference.value)
			: [];

	const handleSubmit = ({
		queryStringList,
	}: {
		queryStringList: string[];
	}): void => {
		const currentForm = _formRef.current;

		const searchQueryStrings = queryStringList.map(removeSpecialCharacters);

		updatePreference({
			variables: {
				key: SEARCH_QUERY_STRINGS_KEY,
				value: JSON.stringify(searchQueryStrings),
			},
		})
			.then(() => {
				addAlert({
					alertType: Alert.Types.Success,
					message: Liferay.Language.get(
						'search-query-definition-has-been-saved'
					),
				});

				history.push(toRoute(Routes.SETTINGS_DEFINITIONS, {groupId}));
			})
			.catch(() => {
				currentForm.setSubmitting(false);

				addAlert({
					alertType: Alert.Types.Error,
					message: Liferay.Language.get('error'),
				});
			});
	};

	const handleCancel = (touchedFields: FormikTouched<FormikValues>): void => {
		Object.keys(touchedFields).length
			? open(modalTypes.CONFIRMATION_MODAL, {
					cancelMessage: Liferay.Language.get('cancel'),
					message: Liferay.Language.get(
						'edits-made-to-search-queries-have-not-been-saved-do-you-want-to-exit-without-saving'
					),
					modalVariant: 'modal-warning',
					onClose: close,
					onSubmit: () => {
						history.push(
							toRoute(Routes.SETTINGS_DEFINITIONS, {groupId})
						);
					},
					submitButtonDisplay: 'warning',
					submitMessage: Liferay.Language.get('exit'),
					title: Liferay.Language.get('exit-without-saving'),
					titleIcon: 'warning-full',
				})
			: history.push(toRoute(Routes.SETTINGS_DEFINITIONS, {groupId}));
	};

	const handleBlur = (
		fieldIdentifier: string,
		fieldValue: string,
		setFieldValue: Function,
		setFieldTouched: Function
	): void => {
		setFieldValue(fieldIdentifier, removeSpecialCharacters(fieldValue));
		setFieldTouched(fieldIdentifier, true);
	};

	return (
		<Card className="query-card-root">
			<Card.Header className="mb-1">
				<Card.Title>{Liferay.Language.get('query-string')}</Card.Title>
			</Card.Header>

			<Card.Body>
				<WrapSafeResults
					className="flex-grow-1"
					error={error}
					errorProps={{
						className: 'flex-grow-1',
					}}
					loading={loading}
					page={false}
					pageDisplay={false}
				>
					<Form
						initialValues={{
							queryStringList: getQueryStringListInitialValue(),
						}}
						innerRef={_formRef as any}
						onSubmit={handleSubmit}
					>
						{({
							handleSubmit,
							isSubmitting,
							isValid,
							setFieldTouched,
							setFieldValue,
							touched,
							values,
						}) => (
							<Form.Form onSubmit={handleSubmit}>
								<FieldArray
									name="queryStringList"
									render={(arrayHelpers) => (
										<>
											<div className="form-inline mb-3">
												<Form.Input
													className="query-input"
													disabled
													name="defaultQueryString"
													value="q"
												/>

												{renderAddButton(
													authorized,
													values.queryStringList
														.length,
													{
														disabled: isSubmitting,
														onClick: () =>
															arrayHelpers.push(
																''
															),
													}
												)}
											</div>

											{values.queryStringList.map(
												(queryString, index) => (
													<div
														className="form-inline mb-3"
														key={index}
													>
														<Form.Input
															className="query-input"
															disabled={
																!authorized
															}
															name={`queryStringList.${index}`}
															onBlur={() =>
																handleBlur(
																	`queryStringList.${index}`,
																	queryString,
																	setFieldValue,
																	setFieldTouched
																)
															}
															validate={sequence([
																validateRequired,
																validateMaxLength(
																	QUERY_STRING_SIZE_LIMIT
																),
															])}
														/>
														{authorized && (
															<ClayButton
																aria-label={Liferay.Language.get(
																	'delete'
																)}
																borderless
																className="button-root ml-1"
																disabled={
																	isSubmitting
																}
																displayType="secondary"
																onClick={() =>
																	arrayHelpers.remove(
																		index
																	)
																}
															>
																<ClayIcon
																	className="icon-root"
																	symbol="trash"
																/>
															</ClayButton>
														)}

														{renderAddButton(
															authorized,
															values
																.queryStringList
																.length,
															{
																disabled:
																	isSubmitting,
																onClick: () =>
																	arrayHelpers.push(
																		''
																	),
															},
															index
														)}
													</div>
												)
											)}
										</>
									)}
								/>

								{authorized && (
									<div className="mt-4">
										<ClayButton
											className="button-root"
											disabled={!isValid}
											displayType="primary"
											type="submit"
										>
											{isSubmitting && (
												<Loading align={Align.Left} />
											)}

											{Liferay.Language.get('save')}
										</ClayButton>

										<ClayButton
											className="button-root ml-4"
											displayType="secondary"
											onClick={() =>
												handleCancel(touched)
											}
										>
											{Liferay.Language.get('cancel')}
										</ClayButton>
									</div>
								)}
							</Form.Form>
						)}
					</Form>
				</WrapSafeResults>
			</Card.Body>
		</Card>
	);
};

export default compose<any>(
	withHistory,
	connect(null, {addAlert, close, open})
)(SearchCard);
