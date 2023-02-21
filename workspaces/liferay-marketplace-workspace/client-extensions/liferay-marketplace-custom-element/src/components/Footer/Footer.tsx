import './Footer.scss';

export function Footer() {
    return (
        <footer className='footer-container'>
            <div className='footer-left-container'>
                <span className='footer-text'>© 2022 Liferay Inc. All Rights Reserved</span>
                <a className='footer-text' href="#">Content Policy</a>
                <a className='footer-text' href="#">Terms</a>
                <a className='footer-text' href="#">Privacy</a>
            </div>

            <a className='footer-text-bold' href="#">Send Feedback</a>
        </footer>
    )
}