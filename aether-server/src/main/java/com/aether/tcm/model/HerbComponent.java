package com.aether.tcm.model;

public class HerbComponent {
    private final String name;
    private final String smiles;

    public HerbComponent(String name, String smiles) {
        this.name = name;
        this.smiles = smiles;
    }

    public String getName() { return name; }
    public String getSmiles() { return smiles; }
}