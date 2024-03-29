/**
 * Package che gestisce la connessione tra client e server.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che gestisce la connessione
 * tra Client e Server e che contiene il main method del server.<br>
 * @author Claudia Lorusso, Angela Dileo
 *
 */
class MultiServer {
	
	/**
	 * Porta a cui si deve connetere il server
	 * per interagire con il client
	 */
	private static int PORT=8080;
	
	/**
	 * Instanzia un oggetto di tipo {@link MultiServer} gestendo la connessione
	 * ad una determinata porta in modo da poter mettere in comunicazione il client
	 * ed il server.
	 * @param args intero pari al numero di porta.
	 */
	public static void main(String[] args) {
		int port = 8080;
		if (args.length == 1) {
			try {
				try {
					port = new Integer(args[0]).intValue();
					if ((port >= 1 && port <= 1024) || port > 65535) {
						System.out.println("The selected port number '" + port + "' is NOT available.");
						port = PORT;
						System.out.println("Value setted to '" + port + "' by DEFAULT");
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					port = PORT;
					System.out.println("Value setted to '" + port + "' by DEFAULT");
				} finally {
					System.out.println("Port number: " + port);
					new MultiServer(port);
				}
			} catch (IOException e) {
				System.out.println("Address already in use.\nClosing...");
				return;
			}
		} else if (args.length == 0) {
			try {
				port=8080;
				System.out.println("Port value setted to '" + port + "' by DEFAULT");
				new MultiServer(port);
			} catch (IOException e) {
				System.out.println("Address already in use.\nClosing...");
			}
		}else {
			System.out.println("ERROR: MAX 1 argument.");
		}
	}
	
	/**
	 * Costruttore di classe.<br>
	 * Inizializza la porta {@link #PORT} ed invoca {@link #run}.
	 * @param port valore da assegnare alla porta {@link #PORT}.
	 * @throws IOException in caso di errore nell'apertura della socket
	 */
	private MultiServer(int port) throws IOException {
		PORT = port;
		run();
	}
	
	/**
	 * Istanzia un oggetto istanza della classe {@link ServerSocket}
	 * che pone in attesa di richiesta di connessioni da parte
	 * del client.<br>
	 * Ad ogni nuova richiesta di connessione
	 * si istanzia {@link ServerOneClient}.
	 * @throws IOException in caso di errore nell'apertura della socket
	 */
	private void run() throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");
		try {
			while (true) {
				//Si blocca finche' non si verifica una connessione:
				Socket socket = s.accept();
				try {
					new ServerOneClient(socket);
				} catch(IOException e) {
					/*Se fallisce chiude il socket,
					 *altrimenti il thread la chiudera':*/
					socket.close();
				}
			}
		} finally {
			s.close();
		}
	}
}