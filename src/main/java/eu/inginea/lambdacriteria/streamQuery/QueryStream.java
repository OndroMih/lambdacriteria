package eu.inginea.lambdacriteria.streamQuery;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public interface QueryStream<T> {
    QueryStream<T> filter(QueryPredicate<? super T> predicate);
    <R, A> R collect(Collector<? super T, A, R> collector);
    
    /***********************************************************************
     * stub methods for API eperiments, to be implemented later if needed
     ***********************************************************************/
    
    default QueryStream<T> select() {
        return null;
    }

    default QueryStream<T> select(Function<T, ?> selector) {
        return null;
    }

    default <R, A> List<?> collectSelection(Collector<? super T, A, R> collector) {
        return null;
    }
    
    default QueryStream<T> join(BiFunction<T, Util,? extends QueryStream<?>> mapper) {
        return null;
    }
    
    /**
     * Conversion function to standard interface
     */
    default Stream<T> toStream() {
        return null;
    }

    public interface Util {
        <TYPE> QueryStream<TYPE> toStream(Collection<TYPE> coll);
    }
}
