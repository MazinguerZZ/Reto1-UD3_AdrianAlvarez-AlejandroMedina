import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Representa un empleado básico de la base de datos original Scott.
 * Contiene la información principal de un empleado sin relaciones complejas.
 * Esta clase se mapea directamente a la tabla EMP de la base de datos.
 */
@Entity
public class Scott {

    /**
     * Número único que identifica al empleado. Es la clave primaria.
     */
    @Id
    private Long empno;
    
    /**
     * Nombre del empleado.
     */
    private String ename;
    
    /**
     * Puesto o trabajo del empleado.
     */
    private String job;
    
    /**
     * Número del manager del empleado.
     */
    private int mgr;
    
    /**
     * Salario del empleado.
     */
    private int sal;
    
    /**
     * Comisión del empleado (puede ser cero).
     */
    private int comm;
    
    /**
     * Número del departamento donde trabaja el empleado.
     */
    private int deptno;
    
    /**
     * Constructor vacío necesario para JPA.
     */
    public Scott() {
        super();
    }
    
    /**
     * Constructor para crear un nuevo empleado con todos sus datos.
     */
    public Scott(long empno, String ename, String job, int mgr, int sal, int comm, int deptno) {
        super();
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
        this.sal = sal;
        this.comm = comm;
        this.deptno = deptno;
    }


    public Long getEmpno() {
        return empno;
    }


    public void setEmpno(Long empno) {
        this.empno = empno;
    }


    public String getEname() {
        return ename;
    }


    public void setEname(String ename) {
        this.ename = ename;
    }


    public String getJob() {
        return job;
    }


    public void setJob(String job) {
        this.job = job;
    }


    public int getMgr() {
        return mgr;
    }


    public void setMgr(int mgr) {
        this.mgr = mgr;
    }


    public int getSal() {
        return sal;
    }


    public void setSal(int sal) {
        this.sal = sal;
    }


    public int getComm() {
        return comm;
    }


    public void setComm(int comm) {
        this.comm = comm;
    }


    public int getDeptno() {
        return deptno;
    }


    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }


    /**
     * Muestra todos los datos del empleado en un formato legible.
     */
    @Override
    public String toString() {
        return "scott [empno=" + empno + ", ename=" + ename + ", job=" + job + 
               ", mgr=" + mgr + ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno + "]";
    }


    @Override
    public int hashCode() {
        return Objects.hash(comm, deptno, empno, ename, job, mgr, sal);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Scott other = (Scott) obj;
        return comm == other.comm && deptno == other.deptno && empno == other.empno
                && Objects.equals(ename, other.ename) && Objects.equals(job, other.job) && mgr == other.mgr
                && sal == other.sal;
    }
}