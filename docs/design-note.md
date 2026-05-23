# Design Note - Session 01

## 1. Danh sách class

- Student
- Course
- Main

## 2. Responsibility

### Student
Đại diện cho sinh viên, lưu thông tin id, name, email.

### Course
Đại diện cho khóa học, lưu thông tin id, name, maxStudents và danh sách sinh viên đã đăng ký.

### Main
Tạo object và chạy demo chương trình.

## 3. Object collaboration

- Main tạo Student và Course.
- Main gọi Course.addStudent(Student).
- Course kiểm tra capacity và duplicate.
- Course lưu Student nếu hợp lệ.


## 4. Điểm thiết kế còn chưa tốt

- Course đang có method print, có thể sau này tách ra class khác.
- addStudent chỉ trả true/false, chưa nói rõ lý do thất bại.
- Chưa có custom exception.
- Chưa có Enrollment object riêng.