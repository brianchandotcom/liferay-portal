/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import dagre from 'dagre';
import React, {useCallback, useEffect, useState} from 'react';
import ReactFlow, {
	Background,
	ConnectionLineType,
	Controls,
	Panel,
	ReactFlowProvider,
	useEdgesState,
	useNodesState,
} from 'reactflow';

import 'reactflow/dist/style.css';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {Form, Input} from 'antd';

import {
	addFolderTemplateNode,
	deleteFolderTemplateNodesBatch,
	getAvailableTemplatesNodesPage,
	updateFolderTemplateNode,
} from '../../services/TemplateDiagramService';
import {showError} from '../../utils/util';
import FolderNode from './FolderNode';

const EDGE_TYPE = 'SmoothStep';

const NODE_WIDTH = 172;

const NODE_HEIGHT = 36;

const POSITION = {x: 0, y: 0};

const dagreGraph = new dagre.graphlib.Graph();

dagreGraph.setDefaultEdgeLabel(() => ({}));

const deleteNodes = async (nodeIds) => {
	await deleteFolderTemplateNodesBatch(
		nodeIds.map((nodeId) => {
			return {
				id: nodeId,
			};
		})
	);
};

const getEdge = (nodeId, parentId) => {
	return {
		animated: false,
		id: `edge${nodeId}${parentId}`,
		source: parentId.toString(),
		target: nodeId.toString(),
		type: EDGE_TYPE,
	};
};

const getParentNodeId = (node) => {
	if (node.root) {
		return null;
	}

	if (node.pid) {
		return node.pid.toString();
	}

	if (node.parentId) {
		return node.parentId.toString();
	}

	return '';
};

const getLayoutedElements = (nodes, edges) => {
	dagreGraph.setGraph({
		rankdir: 'TB',
	});

	nodes.forEach((node) => {
		dagreGraph.setNode(node.id, {height: NODE_HEIGHT, width: NODE_WIDTH});
	});

	edges.forEach((edge) => {
		dagreGraph.setEdge(edge.source, edge.target);
	});

	dagre.layout(dagreGraph);

	const updatedNodes = nodes.map((node) => {
		const nodeWithPosition = dagreGraph.node(node.id);

		return {
			...node,
			draggable: false,
			position: {
				x: nodeWithPosition.x - NODE_WIDTH / 2,
				y: nodeWithPosition.y - NODE_HEIGHT / 2,
			},
			sourcePosition: 'bottom',
			targetPosition: 'top',
		};
	});

	return {layoutedEdges: edges, layoutedNodes: updatedNodes};
};

const getChildNodeIds = (nodeId, subNodeIds = []) => {
	subNodeIds.push(nodeId);

	const childNodeIds = dagreGraph.successors(nodeId);

	if (childNodeIds && childNodeIds.length) {
		childNodeIds.forEach((nodeId) => {
			getChildNodeIds(nodeId, subNodeIds);
		});
	}

	return subNodeIds;
};

const normalizeNode = (node) => {
	return {
		POSITION,
		data: {
			description: node.description,
			label: node.name,
			nodeId: node.id.toString(),
			parent: getParentNodeId(node),
			root: node.root,
		},
		deletable: !node.root,
		id: node.id.toString(),
		parent: getParentNodeId(node),
		type: 'folderNode',
	};
};

const Diagram = ({templateId}) => {
	const [edges, setEdges] = useEdgesState(null);
	const [nodes, setNodes] = useNodesState(null);

	const [isLoading, setIsLoading] = useState(false);
	const [selectedNode, setSelectedNode] = useState();

	const [form] = Form.useForm();

	const handleNodeSelect = (node) => {
		form.setFieldsValue({
			description: node.description,
			id: node.nodeId,
			name: node.label,
			parentId: node.root ? 0 : node.parent,
			root: node.root,
			templateId,
		});

		setSelectedNode(node);
	};

	const updateDiagramDataSourceLocally = useCallback(
		(currentNodes, currentEdges, idsToExclude) => {
			const filteredNodes = currentNodes.filter(
				(node) => !idsToExclude.includes(node.id)
			);

			const filteredEdges = currentEdges.filter(
				(edge) => !idsToExclude.includes(edge.source)
			);

			const {layoutedEdges, layoutedNodes} = getLayoutedElements(
				filteredNodes,
				filteredEdges
			);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[setNodes, setEdges]
	);

	const updateNode = useCallback(
		(updatedNode) => {
			const selectedDiagramNode = nodes.find(
				(node) => node.id.toString() === updatedNode.id
			);

			if (selectedDiagramNode) {
				selectedDiagramNode.data.description = updatedNode.description;
				selectedDiagramNode.data.label = updatedNode.name;
			}
		},
		[nodes]
	);

	const handleAdd = useCallback(
		async (parentNodeId) => {
			const newNode = await addFolderTemplateNode(
				parentNodeId,
				false,
				'New Node',
				templateId
			);

			const newDiagramNode = normalizeNode(newNode);

			const newDiagramEdge = getEdge(newDiagramNode.id, parentNodeId);

			nodes.push(newDiagramNode);

			edges.push(newDiagramEdge);

			const {layoutedEdges, layoutedNodes} = getLayoutedElements(
				nodes,
				edges
			);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[nodes, edges, setNodes, setEdges, templateId]
	);

	const handleDelete = useCallback(
		(nodeId) => {
			try {
				const nodeIds = getChildNodeIds(nodeId || selectedNode.id);

				updateDiagramDataSourceLocally(nodes, edges, nodeIds);

				deleteNodes(nodeIds);

				setSelectedNode(null);
			}
			catch (error) {
				showError('Error', error.message);
			}
		},
		[nodes, edges, updateDiagramDataSourceLocally, selectedNode]
	);

	const loadNodes = useCallback(
		async (templateId) => {
			const templateNodesPage =
				await getAvailableTemplatesNodesPage(templateId);

			const templateNodes = templateNodesPage.items;

			if (!templateNodes.length) {
				const rootNode = await addFolderTemplateNode(
					0,
					true,
					'Root Node',
					templateId
				);

				templateNodes.push(rootNode);
			}

			const templateEdges = [];

			const normalizedNodes = templateNodes.map((node) => {
				if (!node.root) {
					const edge = getEdge(node.id, node.parentId);

					templateEdges.push(edge);
				}

				return normalizeNode(node);
			});

			const {layoutedEdges, layoutedNodes} = getLayoutedElements(
				normalizedNodes,
				templateEdges
			);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[setEdges, setNodes]
	);

	useEffect(() => {
		loadNodes(templateId);
	}, [loadNodes, templateId]);

	const handleNodeSave = () => {
		form.validateFields()
			.then(
				async (values) => {
					try {
						setIsLoading(true);

						await updateFolderTemplateNode(values.id, values);

						setIsLoading(false);

						updateNode(values);
					}
					catch (error) {
						setIsLoading(false);

						showError('Error', error.message);
					}
				},
				(error) => {
					showError('Error', error.message);
				}
			)
			.catch((error) => {
				showError('Error', error.message);
			});
	};

	return (
		<ReactFlowProvider>
			{nodes && edges && (
				<ReactFlow
					connectionLineType={ConnectionLineType.SmoothStep}
					edges={edges}
					fitView
					nodeTypes={{
						folderNode: (props) => (
							<FolderNode
								{...props}
								nodeWidth={NODE_WIDTH}
								onAdd={handleAdd}
								onDelete={handleDelete}
								onSelect={handleNodeSelect}
							/>
						),
					}}
					nodes={nodes}
					onConnect={null}
					onNodesDelete={(nodes) => handleDelete(nodes[0].id)}
					onPaneClick={() => setSelectedNode(null)}
				>
					<Controls />

					<Background className="bg-light" />

					{selectedNode && (
						<Panel
							className="h-100 m-0 side-panel w-25"
							position="top-right"
						>
							<div className="sidebar sidebar-light">
								<div className="sidebar-header">
									<div className="autofit-row sidebar-section">
										<div className="autofit-col autofit-col-expand">
											<div className="component-title mb-auto mt-auto">
												<span className="text-truncate-inline">
													{selectedNode.label}
												</span>
											</div>
										</div>
										<div className="autofit-col">
											<ClayButtonWithIcon
												aria-label="Close"
												displayType="unstyled"
												onClick={() =>
													setSelectedNode(null)
												}
												symbol="times"
												title="Close"
											/>
										</div>
									</div>
								</div>
								<div className="sidebar-body">
									<Form
										autoComplete="off"
										form={form}
										layout="vertical"
									>
										<Form.Item
											hidden={true}
											initialValue={selectedNode.id}
											name="id"
											rules={[
												{
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={selectedNode.parent}
											name="parentId"
											rules={[
												{
													message:
														'Please provide a node parent id.',
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={selectedNode.root}
											name="root"
											rules={[
												{
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={templateId}
											name="templateId"
											rules={[
												{
													message:
														'Please provide a template id.',
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>

										<Form.Item
											initialValue={selectedNode.label}
											label="Title"
											name="name"
											rules={[
												{
													message:
														'Please provide a title.',
													required: true,
												},
											]}
										>
											<ClayInput />
										</Form.Item>
										<Form.Item
											initialValue={
												selectedNode.description
											}
											label="Description"
											name="description"
										>
											<ClayInput
												component="textarea"
												type="text"
											/>
										</Form.Item>
									</Form>
								</div>
								<div className="d-flex justify-content-between sidebar-footer">
									{!selectedNode.root && (
										<ClayButton
											disabled={isLoading}
											displayType="danger"
											onClick={() => {
												handleDelete(
													selectedNode.nodeId
												);
											}}
										>
											Delete
										</ClayButton>
									)}

									<ClayButton
										disabled={isLoading}
										onClick={handleNodeSave}
									>
										Save
									</ClayButton>
								</div>
							</div>
						</Panel>
					)}
				</ReactFlow>
			)}
		</ReactFlowProvider>
	);
};

export default Diagram;
