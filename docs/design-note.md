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

### Enrollment
Đại diện cho một lần đăng ký thể giữa một Student và một Course
Lưu ngày đăng ký và trạng thái (ACTIVE / WAITLISTED / CANCELLED)
Có thể tự cancel hoặc activate từ waitlist

### StudentEmail
Validate format email khi khởi tạo.

### CourseCode
Validate format của course code (match C001, C002...)

### CourseSchedule
Lưu lịch học (ngày, giờ bắt đầu, giờ kết thúc)
Method `conflictsWith()` kiểm tra hai lịch trùng nhau

### EnrollmentPolicy
Validate trước khi cho phép đăng ký: student có active ko, đã đăng ký chưa, course có mở ko, đủ prerequisite chưa, có trùng lịch không

### EnrollmentService
Nhận yêu cầu register/cancel, gọi EnrollmentPolicy kiểm tra trước, nếu pass thì cho Course xử lý. Giữ danh sách courses để kiểm tra schedule conflict

### CourseReport
Tách riêng text report ra khỏi Course
Nhận Course và format output thành enrollment list

### OperationResult
Chứa các kết quả của operations: success, ErrorCode, message
Dùng static factory method `success()` và `failure()`

## 3. Object collaboration

- ApplicationTest tạo Student, Course, EnrollmentPolicy, EnrollmentService, CourseReport
- ApplicationTest gọi `EnrollmentService.register(student, course)`.
- EnrollmentService gọi `EnrollmentPolicy.checkCanRegister(student, course, allCourses)`
- EnrollmentPolicy kiểm tra rules, trả OperationResult
- Nếu policy pass, EnrollmentService gọi `Course.registerEnrollment(student)`
- Course tạo Enrollment object mới với status ACTIVE hoặc WAITLISTED theo capacity
- Khi cancel hoặc changeCapacity, Course gọi `promoteWaitlistedStudentsIfPossible()` để tự động activate Enrollment đang waitlisted
- CourseReport nhận Course, đọc danh sách Enrollment, format thành text


## 4. Điểm thiết kế còn chưa tốt

- `EnrollmentService` hiện giữ list courses để check conflict, nếu sau này có nhiều services khác cũng cần list này, nên tách ra một CourseRepository