import ClayIcon from '@clayui/icon';
import { MemberProps } from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';

import './MemberProfile.scss';

import catalogIcon from '../../assets/icons/catalog-icon.svg';
import shieldCheckIcon from '../../assets/icons/shield-check-icon.svg';
import userIcon from '../../assets/icons/user-icon.svg';

interface MemberProfileProps {
	member: MemberProps;
	setSelectedMember: (value: MemberProps | undefined) => void;
}

export function MemberProfile({member, setSelectedMember}: MemberProfileProps) {
	return (
		<div className="member-profile-view-container">
			<a className="member-profile-back-button mb-4"
				onClick={() => {setSelectedMember(undefined)}}
			>
				<ClayIcon
					className="d-inline-block"
					symbol="order-arrow-left"
				/>
				<div className="member-profile-back-button-text d-inline-block">
					&nbsp;Back to Members
				</div>
			</a>

			<div className="member-profile-image d-inline-block mr-4">
				<img
					src={member.image}
					alt="Member Image">
				</img>
			</div>
			<div className="member-profile-heading-container d-inline-block">
				<h2 className="member-profile-heading">
					{member.name}
				</h2>
				{member.lastLoginDate ? (
					<div className="member-profile-subheading">
						<div className="member-profile-subheading-email d-inline-block">
							{member.email},&nbsp;
						</div>
						<div className="member-profile-subheading-date d-inline-block">
							Last Login at {member.lastLoginDate}
						</div>
					</div>
					) : (
						<div className="member-account-never-logged-in-text d-inline-block">
							{member.email}, Never Logged In
						</div>
					)
				}
			</div>

			<div className="member-profile-row mt-5">
				<div className="member-profile-card">
					<h2 className="member-profile-card-heading d-inline-block">
						Profile
					</h2>
					<div className="member-profile-card-icon d-inline-block">
						<img src={userIcon} alt="Member Card Icon"></img>
					</div>
					<table className="member-profile-information mt-4">
						<tr className="member-profile-name">
							<th className="member-profile-name-heading">Name</th>
							<td>{member.name}</td>
						</tr>
						<tr>
							<th>Email</th>
							<td>{member.email}</td>
						</tr>
						<tr>
							<th>User ID</th>
							<td>{member.userId}</td>
						</tr>
					</table>
				</div>
				<div className="member-roles-card">
					<h2 className="member-roles-card-heading d-inline-block">
						Roles
					</h2>
					<div className="member-roles-card-icon d-inline-block">
						<img src={shieldCheckIcon} alt="Member Roles Icon"></img>
					</div>

					<table className="member-roles-information mt-4">
						<tr>
							<th className="member-roles-permissions-heading">Permissions</th>
							<td>{member.role}</td>
						</tr>
					</table>
				</div>
			</div>
			<div className="member-profile-row">
				<div className="member-account-card">
					<h2 className="member-account-card-heading d-inline-block">
						Account
					</h2>
					<div className="member-account-card-icon d-inline-block">
						<img src={catalogIcon} alt="Member Account Icon"></img>
					</div>

					<table className="member-account-information mt-4">
						<tr>
							<th className="member-account-membership-heading">Membership</th>
							<td>Invited On {member.dateCreated}</td>
						</tr>
						<tr>
							<th className="member-account-last-logged-in-heading"></th>
							<td>
								<div className="d-inline-block">
									Last Login at&nbsp;
								</div>
								{member.lastLoginDate ? (
										<div className="member-account-lasted-logged-in d-inline-block">
											{member.lastLoginDate}
										</div>
									) : (
										<div className="member-account-never-logged-in-text d-inline-block">
											Never Logged In
										</div>
									)
								}
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	);
}