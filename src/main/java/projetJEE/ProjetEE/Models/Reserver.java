package projetJEE.ProjetEE.Models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Reserver")
public class Reserver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReservation")
    private Long idReservation;    
    
//    @ManyToOne
//    @JoinColumn(name = "idUtilisateur",  referencedColumnName = "idUtilisateur")
//    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "idVoyage",  referencedColumnName = "voyageId")
    private Voyage voyage;

    @Column(name = "prixTotal")
    private double prixTotal;

    @Column(name = "nbPersonnes")
    private int nbPersonnes;

    @Column(name = "dateD")
    private Date dateDebut;

    @Column(name = "dateF")
    private Date dateFin;

//    @Column(name = "statutReservation")
//    private String statutReservation;

    // Getters and setters

//    public Utilisateur getUtilisateur() {
//        return utilisateur;
//    }
//
//    public void setUtilisateur(Utilisateur utilisateur) {
//        this.utilisateur = utilisateur;
//    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public void setNbPersonnes(int nbPersonnes) {
        this.nbPersonnes = nbPersonnes;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

//    public String getStatutReservation() {
//        return statutReservation;
//    }
//
//    public void setStatutReservation(String statutReservation) {
//        this.statutReservation = statutReservation;
//    }
}
