class App {
    static SweetAlert = class {
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                position: 'top-end',
                text: t,
                timer: 1500
            })
        }


        static showConfirmDelete() {
            Swal.fire({
                title: 'Bạn có chắc chắn muốn xóa sản phẩm không?',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'Xóa',
                denyButtonText: `Không!`,
            })
            //     .then((result) => {
            //     /* Read more about isConfirmed, isDenied below */
            //     if (result.isConfirmed) {
            //         Swal.fire('Đã xóa!', '', 'Thành công')
            //     } else if (result.isDenied) {
            //         Swal.fire('Các thay đổi không được lưu', '', 'thông tin1')
            //     }
            // })
        }
    }

    static IziToast = class  {
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                message: m,
                timer: 2000,

            });
        }
        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                position: 'topRight',
                message: m,
                timer: 2000,
            });
        }
    }


}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}
class User {
    constructor(id, userName, password, role) {
        this.id = id;
        this.username= userName;
        this.password = password;
        this.role = role;

    }
}

class Customer {
    constructor(id, fullName, email, phone, urlImage, address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.urlImage = urlImage;
        this.address = address;
    }
}
class Role {
    constructor(id, code, name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
class Cart {
    constructor(id,grandTotal,user) {
        this.id = id;
        this.grandTotal = grandTotal;
        this.user = user;
    }
}

class CartItem {
    constructor(id, price, quantity, title, totalPrice, product, cart) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.totalPrice = totalPrice;
        this.product = product;
        this.cart = cart;
    }
}

class Product {
    constructor(id, title, urlImage, price, quantity,category ) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.urlImage = urlImage;
        this.category = category;
    }
}

class Category{
    constructor(id , title) {
        this.id = id;
        this.title = title;
    }
}




class Order {
    constructor(id,email,fullName, address, status, phone, createdAt, updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}


