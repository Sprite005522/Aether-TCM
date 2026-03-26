import grpc
from concurrent import futures
import time
import engine_pb2
import engine_pb2_grpc
from rdkit import Chem
from rdkit.Chem import Descriptors, Crippen

class TcmEngineServicer(engine_pb2_grpc.TcmEngineServiceServicer):
    def RunMolecularSimulation(self, request, context):
        try:
            mol = Chem.MolFromSmiles(request.mol_structure)
            if mol:
                logp = Crippen.MolLogP(mol)
                mw = Descriptors.MolWt(mol)
                energy = -(logp * 0.4 + (mw / 120))
                formula = Chem.rdMolDescriptors.CalcMolFormula(mol)
                print(f">>> Processing: {request.herb_name} | LogP: {logp:.2f}")
                return engine_pb2.SimulationResponse(binding_energy=energy, molecular_formula=formula, toxicity_risk=(logp > 5.0), detail_report=f"MW: {mw:.2f}, LogP: {logp:.2f}")
            return engine_pb2.SimulationResponse(binding_energy=0.0, molecular_formula="Invalid", toxicity_risk=True, detail_report="Failed to parse SMILES")
        except Exception as e:
            print(f"Error: {str(e)}")
            return engine_pb2.SimulationResponse()

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    engine_pb2_grpc.add_TcmEngineServiceServicer_to_server(TcmEngineServicer(), server)
    server.add_insecure_port('0.0.0.0:50051')
    print("Aether-TCM Python Engine is running on port 50051...")
    server.start()
    try:
        while True: time.sleep(86400)
    except KeyboardInterrupt: server.stop(0)

if __name__ == '__main__':
    serve()
