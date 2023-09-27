/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.transport;

import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * The MulticastTransport will send strings across a specified multicast
 * address. It will also listen for messages and hand them to the appropriate
 * DatagramHandler.
 * </p>
 *
 * @author Michael C. Han
 */
public class MulticastTransport extends Thread implements Transport {

	public MulticastTransport(
		DatagramHandler handler, String multicastAddress, int port,
		String bindAddress) {

		super("MulticastListener-" + multicastAddress + port);

		setDaemon(true);
		_handler = handler;
		_multicastAddress = multicastAddress;
		_port = port;
		_bindAddress = bindAddress;
	}

	@Override
	public synchronized void connect() throws IOException {
		if (_socket == null) {
			_socket = new MulticastSocket(_port);
		}
		else if (_socket.isConnected() && _socket.isBound()) {
			return;
		}

		_address = InetAddress.getByName(_multicastAddress);

		_socket.joinGroup(_address);

		if (Validator.isNotNull(_bindAddress)) {
			_socket.setNetworkInterface(
				NetworkInterface.getByInetAddress(
					InetAddress.getByName(_bindAddress)));
		}

		_connected = true;

		start();
	}

	@Override
	public synchronized void disconnect() {

		// Interrupt all processing

		if (_address != null) {
			try {
				_socket.leaveGroup(_address);
				_address = null;
			}
			catch (IOException ioException) {
				_log.error("Unable to leave group", ioException);
			}
		}

		_connected = false;

		interrupt();

		_socket.close();
	}

	@Override
	public boolean isConnected() {
		return _connected;
	}

	@Override
	public void run() {
		try {
			while (_connected) {
				_socket.receive(_inboundPacket);
				_handler.process(_inboundPacket);
			}
		}
		catch (IOException ioException) {
			if (!_connected) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to disconnect", ioException);
				}

				return;
			}

			_log.error("Unable to process ", ioException);

			_socket.disconnect();

			_connected = false;

			_handler.errorReceived(ioException);
		}
	}

	public synchronized void sendMessage(byte[] bytes) throws IOException {
		_outboundPacket.setData(bytes);
		_outboundPacket.setAddress(_address);
		_outboundPacket.setPort(_port);

		_socket.send(_outboundPacket);
	}

	@Override
	public synchronized void sendMessage(String message) throws IOException {
		sendMessage(message.getBytes());
	}

	private static final Log _log = LogFactory.getLog(MulticastTransport.class);

	private InetAddress _address;
	private final String _bindAddress;
	private boolean _connected;
	private final DatagramHandler _handler;
	private final byte[] _inboundBuffer = new byte[4096];
	private final DatagramPacket _inboundPacket = new DatagramPacket(
		_inboundBuffer, _inboundBuffer.length);
	private final String _multicastAddress;
	private final byte[] _outboundBuffer = new byte[4096];
	private final DatagramPacket _outboundPacket = new DatagramPacket(
		_outboundBuffer, _outboundBuffer.length);
	private final int _port;
	private MulticastSocket _socket;

}