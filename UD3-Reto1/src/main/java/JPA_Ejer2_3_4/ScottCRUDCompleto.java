package JPA_Ejer2_3_4;

import jakarta.persistence.*;
import java.util.List;
import java.util.Scanner;

/**
 * Sistema CRUD completo para gestionar empleados, proyectos y habilidades.
 * Permite crear, leer, actualizar y eliminar datos mediante un menú interactivo.
 * Utiliza JPA para conectar con la base de datos y manejar las relaciones entre entidades.
 */
public class ScottCRUDCompleto {
    
    /**
     * Método principal que inicia la aplicación.
     * Crea la conexión con la base de datos y muestra el menú principal.
     * Controla todas las operaciones y cierra los recursos al finalizar.
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadScott2");
        EntityManager em = emf.createEntityManager();
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.println("\n=== MENÚ CRUD SCOTT2 ===");
                System.out.println("1. CREATE - Crear datos iniciales");
                System.out.println("2. READ - Mostrar todos los datos");
                System.out.println("3. UPDATE - Modificar empleado");
                System.out.println("4. DELETE - Eliminar proyecto");
                System.out.println("5. Salir");
                System.out.print("Selecciona opción: ");
                
                int opcion = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcion) {
                    case 1:
                        createDatos(em);
                        break;
                    case 2:
                        readDatos(em);
                        break;
                    case 3:
                        updateDatos(em, scanner);
                        break;
                    case 4:
                        deleteDatos(em, scanner);
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
            em.close();
            emf.close();
        }
    }
    
    /**
     * Crea datos iniciales de prueba: 3 empleados, 3 habilidades y 3 proyectos.
     * Establece las relaciones entre ellos y guarda todo en la base de datos.
     */
    private static void createDatos(EntityManager em) {
        em.getTransaction().begin();
        
        try {
            // Crear empleados
            Scott2 emp1 = new Scott2(7369L, "SMITH", "CLERK", 7902, 800, null, 20);
            Scott2 emp2 = new Scott2(7499L, "ALLEN", "SALESMAN", 7698, 1600, 300, 30);
            Scott2 emp3 = new Scott2(7521L, "WARD", "SALESMAN", 7698, 1250, 500, 30);
            
            // Crear habilidades
            Habilidad h1 = new Habilidad("Java", "Avanzado");
            Habilidad h2 = new Habilidad("SQL", "Intermedio");
            Habilidad h3 = new Habilidad("HTML", "Básico");
            
            // Crear proyectos
            Proyecto p1 = new Proyecto("Sistema Contable", "Desarrollo principal");
            Proyecto p2 = new Proyecto("App Web", "Aplicación web ventas");
            Proyecto p3 = new Proyecto("Base de Datos", "Migración BD");
            
            // Establecer relaciones usando los métodos auxiliares
            emp1.addProyecto(p1);
            emp1.addProyecto(p2);
            emp2.addProyecto(p3);
            
            emp1.addHabilidad(h1);
            emp1.addHabilidad(h2);
            emp2.addHabilidad(h2);
            emp2.addHabilidad(h3);
            emp3.addHabilidad(h3);
            
            // Persistir
            em.persist(emp1);
            em.persist(emp2);
            em.persist(emp3);
            em.persist(h1);
            em.persist(h2);
            em.persist(h3);
            
            em.getTransaction().commit();
            System.out.println("Datos creados exitosamente");
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al crear datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Muestra todos los empleados con sus proyectos y habilidades.
     * Lista cada empleado, su salario, los proyectos asignados y sus habilidades.
     */
    private static void readDatos(EntityManager em) {
        try {
            // Leer empleados con sus proyectos
            List<Scott2> empleados = em.createQuery(
                "SELECT DISTINCT e FROM Scott2 e LEFT JOIN FETCH e.proyectos", Scott2.class)
                .getResultList();
            
            System.out.println("\n=== EMPLEADOS Y SUS PROYECTOS ===");
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados registrados");
                return;
            }
            
            for (Scott2 emp : empleados) {
                System.out.println("\n" + emp.getEname() + " (" + emp.getJob() + ") ID: " + emp.getEmpno());
                System.out.println("  Salario: " + emp.getSal());
                System.out.println("  Departamento: " + emp.getDeptno());
                System.out.println("  Proyectos: " + emp.getProyectos().size());
                
                for (Proyecto p : emp.getProyectos()) {
                    System.out.println("    - " + p.getNombre() + ": " + p.getDescripcion());
                }
                
                // Cargar habilidades por separado
                List<Habilidad> habilidades = em.createQuery(
                    "SELECT h FROM Habilidad h JOIN h.empleados e WHERE e.empno = :empno", Habilidad.class)
                    .setParameter("empno", emp.getEmpno())
                    .getResultList();
                
                System.out.println("  Habilidades: " + habilidades.size());
                for (Habilidad h : habilidades) {
                    System.out.println("    - " + h);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error al leer datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Modifica el salario de un empleado específico.
     * Pide el ID del empleado y el nuevo salario por consola.
     */
    private static void updateDatos(EntityManager em, Scanner scanner) {
        System.out.print("ID del empleado a modificar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        
        em.getTransaction().begin();
        
        try {
            Scott2 emp = em.find(Scott2.class, id);
            if (emp != null) {
                System.out.println("Empleado encontrado: " + emp.getEname());
                System.out.println("Salario actual: " + emp.getSal());
                System.out.print("Nuevo salario: ");
                int nuevoSalario = scanner.nextInt();
                scanner.nextLine();
                
                int salarioAnterior = emp.getSal();
                emp.setSal(nuevoSalario);
                System.out.println("Empleado " + emp.getEname() + 
                                 " - Salario actualizado de " + salarioAnterior + 
                                 " a " + nuevoSalario);
                em.getTransaction().commit();
            } else {
                System.out.println("Empleado no encontrado con ID: " + id);
                em.getTransaction().rollback();
            }
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Elimina un proyecto buscándolo por su nombre.
     * Pide el nombre del proyecto a eliminar y lo borra si existe.
     */
    private static void deleteDatos(EntityManager em, Scanner scanner) {
        System.out.print("Nombre exacto del proyecto a eliminar: ");
        String nombreProyecto = scanner.nextLine();
        
        em.getTransaction().begin();
        
        try {
            TypedQuery<Proyecto> query = em.createQuery(
                "SELECT p FROM Proyecto p WHERE p.nombre = :nombre", Proyecto.class);
            query.setParameter("nombre", nombreProyecto);
            
            List<Proyecto> proyectos = query.getResultList();
            
            if (!proyectos.isEmpty()) {
                Proyecto proyecto = proyectos.get(0);
                String nombre = proyecto.getNombre();
                
                // Desasociar el proyecto del empleado antes de eliminar
                if (proyecto.getEmpleado() != null) {
                    proyecto.getEmpleado().getProyectos().remove(proyecto);
                }
                
                em.remove(proyecto);
                em.getTransaction().commit();
                System.out.println("Proyecto eliminado: " + nombre);
            } else {
                System.out.println("Proyecto no encontrado con nombre: " + nombreProyecto);
                em.getTransaction().rollback();
            }
            
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}