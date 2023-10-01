Welcome to the CalRent wiki!

# Software Modeling

## Modeling business processes

- Tính tiền nhà thủ công.

```mermaid
graph LR
A((Bắt đầu)) --> B(Kiểm tra xem tới hạng tiền nhà chưa)
B --> |Chưa| C(Không làm gì cả)
B --> |Có| D(Bắt đầu tính tiền nhà)
D --> E(Chụp công tơ điện)
D --> F(Chụp đồng hồ nước)
E --> G(Tính tiền điện nước -> tiền nhà)
F --> G
G --> H(Gửi hình ảnh tính toán, công tơ điện nước cho chủ trọ)
H --> I(Gửi tiền)
```

- Tính tiền nhà dùng app.
```mermaid
graph LR
A((Bắt đầu)) --> B("Ứng dụng gửi thông báo (Tự động)")
B --> |Tới ngày| C("Chụp công tơ điện -> lấy số khối điện (Tự động)")
B --> |Tới ngày| D("Chụp đồng hồ nước -> lấy số khối nước (Tự động)")
D --> E("Tính tiền điện nước (Tự động) -> tiền nhà (Tự động)")
C --> E
E --> F("Hiện bill chi tiết các thông tin (Tự động)")
F --> G(Chụp hình gửi chủ trọ)
G --> H(Gửi tiền)
```

## User Requests

Ứng dụng calRent giải quyết sự phiền phức trong việc tính toán và gửi tiền trọ hàng tháng. Người dùng có thể tạo yêu cầu mới, nhập thông số đo điện nước, và nhận thông báo về hạn đóng tiền. Lịch sử yêu cầu được lưu trữ, và thông tin về người thuê trọ và chủ trọ được quản lý. Báo cáo thống kê và hỗ trợ khách hàng cũng là những tính năng quan trọng của ứng dụng.
## Modeling User Requirements

### Use case diagram

```mermaid
  graph TD
    subgraph "Người thuê trọ"
        UT1[Tạo yêu cầu mới]
        UT2[Nhập thông số đo điện nước]
        UT3[Nhận thông báo về hạn đóng tiền]
        UT4[Xem lịch sử yêu cầu]
        UT5[Xem thông tin cá nhân]
    end

    subgraph "Hệ thống"
        SS1[Lưu trữ lịch sử và các thông tin từ người thuê trọ]
        SS2[Quản lý thông tin về người thuê trọ và chủ trọ]
    end

    UT1 -->|Tạo yêu cầu| SS1
    UT2 -->|Nhập thông số| SS1
    UT3 -->|Nhận thông báo| SS1
    UT4 -->|Xem lịch sử yêu cầu| SS1
    UT5 -->|Xem thông tin cá nhân| SS2
```

**Use Case Diagram cho ứng dụng calRent:**

- **Người thuê trọ**:
    - Tạo yêu cầu mới (Tính tiền trọ cho một tháng cụ thể)
    - Nhập thông số đo điện nước
    - Nhận thông báo về hạn đóng tiền
    - Xem lịch sử yêu cầu
    - Xem thông tin cá nhân

- **Hệ thống**:
    - Lưu trữ lịch sử các yêu cầu từ người thuê trọ
    - Quản lý thông tin về người thuê trọ và chủ trọ
    - Tạo báo cáo thống kê

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
classDiagram
  class BillEntity {
    - id: Long
    - moneyRent: Long
    - preElectric: Int
    - newElectric: Int
    - priceElectric: Int
    - preWater: Int
    - newWater: Int
    - priceWater: Int
    - timeFrom: Long
    - timeTo: Long
    + BillEntity()
    + BillEntity(bill: Bill)
    + toBill(): Bill
  }
  class DefaultSettingEntity {
    - id: Long
    - timeNotification: Long
    - rentHouse: Long
    - rentElect: Long
    - rentWater: Long
    + DefaultSettingEntity()
    + DefaultSettingEntity(defaultSetting: DefaultSetting)
    + toDefaultSetting(): DefaultSetting
  }
  class DefaultSurchargeEntity {
    - id: Long
    - idSetting: Long
    - name: String
    - price: Long
    + DefaultSurchargeEntity(idSetting: Long)
    + DefaultSurchargeEntity(defaultSurcharge: DefaultSurcharge, idSetting: Long)
    + toDefaultSurcharge(): DefaultSurcharge
  }
  class SurchargeEntity {
    - id: Long
    - idBill: Long
    - name: String
    - price: Int
    + SurchargeEntity(idBill: Long)
    + SurchargeEntity(surcharge: Surcharge, idBill: Long)
    + toSurcharge(): Surcharge
  }

BillEntity --> SurchargeEntity
DefaultSettingEntity --> DefaultSurchargeEntity
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