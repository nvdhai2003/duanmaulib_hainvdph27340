package hainvdph27340.fpoly.libpn.model;

public class ThanhVien {
    private int MATV;
    private String HOTEN;
    private String NAMSINH;
    private String GIOITINH;

    public ThanhVien(int MATV, String HOTEN, String NAMSINH, String GIOITINH) {
        this.MATV = MATV;
        this.HOTEN = HOTEN;
        this.NAMSINH = NAMSINH;
        this.GIOITINH = GIOITINH;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public int getMATV() {
        return MATV;
    }

    public void setMATV(int MATV) {
        this.MATV = MATV;
    }

    public String getHOTEN() {
        return HOTEN;
    }

    public void setHOTEN(String HOTEN) {
        this.HOTEN = HOTEN;
    }

    public String getNAMSINH() {
        return NAMSINH;
    }

    public void setNAMSINH(String NAMSINH) {
        this.NAMSINH = NAMSINH;
    }
}
