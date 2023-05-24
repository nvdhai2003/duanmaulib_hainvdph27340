package hainvdph27340.fpoly.libpn.model;

public class LoaiSach {
    private int MALOAI;
    private String TENLOAI;

    public LoaiSach(int MALOAI, String TENLOAI) {
        this.MALOAI = MALOAI;
        this.TENLOAI = TENLOAI;
    }

    public int getMALOAI() {
        return MALOAI;
    }

    public void setMALOAI(int MALOAI) {
        this.MALOAI = MALOAI;
    }

    public String getTENLOAI() {
        return TENLOAI;
    }

    public void setTENLOAI(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }
}
