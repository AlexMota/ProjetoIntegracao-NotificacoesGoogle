package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServSocketRecebimentoReg implements Runnable{
	
	ServidorGCM servidorGCM;
	ServerSocket serverSocket = null;
	Socket socket = null;
	BufferedReader bufferedReader = null;
	private boolean rodando;
	private int porta = 5000;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			
			serverSocket = new ServerSocket(porta);
			
			while (rodando) {
				
				System.out.println("Aguardando Conexao...");
				socket = serverSocket.accept();
				System.out.println("Conexao aceita!");
				
				bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String reg = bufferedReader.readLine();
				System.out.println("Dado recebido: "+reg);
				servidorGCM.adicionarRegId(reg);
				
			}
			
			
			
		} catch (IOException e) {
			System.out.println("Problema na criação do socket.");
		}finally{
			
			try {
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public ServSocketRecebimentoReg(ServidorGCM servidorGCM) {
		super();
		this.servidorGCM = servidorGCM;
		rodando = true;
	}
	
	public void pararSocket(){
		rodando = false;
	}

}
