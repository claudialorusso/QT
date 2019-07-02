//********************************************************************
//  Keyboard.java       Author: Lewis and Loftus
//
//  Facilitates keyboard input by abstracting details about input
//  parsing, conversions, and exception handling.
//********************************************************************
package keyboardinput;
import java.io.*;
import java.util.*;
/**
 * Classe che gestisce l'acquisizione da tastiera.
 * @author Claudia Lorusso, Angela Dileo
 */
public class Keyboard {
	// ************* Error Handling Section **************************
	/**
	 * Verifica la stampa di errori.
	 */
	private static boolean printErrors = true;
	/**
	 * Conteggia gli errori.
	 */
	private static int errorCount = 0;
	/**
	 * Acquisisce il numero corrente di errori.
	 * @return il conteggio corrente di errori.
	 */
	//public
	/*private static int getErrorCount() {
		return errorCount;
	}*/
	/**
	 * Reimposta il numero corrente di errori a zero.
	 * @param count numero di errori.
	 */
	//public
	/*private static void resetErrorCount(int count) {
		errorCount = 0;
	}*/
	/**
	 * Acquisisce printErrors
	 * @return
	 */
	//public
	/*private static boolean getPrintErrors() {
		return printErrors;
	}*/
	/**
	 * Imposta printErrors ad un certo valore booleano a seconda che stia
	 * per avvenire una stampa di un errore o meno.
	 * @param flag true o false a seconda che stia per avvenire una stampa
	 * di un errore o meno.
	 */
	//public
	/*private static void setPrintErrors(boolean flag) {
		printErrors = flag;
	}*/
	/**
	 * Incrementa il numero di errori e stampa
	 * a video il messaggio di errore appropriato.
	 * @param str stringa contenente l'errore da stampare.
	 */
	private static void error(String str) {
		errorCount++;
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
			if (reader == null)
				reader = new StringTokenizer(in.readLine(), delimiters, true);

			while (token == null || ((delimiters.indexOf(token) >= 0) && skip)) {
				while (!reader.hasMoreTokens())
					reader = new StringTokenizer(in.readLine(), delimiters,
							true);

				token = reader.nextToken();
			}
		} catch (Exception exception) {
			token = null;
		}

		return token;
	}
	/**
	 * Verifica che non ci siano altri token
	 * da leggere sulla corrente riga in input.
	 * @return true se non ci sono altri token da leggere
	 * sulla corrente riga di input.
	 */
	//public
	private static boolean endOfLine() {
		return !reader.hasMoreTokens();
	}

	// ************* Reading Section *********************************

	/**
	 * Gestisce l'acquisizione di una stringa da
	 * standard input.
	 * @return stringa letta da standard input.
	 */
	public static String readString() {
		String str;

		try {
			str = getNextToken(false);
			while (!endOfLine()) {
				str = str + getNextToken(false);
			}
		} catch (Exception exception) {
			error("Error reading String data, null value returned.");
			str = null;
		}
		return str;
	}
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
	 * Restituisce un booleano letto da standard input.
	 * @return valore booleano letto da standard input.
	 */
	//public
	static boolean readBoolean() {
		String token = getNextToken();
		boolean bool;
		try {
			if (token.toLowerCase().equals("true"))
				bool = true;
			else if (token.toLowerCase().equals("false"))
				bool = false;
			else {
				error("Error reading boolean data, false value returned.");
				bool = false;
			}
		} catch (Exception exception) {
			error("Error reading boolean data, false value returned.");
			bool = false;
		}
		return bool;
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
				current_token = token.substring(1, token.length());
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
	 * Restituisce un long letto da standard input.
	 * @return un long letto da standard input.
	 */
	//public
	static long readLong() {
		String token = getNextToken();
		long value;
		try {
			value = Long.parseLong(token);
		} catch (Exception exception) {
			error("Error reading long data, MIN_VALUE value returned.");
			value = Long.MIN_VALUE;
		}
		return value;
	}
	/**
	 * Restituisce un float letto da standard input.
	 * @return un float letto da standard input.
	 */
	//public
	static float readFloat() {
		String token = getNextToken();
		float value;
		try {
			value = (new Float(token)).floatValue();
		} catch (Exception exception) {
			error("Error reading float data, NaN value returned.");
			value = Float.NaN;
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