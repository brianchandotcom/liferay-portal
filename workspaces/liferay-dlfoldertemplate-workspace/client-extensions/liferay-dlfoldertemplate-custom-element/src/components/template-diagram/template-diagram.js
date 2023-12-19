/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CloseCircleTwoTone} from '@ant-design/icons';
import OrgChart from '@balkangraph/orgchart.js/orgchart';
import {Modal, Spin} from 'antd';
import React, {useCallback, useEffect, useRef, useState} from 'react';

import EditNode from './controls/edit-node/edit-node';

import './template-diagram.css';
import {
	addIcon,
	diagramNodeBox,
	diagramNodeContent,
	diagramNodeMenuButton,
	editIcon,
	removeIcon,
} from '../../assets/svg-icons/diagram-icons';
import {
	addNode,
	deleteFolderTemplateBatch,
	getAvailableTemplatesNodesPage,
	updateFolderTemplate,
} from '../../services/template-diagram.service';

const FolderStructureDesigner = ({templateId}) => {
	const myElementRef = useRef(null);

	const [isLoading, setIsLoading] = useState(false);

	const [orgChart, setOrgChart] = useState(null);

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
		async (data) => {
			editModalRef.current = Modal.warn({
				closeIcon: <CloseCircleTwoTone />,
				content: (
					<EditNode
						chart={data.chart}
						description={data.description}
						name={data.name}
						nodeId={data.nodeId}
						onClose={closeNodeEditor}
						onNodeUpdate={updateChartNode}
						parentID={data.parentID}
						root={data.root}
						templateID={data.templateID}
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

		OrgChart.templates['white'].node = diagramNodeBox;

		OrgChart.templates['white'].ripple = {
			color: '#0890D3',
			radius: 0,
			rect: {height: 80, rx: 0, ry: 0, width: 300, x: 0, y: 0},
		};

		OrgChart.templates['white'].nodeMenuButton = diagramNodeMenuButton;

		OrgChart.templates['white']['field_0'] = diagramNodeContent;

		const chart = new OrgChart(myElementRef.current, {
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

						chart.addNode(newNode);

						chart.draw(OrgChart.action.init);

						setOrgChart((prev) => (prev === chart ? prev : chart));

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
								const node = chart.getNode(id);

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

							const liferayNodes = [];

							nodesToBeDeleted.forEach((item) => {
								liferayNodes.push(chart.get(item.id));

								chart.remove(item.id);
							});

							await deleteFolderTemplateBatch(liferayNodes);

							chart.draw(OrgChart.action.init);

							setOrgChart((prev) =>
								prev === chart ? prev : chart
							);
						};

						if (chart) {
							if (
								nodeId.toString() ===
								chart.roots[0].id.toString()
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
						if (chart) {
							const selectedNode = chart.get(node);

							selectedNode.templateID = templateId;

							const data = {
								chart,

								description: selectedNode.description,

								name: selectedNode.name,

								nodeId: node,

								parentID: selectedNode.pid,

								root: selectedNode.root,

								templateID: templateId,
							};

							editNode(data);

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
		chart.on('drop', (sender, draggedNodeId, droppedNodeId) => {
			if (draggedNodeId.toString() === '1') {
				return false;
			}
			const node = chart.get(draggedNodeId);

			const newParentNode = chart.get(droppedNodeId);

			const liferayNode = {
				description: node.description,

				id: node.id,

				name: node.name,

				parentID: newParentNode.id,

				root: node.root,

				templateID: node.templateID,
			};

			updateFolderTemplate(node.id, liferayNode);

			updateChartNode(chart, liferayNode, false);

			return false;
		});

		setOrgChart((prev) => (prev ? prev : chart));

		loadTemplateNodes(chart);
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
			{isLoading && <Spin fullscreen></Spin>}
			<div
				id="orgChartContainer"
				ref={myElementRef}
				style={{height: '100%', width: '100%'}}
			></div>
		</>
	);
};

export default FolderStructureDesigner;
