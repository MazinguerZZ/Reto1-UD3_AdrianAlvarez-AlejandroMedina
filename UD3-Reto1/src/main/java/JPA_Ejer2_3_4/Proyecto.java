package JPA_Ejer2_3_4;
import jakarta.persistence.*;

/**
 * Representa un proyecto asignado a un empleado.
 * Cada proyecto tiene un nombre, una descripción y está asociado a un empleado específico.
 * Un empleado puede tener varios proyectos, pero cada proyecto pertenece a un solo empleado.
 */
@Entity
@Table(name = "PROYECTO")
public class Proyecto {
    
    /**
     * ID único del proyecto, generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROYECTO_ID")
    private Long id;
    
    /**
     * Nombre del proyecto (máximo 50 caracteres).
     */
    @Column(name = "NOMBRE", length = 50)
    private String nombre;
    
    /**
     * Descripción detallada del proyecto (máximo 200 caracteres).
     */
    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
    
    /**
     * Empleado asignado a este proyecto.
     * Varios proyectos pueden pertenecer al mismo empleado.
     */
    @ManyToOne
    @JoinColumn(name = "EMPNO")
    private Scott2 empleado;
    
    /**
     * Constructor vacío necesario para JPA.
     */
    public Proyecto() {}
    
    /**
     * Constructor para crear un nuevo proyecto.
     * @param nombre Nombre del proyecto
     * @param descripcion Descripción del proyecto
     */
    public Proyecto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Scott2 getEmpleado() { return empleado; }
    public void setEmpleado(Scott2 empleado) { this.empleado = empleado; }
    
    /**
     * Muestra solo el nombre del proyecto.
     */
    @Override
    public String toString() {
        return nombre;
    }
}