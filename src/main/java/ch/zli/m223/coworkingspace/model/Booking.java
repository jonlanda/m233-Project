package ch.zli.m223.coworkingspace.model;

import javax.persistence.*;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@Entity
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime date;

  @Column(nullable = false)
  private Boolean halfDay;

  @Column(nullable = true)
  private Boolean morning;

  @Column(nullable = true)
  private Boolean afternoon;

  @Column(nullable = false)
  private Boolean status;

  @ManyToOne
  @JsonIgnoreProperties("booking")
  private ApplicationUser applicationuser;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public Boolean getHalfDay() {
    return halfDay;
  }

  public void setHalfDay(Boolean halfDay) {
    this.halfDay = halfDay;
  }

  public Boolean getMorning() {
    return morning;
  }

  public void setMorning(Boolean morning) {
    this.morning = morning;
  }

  public Boolean getAfternoon() {
    return afternoon;
  }

  public void setAfternoon(Boolean afternoon) {
    this.afternoon = afternoon;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public ApplicationUser getUser() {
    return applicationuser;
  }

  public void setUser(ApplicationUser appliacationUser) {
    this.applicationuser = appliacationUser;
  }
}
