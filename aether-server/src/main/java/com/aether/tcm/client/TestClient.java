package com.aether.tcm.client;

import com.aether.tcm.dao.HerbDao;
import com.aether.tcm.model.HerbComponent;
import com.aether.tcm.service.SimulationService;
import com.aether.tcm.grpc.SimulationResponse;
import java.util.List;

public class TestClient {
    public static void main(String[] args) {
        // 1. 初始化 gRPC 通讯服务（连接本地 Python 引擎）
        SimulationService service = new SimulationService("127.0.0.1", 50051);

        // 2. 初始化数据库访问对象
        HerbDao dao = new HerbDao();

        System.out.println(">>> [Aether] 正在从 MySQL 加载成分数据...");

        // 3. 从数据库捞数据
        List<HerbComponent> dbComponents = dao.findAll();

        if (dbComponents.isEmpty()) {
            System.err.println(">>> [ERROR] 数据库为空，推演停止。请确认已执行 SQL 插入语句！");
            return;
        }

        System.out.printf(">>> [Aether] 成功加载 %d 种成分，开始批量推演...\n", dbComponents.size());

        // 4. 循环发起推演请求
        for (HerbComponent comp : dbComponents) {
            try {
                SimulationResponse res = service.run(comp);
                System.out.println("----------------------------------------");
                System.out.printf("【推演成功】成分名称: %s\n", comp.getName());
                System.out.printf("【药材分子】: %s\n", res.getMolecularFormula());
                System.out.printf("【系统能量】: %.4f kcal/mol\n", res.getBindingEnergy());
                System.out.printf("【风险评估】: %s\n", res.getToxicityRisk() ? "高风险 (LogP > 5)" : "安全");
            } catch (Exception e) {
                System.err.println(">>> 推演异常: " + comp.getName());
                e.printStackTrace();
            }
        }

        // 5. 优雅关闭
        service.close();
        System.out.println("\n>>> [Aether] 批量任务执行完毕，连接已释放。");
    }
}