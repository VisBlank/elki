package de.lmu.ifi.dbs.algorithm.classifier;

import de.lmu.ifi.dbs.data.ClassLabel;
import de.lmu.ifi.dbs.data.DatabaseObject;
import de.lmu.ifi.dbs.database.Database;
import de.lmu.ifi.dbs.distance.Distance;
import de.lmu.ifi.dbs.logging.LoggingConfiguration;
import de.lmu.ifi.dbs.utilities.Description;
import de.lmu.ifi.dbs.utilities.QueryResult;
import de.lmu.ifi.dbs.utilities.optionhandling.AttributeSettings;
import de.lmu.ifi.dbs.utilities.optionhandling.OptionHandler;
import de.lmu.ifi.dbs.utilities.optionhandling.ParameterException;
import de.lmu.ifi.dbs.utilities.optionhandling.WrongParameterValueException;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * KNNClassifier classifies instances based on the class distribution among the
 * k nearest neighbors in a database.
 *
 * @author Arthur Zimek (<a
 *         href="mailto:zimek@dbs.ifi.lmu.de">zimek@dbs.ifi.lmu.de</a>)
 */
public class KNNClassifier<O extends DatabaseObject, D extends Distance<D>>
extends DistanceBasedClassifier<O, D> {
  

 
  /**
   * Generated serial version UID.
   */
  private static final long serialVersionUID = 5467968122892109545L;

  /**
   * The parameter k.
   */
  public static final String K_P = "k";

  /**
   * Default value for the parameter k.
   */
  public static final int K_DEFAULT = 1;

  /**
   * Description for parameter k.
   */
  public static final String K_D = "<int>number of neighbors (>0) to take into account for classification (default=" + K_DEFAULT + ")";

  /**
   * Holds the database where the classification is to base on.
   */
  protected Database<O> database;

  /**
   * Holds the value for k.
   */
  protected int k = K_DEFAULT;

  /**
   * Provides a KNNClassifier.
   */
  public KNNClassifier() {
    super();
    parameterToDescription.put(K_P + OptionHandler.EXPECTS_VALUE, K_D);
//    optionHandler = new OptionHandler(parameterToDescription,
//                                      KNNClassifier.class.getName());
  }

  /**
   * Checks whether the database has the class labels set. Collects the class
   * labels available n the database. Holds the database to lazily classify
   * new instances later on.
   *
   * @see Classifier#buildClassifier(de.lmu.ifi.dbs.database.Database,
   *      de.lmu.ifi.dbs.data.ClassLabel[])
   */
  public void buildClassifier(Database<O> database, ClassLabel[] labels)
  throws IllegalStateException {
    this.setLabels(labels);
    this.database = database;
  }

  /**
   * Provides a class distribution for the given instance. The distribution is
   * the relative value for each possible class among the k nearest neighbors
   * of the given instance in the previously specified database.
   *
   * @see Classifier#classDistribution(DatabaseObject)
   */
  public double[] classDistribution(O instance) throws IllegalStateException {
    try {
      double[] distribution = new double[getLabels().length];
      int[] occurences = new int[getLabels().length];

      List<QueryResult<D>> query = database.kNNQueryForObject(instance,
                                                              k, getDistanceFunction());
      for (QueryResult<D> neighbor : query) {
        int index = Arrays.binarySearch(getLabels(),
                                        (CLASS.getType().cast(database.getAssociation(CLASS,
                                                                                      neighbor.getID()))));
        if (index >= 0) {
          occurences[index]++;
        }
      }
      for (int i = 0; i < distribution.length; i++) {
        distribution[i] = ((double) occurences[i])
                          / (double) query.size();
      }
      return distribution;
    }
    catch (NullPointerException e) {
      IllegalArgumentException iae = new IllegalArgumentException(e);
      iae.fillInStackTrace();
      throw iae;
    }
  }

  /**
   * @see de.lmu.ifi.dbs.algorithm.Algorithm#getDescription()
   */
  public Description getDescription() {
    return new Description(
    "kNN-classifier",
    "kNN-classifier",
    "lazy classifier classifies a given instance to the majority class of the k-nearest neighbors",
    "");
  }

  /**
   * @see de.lmu.ifi.dbs.algorithm.Algorithm#getAttributeSettings()
   */
  @Override
  public List<AttributeSettings> getAttributeSettings() {
    List<AttributeSettings> attributeSettings = super
    .getAttributeSettings();

    AttributeSettings mySettings = attributeSettings.get(0);
    mySettings.addSetting(K_P, Integer.toString(k));

    return attributeSettings;
  }

  /**
   * Sets the parameter k, if speicified. Otherwise, k will remain at the
   * default value 1 or the previously specified value, respectively.
   *
   * @see de.lmu.ifi.dbs.utilities.optionhandling.Parameterizable#setParameters(String[])
   */
  @Override
  public String[] setParameters(String[] args) throws ParameterException {
    String[] remainingParameters = super.setParameters(args);

    if (optionHandler.isSet(K_P)) {
      String kString = optionHandler.getOptionValue(K_P);
      try {
        k = Integer.parseInt(kString);
        if (k <= 0) {
          throw new WrongParameterValueException(K_P, kString, K_D);
        }
      }
      catch (NumberFormatException e) {
        throw new WrongParameterValueException(K_P, kString, K_D, e);
      }
    }
    setParameters(args, remainingParameters);
    return remainingParameters;
  }

  /**
   * @see Classifier#model()
   */
  public String model() {
    return "lazy learner - provides no model";
  }

}
