/*
 * Copyright 2017, OpenSkywalking Organization All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project repository: https://github.com/OpenSkywalking/skywalking
 */

package org.skywalking.apm.agent.core.context.ids;

import org.skywalking.apm.network.proto.UniqueId;

/**
 * 分布式链路追踪编号
 *
 * The <code>DistributedTraceId</code> presents a distributed call chain.
 * <p>
 * This call chain has an unique (service) entrance,
 * <p>
 * such as: Service : http://www.skywalking.com/cust/query, all the remote, called behind this service, rest remote,
 * db executions, are using the same <code>DistributedTraceId</code> even in different JVM.
 * <p>
 * The <code>DistributedTraceId</code> contains only one string, and can NOT be reset, creating a new instance is the
 * only option.
 *
 * @author wusheng
 */
public abstract class DistributedTraceId {

    /**
     * 编号
     */
    private ID id;

    public DistributedTraceId(ID id) {
        this.id = id;
    }

    public DistributedTraceId(String id) {
        this.id = new ID(id);
    }

    /**
     * @return 编码编号( id )
     */
    public String encode() {
        return id.encode();
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public UniqueId toUniqueId() {
        return id.transform();
    }

    /**
     * Compare the two <code>DistributedTraceId</code> by its {@link #id},
     * even these two <code>DistributedTraceId</code>s are not the same instances.
     *
     * @param o target <code>DistributedTraceId</code>
     * @return return if they have the same {@link #id}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DistributedTraceId id1 = (DistributedTraceId)o;

        return id != null ? id.equals(id1.id) : id1.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
