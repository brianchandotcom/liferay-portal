import './Tag.scss';

interface TagProps {
    label: string
}

export function Tag ({
    label
}: TagProps) {
    return (
        <span className='tag'>{label}</span>

    )
}