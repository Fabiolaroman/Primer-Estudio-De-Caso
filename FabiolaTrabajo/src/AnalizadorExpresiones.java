import java.util.Scanner;

public class AnalizadorExpresiones {


    public static boolean verificarBalance(String expresion) {
        Pila pila = new Pila();
        String[] tokens = tokenizar(expresion);

        for (String t : tokens) {
            if (t.equals("(") || t.equals("[") || t.equals("{")) {
                pila.push(t);
            } else if (t.equals(")") || t.equals("]") || t.equals("}")) {
                if (pila.estaVacia()) return false;
                String tope = pila.pop();
                if ((t.equals(")") && !tope.equals("(")) ||
                        (t.equals("]") && !tope.equals("[")) ||
                        (t.equals("}") && !tope.equals("{"))) {
                    return false;
                }
            }
        }
        return pila.estaVacia();
    }


    public static String infijaAPostfija(String expresion) {
        StringBuilder salida = new StringBuilder();
        Pila pila = new Pila();
        String[] tokens = tokenizar(expresion);

        for (String t : tokens) {
            if (esNumero(t) || esVariable(t)) {
                salida.append(t).append(" ");
            } else if (t.equals("(")) {
                pila.push(t);
            } else if (t.equals(")")) {
                while (!pila.estaVacia() && !pila.peek().equals("(")) {
                    salida.append(pila.pop()).append(" ");
                }
                if (!pila.estaVacia()) pila.pop(); // eliminar '('
            } else if (esOperador(t)) {
                while (!pila.estaVacia() && prioridad(pila.peek()) >= prioridad(t)) {
                    salida.append(pila.pop()).append(" ");
                }
                pila.push(t);
            }
        }

        while (!pila.estaVacia()) {
            salida.append(pila.pop()).append(" ");
        }

        return salida.toString().trim();
    }

    // ===================== Evaluación de expresión postfija =====================
    public static int evaluarPostfija(String expresion) {
        String[] tokens = expresion.split("\\s+");
        int[] pila = new int[tokens.length];
        int tope = -1;

        for (String t : tokens) {
            if (esNumero(t)) {
                pila[++tope] = Integer.parseInt(t);
            } else if (esOperador(t)) {
                int b = pila[tope--];
                int a = pila[tope--];
                int resultado = 0;

                switch (t) {
                    case "+": resultado = a + b; break;
                    case "-": resultado = a - b; break;
                    case "*": resultado = a * b; break;
                    case "/": resultado = a / b; break;
                }
                pila[++tope] = resultado;
            }
        }
        return pila[tope];
    }

    // ===================== Funciones de apoyo =====================
    public static boolean esOperador(String t) {
        return t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/");
    }

    public static boolean esNumero(String t) {
        return t.matches("\\d+");
    }

    public static boolean esVariable(String t) {
        return t.matches("[a-zA-Z]+");
    }

    public static int prioridad(String t) {
        switch (t) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
            default: return -1;
        }
    }

    // Separa la expresión en tokens (números, variables, operadores y paréntesis)
    public static String[] tokenizar(String expresion) {
        expresion = expresion.replaceAll("\\s+", "");
        return expresion.split("(?<=[-+*/()])|(?=[-+*/()])");
    }

    // ===================== MENÚ PRINCIPAL =====================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        String expresion;

        do {
            System.out.println("\n=== Analizador de Expresiones con Pilas ===");
            System.out.println("1. Verificar balance de paréntesis");
            System.out.println("2. Convertir expresión infija a postfija");
            System.out.println("3. Evaluar expresión aritmética");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la expresión: ");
                    expresion = sc.nextLine();
                    boolean balance = verificarBalance(expresion);
                    System.out.println("Paréntesis balanceados: " + (balance ? "Sí" : "No"));
                    break;

                case 2:
                    System.out.print("Ingrese la expresión: ");
                    expresion = sc.nextLine();
                    System.out.println("Expresión postfija: " + infijaAPostfija(expresion));
                    break;

                case 3:
                    System.out.print("Ingrese la expresión (solo números y + - * /): ");
                    expresion = sc.nextLine();
                    String postfija = infijaAPostfija(expresion);
                    int resultado = evaluarPostfija(postfija);
                    System.out.println("Expresión postfija: " + postfija);
                    System.out.println("Resultado: " + resultado);
                    break;

                case 4:
                    System.out.println("¡Gracias por usar el analizador!");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 4);

        sc.close();
    }
}
