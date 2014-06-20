package principal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class MainServidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ServidorGCM servidorGCM = new ServidorGCM();
		ServSocketRecebimentoReg servReceb = new ServSocketRecebimentoReg(servidorGCM);
		/*servidorGCM.enviarMensagem(
						"APA91bHAq9d8mgWx-FG1iI8Se4wdqMgXC21sCFjUMowYNjbHAnpFxVfZwVvgx1U5JbxfnItDkSOpwoiDthucvmD39oBs6lyVj_9rhKRuzT23vh_SDsGC0ICQarK0lQbF9cgQhFmMi5gz_op3FPxMmSicEf0sQuagbwb2FHDycrk-JnreROheD0E",
						"Testando notificacao");*/
		
		//Thread Socket
		
		Thread thread = new Thread(servReceb);
		thread.start();
		
		//Thread Socket
		
		
		//Variaveis do MENU
		String mensagemAtual = "Mensagem teste";
		boolean rodando = true;
		Scanner scanner = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);

		int opcao;

		while (rodando) {
			System.out.println("MENU - SERVIDOR");
			System.out.println("Escolha uma oppção");
			System.out.println("1 - Adicionar um regID na mão");
			System.out.println("2 - Trocar Mensagem");
			System.out.println("3 - Enviar mensagem a todos cadastrados");
			System.out.println("4 - Exibir regID dos cadastrados");
			System.out.println("5 - Sair");

			opcao = scanner.nextInt();

			switch (opcao) {
			case 1: {
				String reg = scanner2.nextLine();
				servidorGCM.adicionarRegId(reg);
				System.out.println("Adicionado com sucesso!");
				break;
			}
			case 2: {
				System.out.println("Insira a mensagem:");
				mensagemAtual = scanner2.nextLine();
				System.out.println("Mensagem inserida com sucesso!");
				break;
			}
			case 3: {
				servidorGCM.enviarMsgTodosRegId(mensagemAtual);
				System.out.println("Mensagem enviada!");

				break;
			}
			case 4: {
				
				TreeSet<String> lista = servidorGCM.getListaregId();
				int cont = 0;
				Iterator<String > iterator = lista.iterator();
				
				while (iterator.hasNext()) {
					cont++;
					System.out.println(cont+" - Reg: "+iterator.next());
				}

				break;
			}
			case 5: {
				servReceb.pararSocket();
				rodando = false;

				break;
			}

			default: {
				System.out.println("Opcao incorreta.Tente novamente.");
				break;
			}
			}

		}
		
		System.out.println("Fim do servidor");

	}
}
