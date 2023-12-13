/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CloseCircleTwoTone} from '@ant-design/icons';
import OrgChart from '@balkangraph/orgchart.js/orgchart';
import {ClayModalProvider} from '@clayui/modal';
import {Modal, Spin} from 'antd';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import EditNode from './controls/edit-node/edit-node';

import './template-diagram.css';
import {
	addIcon,
	editIcon,
	removeIcon,
} from '../../assets/svg-icons/diagram-icons';
import {
	addNode,
	deleteFolderTemplateBatch,
	getAvailableTemplatesNodesPage,
	updateFolderTemplate,
} from '../../services/template-diagram.service';
import {ApplicationUtil} from '../../utils/appUtil';

const FolderStructureDesigner = ({templateId}) => {
	const myElementRef = useRef(null);
	const spritemap = ApplicationUtil.getDefaultSpriteMap();
	const [isLoading, setIsLoading] = useState(false);
	const [orgChart, setOrgChart] = useState(null); // Updated variable name
	const updateChartNode = useCallback((newChart, node, close = true) => {
		const chartNode = {
			description: node.description,
			id: node.id,
			name: node.name,
			pid: node.parentID,
			root: node.root,
			templateID: node.templateID,
		};
		newChart.update(chartNode);
		newChart.draw(OrgChart.action.init);
		setOrgChart((prev) => (prev === newChart ? prev : newChart));
		if (close) {
			closeNodeEditor();
		}
	}, []);
	const editNode = useCallback(
		async (
			newChart,
			nodeId,
			templateID,
			name,
			description,
			parentID,
			root
		) => {
			editModalRef.current = Modal.warn({
				closeIcon: <CloseCircleTwoTone />,
				content: (
					<EditNode
						chart={newChart}
						close={closeNodeEditor}
						description={description}
						name={name}
						nodeId={nodeId}
						parentID={parentID}
						root={root}
						templateID={templateID}
						updateParent={updateChartNode}
					></EditNode>
				),
				footer: null,
				title: 'Edit Node',
			});
		},
		[updateChartNode]
	);
	const loadTemplateNodes = useCallback(
		async (newOrgChart) => {
			setIsLoading(true);
			const result = await getAvailableTemplatesNodesPage(templateId);
			const nodes = result.items.map((node) => {
				return {
					description: node.description,
					id: node.id,
					name: node.name,
					pid: node.parentID,
					root: node.root,
					templateID: node.templateID,
				};
			});
			if (nodes.length <= 0) {
				const root = await addNode(0, true, 'Root Folder', templateId);
				nodes.push(root);
			}
			if (newOrgChart) {
				newOrgChart.load(nodes);
				newOrgChart.draw(OrgChart.action.init);
			}
			setIsLoading(false);
		},
		[templateId]
	);

	const renderChart = useCallback(() => {
		OrgChart.templates['white'] = {...OrgChart.templates['ana']};
		OrgChart.templates['white'].size = [300, 100];
		OrgChart.templates['white'].node =
			'<rect x="0" y="0" height="80" width="300" fill="#039BE5" stroke-width="1" stroke="#ffca2761" rx="0" ry="0"></rect>' +
			`<svg width="64px" height="64px" x="20" y="10" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg">
            <path d="M853.333333 256H469.333333l-85.333333-85.333333H170.666667c-46.933333 0-85.333333 38.4-85.333334 85.333333v170.666667h853.333334v-85.333334c0-46.933333-38.4-85.333333-85.333334-85.333333z" fill="#FFA000" />
            <path d="M853.333333 256H170.666667c-46.933333 0-85.333333 38.4-85.333334 85.333333v426.666667c0 46.933333 38.4 85.333333 85.333334 85.333333h682.666666c46.933333 0 85.333333-38.4 85.333334-85.333333V341.333333c0-46.933333-38.4-85.333333-85.333334-85.333333z" fill="#FFCA28" />
            </svg>`;
		OrgChart.templates['white'].ripple = {
			color: '#0890D3',
			radius: 0,
			rect: {height: 80, rx: 0, ry: 0, width: 300, x: 0, y: 0},
		};
		OrgChart.templates['white'].nodeMenuButton =
			'<g style="cursor:pointer;" transform="matrix(1,0,0,1,285,33)" data-ctrl-n-menu-id="{id}"><rect x="-4" y="-10" fill="#000000" fill-opacity="0" width="22" height="22"></rect><circle cx="0" cy="0" r="2" fill="black"></circle><circle cx="0" cy="7" r="2" fill="black"></circle><circle cx="0" cy="14" r="2" fill="black"></circle></g>';
		OrgChart.templates['white'][
			'field_0'
		] = `<svg x="100" width="200" height="60" xmlns="http://www.w3.org/2000/svg"><text x="10" y="20" font-family="Arial" font-size="14"><tspan x="10" dy="2.2em">{val}</tspan></text></svg>`;
		const newChart = new OrgChart(myElementRef.current, {
			enableDragDrop: true,
			enableSearch: false,
			nodeBinding: {
				field_0: 'name',
			},
			nodeContent: 'title',
			nodeMenu: {
				addCustom: {
					icon: addIcon,
					onClick: async (node) => {
						const newNode = await addNode(
							node,
							false,
							'New Node',
							templateId
						);
						newChart.addNode(newNode);
						newChart.draw(OrgChart.action.init);
						setOrgChart((prev) =>
							prev === newChart ? prev : newChart
						);

						return false;
					},
					text: 'Add',
				},
				deleteNode: {
					icon: removeIcon,
					onClick: async (nodeId) => {
						const deleteNodeAndChilds = async (nodeId) => {
							const nodesToBeDeleted = [];
							const deleteNode = (id) => {
								const node = newChart.getNode(id);
								nodesToBeDeleted.push(node);
								if (
									node.childrenIds.length.toString() === '0'
								) {
									return;
								}
								for (
									let index = 0;
									index < node.childrenIds.length;
									index++
								) {
									deleteNode(node.childrenIds[index]);
								}
							};
							deleteNode(nodeId);
							const lrNodes = [];
							nodesToBeDeleted.forEach((item) => {
								lrNodes.push(newChart.get(item.id));
								newChart.remove(item.id);
							});
							await deleteFolderTemplateBatch(lrNodes);
							newChart.draw(OrgChart.action.init);
							setOrgChart((prev) =>
								prev === newChart ? prev : newChart
							);
						};
						if (newChart) {
							if (
								nodeId.toString() ===
								newChart.roots[0].id.toString()
							) {
								return false;
							}
							else {
								await deleteNodeAndChilds(nodeId);
							}

							return true;
						}

						return false;
					},
					text: 'Remove',
				},
				edit: {
					icon: editIcon,
					onClick: (node) => {
						if (newChart) {
							const selectedNode = newChart.get(node);
							selectedNode.templateID = templateId;
							editNode(
								newChart,
								node,
								templateId,
								selectedNode.name,
								selectedNode.description,
								selectedNode.pid,
								selectedNode.root
							);

							return true;
						}
					},
					text: 'Edit',
				},
			},
			nodeMouseClick: OrgChart.action.expand,
			template: 'white',
			toolbar: {
				expandAll: true,
				fit: true,
				layout: false,
				zoom: true,
			},
		});
		newChart.on('drop', (sender, draggedNodeId, droppedNodeId) => {
			if (draggedNodeId.toString() === '1') {
				return false;
			}
			const node = newChart.get(draggedNodeId);
			const newParentNode = newChart.get(droppedNodeId);
			const lrNode = {
				description: node.description,
				id: node.id,
				name: node.name,
				parentID: newParentNode.id,
				root: node.root,
				templateID: node.templateID,
			};
			updateFolderTemplate(node.id, lrNode);
			updateChartNode(newChart, lrNode, false);

			return false;
		});
		setOrgChart((prev) => (prev ? prev : newChart));
		loadTemplateNodes(newChart);
	}, [editNode, templateId, loadTemplateNodes, updateChartNode]);
	const editModalRef = useRef();
	const closeNodeEditor = () => {
		if (editModalRef && editModalRef.current) {
			editModalRef.current.destroy();
		}
	};
	useEffect(() => {
		if (myElementRef && !orgChart) {
			renderChart();
		}

		return () => {};
	}, [renderChart, orgChart]);

	return (
		<>
			<ClayModalProvider spritemap={spritemap}>
				{isLoading && <Spin fullscreen></Spin>}
				<div
					id="orgChartContainer"
					ref={myElementRef}
					style={{height: '100%', width: '100%'}}
				></div>
			</ClayModalProvider>
		</>
	);
};

export default FolderStructureDesigner;
