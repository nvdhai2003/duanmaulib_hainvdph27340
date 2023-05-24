package hainvdph27340.fpoly.libpn.model;

public class PhieuMuon {
    private int MAPM;
    private String MATT;
    private int MATV;
    private int MASACH;
    private String NGAY;
    private int TRASACH;
    private int TIENTHUE;
    private String TENTT;
    private String TENTV;
    private String TENSACH;

    public PhieuMuon(int MAPM, String MATT, String TENTT, int MATV, String TENTV, int MASACH, String TENSACH, String NGAY, int TIENTHUE, int TRASACH) {
        this.MAPM = MAPM;
        this.MATT = MATT;
        this.MATV = MATV;
        this.MASACH = MASACH;
        this.NGAY = NGAY;
        this.TRASACH = TRASACH;
        this.TIENTHUE = TIENTHUE;
        this.TENTT = TENTT;
        this.TENTV = TENTV;
        this.TENSACH = TENSACH;
    }

    public PhieuMuon(String MATT, int MATV, int MASACH, String NGAY, int TRASACH, int TIENTHUE) {
        this.MATT = MATT;
        this.MATV = MATV;
        this.MASACH = MASACH;
        this.NGAY = NGAY;
        this.TRASACH = TRASACH;
        this.TIENTHUE = TIENTHUE;
    }

    public int getMAPM() {
        return MAPM;
    }

    public void setMAPM(int MAPM) {
        this.MAPM = MAPM;
    }

    public String getMATT() {
        return MATT;
    }

    public void setMATT(String MATT) {
        this.MATT = MATT;
    }

    public int getMATV() {
        return MATV;
    }

    public void setMATV(int MATV) {
        this.MATV = MATV;
    }

    public int getMASACH() {
        return MASACH;
    }

    public void setMASACH(int MASACH) {
        this.MASACH = MASACH;
    }

    public String getNGAY() {
        return NGAY;
    }

    public void setNGAY(String NGAY) {
        this.NGAY = NGAY;
    }

    public int getTRASACH() {
        return TRASACH;
    }

    public void setTRASACH(int TRASACH) {
        this.TRASACH = TRASACH;
    }

    public int getTIENTHUE() {
        return TIENTHUE;
    }

    public void setTIENTHUE(int TIENTHUE) {
        this.TIENTHUE = TIENTHUE;
    }

    public String getTENTT() {
        return TENTT;
    }

    public void setTENTT(String TENTT) {
        this.TENTT = TENTT;
    }

    public String getTENTV() {
        return TENTV;
    }

    public void setTENTV(String TENTV) {
        this.TENTV = TENTV;
    }

    public String getTENSACH() {
        return TENSACH;
    }

    public void setTENSACH(String TENSACH) {
        this.TENSACH = TENSACH;
    }
}
