import java.util.Random;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.meta.ClassificationViaClustering;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemoveWithValues;
import weka.classifiers.functions.supportVector.*;

/**
 * Prioritizes gene as described in the paper
 * Prioritization of Retinal Disease Genes: An Integrative Approach
 * AH Wagner, et. al
 *
 * @author KR Taylor
 */
public class LogisticRegressionPrioritization {

	static int numCentroids = 2;
	
	public static void main(String args[]) throws Exception {

		int index = 0;
		
		Random rand = new Random();
		
		if(args.length < 1)
			System.err.println("You must supply a dataset file as the first argument.");
		
		DataSource source = new DataSource(args[0]);
		Instances dataset = source.getDataSet();
		
		
		 
		 if (dataset.classIndex() == -1)
			 dataset.setClassIndex(dataset.numAttributes() - 1);
		 

		 NumericToNominal filter = new NumericToNominal();
		 
		 String filterOptions[] = {"-R","last"};
		 filter.setOptions( filterOptions );
		 filter.setInputFormat(dataset);
		 dataset = Filter.useFilter(dataset, filter);
		 
		 Normalize normFilter = new Normalize();
		 String normFilterOptions[] = {"-S","1.0","-T","0.0"};
		 normFilter.setOptions( normFilterOptions );
		 normFilter.setInputFormat(dataset);
		 dataset = Filter.useFilter(dataset, filter);
		 
		 Instances cleanedLabels = filterLabels(dataset);
		 
		 int start = 0;
		 int end = cleanedLabels.numInstances();
		 System.out.println("Gene,Probability");
		 
		 //Perform Leave one out
		for(int i = start; i < end; i++) {
			 
			 Instances test = new Instances(cleanedLabels,1);
			 
			 test.add( cleanedLabels.get( i ) );

			 Instances train = new Instances(cleanedLabels);
			 
			 train.remove(i);
			 
			 
			  Logistic classifier = new Logistic();
			 String options[] = {"-R","1.0E-8","-M","-1"};
			 classifier.setOptions(options);
			 classifier.buildClassifier(train);
			  
			 classifier.buildClassifier(train);
			 
			 double dist[] = classifier.distributionForInstance(test.get(0));
			 
			 System.out.println(dataset.get(i).stringValue(0)+","+dist[1]);


		}

	}
	
	
	
	static Instances filterLabels(Instances dataset) throws Exception {
		 
		Remove filter = new Remove();
		 String[] filterOptions = {"-R","first"};
		 
		 filter.setOptions(filterOptions);
		 filter.setInputFormat(dataset);
		 return Filter.useFilter(dataset, filter);
	}
	
}
