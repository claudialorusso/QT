package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import data.Data;
import data.EmptyDatasetException;
import data.TableNotFoundException;
import mining.ClusteringRadiusException;
import mining.QTMiner;
import database.DatabaseConnectionException;
import database.NoValueException;

/**
 * Estende la classe Thread e
 * gestisce la connessione alla socket.
 * @author Lorusso Claudia, Dileo Angela
 *
 */
public class ServerOneClient extends Thread {
	/**
	 * Socket che permette la connessione
	 */
	private Socket socket;
	
	/**
	 * Stream di dati in input
	 */
	private ObjectInputStream in;
	
	/**
	 * Stream di dati in output
	 */
	private ObjectOutputStream out;
	/**
	 * 
	 */
	private QTMiner qt;
	/**
	 * Costruttore di classe. 
	 * Inizializza gli attributi di socket, in e out.
	 * Avvia il thread.
	 * @param s Socket
	 * @throws IOException Eccezione di tipo I/O.
	 */
	public ServerOneClient(Socket s) throws IOException {
		this.socket = s;
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		start(); //avvio del thread
	}
	/**
	 * Riscrive il metodo run della superclasse Thread
	 * al fine di gestire le richieste dei client.
	 */
	@Override
	public void run() {
		Data data=null;
		qt=null;
		double radius;
		while(true){
			try {
				int cases = new Integer((int)this.in.readObject());
				System.out.println(socket.toString());
				System.out.print("\tThe client is ");
				switch(cases){
				case 0://store Table From Db
					System.out.println("storing a table from the Db");
					String table = ((String)this.in.readObject());
					try{
						data = new Data(table);
						out.writeObject("OK");
					}catch(DatabaseConnectionException|
							SQLException|
							NoValueException|
							EmptyDatasetException|
							TableNotFoundException|
							IOException e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 1://learning From Db Table
					System.out.println("learning a table from the Db");
					out.writeObject(data.toString());
					//readIN Radius
					radius = ((double) this.in.readObject());
					qt = new QTMiner(radius);
					try{
						int numC=qt.compute(data);
						//OUT ok
						out.writeObject("OK");
						out.writeObject(numC);
						out.writeObject(qt.getC().toString(data));
					}catch(ClusteringRadiusException e){ 
						System.out.println(e.getMessage());
					}
					break;
				case 2://store Cluster In File
					try {
						System.out.println("is storing the ClusterSet into a file");
						String file = (String) in.readObject();
						qt.salva(file);
						out.writeObject("OK");
					} catch (FileNotFoundException e) {
						System.out.println("ERROR: File Not Found!");
					}catch (IOException e) {
						System.out.println("ERROR: I/O not corrected!");
					}catch(Exception e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 3: // learning From File
					System.out.println("learning a ClusterSet from a file.");
					String fileName = ((String) this.in.readObject());
					try {
						if(fileName!=null) {
							out.writeObject("OK");
							qt = new QTMiner(fileName);
							out.writeObject(qt.toString());
						} else out.writeObject("Caricamento non andato a buon fine!");
					}catch (FileNotFoundException e) {
						out.writeObject(e.getMessage());
					}catch(	IOException|
							ClassNotFoundException e) {
						out.writeObject(e.getMessage());
					}
					break;
				}
			}catch(ClassNotFoundException e){
				try {
					out.writeObject(e.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println(e.getMessage());
			}catch (IOException e) {
				try {
					out.writeObject("ERROR: It is not possible to continue...");
					break;
				} catch (IOException e1) {
					System.out.println("Connection closed ["+socket.getPort()+"]... ");
					break;
				}finally{
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}


