public class Pair<T> {
    private T a, b;

    public Pair(T x, T y) {
        this.a = x;
        this.b = y;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?> pair = (Pair<?>) o;

        if (a != null ? !a.equals(pair.a) : pair.a != null) return false;
        return b != null ? b.equals(pair.b) : pair.b == null;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
