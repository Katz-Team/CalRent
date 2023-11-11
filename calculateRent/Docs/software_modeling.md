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
        SS0["Tính tiền trọ"]
        SS1["Quản lý tiền trọ"]
        SS2["Thông báo ngày tính tiền"]
        SS3["Khởi tạo mặc định"]
    end

    UT -->|Xoá/Sửa Tiền trọ tháng| SS1
    UT -->|Xem lịch sử yêu cầu| SS1
    UT --> |Tính tiền | SS0
    SS0 --> | Không có tháng trước | SS3
    SS0 --> | Lưu tiền trọ | SS1
    UT -->|Nhận thông báo| SS2
```

#### List of use case


| ID       | Tên                      | Mô tả ngắn                                                                                                                                                                                                                                  |
|----------|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| UC - 001 | Tính tiền trọ            | Use case này cho phép người thuê trọ tính tiền trọ cho một tháng cụ thể. Người dùng có thể nhập các thông số như khối nước, khối điện và các phụ thu (ví dụ: wifi, rác), sau đó ứng dụng sẽ tính toán số tiền cần đóng và hiển thị kết quả. |
| UC - 002 | Quản lý tiền trọ         | Use case này cho phép người dùng quản lý thông tin về tiền trọ, bao gồm lịch sử các yêu cầu tính tiền trọ và các thông tin cá nhân như tên, địa chỉ. Người dùng có thể xem lịch sử, chỉnh sửa thông tin và thêm mới thông tin cần thiết.    |
| UC - 003 | Thông báo ngày tính tiền | Use case này tự động thông báo đến người thuê trọ về ngày đến hạn đóng tiền. Ứng dụng sẽ gửi thông báo nhắc nhở để người thuê trọ không quên việc thanh toán tiền trọ vào ngày hợp lệ.                                                      |
| UC - 004 | Khởi tạo mặc định        | Use case này sử dụng để lấy thông tin mặc định về phòng trọ, bao gồm tiền thuê, tiền nước, khối điện ban đầu, khối nước ban đầu, và các phí phụ thu sau đó lưu chúng vào cơ sở dữ liệu.                                                     |

#### Use case specification

1. Tính tiền trọ


| ID                       | UC - 001                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Tên**                  | Tính tiền trọ                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| **Mô tả ngắn**           | Use case này cho phép người thuê trọ tính tiền trọ cho một tháng cụ thể. Người dùng có thể nhập các thông số như khối nước, khối điện và các phụ thu (ví dụ: wifi, rác), sau đó ứng dụng sẽ tính toán số tiền cần đóng và hiển thị kết quả.                                                                                                                                                                                                                                                                                                                     |
| **Người dùng**           | Người thuê trọ                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| **Luồng cơ bản**         | 1. Người thuê trọ khởi động ứng dụng.<br /> 2. Người thuê trọ chọn use case "Tính tiền trọ".<br /> 3. Hệ thống yêu cầu người thuê trọ nhập các thông số: khối nước, khối điện và các phụ thu (nếu có).<br /> 4. Người thuê trọ nhập thông số và xác nhận.<br /> 5. Hệ thống tính toán số tiền cần đóng dựa trên thông số nhập vào và thông tin về giá điện/nước (của tháng trước hoặc nhập mặc định từ đầu).<br /> 6. Hệ thống hiển thị kết quả tính toán trên giao diện người dùng. 7. Người thuê trọ có thể lưu kết quả hoặc thực hiện lại tính toán nếu cần. |
| **Luồng thay thế**       | *Bước 3:* Nếu người thuê trọ không nhập đủ thông số, hệ thống hiển thị thông báo lỗi và yêu cầu nhập lại thông số. <br/> *Bước 4:* Nếu người thuê trọ không xác nhận thông số, quy trình kết thúc. <br/> *Bước 6:* Nếu có lỗi trong quá trình tính toán, hệ thống hiển thị thông báo lỗi và yêu cầu người thuê trọ kiểm tra lại thông số nhập và thực hiện lại tính toán.                                                                                                                                                                                       |
| **Yêu cầu đặc biệt**     |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| **Điều kiện tiên quyết** | Hệ thống phải có chỉ số tháng trước hoặc chỉ số mặc định                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| **Hậu điều kiện**        | Kết quả tính toán được hiển thị trên giao diện người dùng và có thể được lưu vào cơ sở dữ liệu nếu người thuê trọ muốn.                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| **Điểm mở rộng**         |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |

2. Quản lý tiền trọ


| ID                       | UC - 002                                                                                                                                                                                                                                                                                                                                                                                                                                        |
|--------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Tên**                  | Quản lý tiền trọ                                                                                                                                                                                                                                                                                                                                                                                                                                |
| **Mô tả ngắn**           | Use case này cho phép người dùng quản lý thông tin về tiền trọ, bao gồm lịch sử các yêu cầu tính tiền trọ và các thông tin cá nhân như tên, địa chỉ. Người dùng có thể xem lịch sử, chỉnh sửa thông tin và thêm mới thông tin cần thiết.                                                                                                                                                                                                        |
| **Người dùng**           | Người thuê trọ                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| **Luồng cơ bản**         | 1. Người thuê trọ khởi động ứng dụng.<br />2. Người thuê trọ chọn use case "Quản lý tiền trọ".<br />3. Hệ thống hiển thị thông tin phòng trọ, bao gồm tên, địa chỉ, giá nước điện, các phụ phí khác.<br />4. Người thuê trọ có thể xem lịch sử các yêu cầu tính tiền trọ trước đó.<br /> 5. Người thuê trọ có thể chỉnh sửa thông tin phòng trọ nếu cần.<br />6. Người thuê trọ có thể thêm mới thông tin cần thiết (ví dụ: thông tin liên hệ). |
| **Luồng thay thế**       | *Bước 5:* Nếu người thuê trọ không muốn chỉnh sửa thông tin phòng trọ, quy trình kết thúc. <br/> *Bước 6:* Nếu người thuê trọ không thêm mới thông tin, quy trình kết thúc.                                                                                                                                                                                                                                                                     |
| **Yêu cầu đặc biệt**     |                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| **Điều kiện tiên quyết** |                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| **Hậu điều kiện**        | Thông tin phòng trọ và lịch sử các yêu cầu tính tiền trọ có thể được cập nhật và lưu vào cơ sở dữ liệu.                                                                                                                                                                                                                                                                                                                                         |
| **Điểm mở rộng**         |                                                                                                                                                                                                                                                                                                                                                                                                                                                 |

3. Thông báo ngày tính tiền


| ID                       | UC - 003                                                                                                                                                                                                                                                                                                       |
|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Tên**                  | Thông báo ngày tính tiền                                                                                                                                                                                                                                                                                       |
| **Mô tả ngắn**           | Use case này tự động thông báo đến người thuê trọ về ngày đến hạn đóng tiền. Ứng dụng sẽ gửi thông báo nhắc nhở để người thuê trọ không quên việc tính tiền trọ vào ngày hợp lệ.                                                                                                                               |
| **Người dùng**           | Người thuê trọ                                                                                                                                                                                                                                                                                                 |
| **Luồng cơ bản**         | 1. Hệ thống tự động xác định ngày đến hạn đóng tiền dựa trên thông tin trong cơ sở dữ liệu.<br /> 2. Hệ thống tự động gửi thông báo nhắc nhở đến người thuê trọ trước ngày đến hạn đóng tiền (ví dụ: 3 ngày trước). <br /> 3. Người thuê trọ nhận thông báo và có thể đứa đến chức năng tính tiền trọ nếu cân. |
| **Luồng thay thế**       | *Bước 3:* Nếu người thuê trọ không xem thông tin chi tiết về việc tính tiền trọ, quy trình kết thúc.                                                                                                                                                                                                           |
| **Điều kiện tiên quyết** | Hệ thống đã lưu thông tin về ngày đến hạn đóng tiền trong cơ sở dữ liệu.                                                                                                                                                                                                                                       |
| **Hậu điều kiện**        | Thông báo nhắc nhở đã được gửi thành công đến người thuê trọ và có thể xem trên ứng dụng nếu cần.                                                                                                                                                                                                              |
| **Điểm mở rộng**         | Có thể thêm tính năng cho phép người thuê trọ thực hiện thanh toán tiền trọ trực tiếp từ ứng dụng.                                                                                                                                                                                                             |

4. Khởi tạo mặc định

| ID                       | UC - 004                                                                                                                                                                                                                                                  |
|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Tên**                  | Khởi tạo mặc định                                                                                                                                                                                                                                         |
| **Mô tả ngắn**           | Use case này dùng để thu thập thông tin mặc định về phòng trọ, bao gồm tiền thuê, tiền nước, khối điện ban đầu, khối nước ban đầu, và các phí phụ thu, sau đó lưu chúng vào cơ sở dữ liệu.                                                                |
| **Người dùng**           | Người thuê trọ                                                                                                                                                                                                                                            |
| **Luồng cơ bản**         | 1. Người thuê trọ khởi động ứng dụng. <br /> 2. Người thuê trọ chọn use case "Khởi tạo mặc định". <br /> 3. Hệ thống thu thập thông tin mặc định về phòng trọ. <br /> 4. Hệ thống lưu thông tin thu thập vào cơ sở dữ liệu. <br /> 5. Quá trình kết thúc. |                                                                                                                                                                                      |
| **Luồng thay thế**       | Bước 4: Nếu hệ thống không thể lưu thông tin vào cơ sở dữ liệu, quy trình kết thúc và thông báo lỗi cho người dùng.                                                                                                                                       |                                                                                                                                                                           |
| **Điều kiện tiên quyết** | Database chưa có thông tin mặc định của phòng                                                                                                                                                                                                             |
| **Hậu điều kiện**        | Thông tin mặc định đã được lưu vào cơ sở dữ liệu và có thể sử dụng cho việc tính toán tiền trọ và quản lý phòng trọ.                                                                                                                                      |
| **Điểm mở rộng**         | Có thể bổ sung tính năng cho phép người thuê trọ cập nhật hoặc chỉnh sửa thông tin mặc định trong tương lai.                                                                                                                                              | 

### User Story Map


| Tính tiền điện nước                 | Quản lý tiền trọ                                        | Thông báo ngày tính tiền         | Khởi tạo mặc định               |
|-------------------------------------|---------------------------------------------------------|----------------------------------|---------------------------------|
| Ghi nhận tiêu thụ điện nước (1)     | Xem Lịch sử thanh toán tiền trọ (1)                     | Thông báo đến hạn thanh toán (1) | Ghi nhận thông tin thủ công (1) |
| Nhập điện nước qua hình ảnh    (1)  | Quản Lý Lịch Sử Thanh Toán thông qua Chức Năng Xóa  (1) | Tắt/Bật thông báo  (2)           |                                 |
| Nhập điện nước từ hình hóa đơn (2)  | Chỉnh sửa thông tin tiền trọ đã chọn (1)                |                                  |                                 |
|                                     | Chỉnh sửa thông tin mặc định tiền trọ  (1)              |                                  |                                 |
|                                     | Lưu thanh toán tiền trọ mới (1)                         |                                  |                                 |

```
Lưu ý:

(1): Sẽ release vào version 1

(2): Sẽ release vào version 2
```

### User Story Description For MVP

| Tiêu đề: Ghi nhận điện nước tiêu thụ thủ công                                                                                                                                                                                                                                                                                          |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Người dùng muốn có khả năng ghi nhận tiêu thụ điện nước hàng tháng bằng cách nhập thủ công các chỉ số đo điện và nước vào ứng dụng di động của họ.                                                                                                                                                                         |
| System provide: Hệ thống cung cấp giao diện cho phép người dùng nhập các chỉ số đo điện và nước một cách dễ dàng. Sau khi người dùng nhập các thông số này, hệ thống sẽ tính toán tự động số tiền phải đóng dựa trên giá tiền đã được định sẵn. Kết quả tính toán sẽ được hiển thị trên giao diện để người dùng xem trước và xác nhận. |

| Tiêu đề: Lưu thanh toán tiền trọ mới                                                                                                                                                                                                                                                                         |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Người dùng muốn có khả năng lưu trữ kết quả tính toán tiền trọ sau khi đã nhập thông số đo điện và nước vào ứng dụng di động.                                                                                                                                                                    |
| System provide: Hệ thống cung cấp tính năng lưu trữ kết quả tính toán, bao gồm thông tin về số tiền cần đóng, tháng tính tiền, và các thông số đo điện và nước tương ứng. Kết quả tính toán sẽ được lưu trữ trong cơ sở dữ liệu của ứng dụng để người dùng có thể xem lại và theo dõi lịch sử tính tiền trọ. |

| Tiêu đề: Nhập điện nước qua hình ảnh                                                                                                                                                                                                                                                                                                                                                                                            |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Người dùng muốn có khả năng nhập thông số đo điện và nước thông qua hình ảnh để tiết kiệm thời gian và công sức.                                                                                                                                                                                                                                                                                                    |
| System provide: Hệ thống cung cấp tính năng cho phép người dùng chụp hình ảnh hoặc tải lên hình ảnh của đồng hồ điện và nước. Hệ thống sẽ tự động phân tích hình ảnh để trích xuất thông tin về các con số đo điện và nước. Sau đó, thông tin này sẽ được nhập tự động vào ứng dụng để tiến hành tính toán tiền trọ. Điều này giúp người dùng dễ dàng và nhanh chóng cập nhật thông tin mà không cần phải nhập tay từng con số. |

| Tiêu đề: Ghi nhận thông tin thủ công                                                                                                                                                                                                                                                                                                                                                                    |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Người dùng muốn có khả năng ghi nhận thông tin mặc định của phòng trọ thủ công, bao gồm tiền thuê, tiền nước, khối điện ban đầu, khối nước ban đầu và các phí phụ thu                                                                                                                                                                                                                       |
| System provide: Hệ thống cung cấp khả năng cho người thuê trọ ghi nhận và cập nhật thông tin mặc định của phòng trọ thủ công thông qua ứng dụng di động. Điều này bao gồm nhập số tiền thuê, khối nước, khối điện ban đầu và các phí phụ thu một cách thủ công. Sau khi tôi đã nhập thông tin này, hệ thống sẽ lưu chúng vào cơ sở dữ liệu để sử dụng cho việc tính toán tiền trọ và quản lý phòng trọ. |

| Tiêu đề: Xem Lịch sử thanh toán tiền trọ                                                                                                                                                                                                                                                                                                                                              |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Người dùng muốn có khả năng xem lịch sử thanh toán tiền trọ để theo dõi các khoản thanh toán trước đây và kiểm tra tính đúng đắn của các khoản thanh toán đó.                                                                                                                                                                                                             |
| System provide: Hệ thống cung cấp khả năng cho người thuê trọ, để xem lịch sử thanh toán tiền trọ thông qua ứng dụng di động. Điều này bao gồm việc hiển thị danh sách các khoản thanh toán trước đây bao gồm thông tin về tháng, số tiền. Tôi có thể dễ dàng xem thông tin chi tiết về từng khoản thanh toán bằng cách chọn vào một khoản cụ thể trong danh sách lịch sử thanh toán. |

---

**Tiêu đề:** Quản Lý Lịch Sử Thanh Toán thông qua Chức Năng Xóa

**Mô tả User Story:**
Là một người dùng của ứng dụng, tôi muốn có khả năng lựa chọn và xóa các bản ghi lịch sử thanh toán cụ thể. Mục tiêu của tôi là quản lý và duy trì lịch sử thanh toán của mình một cách gọn gàng và hiệu quả. Khi tôi chọn một hoặc nhiều bản ghi từ danh sách, tôi mong đợi hệ thống sẽ yêu cầu xác nhận trước khi thực hiện xóa, đảm bảo rằng tôi không xóa nhầm bản ghi quan trọng.

**Chức năng Hệ thống Cung cấp:**
- **Hiển thị Danh Sách Lịch Sử Thanh Toán**: Hệ thống sẽ hiển thị danh sách đầy đủ các bản ghi lịch sử thanh toán cho người dùng.
- **Chọn Bản Ghi để Xóa**: Người dùng có thể lựa chọn một hoặc nhiều bản ghi từ danh sách này để xóa.
- **Xác Nhận Trước Khi Xóa**: Sau khi người dùng chọn bản ghi cần xóa, hệ thống sẽ yêu cầu xác nhận để đảm bảo rằng người dùng không xóa nhầm bản ghi.
- **Xử Lý Xóa Bản Ghi**: Nếu người dùng xác nhận, hệ thống sẽ tiến hành xóa các bản ghi đã chọn và cung cấp thông báo xác nhận việc xóa thành công.
- **Hủy Xóa và Quay lại Danh Sách**: Trong trường hợp người dùng quyết định không xóa hoặc hủy thao tác, hệ thống sẽ quay lại danh sách lịch sử thanh toán mà không thực hiện xóa.

---

| Tiêu đề: Chỉnh sửa thông tin tiền trọ đã chọn                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Như một người dùng của ứng dụng, tôi muốn có khả năng chỉnh sửa thông tin về tiền trọ đã được ghi nhận để cập nhật và điều chỉnh thông tin liên quan đến tiền trọ. Khi tôi chọn chỉnh sửa, tôi sẽ có thể thay đổi số tiền, ngày thanh toán, và các chi tiết liên quan đến tiền trọ. Hệ thống sẽ cập nhật thông tin này vào cơ sở dữ liệu sau khi tôi hoàn tất chỉnh sửa.                                                                                                                                                                                                                                                                                                                      |
| System provide: Hệ thống cung cấp giao diện cho người dùng để xem và chỉnh sửa thông tin tiền trọ đã ghi nhận trước đó. Người dùng chọn thông tin tiền trọ cần chỉnh sửa từ danh sách. Hệ thống hiển thị thông tin tiền trọ đang chọn và cho phép người dùng chỉnh sửa số tiền, ngày thanh toán, và các chi tiết liên quan đến tiền trọ. Người dùng cập nhật thông tin và chấp nhận chỉnh sửa. Hệ thống kiểm tra và xác nhận thông tin cập nhật từ người dùng. Nếu thông tin hợp lệ, hệ thống cập nhật thông tin tiền trọ trong cơ sở dữ liệu và hiển thị thông báo xác nhận cho người dùng. Nếu thông tin không hợp lệ, hệ thống cung cấp thông báo lỗi cho người dùng và yêu cầu họ nhập lại thông tin. |

| Tiêu đề: Chỉnh sửa thông tin mặc định tiền trọ                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Như một người dùng của ứng dụng, tôi muốn có khả năng chỉnh sửa thông tin mặc định về tiền trọ để cập nhật và điều chỉnh các giá trị tiền thuê, tiền nước, khối điện ban đầu, khối nước ban đầu và các phí phụ thu một cách dễ dàng. Khi tôi cần thay đổi các giá trị này, tôi sẽ có thể thực hiện tại giao diện chỉnh sửa thông tin mặc định. Hệ thống sẽ lưu lại các thay đổi này vào cơ sở dữ liệu để áp dụng cho tính toán tiền trọ và quản lý phòng trọ.                                                                                                   |
| System provide: Hệ thống cung cấp giao diện cho người dùng để xem và chỉnh sửa thông tin mặc định về tiền trọ. Người dùng có thể thay đổi giá trị tiền thuê, tiền nước, khối điện ban đầu, khối nước ban đầu và các phí phụ thu theo nhu cầu của họ. Hệ thống kiểm tra và xác nhận thông tin cập nhật từ người dùng. Nếu thông tin hợp lệ, hệ thống cập nhật thông tin mặc định về tiền trọ trong cơ sở dữ liệu và hiển thị thông báo xác nhận cho người dùng. Nếu thông tin không hợp lệ, hệ thống cung cấp thông báo lỗi cho người dùng và yêu cầu họ nhập lại thông tin. | 

| Tiêu đề: Thông báo đến hạn thanh toán                                                                                                                                                                                                                                                                                                                                       |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story: Như một người dùng của ứng dụng, tôi muốn nhận được thông báo nhắc nhở đến ngày đến hạn thanh toán tiền trọ. Ứng dụng sẽ tự động gửi thông báo nhắc nhở cho tôi trước ngày đó để tôi không quên việc thanh toán tiền trọ vào ngày hợp lệ. Điều này giúp tôi duy trì việc thanh toán kịp thời và tránh các rủi ro liên quan đến việc không đóng tiền đúng hạn.   |
| System provide: Hệ thống sẽ xác định ngày đến hạn thanh toán dựa trên thông tin trong cơ sở dữ liệu. Hệ thống sẽ tự động gửi thông báo nhắc nhở cho người dùng trước ngày đến hạn (ví dụ: 3 ngày trước). Người dùng sẽ nhận thông báo và có thể xem thông tin chi tiết về việc tính toán tiền trọ nếu cần. Nếu người dùng không xem thông tin chi tiết, quy trình kết thúc. | 

### User

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

        subgraph UseCase["Domain (UseCase)"]
            Logic(Logic)
        end

        subgraph Data_Repository[Data Repository]
            RoomData(Room Database)
        end
    end

    User_Interface <--> ViewModel
    ViewModel <--> UseCase
    UseCase <--> Data_Repository

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

#### 1. Flow Ghi nhận thông tin thủ công

```mermaid
sequenceDiagram
    actor User
    participant FeedScreen
    participant CalScreen
    participant BillScreen
    participant CalViewModel
    participant BillViewModel
    participant DatabaseUseCase
    participant Repository
    participant Room

    User ->> FeedScreen: Chọn chức năng tính tiền điện
    FeedScreen ->> CalScreen: Chuyển màn hình
    User ->> CalScreen: Nhập khối điện, khối nước
    User ->> CalScreen: Chọn Tính tiền
    CalScreen ->> CalViewModel: getLastBillTemp(): Bill
    CalViewModel ->> DatabaseUseCase: getLastBillTemp(): Bill
    
    par Retrieve DefaultSetting and Last Bill
        DatabaseUseCase ->> Repository: getDefaultSetting: DefaultSetting
        DatabaseUseCase ->> Repository: getBillLast: Bill
    end

    Repository ->> Room: getAllDefaultSetting(): List<DefaultSettingEntity>
    Room -->> Repository: return List<DefaultSettingEntity> -> get first
    Repository ->> Room: getAllBill(): List<BillEntity>
    Room -->> Repository: return List<BillEntity> -> get last BillEntity
    Repository ->> Repository: convertEntityToObject(): DefaultSetting
    Repository ->> Repository: convertEntityToObject(): Bill
    Repository -->> DatabaseUseCase: return DefaultSetting
    Repository -->> DatabaseUseCase: return Bill
    DatabaseUseCase ->> DatabaseUseCase: merge Bill and DefaultSetting
    DatabaseUseCase -->> CalViewModel: return Bill
    CalViewModel -->> CalScreen: return Bill

    CalScreen ->> BillScreen: Chuyển màn hình kèm data Bill
    BillScreen ->> BillScreen: getTotalMoney()

    User ->> BillScreen: Chọn Hoàn tất
    BillScreen ->> BillViewModel: insertBill(bill)
    BillViewModel ->> DatabaseUseCase: insertBill(bill)

    par Insert Bill and Surcharge
        DatabaseUseCase ->> Repository: insertBill(bill)
        DatabaseUseCase ->> Repository: insertSurcharge(surchargeEntity)
    end

    Repository ->> Room: insertBill(billEntity)
    Room -->> Repository: Confirmation
    Repository ->> Room: insertSurcharge(surchargeEntity)
    Room -->> Repository: Confirmation
    Repository -->> DatabaseUseCase: Confirm Insertions
    DatabaseUseCase -->> BillViewModel: Confirm
    BillViewModel -->> BillScreen: Display Confirmation

    BillScreen ->> FeedScreen: Chuyển về và hiển thị thông báo thành công

    alt Error occurs
        BillScreen ->> User: Hiển thị thông báo lỗi
    end
```

#### 2. Ghi nhận điện nước tiêu thụ thủ công

```mermaid
sequenceDiagram
    actor User
    participant FeedScreen
    participant CalScreen
    participant BillScreen
    participant CalViewModel
    participant BillViewModel
    participant DatabaseUseCase
    participant Repository
    participant Room

    User ->> FeedScreen: Chọn chức năng tính tiền điện
    FeedScreen ->> CalScreen: Chuyển màn hình
    User ->> CalScreen: Nhập khối điện, khối nước
    User ->> CalScreen: Chọn Tính tiền
    CalScreen ->> CalViewModel: getLastBillTemp(): Bill
    CalViewModel ->> DatabaseUseCase: getLastBillTemp(kgElect,kgWater,Time) : Bill

    DatabaseUseCase ->> Repository: getDefaultSetting: DefaultSetting
    Repository ->> Room: getAllDefaultSetting(): List<DefaultSettingEntity>
    Room -->> Repository: return List<DefaultSettingEntity> -> get first
    Repository ->> Repository: convertEntityToObject(): DefaultSetting
    Repository -->> DatabaseUseCase: return DefaultSetting

    DatabaseUseCase ->> Repository: getBillLast: Bill
    Repository ->> Room: getAllBill(): List<BillEntity>
    Room -->> Repository: return List<BillEntity> -> get last BillEntity
    Repository ->> Repository: convertEntityToObject(): Bill
    Repository -->> DatabaseUseCase: return Bill
    DatabaseUseCase ->> DatabaseUseCase: merge Bill and DefaultSetting
    DatabaseUseCase -->> CalViewModel: return Bill
    CalViewModel -->> CalScreen: return Bill


    CalScreen ->> BillScreen: Chuyển màn hình kèm data Bill

    BillScreen ->> BillScreen: getTotalMoney()

    User ->> BillScreen: Chọn Hoàn tất
    BillScreen ->> BillViewModel: insertBill(bill)
    BillViewModel ->> DatabaseUseCase: insertBill(bill)
    DatabaseUseCase ->> Repository: insertBill(bill)
    Repository ->> Room: insertBill(billEntity)
    Repository ->> Room: insertSurcharge(surchargeEntity)

    BillScreen ->> FeedScreen: Chuyển về
```

#### 3. Xóa lịch sử thanh toán đã chọn

```mermaid
sequenceDiagram
    actor User
    participant FeedScreen
    participant FeedViewModel
    participant DatabaseUseCase
    participant Repository
    participant Room

    User ->> FeedScreen: Chọn Edit Model
    FeedScreen ->> FeedScreen: Chuyển sang Edit Model
    User ->> FeedScreen: Chọn Item muốn xóa
    User ->> FeedScreen: Chọn xóa
    FeedScreen -->> User: Xác thực muốn xóa

    alt name
        User ->> FeedScreen: Xác Nhận
        FeedScreen ->> FeedViewModel: deleteBill(bill)
        FeedViewModel ->> DatabaseUseCase: removeBill(bill)
        DatabaseUseCase ->> Repository: removeBill(bill)
        Repository ->> Room: deleteSurcharge(SurchargeEntity)
        Repository ->> Room: deleteBill(BillEntity)
    else
        User ->> FeedScreen: Hủy Bỏ
    end
```

#### 4. Xem Lịch sử thanh toán tiền trọ

```mermaid
sequenceDiagram
    participant FeedScreen
    participant FeedViewModel
    participant DatabaseUseCase
    participant Repository
    participant Room

    FeedViewModel ->> DatabaseUseCase: getFlowBills: Flow<List<Bill>>
    DatabaseUseCase ->> Repository: getFlowBills: Flow<List<Bill>>
    Repository ->> Room: loadBillAndSurcharge: Flow<Map<BillEntity, List<SurchargeEntity>>>
    Room -->> Repository: return Flow<Map<BillEntity, List<SurchargeEntity>>>
    Repository -->> DatabaseUseCase: return Flow<List<Bill>>
    DatabaseUseCase -->> FeedViewModel: Flow<List<Bill>>

    FeedScreen ->> FeedViewModel: getRoom() : StateFlow<List<Bill>>
    FeedViewModel -->> FeedScreen: StateFlow<List<Bill>>
```

#### 5. Lưu thanh toán tiền trọ mới

```mermaid
sequenceDiagram
    participant DatabaseUseCase
    participant Repository
    participant Room


    DatabaseUseCase ->> Repository: insertBill(bill)
    Repository ->> Room: insertBill(BillEntity)
    Repository ->> Room: insertSurcharge(SurchargeEntity)
```

#### 6. Chỉnh sửa thông tin tiền trọ đã chọn
```mermaid
sequenceDiagram
    actor User
    participant FeedScreen
    participant BillScreen
    participant BillViewModel
    participant DatabaseUseCase
    participant Repository
    participant Room

    User ->> FeedScreen: Chọn tiền trọ muốn chỉnh sửa
    FeedScreen ->> BillScreen: Chuyển màn hình kèm data Bill
    User ->> BillScreen: Chọn Edit Mode
    User ->> BillScreen: Sửa đổi các thông tin
    User ->> BillScreen: Chọn lưu
    BillScreen ->> BillViewModel: updateBill(bill)
    BillViewModel ->> DatabaseUseCase: updateBill(bill)
    DatabaseUseCase ->> Repository: updateBill(bill)
    Repository ->> Room: updateBillEntity(BillEntity)
    Repository ->> Room: updateSurchargeEntity(BillEntity)
```


#### 7. Chỉnh sửa thông tin mặc định tiền trọ

```mermaid
sequenceDiagram
    actor User
    participant FeedScreen
    participant DefaultSettingScreen
    participant DefaultSettingViewModel
    participant DatabaseUseCase
    participant Repository
    participant Room

    User ->> FeedScreen: Chọn Chỉnh Sửa Default
    FeedScreen ->> DefaultSettingScreen: Chuyển màn hình
    DefaultSettingScreen ->> DefaultSettingViewModel: getDefaultSetting(): DefaultSetting
    DefaultSettingViewModel ->> DatabaseUseCase: getDefaultSetting(): DefaultSetting
    DatabaseUseCase ->> Repository: getDefaultSetting(): DefaultSetting
    Repository ->> Room: getDefaultSettingEntity(): DefaultSettingEntity
    Repository ->> Room: getDefaultSettingEntity(): DefaultSurchargeEntity

    Room -->> Repository: return DefaultSettingEntity
    Repository -->> DatabaseUseCase: return DefaultSetting
    DatabaseUseCase -->> DefaultSettingViewModel: return DefaultSetting
    DefaultSettingViewModel -->> DefaultSettingScreen: return DefaultSetting

    User ->> DefaultSettingScreen: Thay đổi thông tin
    DefaultSettingScreen ->> DefaultSettingViewModel: updateDefaultSetting(defaultSetting)
    DefaultSettingViewModel ->> DatabaseUseCase: updateDefaultSetting(defaultSetting)
    DatabaseUseCase ->> Repository: updateDefaultSetting(defaultSetting)
    Repository ->> Room: updateDefaultSettingEntity(defaultSettingEntity)
    Repository ->> Room: updateDefaultSurchargeEntity(defaultSurchargeEntity)
    DefaultSettingScreen ->> FeedScreen: Chuyên màn hình
```

#### 8. Nhập điện qua hình ảnh

```mermaid
sequenceDiagram
    actor User
    participant SomeThingScreen
    participant SomeThingViewModel
    participant CameraScreen
    participant CameraViewModel
    participant CameraUseCase

    User ->> SomeThingScreen: Chọn chức năng camera
    SomeThingScreen ->> SomeThingViewModel: Chuyển sang màn hình
    SomeThingViewModel ->> CameraScreen: Navigator kèm theo mode Water hoặc Electric
    User ->> CameraScreen: Di chuyển camera đến bức ảnh
    CameraScreen ->> CameraViewModel: detectClock() : String
    loop detect bức ảnh
        CameraViewModel ->> CameraUseCase: detectClock() : String
        CameraUseCase ->>  CameraUseCase: detectAllString() : String
        CameraUseCase ->> CameraUseCase: filterResult(water/electric) : String
        break filterResult != null
            CameraUseCase -->> CameraViewModel: return result : String
        end

    end
    CameraViewModel -->> SomeThingScreen: Navigator kèm theo result: String

```

### Class Diagram

```mermaid
classDiagram
    class Bill {
        id: Long
        moneyRent: Long
        electricityBill: ElectricityBill
        waterBill: WaterBill
        timeFrom: Long
        timeTo: Long
        surcharges: ArrayList<Surcharge>
        +getTotalMoney(): Long
    }

    class DefaultSetting {
        id: Long
        timeNotification: Long
        rentHouse: Long
        rentElect: Long
        rentWater: Long
        defaultSurcharges: ArrayList<DefaultSurcharge>
        +toSurcharges(): ArrayList<Surcharge>
    }

    class DefaultSurcharge {
        id: Long
        name: String
        price: Long
        +toSurcharge(): Surcharge
    }

    class ElectricityBill {
        preElectric: Int
        newElectric: Int
        price: Int
        +getKgElectric(): Int
        +getMoney(): Int
    }

    class Room {
        bills: List<Bill>
        +getBill(index: Int): Bill
        +getFirstBill(): Bill
    }

    class Surcharge {
        id: Long
        name: String
        price: Int
    }

    class WaterBill {
        preWater: Int
        newWater: Int
        price: Int
        +getKgWater(): Int
        +getMoney(): Int
    }

    Bill <-- DefaultSetting
    DefaultSetting --> DefaultSurcharge
    DefaultSurcharge --> Surcharge
    Bill --> ElectricityBill
    Bill --> WaterBill
    Room --> Bill

```


### UI Flow

#### 1. Yêu cầu thiết kế:
- **Phong cách thiết kế**: [Material Design 3 Kit](https://www.figma.com/community/file/1035203688168086460)
- **Màu sắc**: Chưa biết.
- **Logo & hình ảnh**: Chưa biết.

#### 2. Mô tả chức năng:

- **Ghi Nhận Chỉ Số Điện và Nước**: Người dùng nhập các chỉ số điện và nước. Hệ thống tính toán tổng số tiền dựa trên giá cả đã cài đặt và hiển thị số tiền cho người dùng trước khi lưu.

#### 3. Luồng công việc (User Flow):

##### 3.1 Ghi nhận điện nước tiêu thụ thủ công

###### Flow

```mermaid
graph TB
  A((Bắt đầu: Màn hình Home)) -->|Chọn FAB| B((Màn hình Calculate))
  B -->|Người dùng nhập thông tin| B1{Kiểm tra dữ liệu}
  B1 -->|Dữ liệu hợp lệ| C((Màn hình Bill))
  B1 -->|Điện tháng này > tháng trước| B1a[Hiển thị lỗi: 'Không được nhập tháng sau cao hơn tháng trước']
B1 -->|Nước tháng này > tháng trước| B1b[Hiển thị lỗi: 'Không được nhập tháng sau cao hơn tháng trước']
B1 -->|Để trống điện/nước| B1c[Hiển thị lỗi: 'Không được để trống']
B1 -->|Điện tháng trước < tháng này| B1d[Hiển thị lỗi: 'Không được nhập tháng trước thấp hơn tháng sau']
B1 -->|Nước tháng trước < tháng này| B1e[Hiển thị lỗi: 'Không được nhập tháng trước thấp hơn tháng sau']
C -->|Chọn Hoàn Tất| D((Màn hình Home))
```

###### Process
- **Bắt đầu**: Màn hình Home.
- **Quy trình cơ bản**: 
  - 1 Người dùng: Chọn FAB
  - 2 Hệ thống: Chuyển màn hình Calculate
  - 3 Hệ thống: màn hình Calculate gồm
    - 3.1 TextField: 
      - Nội dung: Khối điện tháng này
      - Đơn vị: Kwh
    - 3.2 TextField: 
      - Nội dung: Khối nước tháng này
      - Đơn vị: Dm3
    - 3.3 TextField: Hệ thống tự điền
        - Nội dung: Khối điện tháng trước
        - Đơn vị: Kwh
    - 3.4 TextField: Hệ thống tự điền
        - Nội dung: Khối nước tháng trước
        - Đơn vị: Dm3
    - 3.5 Danh sách các TextField phụ phí. (Hệ thống điền sẵn)
    - 3.6 Button tính tiền
  - 4 Người dùng: 
    - 4.1 Nhập chỉ số khối điện tháng này
    - 4.2 Nhập chỉ số khối nước tháng này
    - 4.3 Chọn tính tiền
  - 5 Hệ thống: Chuyển sang màn hình Bill
  - 6 Hệ thống: màn hình Bill gồm
    - 6.1 Text
      - Nội dung: Thời gian tính tiền từ DD/MM/YYYY đến DD/MM/YYYY
    - 6.2 Text
      - Nội dung: Điện - Cách tính - Kết quả
    - 6.3 Text
      - Nội dung: Nước - Cách tính - Kết quả
    - 6.4 Danh sách Text các phụ phí
      - Nội dung: Tên - Giá tiền
    - 6.5 Text
      - Nội dung: Tiền nhà - Giá Tiền
    - 6.6 Text
      - Nội dung: Tổng giá tiền
    - 6.7 Button
      - Nội dung: Hoàn Tất
  - 7 Người dùng: Chọn Hoàn Tất
  - 8 Hệ Thống: Chuyển sang màn hình Home.

- **Các trường hợp ngoại lệ**: 
  - 4.1.a Người dùng: Nhập chỉ số điện tháng này cao hơn tháng trước
    - Hệ thống: Chuyển textField điện tháng này sang error với thông báo "Không được nhập tháng sau cao hơn tháng trước"
  - 4.2.a Người dùng: Nhập chỉ số nước tháng này cao hơn tháng trước
    - Hệ thống: Chuyển textField nước tháng này sang error với thông báo "Không được nhập tháng sau cao hơn tháng trước"
  - 4.a Người dùng: 
    - 4.1 Để trống
    - 4.2 Để trống
    - Hệ thống: Chuyển textField nước và điện sang error với thông báo "Không được để trống"
  - 4.a Người dùng: Nhập chỉ số điện tháng trước thấp hơn tháng này
    - Hệ thống: Chuyển textField điện tháng trước sang error với thông báo "Không được nhập tháng trước thấp hơn tháng sau"
  - 4.b Người dùng: Nhập chỉ số nước tháng trước thấp hơn tháng này
    - Hệ thống: Chuyển textField nước tháng trước sang error với thông báo "Không được nhập tháng trước thấp hơn tháng sau"

#### 4. Wireframes (nếu có):


#### 5. Tham khảo:

