package org.jboss.messaging.core.client.impl;

import org.jboss.messaging.core.client.Location;
import org.jboss.messaging.core.client.RemotingSessionListener;
import org.jboss.messaging.core.exception.MessagingException;
import org.jboss.messaging.core.remoting.Packet;
import org.jboss.messaging.core.remoting.PacketDispatcher;

/**
 * 
 * A RemotingConnection
 * 
 * @author <a href="mailto:tim.fox@jboss.com">Tim Fox</a>
 *
 */
public interface RemotingConnection
{
	public void start() throws Throwable;

   public void stop();
   
   public long getSessionID();
 
   Packet sendBlocking(long targetID, long executorID, Packet packet) throws MessagingException;
   
   void sendOneWay(long targetID, long executorID, Packet packet) throws MessagingException;
   
   void setRemotingSessionListener(RemotingSessionListener newListener);
   
   PacketDispatcher getPacketDispatcher();
   
   public Location getLocation();
}
