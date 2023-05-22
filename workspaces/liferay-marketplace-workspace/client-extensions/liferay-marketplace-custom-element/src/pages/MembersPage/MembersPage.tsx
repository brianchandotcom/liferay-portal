import ClayLoadingIndicator from '@clayui/loading-indicator';
import { useEffect, useState } from 'react';

import { DashboardListItems } from '../DashBoardPage/DashboardPage';
import { DashboardPage } from '../DashBoardPage/DashboardPage';
import {
  DashboardTable,
  TableHeaders,
} from '../../components/DashboardTable/DashboardTable';
import { MemberProfile } from '../../components/MemberProfile/MemberProfile';
import {
  AccountBriefProps,
  customerRoles,
  MemberProps,
  publisherRoles,
  UserAccountProps,
} from '../PublishedAppsDashboardPage/PublishedDashboardPageUtil';
import { getMyUserAccount, getUserAccounts } from '../../utils/api';
import { DashboardMemberTableRow } from '../../components/DashboardTable/DashboardMemberTableRow';
import { InviteMemberModal } from '../../components/InviteMemberModal/InviteMemberModal';

interface MembersPageProps {
  dashboardNavigationItems: DashboardListItems[];
  selectedAccount: Account;
  setShowDashboardNavigation: (value: boolean) => void;
}

const memberTableHeaders: TableHeaders = [
  {
    iconSymbol: 'order-arrow',
    title: 'Name',
  },
  {
    title: 'Email',
  },
  {
    title: 'Role',
  },
];

export function MembersPage({
  dashboardNavigationItems,
  selectedAccount,

  setShowDashboardNavigation,
}: MembersPageProps) {
  const [visible, setVisible] = useState(false);
  const [loading, setLoading] = useState(false);
  const [members, setMembers] = useState<MemberProps[]>(Array<MemberProps>());
  const [selectedMember, setSelectedMember] = useState<MemberProps>();

  const memberMessages = {
    description: 'Manage users in your development team and invite new ones',
    emptyStateMessage: {
      description1: 'Create new members and they will show up here.',
      description2: 'Click on “New Member” to start.',
      title: 'No Members Yet',
    },
    title: 'Members',
  };

  function getRolesList(accountBriefs: AccountBrief[]) {
    const rolesList: string[] = [];

    const accountBrief = accountBriefs.find(
      (accountBrief) => accountBrief.id === selectedAccount.id
    );

    accountBrief?.roleBriefs.forEach((role) => {
      rolesList.push(role.name);
    });

    return rolesList.join(', ');
  }

  useEffect(() => {
    (async () => {
      const currentUserAccountResponse = await getMyUserAccount();

      const currentUserAccount = {
        accountBriefs: currentUserAccountResponse.accountBriefs,
        isCustomerAccount: false,
        isPublisherAccount: false,
      };

      const currentUserAccountBriefs = currentUserAccount.accountBriefs.find(
        (accountBrief: { id: number }) => accountBrief.id === selectedAccount.id
      );

      if (currentUserAccountBriefs) {
        customerRoles.forEach((customerRole) => {
          if (
            currentUserAccountBriefs.roleBriefs.find(
              (role: { name: string }) => role.name === customerRole
            )
          ) {
            currentUserAccount.isCustomerAccount = true;
          }
        });

        publisherRoles.forEach((publisherRole) => {
          if (
            currentUserAccountBriefs.roleBriefs.find(
              (role: { name: string }) => role.name === publisherRole
            )
          ) {
            currentUserAccount.isPublisherAccount = true;
          }
        });
      }

      const accountsListResponse = await getUserAccounts();

      const membersList = accountsListResponse.items.map(
        (member: UserAccountProps) => {
          return {
            accountBriefs: member.accountBriefs,
            dateCreated: member.dateCreated,
            email: member.emailAddress,
            image: member.image,
            isCustomerAccount: false,
            isPublisherAccount: false,
            lastLoginDate: member.lastLoginDate,
            name: member.name,
            role: getRolesList(member.accountBriefs),
            userId: member.id,
          } as MemberProps;
        }
      );

      membersList.forEach((member: MemberProps) => {
        const rolesList = member.role.split(', ');

        customerRoles.forEach((customerRole) => {
          if (rolesList.find((role) => role === customerRole)) {
            member.isCustomerAccount = true;
          }
        });

        publisherRoles.forEach((publisherRole) => {
          if (rolesList.find((role) => role === publisherRole)) {
            member.isPublisherAccount = true;
          }
        });
      });

      let filteredMembersList: MemberProps[] = [];

      filteredMembersList = membersList.filter((member: MemberProps) => {
        if (
          member.accountBriefs.find(
            (accountBrief: AccountBriefProps) =>
              accountBrief.externalReferenceCode ===
              selectedAccount.externalReferenceCode
          ) &&
          member.isPublisherAccount
        ) {
          return true;
        }

        return false;
      });

      setMembers(filteredMembersList);
    })();
  }, [selectedAccount]);

  return (
    <>
      {!loading ? (
        <DashboardPage
          buttonMessage="+ New Member"
          dashboardNavigationItems={dashboardNavigationItems}
          messages={memberMessages}
          onButtonClick={() => setVisible(true)}
        >
          {selectedMember ? (
            <MemberProfile
              member={selectedMember}
              setSelectedMember={setSelectedMember}
            ></MemberProfile>
          ) : (
            <>
              <DashboardTable<MemberProps>
                emptyStateMessage={memberMessages.emptyStateMessage}
                items={members}
                tableHeaders={memberTableHeaders}
              >
                {(item) => (
                  <DashboardMemberTableRow
                    item={item}
                    key={item.name}
                    onSelectedMemberChange={setSelectedMember}
                  />
                )}
              </DashboardTable>
            </>
          )}
        </DashboardPage>
      ) : (
        <ClayLoadingIndicator
          className="members-page-loading-indicator"
          displayType="primary"
          shape="circle"
          size="md"
        />
      )}

      {visible && (
        <InviteMemberModal
          handleClose={() => setVisible(false)}
          selectedAccount={selectedAccount}
        ></InviteMemberModal>
      )}
    </>
  );
}
