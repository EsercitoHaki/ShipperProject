package Controller;

import ConnectDataBase.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DonHangController implements Initializable {

    @FXML
    private TableView<DonHang> BangDonHang;

    @FXML
    private TableColumn<DonHang, String> cDiaChiGui;

    @FXML
    private TableColumn<DonHang, String> cDiaChiNhan;

    @FXML
    private TableColumn<DonHang, String> cLoaiHang;

    @FXML
    private TableColumn<DonHang, String> cMaDonHang;

    @FXML
    private TableColumn<DonHang, String> cTenDonHang;

    @FXML
    private TableColumn<DonHang, String> cThuNhap;

    @FXML
    private TableColumn<DonHang, String> cTrangThai;

    @FXML
    private TextField textfieldGiaDen;

    @FXML
    private TextField textfieldGiaTu;

    @FXML
    private TextField textfieldDiaChiGui;

    @FXML
    private TextField textfieldDiaChiNhan;

    @FXML
    private Label labelDiaChiGui;

    @FXML
    private Label labelDiaChiNhan;

    @FXML
    private Label labelLoaiHang;

    @FXML
    private Label labelMaDonHang;

    @FXML
    private Label labelTenDonHang;

    @FXML
    private Label labelThuNhap;

    @FXML
    private Label labelTrangThaiDon;

    public boolean isCheckTrangThai(){
        DonHang selectedDonHang = BangDonHang.getSelectionModel().getSelectedItem();
        if (selectedDonHang != null) {
            String trangThai = selectedDonHang.getTrangThaiDon();
            if (trangThai.equalsIgnoreCase("Đang Chờ")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public ObservableList<DonHang> DuLieuDonHang(){
        ObservableList<DonHang> CacDonHang = FXCollections.observableArrayList();
        String sql = "SELECT MaDH, TenDonHang, LoaiHang, DiaChiGui, DiaChiNhan, TrangThaiDon, ThuNhap FROM DonHang ";
        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            DonHang donhang;
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()){
                donhang = new DonHang(result.getString("MaDH"),
                        result.getString("TenDonHang"),
                        result.getString("LoaiHang"),
                        result.getString("DiaChiGui"),
                        result.getString("DiaChiNhan"),
                        result.getString("TrangThaiDon"),
                        result.getString("ThuNhap")
                );
                CacDonHang.add(donhang);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CacDonHang;
    }

    private ObservableList<DonHang> DanhSachDonHang;
    public void HienThiDonHang(){
        DanhSachDonHang = DuLieuDonHang();
        cMaDonHang.setCellValueFactory(new PropertyValueFactory<>("MaDH"));
        cTenDonHang.setCellValueFactory(new PropertyValueFactory<>("TenDonHang"));
        cLoaiHang.setCellValueFactory(new PropertyValueFactory<>("LoaiHang"));
        cDiaChiGui.setCellValueFactory(new PropertyValueFactory<>("DiaChiGui"));
        cDiaChiNhan.setCellValueFactory(new PropertyValueFactory<>("DiaChiNhan"));
        cTrangThai.setCellValueFactory(new PropertyValueFactory<>("TrangThaiDon"));
        cThuNhap.setCellValueFactory(new PropertyValueFactory<>("ThuNhap"));
        BangDonHang.setItems(DanhSachDonHang);
    }

    public ObservableList<DonHang> DuLieuDonHangTheoKhoangGiaVaDiaChi(double giaTu, double giaDen, String diaChiGui, String diaChiNhan) {
        ObservableList<DonHang> CacDonHang = FXCollections.observableArrayList();
        String sql = "SELECT MaDH, TenDonHang, LoaiHang, DiaChiGui, DiaChiNhan, TrangThaiDon, ThuNhap FROM DonHang WHERE ThuNhap BETWEEN ? AND ?";


        if (!diaChiGui.isEmpty()) {
            sql += " AND DiaChiGui LIKE '%" + diaChiGui + "%'";
        }
        if (!diaChiNhan.isEmpty()) {
            sql += " AND DiaChiNhan LIKE '%" + diaChiNhan + "%'";
        }

        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            DonHang donhang;
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            prepare.setDouble(1, giaTu);
            prepare.setDouble(2, giaDen);
            ResultSet result = prepare.executeQuery();
            while (result.next()){
                donhang = new DonHang(result.getString("MaDH"),
                        result.getString("TenDonHang"),
                        result.getString("LoaiHang"),
                        result.getString("DiaChiGui"),
                        result.getString("DiaChiNhan"),
                        result.getString("TrangThaiDon"),
                        result.getString("ThuNhap")
                );
                CacDonHang.add(donhang);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return CacDonHang;
    }

    public void TimKiemTheoKhoangGiaVaDiaChi() {
        double giaTu;
        try {
            giaTu = Double.parseDouble(textfieldGiaTu.getText());
            if (giaTu < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Lỗi nhập liệu");
//            alert.setHeaderText("Giá trị không hợp lệ");
//            alert.setContentText("Vui lòng nhập giá trị số dương");
//            alert.showAndWait();

            Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
            alert.setTitle("Lỗi nhập dữ liệu");
            alert.setHeaderText(null);
            alert.setContentText("Giá trị nhập không hợp lệ!");
            alert.showAndWait();
            return;
        }

        double giaDen;
        try {
            giaDen = Double.parseDouble(textfieldGiaDen.getText());
            if (giaDen < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Lỗi nhập liệu");
//            alert.setHeaderText("Giá trị không hợp lệ");
//            alert.setContentText("Vui lòng nhập giá trị số dương");
//            alert.showAndWait();

            Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
            alert.setTitle("Lỗi nhập dữ liệu");
            alert.setHeaderText(null);
            alert.setContentText("Giá trị nhập không hợp lệ!");
            alert.showAndWait();
            return;
        }

        if (giaTu > giaDen) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Lỗi nhập liệu");
//            alert.setHeaderText("Giá trị không hợp lệ");
//            alert.setContentText("Giá trị từ phải nhỏ hơn hoặc bằng đến");
//            alert.showAndWait();

            Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
            alert.setTitle("Lỗi nhập dữ liệu");
            alert.setHeaderText(null);
            alert.setContentText("Giá trị nhập không hợp lệ!");
            alert.showAndWait();
            return;
        }

        String diaChiGui = textfieldDiaChiGui.getText();
        String diaChiNhan = textfieldDiaChiNhan.getText();

        DanhSachDonHang = DuLieuDonHangTheoKhoangGiaVaDiaChi(giaTu, giaDen, diaChiGui, diaChiNhan);
        cMaDonHang.setCellValueFactory(new PropertyValueFactory<>("MaDH"));
        cTenDonHang.setCellValueFactory(new PropertyValueFactory<>("TenDonHang"));
        cLoaiHang.setCellValueFactory(new PropertyValueFactory<>("LoaiHang"));
        cDiaChiGui.setCellValueFactory(new PropertyValueFactory<>("DiaChiGui"));
        cDiaChiNhan.setCellValueFactory(new PropertyValueFactory<>("DiaChiNhan"));
        cTrangThai.setCellValueFactory(new PropertyValueFactory<>("TrangThaiDon"));
        cThuNhap.setCellValueFactory(new PropertyValueFactory<>("ThuNhap"));
        BangDonHang.setItems(DanhSachDonHang);
    }

    public void hienThiThongTinDonHang() {
        DonHang selectedDonHang = BangDonHang.getSelectionModel().getSelectedItem();
        if (selectedDonHang != null) {
            String maDonHang = selectedDonHang.getMaDH();
            String tenDonHang = selectedDonHang.getTenDonHang();
            String loaiHang = selectedDonHang.getLoaiHang();
            String diaChiGui = selectedDonHang.getDiaChiGui();
            String diaChiNhan = selectedDonHang.getDiaChiNhan();
            String trangThaiDon = selectedDonHang.getTrangThaiDon();
            String thuNhap = selectedDonHang.getThuNhap();

            labelMaDonHang.setText(maDonHang);
            labelTenDonHang.setText(tenDonHang);
            labelLoaiHang.setText(loaiHang);
            labelDiaChiGui.setText(diaChiGui);
            labelDiaChiNhan.setText(diaChiNhan);
            labelTrangThaiDon.setText(trangThaiDon);
            labelThuNhap.setText(thuNhap);
        }
    }

    private void capNhatTrangThaiDonHang(DonHang donHang) {
        try {
            Connection connection = DBConnection.ConnectDB();
            String sql = "UPDATE DonHang SET TrangThaiDon = ? WHERE MaDH = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, donHang.getTrangThaiDon());
            statement.setString(2, donHang.getMaDH());
            statement.executeUpdate();
            statement.close();
            connection.close();

            Alert alert = new Alert(Alert.AlertType.ERROR.INFORMATION);
            alert.setTitle("Thông Báo Cho Bạn");
            alert.setHeaderText(null);
            alert.setContentText("Đã Nhận Đơn Thành Công");
            alert.showAndWait();
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật trạng thái đơn hàng: " + e.getMessage());
        }
    }

    private void xuLyNhanDonHang() {
        DonHang selectedDonHang = BangDonHang.getSelectionModel().getSelectedItem();
        if(isCheckTrangThai()){
            if (selectedDonHang != null) {
                selectedDonHang.setTrangThaiDon("Đang Giao");
                BangDonHang.refresh();
                capNhatTrangThaiDonHang(selectedDonHang);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
            alert.setTitle("Thông Báo Cho Bạn");
            alert.setHeaderText(null);
            alert.setContentText("Nhận đơn hàng không thành công");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HienThiDonHang();
    }

    public void NhanDonHang(ActionEvent event) {
        xuLyNhanDonHang();
    }
}
