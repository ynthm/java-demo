package com.ynthm.tools.tuple;

import java.util.Spliterator;
import java.util.function.Consumer;

public final class Tuple4<A, B, C, D> extends Tuple implements IValue0<A>, IValue1<B>, IValue2<C>, IValue3<D> {
    private static final long serialVersionUID = 2445136048617019549L;
    private static final int SIZE = 4;
    private final A val0;
    private final B val1;
    private final C val2;
    private final D val3;

    public Tuple4(A val0, B val1, C val2, D val3) {
        super(new Object[]{val0, val1, val2, val3});
        this.val0 = val0;
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    public static <A, B, C, D> Tuple4<A, B, C, D> of(A value0, B value1, C value2, D value3) {
        return new Tuple4(value0, value1, value2, value3);
    }

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public void forEach(Consumer<? super Object> action) {

    }

    @Override
    public Spliterator<Object> spliterator() {
        return null;
    }

    @Override
    public A getValue0() {
        return val0;
    }

    @Override
    public B getValue1() {
        return val1;
    }

    @Override
    public C getValue2() {
        return val2;
    }

    @Override
    public D getValue3() {
        return val3;
    }
}
