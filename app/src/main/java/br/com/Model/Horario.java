package br.com.Model;

/**
 * Created by gabrielnovakovski on 15/07/2017.
 */

public class Horario {
    private String horaInicioManha;
    private String horaInicioTarde;
    private String minutoInicioManha;
    private String minutoInicioTarde;
    private String horaFimManha;
    private String horaFimTarde;
    private String minutoFimManha;
    private String minutoFimTarde;
    private Integer id;

    public Horario() {
    }

    public Horario(String horaInicioManha, String horaInicioTarde, String minutoInicioManha, String minutoInicioTarde, String horaFimManha, String horaFimTarde, String minutoFimManha, String minutoFimTarde) {
        this.horaInicioManha = horaInicioManha;
        this.horaInicioTarde = horaInicioTarde;
        this.minutoInicioManha = minutoInicioManha;
        this.minutoInicioTarde = minutoInicioTarde;
        this.horaFimManha = horaFimManha;
        this.horaFimTarde = horaFimTarde;
        this.minutoFimManha = minutoFimManha;
        this.minutoFimTarde = minutoFimTarde;
    }

    public String getHoraInicioManha() {
        return horaInicioManha;
    }

    public void setHoraInicioManha(String horaInicioManha) {
        this.horaInicioManha = horaInicioManha;
    }

    public String getHoraInicioTarde() {
        return horaInicioTarde;
    }

    public void setHoraInicioTarde(String horaInicioTarde) {
        this.horaInicioTarde = horaInicioTarde;
    }

    public String getMinutoInicioManha() {
        return minutoInicioManha;
    }

    public void setMinutoInicioManha(String minutoInicioManha) {
        this.minutoInicioManha = minutoInicioManha;
    }

    public String getMinutoInicioTarde() {
        return minutoInicioTarde;
    }

    public void setMinutoInicioTarde(String minutoInicioTarde) {
        this.minutoInicioTarde = minutoInicioTarde;
    }

    public String getHoraFimManha() {
        return horaFimManha;
    }

    public void setHoraFimManha(String horaFimManha) {
        this.horaFimManha = horaFimManha;
    }

    public String getHoraFimTarde() {
        return horaFimTarde;
    }

    public void setHoraFimTarde(String horaFimTarde) {
        this.horaFimTarde = horaFimTarde;
    }

    public String getMinutoFimManha() {
        return minutoFimManha;
    }

    public void setMinutoFimManha(String minutoFimManha) {
        this.minutoFimManha = minutoFimManha;
    }

    public String getMinutoFimTarde() {
        return minutoFimTarde;
    }

    public void setMinutoFimTarde(String minutoFimTarde) {
        this.minutoFimTarde = minutoFimTarde;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}