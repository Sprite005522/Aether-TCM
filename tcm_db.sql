CREATE DATABASE IF NOT EXISTS tcm_db CHARACTER SET utf8mb4;

USE tcm_db;

-- 中药成分分子结构表
CREATE TABLE IF NOT EXISTS tcm_molecules (
    id INT AUTO_INCREMENT PRIMARY KEY,
    herb_name VARCHAR(100) NOT NULL,      -- 来源药材名称（如：人参）
    component_name VARCHAR(100) NOT NULL, -- 成分名称（如：人参皂苷Rg1）
    smiles_code TEXT NOT NULL,            -- 分子结构 SMILES 编码
    mol_weight DOUBLE,                    -- 分子量
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 插入几条测试数据（用于验证批量读取）
INSERT INTO tcm_molecules (herb_name, component_name, smiles_code) VALUES 
('青蒿', '青蒿素', 'CC12CCC3C(C1OOC24C(CCC3C4C)C)C'),
('黄芩', '黄芩苷', 'C21H18O11'),
('补骨脂', '补骨脂素', 'c1cc2c(cc1)oc(=O)cc2'),
('甘草', '甘草酸', 'CC1(C2CCC3(C(C2(CCC1(C(=O)O)C)C)C(=O)C=C4C3(CCC5(C4CC(CC5)(C)C(=O)O)C)C)C)C');