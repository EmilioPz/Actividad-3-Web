package recetario.operator_service.entities;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "recetas")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String instrucciones;

    @Column(nullable = false, length = 50)
    private String tiempo;

    @Column(length = 255)
    private String imagen;

    @Setter
    @CollectionTable(
            name = "receta_ingredientes",
            joinColumns = @JoinColumn(name = "receta_id")
    )
    @Column(name = "ingrediente", nullable = false)
    private List<String> ingredientes;

    public Recipe() {}

    public Recipe(String nombre, String instrucciones, String tiempo, String imagen, List<String> ingredientes) {
        this.nombre = nombre != null ? nombre.trim() : null;
        this.instrucciones = instrucciones != null ? instrucciones.trim() : null;
        this.tiempo = tiempo != null ? tiempo.trim() : null;
        this.imagen = imagen != null ? imagen.trim() : null;
        this.ingredientes = ingredientes;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre != null ? nombre.trim() : null;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones != null ? instrucciones.trim() : null;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo != null ? tiempo.trim() : null;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen != null ? imagen.trim() : null;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

}
