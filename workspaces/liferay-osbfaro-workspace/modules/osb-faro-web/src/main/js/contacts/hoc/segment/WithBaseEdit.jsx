/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import {addAlert} from '~/shared/actions/alerts';
import {close, modalTypes, open} from '~/shared/actions/modals';
import * as API from '~/shared/api';
import Label from '~/shared/components/Label';
import BasePage from '~/shared/components/base-page';
import {ChannelContext} from '~/shared/context/channel';
import {Alert} from '~/shared/types';
import * as breadcrumbs from '~/shared/util/breadcrumbs';
import {SegmentTypes} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import omitDefinedProps from '~/shared/util/omitDefinedProps';
import {Segment} from '~/shared/util/records';
import {Routes, SEGMENTS, toRoute} from '~/shared/util/router';

const MessageKeys = {
	ExternalReferenceCodeIsAlreadyUsed:
		'external-reference-code-is-already-used',
	NameCannotBeBlank: 'name-cannot-be-blank',
	NameIsAlreadyUsed: 'name-is-already-used',
};

const ERRORS = {
	[MessageKeys.ExternalReferenceCodeIsAlreadyUsed]: {
		alertType: Alert.Types.Warning,
		message: Liferay.Language.get(
			'this-segment-erc-is-currently-in-use.-please-try-a-different-one'
		),
	},
	[MessageKeys.NameCannotBeBlank]: {
		alertType: Alert.Types.Error,
		message: Liferay.Language.get('name-cannot-be-blank'),
	},

	[MessageKeys.NameIsAlreadyUsed]: {
		alertType: Alert.Types.Warning,
		message: Liferay.Language.get(
			'this-segment-name-is-currently-in-use.-please-try-a-different-one'
		),
	},
};
export default function WithBaseEdit(WrappedComponent) {
	const baseEditPropTypes = {
		addAlert: PropTypes.func.isRequired,
		channelId: PropTypes.string,
		close: PropTypes.func.isRequired,
		groupId: PropTypes.string.isRequired,
		history: PropTypes.object.isRequired,
		id: PropTypes.string,
		open: PropTypes.func.isRequired,
		segment: PropTypes.instanceOf(Segment),
	};

	class BaseEdit extends React.Component {
		static contextType = ChannelContext;

		static propTypes = baseEditPropTypes;

		state = {
			onDelete: false,
		};

		componentDidMount() {
			this._startDate = Date.now();
		}

		@autobind
		deleteSegment() {
			const {addAlert, channelId, close, groupId, history, id, open} =
				this.props;

			open(modalTypes.CONFIRMATION_MODAL, {
				message: (
					<div>
						<div className="h4 text-secondary">
							{Liferay.Language.get(
								'are-you-sure-you-want-to-delete-this-segment'
							)}
						</div>

						<p>
							{Liferay.Language.get(
								'you-will-lose-all-data-related-to-this-segment.-you-will-not-be-able-to-undo-this-operation'
							)}
						</p>
					</div>
				),
				modalVariant: 'modal-warning',
				onClose: close,
				onSubmit: () => {
					this.setState({onDelete: true});

					return API.individualSegment
						.delete({
							groupId,
							ids: [id],
						})
						.then(() => {
							addAlert({
								alertType: Alert.Types.Success,
								message: Liferay.Language.get(
									'the-segment-has-been-deleted'
								),
							});

							history.push(
								toRoute(Routes.CONTACTS_LIST_ENTITY, {
									channelId,
									groupId,
									type: SEGMENTS,
								})
							);
						})
						.catch(() => {
							addAlert({
								alertType: Alert.Types.Error,
								message: Liferay.Language.get('error'),
								timeout: false,
							});

							this.setState({onDelete: false});
						});
				},
				submitButtonDisplay: 'warning',
				submitMessage: Liferay.Language.get('delete'),
				title: Liferay.Language.get('warning'),
				titleIcon: 'warning-full',
			});
		}

		getPageTitle() {
			const {segment} = this.props;

			return this.props.id && segment
				? Liferay.Language.get('edit-individuals-segment')
				: Liferay.Language.get('create-individuals-segment');
		}

		@autobind
		handleSubmit(form, formRef, submitFn) {
			const {addAlert, channelId, close, groupId, history, id, open} =
				this.props;

			const {setSubmitting} = formRef.current;

			open(
				modalTypes.LOADING_MODAL,
				{
					message: Liferay.Language.get(
						'this-will-only-take-a-moment'
					),
					title: id
						? Liferay.Language.get('updating')
						: Liferay.Language.get('creating'),
				},
				{closeOnBlur: false}
			);

			submitFn(form)
				.then((segment) => {
					if (
						(Array.isArray(segment) && segment.length) ||
						(segment && !Array.isArray(segment))
					) {
						history.push(
							toRoute(Routes.CONTACTS_ENTITY, {
								channelId,
								groupId,
								id: segment.id || segment[0].id,
								type: SEGMENTS,
							})
						);

						addAlert({
							alertType: Alert.Types.Success,
							message: Liferay.Language.get(
								'changes-to-segment-saved'
							),
						});
					}

					setSubmitting(false);

					return segment;
				})
				.catch((error) => {
					const {alertType, message} = ERRORS[error.message];

					addAlert({
						alertType,
						message,
					});

					setSubmitting(false);
				})
				.finally(() => close());
		}

		render() {
			const {
				channelId,
				className,
				groupId,
				id,
				segment,
				type,
				...otherProps
			} = this.props;

			const {onDelete} = this.state;

			const {selectedChannel} = this.context;

			const editing = !!id;

			const breadcrumbItems = id
				? [
						breadcrumbs.getEntityName({
							active: false,
							href: toRoute(Routes.CONTACTS_SEGMENT, {
								channelId,
								groupId,
								id,
							}),
							label: segment.name,
						}),
						{
							active: true,
							label: Liferay.Language.get('edit'),
						},
					]
				: [
						{
							active: true,
							label: Liferay.Language.get('create-segment'),
						},
					];

			const SEGMENT_TYPES_LABEL_MAP = {
				[SegmentTypes.Batch]: Liferay.Language.get('batch'),
				[SegmentTypes.RealTime]: Liferay.Language.get('real-time'),
			};

			return (
				<BasePage
					className={getCN('segment-edit-root', className, {
						editing,
					})}
					documentTitle={`${this.getPageTitle()} - ${Liferay.Language.get(
						'segment'
					)}`}
				>
					<BasePage.Header
						breadcrumbs={[
							breadcrumbs.getHome({
								channelId,
								groupId,
								label: selectedChannel && selectedChannel.name,
							}),
							breadcrumbs.getSegments({channelId, groupId}),
							...breadcrumbItems,
						]}
						groupId={groupId}
					>
						<BasePage.Row>
							<BasePage.Header.TitleSection
								title={this.getPageTitle()}
							>
								<Label display="secondary" size="lg" uppercase>
									{sub(Liferay.Language.get('x-segment'), [
										SEGMENT_TYPES_LABEL_MAP[type],
									])}
								</Label>
							</BasePage.Header.TitleSection>

							<BasePage.Header.Section>
								<BasePage.Header.PageActions
									actions={
										editing
											? [
													{
														button: true,
														displayType:
															'secondary',
														label: Liferay.Language.get(
															'delete-segment'
														),
														onClick:
															this.deleteSegment,
													},
												]
											: []
									}
								/>
							</BasePage.Header.Section>
						</BasePage.Row>
					</BasePage.Header>
					<BasePage.Body pageContainer={false}>
						<WrappedComponent
							{...omitDefinedProps(otherProps, baseEditPropTypes)}
							channelId={channelId}
							editing={editing}
							groupId={groupId}
							id={id}
							onDelete={onDelete}
							onSubmit={this.handleSubmit}
							segment={segment}
						/>
					</BasePage.Body>
				</BasePage>
			);
		}
	}

	return connect(null, {
		addAlert,
		close,
		open,
	})(BaseEdit);
}
