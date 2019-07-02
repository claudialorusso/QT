package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Classe che gestisce la connessione
 * tra Client e Server.
 * @author Claudia Lorusso, Angela Dileo
 *
 */
//public
class MultiServer{
	/**
	 * Porta a cui si deve connetere il server
	 * per interagire con il client
	 */
	private static int PORT=8080;
	/**
	 * Instanzia un oggetto di tipo MultiServer
	 * @param args intero pari al numero di porta.
	 */
	public static void main(String[] args) {
		int port=8080;
		if(args.length==1) {
			try{
				try {
					port=new Integer(args[0]).intValue();
					if(port>=1 && port<=1024) {
						System.out.println("The selected port number '"+port+"' is NOT available.");
						port=PORT;
						System.out.println("Value setted to '"+port+"' by DEFAULT");
					}
				}catch(ArrayIndexOutOfBoundsException e){
					port = PORT;
					System.out.println("Value setted to '"+port+"' by DEFAULT");
				}finally {
					System.out.println("Port number: "+port);
					new MultiServer(port);
				}
			}catch (IOException e){
				System.out.println(e.getMessage());

				return;
			}
		}else if(args.length==0) {
			try {
				port=8080;
				System.out.println("Port value setted to '"+port+"' by DEFAULT");
				new MultiServer(port);
			}catch (IOException e){
				System.out.println(e.getMessage());
			}
		}else {
			System.out.println("ERROR: MAX 1 argument.");
		}
	}
	/**
	 * Costruttore di classe.
	 * Inizializza la porta ed invoca run().
	 * @param port valore da assegnare alla porta.
	 */
	private MultiServer(int port) throws IOException{
		PORT = port;
		run();
	}
	/**
	 * Istanzia un oggetto istanza della classe ServerSocket
	 * che pone in attesa di richiesta di connessioni da parte
	 * del client.
	 * Ad ogni nuova richiesta di connessione
	 * si istanzia ServerOneClient.
	 */
	private void run() throws IOException{
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");
		try {
			while(true) {
				//Si blocca finche' non si verifica una connessione:
				Socket socket =s.accept();
				try {
					new ServerOneClient(socket);
				}catch(IOException e) {
					//Se fallisce chiude il socket,
					//altrimenti il thread la chiudera':
					socket.close();
				}
			}
		}finally{
			s.close();
		}
	}
}