package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	private static final int PORT = 8080;
	public static void main(String[] args){
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");//////////
		try {
			while(true) {
				//Si blocca finche' non si verifica una connessione
				Socket socket = s.accept();
				try {
					new MultiServerHandle(socket);
				}catch(IOException e) {
					//Se fallisce chiude il socket,
					//altrimenti il thread la chiudera':
					socket.close();
				}
			}
		}finally {
			s.close();
		}
	}
}
class MultiServerHandle extends Thread{
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	public MultiServerHandle(Socket s) throws IOException{
		socket=s;
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(
								socket.getOutputStream())),true);
		//se una qualsiasi delle chiamate precedenti solleva una
		//eccezione, il processo chiamante e' responsabile della
		//chiusura del socket. Altrimenti lo chiudera' il thread
		start();//chiama run()
	}
	private void run() {
		try {
			while(true) {
				String str = in.readLine();
				if(str.equals("END")) break;
				System.out.println("Echoing: "+str);///////////
				out.println(str);//////////
			}
			System.out.println("closing...");
		}catch(IOException e) {
			System.out.println("IO Exception");
		}finally {
			try {
				socket.close();
			}catch(IOException e) {
				System.out.println("Socket not closed");
			}
		}
	}
}

