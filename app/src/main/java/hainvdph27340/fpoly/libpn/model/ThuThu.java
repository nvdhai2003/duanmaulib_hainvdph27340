package hainvdph27340.fpoly.libpn.model;

public class ThuThu {
    private String MATT;
    private String HOTEN;
    private String MATKHAU;

    public ThuThu(String MATT, String HOTEN, String MATKHAU) {
        this.MATT = MATT;
        this.HOTEN = HOTEN;
        this.MATKHAU = MATKHAU;
    }

    public String getMATT() {
        return MATT;
    }

    public void setMATT(String MATT) {
        this.MATT = MATT;
    }

    public String getHOTEN() {
        return HOTEN;
    }

    public void setHOTEN(String HOTEN) {
        this.HOTEN = HOTEN;
    }

    public String getMATKHAU() {
        return MATKHAU;
    }

    public void setMATKHAU(String MATKHAU) {
        this.MATKHAU = MATKHAU;
    }
}
