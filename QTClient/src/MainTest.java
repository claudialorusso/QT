import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import keyboardinput.Keyboard;

/**
 * Classe che comprende il {@link #main} del software,
 * corrispondente all'interfaccia del Client con
 * cui si interfaccia il Server.
 * @author Claudia Lorusso, Angela Dileo
 *
 */
class MainTest {
	
	/**
	 * Stream di Output.
	 */
	private ObjectOutputStream out;
	
	/**
	 * Stream di input con richieste del Client.
	 */
	private ObjectInputStream in ;

	/**
	 * Costruttore della classe {@link MainTest}.<br>
	 * Imposta l'indirizzo ip e la porta
	 * per la connessione con il server tramite socket
	 * ai valore <tt>ip</tt> e <tt>port</tt> passati come parametri.<br>
	 * Inizializza gli stream di input, {@link #in}, e di output, {@link #out}
	 * per inviare e ricevere richieste dal Server.
	 * @param ip stringa contenente l'indirizzo ip
	 * @param port numero di porta
	 * @throws IOException nella lettura dell'intestazione dello Stream
	 * @throws ConnectException nel caso in cui il server smetta di funzionare
	 */
	private MainTest(String ip, int port) throws IOException, ConnectException {
		InetAddress addr = InetAddress.getByName(ip);
		System.out.println("addr = " + addr);
		Socket socket = new Socket(addr, port);
		System.out.println(socket);
		out = new ObjectOutputStream(socket.getOutputStream());
		// stream con richieste del client
		in = new ObjectInputStream(socket.getInputStream());
	}
	
	/**
	 * Controlla e stampa a video il menu principale del programma.
	 * @return un intero pari ad 1
	 * se si vogliono caricare dei cluster da file;
	 * un intero pari a 2 se si vuole caricare una
	 * tabella da cui estrapolare dati
	 * presente nel database.
	 */
	private int menu() {
		int answer;
		do {
			System.out.println("(1) Load clusters from file");
			System.out.println("(2) Load data from db");
			System.out.print("(1/2):");
			answer=Keyboard.readInt();
		} while (answer <= 0 || answer > 2 || answer == Integer.MIN_VALUE);
		return answer;
	}
	
	/**
	 * Controlla l'acquisizione
	 * di un carattere per permettere
	 * l'inizio di una nuova
	 * esecuzione.
	 * @return 'y' se si vuole effettuare una nuova esecuzione;<br>
	 * 'n' se si vuole terminare l'esecuzione.
	 */
	private static char newExecution() {
		char newExecution;
		do {
			newExecution = Keyboard.readChar();
			if (Character.toLowerCase(newExecution) != 'y' && Character.toLowerCase(newExecution) != 'n')
				System.out.print("ERROR: only 'y' or 'n' admitted.\nType again your choice: ");
		} while (Character.toLowerCase(newExecution) != 'y' && Character.toLowerCase(newExecution) != 'n' || newExecution == Character.MIN_VALUE);
		return newExecution;
	}
	
	/**
	 * Permette di
	 * recuperare dei dati da una tabella,
	 * il cui nome deve essere esplicitato dall'utente.
	 * @throws SocketException nel caso in cui si verifichi un problema
	 * nell'accesso ad una socket.
	 * @throws ServerException nel caso in cui il server
	 * riscontri dei problemi nella risoluzione
	 * della richiesta del client.
	 * @throws IOException nella lettura dell'intestazione dello stream
	 * @throws ClassNotFoundException nel caso in cui non si riesca a
	 * trovare la classe corrispondente ad un oggetto serializzato.
	 */
	private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
		//CASE 0
		out.writeObject(0);
		String tabName;
		do {
			System.out.print("Table name:");
			tabName = Keyboard.readWord();
			if (tabName == null)
				System.out.println("Spaces between words are NOT allowed.Try again.");
		} while (tabName == null);
		out.writeObject(tabName.toLowerCase());
		String result = (String)in.readObject();
		if(!result.equals("OK"))
			throw new ServerException(result);
	}
	
	/**
	 * Controlla l'acquisizione di dati
	 * prelevati da una tabella contenuta nel database.<br>
	 * Mostra i Clusters rinvenuti a seguito
	 * dell'inserimento del valore del radius.
	 * @return numero di Cluster individuati.
	 * @throws SocketException nel caso in cui si verifichi un problema
	 * nell'accesso ad una socket.
	 * @throws ServerException nel caso in cui il server ha
	 * riscontrato dei problemi nella risoluzione
	 * della richiesta del client.
	 * @throws IOException nella lettura dell'intestazione dello stream
	 * @throws ClassNotFoundException nel caso in cui non si riesca a
	 * trovare la classe corrispondente ad un oggetto serializzato.
	 */
	private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
		//CASE 1
		double r = 1.0;
		out.writeObject(1);
		System.out.println((String)in.readObject());
		do{
			System.out.print("Radius(>0):");
			r=Keyboard.readDouble();
		} while(r <= 0 || Double.isNaN(r));
		//writeOUT Radius
		out.writeObject(r);
		String result = (String)in.readObject();
		if (result.equals("OK")) {
			System.out.println("Number of Clusters:" + in.readObject());
			return (String)in.readObject();
		}
		else throw new ServerException(result);
	}
	
	/**
	 * Controlla il salvataggio del
	 * ClusterSet all'interno di un file '.dmp',
	 * previa acquisizione del nome del file
	 * in cui effettuare tale salvataggio.
	 * @throws SocketException nel caso in cui si verifichi un problema
	 * nell'accesso ad una socket.
	 * @throws ServerException nel caso in cui il server ha
	 * riscontrato dei problemi nella risoluzione
	 * della richiesta del client.
	 * @throws IOException nella lettura dell'intestazione dello stream
	 * @throws ClassNotFoundException nel caso in cui non si riesca a
	 * trovare la classe corrispondente ad un oggetto serializzato.
	 */
	private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
		//CASE 2
		out.writeObject(2);
		String fileName = "";
		do {
			System.out.print("File name:");
			fileName = Keyboard.readWord();
			if (fileName == null)
				System.out.println("Spaces between words are NOT allowed.Try again:");
		} while(fileName == null);
		fileName=fileName.concat(".dmp");
		out.writeObject(fileName);
		String result = (String)in.readObject();
		if (result.equals("OK"))
			System.out.println("Saving clusters in " + fileName);
		else throw new ServerException(result);
	}
	
	/**
	 * Controlla l'acquisizione della stringa,
	 * che contiene il ClusterSet desiderato,
	 * prelevata da file, previa acquisizione del suo nome.
	 * @return la stringa contenente il ClusterSet memorizzato
	 * nel file.
	 * @throws FileNotFoundException nel caso in cui,
	 * a seguito dell'acquisizione della stringa
	 * contenente il nome del file in cui e' memorizzato il
	 * ClusterSet desiderato, non si riesca a trovare
	 * il file con quel nome.
	 * @throws ServerException nel caso in cui il server ha
	 * riscontrato dei problemi nella risoluzione
	 * della richiesta del client.
	 * @throws IOException nella lettura dell'intestazione dello Stream
	 * @throws ClassNotFoundException nel caso in cui non si riesca a
	 * trovare la classe corrispondente ad un oggetto serializzato.
	 */
	private String learningFromFile() throws FileNotFoundException, IOException, ClassNotFoundException, ServerException {
		//CASE 3
		out.writeObject(3);
		String fileName = "";
		do {
			System.out.print("File name:");
			fileName = Keyboard.readWord();
			if (fileName == null)
				System.out.println("Spaces between words are NOT allowed.Try again:");
		} while (fileName == null);
		fileName = fileName.concat(".dmp");
		out.writeObject(fileName);
		String result = (String)in.readObject();
		if (result.equals("OK"))
			return (String)in.readObject();
		else throw new ServerException(result);
	}
	
	/**
	 * Meotodo main che gestisce la connessione client/server
	 * ed i due case principali:<ul>
	 * 	<li> Acquisizione da database
	 * 	<li> Acquisizione da file
	 * </ul>
	 * @param args contiene l'indirizzo ip, nel primo argomento,
	 * il numero di porta, nel secondo argomento.
	 * @see #learningFromDbTable
	 * @see #learningFromFile
	 */
	public static void main(String[] args) {
		String ip;
		int port;
		if (args.length == 2) {
			ip = args[0];
			port=new Integer(args[1]).intValue();
		} else if (args.length == 0) {
			ip = "localhost";
			port = 8080;
			System.out.println("IP value setted by DEFAULT to: " + ip);
			System.out.println("PORT value setted by DEFAULT to: " + port + "\n");
		} else {
			System.out.println("ERROR:Only two arguments (IP and PORT values) allowed!");
			return;
		}
		MainTest main = null;
		try {
			main = new MainTest(ip,port);
		} catch (ConnectException e) {
			System.out.println("The Server @" + ip + ":" + port + " is sleeping. (Zzz)");
			return;
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
		do {
			int menuAnswer=main.menu();
			switch(menuAnswer)
			{
			case 1: // learning from File
				try {
					String qt = main.learningFromFile();
					System.out.println(qt);
				} catch (SocketException e) {
					System.out.println("The Server shutted down. May he rest in peace =(");
					return;
				} catch (FileNotFoundException e) {
					System.out.println(e);
					return ;
				} catch (IOException e) {
					System.out.println(e);
					return;
				} catch (ClassNotFoundException e) {
					System.out.println(e);
					return;
				} catch (ServerException e) {
					System.out.println(e.getMessage());
				}
				break;//fine case 1
			case 2: // learning from DB
				while (true) {
					try {
						main.storeTableFromDb();
						break; //esce fuori dal while
					} catch (SocketException e) {
						System.out.println("The Server shutted down. May he rest in peace =(");
						return;
					} catch (FileNotFoundException e) {
						System.out.println(e);
						return;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					} catch (ServerException e) {
						System.out.println(e.getMessage());
					}
				} //end while [viene fuori dal while con un db (in alternativa il programma termina)
				char answer = 'y';//itera per learning al variare di k
				do {
					try	{
						String clusterSet=main.learningFromDbTable();
						System.out.println(clusterSet);
						main.storeClusterInFile();
					} catch (SocketException e) {
						System.out.println("The Server shutted down. May he rest in peace =(");
						return;
					} catch (FileNotFoundException e) {
						System.out.println(e);
						return;
					} catch (ClassNotFoundException e) {
						System.out.println(e);
						return;
					} catch (IOException e) {
						System.out.println(e);
						return;
					} catch (ServerException e) {
						System.out.println(e.getMessage());
					}
					System.out.print("Would you repeat?(y/n)");
					answer = newExecution();
				} while (Character.toLowerCase(answer) == 'y');
				break; //fine case 2
			default:
				System.out.println("Invalid option!");
			}
			System.out.print("Would you choose a new operation from menu?(y/n)");
			char execution = newExecution();
			if (Character.toLowerCase(execution) != 'y') break;
		} while (true);
	}
}