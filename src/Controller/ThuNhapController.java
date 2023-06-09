package Controller;

import ConnectDataBase.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ThuNhapController implements Initializable {
    @FXML
    private Label labelDiaChiGuiTN;

    @FXML
    private Label labelDiaChiNhanTN;

    @FXML
    private Label labelLoaiHangTN;

    @FXML
    private Label labelMaDHTN;

    @FXML
    private Label labelTenDonHangTN;

    @FXML
    private Label labelThuNhapTN;

    @FXML
    private TableView<ThuNhap> BangThuNhap;

    @FXML
    private TableColumn<ThuNhap, String> cTNDiaChiGui;

    @FXML
    private TableColumn<ThuNhap, String> cTNDiaChiNhan;

    @FXML
    private TableColumn<ThuNhap, String> cTNLoaiHang;

    @FXML
    private TableColumn<ThuNhap, String> cTNMaDonHang;

    @FXML
    private TableColumn<ThuNhap, String> cTNTenDonHang;

    @FXML
    private TableColumn<ThuNhap, String> cTNThuNhap;

    @FXML
    private TableColumn<ThuNhap, String> cTNTrangThai;
    public ObservableList<ThuNhap> DuLieuThuNhap(){
        ObservableList<ThuNhap> CacThuNhap = FXCollections.observableArrayList();
        String sql = "SELECT MaDH, TenDonHang, LoaiHang, DiaChiGui, DiaChiNhan, TrangThaiDon, ThuNhap FROM DonHang WHERE TrangThaiDon <> 'Đang Chờ' AND MaShipper = 'SP001'";
        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            ThuNhap thunhap;
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()){
                thunhap = new ThuNhap(result.getString("MaDH"),
                        result.getString("TenDonHang"),
                        result.getString("LoaiHang"),
                        result.getString("DiaChiGui"),
                        result.getString("DiaChiNhan"),
                        result.getString("TrangThaiDon"),
                        result.getString("ThuNhap")
                );
                CacThuNhap.add(thunhap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CacThuNhap;
    }

    private ObservableList<ThuNhap> DanhSachThuNhap;

    public void HienThiThuNhap(){
        DanhSachThuNhap = DuLieuThuNhap();
        cTNMaDonHang.setCellValueFactory(new PropertyValueFactory<>("MaDH"));
        cTNTenDonHang.setCellValueFactory(new PropertyValueFactory<>("TenDonHang"));
        cTNLoaiHang.setCellValueFactory(new PropertyValueFactory<>("LoaiHang"));
        cTNDiaChiGui.setCellValueFactory(new PropertyValueFactory<>("DiaChiGui"));
        cTNDiaChiNhan.setCellValueFactory(new PropertyValueFactory<>("DiaChiNhan"));
        cTNTrangThai.setCellValueFactory(new PropertyValueFactory<>("TrangThaiDon"));
        cTNThuNhap.setCellValueFactory(new PropertyValueFactory<>("ThuNhap"));
        BangThuNhap.setItems(DanhSachThuNhap);
    }

    public void HienThiDonHangDangGiao() {
        String trangThaiGiaoHang = "Đang Giao";
        String maShipper = "SP001";

        String sql = "SELECT MaDH, TenDonHang, LoaiHang, DiaChiGui, DiaChiNhan, ThuNhap FROM DonHang WHERE TrangThaiDon = ? AND MaShipper = ?";
        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            prepare.setString(1, trangThaiGiaoHang);
            prepare.setString(2, maShipper);
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                String maDH = result.getString("MaDH");
                String tenDonHang = result.getString("TenDonHang");
                String loaiHang = result.getString("LoaiHang");
                String diaChiGui = result.getString("DiaChiGui");
                String diaChiNhan = result.getString("DiaChiNhan");
                String thuNhap = result.getString("ThuNhap");

                // Hiển thị thông tin đơn hàng đang giao vào các label tương ứng
                labelMaDHTN.setText(maDH);
                labelTenDonHangTN.setText(tenDonHang);
                labelLoaiHangTN.setText(loaiHang);
                labelDiaChiGuiTN.setText(diaChiGui);
                labelDiaChiNhanTN.setText(diaChiNhan);
                labelThuNhapTN.setText(thuNhap);
            } else {
                // Hiển thị thông báo không có đơn hàng đang giao
                labelMaDHTN.setText("");
                labelTenDonHangTN.setText("");
                labelLoaiHangTN.setText("");
                labelDiaChiGuiTN.setText("");
                labelDiaChiNhanTN.setText("");
                labelThuNhapTN.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hoanThanhDonHang() {
        String maDH = labelMaDHTN.getText(); // Lấy mã đơn hàng từ Label

        // Cập nhật trạng thái đơn hàng từ 'Đang Giao' thành 'Hoàn Thành'
        String sqlUpdateDonHang = "UPDATE DonHang SET TrangThaiDon = 'Hoàn Thành' WHERE MaDH = ?";

        // Lấy giá trị ThuNhap từ bảng DonHang
        String sqlSelect = "SELECT ThuNhap FROM DonHang WHERE MaDH = ?";

        // Cập nhật giá trị ThuNhap trong bảng ThuNhap
        String sqlUpdateThuNhap = "UPDATE ThuNhap SET ThuNhap = ThuNhap + ? WHERE MaShipper = 'SP001'";

        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();

        try {
            // Cập nhật trạng thái đơn hàng
            PreparedStatement updateDonHangPrepare = connectionDB.prepareStatement(sqlUpdateDonHang);
            updateDonHangPrepare.setString(1, maDH);
            int rowsAffected = updateDonHangPrepare.executeUpdate();

            if (rowsAffected > 0) {
                // Trạng thái đơn hàng đã được cập nhật thành công

                // Lấy giá trị ThuNhap từ bảng DonHang
                PreparedStatement selectPrepare = connectionDB.prepareStatement(sqlSelect);
                selectPrepare.setString(1, maDH);
                ResultSet selectResult = selectPrepare.executeQuery();

                String thuNhap = "";

                if (selectResult.next()) {
                    thuNhap = selectResult.getString("ThuNhap");
                }

                // Cập nhật giá trị ThuNhap trong bảng ThuNhap
                PreparedStatement updatePrepare = connectionDB.prepareStatement(sqlUpdateThuNhap);
                updatePrepare.setString(1, thuNhap);
                int rowsAffectedThuNhap = updatePrepare.executeUpdate();

                if (rowsAffectedThuNhap > 0) {
                    // Cập nhật thành công trong bảng ThuNhap
                    // Thực hiện các thao tác cần thiết sau khi hoàn thành đơn hàng
                    // Hiển thị thông báo thành công hoặc cập nhật lại giao diện
                    Alert alert = new Alert(Alert.AlertType.ERROR.INFORMATION);
                    alert.setTitle("Thông Báo Cho Bạn");
                    alert.setHeaderText(null);
                    alert.setContentText("Hoàn thành đơn hàng thành công");
                    alert.showAndWait();
                } else {
                    // Không có dòng nào bị ảnh hưởng, không thể cập nhật trong bảng ThuNhap
                    // Hiển thị thông báo lỗi hoặc xử lý lỗi khác tùy theo yêu cầu
                    Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
                    alert.setTitle("Thông Báo Cho Bạn");
                    alert.setHeaderText(null);
                    alert.setContentText("Hoàn thành đơn hàng thành công");
                    alert.showAndWait();
                }
            } else {
                // Không có dòng nào bị ảnh hưởng, không thể cập nhật trạng thái đơn hàng
                // Hiển thị thông báo lỗi hoặc xử lý lỗi khác tùy theo yêu cầu
                Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
                alert.setTitle("Thông Báo Cho Bạn");
                alert.setHeaderText(null);
                alert.setContentText("Lỗi chương trình");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void huyDonHang() {
        String maDH = labelMaDHTN.getText(); // Lấy mã đơn hàng từ Label

        String sql = "UPDATE DonHang SET TrangThaiDon = 'Huỷ' WHERE MaDH = ?";
        Connection connection = DBConnection.ConnectDB();
        DBConnection connectionDB = new DBConnection();
        try {
            PreparedStatement prepare = connectionDB.prepareStatement(sql);
            prepare.setString(1, maDH);
            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                // Cập nhật thành công
                // Thực hiện các thao tác cần thiết sau khi hủy đơn hàng

                // Hiển thị thông báo thành công hoặc cập nhật lại giao diện
                Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
                alert.setTitle("Thông Báo Cho Bạn");
                alert.setHeaderText(null);
                alert.setContentText("Huỷ đơn hàng thành công!");
                alert.showAndWait();
                HienThiThuNhap();
            } else {
                // Không có dòng nào bị ảnh hưởng, không thể cập nhật
                // Hiển thị thông báo lỗi hoặc xử lý lỗi khác tùy theo yêu cầu
                Alert alert = new Alert(Alert.AlertType.ERROR.ERROR);
                alert.setTitle("Thông Báo Cho Bạn");
                alert.setHeaderText(null);
                alert.setContentText("Lỗi chương trình");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HienThiThuNhap();
        HienThiDonHangDangGiao();
    }
}
