let cartItem = new CartItem(), product = new Product(), category = new Category(), customer = new Customer();

const drawAllItemsCustomerOrder = () => {
    return $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/carts/cart-item/" + $("#userNameLogin").text(),
    }).done((data) => {
        $(".OrderItem tbody").html("")
        if (data.length === 0) {
            $(".allCartItem").html("")
            $(".allCartItem").append(`<h2>Giỏ hàng trống</h2>`)
            $(".allCartItem").css('text-align', 'center');
        }
        let sum = 0
        $.each(data, (i, item) => {
            cartItem = item;
            let str = `
                        <tr id="tr_${cartItem.id}">
                            <td class="text-center align-middle">
                            <input type="hidden" id="idOrderItem" value="${cartItem.id}">
                                <a href="#"><img
                                        width="80px"
                                         height="100px" src="${cartItem.product.urlImage}"
                                        alt=""
                                        title=""
                                        class="img-thumbnail"></a>
                            </td>
                            <td class="text-left align-middle">
                                <a href="">${cartItem.product.title}</a>
                                <br>
                                <small>Danh mục: ${cartItem.product.category.title}</small>
                            </td>
                            <td class="text-left align-middle">
                                <div class="input-group btn-block" style="max-width: 134px;">
                                    <input style="width: 60px;" type="number" id="inputQuantity_${cartItem.id}" onchange="onChangeInputQuantityCartItem(${cartItem.id})" max="10"
                                           name="inputQuantity" value="${cartItem.quantity}" min="1"
                                           class="form-control inputQuantity">
                                    <span class="input-group-btn">
                                            <button type="button" style="right: 34px;"
                                                      class="btn btn-danger btn-delete-cart"
                                                      data-id="${cartItem.id}"
                                                     ><i
                                                      class="fa fa-times-circle"></i></button>
                                    </span>
                                </div>
                            </td>
                            <td class="text-right align-middle" >${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(cartItem.price)}</td>
                            <td class="text-right align-middle">${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(cartItem.totalPrice)}</td>
                        </tr>
                `;
            $(".OrderItem tbody").append(str);
            sum += cartItem.totalPrice;
        });
        $(".OrderItem tfoot").html("");
        let str1 = `
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td  class="text-right align-middle">Tổng tiền:</td>
            <td  class="text-right align-middle">${new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(sum)}</td>
        </tr>
        `

        $(".OrderItem tfoot").append(str1)
        handleDeleteCartItem();
    }).fail(() => {
        App.IziToast.showErrorAlert("Giỏ hàng trống!!");
    })
}

const handleDeleteCartItem = () => {
    $(".btn-delete-cart").on("click", function () {
        let id = $(this).data("id");
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/api/carts/cart-item/" + id,
        }).done((data) => {
            App.IziToast.showSuccessAlert("Bạn đã xóa cart thanh công!")
            drawAllItemsCustomerOrder()
        }).fail((e) => {
            console.log(e)
        })
    })
}

const getCartItemByIdCartItem = (id) => {
    return $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/carts/cart-item/id/" + id,
    }).done((data) => {
        cartItem = data;
    }).fail((e) => {
        console.log(e);
    })
}

const onChangeInputQuantityCartItem = (id) => {
    getCartItemByIdCartItem(id).then(() => {
        if ($("#inputQuantity_" + cartItem.id).val() <= 0) {
            App.IziToast.showErrorAlert("số lượng sản phẩm phải lớn hơn 0!");
            $("#inputQuantity_" + cartItem.id).val(cartItem.quantity);
            return;
        }
        let pattern = /\d/;
        if (pattern.test($("#inputQuantity_" + cartItem.id).val()) === false) {
            App.IziToast.showErrorAlert("đây không phải số");
            $("#inputQuantity_" + cartItem.id).val(cartItem.quantity);
            return;
        }
        cartItem.quantity = $("#inputQuantity_" + cartItem.id).val();
        cartItem.totalPrice = Number(cartItem.price) * Number(cartItem.quantity);
        $.ajax({
            "headers": {
                "accept": "application/json",
                "content-type": "application/json"
            },
            "type": "PUT",
            "url": "http://localhost:8080/api/carts/cart-item-change-input",
            "data": JSON.stringify(cartItem)
        }).done((data) => {
            drawAllItemsCustomerOrder();
        }).fail((e) => {
            console.log(e);
        })
    })
}


const doCreateOrder = () => {
    $(".btn-buy").on("click", function () {
        if ($("#emailCustomer").val() != $("#userNameLogin").text()) {
            App.IziToast.showErrorAlert("Email không đúng với tên đăng nhập");
            $("#emailCustomer").val($("#userNameLogin").text());
            return;
        }
        delete customer.id;
        customer.fullName = $("#fullNameCustomer").val();
        customer.email = $("#emailCustomer").val();
        customer.phone = $("#phoneCustomer").val();
        customer.urlImage = "https://png.pngtree.com/png-vector/20190215/ourlarge/pngtree-vector-question-mark-icon-png-image_515448.jpg";
        customer.address = $("#addressCustomer").val();
        $.ajax({
            "headers": {
                "accept": "application/json",
                "content-type": "application/json"
            },
            "type": "POST",
            "url": "http://localhost:8080/api/orders/add",
            "data": JSON.stringify(customer),
        }).done((data) => {
            App.IziToast.showSuccessAlert("Tạo đơn hàng thành công!!");
            $(".allCartItem").html("");
            $(".allCartItem").append(`<h2>Giỏ hàng trống</h2>`)
            $(".allCartItem").css('text-align', 'center');
            $(".btn-Cart-item").html("");
            let str = `
        <img class="icon-in-menu" src="https://zunezx.com/public/home/img/w-shop.png" alt="Giỏ hàng"><span style=" color: white;
                                                                    background-color: red;
                                                                    border-radius: 50%;
                                                                    position: relative;
                                                                    padding: 2px 4px 2px 4px;
                                                                    font-size: 10px;
                                                                    left: -5px;
                                                                    bottom: 5px;">0</span>
        `
            $(".btn-Cart-item").append(str);
        }).fail((jqXHR) => {
            console.log(jqXHR);
            App.IziToast.showErrorAlert("Tạo đơn hàng thất bại!");
        })
    })
}


$(() => {
    drawAllItemsCustomerOrder()
    $("#emailCustomer").val($("#userNameLogin").text());
    doCreateOrder();
})
