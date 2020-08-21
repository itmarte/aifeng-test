package com.aifeng.highAva.sentinel;

import com.alibaba.csp.sentinel.node.Node;
import com.alibaba.csp.sentinel.slots.block.flow.TrafficShapingController;

/**
 * @Description:
 * @author: imart·deng
 * @date: 2020/7/13 10:50
 */
public class RateLimiterController implements TrafficShapingController {
    @Override
    public boolean canPass(Node node, int i, boolean b) {
        return false;
    }

    @Override
    public boolean canPass(Node node, int i) {
        return false;
    }
}
