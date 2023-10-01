Welcome to the CalRent wiki!

# Software Modeling

## Modeling business processes

- Tính tiền nhà thủ công.

```mermaid
graph LR
A((Bắt đầu)) --> B{Tới hạng tiền nhà chưa}
B --> |Chưa| C(Không làm gì cả)
B --> |Có| D(Bắt đầu tính tiền nhà)
D --> E(Chụp công tơ điện)
D --> F(Chụp đồng hồ nước)
E --> G(Tính tiền điện nước -> tiền nhà)
F --> G
G --> H(Gửi hình ảnh tính toán, công tơ điện nước cho chủ trọ)
H --> I((Gửi tiền))
```

- Tính tiền nhà dùng app.
```mermaid
graph LR
A((Bắt đầu)) --> B("Ứng dụng gửi thông báo (Tự động)")
B --> |Tới ngày| C("Chụp công tơ điện")
B --> |Tới ngày| D("Chụp đồng hồ nước")
D --> E("Tính tiền điện nước (Tự động) -> tiền nhà (Tự động)")
C --> E
E --> F("Hiện bill chi tiết các thông tin (Tự động)")
F --> G(Chụp hình gửi chủ trọ)
G --> H((Gửi tiền))
```

## User Requests

Ứng dụng calRent giải quyết sự phiền phức trong việc tính toán và gửi tiền trọ hàng tháng. Người dùng có thể tạo yêu cầu mới, nhập thông số đo điện nước, và nhận thông báo về hạn đóng tiền. Lịch sử yêu cầu được lưu trữ, và thông tin về người thuê trọ và chủ trọ được quản lý. Báo cáo thống kê và hỗ trợ khách hàng cũng là những tính năng quan trọng của ứng dụng.
## Modeling User Requirements

### Use case diagram

```mermaid
  graph TD
    subgraph UT["Actor: Người thuê trọ"]
    end

    subgraph "Hệ thống CalRent"
        SS0["Tính tiền trọ mỗi tháng"]
        SS1["Quản lý tiền trọ"]
        SS2["Thông báo ngày hạn tính tiền"]
    end

    UT -->|Thêm/Xoá/Sửa Tiền trọ tháng| SS1
    UT -->|Xem lịch sử yêu cầu| SS1
    UT -->|Nhận thông báo| SS2
    UT --> |Tính tiền| SS0
```

### Architectural Models

```mermaid
graph TD
  subgraph Android_Application[Android Application]
    subgraph User_Interface[User Interface]
      View(View)
      Component(Component)
    end

    subgraph ViewModel[ViewModel]
      flow(Flow / MutableStateFlow)
    end

    subgraph Data_Repository[Data Repository]
      RoomData(Room Database)
    end
  end

  User_Interface <--> ViewModel
  ViewModel <--> Data_Repository

```

### Data Modeling

```mermaid
erDiagram
    BillEntity {
    id Long
    moneyRent Long
    preElectric Int
    newElectric Int
    priceElectric Int
    preWater Int
    newWater Int
    priceWater Int
    timeFrom Long
    timeTo Long
}
    DefaultSettingEntity {
    id Long
    timeNotification Long
    rentHouse Long
    rentElect Long
    rentWater Long
}
    DefaultSurchargeEntity {
    id Long
    idSetting Long
    name String
    price Long
}
    SurchargeEntity {
    id Long
    idBill Long
    name String
    price Int
}

BillEntity ||--|{ SurchargeEntity: idBill
DefaultSettingEntity ||--|{ DefaultSurchargeEntity: idSetting

```

### Sequence Diagram

### Innit Flow

```mermaid
graph TD
  subgraph Start
    A[Kiểm tra xem các chỉ số mặc định, điện, nước, tiền nhà, điện nước đã có chưa]
  end

  subgraph Có Dữ Liệu Mặc Định
    B[Chuyển đến luồng tính tiền nhà]
    A -->|Có dữ liệu| B
  end

  subgraph Không Có Dữ Liệu Mặc Định
    A -->|Không có dữ liệu| D[Bắt người dùng nhập: Thời gian, Tiền Nhà]
    D --> E[Nhập tiếp khối điện khởi điểm, khối nước khởi điểm, giá nước, giá điện]
    E --> F[Nhập tiếp các phụ wifi, rác,...]
    F --> G[Chuyển đến luồng tính tiền nhà]
  end
```

### Calculator New Rent Month

```mermaid
graph TD
  subgraph Start
    A[Cho phép người dùng nhập khối nước, khối điện tháng này]
  end

  subgraph havePreMonth[Có Dữ Liệu Tháng Trước]
    B[Tải dữ liệu tháng trước]
    A -->|Có dữ liệu| B
  end

  subgraph haveDefault[Có Dữ Liệu Mặc Định]
    C[Tải dữ liệu mặc định]
    B -->|Có dữ liệu| C
  end

  subgraph Tính Toán Tiền Nước và Điện
    D[Lấy hiệu khối lượng nước và điện của tháng trước và mới nhập]
    E[Lấy tiền điện/nước của dữ liệu mặc định]
    D --> F[Thực hiện tính toán tiền điện/nước]
    E --> F
  end

  subgraph Hiển Thị Kết Quả
    F --> G[Show lên UI]
  end

  subgraph Lưu Vào Database
    G --> H[Lưu vào database]
  end

  subgraph haveboth[Có cả hai]
      
  end

  subgraph justOne[Chỉ có một]

  end

  havePreMonth --> haveboth
  haveDefault --> haveboth
  haveboth --> D

  haveDefault --> justOne
  justOne --> E
```