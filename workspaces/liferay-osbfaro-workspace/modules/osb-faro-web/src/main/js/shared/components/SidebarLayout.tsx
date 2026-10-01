import Loading from 'shared/components/Loading';
import NoPropertiesAvailable from 'shared/pages/NoPropertiesAvailable';
import React, {Suspense, useRef} from 'react';
import Sidebar from 'shared/components/sidebar';
import Toolbar from 'shared/components/toolbar';
import {collapseSidebar} from 'shared/actions/sidebar';
import {Map} from 'immutable';
import {Outlet, useLocation, useParams} from 'react-router-dom';
import {RootState} from 'shared/store';
import {useChannelContext} from 'shared/context/channel';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDispatch, useSelector} from 'react-redux';

const SidebarLayout: React.FC = () => {
	const {groupId = ''} = useParams<{groupId: string}>();

	const {pathname} = useLocation();

	const {channels, selectedChannel} = useChannelContext();

	const currentUser = useCurrentUser();

	const collapsed = useSelector((state: RootState) =>
		state.getIn(['sidebar', String(currentUser.id), 'collapsed'], false)
	);

	const collapsedSections = useSelector((state: RootState) =>
		state.getIn(
			['sidebar', String(currentUser.id), 'collapsedSections'],
			Map()
		)
	);

	const dispatch = useDispatch();

	const contentRef = useRef<HTMLDivElement>(null);

	return (
		<>
			<Toolbar groupId={groupId} />

			<div>
				<Sidebar
					activePathname={pathname}
					channelId={selectedChannel?.id}
					channels={channels}
					collapsed={collapsed}
					collapsedSections={collapsedSections}
					containerRef={contentRef}
					groupId={groupId}
					onCollapsedChange={(collapsed) =>
						dispatch(
							collapseSidebar({
								collapsed,
								currentUserId: currentUser.id,
							})
						)
					}
					onSectionToggle={(sectionKey, collapsed) =>
						dispatch(
							collapseSidebar({
								collapsed,
								currentUserId: currentUser.id,
								sectionKey,
							})
						)
					}
				/>

				<div ref={contentRef}>
					<Suspense fallback={<Loading />}>
						{selectedChannel ? (
							<Outlet />
						) : (
							<NoPropertiesAvailable
								currentUser={currentUser}
								groupId={groupId}
							/>
						)}
					</Suspense>
				</div>
			</div>
		</>
	);
};

export default SidebarLayout;
