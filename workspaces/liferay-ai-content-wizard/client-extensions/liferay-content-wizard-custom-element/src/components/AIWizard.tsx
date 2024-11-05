/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Modal, {useModal} from '@clayui/modal';
import {zodResolver} from '@hookform/resolvers/zod';
import {useEffect, useRef, useState} from 'react';
import {useForm} from 'react-hook-form';
import useSWR from 'swr';
import {z} from 'zod';

import useAIWizardContentOAuth2 from '../hooks/useAIWizardOAuth2';
import {Message} from '../types';
import ChatBody from './Chat/ChatBody';
import ChatInput from './Chat/ChatInput';

type AIWizardProps = {
	modal: ReturnType<typeof useModal>;
};

const schema = z.object({
	files: z.array(
		z.object({type: z.enum(['fileEntryId', 'folder']), value: z.string()})
	),
	image: z.string(),
	input: z.string(),
});

export type Schema = z.infer<typeof schema>;

export default function AIWizard({modal}: AIWizardProps) {
	const [fullscreen, setFullscreen] = useState(false);
	const [messages, setMessages] = useState<Message[]>([]);
	const aiWizardContentOAuth2 = useAIWizardContentOAuth2();
	const ref = useRef<HTMLDivElement>(null);

	const {data: settings = {}, isLoading} = useSWR('/ai/settings/status', () =>
		aiWizardContentOAuth2.getSettingsStatus()
	);

	const form = useForm<Schema>({
		defaultValues: {files: [], input: ''},
		resolver: zodResolver(schema),
	});

	const appendMessage = (message: Message) =>
		setMessages((prevMessages) => [...prevMessages, message]);

	useEffect(() => {
		ref.current?.scrollIntoView({behavior: 'smooth'});
	}, [messages]);

	async function onSubmit({files, input}: Schema) {
		appendMessage({role: 'user', text: input});

		try {
			const data = await aiWizardContentOAuth2.generate({
				files,
				image: (
					document.getElementById(
						'wizard-content-image'
					) as HTMLInputElement
				)?.value,
				question: input,
			});

			appendMessage({
				role: 'assistant',
				text: data.output || JSON.stringify(data, null, 2),
			});

			form.setValue('input', '');
		}
		catch (error) {
			const data = await (error as Response).json();

			appendMessage({
				role: 'system',
				text: (
					<ClayAlert displayType="danger">
						<b>Error:</b> it seems like you might have typed a
						symbol by mistake. Please try again.
						<details className="mt-2">
							<pre>{JSON.stringify(data, null, 2)}</pre>
						</details>
					</ClayAlert>
				),
			});
		}
	}

	const configured = settings.active;

	return (
		<Modal
			className="ai-parent-modal"
			disableAutoClose
			observer={modal.observer}
			size={fullscreen ? 'full-screen' : 'lg'}
		>
			<Modal.Header>
				<div className="align-items-center d-flex justify-content-between">
					Liferay AI Content Wizard
					<span
						className="modal-options"
						onClick={() => setFullscreen(!fullscreen)}
					>
						<ClayIcon symbol={fullscreen ? 'compress' : 'expand'} />
					</span>
				</div>
			</Modal.Header>

			<Modal.Body>
				<ChatBody
					configured={configured}
					fullscreen={fullscreen}
					isLoading={isLoading}
					isLoadingContent={form.formState.isSubmitting}
					messages={messages}
					onClose={modal.onClose}
					onSelectAsset={(asset) => {
						appendMessage({
							role: 'user',
							text: `I would like to create ${asset.title}.`,
						});

						setTimeout(() => {
							appendMessage({
								role: 'assistant',
								text: (
									<>
										Tell me more about what you would like
										to create. Here is an example: <br />
										<br />
										<i>&quot;{asset.hint}&quot;</i>
									</>
								),
							});
						}, 750);
					}}
				/>

				{/* Bottom Reference, to scroll messages */}
				<div ref={ref} />
			</Modal.Body>

			{configured && (
				<div className="modal-footer">
					<ChatInput form={form} onSubmit={onSubmit} />

					<div className="d-flex justify-content-end mt-4">
						<ClayButton
							borderless
							displayType="secondary"
							onClick={() => setMessages([])}
							size="xs"
						>
							<ClayIcon symbol="reset" /> Restart Chat
						</ClayButton>
					</div>
				</div>
			)}
		</Modal>
	);
}
