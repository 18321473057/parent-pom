package org.line.core.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: yangcs
 * @Date: 2022/5/14 9:07
 * @Description: // 默认负载均衡策略  -- 轮训
 */
public class ILineRule extends AbstractLoadBalancerRule {

    @Override
    public Server choose(Object o) {
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        List<Server> reachableServers = loadBalancer.getReachableServers();
        int nextInt = ThreadLocalRandom.current().nextInt(reachableServers.size());
        Server server = reachableServers.get(nextInt);
        while (!server.isAlive()) {
            nextInt = ThreadLocalRandom.current().nextInt(reachableServers.size());
            server = reachableServers.get(nextInt);
        }
        return server;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        System.out.println(".............................................");
    }
}
