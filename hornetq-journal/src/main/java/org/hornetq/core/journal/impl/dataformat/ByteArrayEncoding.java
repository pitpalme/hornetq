/*
 * Copyright 2005-2014 Red Hat, Inc.
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.hornetq.core.journal.impl.dataformat;

import org.hornetq.api.core.HornetQBuffer;
import org.hornetq.core.journal.EncodingSupport;

/**
 * A ByteArrayEncoding
 *
 * @author <mailto:clebert.suconic@jboss.org">Clebert Suconic</a>
 *
 *
 */
public class ByteArrayEncoding implements EncodingSupport
{

   final byte[] data;

   public ByteArrayEncoding(final byte[] data)
   {
      this.data = data;
   }

   // Public --------------------------------------------------------

   public void decode(final HornetQBuffer buffer)
   {
      throw new IllegalStateException("operation not supported");
   }

   public void encode(final HornetQBuffer buffer)
   {
      buffer.writeBytes(data);
   }

   public int getEncodeSize()
   {
      return data.length;
   }
}
