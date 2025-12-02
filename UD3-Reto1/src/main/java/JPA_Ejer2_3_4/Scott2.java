package JPA_Ejer2_3_4;

import jakarta.persistence.*;
import java.util.*;

/**
 * Versión mejorada del empleado Scott, con relaciones a proyectos y habilidades.
 * Además de los datos básicos, cada empleado puede tener varios proyectos asignados
 * y múltiples habilidades. Se usa en el sistema CRUD completo.
 */
@Entity
@Table(name = "EMP2")
public class Scott2 {
    
    /**
     * Número único del empleado. Clave primaria.
     */
    @Id
    @Column(name = "EMPNO")
    private Long empno;
    
    /**
     * Nombre del empleado (máximo 10 caracteres).
     */
    @Column(name = "ENAME", length = 10)
    private String ename;
    
    /**
     * Puesto del empleado (máximo 20 caracteres).
     */
    @Column(name = "JOB", length = 20)
    private String job;
    
    /**
     * Número del manager. Puede ser null si no tiene manager.
     */
    @Column(name = "MGR")
    private Integer mgr;
    
    /**
     * Salario del empleado.
     */
    @Column(name = "SAL")
    private Integer sal;
    
    /**
     * Comisión del empleado. Puede ser null si no tiene comisión.
     */
    @Column(name = "COMM")
    private Integer comm;
    
    /**
     * Número del departamento.
     */
    @Column(name = "DEPTNO")
    private Integer deptno;
    
    /**
     * Lista de proyectos asignados a este empleado.
     * Un empleado puede tener varios proyectos.
     */
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proyecto> proyectos = new ArrayList<>();
    
    /**
     * Lista de habilidades que posee el empleado.
     * Un empleado puede tener varias habilidades, y cada habilidad puede ser de varios empleados.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "EMP2_HABILIDAD2",
        joinColumns = @JoinColumn(name = "EMPNO"),
        inverseJoinColumns = @JoinColumn(name = "HABILIDAD_ID")
    )
    private List<Habilidad> habilidades = new ArrayList<>();
    
    /**
     * Constructor vacío necesario para JPA.
     */
    public Scott2() {}
    
    /**
     * Constructor completo para crear un empleado con todos sus datos.
     */
    public Scott2(Long empno, String ename, String job, Integer mgr, Integer sal, Integer comm, Integer deptno) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }
    
    // Getters y Setters
    public Long getEmpno() { return empno; }
    public void setEmpno(Long empno) { this.empno = empno; }
    
    public String getEname() { return ename; }
    public void setEname(String ename) { this.ename = ename; }
    
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }
    
    public Integer getMgr() { return mgr; }
    public void setMgr(Integer mgr) { this.mgr = mgr; }
    
    public Integer getSal() { return sal; }
    public void setSal(Integer sal) { this.sal = sal; }
    
    public Integer getComm() { return comm; }
    public void setComm(Integer comm) { this.comm = comm; }
    
    public Integer getDeptno() { return deptno; }
    public void setDeptno(Integer deptno) { this.deptno = deptno; }
    
    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyectos(List<Proyecto> proyectos) { this.proyectos = proyectos; }
    
    public List<Habilidad> getHabilidades() { return habilidades; }
    public void setHabilidades(List<Habilidad> habilidades) { this.habilidades = habilidades; }
    
    /**
     * Asigna un proyecto a este empleado y actualiza la relación.
     */
    public void addProyecto(Proyecto proyecto) {
        proyectos.add(proyecto);
        proyecto.setEmpleado(this);
    }
    
    /**
     * Quita un proyecto del empleado y actualiza la relación.
     */
    public void removeProyecto(Proyecto proyecto) {
        proyectos.remove(proyecto);
        proyecto.setEmpleado(null);
    }
    
    /**
     * Agrega una habilidad al empleado y actualiza la relación.
     */
    public void addHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
        habilidad.getEmpleados().add(this);
    }
    
    /**
     * Muestra información básica del empleado: ID, nombre y puesto.
     */
    @Override
    public String toString() {
        return "Scott2 [empno=" + empno + ", ename=" + ename + ", job=" + job + "]";
    }
}