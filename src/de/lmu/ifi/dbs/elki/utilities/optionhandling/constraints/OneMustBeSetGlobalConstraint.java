package de.lmu.ifi.dbs.elki.utilities.optionhandling.constraints;

import de.lmu.ifi.dbs.elki.logging.AbstractLoggable;
import de.lmu.ifi.dbs.elki.logging.LoggingConfiguration;
import de.lmu.ifi.dbs.elki.utilities.Util;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.Parameter;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.ParameterException;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.WrongParameterValueException;

import java.util.List;

/**
 * Represents a global parameter constraint specifying that at least one
 * parameter value of a given list of parameters ({@link Parameter}) has to be
 * set.
 *
 * @author Steffi Wanka
 */
public class OneMustBeSetGlobalConstraint extends AbstractLoggable implements GlobalParameterConstraint {

    /**
     * List of parameters to be checked.
     */
    private List<Parameter<?, ?>> parameters;

    /**
     * Creates a One-Must-Be-Set global parameter constraint. That is, at least
     * one parameter value of the given list of parameters has to be set.
     *
     * @param params list of parameters
     */
    public OneMustBeSetGlobalConstraint(List<Parameter<?, ?>> params) {
        super(LoggingConfiguration.DEBUG);
        parameters = params;
    }

    /**
     * Checks if at least one parameter value of the list of parameters
     * specified is set. If not, a parameter exception is thrown.
     *
     */
    public void test() throws ParameterException {
        for (Parameter<?, ?> p : parameters) {
            if (p.isSet()) {
                return;
            }
        }
        throw new WrongParameterValueException("Global Parameter Constraint Error.\n" +
            "At least one of the parameters " + Util.optionsNamesToString(parameters) + " has to be set.");

	}

    public String getDescription() {
        return "At least one of the parameters " + Util.optionsNamesToString(parameters) + " has to be set.";
    }
}
