package hainvdph27340.fpoly.libpn.model;

public class Sach {
    private int MASACH;
    private String TENSACH;
    private int GIATHUE;
    private int MALOAI;
    private String TENLOAI;
    private int SOLUONGMUON;

    public Sach(int MASACH, String TENSACH, int GIATHUE, int MALOAI, String TENLOAI) {
        this.MASACH = MASACH;
        this.TENSACH = TENSACH;
        this.GIATHUE = GIATHUE;
        this.MALOAI = MALOAI;
        this.TENLOAI = TENLOAI;
    }

    public Sach(int MASACH, String TENSACH, int SOLUONGMUON) {
        this.MASACH = MASACH;
        this.TENSACH = TENSACH;
        this.SOLUONGMUON = SOLUONGMUON;
    }

    public int getSOLUONGMUON() {
        return SOLUONGMUON;
    }

    public void setSOLUONGMUON(int SOLUONGMUON) {
        this.SOLUONGMUON = SOLUONGMUON;
    }

    public String getTENLOAI() {
        return TENLOAI;
    }

    public void setTENLOAI(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }

    public int getMASACH() {
        return MASACH;
    }

    public void setMASACH(int MASACH) {
        this.MASACH = MASACH;
    }

    public String getTENSACH() {
        return TENSACH;
    }

    public void setTENSACH(String TENSACH) {
        this.TENSACH = TENSACH;
    }

    public int getGIATHUE() {
        return GIATHUE;
    }

    public void setGIATHUE(int GIATHUE) {
        this.GIATHUE = GIATHUE;
    }

    public int getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(int MALOAI) {
        this.MALOAI = MALOAI;
    }
}
