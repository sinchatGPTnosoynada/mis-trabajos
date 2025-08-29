package pe.edu.upeu.claseinterna;

public class claseExterna {

    int a, b;

    class claseinterna1 {

        double r;

        double sumar() {

            return a + b;
        }

    }

    class claseinterna2 {

        double r;

        double restar() {
            r = a - b;
            return r;
        }

    }
}
    class claseExterna2 {

        public static void main(String[] args) {

            claseExterna cs = new claseExterna();

            cs.a = 10;
            cs.b = 20;

            claseExterna.claseinterna1 ci1 = cs.new claseinterna1();
            System.out.println(ci1.sumar());

            claseExterna.claseinterna2 ci2 = cs.new claseinterna2();
            System.out.println(ci2.restar());

        }

    }
