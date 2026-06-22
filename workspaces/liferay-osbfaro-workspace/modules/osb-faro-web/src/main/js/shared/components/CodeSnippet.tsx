import CopyButton from 'shared/components/CopyButton';
import React from 'react';

interface ICodeSnippet {
	codeLines: Array<string>;
}

const CodeSnippet: React.FC<ICodeSnippet> = ({codeLines}) => {
	const getDisplayedCode = ([...codeLines]: Array<string>): string => {
		const lastLineModifier = codeLines.length > 1 ? '\n' : '';
		const lastLine = codeLines.pop();
		return codeLines.join('\n\t').concat(`${lastLineModifier}${lastLine}`);
	};

	const displayedCode = getDisplayedCode(codeLines);

	return (
		<div className='code-snippet-root'>
			<CopyButton
				buttonText={Liferay.Language.get('copy')}
				className='copy-button'
				displayType='secondary'
				text={displayedCode}
			/>

			<code className='code-container'>{displayedCode}</code>
		</div>
	);
};

export default CodeSnippet;
