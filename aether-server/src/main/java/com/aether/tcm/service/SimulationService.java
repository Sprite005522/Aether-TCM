package com.aether.tcm.service;

import com.aether.tcm.grpc.*;
import com.aether.tcm.model.HerbComponent;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SimulationService {
    private final TcmEngineServiceGrpc.TcmEngineServiceBlockingStub stub;
    private final ManagedChannel channel;

    public SimulationService(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.stub = TcmEngineServiceGrpc.newBlockingStub(channel);
    }

    public SimulationResponse run(HerbComponent comp) {
        SimulationRequest req = SimulationRequest.newBuilder()
                .setHerbName(comp.getName())
                .setMolStructure(comp.getSmiles())
                .build();
        return stub.runMolecularSimulation(req);
    }

    public void close() { channel.shutdown(); }
}