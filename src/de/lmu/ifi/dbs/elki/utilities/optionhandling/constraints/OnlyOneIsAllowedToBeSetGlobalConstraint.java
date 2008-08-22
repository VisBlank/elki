package de.lmu.ifi.dbs.elki.utilities.optionhandling.constraints;

import de.lmu.ifi.dbs.elki.logging.AbstractLoggable;
import de.lmu.ifi.dbs.elki.logging.LoggingConfiguration;
import de.lmu.ifi.dbs.elki.utilities.Util;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.Parameter;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.ParameterException;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.WrongParameterValueException;

import java.util.List;
import java.util.Vector;

/**
 * Global parameter constraint specifying that only one parameter of a list of
 * parameters ({@link Parameter}) is allowed to be set.
 *
 * @author Steffi Wanka
 */
public class OnlyOneIsAllowedToBeSetGlobalConstraint extends AbstractLoggable implements GlobalParameterConstraint {

    /**
     * List of parameters to be checked.
     */
    private List<Parameter<?, ?>> parameters;

    /**
     * Constructs a global parameter constraint for testing if only one
     * parameter of a list of parameters is set.
     *
     * @param params list of parameters to be checked
     */
    public OnlyOneIsAllowedToBeSetGlobalConstraint(List<Parameter<?, ?>> params) {
        super(LoggingConfiguration.DEBUG);
        parameters = params;
    }

    /**
     * Checks if only one parameter of a list of parameters is set. If not, a
     * parameter exception is thrown.
     *
     */
    public void test() throws ParameterException {
        Vector<String> set = new Vector<String>();
        for (Parameter<?, ?> p : parameters) {
            if (p.isSet()) {
                set.add(p.getName());
            }
        }
        if (set.size() > 1) {
            throw new WrongParameterValueException("Global Parameter Constraint Error.\n" +
                "Only one of the parameters " + Util.optionsNamesToString(parameters) + " is allowed to be set. " +
                "Parameters currently set: " + set.toString());
        }
    }

    public String getDescription() {
        return "Only one of the parameters " + Util.optionsNamesToString(parameters) + " is allowed to be set.";
    }

}
