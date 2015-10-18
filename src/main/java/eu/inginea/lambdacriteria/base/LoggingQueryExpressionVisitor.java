package eu.inginea.lambdacriteria.base;

import com.trigersoft.jaque.expression.BinaryExpression;
import com.trigersoft.jaque.expression.ConstantExpression;
import com.trigersoft.jaque.expression.Expression;
import com.trigersoft.jaque.expression.InvocationExpression;
import com.trigersoft.jaque.expression.LambdaExpression;
import com.trigersoft.jaque.expression.MemberExpression;
import com.trigersoft.jaque.expression.ParameterExpression;
import com.trigersoft.jaque.expression.UnaryExpression;
import eu.inginea.lambdacriteria.base.QueryExpressionVisitor;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoggingQueryExpressionVisitor extends QueryExpressionVisitor {
    private Consumer<String> logger = System.out::println;

    public LoggingQueryExpressionVisitor() {
    } 

    @Override
    public Expression visit(UnaryExpression e) {
        info(e);
        Expression visitResult = super.visit(e);
        return visitResult; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression visit(ParameterExpression e) {
        info(e);
        Expression visitResult = super.visit(e);
        return visitResult; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression visit(MemberExpression e) {
        info(e);
        Expression visitResult = super.visit(e);
        return visitResult; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression visit(LambdaExpression<?> e) {
        info(e);
        Expression visitResult = super.visit(e);
        return visitResult; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression visit(InvocationExpression e) {
        info(e);
        Expression visitResult = super.visit(e);
        return visitResult; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression visit(ConstantExpression e) {
        info(e);
        Expression visitResult = super.visit(e);
        return visitResult; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expression visit(BinaryExpression e) {
        info(e);
        Expression visitResult = super.visit(e);
        return visitResult; //To change body of generated methods, choose Tools | Templates.
    }

    protected void info(Object loggedValue) {
        String msg = createLogMessage(loggedValue);
        String prefix = getPrefix();
        logger.accept(prefix + msg);
    }

    protected String getPrefix() {
        return IntStream.range(0, level).mapToObj((i) -> "-").collect(Collectors.joining());
    }

    protected String createLogMessage(Object loggedValue) {
        String msg;
        if (loggedValue != null) {
            if (loggedValue instanceof Expression) {
                msg = "[" + loggedValue.getClass().getSimpleName() + "]: " + loggedValue;
            } else {
                msg = "MESSAGE:" + loggedValue;
            }
        } else {
            msg = ">>NULL<<";
        }
        return msg;
    }

}