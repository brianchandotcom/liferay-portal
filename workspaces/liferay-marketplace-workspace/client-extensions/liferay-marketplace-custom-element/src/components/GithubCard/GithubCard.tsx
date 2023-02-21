import githubIcon from '../../assets/icons/github-icon.svg'
import linkIcon from '../../assets/icons/link-icon.svg'

import AutoComplete from '../AutoComplete';
import ClayButton from '@clayui/button';

import './GithubCard.scss';

interface GithubCard {
    user: string;
}

export function GithubCard({
    user
} : GithubCard){
    return (
        <div className='github-card-container'>
            <div className='github-card-header'>
                <div className='github-card-header-title'>
                    <div className='github-card-header-circle'>
                        <img
                            className='github-card-header-icon-github'
                            src={githubIcon}
                        />
                    </div>
                    <img
                        className='github-card-header-icon-link'
                        src={linkIcon}
                    />
                    <span>
                        Connected to <b>{user}</b> account
                    </span>
                </div>
                <div className='github-card-header-button'>
                    <ClayButton
                        size="sm"
                        displayType='secondary'
                    >
                        <span>Remove</span>
                    </ClayButton>
                </div>
            </div>
            <div>
                <hr className='github-card-divider'></hr>
            </div>
            <div className='github-card-content'>
                <AutoComplete
                    emptyStateMessage='Not found'
                    items={[]}
                    label='Repo' 
                    onChangeQuery={() => {}}
                    onSelectItem={() => {}}
                    query=''
                    required
                >
                    {() => (<div></div>)}
                </AutoComplete>
                <AutoComplete
                    emptyStateMessage='Not found'
                    items={[]}
                    label='Branch'
                    onChangeQuery={() => {}}
                    onSelectItem={() => {}}
                    query=''
                    required
                >
                    {() => (<div></div>)}
                </AutoComplete>
            </div>
        </div>
    );
}