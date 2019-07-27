package keyboardinput;

import java.io.*;
import java.util.*;
/**
 * Classe che gestisce l'acquisizione da tastiera.
 * @author Claudia Lorusso, Angela Dileo
 */
public class Keyboard {
	/**
	 * Carattere corrispondente al simbolo della croce.
	 */
	public static final char CROSS_ASCII =(char)8224; 
	// ************* Error Handling Section **************************
	/**
	 * Verifica la stampa di errori.
	 */
	private static boolean printErrors = true;
	/**
	 * Incrementa il numero di errori e stampa
	 * a video il messaggio di errore appropriato.
	 * @param str stringa contenente l'errore da stampare.
	 */
	private static void error(String str) {
		if (printErrors)
			System.out.println(str);
	}

	// ************* Tokenized Input Stream Section ******************

	/**
	 * Token/Blocco di testo corrente.
	 */
	private static String current_token = null;
	/**
	 * Suddivide una stringa in tokens.
	 */
	private static StringTokenizer reader;
	/**
	 * Acquisizione input utente.
	 */
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	/**
	 * Acquisisce il token in input successivo
	 * assumento che possa essere su righe di input
	 * consegutive.
	 * @return token successivo
	 */
	private static String getNextToken() {
		return getNextToken(true);
	}
	/**
	 * Acquisisce il token in input successivo
	 * @param skip  determina se le righe consecutive sono utilizzate o meno.
	 * @return token successivo
	 */
	private static String getNextToken(boolean skip) {
		String token;

		if (current_token == null)
			token = getNextInputToken(skip);
		else {
			token = current_token;
			current_token = null;
		}
		return token;
	}
	/**
	 * Acquisisce il successivo token in input,
	 * che potrebbe provenire
	 * dalla corrente riga in input.
	 * @param skip determina se le righe consecutive sono utilizzate o meno.
	 * @return token successivo.
	 */
	private static String getNextInputToken(boolean skip) {
		final String delimiters = " \t\n\r\f";
		String token = null;
		try {
			reader = new StringTokenizer(in.readLine(), delimiters, true);
			if (reader.countTokens()==1) {
				token = reader.nextToken();
			}else {
				reader=null;
				token=null;
			}
		} catch (Exception exception) {
			token = null;
		}
		return token;
	}

	// ************* Reading Section *********************************
	
	/**
	 * Restituisce una sotto-stringa (parola)
	 * letta da standard input.
	 * @return una parola letta
	 * da standard input.
	 */
	public static String readWord() {
		String token;
		try {
			token = getNextToken();
		} catch (Exception exception) {
			error("Error reading String data, null value returned.");
			token = null;
		}
		return token;
	}
	/**
	 * Restituisce un carattere letto da standard input.
	 * @return un carattere letto da standard input.
	 */
	public static char readChar() {
		String token = getNextToken(false);
		char value;
		try {
			if (token.length() > 1) {
				token=null;
			} else
				current_token = null;
			value = token.charAt(0);
		} catch (Exception exception) {
			error("Error reading char data, MIN_VALUE value returned.");
			value = Character.MIN_VALUE;
		}
		return value;
	}
	/**
	 * Restituisce un intero letto da standard input.
	 * @return un intero letto da standard input.
	 */
	public static int readInt() {
		String token = getNextToken();
		int value;
		try {
			value = Integer.parseInt(token);
		} catch (Exception exception) {
			error("Error reading int data, MIN_VALUE value returned.");
			value = Integer.MIN_VALUE;
		}
		return value;
	}
	/**
	 * Restituisce un double letto da standard input.
	 * @return double letto da standard input.
	 */
	public static double readDouble() {
		String token = getNextToken();
		double value;
		try {
			value = (new Double(token)).doubleValue();
		} catch (Exception exception) {
			error("Error reading double data, NaN value returned.");
			value = Double.NaN;
		}
		return value;
	}
}