/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseFiles;

/**
 *
 * @author prici
 */

    class Pair<A, B> {
    private final A a;
    private final B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A fst() {
        return a;
    }

    public B snd() {
        return b;
    }

    @Override
    public int hashCode() {
        return 3 * a.hashCode() + 7 * b.hashCode();
    }

    @Override
    public String toString() {
        return "{" + a + ", " + b + "}";
    }

    @Override
    public boolean equals(Object o) {
        if ((o instanceof Pair<?, ?>)) {
            Pair<?, ?> other = (Pair<?, ?>) o;
            return other.fst().equals(a) && other.snd().equals(b);
        }
        return false;
    }
}
    

