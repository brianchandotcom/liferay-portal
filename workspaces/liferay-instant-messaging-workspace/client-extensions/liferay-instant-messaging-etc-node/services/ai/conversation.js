const {pipeline, env} = require('@huggingface/transformers');
const path = require('path');

const modelPath = path.join(__dirname, './models');
env.allowRemoteModels = false;
env.localModelPath = modelPath;

const fullModelPath = path.resolve(__dirname, 'models', 'SmolLM2-360M-Instruct');

let qaPipeline;

const initializePipeline = async () => {
    if (!qaPipeline) {
        qaPipeline = await pipeline('text-generation', fullModelPath,{
            dtype: "fp16"
        });
    }
};

async function promptTask(prompt){

    try {
        await initializePipeline();  // Ensure pipeline is initialized

        const output = await qaPipeline(
            [{
                "role":"user",
                "content":prompt
            }], {
                max_length: 512,
                temperature: 0.5,
                top_k: 20,
                top_p: 0.8,
                do_sample: false
            }
        );

        return output[0].generated_text[output[0].generated_text.length - 1].content;

    } catch (error) {
        console.error('Error:', error);
        return {score: 0, answer: 'Error processing the question.'};
    }

}

async function conversation(chatLog) {

    try {
        await initializePipeline();  // Ensure pipeline is initialized

        const output = await qaPipeline(
            [
                ...chatLog
            ], {
                max_length: 1024 * 2,
                temperature: 0.5,
                top_k: 20,
                top_p: 0.8,
                do_sample: false
            }
        );

        return output[0].generated_text[output[0].generated_text.length - 1].content;

    } catch (error) {
        console.error('Error:', error);
        return {score: 0, answer: 'Error processing the question.'};
    }
}

module.exports = {
    conversation,
    promptTask
};

