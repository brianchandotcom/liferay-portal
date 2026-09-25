import DocumentTitle from 'shared/components/DocumentTitle';
import React from 'react';
import Title from './Title';
import Toolbar from './Toolbar';

interface IBaseEditPageProps {
	children?: React.ReactNode;
	className?: string;
	documentTitle: string;
}

export const BaseEditPage: React.FC<IBaseEditPageProps> & {
	Title: typeof Title;
	Toolbar: typeof Toolbar;
} = ({children, className, documentTitle}) => (
	<div className={className}>
		<DocumentTitle title={documentTitle} />

		{children}
	</div>
);

BaseEditPage.Title = Title;
BaseEditPage.Toolbar = Toolbar;

export default BaseEditPage;
