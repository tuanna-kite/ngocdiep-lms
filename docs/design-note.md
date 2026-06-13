# Design Note - Session 01

## 1. Danh sách class

### DomainClass
- Student
- Course
- Enrollment
### Value Object
- StudentEmail
- CourseCode
- CourseSchedule
### Enums
- StudentStatus
- EnrollmentStatus
- CourseLevel
- ErrorCode
### Service
- EnrollmentPolicy
- EnrollmentService
- CourseReport
- OperationResult
### Demo
- ApplicationTest

## 2. Responsibility

### Student
Đại diện cho sinh viên, lưu thông tin id, name, email.
Lưu status, và danh sách CourseCode đã hoàn thành
Tự quản lý changeEmail, suspend, activate, completeCourse

### Course
Đại diện cho khóa học, lưu thông tin code, name, capacity, level, schedule, prerequisite, danh sachs Enrollment
Tự quản lý registerEnrollment, cancelEnrollment, changeCapacity, rename, open và closeRegistration
Khi có slot trống (cancel hoặc tăng capacity), Course sẽ auto promote waitlisted students

### EnrollmentResult
- Đóng gói dữ liệu kết quả của một phiên đăng ký, bao gồm trạng thái thành công và lý do chi tiết nếu fail

### Main
Tạo object và chạy demo chương trình.

## 3. Object collaboration

- Main tạo Student và Course.
- Main gọi Course.addStudent(Student).
- Course kiểm tra capacity và duplicate.
- Course lưu Student nếu hợp lệ.
- Main tiếp nhận EnrollmentResult, đọc và display message.


## 4. Điểm thiết kế còn chưa tốt

- Course đang có method print, có thể sau này tách ra class khác.
- addStudent chỉ trả true/false, chưa nói rõ lý do thất bại.
- Chưa có custom exception.
- Chưa có Enrollment object riêng.