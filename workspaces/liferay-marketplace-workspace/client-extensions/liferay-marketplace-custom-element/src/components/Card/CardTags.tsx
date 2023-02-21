
import './CardTags.scss';
import {Tag} from '../../components/Tag/Tag';

interface CardTagsProps {
    title: string;
    icon?: string;
    tags: string[];
}

export function CardTags({
    icon,
    tags,
    title,
}: CardTagsProps) {
    return (
        <div className='card-tags-container'>
            <div className='card-tags-main-info'>
                <div className='card-tags-icon'><img
                    className='card-tags-icon-image'
                    src={icon}
                    alt="Icon"
                /></div>
                
                <div className='card-tags-info'>
                    <span
                        className='card-tags-info-text'
                    >
                        {title}
                    </span>
                    
                    <div className="card-tags-info-tags">
                    {tags.map(tag => {
                        return (
                            <Tag label={tag} ></Tag>
                        )
                    })}
                    </div>
                </div>
            </div>

           
        </div>
    );
}