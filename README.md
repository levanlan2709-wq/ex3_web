# Ví dụ 3 - Spring Security 7 / Spring Boot 4.1.1

Project bám yêu cầu Ví dụ 3:
- Users, Roles (ROLE_USER/ROLE_ADMIN), OtpToken, Products.
- Quan hệ 1 User - N Product.
- Register + OTP email.
- Login bằng username hoặc email, Spring Security lưu SecurityContext trong session.
- Forgot Password + OTP email.
- CRUD User (ADMIN).
- CRUD Product theo user.
- Tìm kiếm + phân trang User/Product.
- Đếm tổng User, tổng Product, số Product của user đang đăng nhập.
- MapStruct DTO <-> Entity.
- Thymeleaf.
- SQL Server.
- Upload ảnh Product lên Cloudinary.

## 1. Database
Chạy `database.sql` trên SQL Server. Database: `webst10`.

## 2. SQL Server
Mặc định:
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=webst10;encrypt=false;trustServerCertificate=true
DB_USERNAME=sa
DB_PASSWORD=123456

Nếu password `sa` khác:
export DB_PASSWORD='mat_khau_sa_cua_ban'

## 3. Email OTP
Dùng Gmail App Password:
export MAIL_USERNAME='your@gmail.com'
export MAIL_PASSWORD='your_google_app_password'

Nếu chưa cấu hình mail, OTP sẽ được in ở Terminal với tiền tố `[DEV OTP]` để có thể test chức năng.

## 4. Cloudinary
export CLOUDINARY_CLOUD_NAME='...'
export CLOUDINARY_API_KEY='...'
export CLOUDINARY_API_SECRET='...'

Cloudinary là bắt buộc để test upload ảnh.

## 5. Run
mvn spring-boot:run

URL: http://localhost:8082/login

Admin mẫu:
- username: admin
- email: admin@gmail.com
- password: 123456

## Luồng test
1. Login admin -> Dashboard.
2. /admin/users -> search/paging/edit/delete User.
3. /products -> CRUD/search/paging Product, upload Cloudinary.
4. Logout -> Register -> lấy OTP email (hoặc `[DEV OTP]` trong terminal nếu chưa cấu hình mail) -> verify -> login.
5. Logout -> Forgot password -> OTP -> reset password -> login lại.
