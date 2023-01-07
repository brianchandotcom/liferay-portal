/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
import dom from 'metal-dom';

class ClassToggle {
	constructor() {
		this.activateCallback = null;
		this.baseClassName = 'class-toggle';
		this.deactivateCallback = null;
		this.item = '.class-toggle';
		this.offclickContent = null;
		this.targetClass = null;
		this.targetNodes = null;
		this.toggleType = null;
		this.trigger = null;

		this.syncUI();
	}

	syncUI = function () {
		var instance = this;

		var wrapper = document.getElementById('wrapper');

		if (!wrapper) {
			wrapper = document.querySelector('body');
		}

		var trigger = this.trigger;

		if (!trigger) {
			trigger = this.item;
		}

		dom.delegate(wrapper, 'click', trigger, (event) => {
			var node = event.delegateTarget;

			if (!node) {
				node = event.currentTarget;
			}

			var targetClass = instance._getTargetClass(node);
			var targetNodes = instance._getTargetNodes(node);

			var active = false;

			if (dom.hasClass(targetNodes[0], targetClass)) {
				active = true;
			}

			var toggleType = this.toggleType;

			if (node.getAttribute('data-toggle-type')) {
				toggleType = node.getAttribute('data-toggle-type');
			}

			if (toggleType == 'offclick') {
				if (active) {
					return;
				}

				instance._activate(node, targetNodes, targetClass);

				instance._offclickAction(node, targetNodes, targetClass, event);
			}
			else if (toggleType == 'carousel') {
				if (active) {
					return;
				}

				instance._carouselAction(node, targetNodes, targetClass);

				instance._activate(node, targetNodes, targetClass);
			}
			else if (active) {
				instance._deactivate(node, targetNodes, targetClass);
			}
			else {
				instance._activate(node, targetNodes, targetClass);
			}
		});
	};

	_activate = function (node, targetNodes, targetClass) {
		var instance = this;

		dom.addClasses(targetNodes, targetClass);

		var activateCallback = this.activateCallback;

		if (activateCallback instanceof Function) {
			activateCallback(instance, node, targetNodes, targetClass);
		}
	};

	_carouselAction = function (node, targetNodes) {
		var instance = this;

		var curActiveNode = targetNodes[0]._activeNode;

		if (curActiveNode && curActiveNode != node) {
			instance._deactivate(
				curActiveNode,
				instance._getTargetNodes(curActiveNode),
				instance._getTargetClass(curActiveNode)
			);
		}

		targetNodes[0]._activeNode = node;
	};

	_deactivate = function (node, targetNodes, targetClass) {
		var instance = this;

		dom.removeClasses(targetNodes, targetClass);

		var deactivateCallback = this.deactivateCallback;

		if (deactivateCallback instanceof Function) {
			deactivateCallback(instance, node, targetNodes, targetClass);
		}
	};

	_getTargetClass = function (node) {
		var className = this.baseClassName + '-active';

		if (node.getAttribute('data-target-class')) {
			className = node.getAttribute('data-target-class');
		}
		else if (this.targetClass) {
			className = this.targetClass;
		}

		return className;
	};

	_getTargetNodes = function (node) {
		let nodes;

		if (node.getAttribute('data-target-nodes')) {
			nodes = document.querySelectorAll(
				node.getAttribute('data-target-nodes')
			);
		}
		else if (this.targetNodes) {
			nodes = document.querySelectorAll(this.targetNodes);
		}

		if (!nodes || nodes.length == 0) {
			return [node];
		}

		return nodes;
	};

	_offclickAction = function (node, targetNodes, targetClass, event) {
		var instance = this;

		event.stopPropagation();

		let nodeContent;

		if (node.getAttribute('data-offclick-content')) {
			nodeContent = document.querySelectorAll(
				node.getAttribute('data-offclick-content')
			);
		}
		else if (node.getAttribute('data-target-nodes')) {
			var offclickContent =
				node.getAttribute('data-target-nodes') +
				' .' +
				this.baseClassName +
				'-content';

			nodeContent = document.querySelectorAll(offclickContent);
		}
		else if (this.offclickContent) {
			nodeContent = document.querySelectorAll(this.offclickContent);
		}
		else {
			nodeContent = node.querySelectorAll(
				'.' + this.baseClassName + '-content'
			);
		}

		if (!nodeContent || nodeContent.length == 0) {
			nodeContent = [node];
		}

		function handleOffclick(event) {
			for (var i = 0; i < nodeContent.length; i++) {
				if (!nodeContent[i].contains(event.target)) {
					instance._deactivate(node, targetNodes, targetClass);
					document.removeEventListener('click', handleOffclick);
				}
			}
		}
		document.addEventListener('click', handleOffclick);
	};
}
export default ClassToggle;
