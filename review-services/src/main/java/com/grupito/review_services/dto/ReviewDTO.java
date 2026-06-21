package com.grupito.review_services.dto;

import java.time.LocalDateTime;

import com.grupito.review_services.model.Review;

public class ReviewDTO {

	private Long id;
	private String nombre;
	private String mensaje;
	private LocalDateTime fecha;

	public ReviewDTO() {
	}

	public ReviewDTO(String nombre, String mensaje) {
		this.nombre = nombre;
		this.mensaje = mensaje;
	}

	public ReviewDTO(Long id, String nombre, String mensaje, LocalDateTime fecha) {
		this.id = id;
		this.nombre = nombre;
		this.mensaje = mensaje;
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Review toModel() {
		Review r = new Review();
		r.setId(this.id);
		r.setNombre(this.nombre);
		r.setMensaje(this.mensaje);
		r.setFecha(this.fecha);
		return r;
	}

	public static ReviewDTO fromModel(Review r) {
		if (r == null) return null;
		return new ReviewDTO(r.getId(), r.getNombre(), r.getMensaje(), r.getFecha());
	}
}
