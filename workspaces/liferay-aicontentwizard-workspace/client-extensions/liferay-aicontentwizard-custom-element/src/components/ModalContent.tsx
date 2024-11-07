/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Card from '@clayui/card';
import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import LoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {useState} from 'react';
import ReactMarkdown from 'react-markdown';

import {useAppContext} from '../context/AppContext';
import {Liferay} from '../services/liferay';
import {Message as MessageType} from '../types';
import {assets} from '../utils/assets';

const ASSETS_BASE_LIMIT = 4;
const ASSETS_BASE_LIMIT_FULLSCREEN = 5;
const SHOW_MORE = false; // Hiding More Button Temporarily For LPD-33226

type ModalContentProps = {
	fullscreen: boolean;
	isLoadingContent: boolean;
	messages: any[];
	onSelectAsset: (asset: any) => void;
};

const Asset = ({asset, isLoadingContent, onSelectionClick}: any) => (
	<div className="mb-3 mt-0 option-col">
		<Card className="ai-prompt-option cursor-pointer">
			<Card.Body
				onClick={() =>
					isLoadingContent ? null : onSelectionClick(asset)
				}
			>
				<span
					className="icon-square"
					style={{
						backgroundColor: asset.bgColor + '22',
						border: '1px solid ' + asset.bgColor,
					}}
				>
					<ClayIcon color={asset.iconColor} symbol={asset.icon} />
				</span>
				<small className="font-weight-bold ml-2">{asset.title}</small>
			</Card.Body>
		</Card>
	</div>
);

const Message = ({
	children,
	role,
}: {
	children: any;
} & Omit<MessageType, 'text'>) => {
	const {myUserAccount} = useAppContext();

	if (role === 'system') {
		return children;
	}

	return (
		<div
			className={classNames('d-flex rounded p-4 align-items-center', {
				'ai-options-panel': role === 'assistant',
				'justify-content-end ': role === 'user',
			})}
		>
			{role === 'assistant' && (
				<div className="mr-5">
					<ClayIcon color="blue" symbol="stars" />
				</div>
			)}

			<div className="optionContainer">{children}</div>

			{role === 'user' && (
				<img
					className="ml-3 rounded-circle"
					height={32}
					src={myUserAccount?.image || '/image/user_portrait'}
					width={32}
				/>
			)}
		</div>
	);
};

const More = ({
	fullscreen,
	isLoadingContent,
	setAssetCount,
}: {
	fullscreen: boolean;
	isLoadingContent: boolean;
	setAssetCount: React.Dispatch<number>;
}) => (
	<Asset
		asset={{icon: 'plus', title: 'More'}}
		isLoadingContent={isLoadingContent}
		onSelectionClick={() =>
			setAssetCount(
				(assetCount) =>
					assetCount +
					(fullscreen
						? ASSETS_BASE_LIMIT_FULLSCREEN
						: ASSETS_BASE_LIMIT)
			)
		}
	/>
);

export default function ModalContent({
	fullscreen,
	isLoadingContent,
	messages,
	onSelectAsset,
}: ModalContentProps) {
	const [assetCount, setAssetCount] = useState(ASSETS_BASE_LIMIT);

	return (
		<>
			<div
				className={classNames({
					disabled: isLoadingContent,
				})}
			>
				<Message role="assistant">
					<b>
						Hi {Liferay.ThemeDisplay.getUserName()}! What would you
						like to generate?
					</b>
					<br />
					Suggested content (choose one):
					<div className="d-flex flex-wrap mt-3 row">
						{assets
							.filter((_, index) => index < assetCount)
							.map((asset, index) => (
								<Asset
									asset={asset}
									isLoadingContent={isLoadingContent}
									key={index}
									onSelectionClick={onSelectAsset}
								/>
							))}

						{assetCount < assets.length && SHOW_MORE && (
							<More
								fullscreen={fullscreen}
								isLoadingContent={isLoadingContent}
								setAssetCount={setAssetCount}
							/>
						)}
					</div>
				</Message>
			</div>

			{messages.map((message, index) => (
				<Message key={index} role={message.role}>
					{React.isValidElement(message.text) ? (
						message.text
					) : (
						<ReactMarkdown>{message.text}</ReactMarkdown>
					)}
				</Message>
			))}

			{isLoadingContent && (
				<Message role="assistant">
					<div className="align-items-center d-flex justify-content-center w-100">
						<Text color="secondary" italic>
							Content is being generated... be patient.
						</Text>

						<LoadingIndicator
							className="ml-2"
							displayType="secondary"
							shape="squares"
							size="sm"
							title="Content is being generated... be patient."
						/>
					</div>
				</Message>
			)}
		</>
	);
}
