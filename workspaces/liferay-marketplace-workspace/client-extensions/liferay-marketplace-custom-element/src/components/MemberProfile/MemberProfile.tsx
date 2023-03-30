import ClayIcon from '@clayui/icon';

import {MemberProps} from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';

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
			<a
				className="mb-4 member-profile-back-button"
				onClick={() => {
					setSelectedMember(undefined);
				}}
			>
				<ClayIcon
					className="d-inline-block"
					symbol="order-arrow-left"
				/>

				<div className="d-inline-block member-profile-back-button-text">
					&nbsp;Back to Members
				</div>
			</a>

			<div className="d-inline-block member-profile-image mr-4">
				<img alt="Member Image" src={member.image}></img>
			</div>

			<div className="d-inline-block member-profile-heading-container">
				<h2 className="member-profile-heading">{member.name}</h2>

				{member.lastLoginDate ? (
					<div className="member-profile-subheading">
						<div className="d-inline-block member-profile-subheading-email">
							{member.email},&nbsp;
						</div>

						<div className="d-inline-block member-profile-subheading-date">
							Last Login at {member.lastLoginDate}
						</div>
					</div>
				) : (
					<div className="d-inline-block member-account-never-logged-in-text">
						{member.email}, Never Logged In
					</div>
				)}
			</div>

			<div className="member-profile-row mt-5">
				<div className="member-profile-card">
					<h2 className="d-inline-block member-profile-card-heading">
						Profile
					</h2>

					<div className="d-inline-block member-profile-card-icon">
						<img alt="Member Card Icon" src={userIcon}></img>
					</div>

					<table className="member-profile-information mt-4">
						<tr className="member-profile-name">
							<th className="member-profile-name-heading">
								Name
							</th>

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
					<h2 className="d-inline-block member-roles-card-heading">
						Roles
					</h2>

					<div className="d-inline-block member-roles-card-icon">
						<img
							alt="Member Roles Icon"
							src={shieldCheckIcon}
						></img>
					</div>

					<table className="member-roles-information mt-4">
						<tr>
							<th className="member-roles-permissions-heading">
								Permissions
							</th>

							<td>{member.role}</td>
						</tr>
					</table>
				</div>
			</div>

			<div className="member-profile-row">
				<div className="member-account-card">
					<h2 className="d-inline-block member-account-card-heading">
						Account
					</h2>

					<div className="d-inline-block member-account-card-icon">
						<img alt="Member Account Icon" src={catalogIcon}></img>
					</div>

					<table className="member-account-information mt-4">
						<tr>
							<th className="member-account-membership-heading">
								Membership
							</th>

							<td>Invited On {member.dateCreated}</td>
						</tr>

						<tr>
							<th className="member-account-last-logged-in-heading"></th>

							<td>
								<div className="d-inline-block">
									Last Login at&nbsp;
								</div>

								{member.lastLoginDate ? (
									<div className="d-inline-block member-account-lasted-logged-in">
										{member.lastLoginDate}
									</div>
								) : (
									<div className="d-inline-block member-account-never-logged-in-text">
										Never Logged In
									</div>
								)}
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	);
}
