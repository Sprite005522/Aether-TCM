package com.aether.tcm.dao;

import com.aether.tcm.model.HerbComponent;
import com.aether.tcm.util.DbUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HerbDao {
    /**
     * 从数据库加载所有待推演的成分
     */
    public List<HerbComponent> findAll() {
        List<HerbComponent> list = new ArrayList<>();
        String sql = "SELECT component_name, smiles_code FROM tcm_molecules";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new HerbComponent(
                        rs.getString("component_name"),
                        rs.getString("smiles_code")
                ));
            }
        } catch (Exception e) {
            System.err.println(">>> [ERROR] SQL 执行异常，请检查数据库表结构或连接配置！");
            e.printStackTrace();
        }
        return list;
    }
}