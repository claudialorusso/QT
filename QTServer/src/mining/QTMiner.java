package mining;

import data.Data;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Classe che gestisce ed implementa l'algoritmo
 * Quality Threshold.
 * @author Claudia Lorusso, Angela Dileo
 */
public class QTMiner implements Serializable {

	/**
	 * ID di serializzazione.
	 */
	private static final long serialVersionUID=1L;

	/**
	 * Set di {@link Cluster}.
	 * @see ClusterSet
	 */
	private ClusterSet D;

	/**
	 * Raggio corrispondente alla distanza massima
	 * che ogni tupla deve avere per poter essere
	 * inserita in un unico {@link Cluster}.
	 */
	private double radius;

	/**
	 * Costruttore della classe {@link QTMiner}.<br>
	 * Crea l'oggetto array riferito da {@link #D}
	 * ed inizializza {@link #radius}
	 * con il parametro passato in input
	 * @param radius raggio
	 */
	public QTMiner(double radius) {
		D = new ClusterSet();
		this.radius = radius;
	}

	/**
	 * Restituisce il {@link ClusterSet} {@link #D}
	 * @return il {@link ClusterSet}
	 */
	public ClusterSet getC() {
		return this.D;
	}

	/**
	 * Esegue l'algoritmo QT costruendo il {@link ClusterSet} definitivo.
	 * @param data oggetto di tipo {@link Data} lista di esempi/tuple
	 * @return numero di {@link Cluster}s scoperti
	 * @throws ClusteringRadiusException qualora l'algoritmo di clustering generi un solo {@link Cluster}
	 */
	public int compute(Data data) throws ClusteringRadiusException {
		int numclusters = 0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];
		for (int i=0; i < isClustered.length; i++)
			isClustered[i] = false;
		int countClustered = 0;	
		while (countClustered != data.getNumberOfExamples()){
			Cluster c = buildCandidateCluster(data,isClustered);
			D.add(c);
			numclusters++;
			Iterator<Integer> it = c.iterator();
			while (it.hasNext())
				//imposto a true le tuple del cluster c che sono state clusterizzate
				isClustered[it.next()] = true;
			countClustered += c.getSize();
		}
		if(numclusters == 1)
			throw new ClusteringRadiusException(data.getNumberOfExamples());
		return numclusters;
	}

	/**
	 * Costruisce un {@link Cluster} per ciascuna
	 * tupla di data non ancora clusterizzata
	 * in un cluster del {@link ClusterSet} e restituisce il {@link Cluster}
	 * candiadato piu' popoloso.
	 * @param data insieme di esempi/tuple che necessitano di
	 * essere raggruppati in {@link Cluster}s
	 * @param isClustered informazione booleana
	 * sullo stato di clusterizzazione di una tupla:
	 * <br><ul>
	 * 	<li>false se la tupla considerata non e' ancora assegnata ad alcun
	 * cluster di {@link #D};
	 * 	<li>true altrimenti.</ul>
	 * @return il {@link Cluster} candidato piu' popoloso
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		//ClusterSet temporaneo in cui andro' ad inserire i Cluster temporanei
		ClusterSet cSet = new ClusterSet(true);
		for (int i=0; i < data.getNumberOfExamples(); i++) {
			if (!isClustered[i]) {
				Cluster cTemp = new Cluster(data.getItemSet(i));
				cSet.add(cTemp);
				for (int j=0; j < data.getNumberOfExamples(); j++) 
					if (!isClustered[j])
						if (data.getItemSet(i).getDistance(data.getItemSet(j)) <= this.radius)
							cTemp.addData(j);
			}
		}
		return cSet.last();//l'ultimo Cluster corrisponde al piu' popoloso
	}

	/**
	 * Costruttore per la serializzazione della classe {@link QTMiner}.<br>
	 * Apre il file identificato da <tt>fileName</tt>,
	 * legge l'oggetto ivi memorizzato e lo assegna a {@link #D}.
	 * @param fileName Nome del file.
	 * @throws FileNotFoundException Eccezione sollevata se il file esiste/non esiste ma
	 * non puo' essere non viene trovato, creato o aperto .
	 * @throws IOException Eccezione di tipo I/O.
	 * @throws ClassNotFoundException Eccezione sollevata se la classe di
	 * un oggetto serializzato non è trovata.
	 */
	public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
		FileInputStream fileInput = new FileInputStream(fileName);
		ObjectInputStream in = new ObjectInputStream(fileInput);
		this.D = (ClusterSet) in.readObject();
		//chiusura degli stream
		in.close();
		fileInput.close();
	}

	/**
	 * Apre il file identificato da <tt>fileName</tt> e salva 
	 * l'oggetto riferito da {@link #D} in tale file.
	 * @param fileName Nome del file.
	 * @throws FileNotFoundException Eccezione sollevata se il file esiste/non esiste ma
	 * non puo' essere non viene trovato, creato o aperto .
	 * @throws IOException Eccezione di tipo I/O.
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException {
		FileOutputStream fileOutput = new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(fileOutput);
		out.writeObject(this.D);
		//chiusura degli stream
		out.close();
		fileOutput.close();
	}

	/**
	 * Override del metodo toString di {@link Object}.<br>
	 * Restituisce una stringa che rappresenta il {@link ClusterSet}.
	 */
	@Override
	public String toString() {
		return this.D.toString();
	}
}