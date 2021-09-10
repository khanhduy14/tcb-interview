#TECHCOMBACK INTERVIEW
Sản phẩm em sử dụng java spring boot để phát triển. Ứng dụng cung cấp 2 endpoints:
- POST /update : Tạo mới hoặc cập nhập pool
- POST /query : Tính giá trị quantile dựa trên poolId và percentile
Để trực quan hóa và để test endpoints em có tích hợp swagger vào sản phẩm.

###DESIGN
####Architecture
- Presentation Layer : Xử lý các request (controller)
- Business Layer : Chứa các logic nghiệp vụ (service)
- Persistence Layer : Cung cấp các chức năng lưu trữ (model, storage)

Để đơn giản nên em không sử dụng Database mà thay vào đó em sử dụng một **HashMap** để lưu trữ dữ liệu các pool với key là các poolId
####How to calculate quantile
Em sử dụng thuật toán nearest-rank để tính giá trị quantile.
Trước khi lưu vào static map em sẽ sắp xếp poolValues theo thứ tự tăng dần. Khi query em chỉ cần lấy ra với index = [n * percentile / 100] với n là số lượng phần tử của poolValues
[https://en.wikipedia.org/wiki/Percentile#Worked_examples_of_the_nearest-rank_method
](https://en.wikipedia.org/wiki/Percentile#Worked_examples_of_the_nearest-rank_method)
###HOW TO RUN

- Requirement: 
  - Jdk1.8
  - maven
- Test Command: `./mvnw test`
- Run Command: `./mvnw spring-boot:run`


###Test endpoints with swagger
Using swagger for test endpoints: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

###HA-SCALABILITY
HA là kiến trúc làm tăng tính sẵn sãng của các endpoints nghĩa là khi client gửi request đến thì luôn có ít nhất 1 server trong trạng thái sẵn sàng phục vụ trong bất kỳ hoản cảnh nào.
Scalability là tăng khả năng xử lý công việc của một hệ thống. 
Với bài toán này có thể tận dụng kiến trúc serverless của AWS để xây dựng các endpoints (API Gateway, Lambda, DynamoDB ...)
