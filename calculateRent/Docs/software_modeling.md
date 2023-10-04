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


| Tính tiền điện nước                 | Quản lý tiền trọ                           | Thông báo ngày tính tiền         | Khởi tạo mặc định               |
|-------------------------------------|--------------------------------------------|----------------------------------|---------------------------------|
| Ghi nhận tiêu thụ điện nước (1)     | Xem Lịch sử thanh toán tiền trọ (1)        | Thông báo đến hạn thanh toán (1) | Ghi nhận thông tin thủ công (1) |
| Nhập điện nước qua hình ảnh    (1)  | Xóa lịch sử thanh toán đã chọn (1)         | Tắt/Bật thông báo  (2)           |                                 |
| Nhập điện nước từ hình hóa đơn (2)  | Chỉnh sửa thông tin tiền trọ đã chọn (1)   |                                  |                                 |
|                                     | Chỉnh sửa thông tin mặc định tiền trọ  (1) |                                  |                                 |
|                                     | Lưu thanh toán tiền trọ mới (1)            |                                  |                                 |

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

| Tiêu đề: Xóa lịch sử thanh toán đã chọn                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| User story:  Như một người dùng của ứng dụng, tôi muốn có khả năng xóa các bản ghi lịch sử thanh toán đã chọn để tôi có thể quản lý lịch sử thanh toán một cách hiệu quả hơn. Khi tôi chọn một bản ghi lịch sử thanh toán cụ thể và yêu cầu xóa nó, hệ thống sẽ xác nhận yêu cầu của tôi và sau đó xóa bản ghi đó khỏi danh sách lịch sử thanh toán.                                                                                                                                                                                                                                             |
| System provide: Hệ thống hiển thị danh sách các bản ghi lịch sử thanh toán có sẵn cho người dùng. Người dùng có thể chọn một hoặc nhiều bản ghi lịch sử thanh toán để xóa. Hệ thống xác nhận với người dùng xem họ có chắc chắn muốn xóa các bản ghi đã chọn không. Nếu người dùng xác nhận, hệ thống sẽ xóa các bản ghi đã chọn khỏi danh sách lịch sử thanh toán. Hệ thống cung cấp thông báo xóa thành công cho người dùng. Nếu người dùng không xác nhận xóa hoặc hủy bỏ thao tác xóa, hệ thống quay lại danh sách lịch sử thanh toán mà không thực hiện xóa. |

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
    participant ScreenInnit
    participant ScreenStep1
    participant ScreenStep2
    participant ScreenStep3
    participant ViewModelInnit
    participant UseCaseInnit
    participant Repository
    participant Room
    
    User ->> ScreenInnit: Open App
    ScreenInnit ->> ViewModelInnit: haveDefaultSetting(): Boolean
    ViewModelInnit ->> UseCaseInnit: haveDefaultSetting() : Boolean
    UseCaseInnit ->> Repository: getDefaultSetting(): DefaultSetting
    Repository ->> Room: getDefaultSettingEntity(): DefaultSettingEntity
    
    Room -->> Repository: DefaultSettingEntity?
    Repository ->> Repository: convertEntityToObject(): DefaultSetting?
    Repository -->> UseCaseInnit: DefaultSetting?
    
    alt DefaultSetting không null 
        UseCaseInnit -->> ViewModelInnit: True
        ViewModelInnit -->> ScreenInnit: True
        ScreenInnit ->> ViewModelInnit: Chuyển sang Quản lý tiền trọ UC-002
    else DefaultSetting thì null
        UseCaseInnit -->> ViewModelInnit: False
        ViewModelInnit -->> ScreenInnit: False
        ScreenInnit ->> ScreenStep1: Hiện Step1
        loop 
            User ->> ScreenStep1: Nhập tiền nhà, ngày đóng trọ
            User ->> ScreenStep1: Chọn tiếp tục
            ScreenStep1 ->> ViewModelInnit: checkValueInvalid: Boolean()
            alt valid
                ViewModelInnit -->> ScreenStep1: True
                ScreenStep1 -->> ScreenInnit: Hiện Step 2
                ScreenInnit ->> ScreenStep2: Hiện Step 2
                User ->> ScreenStep2: Nhập tiền điện nước, mốc khởi điểm điện nước
                User ->> ScreenStep2: Chọn tiếp tục
                ScreenStep2 ->> ViewModelInnit: checkValueInvalid: Boolean()
                alt valid
                    ViewModelInnit -->> ScreenStep2: True
                    ScreenStep2 -->> ScreenInnit: Hiện Step 3
                    ScreenInnit ->> ScreenStep3: Hiện Step 3
                    User ->> ScreenStep3: Nhập phụ phí
                    User ->> ScreenStep3: Chọn tiếp tục
                    ScreenStep3 ->> ViewModelInnit: checkValueInvalid: Boolean()
                    alt valid
                        ViewModelInnit -->> ScreenStep3: True
                        ScreenStep3 ->> ScreenInnit: Chuyển sang step mới
                        ScreenInnit ->> ViewModelInnit: insertDefaultSetting(default)
                        ViewModelInnit ->> UseCaseInnit: insertDefaultSetting(default)
                        UseCaseInnit ->> Repository: insertDefaultSetting(default)
                        Repository ->> Repository: convertObjectToEntity(): DefaultSettingEntity?
                        Repository ->> Room: insertDefaultSettingEntity(defaultEntity)
                        Repository ->> Room: insertDefaultSurchargeEntity(defaultSubEntity)
                        ScreenInnit ->> ViewModelInnit: Chuyển sang Quản lý tiền trọ UC-002
                    else invalid
                        ViewModelInnit -->> ScreenStep3: False
                        ScreenStep3 ->> ScreenStep3: Thông báo lỗi, nhập lại
                    end
                else invalid
                    ViewModelInnit -->> ScreenStep2: False
                    ScreenStep2 ->> ScreenStep2: Thông báo lỗi, nhập lại
                end
            else invalid
                ViewModelInnit -->> ScreenStep1: False
                ScreenStep1 ->> ScreenStep1: Thông báo lỗi, nhập lại
            end
        end
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
