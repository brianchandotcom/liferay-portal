import ClayIcon from '@clayui/icon';

import {MemberProps} from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';

import './MemberProfile.scss';
import catalogIcon from '../../assets/icons/catalog_icon.svg';
import shieldCheckIcon from '../../assets/icons/shield_check_icon.svg';
import userIcon from '../../assets/icons/user_icon.svg';
import {DetailedCard} from '../DetailedCard/DetailedCard';
import {Avatar} from '../Avatar/Avatar';
import {useAppContext} from '../../manage-app-state/AppManageState';

interface MemberProfileProps {
	member: MemberProps;
	setSelectedMember: (value: MemberProps | undefined) => void;
}

export function MemberProfile({member, setSelectedMember}: MemberProfileProps) {
	const [{gravatarAPI}, _] = useAppContext();

	return (
		<div className="member-profile-view-container">
			<a
				className="member-profile-back-button"
				onClick={() => {
					setSelectedMember(undefined);
				}}
			>
				<ClayIcon symbol="order-arrow-left" />

				<div className="member-profile-back-button-text">
					&nbsp;Back to Members
				</div>
			</a>

			<div className="d-inline-block member-profile-image">
				<Avatar
					emailAddress={member.email}
					gravatarAPI={gravatarAPI}
					initialImage={member.image}
					userName={member.name}
				/>
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

			<div className="member-profile-row">
				<DetailedCard
					cardIcon={userIcon}
					cardIconAltText="Member Card Icon"
					cardTitle="Profile"
				>
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
				</DetailedCard>

				<DetailedCard
					cardIcon={shieldCheckIcon}
					cardIconAltText="Member Roles Icon"
					cardTitle="Roles"
				>
					<table className="member-roles-information mt-4">
						<tr>
							<th className="member-roles-permissions-heading">
								Permissions
							</th>

							<td>{member.role}</td>
						</tr>
					</table>
				</DetailedCard>
			</div>

			<div className="member-profile-row">
				<DetailedCard
					cardIcon={catalogIcon}
					cardIconAltText="Member Account Icon"
					cardTitle="Account"
				>
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
				</DetailedCard>
			</div>
		</div>
	);
}
