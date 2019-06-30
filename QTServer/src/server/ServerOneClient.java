package server;

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
	private QTMiner kmeans;

	public ServerOneClient(Socket s) throws IOException {
		this.socket = s;
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		start(); //avvio del thread
	}

	public void run() {
		Data data=null;
		kmeans=null;
		double value;
		while(true){
			try {
				int cases = new Integer((int)this.in.readObject());
				System.out.println("CASO:"+cases);
				switch(cases){
				case 1:
					out.writeObject(data.toString());
					//readIN Radius
					value = ((double) this.in.readObject());
					kmeans = new QTMiner(value);
					try{
						int numC=kmeans.compute(data);
						//OUT ok
						out.writeObject("OK");
						out.writeObject(numC);
						out.writeObject(kmeans.getC().toString(data));
					}catch(ClusteringRadiusException e){ 
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					try {
						String file = (String) in.readObject();
						kmeans.salva(file);
						out.writeObject("OK");
					} catch(Exception e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 3: 
					String table = ((String) this.in.readObject());
					value = ((double) this.in.readObject());
					String fileName = ((String) this.in.readObject());
					System.out.println(table);
					try {
						data = new Data(table);
						if(fileName!=null) {
							System.out.println(fileName);
							kmeans = new QTMiner(fileName);
							out.writeObject(kmeans.getC().toString(data));
						} 
						else 
							out.writeObject("Caricamento non andato a buon fine!");
					}catch(DatabaseConnectionException|SQLException|NoValueException|EmptyDatasetException|TableNotFoundException|IOException e) {
						out.writeObject(e.getMessage());
					}
					break;
				case 0:
					table = ((String)this.in.readObject());
					try{
						data = new Data(table);
						out.writeObject("OK");
					}catch(DatabaseConnectionException e1){
						out.writeObject(e1.getMessage());
					}catch(SQLException e2){
						out.writeObject(e2.getMessage());
					}catch(NoValueException e3){
						out.writeObject("Valori non presenti!");
					}catch(EmptyDatasetException e4){
						out.writeObject(e4.getMessage());
					}catch(TableNotFoundException e5){
						out.writeObject(e5.getMessage());
					}catch(IOException e6){
						out.writeObject(e6.getMessage());
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


