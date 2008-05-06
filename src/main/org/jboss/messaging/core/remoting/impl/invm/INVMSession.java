/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.messaging.core.remoting.impl.invm;

import java.util.concurrent.TimeUnit;

import org.jboss.messaging.core.logging.Logger;
import org.jboss.messaging.core.remoting.NIOSession;
import org.jboss.messaging.core.remoting.Packet;
import org.jboss.messaging.core.remoting.PacketDispatcher;
import org.jboss.messaging.core.remoting.PacketSender;

/**
 * @author <a href="mailto:jmesnil@redhat.com">Jeff Mesnil</a>
 * 
 * @version <tt>$Revision$</tt>
 * 
 */
public class INVMSession implements NIOSession
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   private final long id;
   private long correlationCounter;
   private final PacketDispatcher clientDispatcher;
   private final PacketDispatcher serverDispatcher;
   private boolean connected;
   
   // Static --------------------------------------------------------
   private static final Logger log = Logger.getLogger(INVMSession.class);

   // Constructors --------------------------------------------------

   public INVMSession(final long id, final PacketDispatcher clientDispatcher, final PacketDispatcher serverDispatcher)
   {
      assert clientDispatcher != null;
      assert serverDispatcher != null;
      
      this.id = id;
      this.correlationCounter = 0;
      this.clientDispatcher = clientDispatcher;
      this.serverDispatcher = serverDispatcher;
      connected = true;
   }

   // Public --------------------------------------------------------

   public boolean close()
   {
      connected = false;
      return true;
   }

   // NIOSession implementation -------------------------------------

   public long getID()
   {
      return id;
   }

   public boolean isConnected()
   {
      return connected;
   }

   public void write(final Packet packet) throws Exception
   {
     // assert packet instanceof Packet;

      serverDispatcher.dispatch((Packet) packet,
            new PacketSender()
            {
               public void send(Packet response) throws Exception
               {                  
                  serverDispatcher.callFilters(response);
                  clientDispatcher.dispatch(response, null);   
               }
               
               public long getSessionID()
               {
                  return getID();
               }
               
               public String getRemoteAddress()
               {
                  return "invm";
               }
            });
   }

//   public Object writeAndBlock(final Packet request, long timeout, TimeUnit timeUnit) throws Exception
//   {
//      request.setCorrelationID(correlationCounter++);
//      final Packet[] responses = new Packet[1];
//
//      serverDispatcher.dispatch(request,
//            new PacketSender()
//            {
//               public void send(Packet response)
//               {
//                  try
//                  {
//                     serverDispatcher.callFilters(response);
//                     // 1st response is used to reply to the blocking request
//                     if (responses[0] == null)
//                     {
//                        responses[0] = response;
//                     } else 
//                     // other later responses are dispatched directly to the client
//                     {
//                        clientDispatcher.dispatch(response, null);
//                     }
//                  }
//                  catch (Exception e)
//                  {
//                     log.warn("An interceptor throwed an exception what caused the packet " + response + " to be ignored", e);
//                     responses[0] = null;
//                  }
//               }
//
//               public long getSessionID()
//               {
//                  return getID();
//               }
//               
//               public String getRemoteAddress()
//               {
//                  return "invm";
//               }
//            });
//
//      if (responses[0] == null)
//      {
//         throw new IllegalStateException("No response received for request " + request);
//      }
//
//      return responses[0];
//   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------
}
