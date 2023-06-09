package Controller;

public class KhachHang {
    private String TenDonHang;
    private String LoaiHang;
    private String DiaChiGui;
    private String DiaChiNhan;
    private String ThuNhap;
    private String MaDH;

    public String getMaDH(){
        return MaDH;
    }


    public String getTenDonHang() {
        return TenDonHang;
    }

    public void setTenDonHang(String TenDonHang) {
        this.TenDonHang = TenDonHang;
    }

    public String getLoaiHang() {
        return LoaiHang;
    }

    public void setLoaiHang(String LoaiHang) {
        this.LoaiHang = LoaiHang;
    }

    public String getDiaChiGui() {
        return DiaChiGui;
    }

    public void setDiaChiGui(String DiaChiGui) {
        this.DiaChiGui = DiaChiGui;
    }

    public String getDiaChiNhan() {
        return DiaChiNhan;
    }

    public void setDiaChiNhan(String DiaChiNhan) {
        this.DiaChiNhan = DiaChiNhan;
    }


    public String getThuNhap() {
        return ThuNhap;
    }

    public void setThuNhap(String ThuNhap) {
        this.ThuNhap = ThuNhap;
    }

    public KhachHang(String TenDonHang, String LoaiHang, String DiaChiGui, String DiaChiNhan, String ThuNhap) {
        this.TenDonHang = TenDonHang;
        this.LoaiHang = LoaiHang;
        this.DiaChiGui = DiaChiGui;
        this.DiaChiNhan = DiaChiNhan;
        this.ThuNhap = ThuNhap;
    }
}
