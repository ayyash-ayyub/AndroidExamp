package seamolec.org.app.udjseamolec;


public class JSONResponse {

    public String getNama_quiz() {
        return nama_quiz;
    }

    public void setNama_quiz(String nama_quiz) {
        this.nama_quiz = nama_quiz;
    }

    String nama_quiz;

    String id;

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    String tanggal_mulai;

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    String durasi;

    public String getNapel() {
        return napel;
    }

    public void setNapel(String napel) {
        this.napel = napel;
    }

    String napel;

    private JSONResponse[] ngHasil;

    public JSONResponse[] ngHasil() {
        return ngHasil;
    }


    public String getId_quiz(){
        return id;
    }

    public void setId_quiz(String id){
        this.id = id;
    }
}
