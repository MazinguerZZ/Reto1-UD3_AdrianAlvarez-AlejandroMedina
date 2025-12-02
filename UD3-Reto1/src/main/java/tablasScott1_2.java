import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase que implementa un menú en consola para realizar operaciones CRUD
 * sobre la entidad {@code Scott} utilizando JPA.
 * Permite leer, crear, actualizar y eliminar registros de la tabla Scott.
 */
public class tablasScott1_2 {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadScott");
    private static EntityManager em = emf.createEntityManager();
    private static Scanner sc = new Scanner(System.in);

    /**
     * Método principal que ejecuta el menú interactivo.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        int opcion = 0;

        do {
            System.out.println("\n========== MENÚ SCOTT ==========");
            System.out.println("1. Leer empleados");
            System.out.println("2. Actualizar salario");
            System.out.println("3. Eliminar todos los empleados");
            System.out.println("4. Crear un empleado nuevo");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    leerEmpleados();
                    break;
                case 2:
                    actualizarSalario();
                    break;
                case 3:
                    eliminarTodos();
                    break;
                case 4:
                    crearEmpleado();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 5);

        em.close();
        emf.close();
        sc.close();
    }


    /**
     * Lee e imprime todos los empleados almacenados en la base de datos.
     */
    private static void leerEmpleados() {
        System.out.println("\n--- LISTA DE EMPLEADOS ---");

        List<Scott> lista = em.createQuery("SELECT a FROM Scott a", Scott.class).getResultList();

        if (lista.isEmpty()) {
            System.out.println("No hay empleados en la tabla.");
        } else {
            for (Scott a : lista) {
                imprimirEmpleado(a);
            }
        }
    }


    /**
     * Actualiza el salario de un empleado según su número EMPNO.
     * Solicita los datos por consola.
     */
    private static void actualizarSalario() {
        System.out.print("\nIntroduce EMPNO del empleado a actualizar: ");
        long empno = sc.nextLong();

        System.out.print("Introduce el nuevo salario: ");
        double nuevoSal = sc.nextDouble();

        em.getTransaction().begin();

        Scott emp = em.find(Scott.class, empno);

        if (emp != null) {
            emp.setSal((int) nuevoSal);
            em.merge(emp);
            em.getTransaction().commit();
            System.out.println("Salario actualizado correctamente.");
        } else {
            em.getTransaction().rollback();
            System.out.println("Empleado no encontrado.");
        }
    }


    /**
     * Elimina todos los registros de la entidad Scott.
     */
    private static void eliminarTodos() {
        System.out.println("\nEliminando todos los registros...");

        em.getTransaction().begin();

        List<Scott> lista = em.createQuery("SELECT a FROM Scott a", Scott.class).getResultList();
        for (Scott s : lista) {
            em.remove(s);
        }

        em.getTransaction().commit();
        System.out.println("Todos los registros han sido eliminados.");
    }


    /**
     * Crea un nuevo registro de la entidad Scott solicitando los datos por consola.
     */
    private static void crearEmpleado() {
        System.out.println("\n--- CREAR NUEVO EMPLEADO ---");

        System.out.print("EMPNO: ");
        int empno = sc.nextInt();
        System.out.print("ENAME: ");
        String ename = sc.next();
        System.out.print("JOB: ");
        String job = sc.next();
        System.out.print("MGR: ");
        int mgr = sc.nextInt();
        System.out.print("SAL: ");
        int sal = sc.nextInt();
        System.out.print("COMM: ");
        int comm = sc.nextInt();
        System.out.print("DEPTNO: ");
        int deptno = sc.nextInt();

        Scott nuevo = new Scott(empno, ename, job, mgr, sal, comm, deptno);

        em.getTransaction().begin();
        em.persist(nuevo);
        em.getTransaction().commit();

        System.out.println("Empleado creado correctamente.");
    }


    /**
     * Imprime por consola los datos de un objeto {@code Scott}.
     *
     * @param a instancia de la entidad Scott a mostrar
     */
    private static void imprimirEmpleado(Scott a) {
        System.out.println(
                "EMPNO: " + a.getEmpno() +
                ", ENAME: " + a.getEname() +
                ", JOB: " + a.getJob() +
                ", MGR: " + a.getMgr() +
                ", SAL: " + a.getSal() +
                ", COMM: " + a.getComm() +
                ", DEPTNO: " + a.getDeptno()
        );
    }
}