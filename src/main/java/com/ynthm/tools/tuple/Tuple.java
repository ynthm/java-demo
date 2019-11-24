package com.ynthm.tools.tuple;

import java.io.Serializable;
import java.util.*;

public abstract class Tuple implements Iterable<Object>, Serializable, Comparable<Tuple> {
    private static final long serialVersionUID = 1;
    private final Object[] valueArray;
    private final List<Object> valueList;


    protected Tuple(Object... values) {
        this.valueArray = values;
        this.valueList = Arrays.asList(values);
    }

    public abstract int getSize();

    public final Object getValue(int pos) {
        if (pos >= this.getSize()) {
            throw new IllegalArgumentException("Cannot retrieve position " + pos + " in " + this.getClass().getSimpleName() + ". Positions for this class start with 0 and end with " + (this.getSize() - 1));
        } else {
            return this.valueArray[pos];
        }
    }

    public final Iterator<Object> iterator() {
        return this.valueList.iterator();
    }

    public final String toString() {
        return this.valueList.toString();
    }

    public final boolean contains(Object value) {
        Iterator i$ = this.valueList.iterator();

        while (i$.hasNext()) {
            Object val = i$.next();
            if (val == null) {
                if (value == null) {
                    return true;
                }
            } else if (val.equals(value)) {
                return true;
            }
        }

        return false;
    }

    public final boolean containsAll(Collection<?> collection) {
        Iterator i$ = collection.iterator();

        Object value;
        do {
            if (!i$.hasNext()) {
                return true;
            }

            value = i$.next();
        } while (this.contains(value));

        return false;
    }

    public final boolean containsAll(Object... values) {
        if (values == null) {
            throw new IllegalArgumentException("Values array cannot be null");
        } else {
            Object[] arr$ = values;
            int len$ = values.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                Object value = arr$[i$];
                if (!this.contains(value)) {
                    return false;
                }
            }

            return true;
        }
    }

    public final int indexOf(Object value) {
        int i = 0;

        for (Iterator i$ = this.valueList.iterator(); i$.hasNext(); ++i) {
            Object val = i$.next();
            if (val == null) {
                if (value == null) {
                    return i;
                }
            } else if (val.equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public final int lastIndexOf(Object value) {
        for (int i = this.getSize() - 1; i >= 0; --i) {
            Object val = this.valueList.get(i);
            if (val == null) {
                if (value == null) {
                    return i;
                }
            } else if (val.equals(value)) {
                return i;
            }
        }

        return -1;
    }

    public final List<Object> toList() {
        return Collections.unmodifiableList(new ArrayList(this.valueList));
    }

    public final Object[] toArray() {
        return (Object[]) this.valueArray.clone();
    }

    public final int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + (this.valueList == null ? 0 : this.valueList.hashCode());
        return result;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            Tuple other = (Tuple) obj;
            return this.valueList.equals(other.valueList);
        }
    }

    public int compareTo(Tuple o) {
        int tLen = this.valueArray.length;
        Object[] oValues = o.valueArray;
        int oLen = oValues.length;

        for (int i = 0; i < tLen && i < oLen; ++i) {
            Comparable tElement = (Comparable) this.valueArray[i];
            Comparable oElement = (Comparable) oValues[i];
            int comparison = tElement.compareTo(oElement);
            if (comparison != 0) {
                return comparison;
            }
        }

        return Integer.valueOf(tLen).compareTo(oLen);
    }
}