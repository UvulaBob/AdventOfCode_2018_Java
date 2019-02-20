import java.util.ArrayDeque;

class CircleDeque<T> extends ArrayDeque<T> {
    void rotate(int num) {
        if (num == 0) return;
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                T t = this.removeLast();
                this.addFirst(t);
            }
        } else {
            for (int i = 0; i < Math.abs(num) - 1; i++) {
                T t = this.remove();
                this.addLast(t);
            }
        }

    }
}