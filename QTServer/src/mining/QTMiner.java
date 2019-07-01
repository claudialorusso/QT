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
 * Include l'implementazione dell'algoritmo
 * Quality Threshold
 * @author Lorusso Claudia, Dileo Angela
 */
public class QTMiner implements Serializable{
	/*
	 * La versione serve in fase di deserializzazione per verificare che le classi usate
	 * da chi ha serializzato l'oggetto e chi lo sta deserializzando siano compatibili
	 */
	private static final long serialVersionUID=1L;
	/**
	 * Insieme dei cluster
	 */
	private ClusterSet C;
	/**
	 * Raggio dei cluster
	 */
	private double radius;
	/**
	 * Costruttore della classe QTMiner.
	 * <p>
	 * Crea l'oggetto array riferito da C
	 * ed inizializza radius
	 * con il parametro passato come input
	 * @param radius raggio dei cluster
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
	////////////////////////////////////modifica javadoc////////////////////////
	/**
	 * Esegue l'algoritmo QT,
	 * eseguendo i passi dello pseudo-codice:
	 * <p>
	 * 1. Costruisce un cluster per ciascuna
	 * tupla non ancora clusterizzata,
	 * includendo nel cluster i punti
	 * (non ancora clusterizzati
	 * in alcun altro cluster)
	 * che ricadano nel vicinato sferico
	 * delle tuple avente raggio radius
	 * <p>
	 * 2. Salva il candidato cluster piu' popoloso
	 * e rimuove tutti i punti di tale
	 * cluster dall'elenco delle tuple
	 * ancora da clusterizzare
	 * <p>
	 * 3. Ritorna al passo 1 finche' ci sono
	 * ancora tuple da assegnare ad un cluster
	 * @param data matrice di tuple
	 * @return numero di clusters scoperti
	 */
	public int compute(Data data) throws ClusteringRadiusException {
		//TUTTI QUESTI COMMENTI ANDRANNO CANCELLATI///////////////////////////
		/*
		 * numero di clusters scoperti
		 */
		int numclusters=0;
		/*
		 * creo una matrice isClustered
		 * di booleani avente grandezza pari
		 * al numero degli esempi
		 * presenti nella matrice data
		 */
		boolean isClustered[]=new boolean[data.getNumberOfExamples()];
		/*
		 * imposto tutti i valori della matrice al valore
		 * false, di default;
		 * di default, dunque, imponiamo che quegli
		 * elementi non siano clusterizzati.
		 * Si effettuera' in seguito l'effettivo controllo
		 * sulla clusterizzazione.
		 */
		for(int i=0;i<isClustered.length;i++)
			isClustered[i]=false;
		/*
		 * inizializzo a zero il contatore del numero di cluster scoperti
		 */
		int countClustered=0;
		/*
		 * Il ciclo termina quando il contatore
		 * dei clusters e' diverso dal numero di esempi
		 * presenti in data,
		 * cioe': finche' non ci sono piu' tuple da assegnare
		 * ad un cluster.
		 */
		while(countClustered!=data.getNumberOfExamples())
		{
			/*
			 * Ricerca cluster piu' popoloso:
			 * creo un cluster c che sara' uguale
			 * al cluster piu' popoloso
			 */
			Cluster c = buildCandidateCluster(data,isClustered);
			//aggiungo il nuovo cluster al clusterSet
			C.add(c);
			numclusters++;
			/*
			 * Rimuovo tuple clusterizzate da dataset
			 * impostando il loro valore in isClustered a true
			 */
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
	 * tupla di data non ancora clusterizzata
	 * in un cluster di C e restituisce il cluster
	 * candiadato piu' popoloso
	 * @param data insieme di tuple da raggruppare in cluster
	 * @param isClustered informazione booleana
	 * sullo stato di clusterizzazione di una tupla
	 * (es. isClustered[i]=false se la tupla
	 * i-esima di data non e' ancora assegnata ad alcun
	 * cluster di C,
	 * true altrimenti).
	 * @return il cluster candidato piu' popoloso
	 */
	private Cluster buildCandidateCluster(Data data,boolean isClustered[]) {
		ClusterSet cSet = new ClusterSet(true);//ClusterSet temporaneo in cui andro' ad inserire i cluster temporanei
		for(int i=0;i<data.getNumberOfExamples();i++)
			if(!isClustered[i]) {
				Cluster cTemp=new Cluster(data.getItemSet(i));
				cSet.add(cTemp);
				for(int j=0;j<data.getNumberOfExamples();j++) 
					if(!isClustered[j])
						if(data.getItemSet(i).getDistance(data.getItemSet(j))<=this.radius)
							cTemp.addData(j);
			}
		///////////////////////////////MODIFICARE TUTTI I NOMI MAI SIA LI LASCIAMO COSI'!
		/*Iterator<Cluster> iter = cSet.iterator();
		Cluster massimoRagliano=iter.next(); 
		int max = massimoRagliano.getSize();
		for(int i=1;i<cSet.getSize();i++) {
			Cluster ciccio =  iter.next();
			if(ciccio.getSize()>=max) {
				max = ciccio.getSize();
				massimoRagliano=ciccio;
			}

		}return massimoRagliano;*/

		return cSet.last();
	}

	//AGGIUNTA DEI METODI PER LA SERIALIZZAZIONE E DE-SERIALIZZAZIONE DI C

	/**
	 * Nuovo costruttore della classe QTMiner.
	 * Apre il file identificato da fileName,
	 * legge l'oggetto ivi memorizzato e lo assegna a C.
	 * @param fileName Nome del file.
	 * @throws FileNotFoundException Eccezione sollevata se il file non viene trovato.
	 * @throws IOException Eccezione di tipo I/O.
	 * @throws ClassNotFoundException Eccezione sollevata se la classe non è trovata.
	 */
	//nuovo costruttore della classe QTMiner
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
	 * Restituisce una stringa che rappresenta l'insieme dei cluster [???]
	 */
	@Override
	public String toString() {
		return this.C.toString();
	}

}