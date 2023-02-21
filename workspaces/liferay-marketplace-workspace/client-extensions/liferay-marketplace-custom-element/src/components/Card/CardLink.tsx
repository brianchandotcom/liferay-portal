
import './CardLink.scss';

interface CardLinkProps {
    title: string;
    icon?: string;
    description: string;
}

export function CardLink({
    description,
    title,
    icon,
}: CardLinkProps) {
    return (
        <div className='card-link-container'>
            <div className='card-link-main-info'>
                <div className='card-link-icon'><img
                    className='card-link-icon-image'
                    src={icon}
                    alt="Icon"
                /></div>
                
                <div className='card-link-info'>
                    <span
                        className='card-link-info-text'
                    >
                        {title}
                    </span>
                    <a href='#' className='card-link-info-description'>{description}</a>
                   
                </div>
            </div>

           
        </div>
    );
}