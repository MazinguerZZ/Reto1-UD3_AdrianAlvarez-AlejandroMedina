package JPA_Ejer2_3_4;

import jakarta.persistence.*;
import java.util.*;

/**
 * Representa una habilidad que pueden tener los empleados.
 * Cada habilidad tiene un nombre (como "Java" o "SQL") y un nivel (como "Avanzado").
 * Los empleados pueden tener múltiples habilidades, y cada habilidad puede pertenecer a varios empleados.
 */
@Entity
@Table(name = "HABILIDAD")
public class Habilidad {
    
    /**
     * ID único de la habilidad, generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HABILIDAD_ID")
    private Long id;
    
    /**
     * Nombre de la habilidad, como "Java", "SQL" o "HTML".
     */
    @Column(name = "NOMBRE", length = 30)
    private String nombre;
    
    /**
     * Nivel de dominio de la habilidad: "Básico", "Intermedio" o "Avanzado".
     */
    @Column(name = "NIVEL", length = 20)
    private String nivel;
    
    /**
     * Lista de empleados que tienen esta habilidad.
     */
    @ManyToMany(mappedBy = "habilidades")
    private List<Scott2> empleados = new ArrayList<>();
    
    /**
     * Constructor vacío necesario para JPA.
     */
    public Habilidad() {}
    
    /**
     * Constructor para crear una nueva habilidad.
     * @param nombre Nombre de la habilidad
     * @param nivel Nivel de dominio
     */
    public Habilidad(String nombre, String nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    
    public List<Scott2> getEmpleados() { return empleados; }
    public void setEmpleados(List<Scott2> empleados) { this.empleados = empleados; }
    
    /**
     * Devuelve la habilidad en formato "Nombre (Nivel)".
     */
    @Override
    public String toString() {
        return nombre + " (" + nivel + ")";
    }
}