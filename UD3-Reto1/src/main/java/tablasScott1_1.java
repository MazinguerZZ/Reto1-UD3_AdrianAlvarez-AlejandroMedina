import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase para crear datos iniciales en la tabla original de empleados Scott.
 * Inserta cuatro empleados de ejemplo con diferentes nombres y salarios.
 * Se usa para poblar la base de datos con datos básicos de prueba.
 */
public class tablasScott1_1 {
    
    /**
     * Crea y guarda cuatro empleados con datos predefinidos.
     * Cada empleado tiene un ID único, nombre, puesto, salario y departamento.
     */
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadScott");
		EntityManager em = emf.createEntityManager();

		Scott empleado1 = new Scott(7000, "Alejandro", "ANALYST", 7600, 1000, 500, 20);
		Scott empleado2 = new Scott(7001, "Pedro", "ANALYST", 7601, 1100, 600, 20);
		Scott empleado3 = new Scott(7002, "Adrian", "ANALYST", 7602, 1200, 700, 20);
		Scott empleado4 = new Scott(7003, "Jose", "ANALYST", 7603, 1300, 800, 20);

		

		try {
			em.getTransaction().begin();
			em.persist(empleado1);
			em.persist(empleado2);
			em.persist(empleado3);
			em.persist(empleado4);
			em.getTransaction().commit();
			System.out.println("Todo ha ido bien");
		} catch (Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("Ha sido un desastre");

		} finally {
			em.close();
			emf.close();
		}
	}
}