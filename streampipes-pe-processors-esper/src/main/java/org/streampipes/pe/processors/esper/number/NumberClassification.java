package org.streampipes.pe.processors.esper.number;

import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.soda.Expression;
import com.espertech.esper.client.soda.Expressions;
import com.espertech.esper.client.soda.FilterStream;
import com.espertech.esper.client.soda.FromClause;
import com.espertech.esper.client.soda.SelectClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streampipes.wrapper.esper.EsperEventEngine;

import java.util.ArrayList;
import java.util.List;

public class NumberClassification extends EsperEventEngine<NumberClassificationParameters> {
	private static final Logger logger = LoggerFactory.getLogger(NumberClassification.class.getSimpleName());

	@Override
	protected List<String> statements(NumberClassificationParameters bindingParameters) {
		
		List<String> statements = new ArrayList<String>();
		
		for(DataClassification data : bindingParameters.getDomainConceptData()) {
			EPStatementObjectModel model = new EPStatementObjectModel();
			model.selectClause(makeSelectClause(data, bindingParameters));
//			model.selectClause(makeSelectClause(data));
			model.fromClause(new FromClause().add(FilterStream.create(fixEventName(bindingParameters.getInputStreamParams().get(0).getInName())))); 

			model.whereClause(getWhereClause(bindingParameters.getPropertyName(), data.getMinValue(), data.getMaxValue()));
			logger.info("Generated EPL: " +model.toEPL());
			
			statements.add(model.toEPL());
		}

		return statements;
	}

	private SelectClause makeSelectClause(DataClassification dataClassification, NumberClassificationParameters bindingParameters) {
		SelectClause clause = SelectClause.create();
		for(String property : bindingParameters.getInputStreamParams().get(0).getAllProperties()) {
			clause.add(property);
		}
		
		clause.add(Expressions.constant(dataClassification.getLabel()), bindingParameters.getOutputProperty());
		return clause;
	}
	
	private Expression getWhereClause(String propertyName, double minValue, double maxValue)
	{
		return Expressions.between(Expressions.property(propertyName), Expressions.constant(minValue), Expressions.constant(maxValue));
	}

}
