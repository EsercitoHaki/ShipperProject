package Controller;

public class ThuNhap {
    private String MaDH;
    private String TenDonHang;
    private String LoaiHang;
    private String DiaChiGui;
    private String DiaChiNhan;
    private String TrangThaiDon;
    private String ThuNhap;

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String MaDH) {
        this.MaDH = MaDH;
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

    public String getTrangThaiDon() {
        return TrangThaiDon;
    }

    public void setTrangThaiDon(String TrangThaiDon) {
        this.TrangThaiDon = TrangThaiDon;
    }

    public String getThuNhap() {
        return ThuNhap;
    }

    public void setThuNhap(String ThuNhap) {
        this.ThuNhap = ThuNhap;
    }

    public ThuNhap(String MaDH, String TenDonHang, String LoaiHang, String DiaChiGui, String DiaChiNhan, String TrangThaiDon, String ThuNhap) {
        this.MaDH = MaDH;
        this.TenDonHang = TenDonHang;
        this.LoaiHang = LoaiHang;
        this.DiaChiGui = DiaChiGui;
        this.DiaChiNhan = DiaChiNhan;
        this.TrangThaiDon = TrangThaiDon;
        this.ThuNhap = ThuNhap;
    }

}
