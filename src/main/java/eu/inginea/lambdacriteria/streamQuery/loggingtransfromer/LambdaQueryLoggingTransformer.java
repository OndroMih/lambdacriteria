package eu.inginea.lambdacriteria.streamQuery.loggingtransfromer;

import com.trigersoft.jaque.expression.ExpressionType;
import eu.inginea.lambdacriteria.streamQuery.Literal;
import eu.inginea.lambdacriteria.streamQuery.QueryMapping;
import eu.inginea.lambdacriteria.streamQuery.QueryVisitor;
import eu.inginea.lambdacriteria.streamQuery.Term;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Describes transformations from lambda expressions in query stream to final query like JPQL or Criteria
 */
public class LambdaQueryLoggingTransformer implements QueryMapping, QueryVisitor {
    private final Map<Object,Literal> literalsMap = new HashMap<>();
    private final Collection<LiteralMapper> literalMappers = new ArrayList<>();
    
    {
        OperationLiteral opEqual = new OperationLiteral(ExpressionType.Equal);
        addLiteralsMapping(opEqual, ExpressionType.Equal);
        addLiteralMapper(new MethodLiteral(opEqual, "equals", Object.class));
    }

    @Override
    public Optional<Literal> getTermForExpression(Object expr) {
        Optional<Literal> literal = Optional.ofNullable(literalsMap.get(expr));
        if (!literal.isPresent()) {
            literal = literalMappers.stream()
                    .map(mapper -> mapper.getLiteral(expr))
                    .filter(l -> l != null)
                    .findAny();
        }
        return literal;
    }

    @Override
    public void visit(Term literal) {
        System.out.println("Log: " + literal);
    }

    private void addLiteralsMapping(Literal value, Object key) {
        if (key != null) {
            literalsMap.put(key, value);
        }
    }

    private void addLiteralMapper(LiteralMapper mapper) {
        literalMappers.add(mapper);
    }

    private interface LiteralMapper {
        public Literal getLiteral(Object key);
    }
    
    private class MethodLiteral implements LiteralMapper {
        private final String methodName;
        private final List<Class<?>> parameterTypes;
        private final Literal targetLiteral;

        public MethodLiteral(Literal targetLiteral, String methodName, Class<?>... parameterTypes) {
            this.methodName = methodName;
            this.parameterTypes = Arrays.asList(parameterTypes);
            this.targetLiteral = targetLiteral;
        }
        
        @Override
        public Literal getLiteral(Object o) {
            if (o instanceof Method) {
                Method m = (Method)o;
                if (m.getName().equals(methodName) 
                        && Arrays.asList(m.getParameterTypes()).equals(parameterTypes)) {
                    return targetLiteral;
                }
            }
            return null;
        }
    }
    
    private class OperationLiteral implements Literal {
        int operation;

        public OperationLiteral(int operation) {
            this.operation = operation;
        }
        
        @Override
        public String toString() {
            return ExpressionType.toString(operation);
        }
    }
    
}