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
	useEdgesState,
	useNodesState,
} from 'reactflow';

import 'reactflow/dist/style.css';
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {Form, Input} from 'antd';

import {
	addNode,
	deleteFolderTemplateBatch,
	getAvailableTemplatesNodesPage,
	updateFolderTemplate,
} from '../../services/TemplateDiagramService';
import {showError} from '../../utils/util';
import FolderNode from './controls/custom-node/FolderNode';

import './Diagram.css';

const DAGRE_GRAPH = new dagre.graphlib.Graph();

DAGRE_GRAPH.setDefaultEdgeLabel(() => ({}));

const NODE_WIDTH = 172;

const NODE_HEIGHT = 36;

const POSITION = {x: 0, y: 0};

const EDGE_TYPE = 'SmoothStep';

const normalizeNode = (node) => {
	return {
		POSITION,
		data: {
			description: node.description,
			label: node.name,
			nodeId: `${node.id}`,
			parent: node.root ? null : `${node.parentId}`,
			root: node.root,
		},
		deletable: !node.root,
		id: `${node.id}`,
		parent: node.root ? null : `${node.parentId}`,
		type: 'folderNode',
	};
};

const getEdge = (nodeId, parentId) => {
	return {
		animated: false,
		id: `e${nodeId}${parentId}`,
		source: `${parentId}`,
		target: `${nodeId}`,
		type: EDGE_TYPE,
	};
};

const getLayoutedElements = (nodes, edges) => {
	DAGRE_GRAPH.setGraph({
		rankdir: 'TB',
	});

	nodes.forEach((node) => {
		DAGRE_GRAPH.setNode(node.id, {height: NODE_HEIGHT, width: NODE_WIDTH});
	});

	edges.forEach((edge) => {
		DAGRE_GRAPH.setEdge(edge.source, edge.target);
	});

	dagre.layout(DAGRE_GRAPH);

	const updatedNodes = nodes.map((node) => {
		const nodeWithPosition = DAGRE_GRAPH.node(node.id);

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

	return {edges, updatedNodes};
};

const getChildNodeIds = (nodeId, subNodes = []) => {
	subNodes.push(nodeId);

	const childrenNodes = DAGRE_GRAPH.successors(nodeId);

	if (childrenNodes && childrenNodes.length) {
		childrenNodes.forEach((node) => {
			getChildNodeIds(node, subNodes);
		});
	}

	return subNodes;
};

const deleteNodes = async (nodeIds) => {
	await deleteFolderTemplateBatch(
		nodeIds.map((nodeId) => {
			return {
				id: nodeId,
			};
		})
	);
};

const Diagram = ({key, templateId}) => {
	const [nodes, setNodes] = useNodesState(null);

	const [edges, setEdges] = useEdgesState(null);

	const [selectedNode, setSelectedNode] = useState();

	const [isLoading, setIsLoading] = useState(false);

	const [form] = Form.useForm();

	const handleNodeSelect = (data) => {
		form.setFieldsValue({
			description: data.description,
			id: data.nodeId,
			name: data.label,
			parentId: data.parent,
			root: data.root,
			templateId,
		});
		setSelectedNode(data);
	};

	const handlePaneClick = () => {
		setSelectedNode(null);
	};

	const updateDiagramDataSourceLocally = useCallback(
		(currentNodes, currentEdges, idsToExclude) => {
			const filteredNodes = currentNodes.filter(
				(node) => !idsToExclude.includes(node.id)
			);

			const filteredEdges = currentEdges.filter(
				(edge) => !idsToExclude.includes(edge.source)
			);

			const {
				edges: layoutedEdges,
				updatedNodes: layoutedNodes,
			} = getLayoutedElements(filteredNodes, filteredEdges);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[setNodes, setEdges]
	);

	const updateDiagramSingleNodeLocally = useCallback(
		(updatedNode) => {
			const selectedDiagramNode = nodes.filter(
				(node) => node.id.toString() === updatedNode.id
			);

			if (selectedDiagramNode && !!selectedDiagramNode.length) {
				selectedDiagramNode[0].data.description =
					updatedNode.description;
				selectedDiagramNode[0].data.label = updatedNode.name;
			}
		},
		[nodes]
	);

	const handleAdd = useCallback(
		async (parentNodeId) => {
			const newNode = await addNode(
				parentNodeId,
				false,
				'New Node',
				templateId
			);

			const newDiagramNode = normalizeNode(newNode);

			const newDiagramEdge = getEdge(newDiagramNode.id, parentNodeId);

			nodes.push(newDiagramNode);

			edges.push(newDiagramEdge);

			const {
				edges: layoutedEdges,
				updatedNodes: layoutedNodes,
			} = getLayoutedElements(nodes, edges);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[nodes, edges, setNodes, setEdges, templateId]
	);

	const handleDelete = useCallback(
		(params) => {
			try {
				const nodeId = params[0].id || params || selectedNode.id;

				const nodesToBeDeleted = getChildNodeIds(nodeId);

				updateDiagramDataSourceLocally(nodes, edges, nodesToBeDeleted);

				deleteNodes(nodesToBeDeleted);

				setSelectedNode(null);
			}
			catch (error) {
				showError(error.message);
			}
		},
		[nodes, edges, updateDiagramDataSourceLocally, selectedNode]
	);

	const loadNodes = useCallback(
		async (templateId) => {
			const templateNodesPage = await getAvailableTemplatesNodesPage(
				templateId
			);

			const templateNodes = templateNodesPage.items;

			if (!templateNodes.length) {
				const rootNode = await addNode(
					0,
					true,
					'Root Node',
					templateId
				);

				templateNodes.push(rootNode);
			}

			const templateEdges = [];

			const normalizedNodes = templateNodes.map((node) => {
				if (node.parentId !== 0) {
					const edge = getEdge(node.id, node.parentId);

					templateEdges.push(edge);
				}

				return normalizeNode(node);
			});

			const {
				edges: layoutedEdges,
				updatedNodes: layoutedNodes,
			} = getLayoutedElements(normalizedNodes, templateEdges);

			setNodes([...layoutedNodes]);

			setEdges([...layoutedEdges]);
		},
		[setEdges, setNodes]
	);

	useEffect(() => {
		loadNodes(templateId);
	}, [loadNodes, templateId]);

	const handleSave = () => {
		form.validateFields()
			.then(
				async (values) => {
					try {
						setIsLoading(true);

						await updateFolderTemplate(values.id, values);

						setIsLoading(false);

						updateDiagramSingleNodeLocally(values);
					}
					catch (error) {
						setIsLoading(false);

						showError(error.message);
					}
				},
				(error) => {
					showError(error.message);
				}
			)
			.catch((error) => {
				showError(error.message);
			});
	};

	return (
		<>
			{nodes && edges && (
				<ReactFlow
					connectionLineType={ConnectionLineType.SmoothStep}
					edges={edges}
					fitView
					key={key}
					nodeTypes={{
						folderNode: (props) => (
							<FolderNode
								{...props}
								onAdd={handleAdd}
								onDelete={handleDelete}
								onSelect={handleNodeSelect}
							/>
						),
					}}
					nodes={nodes}
					onConnect={null}
					onNodesDelete={handleDelete}
					onPaneClick={handlePaneClick}
				>
					<Controls />
					<Background className="background" />
					{selectedNode && (
						<Panel className="side-panel" position="top-right">
							<div className="sidebar">
								<div className="border-bottom sidebar-header">
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
												onClick={() => {
													handlePaneClick(null);
												}}
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
											initialValue={selectedNode.label}
											label="Title"
											name="name"
											rules={[
												{
													message:
														'Please provide node name.',
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
										<Form.Item
											hidden={true}
											initialValue={selectedNode.parent}
											label="parentId"
											name="parentId"
											rules={[
												{
													message:
														'Please provide node parent id.',
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
										<Form.Item
											hidden={true}
											initialValue={templateId}
											label="templateId"
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
											hidden={true}
											initialValue={selectedNode.root}
											label="root"
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
											initialValue={selectedNode.id}
											label="id"
											name="id"
											rules={[
												{
													required: true,
												},
											]}
										>
											<Input />
										</Form.Item>
									</Form>
								</div>
								<div className="actions fixed-bottom sidebar-footer">
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
										onClick={() => {
											handleSave();
										}}
									>
										Save
									</ClayButton>
								</div>
							</div>
						</Panel>
					)}
				</ReactFlow>
			)}
		</>
	);
};

export default Diagram;
