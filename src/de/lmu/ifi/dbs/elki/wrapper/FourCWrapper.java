package de.lmu.ifi.dbs.elki.wrapper;

import de.lmu.ifi.dbs.elki.algorithm.AbortException;
import de.lmu.ifi.dbs.elki.algorithm.clustering.correlation.FourC;
import de.lmu.ifi.dbs.elki.algorithm.clustering.subspace.PreDeCon;
import de.lmu.ifi.dbs.elki.preprocessing.FourCPreprocessor;
import de.lmu.ifi.dbs.elki.utilities.Util;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.DoubleParameter;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.Flag;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.IntParameter;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.OptionID;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.ParameterException;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.PatternParameter;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.constraints.GreaterConstraint;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.constraints.GreaterEqualConstraint;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.constraints.LessEqualConstraint;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.constraints.ParameterConstraint;
import de.lmu.ifi.dbs.elki.varianceanalysis.LimitEigenPairFilter;

import java.util.List;
import java.util.Vector;

/**
 * A wrapper for the 4C algorithm. Performs an attribute wise normalization on
 * the database objects.
 *
 * @author Arthur Zimek
 *         todo parameter
 */
public class FourCWrapper extends NormalizationWrapper {

    /**
     * Parameter to specify the maximum radius of the neighborhood to be considered,
     * must be suitable to {@link de.lmu.ifi.dbs.elki.distance.distancefunction.LocallyWeightedDistanceFunction}.
     * <p>Key: {@code -projdbscan.epsilon} </p>
     */
    private final PatternParameter EPSILON_PARAM = new PatternParameter(FourC.EPSILON_ID);


    /**
     * Parameter to specify the threshold for minimum number of points in
     * the epsilon-neighborhood of a point,
     * must be an integer greater than 0.
     * <p>Key: {@code -projdbscan.minpts} </p>
     */
    private final IntParameter MINPTS_PARAM = new IntParameter(
        FourC.MINPTS_ID,
        new GreaterConstraint(0));

    /**
     * Parameter to specify the intrinsic dimensionality of the clusters to find,
     * must be an integer greater than 0.
     * <p>Key: {@code -projdbscan.lambda} </p>
     */
    private final IntParameter LAMBDA_PARAM = new IntParameter(
        PreDeCon.LAMBDA_ID,
        new GreaterConstraint(0));

    /**
     * Parameter delta.
     */
    private DoubleParameter delta;

    /**
     * Absolute flag.
     */
    private Flag absolute;

    /**
     * Main method to run this wrapper.
     *
     * @param args the arguments to run this wrapper
     */
    public static void main(String[] args) {
        FourCWrapper wrapper = new FourCWrapper();
        try {
            wrapper.setParameters(args);
            wrapper.run();
        }
        catch (ParameterException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            wrapper.exception(wrapper.optionHandler.usage(e.getMessage()), cause);
        }
        catch (AbortException e) {
            wrapper.verbose(e.getMessage());
        }
        catch (Exception e) {
            wrapper.exception(wrapper.optionHandler.usage(e.getMessage()), e);
        }
    }

    /**
     * Adds parameters
     * {@link #EPSILON_PARAM}, {@link #MINPTS_PARAM}, {@link #LAMBDA_PARAM}, {@link } and flag {@link } todo
     * to the option handler additionally to parameters of super class.
     */
    public FourCWrapper() {
        super();
        // epsilon
        addOption(EPSILON_PARAM);

        // minpts
        addOption(MINPTS_PARAM);

        // lambda
        addOption(LAMBDA_PARAM);

        // delta
        List<ParameterConstraint<Number>> cons = new Vector<ParameterConstraint<Number>>();
        cons.add(new GreaterEqualConstraint(0));
        cons.add(new LessEqualConstraint(1));
        delta = new DoubleParameter(OptionID.EIGENPAIR_FILTER_DELTA, cons);
        delta.setDefaultValue(LimitEigenPairFilter.DEFAULT_DELTA);
        optionHandler.put(delta);

        // absolute flag
        absolute = new Flag(OptionID.EIGENPAIR_FILTER_ABSOLUTE);
        optionHandler.put(absolute);
    }

    @Override
    public List<String> getKDDTaskParameters() {
        List<String> parameters = super.getKDDTaskParameters();

        // 4C algorithm
        Util.addParameter(parameters, OptionID.ALGORITHM, FourC.class.getName());

        // epsilon
        Util.addParameter(parameters, EPSILON_PARAM, getParameterValue(EPSILON_PARAM));

        // minpts
        Util.addParameter(parameters, MINPTS_PARAM, Integer.toString(getParameterValue(MINPTS_PARAM)));

        // lambda
        Util.addParameter(parameters, LAMBDA_PARAM, Integer.toString(getParameterValue(LAMBDA_PARAM)));

        // delta
        Util.addParameter(parameters, delta, getParameterValue(delta).toString());

        // absolute flag
        if (optionHandler.isSet(absolute))
            Util.addFlag(parameters, absolute);

        return parameters;
    }
}
