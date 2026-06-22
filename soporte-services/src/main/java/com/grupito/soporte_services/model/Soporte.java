package com.grupito.soporte_services.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "soporte")
public class Soporte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "usuario_id", nullable = false)
	private Long usuarioId;

	private String asunto;

	private String descripcion;

	private String estado;

	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;

	@Column(name = "respuesta_admin", length = 2000)
	private String respuestaAdmin;

	public Soporte() {}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public Long getUsuarioId() { return usuarioId; }
	public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

	public String getAsunto() { return asunto; }
	public void setAsunto(String asunto) { this.asunto = asunto; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public String getEstado() { return estado; }
	public void setEstado(String estado) { this.estado = estado; }

	public LocalDateTime getFechaCreacion() { return fechaCreacion; }
	public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

	public String getRespuestaAdmin() { return respuestaAdmin; }
	public void setRespuestaAdmin(String respuestaAdmin) { this.respuestaAdmin = respuestaAdmin; }
}

