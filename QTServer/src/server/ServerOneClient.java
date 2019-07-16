package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import data.Data;
import data.TableNotFoundException;
import mining.ClusteringRadiusException;
import mining.QTMiner;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;

/**
 * Estende la classe Thread e
 * gestisce la connessione alla socket.
 * @author Claudia Lorusso, Angela Dileo
 *
 */
class ServerOneClient extends Thread {
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
	 * Oggetto della classe QTMiner tramite cui calcolare il clusterSet
	 */
	private QTMiner qt;
	/**
	 * Costruttore di classe. 
	 * Inizializza gli attributi di socket, in e out.
	 * Avvia il thread.
	 * @param s Socket
	 * @throws IOException Eccezione di tipo I/O.
	 */
	ServerOneClient(Socket s) throws IOException {
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
							TableNotFoundException|
							IOException|
							EmptySetException e) {
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
						out.writeObject("OK");
						out.writeObject(numC);
						out.writeObject(qt.getC().toString(data));
					}catch(ClusteringRadiusException e){ 
						out.writeObject(e.getMessage());
					}
					break;
				case 2://store Cluster In File
					try {
						System.out.println("storing the ClusterSet into a file");
						String file = (String) in.readObject();
						qt.salva(file);
						out.writeObject("OK");
					} catch (FileNotFoundException e) {
						out.writeObject("ERROR: File Not Found!");
					}catch (IOException e) {
						out.writeObject("ERROR: improper I/O!");
					}catch(Exception e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 3: // learning From File
					System.out.println("learning a ClusterSet from a file.");
					String fileName = ((String) this.in.readObject());
					try {
						if(fileName!=null) {
							qt = new QTMiner(fileName);
							out.writeObject("OK");
							out.writeObject(qt.toString());
						} else out.writeObject("Loading aborted!");
					}catch (FileNotFoundException e) {
						out.writeObject("File NOT found!");
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
					System.out.println(e1.getMessage());
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
						System.out.println(e1.getMessage());
					}
				}
			}
		}
	}
}