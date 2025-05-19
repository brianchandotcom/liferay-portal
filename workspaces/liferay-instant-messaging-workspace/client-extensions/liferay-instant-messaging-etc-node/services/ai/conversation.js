/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {env, pipeline} = require('@huggingface/transformers');
const path = require('path');

const modelPath = path.join(__dirname, './models');
env.allowRemoteModels = false;
env.localModelPath = modelPath;

const fullModelPath = path.resolve(
	__dirname,
	'models',
	'SmolLM2-360M-Instruct'
);

let qaPipeline;

const initializePipeline = async () => {
	if (!qaPipeline) {
		qaPipeline = await pipeline('text-generation', fullModelPath, {
			dtype: 'fp16',
		});
	}
};

async function promptTask(prompt) {
	try {
		await initializePipeline(); // Ensure pipeline is initialized

		const output = await qaPipeline(
			[
				{
					content: prompt,
					role: 'user',
				},
			],
			{
				do_sample: false,
				max_length: 512,
				temperature: 0.5,
				top_k: 20,
				top_p: 0.8,
			}
		);

		return output[0].generated_text[output[0].generated_text.length - 1]
			.content;
	}
	catch (error) {
		console.error('Error:', error);

		return {
			answer: 'Error processing the question.',
			score: 0,
		};
	}
}

async function conversation(chatLog) {
	try {
		await initializePipeline(); // Ensure pipeline is initialized

		const output = await qaPipeline([...chatLog], {
			do_sample: false,
			max_length: 1024 * 2,
			temperature: 0.5,
			top_k: 20,
			top_p: 0.8,
		});

		return output[0].generated_text[output[0].generated_text.length - 1]
			.content;
	}
	catch (error) {
		console.error('Error:', error);

		return {
			answer: 'Error processing the question.',
			score: 0,
		};
	}
}

module.exports = {
	conversation,
	promptTask,
};
