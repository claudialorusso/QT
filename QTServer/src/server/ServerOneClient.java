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

public class ServerOneClient extends Thread {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private QTMiner qt;

	public ServerOneClient(Socket s) throws IOException {
		this.socket = s;
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		start(); //avvio del thread
	}

	public void run() {
		Data data=null;
		qt=null;
		double radius;
		while(true){
			try {
				int cases = new Integer((int)this.in.readObject());
				System.out.println("\tCASO:"+cases);//richiesta di...in base al caso
				switch(cases){
				case 0://storeTableFromDb()
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
				case 1://learningFromDbTable()
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
				case 2://storeClusterInFile()
					try {
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
				case 3: // learningFromFile()
					//String table = ((String) this.in.readObject());
					//value = ((double) this.in.readObject());
					//System.out.println(table);
					String fileName = ((String) this.in.readObject());
					try {
						//data = new Data(table);
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
				e.printStackTrace();
			}catch (IOException e) {
				try {
					out.writeObject("ERRORE: non è possibile proseguire!");
					break;
				} catch (IOException e1) {
					System.out.println("Chiusura della connessione... \n");
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


