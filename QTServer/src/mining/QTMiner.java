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
public class QTMiner implements Serializable{
	/**
	 * ID di serializzazione
	 */
	private static final long serialVersionUID=1L;
	/**
	 * Set di Cluster
	 */
	private ClusterSet C;
	/**
	 * Raggio corrispondente alla distanza massima
	 * che ogni tupla deve avere per poter essere
	 * inserita in un unico Cluster.
	 */
	private double radius;
	/**
	 * Costruttore della classe QTMiner.
	 * <p>
	 * Crea l'oggetto array riferito da C
	 * ed inizializza radius
	 * con il parametro passato come input
	 * @param radius raggio
	 */
	public QTMiner(double radius) {
		C = new ClusterSet();
		this.radius=radius;
	}
	/**
	 * Restituisce il clusterSet
	 * @return l'insieme dei cluster
	 */
	public ClusterSet getC() {
		return this.C;
	}
	/**
	 * Esegue l'algoritmo QT.
	 * @param data lista di esempi/tuple
	 * @return numero di clusters scoperti
	 * @throws ClusteringRadiusException qualora l'algoritmo di clustering generi un solo Cluster
	 */
	public int compute(Data data) throws ClusteringRadiusException {
		int numclusters=0;
		boolean isClustered[]=new boolean[data.getNumberOfExamples()];
		for(int i=0;i<isClustered.length;i++)
			isClustered[i]=false;
		int countClustered=0;
		while(countClustered!=data.getNumberOfExamples()){
			Cluster c = buildCandidateCluster(data,isClustered);
			C.add(c);
			numclusters++;
			Iterator<Integer> it = c.iterator();
			while(it.hasNext()){
				isClustered[it.next()]=true;
			}
			countClustered+=c.getSize();
		}
		if(numclusters==1)
			throw new ClusteringRadiusException(data.getNumberOfExamples());
		return numclusters;
	}
	/**
	 * Costruisce un cluster per ciascuna
	 * tupla di data non ancora Clusterizzata
	 * in un cluster del ClusterSet e restituisce il Cluster
	 * candiadato piu' popoloso
	 * @param data insieme di esempi/tuple che necessitano di
	 * essere raggruppati in Clusters
	 * @param isClustered informazione booleana
	 * sullo stato di clusterizzazione di una tupla:
	 * <p>
	 * -	false se la tupla considerata non e' ancora assegnata ad alcun
	 * cluster di C;
	 * <p>
	 * -	true altrimenti.
	 * @return il Cluster candidato piu' popoloso
	 */
	private Cluster buildCandidateCluster(Data data,boolean isClustered[]) {
		//ClusterSet temporaneo in cui andro' ad inserire i Cluster temporanei
		ClusterSet cSet = new ClusterSet(true);
		for(int i=0;i<data.getNumberOfExamples();i++)
			if(!isClustered[i]) {
				Cluster cTemp=new Cluster(data.getItemSet(i));
				cSet.add(cTemp);
				for(int j=0;j<data.getNumberOfExamples();j++) 
					if(!isClustered[j])
						if(data.getItemSet(i).getDistance(data.getItemSet(j))<=this.radius)
							cTemp.addData(j);
			}
		return cSet.last();//l'ultimo Cluster corrisponde al piu' popoloso
	}
	/**
	 * Costruttore per la serializzazione della classe QTMiner.
	 * Apre il file identificato da fileName,
	 * legge l'oggetto ivi memorizzato e lo assegna a C.
	 * @param fileName Nome del file.
	 * @throws FileNotFoundException Eccezione sollevata se il file non viene trovato.
	 * @throws IOException Eccezione di tipo I/O.
	 * @throws ClassNotFoundException Eccezione sollevata se la classe non è trovata.
	 */
	public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
		FileInputStream fileInput = new FileInputStream(fileName);
		ObjectInputStream in = new ObjectInputStream(fileInput);
		this.C = (ClusterSet) in.readObject();
		//chiusura degli stream
		in.close();
		fileInput.close();
	}
	/**
	 * Apre il file identificato da fileName e salva 
	 * l'oggetto riferito da C in tale file.
	 * @param fileName Nome del file.
	 * @throws FileNotFoundException Eccezione sollevata se il file non viene trovato.
	 * @throws IOException Eccezione di tipo I/O.
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException {
		FileOutputStream fileOutput = new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(fileOutput);
		out.writeObject(this.C);
		//chiusura degli stream
		out.close();
		fileOutput.close();
	}
	/**
	 * Override del metodo toString di Object.
	 * Restituisce una stringa che rappresenta il ClusterSet.
	 */
	@Override
	public String toString() {
		return this.C.toString();
	}
}