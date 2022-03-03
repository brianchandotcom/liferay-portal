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

package com.liferay.util.transport;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * <p>
 * A server that will send out heart beat messages until you kill it. This
 * enables you to try and debug multicast issues.
 * </p>
 *
 * @author Michael C. Han
 */
public class MulticastServerTool {

	public static void main(String[] args) {
		try {
			String multicastAddress = args[0];
			int port = GetterUtil.getInteger(args[1]);
			long interval = GetterUtil.getLong(args[2]);

			String bindAddress = null;

			if (args.length > 3) {
				bindAddress = args[3];
			}

			DatagramHandler handler = new DatagramHandler() {

				@Override
				public void errorReceived(Throwable throwable) {
					throwable.printStackTrace();
				}

				@Override
				public void process(DatagramPacket packet) {
					String s = new String(
						packet.getData(), 0, packet.getLength());

					System.out.println(s);
				}

			};

			MulticastTransport transport = new MulticastTransport(
				handler, multicastAddress, port, bindAddress);

			transport.connect();

			InetAddress inetAddress = InetAddress.getLocalHost();

			String msg = StringBundler.concat(
				inetAddress.getHostName(), ":", port, " heartbeat ");

			int i = 0;

			while (true) {
				transport.sendMessage(msg + i);

				i++;

				Thread.sleep(interval);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			System.err.println(
				"Usage: java MulticastServerTool multicastAddress port " +
					"interval bindAddress");

			System.exit(1);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MulticastServerTool.class);

}