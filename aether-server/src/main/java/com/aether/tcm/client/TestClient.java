package com.aether.tcm.client;

import com.aether.tcm.model.HerbComponent;
import com.aether.tcm.service.SimulationService;
import com.aether.tcm.grpc.SimulationResponse;
import java.util.Arrays;
import java.util.List;

public class TestClient {
    public static void main(String[] args) {
        SimulationService service = new SimulationService("localhost", 50051);

        // 模拟从数据库获取的一组方剂成分：人参皂苷、黄芩苷、青蒿素
        // 修正后的数据列表（注意反斜杠的转义）
        List<HerbComponent> prescription = Arrays.asList(
                new HerbComponent("人参皂苷Rg1", "CC(C)=CCCC(C)(O)C1CCC2(C)C1C(O)CC3C4(C)CCC(O)C(C)(C)C4CC(O)C32C"),
                new HerbComponent("黄芩苷", "C21H18O11"),
                new HerbComponent("青蒿素", "CC12CCC3C(C1OOC24C(CCC3C4C)C)C")
        );

        prescription.forEach(comp -> {
            SimulationResponse res = service.run(comp);
            System.out.printf("[%s] 推演结果 -> 能量: %.4f | 风险: %s\n",
                    comp.getName(), res.getBindingEnergy(), res.getToxicityRisk());
        });

        service.close();
    }
}